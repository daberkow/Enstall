/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printerinstaller;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dan
 */
public class PrinterInstaller {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length > 0)
        { //varible was passed
        
        }else{
            //no printer was given
            //System.out.print("No Printer Given.\n\t Correct format is 'PrinterInstaller.jsp vcpalw'\n");
            gui_load();
            //test_functions();
        }
    }
    
    public static void test_functions()
    {
        printercore Core_Code = new printercore();
        List<PrintServer> Server_stats = new ArrayList<PrintServer>();
        String[] Enviroments = Core_Code.scan_enviroment();
        java.net.InetAddress[] Server_Addresses = Core_Code.get_servers(Enviroments);
        boolean [] Matching_Status = new boolean[Server_Addresses.length];
        
        String[] Credentials = Core_Code.get_credentials();
        
        for(int i = 0; i < Server_Addresses.length; i ++)
        {
            PrintServer temp_server = new PrintServer();
            temp_server.set_address(Server_Addresses[i]);
            System.out.print(Server_Addresses[i]);
            Matching_Status[i] = Core_Code.test_server(Server_Addresses[i]);
            temp_server.set_connected(Matching_Status[i]);
            System.out.print(Matching_Status[i] + "\n");
            String[] Printers = null;
            if (Matching_Status[i])
            {
                //This is the first connection to the servers
                Printers = Core_Code.get_printers(Server_Addresses[i], Credentials);
                temp_server.set_Printers(Printers);
            }
            
            //Now we have a list of for this print server and all the pritners on it
            for (String line : Printers)
            {
                System.out.println(line);
            }
        }
    }
    
    public static void gui_load()
    {
        Manager Window_Manager = new Manager();
        Window_Manager.setVisible(true);
    }
}
