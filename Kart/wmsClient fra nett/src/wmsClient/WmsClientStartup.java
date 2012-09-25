package wmsClient;

import javax.swing.UIManager;
import java.awt.*;

import wmsClient.gui.WmsClientFrame;

public class WmsClientStartup
{

    /**Construct the application*/
    public WmsClientStartup() {
        try {
            WmsClientFrame frame = new WmsClientFrame();
            frame.show();
        }
        catch(Exception e) {
            System.out.println("Uncatched error: ");	    
            e.printStackTrace();
        }
        
    }
    /**Main method*/
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new WmsClientStartup();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
