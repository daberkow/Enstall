using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ETUEtest
{
    class ETUEPackage
    {
        //Member variables to store the properties of the package
        private String uid = "";                //  UID
        private String title = "";              //  Package Title
        private String description = "";        //  Description
        private String basicScript = "";        //  Basic Script
        private String advancedScript = "";     //  Advanced Script
        private String organization = "";       //  Organization
        private String fileVersion = "";        //  Package version
        private String versionCreated = "";     //  ETUEVersion this package .xml was created in

        private String packageTitle = "";       //  The package title listed on the Enstall package list site

        //------< Constructors >------//

        //Constructs a default package
        public ETUEPackage()
        {
            //Do nothing
        }

        //Constructs a reader with the information from the .xml at url
        public ETUEPackage(String url)
        {
            copy( ETUEReader.parse(url) );
        }

        //Constructs a reader with the information from the .xml at url, and packageTitle pTitle
        public ETUEPackage(String url, String pTitle) : this(url)
        {
            this.packageTitle = pTitle;
        }

        public ETUEPackage(ETUEPackage package)
        {
            copy(package);
        }

        private void copy( ETUEPackage package )
        {
            //Copies all the data from the passed in package into this one
            uid              = package.getUID();
            title            = package.getTitle();
            description      = package.getDescription();
            basicScript      = package.getBasicScript();
            advancedScript   = package.getAdvancedScript();
            organization     = package.getOrganization();
            fileVersion      = package.getFileVersion();
            versionCreated   = package.getVersionCreated();
        }
        
        //------< Accessors >------//

        //Returns the UID of the package
        public String getUID()
        {
            return this.uid;
        }

        //Returns the Title of the package
        public String getTitle()
        {
            return this.title;
        }

        //Returns the Description of the package
        public String getDescription()
        {
            return this.description;
        }

        //Returns the Basic Script of the package
        public String getBasicScript()
        {
            return this.basicScript;
        }

        //Returns the Advanced Script of the package
        public String getAdvancedScript()
        {
            return this.advancedScript;
        }

        //Returns the organization of this package
        public String getOrganization()
        {
            return this.organization;
        }

        //Returns the version of this package
        public String getFileVersion()
        {
            return this.fileVersion;
        }

        //Returns the ETUE version 
        public String getVersionCreated()
        {
            return this.versionCreated;
        }

        //Returns the package title
        public String getPackageTitle()
        {
            return this.packageTitle;
        }

        //------< Mutators >------//

        //Sets the UID of the package
        public void setUID(String uid)
        {
            this.uid = uid;
        }

        //Sets the Title of the package
        public void setTitle(String title)
        {
            this.title = title;
        }

        //Sets the description of the package
        public void setDescription(String description)
        {
            this.description = description;
        }

        //Sets the Basic Script of the package
        public void setBasicScript(String basicScript)
        {
            this.basicScript = basicScript;
        }

        //Sets the Advanced Script of the package
        public void setAdvancedScript(String advancedScript)
        {
            this.advancedScript = advancedScript;
        }

        //Sets the Organization of the package
        public void setOrganization(String organization)
        {
            this.organization = organization;
        }

        //Sets the File Version of the package
        public void setFileVersion(String fileVersion)
        {
            this.fileVersion = fileVersion;
        }

        //Sets the UID of the package
        public void setVersionCreated(String versionCreated)
        {
            this.versionCreated = versionCreated;
        }

        //Sets the package title
        public void setPackageTitle(String packageTitle)
        {
            this.packageTitle = packageTitle;
        }

        //Prints the info of the package
        public void printInfo()
        {
            Console.WriteLine("Organization: {0}", this.organization);
            Console.WriteLine("File Version: {0}", this.fileVersion);
            Console.WriteLine("ETUE Version: {0}", this.versionCreated);
            Console.WriteLine("UID: {0}", this.uid);
            Console.WriteLine("Title: {0}", this.title);
            Console.WriteLine("Description: {0}", this.description);
            Console.WriteLine("Basic Script: {0}", this.basicScript);
            Console.WriteLine("Advanced Script: {0}", this.advancedScript);
        }

    }
}
