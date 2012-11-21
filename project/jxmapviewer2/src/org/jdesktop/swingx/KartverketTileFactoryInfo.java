//Prosjekt 1: IT1901_sheep
//NTNU. Informatikk, 2. årstrinn.
//Gruppe 10
//
//Copyright (c) Gruppe 10
//
//This code is property of "gruppe 10" 


package org.jdesktop.swingx;

import org.jdesktop.swingx.mapviewer.TileFactoryInfo;

//Uses Kartverket's WMS service. "Topografisk norgeskart 2 WMS"
//http://openwms.statkart.no/skwms1/wms.topo2?request=GetCapabilities&service=wms&version=1.1.1

public class KartverketTileFactoryInfo extends TileFactoryInfo{
	private static final int max = 19;
	
	//Default constructor
	
	public KartverketTileFactoryInfo() {
		super(1,			//min zoom level
				max - 2, 	//max allowed level
				max, 		//max level
				256, 		//tile size
				true, true,		// x/y orientation is normal
				"http://openwms.statkart.no/skwms1/wms.topo2?request=GetCapabilities&service=wms&version=1.1.1", //base URL
				"x", "y", "z");
	}
	
	public String getTileUrl(int x, int y, int zoom) {
		zoom = max - zoom;
		String url = this.baseURL + "/" + zoom + "/" + x + "/" + y + ".png";
		return url;
	}
	
	
	

}
