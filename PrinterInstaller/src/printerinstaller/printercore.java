package printerinstaller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/** Core code for printer manager
 *
 * @author Dan Berkowitz
 */
public class printercore {
    //This is set if you want debug info sent to console
    private boolean enable_Debug = false;
    
    //this inisilaizes the code with debugging or no debugging
    public printercore(boolean passed_enable_debug)
    {
        enable_Debug = passed_enable_debug;
    }
    
    //You may say, "but dan why are these stand alone functions" because now its easy to add and remove printer servers
    public String[] x86Servers()
    {
        String[] return_data = new String[2];
        return_data[0] = "pmanager.win.rpi.edu";
        return_data[1] = "pmanager2.win.rpi.edu";
        return return_data;
    }
    public String[] x64Servers()
    {
        String[] return_data = new String[2];
        return_data[0] = "pmanager64.win.rpi.edu";
        return_data[1] = "pmanager64a.win.rpi.edu";
        return return_data;
    }
    
    //This returns enviromental data for later use
    public String[] scan_enviroment()
    {
        String[] return_data = new String[2];
        return_data[0] = System.getProperty("os.name");
        return_data[1] = System.getProperty("os.arch");
        return return_data;
    }
    
    //This returns what servers you should use for which enviroment, 
    //right now it has room for linux and mac but since the pp doesnt support that...
    public java.net.InetAddress[] get_servers(String[] Enviroment)
    {
        List<String> Servers = new ArrayList<String>();
        
        if(!Enviroment[0].isEmpty() && Enviroment[0].equals("ALL"))
        {
            Servers.addAll(Arrays.asList(x86Servers()));
            Servers.addAll(Arrays.asList(x64Servers()));
        }else{
            if (Enviroment.length == 2)
            {
                //good enviroment passed
                //java doesnt support switching strings cause even though its updated every day no features were added since 1996
            
                if (Enviroment[0].substring(0, 3).equals("Win"))
                {

                    if(Enviroment[1].equals("x86"))
                    {
                        Servers.addAll(Arrays.asList(x86Servers()));

                    }else{
                        if(Enviroment[1].equals("64"))
                        {
                           Servers.addAll(Arrays.asList(x64Servers()));
                        }else{
                            //ITANIUM 4 TEH WIN, or ARM, thats ok too
                        }
                    }
                }else{
                    if (Enviroment[0].substring(0, 3).equals("Mac"))
                    {
                    }else{
                        //Linux and such can go here
                    }
                }
            }
        }
        
        return convert_string_inet(Servers);
    }
    
    //This returns what servers you should use for which enviroment, 
    //right now it has room for linux and mac but since the pp doesnt support that...
    public String[] get_servers_string(String[] Enviroment)
    {
        List<String> Servers = new ArrayList<String>();
        
        if(!Enviroment[0].isEmpty() && Enviroment[0].equals("ALL"))
        {
            Servers.addAll(Arrays.asList(x86Servers()));
            Servers.addAll(Arrays.asList(x64Servers()));
        }else{
            if (Enviroment.length == 2)
            {
                //good enviroment passed
                //java doesnt support switching strings cause even though its updated every day no features were added since 1996
            
                if (Enviroment[0].substring(0, 3).equals("Win"))
                {

                    if(Enviroment[1].equals("x86"))
                    {
                        Servers.addAll(Arrays.asList(x86Servers()));

                    }else{
                        if(Enviroment[1].equals("64"))
                        {
                           Servers.addAll(Arrays.asList(x64Servers()));
                        }else{
                            //ITANIUM 4 TEH WIN, or ARM, thats ok too
                        }
                    }
                }else{
                    if (Enviroment[0].substring(0, 3).equals("Mac"))
                    {
                    }else{
                        //Linux and such can go here
                    }
                }
            }
        }
        
        return Servers.toArray(new String[0]);
    }
    
    public java.net.InetAddress[] convert_string_inet(String Servers)
    {
        java.net.InetAddress[] Server_Link = null;
        try
        {
            Server_Link = java.net.InetAddress.getAllByName(Servers);
        }catch(Throwable e){
        }
            
        return Server_Link;
    }
    
    public java.net.InetAddress[] convert_string_inet(List<String> Servers)
    {
        //here the dns names are translated to INET ADDREsses
       List<java.net.InetAddress> return_data = new ArrayList<InetAddress>(); // Right now there are two 32 bit and 2 64 bit servers
       if (Servers.size() > 0)
       {
            String[] holding_array = Servers.toArray(new String[0]);
               
            for(int i = 0; i < Servers.size(); i++)
            {
                try
                {
                    java.net.InetAddress[] Server_Link = java.net.InetAddress.getAllByName(holding_array[i]);
                    return_data.addAll(Arrays.asList(Server_Link));
                }catch(Throwable e){
                }
            }
            return return_data.toArray(new java.net.InetAddress[0]);
        }else
        {
            //bad enviroment
            return return_data.toArray(new java.net.InetAddress[0]);
        }
    }
    
