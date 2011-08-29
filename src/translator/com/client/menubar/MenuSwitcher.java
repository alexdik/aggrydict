package translator.com.client.menubar;

import translator.com.client.favourite.Favourite;
import translator.com.client.mainview.MainView;

public class MenuSwitcher {
	private MainView mainView;
	private Favourite favourite;

	public MenuSwitcher(MainView mainView, Favourite favourite) {
		this.mainView = mainView;
		this.favourite = favourite;
	}

	void activateMainView(){
		favourite.setVisible(false);
		mainView.setVisible(true);
	}

	void activateFavourite() {
		mainView.setVisible(false);
		favourite.setVisible(true);
		favourite.renderFavourite(true);
	}

}
