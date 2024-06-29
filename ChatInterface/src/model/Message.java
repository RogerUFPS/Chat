package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

public class Message implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String message;
    private User sender;
    private int order;
    private Timestamp date;

    public void setMessage(String m){
        this.message = m;
    }

    public String getMessage(){
        return this.message;
    }

    public void setSender(User user){
        this.sender = user;
    }

    public User getSender(){
        return this.sender;
    }
    
    public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public int getOrder(){
        return this.order;
    }

    public void setOrder(int order){
        this.order = order;
    }

}
