package ca.winterfamily.bggpersonal;

import android.support.v4.app.Fragment;

public class BGGActivity extends SingleFragmentActivity {

    @Override
	protected Fragment createFragment() {
		return new GameFragment();
	}


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bgg, menu);
        return true;
    }
*/  
}
