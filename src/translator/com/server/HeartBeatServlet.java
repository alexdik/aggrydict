package translator.com.server;

import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import translator.com.domain.DatastoreHelper;
import translator.com.domain.UserData;

public class HeartBeatServlet extends HttpServlet {
	private static final long serialVersionUID = 1227368803997804353L;
	private static final Logger LOG  = Logger.getLogger(HeartBeatServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		LOG.info("HeartBeatServlet.doGet");
		
		UserData user = DatastoreHelper.getUser("123");
		if (null == user) {
			DatastoreHelper.creatUser("123", "Ivan");
			user = DatastoreHelper.getUser("123");
		}
		
		String id = user.getUserSecret();
		System.out.println("id: " + id);
		System.out.println("name: " + user.getUserName());
		LOG.info("id: " + id);
		LOG.info("name: " + user.getUserName());
		
		System.out.println(DatastoreHelper.addWord(id, "test1"));
		System.out.println(DatastoreHelper.addWord(id, "test2"));
		System.out.println(DatastoreHelper.addWord(id, "test3"));
		System.out.println(DatastoreHelper.addWord(id, "test4"));
		System.out.println(DatastoreHelper.addWord(id, "test5"));
		System.out.println(DatastoreHelper.addWord(id, "test6"));
		System.out.println(DatastoreHelper.addWord(id, "test7"));
		System.out.println(DatastoreHelper.addWord(id, "test8"));
		System.out.println(DatastoreHelper.addWord(id, "test9"));
		System.out.println(DatastoreHelper.addWord(id, "test0"));
		
		
		
		Set<String> words = DatastoreHelper.getWords(id);
		System.out.println("words:");
		LOG.info("words:");
		for (String string : words) {
			System.out.println(string);
			LOG.info(string);
		}
	}
}
