package ca.winterfamily.bggpersonal;

public class BGGRemoteGameInfo extends BGGRemote<String>
{
//	private static final String BGGURL = "http://boardgamegeek.com/xmlapi2/thing?stats=1&comments=1&";
	private static final String BGGURL = "http://boardgamegeek.com/xmlapi2/thing?stats=1&";
	private static final String BGGID = "id";
	
	protected String getUrlString(String... gameid)
	{
		String url = BGGURL + BGGID + "=" + gameid[0];
		return url;
	}
	
}
