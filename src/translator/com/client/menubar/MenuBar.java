package translator.com.client.menubar;

import translator.com.client.util.UserUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MenuBar extends Composite {

	private static MenuBarUiBinder uiBinder = GWT.create(MenuBarUiBinder.class);
	private final Anchor translator = new Anchor("Translator");
	private final Anchor favourite = new Anchor("Favourite");
	private final Anchor login = new Anchor("Login", GWT.getHostPageBaseURL()+"loginfacebook");
	private final Anchor logout = new Anchor("Logout", GWT.getHostPageBaseURL());
	private final static String ITEM_SELECTED_STYLE = "menuItemSelected";
	private MenuSwitcher menuSwitcher;
	@UiField HorizontalPanel hPanel;

	private ClickHandler clickHandler = new ClickHandler(){
		public void onClick(ClickEvent event) {
			Anchor anch = (Anchor) event.getSource();
			String anchText = anch.getText();
			
			if (anchText.equals("Translator")){
				translator.addStyleName(ITEM_SELECTED_STYLE);
				favourite.removeStyleName(ITEM_SELECTED_STYLE);
				
				menuSwitcher.activateMainView();
			} else if (anchText.equals("Favourite")) {
				favourite.addStyleName(ITEM_SELECTED_STYLE);
				translator.removeStyleName(ITEM_SELECTED_STYLE);
				
				menuSwitcher.activateFavourite();
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
		hPanel.add(translator);
		hPanel.add(favourite);
		
		if (UserUtil.isAuthorized()) {
			hPanel.add(logout);
			logout.addStyleName("menuLoginLogout");
			logout.addClickHandler(logoutHandler);
		} else {
			hPanel.add(login);
			login.addStyleName("menuLoginLogout");
		}
		
		translator.addStyleName(ITEM_SELECTED_STYLE);
		translator.addStyleName("menuItem");
		favourite.addStyleName("menuItem");
		
		super.onLoad();
	}
	
	interface MenuBarUiBinder extends UiBinder<Widget, MenuBar> {
	}

	public MenuBar(MenuSwitcher menuSwitcher) {
		this.menuSwitcher = menuSwitcher;
	    
		initWidget(uiBinder.createAndBindUi(this));
	}

}
