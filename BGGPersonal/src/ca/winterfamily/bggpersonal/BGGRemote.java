package ca.winterfamily.bggpersonal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public abstract class BGGRemote<PARMA> extends AsyncTask<PARMA, Void, String> {
	protected abstract String getUrlString(PARMA... parms);
	
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
	
	
	protected String doInBackground(PARMA... parms) {
		try {
			String url = getUrlString(parms);
			String xml = getUrl(url);
			return xml;
			
		} catch (IOException e) {
			// TODO: something with this exception
			Log.e("BGGPersonal",  "BGGRemoteHotGames.doInBackground", e);
		}
		return null;
	}

}
