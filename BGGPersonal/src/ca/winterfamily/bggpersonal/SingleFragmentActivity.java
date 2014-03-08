package ca.winterfamily.bggpersonal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class SingleFragmentActivity extends FragmentActivity {
	
	protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        
        FragmentManager fm = getSupportFragmentManager();
        
        Fragment container = fm.findFragmentById(R.id.fragmentContainer);
        if (container == null) {
        	container = createFragment();
        	fm.beginTransaction()
        		.add(R.id.fragmentContainer, container)
        		.commit();
        }
    }
}
