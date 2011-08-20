package translator.com.client.menubar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MenuBar extends Composite {

	private static MenuBarUiBinder uiBinder = GWT.create(MenuBarUiBinder.class);
	private final Anchor translator = new Anchor("Translator");
	private final Anchor favourite = new Anchor("Favourite");
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
	
	@Override
	protected void onLoad() {
		translator.addClickHandler(clickHandler);
		favourite.addClickHandler(clickHandler);
		hPanel.add(translator);
		hPanel.add(favourite);
		
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
