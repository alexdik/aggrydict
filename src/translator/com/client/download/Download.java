package translator.com.client.download;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Download extends Composite {
	private static DownloadUiBinder uiBinder = GWT.create(DownloadUiBinder.class);

	interface DownloadUiBinder extends UiBinder<Widget, Download> {
	}

	public Download() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Download(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
