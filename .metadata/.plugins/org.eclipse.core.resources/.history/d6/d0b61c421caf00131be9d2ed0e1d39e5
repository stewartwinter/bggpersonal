package ca.winterfamily.bggpersonal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
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
	private String mUserName = "";
	
	private ArrayList<Game> mGameList = new ArrayList<Game>();
	
	private BGGUser(Context appContext) {
		mAppContext = appContext;
		mUserName = readFromStorage();
	}
	
	private String readFromStorage() {
		String name = "";
		try {
			InputStream in = mAppContext.openFileInput("settings");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			name = reader.readLine();
			reader.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {

		}
		Log.i("BGGPersonal", "Read from storage " + name);
		return name;
	}
	
	private void saveToStorage(String name) {
		Writer writer = null;
		try {
			OutputStream out = mAppContext.openFileOutput("settings", Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(name);
		} catch (FileNotFoundException e) {
			// couldn't write ... ok
		} catch (IOException e) {
			// couldn't write ... ok
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {

				}
			}
		}
	}
	
	public void setUserName(String username) {
		if ( mUserName.compareTo(username) != 0) {
			mUserName = username;
			saveToStorage(username);
			mGameList.clear();
			try {
				populate();
			} catch (Exception e) {
				//TODO: something
				Log.e("BGGPersonal", "In BGGUser.setUserName", e);
			}
		}
	}
	
	public void populate() throws InterruptedException, ExecutionException, XmlPullParserException, IOException {
	
		BGGRemoteUserCollection rem = new BGGRemoteUserCollection();
		RemoteTopGames topgamesRem = new RemoteTopGames();
		Integer[] topGamesParms = {0, 100};
		rem.execute(mUserName);
		topgamesRem.execute(topGamesParms);

		String xml = rem.get();
		
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(xml));
	    int eventType = parser.getEventType();
    	boolean inItem = false;
    	boolean inName = false;
    	boolean inNumplays = false;
    	boolean inStatus = false;
    	Game curGame = null;
    	boolean skip = false;
	    while (eventType != XmlPullParser.END_DOCUMENT) {
	    	switch (eventType) {
	    		case XmlPullParser.START_TAG:
		    		if (parser.getName().compareTo("item") == 0) {
		    			inItem = true;
		    			skip = false;
		    			curGame = null;
		    			
		    			String objecttype = parser.getAttributeValue(null,  "objecttype");
		    			String objectid = parser.getAttributeValue(null, "objectid");
		    			String subtype = parser.getAttributeValue(null, "subtype");
		    			
		    			if (subtype.compareTo("boardgame") != 0)  {
		    				skip = true;
		    			} else {
			    			curGame = new Game();
			    			curGame.mBggId = objectid;
		    			}
		    			
		    		} else if (!skip && inItem && (parser.getName().compareTo("name") == 0)) {
		    			inName = true;
		    		} else if (!skip && inItem && (parser.getName().compareTo("numplays") == 0)) {
		    			inNumplays = true;
		    		} else if (!skip && inItem && (parser.getName().compareTo("status") == 0)) {
		    			inStatus = true;
		    			String own = parser.getAttributeValue(null,  "own");
		    			curGame.mWishlist = parser.getAttributeValue(null, "wishlist");
		    			Log.i("BGGPersonal", "own in BGGUser " + own);
		    			if (own.compareToIgnoreCase("1") == 0) {
		    				curGame.mOwned = true;
		    			}
		    		}
	    			break;
	    			
	    		case XmlPullParser.END_TAG:
		    		if (inItem && (parser.getName().compareTo("item") == 0)) {
		    			if (!skip && (curGame.mOwned || (curGame.mWishlist.compareTo("5") != 0))) {
		    				mGameList.add( curGame );
		    			}
		    			inItem = false;
		    			inName = false;
		    		   	inNumplays = false;
		    		    curGame = null;
		    		} else if (!skip && inName && (parser.getName().compareTo("name") == 0)) {
		    			inName = false;
		    		} else if (!skip && inNumplays && (parser.getName().compareTo("numplays") == 0)) {
		    			inNumplays = false;
		    		} else if (!skip && inStatus && (parser.getName().compareTo("status") == 0)) {
		    			inStatus = false;
		    		}
		    		break;
		    		
	    		case XmlPullParser.TEXT:
		    		if (!skip && inName) {
		    			curGame.setName( parser.getText() );
		    		} else if (!skip && inNumplays) {
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
			try {
				sBGGUser.populate();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return sBGGUser;
	}
	
	public ArrayList<Game> getCollection() {
		return mGameList;
	}
	
	public Game findGame(String gameId) {
		for (Game g : mGameList) {
			if (g.mBggId.compareTo(gameId) == 0)
				return g;
		}
		return null;
	}
	
	

}
