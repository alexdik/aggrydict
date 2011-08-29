package translator.com.client.mainview;

import java.util.Date;

import translator.com.client.resources.Resources;
import translator.com.client.rpc.TranslatorService;
import translator.com.client.rpc.TranslatorServiceAsync;
import translator.com.client.util.TimeoutRequestBuilder;
import translator.com.client.util.UserUtil;
import translator.com.shared.Converter;
import translator.com.shared.Engines;
import translator.com.shared.domen.TranslationResult;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainView extends Composite implements HasText {
	private static MainViewUiBinder uiBinder = GWT.create(MainViewUiBinder.class);
	private static final Resources RESOURCES = GWT.create(Resources.class);
	@UiField Button trButton;
	@UiField TextBox translateBox;
	@UiField HTML html;
	@UiField(provided=true) Image progressIndicator;
	@UiField(provided=true) ListBox engineList;
	@UiField VerticalPanel textPanel;
	@UiField HorizontalPanel progressPanel;
	@UiField(provided=true) Image addFavouriteController;
	@UiField(provided=true) Image remFavouriteController;
	@UiField(provided=true) Image spinFavouriteController;
	private Date lastAccesTime = new Date();
	private String translatedWord;
	
	interface MainViewUiBinder extends UiBinder<Widget, MainView> {
	}
	
	private TranslatorServiceAsync translatorService = GWT.create(TranslatorService.class);
	
	private ClickHandler clickSuggestHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			Anchor anch = (Anchor) event.getSource();
			translateBox.setText(anch.getText());
			MainView.this.onClick(null);
		}
	};
	
	private class FavouriteHandlers {
		private ClickHandler clickAddFavourite = new ClickHandler() {
			final AsyncCallback<Boolean> addWordCallback = new AsyncCallback<Boolean>() {
				public void onFailure(Throwable caught) {
					spinFavouriteController.setVisible(false);
					addFavouriteController.setVisible(true);
				}
	
				public void onSuccess(Boolean result) {
					spinFavouriteController.setVisible(false);
					addFavouriteController.setVisible(false);
					remFavouriteController.setVisible(true);
				}
			};
			
			public void onClick(ClickEvent event) {
				if (translatedWord != null) {
					clickAction();
					translatorService.addWord(UserUtil.getUserSecret(), translatedWord, addWordCallback);
				}
			}
		};
		
		private ClickHandler clickRemFavourite = new ClickHandler() {
			final AsyncCallback<Boolean> removeWordCallback = new AsyncCallback<Boolean>() {
				public void onFailure(Throwable caught) {
					spinFavouriteController.setVisible(false);
					remFavouriteController.setVisible(true);
				}
	
				public void onSuccess(Boolean result) {
					spinFavouriteController.setVisible(false);
					remFavouriteController.setVisible(false);
					addFavouriteController.setVisible(true);
				}
			};
			
			public void onClick(ClickEvent event) {
				if (translatedWord != null) {
					clickAction();
					translatorService.removeWord(UserUtil.getUserSecret(), translatedWord, removeWordCallback);
				}
			}
		};
		
		private void clickAction() {
			addFavouriteController.setVisible(false);
			remFavouriteController.setVisible(false);
			spinFavouriteController.setVisible(true);
		}
	}

	public MainView() {
		engineList = new ListBox();
		engineList.addItem(Engines.abbyy.toString(), Engines.abbyy.toString());
		engineList.addItem(Engines.google.toString(), Engines.google.toString());
		
		progressIndicator = new Image(RESOURCES.progress());
		
		FavouriteHandlers favouriteHandlers = new FavouriteHandlers();
		
		addFavouriteController = new Image(RESOURCES.add());
		addFavouriteController.addClickHandler(favouriteHandlers.clickAddFavourite);
		addFavouriteController.setVisible(false);
		
		remFavouriteController = new Image(RESOURCES.delete());
		remFavouriteController.addClickHandler(favouriteHandlers.clickRemFavourite);
		remFavouriteController.setVisible(false);
		
		spinFavouriteController = new Image(RESOURCES.smallprogress());
		spinFavouriteController.setVisible(false);
		
		initWidget(uiBinder.createAndBindUi(this));
		
		textPanel.setVisible(false);
		progressPanel.setVisible(false);
		
		((ServiceDefTarget) translatorService).setRpcRequestBuilder(new TimeoutRequestBuilder()); 
		
		translateBox.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
					onClick(null);
				}
			}
		});
		
		engineList.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				onClick(null);
			}
		});
	}
	
	@Override
	protected void onLoad() {
		translateBox.setFocus(true);
		super.onLoad();
	}
	
	@UiHandler("trButton")
	public void onClick(ClickEvent e) {
		if (translateBox.getText().length() < 1) {
			return;
		}
		
		formatTranslateBox();
		
		progressPanel.setVisible(true);
		progressIndicator.setVisible(true);
		textPanel.setVisible(false);
		
		// Setting focus back to translateBox so user can avoid using mouse 
		translateBox.setFocus(true);
		
		final AsyncCallback<TranslationResult> translateCallback = new AsyncCallback<TranslationResult>() {
			public void onFailure(Throwable caught) {
				progressIndicator.setVisible(false);
				progressPanel.setVisible(false);
				
				textPanel.setVisible(true);
				html.setHTML("Failed, please retry");
				hideFavControls();
			}
			
			public void onSuccess(TranslationResult result) {
				progressIndicator.setVisible(false);
				progressPanel.setVisible(false);
				textPanel.setVisible(true);
				
				if (!SuggestionMapper.isSuggestData(result.getText())) {
					html.setHTML(result.getText());
					textPanel.clear();
					textPanel.add(html);
					translatedWord = translateBox.getText();
					if(!result.getText().equals("No word found") && UserUtil.isAuthorized()) {
						addFavouriteController.setVisible(!result.isInFavourite());
						remFavouriteController.setVisible(result.isInFavourite());
					} else {
						hideFavControls();
					}
				} else {
					SuggestionMapper.addSuggestList(textPanel, result.getText(), clickSuggestHandler);
					hideFavControls();
					translatedWord = null;
				}
			}
		};
		
		// Simple DOS defense
		Date currentTime = new Date();
		final String userSecret = UserUtil.getUserSecret();
		if (currentTime.getTime() < lastAccesTime.getTime() + 1000) {
			Timer timer = new Timer() {
				public void run() {
					translatorService.translate(Converter.toHex(translateBox.getText()), engineList.getItemText(engineList.getSelectedIndex()), userSecret, translateCallback);
				}
			};
			timer.schedule(1500);
		} else {
			translatorService.translate(Converter.toHex(translateBox.getText()), engineList.getItemText(engineList.getSelectedIndex()), userSecret, translateCallback);
		}
		
		lastAccesTime = currentTime;
	}

	public void setText(String text) {
		translateBox.setText(text);
	}

	public String getText() {
		return translateBox.getText();
	}
	
	private void formatTranslateBox(){
		translateBox.setText(translateBox.getText().trim());
	}
	
	public String getTranslatedWord() {
		return translatedWord;
	}
	
	public void setWordNotExists(String word) {
		if (translatedWord != null && translatedWord.equals(word)) {
			remFavouriteController.setVisible(false);
			addFavouriteController.setVisible(true);
		}
	}

	private void hideFavControls() {
		addFavouriteController.setVisible(false);
		remFavouriteController.setVisible(false);
		spinFavouriteController.setVisible(false);
	}
}