    //Yeah... The test code isnt testing much right now
    public boolean test_server(java.net.InetAddress Passed_Address)
    {
        boolean found = true;

        /*try{
            found = Passed_Address.isReachable(1000);
        }catch(Exception e){
            System.out.print("AHHH");
        }*/
        return found;
    }
    
    /**This is code to go to the different servers with credentials passed in and attempt to get a listing of the printers
     * 
     * @param Passed_Address -- address of server
     * @param Passed_Credentials --creds
     * @return -- array of printers
     */
    public String[] get_printers(java.net.InetAddress Passed_Address, String[] Passed_Credentials)
    {
        List<String> Printers = new ArrayList<String>();
        
        if (enable_Debug) { System.out.println("net use"); }
      
        String[] servers = run_program("net use");
        boolean found = false;
        for( String s : servers)
        {
            if (s.startsWith("OK") && s.contains(Passed_Address.getHostAddress()))
            {
                found=true;
            }
        }
        if (!found)
        {
            if (enable_Debug) {System.out.println("net use \\\\" + Passed_Address.getHostName() +" /USER:WIN\\" + Passed_Credentials[0] + " "); }
            String[] connection = run_program("net use \\\\" + Passed_Address.getHostName() +" /USER:WIN\\" + Passed_Credentials[0] + " " + Passed_Credentials[1]);
            //found is false
            for (String s : connection)
            {
                if (s.contains("successful") ||  s.contains("Multiple connections to a server"))
                {
                    found = true;
                    break;
                }
            }
            if (!found)
            {
                if (enable_Debug) { System.out.print("ERROR CONNECTING"); }
            }
        }
        if (enable_Debug) { System.out.println("net view \\\\" + Passed_Address.getHostName()); }
        String[] results = run_program("net view \\\\" + Passed_Address.getHostName());
        for (int i = 0; i < results.length; i++)
        {
            if (results[i].contains(" Print  "))
            {
                Printers.add(results[i].split(" ")[0]);
            }
        }
        
        return Printers.toArray(new String[0]);
    }
    
    public List<PrintServer> get_cached_printers()
    {
        List<PrintServer> Printers = new ArrayList<PrintServer> ();
        String temp_buffer = "";
        
        try {
            URL webURL = new URL("http://beta.ntbl.co/enstall/printers/printers.dson");
            URLConnection yc = webURL.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                                        yc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) 
                temp_buffer += inputLine + "\r\n";
            in.close();
            
        }catch (Throwable e)
        {
            
        }
        //temp buffer now contains the dson
        
        for (String line : temp_buffer.split("\r\n"))
        {
            PrintServer Temp_configure = new PrintServer();
            String[] Split1 = line.split(":");
            if (Split1.length == 2)
            {
                Temp_configure.set_address(convert_string_inet(Split1[0]));
                Temp_configure.set_Printers(Split1[1].split(","));
            }
            Printers.add(Temp_configure);
        }
        
        return Printers;
    }
    
    //Quick easy (blocking) way to run a program and get data back
    private String[] run_program(String Passed_Command)
    {
        List<String> return_data = new ArrayList<String>();
        
        try {
            String line;
            //System.out.print("\n net use \\\\" + Passed_Address.getHostName() + "\n");
            Process p = Runtime.getRuntime().exec(Passed_Command);
            BufferedReader bri = new BufferedReader
                (new InputStreamReader(p.getInputStream()));
            BufferedReader bre = new BufferedReader
                (new InputStreamReader(p.getErrorStream()));
            while ((line = bri.readLine()) != null) {
                return_data.add(line);
            }
            bri.close();
            while ((line = bre.readLine()) != null) {
                return_data.add("E: " + line);
            }
            bre.close();
            p.waitFor();
            //System.out.println("Done.");
        }
            catch (Exception err) {
                System.err.println(err.toString());
        }
        
        return return_data.toArray(new String[0]);
    }
    
    public String[] get_credentials() 
    {
        String[] return_data = new String[2];
        
        Login Loggy = new Login(null, true);
        Loggy.setVisible(true);
        
        return_data[0] = Loggy.get_username();
        char[] buffer = Loggy.get_password();
        Loggy.dispose();
        return_data[1] = "";
        if (buffer.length > 0)
        {
            for( char c : buffer)
            {
                return_data[1] += c;
            }
        }
        
        return return_data;
    }
    
    //yeah its a function with a fancy name to bind two strings
    public String[] stored_credentials(String passed_username, String passed_password) 
    {
        String[] return_data = new String[2];
        
        return_data[0] = passed_username;
        return_data[1] = passed_password;
        
        return return_data;
    }
    
    //transfers the PritnerServer type to a json file
        
    public String create_DSON(printerinstaller.PrintServer[] passed_servers)
    {
        String return_string = "";
        for (int i =0; i < passed_servers.length; i++)
        {
             String[] Printer_List = passed_servers[i].get_Printers();
             return_string += passed_servers[i].get_address()[0].getHostName() + ":"; // that is a bad assumtption to make, can be fixed later
             
             for (int k = 0; k < Printer_List.length; k++)
             {
                 return_string += Printer_List[k] + ",";
             }
             return_string += "\n";
        }
        
        return return_string;
    }
}
