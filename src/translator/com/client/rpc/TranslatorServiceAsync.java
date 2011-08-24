package translator.com.client.rpc;

import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TranslatorServiceAsync {

	void translate(String word, String dictEngine, AsyncCallback<String> callback);

	void addWord(String userSecret, String word, AsyncCallback<Boolean> callback);

	void removeWord(String userSecret, String word, AsyncCallback<Boolean> callback);

	void getWords(String userSecret, String filter, AsyncCallback<Set<String>> callback);

}
