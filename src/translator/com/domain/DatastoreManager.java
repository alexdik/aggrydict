package translator.com.domain;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public class DatastoreManager {
	private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	private DatastoreManager() {
	}

	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}
}
