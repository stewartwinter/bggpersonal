package ca.winterfamily.bggpersonal;

import android.support.v4.app.Fragment;

public class GameInfoActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new GameInfoFragment();
	}
	
}

