package ca.winterfamily.bggpersonal;

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
