package translator.com.server.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;


public class UrLFetcher {
	public static String get(String urlString) throws IOException {
		URL url = new URL(urlString);

		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		conn.connect();

		byte[] bos = IOUtil.readStream(conn.getInputStream());
		
		return new String(bos, Config.ENCODING);
	}
}
