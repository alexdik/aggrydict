package translator.com.client;


import translator.com.client.download.Download;
import translator.com.client.favourite.Favourite;
import translator.com.client.mainview.MainView;
import translator.com.client.menubar.MenuBar;
import translator.com.client.menubar.MenuSwitcher;
import translator.com.client.util.TranslateAction;
import translator.com.client.util.UserUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Label;

public class Container extends Composite {
	private static ContainerUiBinder uiBinder = GWT.create(ContainerUiBinder.class);
	@UiField(provided=true) MenuBar menuBar;
	@UiField(provided=true) Favourite favourite;
	@UiField(provided=true) MainView mainView;
	@UiField(provided=true) Download download;
	@UiField Label userName;

	interface ContainerUiBinder extends UiBinder<Widget, Container> {
	}

	public Container() {
		mainView = new MainView();
		favourite = new Favourite();
		download = new Download();
		
		MenuSwitcher menuSwitcher = new MenuSwitcher(mainView, favourite, download);
		
		menuBar = new MenuBar(menuSwitcher);
		
		favourite.setTranslateAction(new TranslateAction() {
			public void translateText(String word) {
				mainView.setText(word);
				menuBar.activateTranslator();
				mainView.onClick(null);
			}
			public void setWordNotExists(String word) {
				mainView.setWordNotExists(word);
			}
		});
		
		initWidget(uiBinder.createAndBindUi(this));
		
		String userNameText = UserUtil.getUserName();
		if (null != userNameText) {
			userName.setVisible(true);
			userName.setText(userNameText);
		}
	}
}
