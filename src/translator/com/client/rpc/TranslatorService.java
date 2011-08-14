package translator.com.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("translatorService")
public interface TranslatorService extends RemoteService {

	String translate(String word, String dictEngine) throws Exception;
	
}
