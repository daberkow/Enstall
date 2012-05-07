/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printerinstaller;

import java.net.InetAddress;

/**
 *
 * @author Dan
 */
public class PrintServer {
    private java.net.InetAddress[] Address = null;
    private String[] Printers = null;
    private int is_connected = 0;
    private int rank = 0;
    
    public PrintServer()
    {
        Address = null;
        Printers = new String[0];
    }
    
    public PrintServer(java.net.InetAddress[] Passed_address, String[] Passed_printers, int passed_connected, int passed_rank)
    {
        Address = Passed_address;
        Printers = Passed_printers;
        is_connected = passed_connected;
        rank = passed_rank;
    }
    
    public java.net.InetAddress[] get_address()
    {
        //if blank constructor is used, then no data is entered this can return null
        return Address;
    }
    
    public void set_address(java.net.InetAddress[] passed_address)
    {
        Address = passed_address;
    }
    
    public void set_address(java.net.InetAddress passed_address)
    {
        InetAddress[] temp = {passed_address};
        Address = temp;
    }

    public String[] get_Printers()
    {
        return Printers;
    }
    
    public void set_Printers(String[] passed_printers)
    {
        Printers = passed_printers;
    }
    
    public int get_connected()
    {
        return is_connected;
    }
    
    public void set_connected(int passed_connected)
    {
        is_connected = passed_connected;
    }
    
    public int get_rank()
    {
        return rank;
    }
    
    public void set_rank(int passed_rank)
    {
        rank = passed_rank;
    }
}
