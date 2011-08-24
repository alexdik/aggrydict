package translator.com.client.util;

import com.google.gwt.user.client.Cookies;

public class UserUtil {
	public static final String COOKIE = "www.aggrydict.com";
	public static final String SEPARATOR = "{&}";
	public static final int SEP_LEN = SEPARATOR.length();
	
	public static String getUserSecret(){
		String cookie = Cookies.getCookie(COOKIE);
		if (null == cookie) {
			return null;
		}
		int separatorIndex = cookie.indexOf(SEPARATOR);
		
		return cookie.substring(1, separatorIndex);
	}
	
	public static String getUserName(){
		String cookie = Cookies.getCookie(COOKIE);
		if (null == cookie) {
			return null;
		}
		int separatorIndex = cookie.indexOf(SEPARATOR);
		
		return cookie.substring(separatorIndex + SEP_LEN, cookie.length() - 1);
	}
	
	public static boolean isAuthorized() {
		String cookie = Cookies.getCookie(COOKIE);
		return null != cookie;
	}
}
