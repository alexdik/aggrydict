package translator.com.client.favourite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Favourite extends Composite {

	private static FavouriteUiBinder uiBinder = GWT
			.create(FavouriteUiBinder.class);
	@UiField VerticalPanel verticalPanel;

	interface FavouriteUiBinder extends UiBinder<Widget, Favourite> {
	}

	public Favourite() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
