package translator.com.client.menubar;

import com.google.gwt.user.client.ui.Composite;

public class MenuSwitcher {
	private Composite mainView;
	private Composite favourite;

	public MenuSwitcher(Composite mainView, Composite favourite) {
		this.mainView = mainView;
		this.favourite = favourite;
	}

	public void activateMainView(){
		favourite.setVisible(false);
		mainView.setVisible(true);
	}

	public void activateFavourite() {
		mainView.setVisible(false);
		favourite.setVisible(true);
	}

}
