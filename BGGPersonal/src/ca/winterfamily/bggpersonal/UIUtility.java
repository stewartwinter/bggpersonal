package ca.winterfamily.bggpersonal;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;

public final class UIUtility {
	
	static boolean respondToMenu(MenuItem item, Fragment f) {
		switch (item.getItemId()) {
			case R.id.menu_item_bgg_info:
				Log.i("BGGPersonal", "bgg info menu clicked");
				Intent intent = new Intent(f.getActivity(), SettingsActivity.class);
				f.startActivity(intent);
				return true;
				
			case R.id.menu_item_collection:
				Intent collectionIntent = new Intent(f.getActivity(), GameCollectionActivity.class);
				f.startActivity(collectionIntent);
				return true;
				
			case R.id.menu_item_top:
				Intent topIntent = new Intent(f.getActivity(), TopGameListActivity.class);
				f.startActivity(topIntent);
				return true;
				
			case R.id.menu_item_hot:
				Intent hotIntent = new Intent(f.getActivity(), HotGamesListActivity.class);
				f.startActivity(hotIntent);
				return true;	
				
			case R.id.menu_item_search:
				Intent searchIntent = new Intent(f.getActivity(), SearchResultsActivity.class);
				f.startActivity(searchIntent);
				return true;
				
			case R.id.menu_item_refresh:
				BGGUser user = BGGUser.getBGGUser(f.getActivity());
				try {
					user.populate();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return false;
	}

}
