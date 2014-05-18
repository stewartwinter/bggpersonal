package ca.winterfamily.bggpersonal;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

public class BGGRemoteSearch extends BGGRemote<BGGRemoteSearch.BGGRemoteSearchParm>
{
	private static final String BGGURL = "http://boardgamegeek.com/xmlapi2/search?type=boardgame,boardgameexpansion&query=";
	
	private BGGUser bggUser;
	
	static class BGGRemoteSearchParm {
		final public String mSearchString;
		final public BGGUser mUser;
		
		BGGRemoteSearchParm(BGGUser user, String searchstring) {
			mSearchString = searchstring;
			mUser = user;
		}
		
	}
	
	protected String getUrlString(BGGRemoteSearchParm... userparms)
	{
		// use hotgames as the default (if the search is too small)
		String url = "http://boardgamegeek.com/xmlapi2/hot?type=boardgame";
	
		if (userparms[0].mSearchString.length() >= 3) {
			bggUser = userparms[0].mUser;
			try {
				url = BGGURL + URLEncoder.encode(userparms[0].mSearchString, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.d("BGGPersonal", url);
		return url;
	}
	
	protected void onPostExecute(String xml) {
	}
	
}