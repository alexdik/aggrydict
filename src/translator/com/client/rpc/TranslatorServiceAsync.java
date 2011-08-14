package translator.com.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TranslatorServiceAsync {

	void translate(String word, String dictEngine, AsyncCallback<String> callback);

}
