package ca.winterfamily.bggpersonal;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public class GameListFragment extends ListFragment {
	
	private ArrayList<Game> mGameCollection;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getActivity().setTitle(R.string.collection_title);
		
		// get the games
		mGameCollection = BGGUser.getBGGUser(getActivity()).getCollection();
		
		ArrayAdapter<Game> adapter =
				new ArrayAdapter<Game>(getActivity(), android.R.layout.simple_list_item_1, mGameCollection);
		setListAdapter(adapter);
				
	}
}
