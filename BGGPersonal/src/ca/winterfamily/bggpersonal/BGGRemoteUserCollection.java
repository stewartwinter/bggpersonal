package ca.winterfamily.bggpersonal;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

public class BGGRemoteUserCollection extends BGGRemote<BGGRemoteUserCollection.BGGRemoteUserCollectionParm>
{
	private static final String BGGURL = "http://boardgamegeek.com/xmlapi2/collection?";
	private static final String BGGUSER = "username";
	
	private BGGUser bggUser;
	
	static class BGGRemoteUserCollectionParm {
		final public String mBggUsername;
		final public BGGUser mUser;
		
		BGGRemoteUserCollectionParm(BGGUser user, String username) {
			mBggUsername = username;
			mUser = user;
		}
		
	}
	
	protected String getUrlString(BGGRemoteUserCollectionParm... userparms)
	{
		bggUser = userparms[0].mUser;
		String url = BGGURL + BGGUSER + "=" + userparms[0].mBggUsername;
		return url;
	}
	
	protected void onPostExecute(String xml) {
		try {
			bggUser.addGamesCollectionFromXml(xml);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
