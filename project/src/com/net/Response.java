package com.net;

import java.io.*;
import java.util.ArrayList;

import core.classes.User;
	
/**Wrapper class containing information from server to client
 * 
 * @author Svenn
 *
 */
public class Response implements Serializable { 
	private static final long serialVersionUID = 1L;
	static final int LIST = 1, BOOLEAN = 2, USER = 3;
	private int type;
	private boolean success;
	private User user;
	private String initialRequest;
	private ArrayList<Object> content;
	private int initialRequestId;
	
	/**Constructor
	 * 
	 * @param type
	 * @param success
	 * @param req
	 */
	public Response(int type, boolean success, String req) {
		this.type = type;
		this.success = success;
		this.initialRequest = req;
	}
	/**Constructor
	 * 
	 * @param type
	 * @param success
	 * @param req
	 * @param id
	 */
	public Response(int type, boolean success, String req,int id) {
		this.type = type;
		this.success = success;
		this.initialRequest = req;
		this.initialRequestId = id;
	}
	
	/**Constructor
	 * 
	 * @param type
	 * @param content
	 * @param req
	 */
	public Response(int type, ArrayList<?> content,String req) {
		this.content = new ArrayList<>();
		this.type = type;
		for (int i = 0; i < content.size(); i++) {
			this.content.add(content.get(i));
		}
		this.initialRequest = req;
	}
	
	/**Constructor
	 * 
	 * @param type
	 * @param content
	 * @param req
	 * @param id
	 */
	public Response(int type, ArrayList<?> content,String req,int id) {
		this.content = new ArrayList<>();
		this.type = type;
		this.initialRequestId = id;
		for (int i = 0; i < content.size(); i++) {
			this.content.add(content.get(i));
		}
		this.initialRequest = req;
	}
	
	/**Constructor
	 * 
	 * @param type
	 * @param success
	 * @param content
	 */
	public Response(int type, boolean success, ArrayList<?> content) {
		this.content = new ArrayList<>();
		this.type = type;
		this.success = success;
		for (int i = 0; i < content.size(); i++) {
			this.content.add(content.get(i));
		}
	}
	
	/**Constructor
	 * 
	 * @param type
	 * @param user
	 */
	public Response(int type, User user) {
		this.type = type;
		this.user = new User(user.getId(),user.getUsername(),user.getName(),user.getPassword(),
				user.getMobileNumber(),user.getEmail(),user.getFarmlist());
		}
	
	/**Returns type
	 * 
	 * @return
	 */
	public int getType() {
		return type;
	}
	
	/**Returns success
	 * 
	 * @return
	 */
	public boolean getSuccess() {
		return success;
	}
	
	/**Sets success message
	 * 
	 * @param success
	 */
	public void setMessage(boolean success) {
		this.success = success;
	}

	/**Returns array list with content
	 * 
	 * @return
	 */
	public ArrayList<Object> getContent() {
		return content;
	}

	/**Takes array list of content and adds it to this content.
	 * 
	 * @param content
	 */
	public void setContent(ArrayList<?> content) {
		for (int i = 0; i < content.size(); i++) {
			this.content.add(content.get(i));
		}
	}
	
	/**Returns user
	 * 
	 * @return
	 */
	public User getUser() {
		return user;
	}

	/**Sets 
	 * user
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**Return initial request
	 * 
	 * @return
	 */
	public String getInitialRequest() {
		return initialRequest;
	}
	
	/**Returns initial request id
	 * 
	 * @return
	 */
	public int getInitialRequestId() {
		return initialRequestId;
	}

	/**ToString to format content and success
	 * 
	 */
	public String toString() {
		if(this.type == Response.LIST) {
			String out = new String();
			for (int i = 0; i < content.size(); i++) {
				out += content.get(i).toString();
			}
			return out;
		}
		else
			if(success)
				return "Success";
			else
				return "Fail";
	}
}