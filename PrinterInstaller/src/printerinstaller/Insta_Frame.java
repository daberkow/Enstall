/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printerinstaller;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sun.misc.Sort;

/**
 *
 * @author Dan
 */
public class Insta_Frame extends javax.swing.JFrame {
    public static Boolean canceled = false;
    /**
     * Creates new form Insta_Frame
     */
    public Insta_Frame() {
        initComponents();
    }

    public void run_connect(String pass_printer)
    {
        PritnerConnectLabel.setText("Connecting To " + pass_printer);
        
        SetableStatus.setText("Fetching Server List...");
        printercore Core_Code = new printercore(false);
        List<PrintServer> Holding_List = Core_Code.get_cached_printers();
        if (Holding_List == null && !canceled)
        {
            SetableStatus.setText("Error Getting List... Please Close...");
            
        }else{
            SetableProgressBar.setValue(10);
            SetableStatus.setText("Comparing Server List to System...");
            String[] Servers_to_use = Core_Code.get_servers_string(Core_Code.scan_enviroment());

            List<PrintServer> Correct_servers = new ArrayList<PrintServer>();
            if (canceled) {return;}
            for (PrintServer single_server : Holding_List) // servers in list
            {
                for(String avaible_server : Servers_to_use) //servers this architecture should use
                {
                    for (InetAddress address : single_server.get_address())//OF all things holy, A TRIPLE UNCONTROLLED FOR LOOP
                    {
                        if (address.getHostName().equals(avaible_server))
                        {
                            Correct_servers.add(single_server);
                            break;
                        }
                    }
                }
            }
            if (canceled) {return;}
            if (Correct_servers.size() > 0)
            {
                SetableStatus.setText("Checking connection to available server");
                SetableProgressBar.setValue(20);
                int[] Server_ranker_2000 = new int[Correct_servers.size()];
                for (int i = 0; i < Correct_servers.size(); i++)
                {
                    int returned_status = Core_Code.check_server(Correct_servers.get(i).get_address()[0].getHostName()); // assuming 1 IP 
                    switch (returned_status)
                    {
                        //case 0 and 1 may be able to be used better but they are just there right now
                        case 0:
                            Correct_servers.get(i).set_connected(0);
                            break;
                        case 1:
                            Correct_servers.get(i).set_connected(1);
                            break;
                        case 2:
                            Correct_servers.get(i).set_connected(2);
                            break;
                    }
                
                    int returned_result = Core_Code.check_printer(Correct_servers.get(i).get_address()[0].getHostName(), pass_printer);
                    switch (returned_result)
                    {
                        case 0:
                            //printer not listed, install time, check if authenticated then install
                            switch (Correct_servers.get(i).get_connected())
                            {
                                case 0:
                                    // no conenction
                                    Correct_servers.get(i).set_rank(0);
                                    break;
                                case 1:
                                    //broken conenction
                                    Correct_servers.get(i).set_rank(1);
                                    break;
                                case 2:
                                    //good connection
                                    Correct_servers.get(i).set_rank(2);
                                    break;
                            }       
                            break;
                        case 1:
                            //Printer does exist, if authentication failed fix that
                            switch (Correct_servers.get(i).get_connected())
                            {
                                case 0:
                                    // no conenction
                                    Correct_servers.get(i).set_rank(3);
                                    break;
                                case 1:
                                    //broken conenction
                                    Correct_servers.get(i).set_rank(4);
                                    break;
                                case 2:
                                    //good connection, why it not working? RESTART ALL THE THINGS
                                    Correct_servers.get(i).set_rank(5);
                                    break;
                            }  
                            break;
                    }
                }
                //Servers are now ranked in Server_ranker_2000!
                PrintServerCompare comparator = new PrintServerCompare();
                Collections.sort(Correct_servers, comparator);

                for (int i = 0; i < Correct_servers.size(); i++)
                {
                    //servers are now sorted, go down the list and try to connect to a pritner
                    switch(Correct_servers.get(i).get_rank())
                    {//stopepd here, need to do different conneciton methods
                        case 0:
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;       
                    }
                }
                
            }else{
                SetableStatus.setText("No compatible servers found");
                
            }
        }
            
            
        int kittens = 5;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Cancel_Button = new javax.swing.JButton();
        StatusLabel = new javax.swing.JLabel();
        SetableStatus = new javax.swing.JLabel();
        SetableProgressBar = new javax.swing.JProgressBar();
        PritnerConnectLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(300, 200));

        Cancel_Button.setText("Cancel");
        Cancel_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cancel_ButtonActionPerformed(evt);
            }
        });

        StatusLabel.setText("Status:");

        SetableStatus.setText("Starting...");

        PritnerConnectLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        PritnerConnectLabel.setText("Connecting To");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SetableProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Cancel_Button))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(StatusLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SetableStatus))
                            .addComponent(PritnerConnectLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(PritnerConnectLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StatusLabel)
                    .addComponent(SetableStatus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SetableProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(Cancel_Button)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cancel_ButtonActionPerformed
        // TODO add your handling code here:
        canceled = true;
    }//GEN-LAST:event_Cancel_ButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Insta_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Insta_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Insta_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Insta_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Insta_Frame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cancel_Button;
    private javax.swing.JLabel PritnerConnectLabel;
    private javax.swing.JProgressBar SetableProgressBar;
    private javax.swing.JLabel SetableStatus;
    private javax.swing.JLabel StatusLabel;
    // End of variables declaration//GEN-END:variables
}
