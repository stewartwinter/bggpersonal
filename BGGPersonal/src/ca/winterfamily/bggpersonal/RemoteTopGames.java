package ca.winterfamily.bggpersonal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class RemoteTopGames extends AsyncTask<Integer, Void, String> {
	
	private static final String TOPGAMES = "http://bbg.winterfamily.ca/bggtop.php?";
	private static final String AMOUNT = "amount";
	private static final String AFTER = "after";
	
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
	
	/* Expect intsIn to be in the form of:
	 *   int[0] = the start after value
	 *   int[1] = the number to retrieve -- if not there, then use 100
	 */
	protected String doInBackground(Integer... intsIn) {
		try {
			String url = TOPGAMES;
			if (intsIn.length > 0) {
				url += AFTER + "=" + intsIn[0].toString();
			}
			if (intsIn.length > 1) {
				url += "&" + AMOUNT + "=" + intsIn[1].toString();
			}
			String xml = getUrl(url);
			return xml;
			
		} catch (IOException e) {
			// TODO: something with this exception
			Log.e("BGGPersonal",  "BGGRemote.doInBackground", e);
		}
		return null;
	}

}
