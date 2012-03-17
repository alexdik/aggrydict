package translator.com.client.menubar;

import translator.com.client.util.UserUtil;

import translator.com.client.resources.Resources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import translator.com.shared.DictConstants;

public class MenuBar extends Composite {

	private static MenuBarUiBinder uiBinder = GWT.create(MenuBarUiBinder.class);
	private final Anchor translator = new Anchor("Translator");
	private final Anchor favourite = new Anchor("Favourite");
	private final Anchor download = new Anchor("Download");
	private final Anchor login = new Anchor("", GWT.getHostPageBaseURL() + DictConstants.FACEBOOK_AUTH_SERVLET);
	private final Anchor logout = new Anchor("Logout", GWT.getHostPageBaseURL());
	private final static String ITEM_SELECTED_STYLE = "menuItemSelected";
	private static final Resources RESOURCES = GWT.create(Resources.class);
	private MenuSwitcher menuSwitcher;
	@UiField HorizontalPanel hPanel;

	private ClickHandler clickHandler = new ClickHandler(){
		public void onClick(ClickEvent event) {
			Anchor anch = (Anchor) event.getSource();
			String anchText = anch.getText();
			
			if (anchText.equals("Translator")){
				activateTranslator();
			} else if (anchText.equals("Favourite")) {
				activateFavourite();
			} else if (anchText.equals("Download")) {
				activateDownload();
			}
		}
	};
	
	private ClickHandler logoutHandler = new ClickHandler(){
		public void onClick(ClickEvent event) {
			Cookies.removeCookie(UserUtil.COOKIE);
		}
	};
	
	@Override
	protected void onLoad() {
		translator.addClickHandler(clickHandler);
		favourite.addClickHandler(clickHandler);
		download.addClickHandler(clickHandler);
		hPanel.add(translator);
		hPanel.add(favourite);
		hPanel.add(download);
		
		if (UserUtil.isAuthorized()) {
			hPanel.add(logout);
			logout.addStyleName("menuLogout");
			logout.addClickHandler(logoutHandler);
		} else {
			Image loginImg = new Image(RESOURCES.facebook());
			loginImg.setStyleName("menuLogin");
			login.getElement().appendChild(loginImg.getElement());
			hPanel.add(login);
		}
		
		translator.addStyleName(ITEM_SELECTED_STYLE);
		translator.addStyleName("menuItem");
		favourite.addStyleName("menuItem");
		download.addStyleName("menuItem");
		
		super.onLoad();
	}
	
	interface MenuBarUiBinder extends UiBinder<Widget, MenuBar> {
	}

	public MenuBar(MenuSwitcher menuSwitcher) {
		this.menuSwitcher = menuSwitcher;
	    
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void activateTranslator() {
		translator.addStyleName(ITEM_SELECTED_STYLE);
		favourite.removeStyleName(ITEM_SELECTED_STYLE);
		download.removeStyleName(ITEM_SELECTED_STYLE);
		
		menuSwitcher.activateMainView();
	}
	
	public void activateFavourite() {
		favourite.addStyleName(ITEM_SELECTED_STYLE);
		translator.removeStyleName(ITEM_SELECTED_STYLE);
		download.removeStyleName(ITEM_SELECTED_STYLE);
		
		menuSwitcher.activateFavourite();
	}
	
	public void activateDownload() {
		download.addStyleName(ITEM_SELECTED_STYLE);
		translator.removeStyleName(ITEM_SELECTED_STYLE);
		favourite.removeStyleName(ITEM_SELECTED_STYLE);
		
		menuSwitcher.activateDownload();
	}
}
