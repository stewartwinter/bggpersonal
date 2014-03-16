package ca.winterfamily.bggpersonal;

import java.io.IOException;
import java.io.StringReader;
import java.util.UUID;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class Game {
	private UUID mId;
	private String mName;
	public boolean mOwned = false;
	public int mPlays = 0;
	public String mBggId = "";
	public String mWishlist = "";
	public String mYearPublished = "";
	
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
	
	public void populateFromXML(String xml) {
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(new StringReader(xml));
		
			boolean inItem = false;
	    	
		    int eventType = parser.getEventType();
		    while (eventType != XmlPullParser.END_DOCUMENT) {
		    	switch (eventType) {
		    		case XmlPullParser.START_TAG:
			    		if (parser.getName().compareTo("item") == 0) {
			    			
			    			String objecttype = parser.getAttributeValue(null,  "type");
			    			String objectid = parser.getAttributeValue(null, "id");
			    			
			    			if ((objecttype.compareTo("boardgame") != 0) || (objectid.compareTo(mBggId) != 0)) {
				    			return;
			    			}
			    			inItem = true;
			    			
			    		} else if (inItem && (parser.getName().compareTo("yearpublished") == 0)) {
			    			mYearPublished = parser.getAttributeValue(null,  "value");
			    		}
		    			break;
		    			
		    		case XmlPullParser.END_TAG:
			    		if (inItem && (parser.getName().compareTo("item") == 0)) {
			    			inItem = false;
			    		}
			    		break;
			    		
		    		case XmlPullParser.TEXT:
	//		    		if (inName) {
	//		    		}
			    		break;
	
			    		
		    	}
	
	            eventType = parser.next();
		    }
	  
		} catch (XmlPullParserException e) {
			return;
		} catch (IOException e) {
			return;
		}
		
	}
}
