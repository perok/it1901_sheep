  //Variables
  var map;
  var trondheimLoc = new google.maps.LatLng(63.430515, 10.395053);
  var wmsType;
  var markers = [];
  var lines = [];


  //Function that is started when onload is fired
  function initialize() {


   var mapOptions = {
    zoom: 10,
    center: trondheimLoc,
    mapTypeId: google.maps.MapTypeId.ROADMAP,
    streetViewControl: false,
    mapTypeControlOptions: {
      mapTypeIds: ['statkart', google.maps.MapTypeId.ROADMAP],
      style: google.maps.MapTypeControlStyle.DROPDOWN_MENU
    } 
  };

  map = new google.maps.Map(document.getElementById('map_canvas'), mapOptions);
  wmsType = new google.maps.ImageMapType(StatKartLayer);
  
  


//  map.overlayMapTypes.insertAt(0, wmsType); 
  map.mapTypes.set('statkart', wmsType);
  //map.setMapTypeId('statkart'); 
  //setMarkers(map, beaches);

}
  

  /*
  * WMS
  */
  var StatKartLayer = {
    getTileUrl: function (coord, zoom) {
    	var proj = map.getProjection();
        var zfactor = Math.pow(2, zoom);
	    // get Long Lat coordinates
	    var top = proj.fromPointToLatLng(new google.maps.Point(coord.x * 256 / zfactor, coord.y * 256 / zfactor));
	    var bot = proj.fromPointToLatLng(new google.maps.Point((coord.x + 1) * 256 / zfactor, (coord.y + 1) * 256 / zfactor));
	
	    //corrections for the slight shift of the SLP (mapserver)
	    var deltaX = 0.0013;
	    var deltaY = 0.00058;
	
	    //create the Bounding box string
	    var bbox = (top.lng()) + "," + (bot.lat()) + "," + (bot.lng()) + "," + (top.lat());
	    
	    //base WMS URL
	    var url = "http://openwms.statkart.no/skwms1/wms.topo2?";
	    url += "&REQUEST=GetMap"; //WMS operation
	    url += "&SERVICE=WMS";    //WMS service
	    url += "&VERSION=1.1.1";  //WMS version  
	    url += "&LAYERS=" + "topo2_WMS"; //WMS layers
	    url += "&FORMAT=image/png" ; //WMS format
	    url += "&BGCOLOR=0xFFFFFF";  
	    url += "&TRANSPARENT=FALSE";
	    url += "&SRS=EPSG:4326";     //set WGS84 
	    url += "&BBOX=" + bbox;      // set bounding box
	    url += "&WIDTH=256";         //tile size in google
	    url += "&HEIGHT=256";
	    return url;
    },

  tileSize: new google.maps.Size(256, 256),
  isPng: true,
  name: "statkart",
  maxZoom: 12,
  minZoom: 0,
  alt: "Statens kartvertk"
}

/*
* MARKERS  
*/
    /**
     * Data for the markers consisting of a name, a LatLng and a zIndex for
     * the order in which these markers should display on top of each
     * other.
     */

var beaches = [
["TRONDHEIM BETCHES", 63.430515, 10.395053, 1]
];

//function that is called from the application for one sheep selected
function receiveJSONOne(data){
	setMarkers(map, data);	
	//Add lines
	//makeLines(map);
} 

//function that is called from the application for many sheep selected
function receiveJSONMany(data){
	setMarkers(map, data);	
} 

function setMarkers(map, locations) {
	deleteOverlays();
  // Add markers to the map

  // Marker sizes are expressed as a Size of X,Y
  // where the origin of the image (0,0) is located
  // in the top left of the image.

  // Origins, anchor positions and coordinates of the marker
  // increase in the X direction to the right and in
  // the Y direction down.
  var image = new google.maps.MarkerImage('images/Sheep_WO_backround.png',
    // This marker is 23 pixels wide by 2 pixels tall.
    new google.maps.Size(23, 22),
    // The origin for this image is 0,0.
    new google.maps.Point(0,0),
    // The anchor for this image is the base of the flagpole at 0,32.
    new google.maps.Point(0, 32));
    // Shapes define the clickable region of the icon.
    // The type defines an HTML &lt;area&gt; element 'poly' which
    // traces out a polygon as a series of X,Y points. The final
    // coordinate closes the poly by connecting to the first
    // coordinate.
    var shape = {
      coord: [1, 1, 1, 23, 23, 22, 23 , 1],
      type: 'poly'
   };

    for (var i = 0; i < locations.length; i++) {
    	alert(locations[i].lat + "  " + locations[i].lon);
    	
      var myLatLng = new google.maps.LatLng(locations[i].lat, locations[i].lon);
      var marker = new google.maps.Marker({
        position: myLatLng,
        map: map,
        icon: image,
        shape: shape,
        animation: google.maps.Animation.DROP,
        title: locations[i].name,
        zIndex: 0
      });
      
      markers.push(marker);
    }
    
    setAllMap(map);
}
/*
function makeLines(map){
	//Remove old lines
	for ( var i = 0; i < lines.length; i++) {
		lines[i].setMap(null);
	}
	//make new lines
	//for ( var i = 0; i < (markers.length - 1); i++) {
		var line = new google.maps.Polyline({
			//var datline = [markers[i].getPosition(), markers[i+1].getPosition()];
			path: lineCoordinates,
			icons: [{
				icon: lineSymbol,
				offset: '100%'
			}],
			map: map
			});
		}
	
		lines.push(line);
	//}
}*/

var lineCoordinates = [
                       new google.maps.LatLng(22.291, 153.027),
                       new google.maps.LatLng(18.291, 153.027)
                     ];

var lineSymbol = {
	path: google.maps.SymbolPath.FORWARD_CLOSED_ARROW
};



// Removes the overlays from the map, but keeps them in the array.
function clearOverlays() {
  setAllMap(null);
}

// Sets the map on all markers in the array.
function setAllMap(map) {
  for (var i = 0; i < markers.length; i++) {
    markers[i].setMap(map);
  }
}

// Deletes all markers in the array by removing references to them.
function deleteOverlays() {
  clearOverlays();
  markers = [];
}
