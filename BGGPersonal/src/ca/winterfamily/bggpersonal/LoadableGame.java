package ca.winterfamily.bggpersonal;

import java.util.concurrent.ExecutionException;

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
					BGGRemoteGameInfo rem = new BGGRemoteGameInfo();
					rem.execute(mBggId.toString());
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
					
					mGame = new Game();
					mGame.mBggId = mBggId.toString();
					mGame.populateFromXML(xml);
					mIsLoaded = true;
				}
				return mGame;
			}
		}
	}
	

}
