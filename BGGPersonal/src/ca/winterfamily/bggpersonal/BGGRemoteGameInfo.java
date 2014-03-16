package ca.winterfamily.bggpersonal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class BGGRemoteGameInfo extends AsyncTask<String, Void, String> {
	
	private static final String BGGURL = "http://boardgamegeek.com/xmlapi2/thing?stats=1&";
	private static final String BGGID = "id";
	
	private byte[] getUrlBytes(String urlspec) throws IOException {
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
	
	
	protected String doInBackground(String... gameid) {
		try {
			String url = BGGURL + BGGID + "=" + gameid[0];
			String xml = getUrl(url);
			return xml;
			
		} catch (IOException e) {
			// TODO: something with this exception
			Log.e("BGGPersonal",  "BGGRemoteGameInfo.doInBackground", e);
		}
		return null;
	}

}
