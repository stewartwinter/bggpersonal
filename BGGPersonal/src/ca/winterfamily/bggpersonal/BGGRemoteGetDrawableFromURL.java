package ca.winterfamily.bggpersonal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

public class BGGRemoteGetDrawableFromURL extends AsyncTask<String, Void, Drawable> {
	
	public Drawable drawableFromUrl(String url) throws IOException {
	    Bitmap x;

		    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		    connection.connect();
		    InputStream input = connection.getInputStream();
		    x = BitmapFactory.decodeStream(input);

	    return new BitmapDrawable(x);
	}
	
	protected Drawable doInBackground(String... url) {
		try {
			Drawable image = drawableFromUrl(url[0]);
			return image;
			
		} catch (IOException e) {
			// TODO: something with this exception
			Log.e("BGGPersonal",  "BGGRemote.doInBackground", e);
		}
		return null;
	}

}
