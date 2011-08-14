package translator.com.server.transformation;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltTransformer;

public class XsltEngine {
	private String xsltRootPath;
	
	public XsltEngine(String xsltRootPath) {
		this.xsltRootPath = xsltRootPath;
	}	
	
	public void transform(InputStream inStream, OutputStream outStream, String stylesheet) throws Exception {				    
		Processor proc = new Processor(false);
		XsltCompiler comp = proc.newXsltCompiler();
		
		comp.setURIResolver(new URIResolver() {
			public Source resolve(String href, String base) throws TransformerException {
				return new StreamSource(getResourceAsStream(href));
            }
        });
		
		XsltTransformer trans = comp.compile(new StreamSource(getResourceAsStream(stylesheet))).load();
		
		Serializer out = new Serializer();
        out.setOutputProperty(Serializer.Property.METHOD, "xml"); // Set serialization method
        out.setOutputProperty(Serializer.Property.INDENT, "yes"); // Set indenting
        out.setOutputStream(outStream);
		
		trans.setSource(new StreamSource(inStream));
        trans.setDestination(out);
        trans.transform();
	}
	
	private InputStream getResourceAsStream(String name){
		return XsltEngine.class.getResourceAsStream(xsltRootPath + name);
	}
}
