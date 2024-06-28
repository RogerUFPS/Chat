package model;

import java.util.LinkedList;

public class GroupChat extends Chat  {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GroupChat(){
        super(new LinkedList<Message>());
    }
}
