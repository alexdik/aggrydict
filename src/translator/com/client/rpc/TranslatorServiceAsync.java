package translator.com.client.rpc;

import java.util.Set;

import translator.com.shared.domen.TranslationResult;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TranslatorServiceAsync {

	void translate(String word, String dictEngine, String userSecret, AsyncCallback<TranslationResult> callback);

	void addWord(String userSecret, String word, AsyncCallback<Boolean> callback);

	void removeWord(String userSecret, String word, AsyncCallback<Boolean> callback);

	void getWords(String userSecret, String filter, AsyncCallback<Set<String>> callback);

}
