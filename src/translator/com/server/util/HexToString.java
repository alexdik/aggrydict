package translator.com.server.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Scanner;

public class HexToString {
	public static String toString(String hex) throws UnsupportedEncodingException {
		Scanner scanner = new Scanner(hex);
		scanner.useDelimiter("%");
		
		byte[] buf = new byte[hex.length()];
		
		for(int cnt = 0; scanner.hasNext(); cnt++) {
			String str = scanner.next();
			int oneByte = Integer.parseInt(str, 16);
			buf[cnt] = (byte) oneByte;
		}
		
		buf = Arrays.copyOf(buf, getNonEmptyLen(buf));
		return new String(buf, Config.ENCODING);
	}
	
	private static int getNonEmptyLen(byte[] buf) throws UnsupportedEncodingException {
		int cnt = 0;
		
		i: for (byte b : buf) {
			switch(b) {
				case 0: break i;
			}
			cnt++;
		}
		
		return cnt;
	}
}
