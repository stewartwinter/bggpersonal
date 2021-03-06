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
import android.widget.Toast;

public class SettingsFragment extends Fragment {
	
	private EditText mUserNameWidget;
	private String mUserName;
	private Button mSaveButton;
	private Button mCancelButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_settings, parent, false);
		
		mSaveButton = (Button) v.findViewById(R.id.setting_save);
		mCancelButton = (Button) v.findViewById(R.id.setting_cancel);
		mUserNameWidget = (EditText)v.findViewById(R.id.setting_bgguser);
		
		mUserNameWidget.addTextChangedListener(
				new TextWatcher() {
					@Override
					public void onTextChanged(CharSequence c, int start, int before, int count) {
						mUserName = c.toString();
					}

					@Override
					public void afterTextChanged(Editable arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void beforeTextChanged(CharSequence arg0, int arg1,
							int arg2, int arg3) {
						// TODO Auto-generated method stub
						
					}
				}
				
				);
		
		mSaveButton.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
 						// load user info
 						BGGUser.getBGGUser(getActivity()).setUserName(mUserName);
 						
 						// and close
 						getActivity().finish();
					}
					
				}
				);
		
		mCancelButton.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
 						getActivity().finish();
					}
					
				}
				);		
		
		
		return v;
	}

}
