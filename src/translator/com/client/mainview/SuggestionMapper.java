package translator.com.client.mainview;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SuggestionMapper {
	private static final String SUGGEST_PATTERN = "SuggestCard";
	private static final int SUGGEST_PATTERN_LEN = 50;
	private static final String FIELD_SIZE = "190";
	private static final int ELEMS_PER_ROW = 3;

	public static void addSuggestList(VerticalPanel textPanel, String data, ClickHandler handler) {
		Label label = new Label("Possible you mean:");
		label.setHeight("25px");
		
		textPanel.clear();
		textPanel.add(label);
		
		String str = null;
		HorizontalPanel hp = null;
		String arr[] = data.split("\\$");
		
		for (int cnt = 1; cnt < arr.length; cnt++) {
			str = arr[cnt];
			boolean needNewPanel = cnt % ELEMS_PER_ROW == 1;

			if (needNewPanel) {
				hp = new HorizontalPanel();
				textPanel.add(hp);
			}

			Anchor anch = new Anchor(str.replaceAll("\\$", ""));
			anch.addClickHandler(handler);

			hp.add(anch);
			hp.setCellWidth(anch, FIELD_SIZE);
		}
	}

	public static boolean isSuggestData(String data) {
		return data.substring(0, SUGGEST_PATTERN_LEN).contains(SUGGEST_PATTERN);
	}
}
