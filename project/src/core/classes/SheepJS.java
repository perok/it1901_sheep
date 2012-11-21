package core.classes;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class SheepJS implements JSONAware{
	private int id;
	private String name;
	private boolean isAlive;
	private boolean isAlert;
	private double lat;
	private double lon;
	private String date;
	
	
	public SheepJS(int id, String name, boolean isAlive, boolean isAlert, double lat, double lon, String date){
	        this.id = id;
	        this.name = name;
	        this.isAlive = isAlive;
	        this.isAlert = isAlert;
	        this.lat = lat;
	        this.lon = lon;
	        this.date = date;
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
	        
	        sb.append(JSONObject.escape("isAlive"));
	        sb.append(":");
	        sb.append(isAlive);
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
	        sb.append(",");
	        
	        sb.append(JSONObject.escape("date"));
	        sb.append(":");
	        sb.append("\"" + JSONObject.escape(date) + "\"");
	        
	        sb.append("}");
	        
	        return sb.toString();
	}
}
