package translator.com.client.favourite;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Favourite extends Composite {
	private static final String FIELD_SIZE = "190";
	private static final int ELEMS_PER_ROW = 3;

	private static FavouriteUiBinder uiBinder = GWT.create(FavouriteUiBinder.class);
	@UiField
	VerticalPanel verticalPanel;

	interface FavouriteUiBinder extends UiBinder<Widget, Favourite> {
	}

	public Favourite() {
		initWidget(uiBinder.createAndBindUi(this));
		
//		Anchor anch = new Anchor(word);
//		List<String> lst = new ArrayList<String>();
//		lst.add("test1");
//		lst.add("test2");
//		renderRecords(lst);
	}

	public void renderRecords(List<String> words) {
		verticalPanel.clear();

		if (words.size() == 0) {
			Label label = new Label("You favourite list is empty");
			label.setHeight("25px");
			verticalPanel.add(label);
		}

		HorizontalPanel hp = null;

		int cnt = 1;
		for (String word : words) {
			boolean needNewPanel = cnt % ELEMS_PER_ROW == 1;

			if (needNewPanel) {
				hp = new HorizontalPanel();
				verticalPanel.add(hp);
			}
			
			Anchor anch = new Anchor(word);
			// TODO: anch.addClickHandler(handler);
			
			hp.add(anch);
			hp.setCellWidth(anch, FIELD_SIZE);
			
			cnt++;
		}
	}

}
