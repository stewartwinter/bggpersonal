package ca.winterfamily.bggpersonal;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GameFullDisplayFragment extends Fragment {
	
	public static final String EXTRA_GAME_ID = "ca.winterfamily.bggpersonal.game_id";
	
	private TextView mYearPublishedTextView;
	private TextView mNameTextView;
	Game mGame = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String id = (String)getActivity().getIntent().getSerializableExtra(EXTRA_GAME_ID);
		mGame = BGGUser.getBGGUser(getActivity()).findGame(id);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.game_full_display, parent, false);
		
		mYearPublishedTextView = (TextView) v.findViewById(R.id.game_full_display_year_published);
		mNameTextView = (TextView) v.findViewById(R.id.game_full_display_name);
		
		if (mGame != null) {
			mNameTextView.setText(mGame.getName());
			mYearPublishedTextView.setText(mGame.mYearPublished);
		} else {
			mNameTextView.setText("Game not found");
		}
		return v;
	}

}
