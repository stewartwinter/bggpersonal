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
import java.util.HashMap;
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
	private ArrayList<Game> mTopGames = new ArrayList<Game>();
	private ArrayList<Game> mHotGames = new ArrayList<Game>();
	private ArrayList<Game> mSearchResults = new ArrayList<Game>();
	
	private HashMap<Integer, LoadableGame> mGameDetailMap = new HashMap<Integer, LoadableGame>();
	
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
				populateUserCollection();
			} catch (Exception e) {
				//TODO: something
				Log.e("BGGPersonal", "In BGGUser.setUserName", e);
			}
		}
	}
	
	public void addGamesCollectionFromXml(String xml) throws XmlPullParserException, IOException {
		mGameList.clear();
		
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
	
	public void addTopGames(String xml) throws XmlPullParserException, IOException {
		mTopGames.clear();
		
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(xml));
	    int eventType = parser.getEventType();
    	
	    while (eventType != XmlPullParser.END_DOCUMENT) {
	    	switch (eventType) {
	    		case XmlPullParser.START_TAG:
		    		if (parser.getName().compareTo("game") == 0) {
		    			String bggid = parser.getAttributeValue(null,  "bggid");
		    			String name = parser.getAttributeValue(null, "name");
		    			String rank = parser.getAttributeValue(null, "rank");
		    			
		    			Game game = new Game();
		    			game.setName(name);
		    			game.mRank = rank;
		    			game.mBggId = bggid;
		    			mTopGames.add(game);
		    			
		    			
	    		}
	    		break;
	    			
	    	}

            eventType = parser.next();
	    }	
	}
	
	public void addHotGames(String xml) throws XmlPullParserException, IOException {
		mHotGames.clear();
		
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(xml));
		
		String bggid = "";
		String name = "";
		String year = "";
		String thumbnail = "";
		
	    int eventType = parser.getEventType();
	    while (eventType != XmlPullParser.END_DOCUMENT) {
	    	switch (eventType) {
	    		case XmlPullParser.START_TAG:
		    		if (parser.getName().compareTo("item") == 0) {
		    			bggid = parser.getAttributeValue(null,  "id");
		    		} else if (parser.getName().compareTo("thumbnail") == 0) {
		    			thumbnail = parser.getAttributeValue(null, "value");
		    		} else if (parser.getName().compareTo("name") == 0) {
		    			name = parser.getAttributeValue(null, "value");
		    		} else if (parser.getName().compareTo("yearpublished") == 0) {
		    			year = parser.getAttributeValue(null, "value");
		    		}
		    		break;
		    	
	    		case XmlPullParser.END_TAG:
	    			if (parser.getName().compareTo("item") == 0) {
	    				Game game = new Game();
		    			game.setName(name);
		    			game.mBggId = bggid;
		    			game.mThumbnailUrl = thumbnail;
		    			game.mYearPublished = year;
		    			mHotGames.add(game);
	    			}
	    		break;
	    			
	    	}

            eventType = parser.next();
	    }	
	}
	
	public void addSearchResultsFromXML(String xml)  throws XmlPullParserException, IOException {
		mSearchResults.clear();
		
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(xml));
		
		String bggid = "";
		String name = "";
		String year = "";
		
	    int eventType = parser.getEventType();
	    while (eventType != XmlPullParser.END_DOCUMENT) {
	    	switch (eventType) {
	    		case XmlPullParser.START_TAG:
		    		if (parser.getName().compareTo("item") == 0) {
		    			bggid = parser.getAttributeValue(null,  "id");
		    		} else if (parser.getName().compareTo("name") == 0) {
		    			name = parser.getAttributeValue(null, "value");
		    		} else if (parser.getName().compareTo("yearpublished") == 0) {
		    			year = parser.getAttributeValue(null, "value");
		    		}
		    		break;
		    	
	    		case XmlPullParser.END_TAG:
	    			if (parser.getName().compareTo("item") == 0) {
	    				Game game = new Game();
		    			game.setName(name);
		    			game.mBggId = bggid;
		    			game.mYearPublished = year;
		    			mSearchResults.add(game);
	    			}
	    		break;
	    			
	    	}

            eventType = parser.next();
	    }	

	}
	
	public void populateUserCollection()  throws InterruptedException, ExecutionException, XmlPullParserException, IOException {
		BGGRemoteUserCollection collectionRem = new BGGRemoteUserCollection();
		collectionRem.execute(new BGGRemoteUserCollection.BGGRemoteUserCollectionParm(this, mUserName));
	}
	
	public void populatTopGames()  throws InterruptedException, ExecutionException, XmlPullParserException, IOException {
		RemoteTopGames topgamesRem = new RemoteTopGames();
		topgamesRem.execute(new RemoteTopGames.RemoteTopGamesParm(this,  0, 100));

	}

	public void populatSearchResults(String input)  throws InterruptedException, ExecutionException, XmlPullParserException, IOException {
		BGGRemoteSearch searchRem = new BGGRemoteSearch();
		searchRem.execute(new BGGRemoteSearch.BGGRemoteSearchParm(this,  input));
		String xml = searchRem.get();
		addSearchResultsFromXML(xml);
	}
	
	
	public void populate() throws InterruptedException, ExecutionException, XmlPullParserException, IOException {
	
		BGGRemoteHotGames hotgamesRem = new BGGRemoteHotGames();
		hotgamesRem.execute();
		String xml = hotgamesRem.get();
		addHotGames(xml);
		

		populatTopGames();	
		populateUserCollection();
		
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
	
	public ArrayList<Game> getTopGames() {
		return mTopGames;
	}
	
	public ArrayList<Game> getHotGames() {
		return mHotGames;
	}
	
	public ArrayList<Game> getSearchResults() {
		return mSearchResults;
	}
	
	public Game getDetailedGame(Integer bggid) {
		if (!mGameDetailMap.containsKey(bggid)) {
			LoadableGame game = new LoadableGame(bggid);
			mGameDetailMap.put(bggid, game);
		}
		return mGameDetailMap.get(bggid).getGame();
	}
	public Game getDetailedGame(String sBggid) {
		int bggid = Integer.parseInt(sBggid);
		return getDetailedGame(bggid);
	}
	
	public Game findGame(String gameId) {
		for (Game g : mGameList) {
			if (g.mBggId.compareTo(gameId) == 0)
				return g;
		}
		return null;
	}
	
	

}
