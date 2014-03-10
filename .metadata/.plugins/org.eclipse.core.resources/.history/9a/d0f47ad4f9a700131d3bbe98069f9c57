package ca.winterfamily.bggpersonal;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class GameListFragment extends ListFragment {
	
	private ArrayList<Game> mGameCollection;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		getActivity().setTitle(R.string.collection_title);
		
		// get the games
		mGameCollection = BGGUser.getBGGUser(getActivity()).getCollection();
		
		ArrayAdapter<Game> adapter =
				new ArrayAdapter<Game>(getActivity(), android.R.layout.simple_list_item_1, mGameCollection);
		setListAdapter(adapter);
				
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.bgg, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_bgg_info:
			Log.i("BGGPersonal", "bgg info menu clicked");
			Intent intent = new Intent(getActivity(), SettingsActivity.class);
			startActivity(intent);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		
		}
	}
}
