package translator.com.shared;

import java.io.UnsupportedEncodingException;

public class Converter {
	private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
	private static final String ENCODING = "UTF-8";
	private static final char SEP = '%';

	public static String toHex(String text) {
		byte[] buf;
		try {
			buf = text.getBytes(ENCODING);
		} catch (UnsupportedEncodingException e) {
			buf = text.getBytes();
		}
		
		char[] result = new char[buf.length * 3];
		
		int cnt = 0;
		for (byte b : buf) {
			int i = b & 255;
			char c1 = HEX_CHARS[(i & 0xF0) >>> 4];
			char c2 = HEX_CHARS[i & 0x0F];
			
			result[cnt] = SEP;
			result[cnt + 1] = c1;
			result[cnt + 2] = c2;
			
			cnt += 3;
		}
		
		return new String(result);
	}
}
