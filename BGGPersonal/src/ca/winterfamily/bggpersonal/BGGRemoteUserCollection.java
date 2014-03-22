package ca.winterfamily.bggpersonal;

public class BGGRemoteUserCollection extends BGGRemote<String>
{
	private static final String BGGURL = "http://boardgamegeek.com/xmlapi2/collection?";
	private static final String BGGUSER = "username";
	
	protected String getUrlString(String... username)
	{
		String url = BGGURL + BGGUSER + "=" + username[0];
		return url;
	}
	
}
