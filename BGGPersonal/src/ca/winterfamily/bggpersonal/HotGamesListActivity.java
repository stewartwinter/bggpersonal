package ca.winterfamily.bggpersonal;

import android.support.v4.app.Fragment;

public class HotGamesListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new HotGamesListFragment();
	}

}
