package ca.winterfamily.bggpersonal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class BGGRemote extends AsyncTask<String, Void, String> {
	
	private static final String BGGURL = "http://boardgamegeek.com/xmlapi2/collection?";
	private static final String BGGUSER = "username";
	
	private byte[] getUrlBytes(String urlspec) throws IOException {
		Log.i("BGGPersonal",  "fetching url = " + urlspec);
		URL url = new URL(urlspec);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			InputStream in = connection.getInputStream();
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, bytesRead);
			}
			out.close();
			
			return out.toByteArray();

		} finally {
			connection.disconnect();
		}
	}
	
	private String getUrl(String url) throws IOException {
		return new String (getUrlBytes(url));
	}
	
	
	protected String doInBackground(String... username) {
		try {
			String url = BGGURL + BGGUSER + "=" + username[0];
			String xml = getUrl(url);
//			Log.i("BGGPersonal",  "xml out = " + xml);
			return xml;
			
		} catch (IOException e) {
			// TODO: something with this exception
			Log.e("BGGPersonal",  "BGGRemote.doInBackground", e);
		}
		return null;
	}

}