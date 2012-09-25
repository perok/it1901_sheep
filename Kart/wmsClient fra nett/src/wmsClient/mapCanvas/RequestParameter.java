package wmsClient.mapCanvas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

import wmsClient.layerTree.*;
import wmsClient.layerList.*;


public interface RequestParameter {

    public int getPixelWidth();
    public int getPixelHeight();

    /** X-Koordinate der Linken, oberen Ecke */
    public float getBoundingX1();

    /** Y-Koordinate der Linken, oberen Ecke */
    public float getBoundingY1();

    public float getBoundingX2();
    public float getBoundingY2();

    public String getImageFormat();    

    public String getSRS();  
	  
    public void submitDone();    
    
}
