/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printerinstaller;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.net.InetAddress;

/**
 *
 * @author Dan
 */
public class PrinterInstaller {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                
        String WRITE_BUFFER = "/BUFFER_START/                            /BUFFER_END/";
        
        //This allows a program that is writing to buffer
        WRITE_BUFFER = WRITE_BUFFER.substring(0, 42);
        WRITE_BUFFER = WRITE_BUFFER.substring(14, WRITE_BUFFER.length() - 1);
        String Final_buffer = "";
        for (int i = 0; i < WRITE_BUFFER.length(); i++)
        {
            if(WRITE_BUFFER.charAt(i) != ' ')
            {
                Final_buffer += WRITE_BUFFER.charAt(i);
            }
        }
        
        if (Final_buffer.length() != 0)
        {
            //System.out.println("NOT BLANK");
        }else{
            //System.out.println("BLANK");
        }
        
        if(args.length > 0)
        { //varible was passed
            if (args[0].equals("export"))
            {
                if (args.length > 1)
                    all_export_printers(args[1]);
                else
                    all_export_printers("printers.dson");
            }else{
                if (args[0].equals("connect"))
                {
                    if (args.length > 1)
                        Insta_Connect(args[1]);
                    else
                        System.out.print("No Printer given after connect");
                }
            }
        }else{
            //no printer was given
            //System.out.print("No Printer Given.\n\t Correct format is 'PrinterInstaller.jsp vcpalw'\n");
            gui_load();
            //test_functions();
        }
    }
    
    public static void Insta_Connect(String passed_connect)
    {
        Insta_Frame windows = new Insta_Frame();
        windows.setVisible(true);
        windows.run_connect(passed_connect);
        
    }
    
    public static void all_export_printers(String passed_locations)
    {
        printercore Core_Code = new printercore(false);
        String[] Enviroments = new String[1];
        Enviroments[0] = "ALL";
        java.net.InetAddress[] Server_Addresses = Core_Code.get_servers(Enviroments);
        List<PrintServer> Holding_List = new ArrayList<PrintServer>();
        String[] Credentials = Core_Code.get_credentials();
        boolean [] Matching_Status = new boolean[Server_Addresses.length];
        for(int i = 0; i < Server_Addresses.length; i ++)
        {
            PrintServer temp_server = new PrintServer();
            temp_server.set_address(Server_Addresses[i]);
            //System.out.print(Server_Addresses[i]);
            //System.out.print(Matching_Status[i] + "\n");
            String[] Printers = null;
            if (Matching_Status[i])
            {
                //This is the first connection to the servers
                Printers = Core_Code.get_printers(Server_Addresses[i], Credentials);
                temp_server.set_Printers(Printers);
            }
            //Now we have a list of for this print server and all the pritners on it
            Holding_List.add(temp_server);
        }
        
        System.out.println();
        
        try{
            // Create file 
            FileWriter fstream = new FileWriter(passed_locations);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(Core_Code.create_DSON(Holding_List.toArray(new PrintServer[0])));
            //Close the output stream
            out.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        
        
    }
    
    public static void test_functions()
    {
        printercore Core_Code = new printercore(true);
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
            //took out fake testing code
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
