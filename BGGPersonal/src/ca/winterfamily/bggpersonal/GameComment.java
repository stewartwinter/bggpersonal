package ca.winterfamily.bggpersonal;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;

public class GameComment implements Comparable<GameComment>{
	String mRating;
	String mRatingUser;
	String mComment;
	
	@Override
	public String toString() {
		String out = "(" + mRating + ")";
		out += " " + mComment;
		return out;
	}
	
	private int determineColor() {
		float red = 0;
		float green = 0;
		Float rating = Float.valueOf(0);
		try {
			rating = Float.parseFloat(mRating);
		} catch (Exception e) {
			// eat it
		}
		if (rating == 0) {
			return 0;
		} else {
			green = rating / 10 * 255;
			red = (10 - rating) / 10 * 255;
		}
		return Color.rgb((int)red, (int)Math.floor(green), 0);
	}
	
	public SpannableStringBuilder toSpannableString() {
		final BackgroundColorSpan bcs = new BackgroundColorSpan(determineColor());
		final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(255, 255, 255));

		SpannableStringBuilder rating = new SpannableStringBuilder(mRating);
		rating.setSpan(bcs,0, rating.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		rating.setSpan(fcs,0, rating.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		SpannableStringBuilder out = new SpannableStringBuilder("").append(rating).append(" ");
		out.append(" ").append(mComment);
		return out;


	}


	@Override
	public int compareTo(GameComment arg0) {
		try {
			if (mRating.compareTo(arg0.mRating) == 0) {
				return 0;
			} else if (Float.parseFloat(mRating) < Float.parseFloat(arg0.mRating)) {
				return 1;
			} else {
				return -1;
			}
		} catch(NumberFormatException e) {
		}
		try {
			float num = Float.parseFloat(mRating);
			return -1;
		} catch(NumberFormatException e) {
			return 1;
		}
	}
}
