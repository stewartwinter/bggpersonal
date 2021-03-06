package ca.winterfamily.bggpersonal;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class GameFullDisplayFragment extends Fragment {
	
	public static final String EXTRA_GAME_ID = "ca.winterfamily.bggpersonal.game_id";
	public static final String EXTRA_PARENT_ACTIVITY = "ParentClassName";
	
	private String mParentActivityClass;
	
	private TextView mYearPublishedTextView;
	private TextView mNameTextView;
	private TextView mRatingTextView;
	private TextView mPlayers;
	private TextView mPlayingTime;
	private TextView mUsersRatingTextView;
	private TextView mRankTextView;
	private ImageView mThumbnailImageView;
	private TextView mDescriptionTitle;
	private TextView mDescriptionView;
	private TextView mComments;
	private ScrollView mOverallScrollArea;
	private ScrollView mDescScrollArea;
	private TextView mCommentsTitle;
	private ScrollView mCommentScrollArea;
	
	Game mGame = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String id = (String)getActivity().getIntent().getSerializableExtra(EXTRA_GAME_ID);
		mParentActivityClass = (String)getActivity().getIntent().getSerializableExtra(EXTRA_PARENT_ACTIVITY);
		mGame = BGGUser.getBGGUser(getActivity()).getDetailedGame(id);
		
		setHasOptionsMenu(true);
	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.game_full_display, parent, false);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
		mYearPublishedTextView = (TextView) v.findViewById(R.id.game_full_display_year_published);
		mPlayers = (TextView) v.findViewById(R.id.game_full_display_players);
		mPlayingTime = (TextView) v.findViewById(R.id.game_full_display_playingtime);
		mNameTextView = (TextView) v.findViewById(R.id.game_full_display_name);
		mThumbnailImageView = (ImageView) v.findViewById(R.id.game_full_display_image);
		mRatingTextView = (TextView) v.findViewById(R.id.game_full_display_rating);
		mUsersRatingTextView = (TextView) v.findViewById(R.id.game_full_display_users_rating);
		mRankTextView = (TextView) v.findViewById(R.id.game_full_display_rank);
		mDescriptionTitle = (TextView) v.findViewById(R.id.game_full_display_description_title);
		mDescriptionView = (TextView) v.findViewById(R.id.game_full_display_description);
		mComments = (TextView) v.findViewById(R.id.game_full_display_comments);
		mOverallScrollArea = (ScrollView) v.findViewById(R.id.game_full_display_overallAreaScroller);
		mDescScrollArea = (ScrollView) v.findViewById(R.id.game_full_display_descriptionAreaScroller);
		mCommentsTitle = (TextView) v.findViewById(R.id.game_full_display_comments_title);
		mCommentScrollArea = (ScrollView) v.findViewById(R.id.game_full_display_commentsAreaScroller);
		
		// fix up scrollviews
		mOverallScrollArea.setOnTouchListener(new View.OnTouchListener() 
		{
		       public boolean onTouch(View p_v, MotionEvent p_event) 
		        {
		    	   mDescScrollArea.getParent().requestDisallowInterceptTouchEvent(false);
		    	   mCommentScrollArea.getParent().requestDisallowInterceptTouchEvent(false);
		           //  We will have to follow above for all scrollable contents
		           return false;
		        }
		});
		mDescScrollArea.setOnTouchListener(new View.OnTouchListener() 
		{
		      public boolean onTouch(View p_v, MotionEvent p_event)
		       {
		          // this will disallow the touch request for parent scroll on touch of child view
		           p_v.getParent().requestDisallowInterceptTouchEvent(true);
		           return false;
		       }
		});
		mCommentScrollArea.setOnTouchListener(new View.OnTouchListener() 
		{
		      public boolean onTouch(View p_v, MotionEvent p_event)
		       {
		          // this will disallow the touch request for parent scroll on touch of child view
		           p_v.getParent().requestDisallowInterceptTouchEvent(true);
		           return false;
		       }
		});
		
		mDescriptionTitle.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View arg0) {
					if (mDescScrollArea.getVisibility()==View.VISIBLE) {
					mDescScrollArea.setVisibility(View.GONE);
					Drawable img = getActivity().getResources().getDrawable( R.drawable.ic_action_expand );
					mDescriptionTitle.setCompoundDrawablesWithIntrinsicBounds(null,  null, img,  null);
				} else {
					mDescScrollArea.setVisibility(View.VISIBLE);
					Drawable img = getActivity().getResources().getDrawable( R.drawable.ic_action_collapse );
					mDescriptionTitle.setCompoundDrawablesWithIntrinsicBounds(null,  null, img,  null);
				}
			}

		   });
		
		mCommentsTitle.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View arg0) {
				if (mCommentScrollArea.getVisibility()==View.VISIBLE) {
					mCommentScrollArea.setVisibility(View.GONE);
					Drawable img = getActivity().getResources().getDrawable( R.drawable.ic_action_expand );
					mCommentsTitle.setCompoundDrawablesWithIntrinsicBounds(null,  null, img,  null);
				} else {
					mCommentScrollArea.setVisibility(View.VISIBLE);
					Drawable img = getActivity().getResources().getDrawable( R.drawable.ic_action_collapse );
					mCommentsTitle.setCompoundDrawablesWithIntrinsicBounds(null,  null, img,  null);
				}
			}
	
		   });
		
		if (mGame != null) {
			mNameTextView.setText(mGame.getName());
			mYearPublishedTextView.setText("Published: " + mGame.mYearPublished);
			mPlayers.setText("Players: " + mGame.mMinPlayers + " to " + mGame.mMaxPlayers);
			mPlayingTime.setText("Duration: " + mGame.mPlayingTime);
			mRatingTextView.setText("Average Rating: " + mGame.mAverageRating);
			mUsersRatingTextView.setText("Users Rating: " + mGame.mNumberOfRatings);
			mRankTextView.setText("BGG Rank: " + mGame.mRank);
			mDescriptionView.setText(mGame.mDescription);
			SpannableStringBuilder commentStr = new SpannableStringBuilder("");
			final SpannableString lineEnd = new SpannableString("\n");
			for(GameComment comment : mGame.mCommentList){ 
				commentStr.append(comment.toSpannableString()).append(lineEnd);
			}
			mComments.setText(commentStr);
			if (mGame.mThumbnailUrl.length() > 0) {
				BGGRemoteGetDrawableFromURL rem = new BGGRemoteGetDrawableFromURL();
				rem.execute(mGame.mThumbnailUrl);
				try {
					mThumbnailImageView.setImageDrawable(rem.get());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			refreshComments();
		} else {
			mNameTextView.setText("Game not found");
		}
		return v;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Intent parentIntent;
				try {
					parentIntent = new Intent(getActivity(), Class.forName(mParentActivityClass));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return super.onOptionsItemSelected(item);
				}
				parentIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(parentIntent);

				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private void refreshComments() {
		if (!mGame.mCommentList.isEmpty()) {
			return;
		}
		new Thread(new Runnable() {
		    @Override
		    public void run() {
		        try {
		            Thread.sleep(3000);
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		        FragmentActivity activity = getActivity();
		        if (activity != null) {
		        	activity.runOnUiThread(new Runnable() {
		            @Override
		            public void run() {
		    			SpannableStringBuilder commentStr = new SpannableStringBuilder("");
		    			final SpannableString lineEnd = new SpannableString("\n");
		    			for(GameComment comment : mGame.mCommentList){ 
		    				commentStr.append(comment.toSpannableString()).append(lineEnd);
		    			}
		    			mComments.setText(commentStr);
		            }
		        });
		        }
		    }
		}).start();
	}
}
