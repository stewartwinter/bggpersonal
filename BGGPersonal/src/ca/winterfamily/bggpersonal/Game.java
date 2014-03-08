package ca.winterfamily.bggpersonal;

import java.util.UUID;

public class Game {
	private UUID mId;
	private String mName;
	
	public Game(String name) {
		mId = UUID.randomUUID();
		mName = name;
	}
	
	public UUID getId() {
		return mId;
	}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		mName = name;
	}
	
}
