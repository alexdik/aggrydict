package translator.com.client.favourite;

import java.util.Set;

import translator.com.client.resources.Resources;
import translator.com.client.rpc.TranslatorService;
import translator.com.client.rpc.TranslatorServiceAsync;
import translator.com.client.util.TimeoutRequestBuilder;
import translator.com.client.util.TranslateAction;
import translator.com.client.util.UserUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Favourite extends Composite {
	private static final String FIELD_SIZE = "170";
	private static final int ELEMS_PER_ROW = 3;
	private static final String FILTER_TEXT = "Filter your words";
	private static final Resources RESOURCES = GWT.create(Resources.class);

	private static FavouriteUiBinder uiBinder = GWT.create(FavouriteUiBinder.class);
	@UiField VerticalPanel verticalPanel;
	@UiField(provided=true) Image progressIndicator;
	@UiField HorizontalPanel progressPanel;
	@UiField(provided=true) TextBox filterText;
	@UiField HTMLPanel favouriteContainer;
	@UiField Label loginRequest;
	private TranslatorServiceAsync translatorService = GWT.create(TranslatorService.class);
	private TranslateAction translateAction;
	private String oldFilterValue;

	interface FavouriteUiBinder extends UiBinder<Widget, Favourite> {
	}
	
	public void setTranslateAction(TranslateAction translateAction) {
		this.translateAction = translateAction;
	}
	
	public void renderFavourite(boolean redrawEffectEnabled) {
		if(!UserUtil.isAuthorized()) {
			return;
		}
		
		AsyncCallback<Set<String>> getWordsCallback = new AsyncCallback<Set<String>>(){
			public void onFailure(Throwable caught) {
				progressPanel.setVisible(false);
				verticalPanel.setVisible(true);
				
				HTML failureDescr = new HTML("Failed, please retry");
				verticalPanel.clear();
				verticalPanel.add(failureDescr);
			}

			public void onSuccess(Set<String> result) {
				renderRecords(result);
				progressPanel.setVisible(false);
				verticalPanel.setVisible(true);
			}
		};
		
		if (redrawEffectEnabled) {
			progressPanel.setVisible(true);
			verticalPanel.setVisible(false);
		}
		translatorService.getWords(UserUtil.getUserSecret(), getFilter(), getWordsCallback);
	}
	
	FocusHandler focusFilterHandler = new FocusHandler() {
		public void onFocus(FocusEvent event) {
			String newFilterValue = filterText.getText();
			if (newFilterValue.equals(FILTER_TEXT)) {
				filterText.removeStyleName("emptyTextBox");
				filterText.setText("");
			}
		}
	};
	
	BlurHandler blurFilterHandler = new BlurHandler() {
		public void onBlur(BlurEvent event) {
			String newFilterValue = filterText.getText();
			if (newFilterValue.length() == 0 || newFilterValue.equals(FILTER_TEXT)) {
				filterText.addStyleName("emptyTextBox");
				filterText.setText(FILTER_TEXT);
			}
		}
	};
	
	KeyUpHandler filterHandler = new KeyUpHandler() {
		public void onKeyUp(KeyUpEvent event) {
			String newFilterValue = filterText.getText();
			if (!newFilterValue.equals(oldFilterValue) && !newFilterValue.equals(FILTER_TEXT)) {
				renderFavourite(true);
				oldFilterValue = newFilterValue;
			}
		}
	};
	
	private ClickHandler removeHandler = new ClickHandler() {
		private String wordToRemove = null;
		private Image imageRemoved = null;
		
		AsyncCallback<Boolean> removeCallback = new AsyncCallback<Boolean>(){
			public void onFailure(Throwable caught) {
				imageRemoved.setResource(RESOURCES.smalldelete());
			}

			public void onSuccess(Boolean result) {
				renderFavourite(false);
				translateAction.setWordNotExists(wordToRemove);
			}
		};
		
		public void onClick(ClickEvent event) {
			if (!UserUtil.isAuthorized()) {
				return;
			}
			
			imageRemoved = (Image) event.getSource();
			imageRemoved.setResource(RESOURCES.smallprogress());
			wordToRemove = imageRemoved.getAltText();
			translatorService.removeWord(UserUtil.getUserSecret(), wordToRemove, removeCallback);
		}
	};
	
	private ClickHandler translateHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			Anchor anch = (Anchor) event.getSource();
			translateAction.translateText(anch.getText());
		}
	};

	public Favourite() {
		((ServiceDefTarget) translatorService).setRpcRequestBuilder(new TimeoutRequestBuilder()); 
		progressIndicator = new Image(RESOURCES.progress());
		filterText = new TextBox();
		filterText.setText(FILTER_TEXT);
		filterText.addStyleName("emptyTextBox");
		filterText.addKeyUpHandler(filterHandler);
		filterText.addFocusHandler(focusFilterHandler);
		filterText.addBlurHandler(blurFilterHandler);
		
		initWidget(uiBinder.createAndBindUi(this));
		
		if (UserUtil.isAuthorized()) {
			favouriteContainer.setVisible(true);
		} else {
			loginRequest.setVisible(true);
		}
	}
	
	public void renderRecords(Set<String> words) {
		verticalPanel.clear();

		if (words != null) {
			if (words.size() == 0) {
				Label label = new Label("No words found");
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
					hp.setStyleName("favouriteLine");
				}
				
				Anchor anch = new Anchor(word);
				anch.addClickHandler(translateHandler);
				
				Image remFavouriteController = new Image(RESOURCES.smalldelete());
				remFavouriteController.setAltText(word);
				remFavouriteController.addClickHandler(removeHandler);
				remFavouriteController.setStyleName("favouriteLineRemoveImg");
				
				hp.add(remFavouriteController);
				hp.add(anch);
				hp.setCellWidth(anch, FIELD_SIZE);
				
				cnt++;
			}
		}
	}

	private String getFilter() {
		String filterValue = filterText.getText();
		
		if (null == filterValue || filterValue.length() == 0 || filterValue.equals(FILTER_TEXT)){
			return null;
		} 
		
		return filterValue;
	}
}
