package wmsClient;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.io.*;
import javax.swing.UIManager;

import com.l2fprod.gui.plaf.skin.SkinLookAndFeel;
import com.l2fprod.gui.plaf.skin.Skin;
import com.l2fprod.gui.plaf.skin.CompoundSkin;


public class WMSClient {


    public static void main(String[] args) {

        try {
            try {
                Skin skin = null;  
                try {
                    // URL packtheme = WMSClient.class.getResource( "themepack_modern.zip" );		
                    // InputStream packtheme = WMSClient.class.getResourceAsStream( "themepack_modern.zip" );		
                    // skin = SkinLookAndFeel.loadThemePack(packtheme);
                    URL packtheme = WMSClient.class.getResource( "skinlf-themepack.xml" );		
                    skin = SkinLookAndFeel.loadThemePackDefinition( packtheme );
                    
                    System.out.println( "Theme: "+ packtheme);
                } catch(Exception e) {
                    System.out.println("No theme provided, defaulting to application Look And Feel");
                }
                
                if (skin != null) {
                    SkinLookAndFeel.setSkin(skin);
                    UIManager.setLookAndFeel("com.l2fprod.gui.plaf.skin.SkinLookAndFeel");
                    
                    UIManager.addPropertyChangeListener(new PropertyChangeListener() {
                            public void propertyChange(PropertyChangeEvent event) {
                                Object oldLF = event.getOldValue();
                                Object newLF = event.getNewValue();
                                if ((newLF instanceof SkinLookAndFeel) == false) {
                                    try {
                                        UIManager.setLookAndFeel("com.l2fprod.gui.plaf.skin.SkinLookAndFeel");
                                    } catch (Exception e) { e.printStackTrace(); }
                                }
                            }
                        });
                    
                };
            } catch(Exception e) {
                System.out.println("No themes available.");
            }
            
            WmsClientStartup startup = new WmsClientStartup();
            
        } catch ( Exception e ) {
            System.out.println("An error occurred:");
            e.printStackTrace();
        }
    }
    
}
