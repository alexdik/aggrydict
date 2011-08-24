package translator.com.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.jdo.PersistenceManager;

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

	public static synchronized UserInfo getUser(String userId) {
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

	public static synchronized Set<String> getWords(String userSecret) {
		PersistenceManager pm = DatastoreManager.get().getPersistenceManager();

		try {
			UserDict userDict = pm.getObjectById(UserDict.class, userSecret);
			return userDict.getWords();
		} catch (javax.jdo.JDOObjectNotFoundException e) {
			return null;
		} finally {
			pm.close();
		}
	}

	public static synchronized boolean addWord(String userSecret, String word) {
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
}
