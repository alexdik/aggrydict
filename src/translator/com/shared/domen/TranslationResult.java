package translator.com.shared.domen;

import java.io.Serializable;

public class TranslationResult implements Serializable {
	private static final long serialVersionUID = 5765781060150566917L;
	private String text;
	private boolean isInFavourite;

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public boolean isInFavourite() {
		return isInFavourite;
	}
	
	public void setInFavourite(boolean isInFavourite) {
		this.isInFavourite = isInFavourite;
	}
}
