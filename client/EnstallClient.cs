using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Gtk;
using System.Text.RegularExpressions;

namespace ETUEtest
{
    class EnstallClient
    {
        //List member varaibles for packages
        private List<ETUEPackage> packages;
        private List<bool> inbool;

        //Default width and height of the window
        public const int DEFAULT_WIDTH  = 640;
        public const int DEFAULT_HEIGHT = 480;

        //const strings for window title and regex match pattern
        private  const String winTitle = "Enstall: Because Class is Hard Enough";
        private const String matchPattern = "id='package_title'><.*?>(?<pTitle>.*?)</a>.*?<a href='(?<pLink>.*?)'>";

        //Global gui elements
        private ListStore ls;
        private Window clientWin;

        //Default constructor:
        public EnstallClient() : this( DEFAULT_WIDTH, DEFAULT_HEIGHT )
        {
            
        }

        //Constructor to accpe custom width/height
        public EnstallClient(int width, int height)
        {
            //Initialize the List member variables, then populate the package list from the given url
            this.packages = new List<ETUEPackage>();
            this.inbool   = new List<bool>();
            populateList("http://beta.ntbl.co/enstall/packages.php");

            //GTK# initialization
            Application.Init();

            //Initialize the window and add gui elements
            this.clientWin = new Window( winTitle );
            this.clientWin.Resize(width, height);

            createGUIElements();

            //Show everything and finally run
            this.clientWin.ShowAll();
            Application.Run();
        }

        private int populateList(String url)
        {
            //Initialize the regex used to parse the site
            Regex re = new Regex(matchPattern, RegexOptions.Singleline);

            //Find the matches
            MatchCollection matches = re.Matches(webUtil.GetHtmlFromUrl(url));
            //For each match, extract the title and link, and construct a Package object from them
            foreach (Match m in matches)
            {
                //Extract the package title and link from the match
                String pTitle = m.Groups[1].ToString();
                String pLink = m.Groups[2].ToString();

                //Create and add a new ETUE package to the list
                this.packages.Add(new ETUEPackage(pLink, pTitle));

                //For now, each package will default to "not installed" so add false to the list
                this.inbool.Add(false);
            }

            return 0; //No error

            //TODO: add proper error handling 
        }

        private void createGUIElements()
        {
            //Vertical box for organization
            VBox vbox = new VBox();
            vbox.Homogeneous = false;

            //ScrolledWindow to handle the scrolling of the list
            ScrolledWindow sw = new ScrolledWindow();

            //The treeview that all packages will be stored in
            TreeView tv = new TreeView();

            //Add the TreeView to the ScrolledWindow
            sw.AddWithViewport(tv);

            //Self explanatory, the liststore well store rows of :
            //                (  [string],       [string],        [string],       [string],      [bool],       [string] )
            ls = new ListStore(typeof(string), typeof(string), typeof(string), typeof(string), typeof(bool), typeof(string));

            //Cell Renderer for the toggle button column
            CellRendererToggle crt = new CellRendererToggle();
            crt.Activatable = true;         //Activatable specifies whether or not users can toggle the button
                                            // Here we're using these to "Mark For Installation," so we want them
                                            // to be "toggleable"
            crt.Toggled += buttonToggled;   //Add the event handler for this renderer

            //Add columns to the tree view                         (Optional)
            //              | Name          | Cell Renderer       | Property | Column number
            tv.AppendColumn("Package Title", new CellRendererText(), "text",   0);
            tv.AppendColumn("Version",       new CellRendererText(), "text",   1);
            tv.AppendColumn("Organization",  new CellRendererText(), "text",   2);
            tv.AppendColumn("Description",   new CellRendererText(), "text",   3);
            tv.AppendColumn("Install",       crt,                    "active", 4);
            tv.AppendColumn("",              new CellRendererText(), "text",   5);
                //Column 5 is an empty column.  GTK treeview likes to stretch the last column to fit the
                // rest of the whitespace left over.  If we insert an empty column, this fixes the problem

            //Loop through all packages, and add an entry for each package
            for(int i = 0; i < packages.Count; i++) 
            {
                //Construct the row from the package[i]'s information
                ls.AppendValues(packages[i].getPackageTitle(), packages[i].getFileVersion(),
                    packages[i].getOrganization(), packages[i].getDescription(), inbool[i], "");
            }

            //Set the model to the ListStore
            tv.Model = ls;

            //Button to install all marked packages
            Button installButton = new Button("Install Selected Packages");
            installButton.Clicked += installButtonClicked;
            installButton.HeightRequest = 30;

            //Add the components to the box
            vbox.PackStart(installButton, false, false, 0);  //Add the install button to the beginning (PackStart)
            vbox.PackEnd(sw);                                //Add the scrolledWindow to the end       (PackEnd)

            //Finally, add the box to the window
            this.clientWin.Add(vbox);
        }

        //The togglebutton event handler: 
        //  Finds the index of the button toggled, then toggles the corresponding boolean
        private void buttonToggled(object sender, ToggledArgs args)
        {
            //Iterator to iterate through the model
            TreeIter iter;
            TreePath tp = new TreePath(args.Path);
            //If we can found the toggled button
            if (ls.GetIter(out iter, tp))
            {
                //Get the old value, and set the value to the opposite
                bool old = (bool)ls.GetValue(iter, 4);
                ls.SetValue(iter, 4, !old);

                //This method of getting the index seems pretty janky, but it works for now
                // In the spirit of improving TODO: Find a better way to do this
                inbool[Convert.ToInt32(tp.ToString())] = !old;
            }
        }

        [GLib.ConnectBefore]
        private void installButtonClicked(object sender, EventArgs args)
        {
            //Loop through all of the package booleans and install all of the marked packages
            for (int i = 0; i < this.inbool.Count; i++)
            {
                //If this was marked for installation
                if (inbool[i])
                {
                    //Run the command
                    RunCommand(packages[i].getBasicScript(), "", false);
                }
            }

            //TODO: Handle error cases here
        }

        //Function to run commands, like the basic script and 
        static bool RunCommand(string szCmd, string szArgs, bool wait)
        {
            //If there is no command, simply return false
            if (szCmd == null) return false;

            //Otherwise, create a new process with the given command and arguments
            System.Diagnostics.Process myproc = new System.Diagnostics.Process();
            myproc.EnableRaisingEvents = false;
            myproc.StartInfo.FileName = szCmd;
            myproc.StartInfo.Arguments = szArgs;

            //Start the process
            if (myproc.Start())
            {
                //Using WaitForExit( ) allows for the host program
                //to wait for the command its executing before it continues
                if (wait) myproc.WaitForExit();
                else myproc.Close();
                return true;
            }
            else return false; // If the process could not start, it failed
        }
    }
}
