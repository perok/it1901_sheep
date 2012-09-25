package wmsClient.gui;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class AboutBox extends JDialog implements ActionListener {


    String aboutTextFile = "aboutText.txt";

    JScrollPane sPane;
    public AboutBox(Frame parent) {
        super(parent);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        pack();
    }

    private void jbInit() throws Exception  {

        this.setTitle("About");

        JButton button  =new JButton("close");
        button.addActionListener(this);


        StringBuffer text = new StringBuffer();
        int maxLength = 0;
        try {
            InputStream is = this.getClass().getResourceAsStream(aboutTextFile);
            
            BufferedReader reader = new BufferedReader(  new InputStreamReader( is ) );
            
            String line;
            while ( (line = reader.readLine()) != null ) {
                if (line.length() > maxLength) {
                    maxLength = line.length();
                } // end of if (line.length() > maxLength)
                
                text.append( " " ).append( line ).append( "\n" );
            }             
        } catch (Exception e) {
            System.out.println("Error on reading About Infos: "+ e);
            text.append( "Error on reading About Infos" );
        }


        JPanel panel = new JPanel( new BorderLayout( 10, 10) );
                 
        JTextArea aboutText = new JTextArea( 22, maxLength+2 );
        aboutText.setEditable( false );
        aboutText.setBackground( panel.getBackground() );
        aboutText.setAlignmentY( aboutText.TOP_ALIGNMENT );
        sPane = new JScrollPane(aboutText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        panel.add( sPane , BorderLayout.CENTER );
        aboutText.setText( text.toString() );

        JPanel panel2 = new JPanel();
        panel2.add( new JLabel(  "wmsClient - Version 1.3" ) );
        panel.add( panel2, BorderLayout.NORTH );

        panel.add( button, BorderLayout.SOUTH );

        this.getContentPane().add(panel);
        pack();
    }


    /**Overridden so we can exit when window is closed*/
    protected void processWindowEvent(WindowEvent e) {
        sPane.getViewport().setViewPosition( new Point( 0, 0 ));

        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            cancel();
        }
        super.processWindowEvent(e);
    }
    /**Close the dialog*/
    void cancel() {
        dispose();
    }
    /**Close the dialog on a button event*/
    public void actionPerformed(ActionEvent e) {
        cancel();
    }
}
