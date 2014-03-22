package ca.winterfamily.bggpersonal;

public class BGGRemoteHotGames extends BGGRemote<Void>
{
	private static final String BGGURL = "http://boardgamegeek.com/xmlapi2/hot?type=boardgame";
	
	protected String getUrlString(Void... empty)
	{
		return BGGURL;
	}
	
}

