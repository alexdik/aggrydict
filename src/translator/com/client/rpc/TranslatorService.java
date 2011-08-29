package translator.com.client.rpc;

import java.util.Set;

import translator.com.shared.domen.TranslationResult;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("translatorService")
public interface TranslatorService extends RemoteService {

	TranslationResult translate(String word, String dictEngine, String userSecret) throws Exception;
	
	Boolean addWord(String userSecret, String word) throws Exception;
	
	Boolean removeWord(String userSecret, String word) throws Exception;
	
	Set<String> getWords(String userSecret, String filter) throws Exception;
	
}
