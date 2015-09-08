package kawahara;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import kawahara.db.AnswerDAO;
import kawahara.db.QuestionDAO;
import kawahara.db.UserDAO;
import kawahara.models.AnswerModel;
import kawahara.models.CachedSearchModel;
import kawahara.models.QuestionModel;
import kawahara.models.UserModel;
import kawahara.services.AnswerService;
import kawahara.services.QuestionService;
import kawahara.services.SearchService;
import kawahara.services.UserService;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class MyServletContextListener implements ServletContextListener {
	ServletContext context;
	@Override
	public void contextInitialized(ServletContextEvent sce) {
//		addUsers(us);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		context = sce.getServletContext();
		System.out.println("YO DUDE: Context destroyed");
	}
	
}