package ca.winterfamily.bggpersonal;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

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
		mGameList = new ArrayList<Game>();		
	}
	
	public void populate() throws InterruptedException, ExecutionException, XmlPullParserException, IOException {
	
		BGGRemote rem = new BGGRemote();
		rem.execute("scott");
		String xml = rem.get();
		Log.i("BGGPersonal", "collection xml in BGGUser " + xml);
		
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(xml));
	    int eventType = parser.getEventType();
    	boolean inItem = false;
    	boolean inName = false;
    	boolean inNumplays = false;
    	boolean inStatus = false;
    	Game curGame = null;
	    while (eventType != XmlPullParser.END_DOCUMENT) {
	    	switch (eventType) {
	    		case XmlPullParser.START_TAG:
		    		if (parser.getName().compareTo("item") == 0) {
		    			inItem = true;
		    			curGame = new Game();
		    		} else if (inItem && (parser.getName().compareTo("name") == 0)) {
		    			inName = true;
		    		} else if (inItem && (parser.getName().compareTo("numplays") == 0)) {
		    			inNumplays = true;
		    		} else if (inItem && (parser.getName().compareTo("status") == 0)) {
		    			inStatus = true;
		    			String own = parser.getAttributeValue(null,  "own");
		    			Log.i("BGGPersonal", "own in BGGUser " + own);
		    			if (own.compareToIgnoreCase("1") == 0) {
		    				curGame.mOwned = true;
		    			}
		    		}
	    			break;
	    			
	    		case XmlPullParser.END_TAG:
		    		if (inItem && (parser.getName().compareTo("item") == 0)) {
		    			inItem = false;
		    			inName = false;
		    		   	inNumplays = false;
		    		    curGame = null;
		    		} else if (inName && (parser.getName().compareTo("name") == 0)) {
		    			inName = false;
		    		} else if (inNumplays && (parser.getName().compareTo("numplays") == 0)) {
		    			inNumplays = false;
		    		} else if (inStatus && (parser.getName().compareTo("status") == 0)) {
		    			inStatus = false;
		    		}
		    		break;
		    		
	    		case XmlPullParser.TEXT:
		    		if (inName) {
		    			curGame.setName( parser.getText() );
		    			mGameList.add( curGame );
		    		} else if (inNumplays) {
		    			curGame.mPlays = Integer.parseInt(parser.getText());
		    		}
		    		break;

		    		
	    	}

            eventType = parser.next();
	    }
	  
	}
	
	public String getUserName() {
		return mUserName;
	}
	
	public static BGGUser getBGGUser(Context c) {
		if (sBGGUser == null) {
			sBGGUser = new BGGUser(c);
		}
		
		// todo - get user from settings
		try {
			sBGGUser.setUserName("scott");
			sBGGUser.populate();
		} catch (Exception e) {
			// todo: need to do something better than eating the exception
			Log.e("BGGPersonal", "fetching user collection", e);
		}
		
		return sBGGUser;
	}
	
	public ArrayList<Game> getCollection() {
		return mGameList;
	}
	
	

}
