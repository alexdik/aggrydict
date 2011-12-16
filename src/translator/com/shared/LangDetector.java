package translator.com.shared;

public class LangDetector {
	private static final int ENG_LOW = 65;
	private static final int ENG_HIGH = 122;
	
	private static final int RUS_LOW = 1040;
	private static final int RUS_HIGH = 1103;
	
	private static final char SPACE = ' ';
	
	private static final String RU = "ru";
	private static final String EN = "en";
	
	public static String[] getLang(String word) {
		char[] ltrs = word.toCharArray();
		char firstChar = ltrs[0];
		
		if (ENG_LOW <= firstChar && firstChar <= ENG_HIGH) {
			if (checkAllSame(ltrs, ENG_LOW, ENG_HIGH)) {
				return new String[]{EN, RU};
			}
		} else if (RUS_LOW <= firstChar && firstChar <= RUS_HIGH) {
			if (checkAllSame(ltrs, RUS_LOW, RUS_HIGH)) {
				return new String[]{RU, EN};
			}
		}
		return null;
	}
	
	public static String[] getLangByFirstLetter(String word) {
		char[] ltrs = word.toCharArray();
		for (char chr : ltrs) {
			if (ENG_LOW <= chr && chr <= ENG_HIGH) {
				return new String[]{EN, RU};
			} else if (RUS_LOW <= chr && chr <= RUS_HIGH) {
				return new String[]{RU, EN};
			}
		}
		return null;
	}

	private static boolean checkAllSame(char[] ltrs, int low, int high) {
		for (char c : ltrs) {
			if (c != SPACE && !(low <= c && c <= high)) {
				return false;
			}
		}
		return true;
	}
}