package translator.com.server.transformation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Html2Xml {
	public static String convert(byte[] html) throws IOException, SAXException{
		Parser r = new org.ccil.cowan.tagsoup.Parser();
		r.setProperty(Parser.autoDetectorProperty, new UTFDetector());
		
		InputSource is = new InputSource();
		
		ByteArrayInputStream bais = new ByteArrayInputStream(html);
		is.setByteStream(bais);

		StringWriter osw = new StringWriter();
		org.ccil.cowan.tagsoup.XMLWriter wr = new org.ccil.cowan.tagsoup.XMLWriter(osw);
		r.setContentHandler(wr);

		r.parse(is);
		
		String xml = osw.toString(); //StringEscapeUtils.unescapeHtml(osw.toString());
		return xml;
	}
}
