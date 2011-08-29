package translator.com.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.jdo.PersistenceManager;

import translator.com.shared.DictConstants;

public class DatastoreHelper {
	public static String creatUser(String userId, String userName) {
		PersistenceManager pm = DatastoreManager.get().getPersistenceManager();

		String userSecret = null;
		try {
			UUID uuid = UUID.randomUUID();
			userSecret = uuid.toString() + System.currentTimeMillis();

			UserInfo user = new UserInfo(userId, userSecret, userName);
			UserDict userdDict = new UserDict(userSecret, new HashSet<String>());
			pm.makePersistent(user);
			pm.makePersistent(userdDict);
		} finally {
			pm.close();
		}

		return userSecret;
	}

	public static UserInfo getUser(String userId) {
		PersistenceManager pm = DatastoreManager.get().getPersistenceManager();

		try {
			UserInfo user = pm.getObjectById(UserInfo.class, userId);
			return user;
		} catch (javax.jdo.JDOObjectNotFoundException e) {
			return null;
		} finally {
			pm.close();
		}
	}

	public static Set<String> getWords(String userSecret, String filter) {
		PersistenceManager pm = DatastoreManager.get().getPersistenceManager();

		try {
			UserDict userDict = pm.getObjectById(UserDict.class, userSecret);
			Set<String> allWords = userDict.getWords();
			
			if (null == filter) {
				Set<String> res = new TreeSet<String>();
				for (String word : allWords) {
					res.add(word);
				}
				return res;
			} else {
				Set<String> outputWords = new TreeSet<String>();
				for (String word : allWords) {
					if (word.contains(filter)) {
						outputWords.add(word);
					}
				}
				return outputWords;
			}
		} catch (javax.jdo.JDOObjectNotFoundException e) {
			return null;
		} finally {
			pm.close();
		}
	}

	public static boolean addWord(String userSecret, String word) {
		if (word.length() > DictConstants.MAX_WORD_LEN) {
			word = word.substring(0, DictConstants.MAX_WORD_LEN);
		}
		
		PersistenceManager pm = DatastoreManager.get().getPersistenceManager();

		try {
			UserDict userDict = pm.getObjectById(UserDict.class, userSecret);
			userDict.getWords().add(word);
			return true;
		} catch (javax.jdo.JDOObjectNotFoundException e) {
			return false;
		} finally {
			pm.close();
		}
	}
	
	public static boolean removeWord(String userSecret, String word) {
		PersistenceManager pm = DatastoreManager.get().getPersistenceManager();

		try {
			UserDict userDict = pm.getObjectById(UserDict.class, userSecret);
			Set<String> words = userDict.getWords();
			words.remove(word);
			
			return true;
		} catch (javax.jdo.JDOObjectNotFoundException e) {
			return false;
		} finally {
			pm.close();
		}
	}
	
	public static boolean hasWord(String userSecret, String word) {
		PersistenceManager pm = DatastoreManager.get().getPersistenceManager();

		try {
			UserDict userDict = pm.getObjectById(UserDict.class, userSecret);
			Set<String> words = userDict.getWords();

			return words.contains(word);
		} catch (javax.jdo.JDOObjectNotFoundException e) {
			return false;
		} finally {
			pm.close();
		}
	}
}
