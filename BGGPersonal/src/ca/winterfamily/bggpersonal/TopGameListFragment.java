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

public class TopGameListFragment extends ListFragment {
	
	private ArrayList<Game> mTopGames;
	GameAdapter mListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		mTopGames = BGGUser.getBGGUser(getActivity()).getTopGames();
		mListAdapter = new GameAdapter(mTopGames);

		getActivity().setTitle(R.string.top_games_fragment_title);
		
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
				convertView = getActivity().getLayoutInflater().inflate(R.layout.top_game_list, null);
			}
			
			// configure the view
			Game game = getItem(position);
			
			TextView gameNameTextview = (TextView)convertView.findViewById(R.id.top_games_game_name);
			gameNameTextview.setText(game.getName());
			TextView gameRankTextview = (TextView)convertView.findViewById(R.id.top_games_game_rank);
			gameRankTextview.setText("Rank: " + game.mRank);
			
			return convertView;
		}
	}

}
