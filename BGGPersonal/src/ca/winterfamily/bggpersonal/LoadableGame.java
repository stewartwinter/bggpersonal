package ca.winterfamily.bggpersonal;

import java.util.concurrent.ExecutionException;

import android.util.Log;

public class LoadableGame {
	
	private Game mGame;
	private final Integer mBggId;
	private boolean mIsLoaded = false;
	
	LoadableGame(int bggid) {
		mBggId = bggid;
	}
	
	Game getGame() {
		if (mIsLoaded) {
			return mGame;
		} else {
			synchronized(this) {
				if (!mIsLoaded) {
					Log.d("BGGPersonal", "Loading details for game " + mBggId.toString());
					mGame = new Game();
					mGame.mBggId = mBggId.toString();
					BGGRemoteGameInfo rem = new BGGRemoteGameInfo();
					BGGRemoteGameInfo.BGGRemoteGameInfoParm parm = new BGGRemoteGameInfo.BGGRemoteGameInfoParm(mGame, false);
					rem.execute(parm);
					String xml = "";
					try {
						xml = rem.get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
					
					mGame.populateFromXML(xml);
					mIsLoaded = true;
					
					// now load comments
					rem = new BGGRemoteGameInfo();
					rem.execute(new BGGRemoteGameInfo.BGGRemoteGameInfoParm(mGame, true));
					mGame.populateFromXML(xml);
					
				}
				return mGame;
			}
		}
	}
	

}
