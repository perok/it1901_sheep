package core.classes;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class SheepJS implements JSONAware{
	private int id;
	private String name;
	private boolean isAlert;
	private double lat;
	private double lon;
	
	
	public SheepJS(int id, String name, boolean isAlert, double lat, double lon){
	        this.id = id;
	        this.name = name;
	        this.isAlert = isAlert;
	        this.lat = lat;
	        this.lon = lon;
	}
	
	public String toJSONString(){
	        StringBuffer sb = new StringBuffer();
	        
	        sb.append("{");
	        
	        sb.append(JSONObject.escape("name"));
	        sb.append(":");
	        sb.append("\"" + JSONObject.escape(name) + "\"");
	        sb.append(",");
	        
	        sb.append(JSONObject.escape("ID"));
	        sb.append(":");
	        sb.append(id);
	        sb.append(",");
	        
	        sb.append(JSONObject.escape("isAlert"));
	        sb.append(":");
	        sb.append(isAlert);
	        sb.append(",");
	        
	        sb.append(JSONObject.escape("lat"));
	        sb.append(":");
	        sb.append(lat);
	        sb.append(",");
	        
	        sb.append(JSONObject.escape("lon"));
	        sb.append(":");
	        sb.append(lon);
	        
	        sb.append("}");
	        
	        return sb.toString();
	}
}
