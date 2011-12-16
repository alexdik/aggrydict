package translator.com.server.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;


public class UrlFetcher {
	public static String get(String urlString, String userAgent) throws IOException {
		URL url = new URL(urlString);

		URLConnection conn = url.openConnection();
		if (userAgent != null) {
			conn.setRequestProperty("User-Agent", Config.USER_AGENT);
		}
		conn.setDoOutput(true);
		conn.connect();

		byte[] bos = IOUtil.readStream(conn.getInputStream());
		
		return new String(bos, Config.ENCODING);
	}
	
	public static String get(String urlString) throws IOException {
		return get(urlString, null);
	}
}