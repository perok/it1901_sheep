  //Variables
  var map;
  var trondheimLoc = new google.maps.LatLng(63.430515, 10.395053);
  var wmsType;

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
  
  
  //XML
  downloadUrl("phpsqlajax_genxml.php", function(data) {
    var xml = data.responseXML;
    var markers = xml.documentElement.getElementsByTagName("marker");
    for (var i = 0; i < markers.length; i++) {
      var name = markers[i].getAttribute("name");
      var point = new google.maps.LatLng(
        parseFloat(markers[i].getAttribute("lat")),
        parseFloat(markers[i].getAttribute("lng")));
      var html = "<b>" + name + "</b> <br/>" + address;
      var icon = customIcons[type] || {};
      var marker = new google.maps.Marker({
        map: map,
        position: point,
        icon: icon.icon,
        shadow: icon.shadow
      });
      //bindInfoWindow(marker, map, infoWindow, html);
    }
  });

//  map.overlayMapTypes.insertAt(0, wmsType); 
  //map.mapTypes.set('statkart', wmsType);
  //map.setMapTypeId('statkart'); 
  //setMarkers(map, beaches);

}

//Slices the url from '?' to end or #
function parseURLParams(url) {
  var queryStart = url.indexOf("?") + 1;
  var queryEnd   = url.indexOf("#") + 1 || url.length + 1;
  var query      = url.slice(queryStart, queryEnd - 1);

  if (query === url || query === "") return;

  console.log(query);
  return query;
}

//starts the php script and parses the database
function downloadUrl(url, callback) {
  var request = window.ActiveXObject ?
  new ActiveXObject('Microsoft.XMLHTTP') :
  new XMLHttpRequest;

  request.onreadystatechange = function() {
    if (request.readyState == 4) {
      request.onreadystatechange = doNothing;
      callback(request, request.status);
    }
  };


  request.open('GET', url, true);
  //Send parametre her
  request.send(parseURLParams(url));
}

//Helper function
function doNothing() {}

  /*
  * WMS
  */
  var StatKartLayer = {
    getTileUrl: function (coord, zoom) {
       /*                 var proj = map.getProjection();
                          var zfactor = Math.pow(2, zoom);
    // get Long Lat coordinates
    var top = proj.fromPointToLatLng(new google.maps.Point(coord.x * 256 / zfactor, coord.y * 256 / zfactor));
    var bot = proj.fromPointToLatLng(new google.maps.Point((coord.x + 1) * 256 / zfactor, (coord.y + 1) * 256 / zfactor));

    //corrections for the slight shift of the SLP (mapserver)
    var deltaX = 0.0013;
    var deltaY = 0.00058;

    //create the Bounding box string
    var bbox = (top.lng()) + "," + (bot.lat()) + "," + (bot.lng()) + "," + (top.lat());

    */
    var lULP = new google.maps.Point(coord.x*256,(coord.y+1)*256);
    var lLRP = new google.maps.Point((coord.x+1)*256,coord.y*256);

    var projectionMap = new MercatorProjection();

    var lULg = projectionMap.fromDivPixelToSphericalMercator(lULP, zoom);
    var lLRg  = projectionMap.fromDivPixelToSphericalMercator(lLRP, zoom);

    var lUL_Latitude = lULg.y;
    var lUL_Longitude = lULg.x;
    var lLR_Latitude = lLRg.y;
    var lLR_Longitude = lLRg.x;
    //GJ: there is a bug when crossing the -180 longitude border (tile does not render) - this check seems to fix it
    if (lLR_Longitude < lUL_Longitude){
      lLR_Longitude = Math.abs(lLR_Longitude);
    }

    var urlResult = "&bbox=" + lUL_Longitude + "," + lUL_Latitude + "," + lLR_Longitude + "," + lLR_Latitude;
    
    //base WMS URL
    var url = "http://openwms.statkart.no/skwms1/wms.kartdata2?";
    url += "&REQUEST=GetMap"; //WMS operation
    url += "&SERVICE=WMS";    //WMS service
    url += "&VERSION=1.1.1";  //WMS version  
    url += "&LAYERS=" + "Kartdata2_WMS"; //WMS layers
    url += "&FORMAT=image/png" ; //WMS format
    url += "&BGCOLOR=0xFFFFFF";  
    url += "&TRANSPARENT=FALSE";
    url += "&SRS=EPSG:4326";     //set WGS84 
    url += urlResult; //"&BBOX=" + bbox;      // set bounding box
    url += "&WIDTH=256";         //tile size in google
    url += "&HEIGHT=256";
    console.log(url);
    return url;

    },

  tileSize: new google.maps.Size(256, 256),
  isPng: true,
  name: "statkart",
  maxZoom: 12,
  minZoom: 0,
  alt: "Statens kartvertk"
}
  //Define custom WMS tiled layer
   
;

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

function setMarkers(map, locations) {
  // Add markers to the map

  // Marker sizes are expressed as a Size of X,Y
  // where the origin of the image (0,0) is located
  // in the top left of the image.

  // Origins, anchor positions and coordinates of the marker
  // increase in the X direction to the right and in
  // the Y direction down.
  var image = new google.maps.MarkerImage('images/sheep_small.png',
    // This marker is 20 pixels wide by 32 pixels tall.
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
      coord: [1, 1, 1, 20, 18, 20, 18 , 1],
      type: 'poly'
    };

    for (var i = 0; i < locations.length; i++) {
      var beach = locations[i];
      var myLatLng = new google.maps.LatLng(beach[1], beach[2]);
      var marker = new google.maps.Marker({
        position: myLatLng,
        map: map,
        icon: image,
        shape: shape,
        title: beach[0],
        zIndex: beach[3]
      });
    }
  }
