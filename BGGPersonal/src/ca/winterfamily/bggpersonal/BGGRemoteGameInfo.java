package ca.winterfamily.bggpersonal;

public class BGGRemoteGameInfo extends BGGRemote<BGGRemoteGameInfo.BGGRemoteGameInfoParm>
{
	private static final String BGGURLComments = "http://boardgamegeek.com/xmlapi2/thing?stats=1&comments=1&";
	private static final String BGGURLStats = "http://boardgamegeek.com/xmlapi2/thing?stats=1&";
	private static final String BGGID = "id";
	
	private BGGRemoteGameInfoParm mParm;
	
	static class BGGRemoteGameInfoParm {
		final public Game mGame;
		private Boolean mFetchComments = false;
		
		BGGRemoteGameInfoParm(Game game, Boolean fetchComments) {
			mGame = game;
			mFetchComments = fetchComments;
		}
		
	}
	
	protected String getUrlString(BGGRemoteGameInfoParm... parm)
	{
		mParm = parm[0];
		
		String url;
		if (mParm.mFetchComments) {
			url = BGGURLComments + BGGID + "=" + mParm.mGame.mBggId.toString();
		} else {
			url = BGGURLStats + BGGID + "=" + mParm.mGame.mBggId.toString();
		}
		return url;
	}
	
	protected void onPostExecute(String xml) {
		if (mParm.mFetchComments) {
			mParm.mGame.populateFromXML(xml);
		}
	}
	
}
