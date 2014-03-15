package ca.winterfamily.bggpersonal;

import java.util.UUID;

public class Game {
	private UUID mId;
	private String mName;
	public boolean mOwned = false;
	public int mPlays = 0;
	public String mBggId = "";
	public String mWishlist = "";
	
	public Game(String name) {
		mId = UUID.randomUUID();
		mName = name;
	}
	
	public Game() {
		mId = UUID.randomUUID();
		mName = "unknown";
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
	
	@Override
	public String toString() {
		String out = getName();
		if (mOwned) {
			out += " (own)";
		}
		out += " (plays: " + mPlays + ")";
		return out;
	}
}
