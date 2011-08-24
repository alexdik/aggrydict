package translator.com.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Formatter;
import java.util.Set;

import translator.com.client.rpc.TranslatorService;
import translator.com.domain.DatastoreHelper;
import translator.com.server.transformation.Html2Xml;
import translator.com.server.transformation.Json2Xml;
import translator.com.server.transformation.XsltEngine;
import translator.com.server.util.Config;
import translator.com.server.util.IOUtil;
import translator.com.shared.Engines;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TranslatorServiceImpl extends RemoteServiceServlet implements TranslatorService {
	private static final long serialVersionUID = 3176680658668933784L;

	public String translate(String word, String dictEngine) throws Exception {
		URL url;
		try {
			String draftUrl = Config.getProperty(dictEngine + ".host");
			draftUrl = new Formatter().format(draftUrl, word).toString();
			url = new URL(draftUrl); //"http://localhost:8080/web/proxy"

			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", Config.USER_AGENT);
			conn.setDoOutput(true);
			conn.connect();

//			OutputStream ousCon = conn.getOutputStream();
//			ousCon.write(draftUrl.getBytes(Config.ENCODING));
//			ousCon.flush();

			// Transforming to XML
			byte[] bos = IOUtil.readStream(conn.getInputStream());
			String xml = null;
			if (dictEngine.equals(Engines.abbyy.toString())) {
				xml = Html2Xml.convert(bos);
			} else if (dictEngine.equals(Engines.google.toString())) {
				String json = new String(bos, Config.ENCODING);
				xml = Json2Xml.convert(json);
			}
			
			// Generating html presentation
			XsltEngine xsltEngine = new XsltEngine("/");
			ByteArrayOutputStream ousXslt = new ByteArrayOutputStream();
			InputStream is = new ByteArrayInputStream(xml.getBytes(Config.ENCODING));

			xsltEngine.transform(is, ousXslt, Config.getProperty(dictEngine + ".stylesheet"));

			String newHtml = ousXslt.toString(Config.ENCODING);
			return newHtml;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	@Override
	public Boolean addWord(String userSecret, String word) throws Exception {
		return DatastoreHelper.addWord(userSecret, word);
	}

	@Override
	public Boolean removeWord(String userSecret, String word) throws Exception {
		return DatastoreHelper.removeWord(userSecret, word);
	}

	@Override
	public Set<String> getWords(String userSecret, String filter) throws Exception {
		return DatastoreHelper.getWords(userSecret, filter);
	}
}