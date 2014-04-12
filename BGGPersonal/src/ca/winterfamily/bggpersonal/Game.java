package ca.winterfamily.bggpersonal;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class Game {
	private UUID mId;
	private String mName;
	public String mDescription;
	public boolean mOwned = false;
	public int mPlays = 0;
	public String mBggId = "";
	public String mWishlist = "";
	public String mWishlistPriority = "";
	public String mWanted = "";
	public String mYearPublished = "";
	public String mThumbnailUrl = "";
	public String mRank = "";
	public String mAverageRating = "";
	public String mNumberOfRatings = "";
	public String mMinPlayers = "";
	public String mMaxPlayers = "";
	public String mPlayingTime = "";
		
	public ArrayList<GameComment> mCommentList = new ArrayList<GameComment>();
	
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
	
	private String getTextFromParser(XmlPullParser parser) throws XmlPullParserException, IOException {
		String tag = parser.getName();
		String text = "";
		
	    int eventType = parser.next();
	    while (eventType != XmlPullParser.END_DOCUMENT) {
	    	switch (eventType) {
	    		case XmlPullParser.END_TAG:
		    		if (parser.getName().compareTo(tag) == 0) {
		    			return text;
		    		}
		    		break;
		    		
	    		case XmlPullParser.TEXT:
			    	return parser.getText();

	    	}		
	    	eventType = parser.next();
	    }
	    return text;
 	}
	
	public synchronized void populateFromXML(String xml) {
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(new StringReader(xml));
		
			boolean inItem = false;
			boolean inStatistics = false;
			boolean inRatings = false;
			boolean inRanks = false;
			boolean inDescription = false;
	    	
		    int eventType = parser.getEventType();
		    while (eventType != XmlPullParser.END_DOCUMENT) {
		    	switch (eventType) {
		    		case XmlPullParser.START_TAG:
			    		if (parser.getName().compareTo("item") == 0) {
			    			
			    			String objecttype = parser.getAttributeValue(null,  "type");
			    			String objectid = parser.getAttributeValue(null, "id");
			    			
			    			if (!((objecttype.compareTo("boardgame") == 0) || (objecttype.compareTo("boardgameexpansion") == 0)) || (objectid.compareTo(mBggId) != 0)) {
				    			return;
			    			}
			    			inItem = true;
			    			
			    		} else if (inItem && (parser.getName().compareTo("name") == 0)) {
			    			if (parser.getAttributeValue(null, "type").compareTo("primary") == 0) {
			    				mName = parser.getAttributeValue(null,  "value");
			    			}
			    		} else if (inItem && (parser.getName().compareTo("yearpublished") == 0)) {
			    			mYearPublished = parser.getAttributeValue(null,  "value");
			    		} else if (inItem && (parser.getName().compareTo("minplayers") == 0)) {
			    			mMinPlayers = parser.getAttributeValue(null,  "value");
			    		} else if (inItem && (parser.getName().compareTo("maxplayers") == 0)) {
			    			mMaxPlayers = parser.getAttributeValue(null,  "value");
			    		} else if (inItem && (parser.getName().compareTo("playingtime") == 0)) {
			    			mPlayingTime = parser.getAttributeValue(null,  "value");
			    		} else if (inItem && (parser.getName().compareTo("image") == 0)) {
			    			mThumbnailUrl = getTextFromParser(parser);
			    		} else if (inItem && (parser.getName().compareTo("description") == 0)) {
			    			inDescription = true;
			    		} else if (inItem && (parser.getName().compareTo("statistics") == 0)) {
			    			inStatistics = true;
			    		} else if (inStatistics && (parser.getName().compareTo("ratings") == 0)) {
			    			inRatings = true;
			    		} else if (inRatings && (parser.getName().compareTo("ranks") == 0)) {
			    			inRanks = true;
			    		} else if (inRanks && (parser.getName().compareTo("rank") == 0)) {
			    			String rankname = parser.getAttributeValue(null, "name");
			    			if (rankname.compareTo("boardgame") == 0) {
			    				mRank = parser.getAttributeValue(null, "value");
			    			}
			    		} else if (inItem && (parser.getName().compareTo("comment") == 0)) {
			    			GameComment comment = new GameComment();
			    			comment.mRating = parser.getAttributeValue(null, "rating");
			    			comment.mRatingUser = parser.getAttributeValue(null, "username");
			    			comment.mComment = parser.getAttributeValue(null, "value");
			    			mCommentList.add(comment);
			    			
			    		} else if (inRatings && (parser.getName().compareTo("usersrated") == 0)) {
			    			mNumberOfRatings = parser.getAttributeValue(null, "value");
			    		} else if (inRatings && (parser.getName().compareTo("average") == 0)) {
			    			mAverageRating = parser.getAttributeValue(null, "value");
			    		}
		    			break;
		    			
		    		case XmlPullParser.END_TAG:
			    		if (inItem && (parser.getName().compareTo("item") == 0)) {
			    			inItem = false;
			    		} else if (inItem && (parser.getName().compareTo("description") == 0)) {
			    			inDescription = false;
			    		} else if (inStatistics && (parser.getName().compareTo("statistics") == 0)) {
			    			inStatistics = false;
			    		} else if (inRatings && (parser.getName().compareTo("ratings") == 0)) {
			    			inRatings = false;
			    		} else if (inRanks && (parser.getName().compareTo("ranks") == 0)) {
			    			inRanks = false;
			    		}
			    		break;
			    		
		    		case XmlPullParser.TEXT:
		    			if (inDescription) {
		    				mDescription = parser.getText();
		    			}
			    		break;
	
			    		
		    	}
	
	            eventType = parser.next();
		    }
		    Collections.sort(mCommentList);
	  
		} catch (XmlPullParserException e) {
			return;
		} catch (IOException e) {
			return;
		}
		
	}
}
