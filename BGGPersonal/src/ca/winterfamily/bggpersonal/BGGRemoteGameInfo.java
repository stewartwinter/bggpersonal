package ca.winterfamily.bggpersonal;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

public class BGGRemoteGameInfo extends BGGRemote<BGGRemoteGameInfo.BGGRemoteGameInfoParm>
{
//	private static final String BGGURL = "http://boardgamegeek.com/xmlapi2/thing?stats=1&comments=1&";
	private static final String BGGURL = "http://boardgamegeek.com/xmlapi2/thing?stats=1&";
	private static final String BGGID = "id";
	
	private Game mGame;
	
	static class BGGRemoteGameInfoParm {
		final public Game mGame;
		
		BGGRemoteGameInfoParm(Game game) {
			mGame = game;
		}
		
	}
	
	protected String getUrlString(BGGRemoteGameInfoParm... parm)
	{
		String url = BGGURL + BGGID + "=" + parm[0].mGame.mBggId.toString();
		return url;
	}
	
//	protected void onPostExecute(String xml) {
//		mGame.populateFromXML(xml);
//	}
	
}
