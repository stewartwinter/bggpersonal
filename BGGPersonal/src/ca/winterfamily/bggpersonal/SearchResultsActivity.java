package ca.winterfamily.bggpersonal;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.xmlpull.v1.XmlPullParserException;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class SearchResultsActivity extends SingleFragmentActivity {
	
	@Override
	protected Fragment createFragment() {
		return new SearchResultsFragment();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
	    setIntent(intent);
	    
	    // do something
	    
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	        String query = intent.getStringExtra(SearchManager.QUERY);
	        if (query.length() > 3) {
	        	BGGUser user = BGGUser.getBGGUser(this);
	        	try {
					user.populatSearchResults(query);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
	}

}
