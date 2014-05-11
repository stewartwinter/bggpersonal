package ca.winterfamily.bggpersonal;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

public class RemoteTopGames extends BGGRemote<RemoteTopGames.RemoteTopGamesParm>
{
	private static final String TOPGAMES = "http://bgg.winterfamily.ca/bggtop.php?";
	private static final String AMOUNT = "amount";
	private static final String AFTER = "after";
	
	private BGGUser mBggUser;
	private Integer mAfter;
	
	static class RemoteTopGamesParm {
		final public Integer mAfter;
		final public Integer mAmount;
		final public BGGUser mUser;
		
		RemoteTopGamesParm(BGGUser user, Integer after, Integer amount) {
			mAfter = after;
			mAmount = amount;
			mUser = user;
		}
		
	}
	

	protected String getUrlString(RemoteTopGamesParm... parms)
	{
		mBggUser = parms[0].mUser;
		mAfter = parms[0].mAfter;
		
		String url = TOPGAMES;
		if (parms[0].mAfter != null) {
			url += AFTER + "=" + parms[0].mAfter.toString();
		}
		if (parms[0].mAmount != null) {
			url += "&" + AMOUNT + "=" + parms[0].mAmount.toString();
		}
		return url;
	}
	
	protected void onPostExecute(String xml) {
		try {
			mBggUser.addTopGames(xml);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}


