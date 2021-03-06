package ca.winterfamily.bggpersonal;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class GameListFragment extends ListFragment {
	
	private ArrayList<Game> mGameCollection;
	GameAdapter mListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		mGameCollection = BGGUser.getBGGUser(getActivity()).getCollection();
		mListAdapter = new GameAdapter(mGameCollection);

		getActivity().setTitle(R.string.collection_title);
		
		// get the games
		setListAdapter(mListAdapter);
				
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.bgg, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (UIUtility.respondToMenu(item, this)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mListAdapter.notifyDataSetChanged();

	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Game game = ((GameAdapter)getListAdapter()).getItem(position);
		Log.d("BGGPersonal", game.getName() + " clicked");
/*		
		BGGRemoteGameInfo rem = new BGGRemoteGameInfo();
		rem.execute(game.mBggId);
		String xml = "";
		try {
			xml = rem.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		game.populateFromXML(xml);*/
		Intent intent = new Intent(getActivity(), GameFullDisplayActivity.class);
		intent.putExtra(GameFullDisplayFragment.EXTRA_GAME_ID, game.mBggId);
		intent.putExtra(GameFullDisplayFragment.EXTRA_PARENT_ACTIVITY, getActivity().getClass().getName());
		startActivity(intent);

	}
	
	private class GameAdapter extends ArrayAdapter<Game> {
		
		public GameAdapter(ArrayList<Game> games) {
			super(getActivity(), 0, games);
		}
	
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.collection_item_game, null);
			}
			
			// configure the view
			Game game = getItem(position);
			
			TextView gameNameTextview = (TextView)convertView.findViewById(R.id.collection_item_game_name);
			gameNameTextview.setText(game.getName());
			TextView gamePlaysTextview = (TextView)convertView.findViewById(R.id.collection_item_game_plays);
			gamePlaysTextview.setText("Plays: " + ((Integer)game.mPlays).toString());
			TextView gameOwnedTextview = (TextView)convertView.findViewById(R.id.collection_item_game_owned);
			gameOwnedTextview.setText("");
			if (game.mOwned) {
				gameOwnedTextview.setText("Own");
			} else if (game.mWishlist.compareTo("0") != 0) {
				if (game.mWishlistPriority.compareTo("1") == 0) {
					gameOwnedTextview.setText("Buy");
				} else if (game.mWishlistPriority.compareTo("2") == 0) {
					gameOwnedTextview.setText("Buy");
				} else if (game.mWishlistPriority.compareTo("3") == 0) {
					gameOwnedTextview.setText("Want");
				} else if (game.mWishlistPriority.compareTo("4") == 0) {
					gameOwnedTextview.setText("Maybe");
				} else if (game.mWishlistPriority.compareTo("5") == 0) {
					gameOwnedTextview.setText("X");
				}
				
			} else if (game.mWanted.compareTo("0") != 0) {
				gameOwnedTextview.setText("Want");
			}
			
			
			return convertView;
		}
	}

}
