package translator.com.domain;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;


public class DatastoreHelper {

	public static String creatUser(String userId, String userName) {
		PersistenceManager pm = DatastoreManager.get().getPersistenceManager();

		String userSecret = null;
		try {
			UUID uid = UUID.randomUUID();
			userSecret = uid.toString();
			
//			boolean keyExists = true;
//			while (keyExists) {
//				UUID uuid = UUID.randomUUID();
//				userSecret = uuid.toString();
//				UserData user = pm.getObjectById(UserData.class, userSecret);
//
//				keyExists = null != user;
//			}

			UserData user = new UserData(userSecret, userId, userName, null);
			pm.makePersistent(user);
		} finally {
			pm.close();
		}
		
		return userSecret;
	}
	
	public static UserData getUser(String userId) {
		PersistenceManager pm = DatastoreManager.get().getPersistenceManager();
		
		try {
			Query query = pm.newQuery(String.format("select from translator.com.domain.UserData where userId == '%s'", userId));
			@SuppressWarnings("unchecked")
			List<UserData> res = (List<UserData>) query.execute();

			if (res.size() > 0) {
				return res.get(0);
				
			} else {
				return null;
			}
		} finally {
			pm.close();
		}
	}
	
	public static Set<String> getWords(String userSecret){
		PersistenceManager pm = DatastoreManager.get().getPersistenceManager();
		
		try {
			Query query = pm.newQuery(String.format("select from translator.com.domain.UserData where userSecret == '%s'", userSecret));
			@SuppressWarnings("unchecked")
			List<UserData> res = (List<UserData>) query.execute();
			
			if (res.size() > 0) {
				return res.get(0).getWords();
			} else {
				return null;
			}
		} finally {
			pm.close();
		}
	}
	
	public static synchronized boolean addWord(String userSecret, String word){
		PersistenceManager pm = DatastoreManager.get().getPersistenceManager();
		
		try {
			Query query = pm.newQuery(String.format("select from translator.com.domain.UserData where userSecret == '%s'", userSecret));
			@SuppressWarnings("unchecked")
			List<UserData> res = (List<UserData>) query.execute();
			
			if (res.size() > 0) {
				UserData user = res.get(0);
				Set<String> list = user.getWords();
				list.add(word);
				pm.makePersistent(user);
				pm.flush();
				
				return true;
			} else {
				return false;
			}
		} finally {
			pm.close();
		}
	}
}
