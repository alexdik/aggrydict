package translator.com.client.menubar;

import translator.com.client.download.Download;
import translator.com.client.favourite.Favourite;
import translator.com.client.mainview.MainView;

public class MenuSwitcher {
	private MainView mainView;
	private Favourite favourite;
	private Download download;

	public MenuSwitcher(MainView mainView, Favourite favourite, Download download) {
		this.mainView = mainView;
		this.favourite = favourite;
		this.download = download;
	}

	void activateMainView(){
		favourite.setVisible(false);
		download.setVisible(false);
		mainView.setVisible(true);
	}

	void activateFavourite() {
		mainView.setVisible(false);
		download.setVisible(false);
		favourite.setVisible(true);
		favourite.renderFavourite(true);
	}
	
	void activateDownload() {
		mainView.setVisible(false);
		favourite.setVisible(false);
		download.setVisible(true);
	}

}
