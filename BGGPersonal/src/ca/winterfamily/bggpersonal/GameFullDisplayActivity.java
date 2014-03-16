package ca.winterfamily.bggpersonal;

import android.support.v4.app.Fragment;

public class GameFullDisplayActivity extends SingleFragmentActivity {
	
    @Override
	protected Fragment createFragment() {
		return new GameFullDisplayFragment();
	}	

}
