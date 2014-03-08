package ca.winterfamily.bggpersonal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GameFragment extends Fragment {
	private Game mGame;
	private TextView mNameField;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mGame = new Game("A game with a name");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_game,  parent, false);
		
		//set up fields in the UI
		mNameField = (TextView)v.findViewById(R.id.game_name);
		mNameField.setText(mGame.getName());
		
		return v;
	}
}
