package ca.winterfamily.bggpersonal;

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
		}
		return false;
	}

}
