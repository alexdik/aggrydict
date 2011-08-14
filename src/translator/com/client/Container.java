package translator.com.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class Container extends Composite {
	private static ContainerUiBinder uiBinder = GWT.create(ContainerUiBinder.class);
	@UiField(provided=true) Image imageLogo;
//	@UiField CellList<String> cellList;
//	@UiField Label header;

	interface ContainerUiBinder extends UiBinder<Widget, Container> {
	}
	
	/*@UiFactory CellList<String> makeCellList() {
		TextCell textCell = new TextCell();
		final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
		
		CellList<String> cellList = new CellList<String>(textCell){
			public void render(Context context, String value, SafeHtmlBuilder sb) {
			}
		};
		cellList.setSelectionModel(selectionModel);
		
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				String selected = selectionModel.getSelectedObject();
				if (selected != null) {
					header.setText(selected);
				}
			}
	    });
		
	    return cellList;
	}*/

	public Container() {
		imageLogo = new Image("http://code.google.com/appengine/images/appengine-noborder-120x30.gif");
		initWidget(uiBinder.createAndBindUi(this));
//		final List<String> DAYS = Arrays.asList("Translator", "Dictionary");

//		cellList.setRowCount(DAYS.size(), true);
//		cellList.setRowData(0, DAYS);
	}
}
