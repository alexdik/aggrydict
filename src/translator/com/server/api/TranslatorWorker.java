package translator.com.server.api;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Formatter;
import java.util.LinkedHashSet;
import java.util.Set;

import net.sf.json.JSONArray;
import restdisp.worker.AbstractWorker;
import translator.com.server.util.Config;
import translator.com.server.util.UrlFetcher;
import translator.com.shared.LangDetector;

public class TranslatorWorker extends AbstractWorker {
	public void getShortTranslation(String word) throws IOException {
		String simpleWord = URLDecoder.decode(word, Config.ENCODING);
		String[] langs = LangDetector.getLangByFirstLetter(simpleWord);
		if (null == langs) {
			getResponse().getOutputStream().write("No word found".getBytes(Config.ENCODING));
			return;
		}
		
		String draftUrl = Config.getProperty("google.host");
		draftUrl = new Formatter().format(draftUrl, word, langs[0], langs[1]).toString();
		String jsonString = UrlFetcher.get(draftUrl, Config.USER_AGENT);
		String transString = fetchTranslation(jsonString);
		
		getResponse().getOutputStream().write(transString.getBytes(Config.ENCODING));
	}
	
	private static String fetchTranslation(String json) {
		final JSONArray jsonObj = JSONArray.fromObject(json);
		Set<String> res = new LinkedHashSet<String>();
		String headStr = fetchJsonArray(jsonObj, 0, 0, 0);
		if (headStr != null)
			res.add(headStr);
		
		Object obj = jsonObj.get(1);
		if (obj instanceof JSONArray) {
			JSONArray tmpJson = jsonObj.getJSONArray(1);
			if(tmpJson != null) {
				int len = tmpJson.size();
				for(int cnt = 0; cnt < len; cnt++) {
					JSONArray curJson = tmpJson.getJSONArray(cnt);
					for(int cntWords = 0; cntWords < curJson.getJSONArray(1).size(); cntWords++) {
						res.add(fetchJsonArray(curJson, 1, cntWords));
						if (cntWords == 3)
							break;
					}
				}
			}
		}
		String resStr = res.toString();
		return resStr.substring(1, resStr.length() - 1);
	}
	
	private static String fetchJsonArray(final JSONArray jsonObj, int...dim) {
		JSONArray tmpJson = jsonObj;
		for (int i = 0; i < dim.length; i++) {
			if (i == dim.length - 1) {
				return tmpJson.getString(dim[i]);
			}
			Object obj = tmpJson.get(dim[i]);
			if (obj instanceof JSONArray) {
				tmpJson = tmpJson.getJSONArray(dim[i]);
			} else if (obj instanceof net.sf.json.JSONNull) {
				return null;
			} else {
				return obj.toString();
			}
		}
		return null;
	}
}
