package translator.com.server.transformation;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.ccil.cowan.tagsoup.AutoDetector;

public class UTFDetector implements AutoDetector {
	public Reader autoDetectingReader(InputStream is) {
		try {
			return new InputStreamReader(is, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
