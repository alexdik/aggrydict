package translator.com.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Translator implements EntryPoint {
	Container container = new Container();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootPanel.get("mainPanel").add(container);
	}
}
