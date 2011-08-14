package translator.com.client.mainview;

import java.util.Date;

import translator.com.client.resources.Resources;
import translator.com.client.rpc.TranslatorService;
import translator.com.client.rpc.TranslatorServiceAsync;
import translator.com.client.util.TimeoutRequestBuilder;
import translator.com.shared.Converter;
import translator.com.shared.Engines;

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
	private Date lastAccesTime = new Date();
	
	private TranslatorServiceAsync translatorService = GWT.create(TranslatorService.class);
	private ClickHandler clickHandler = new ClickHandler(){
		public void onClick(ClickEvent event) {
			Anchor anch = (Anchor) event.getSource();
			translateBox.setText(anch.getText());
			MainView.this.onClick(null);
		}
	};

	interface MainViewUiBinder extends UiBinder<Widget, MainView> {
	}

	public MainView() {
		engineList = new ListBox();
		engineList.addItem(Engines.abbyy.toString(), Engines.abbyy.toString());
		engineList.addItem(Engines.google.toString(), Engines.google.toString());
		
		progressIndicator = new Image(RESOURCES.progress());
		
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
	void onClick(ClickEvent e) {
		if (translateBox.getText().length() < 1) {
			return;
		}
		
		formatTranslateBox();
		
		progressPanel.setVisible(true);
		progressIndicator.setVisible(true);
		textPanel.setVisible(false);
		
		// Setting focus back to translateBox so user can avoid using mouse 
		translateBox.setFocus(true);
		
		final AsyncCallback<String> callback = new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				progressIndicator.setVisible(false);
				progressPanel.setVisible(false);
				
				textPanel.setVisible(true);
				html.setHTML("Failed, please retry");
			}
			
			public void onSuccess(String result) {
				progressIndicator.setVisible(false);
				progressPanel.setVisible(false);
				textPanel.setVisible(true);
				
				if (!SuggestionMapper.isSuggestData(result)) {
					html.setHTML(result);
					textPanel.clear();
					textPanel.add(html);
				} else {
					SuggestionMapper.addSuggestList(textPanel, result, clickHandler);
				}
			}
		};
		
		// Simple DOS defense
		Date currentTime = new Date();
		if (currentTime.getTime() < lastAccesTime.getTime() + 1000) {
			Timer timer = new Timer() {
				public void run() {
					translatorService.translate(Converter.toHex(translateBox.getText()), engineList.getItemText(engineList.getSelectedIndex()), callback);
				}
			};
			timer.schedule(1500);
		} else {
			translatorService.translate(Converter.toHex(translateBox.getText()), engineList.getItemText(engineList.getSelectedIndex()), callback);
		}
		
		lastAccesTime = currentTime;
	}

	public void setText(String text) {
		trButton.setText(text);
	}

	public String getText() {
		return trButton.getText();
	}
	
	private void formatTranslateBox(){
		translateBox.setText(translateBox.getText().trim());
	}
}
