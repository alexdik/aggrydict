package translator.com.server;

import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HeartBeatServlet extends HttpServlet {
	private static final long serialVersionUID = 1227368803997804353L;
	private static final Logger LOG  = Logger.getLogger(HeartBeatServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {}
		LOG.info("HeartBeatServlet.doGet");
	}
}
