/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printerinstaller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Dan
 */
public class printercore {
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
    
    public String[] scan_enviroment()
    {
        String[] return_data = new String[2];
        return_data[0] = System.getProperty("os.name");
        return_data[1] = System.getProperty("os.arch");
        return return_data;
    }
    
    public java.net.InetAddress[] get_servers(String[] Enviroment)
    {
        List<java.net.InetAddress> return_data = new ArrayList<InetAddress>(); // Right now there are two 32 bit and 2 64 bit servers
        if (Enviroment.length == 2)
        {
            //good enviroment passed
            //java doesnt support switching strings cause even though its updated every day no features were added since 1996
           String[] Servers = null;
            if (Enviroment[0].substring(0, 3).equals("Win"))
            {
                
                if(Enviroment[1].equals("x86"))
                {
                    Servers = x86Servers();
                    
                }else{
                    if(Enviroment[1].equals("64"))
                    {
                        Servers = x64Servers();
                    }else{
                         //ITANIUM 4 THE WIN, or ARM
                    }
                }
            }else{
                if (Enviroment[0].substring(0, 3).equals("Mac"))
                {
                }else{
                    //Linux and such can go here
                }
            }

            for(int i = 0; i < Servers.length; i++)
            {
                try
                {
                    java.net.InetAddress[] Server_Link = java.net.Inet4Address.getAllByName(Servers[i]);
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
    
    public String[] get_printers(java.net.InetAddress Passed_Address, String[] Passed_Credentials)
    {
        List<String> Printers = new ArrayList<String>();
        
        System.out.println("net use");
      
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
            System.out.println("net use \\\\" + Passed_Address.getHostName() +" /USER:WIN\\" + Passed_Credentials[0] + " ");
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
                System.out.print("ERROR CONNECTING");
            }
        }
        System.out.println("net view \\\\" + Passed_Address.getHostName());
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
}
