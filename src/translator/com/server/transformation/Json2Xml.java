package translator.com.server.transformation;

import java.io.IOException;
import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

public class Json2Xml {
	public static String convert(String jsonData) throws IOException{
		XMLSerializer serializer = new XMLSerializer();
		JSON json = JSONSerializer.toJSON(jsonData);
		String xml = serializer.write(json);
		
		return xml;
	}
}
