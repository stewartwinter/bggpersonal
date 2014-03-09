package ca.winterfamily.bggpersonal;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

public class GameListFragment extends ListFragment {
	
	private ArrayList<Game> mGameCollection;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// todo: consider setting a title
		// see page 171
		
		// get the games
		mGameCollection = BGGUser.getBGGUser(getActivity()).getCollection();
	}
}
