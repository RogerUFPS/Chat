package model;

import java.io.Serializable;

public class User implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private boolean online;
	
	public User(String name) {
		this.username = name;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	
	@Override
	public boolean equals(Object other){
		if(other == null)
			return false;
		if(!(other instanceof User))
			return false;
		User o = (User)other;
		return this.username.trim().equalsIgnoreCase(o.getUsername().trim());
	}
}