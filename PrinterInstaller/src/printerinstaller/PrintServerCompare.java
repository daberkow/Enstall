/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printerinstaller;

import java.util.Comparator;

/**
 *
 * @author Dan
 */
public class PrintServerCompare implements Comparator<PrintServer>{
    
    @Override
    public int compare(PrintServer server1, PrintServer server2)
    {
        if (server1.get_rank() > server2.get_rank())
        {
            return +1;
        }else{
            if (server1.get_rank() < server2.get_rank())
            {
                return -1;
            }else{
                return 0;
            }
        }
    }
}
