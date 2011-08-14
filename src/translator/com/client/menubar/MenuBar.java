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
	@UiField HorizontalPanel hPanel;

	private ClickHandler clickHandler = new ClickHandler(){
		public void onClick(ClickEvent event) {
			Anchor anch = (Anchor) event.getSource();
			String anchText = anch.getText();
			
			if (anchText.equals("Translator")){
				translator.addStyleName("menuItemSelected");
				favourite.removeStyleName("menuItemSelected");
			} else if (anchText.equals("Favourite")) {
				favourite.addStyleName("menuItemSelected");
				translator.removeStyleName("menuItemSelected");
			}
		}
	};
	
	@Override
	protected void onLoad() {
		translator.addClickHandler(clickHandler);
		favourite.addClickHandler(clickHandler);
		hPanel.add(translator);
		hPanel.add(favourite);
		
		translator.addStyleName("menuItemSelected");
		translator.addStyleName("menuItem");
		favourite.addStyleName("menuItem");
		
		favourite.addStyleName("userInfo");
		
		super.onLoad();
	}
	
	interface MenuBarUiBinder extends UiBinder<Widget, MenuBar> {
	}

	public MenuBar() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
