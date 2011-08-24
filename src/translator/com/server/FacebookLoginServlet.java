package translator.com.server;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import translator.com.client.util.UserUtil;
import translator.com.domain.DatastoreHelper;
import translator.com.domain.UserInfo;
import translator.com.server.util.Config;

public class FacebookLoginServlet extends HttpServlet {
	private static final long serialVersionUID = -4096110024780032128L;
	private static final int URL_PREFIX_LEN = "access_token=".length();
	private static final int COOKIE_EXP = 12 * 30 * 86400;

	
	static Properties props = System.getProperties(); 
	static String appId = props.getProperty("fb_app_id");
	static String callbackURL = props.getProperty("fb_callback_url");
	static String clientSecret = props.getProperty("fb_secret");
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		String code = req.getParameter("code");
		
		if (code != null && !code.isEmpty()) {
			String getTokenURL = "https://graph.facebook.com/oauth/access_token"
					+ "?client_id=" + appId + "&redirect_uri=" + callbackURL
					+ "&client_secret=" + clientSecret + "&code=" + code;
			
			try {
				String tokenRsp = UrLFetcher.get(getTokenURL);
				
				String token = URLEncoder.encode(formatUrl(tokenRsp), Config.ENCODING);
				String url = "https://graph.facebook.com/me?access_token=" + token;
				String userData = UrLFetcher.get(url);
				
				JSONObject jsonObj = JSONObject.fromObject( userData );
				if (jsonObj.containsKey("name") && jsonObj.containsKey("id")) {
					String userName = jsonObj.getString("name");
					String userId = jsonObj.getString("id");
					String userSecret = null;
					
					UserInfo userInfo = DatastoreHelper.getUser(userId);
					if (null == userInfo) {
						userSecret = DatastoreHelper.creatUser(userId, userName);
					} else {
						userSecret = userInfo.getUserSecret();
					}
					
					Cookie cookie = new Cookie(UserUtil.COOKIE, userSecret + UserUtil.SEPARATOR + userName);
					cookie.setMaxAge(COOKIE_EXP);
					resp.addCookie(cookie);
					
					resp.sendRedirect(Config.getProperty("hosturl"));
				} else {
					redirectToLogin(resp);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			redirectToLogin(resp);
		}
		
	}
	
	private void redirectToLogin(HttpServletResponse resp) {
		String fbLoginPage = "https://graph.facebook.com/oauth/authorize"
				+ "?client_id=" + appId + "&redirect_uri=" + callbackURL;

		try {
			resp.sendRedirect(fbLoginPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String formatUrl(String urlStr) {
		String url = urlStr.substring(URL_PREFIX_LEN);
		int ampInd = url.indexOf("&");
		url = url.substring(0, ampInd);
		
		return url;
	}
}
