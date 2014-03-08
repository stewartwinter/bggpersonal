package ca.winterfamily.bggpersonal;

import java.util.ArrayList;

import android.content.Context;

public class BGGUser {
	private static BGGUser sBGGUser;
	private Context mAppContext;
	private String mUserName;
	
	private ArrayList<Game> mGameList = new ArrayList<Game>();
	
	private BGGUser(Context appContext) {
		mAppContext = appContext;
	}
	
	public void setUserName(String username) {
		mUserName = username;
		
		// todo: fetch info from BGG for user's collection
		
		mGameList.add(new Game("game1"));
		mGameList.add(new Game("game2"));
		mGameList.add(new Game("game3"));
		
	}
	
	public String getUserName() {
		return mUserName;
	}
	
	public static BGGUser getBGGUser(Context c) {
		if (sBGGUser == null) {
			sBGGUser = new BGGUser(c);
		}
		
		// todo - get user from settings
		sBGGUser.setUserName("aUser");
		
		return sBGGUser;
	}
	
	public ArrayList<Game> getCollection() {
		return mGameList;
	}
	
	

}
