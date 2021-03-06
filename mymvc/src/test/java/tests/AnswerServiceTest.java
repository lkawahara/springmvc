package tests;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.Assert;
import kawahara.db.AnswerDAO;
import kawahara.models.AnswerModel;
import kawahara.models.QuestionModel;
import kawahara.services.AnswerService;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "/springapp-servlet.xml" })
public class AnswerServiceTest {
	@Autowired
	private SessionFactory sessionFactory;

	public AnswerServiceTest() {
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	ExecutorService es = Executors.newFixedThreadPool(1);
	AnswerDAO answerDAO;
	QuestionModel newQ = new QuestionModel("test username", "test heading", "test question");

	//@Before
	public void setUp() {
		answerDAO = new AnswerDAO(sessionFactory);
	}

	//@Test
	public void testCreate() {
		AnswerService service = new AnswerService(answerDAO);
		AnswerModel model = new AnswerModel(newQ, "test answer", "username");
		service.add(model);

		Assert.assertTrue(service.contains(model));
	}

	//@Test
	public void testReadItemExists() {
		AnswerService service = new AnswerService(answerDAO);
		
		AnswerModel testModel = new AnswerModel(newQ, "test answer2", "username2");
		service.add(testModel);
		Assert.assertTrue(service.get(testModel.getId()) == testModel);
	}

	//@Test
	public void testReadItemDoesntExists() {
		AnswerService service = new AnswerService(answerDAO);
		AnswerModel testModel = new AnswerModel(newQ, "test answer", "username");
		Assert.assertTrue(service.get(testModel.getId()) == null);
	}

	//@Test
	public void testUpdateItemExists() {

		AnswerService service = new AnswerService(answerDAO);
		String initialUserName = "test username";
		String initialValue = "this is a test answer.";
		AnswerModel testModel = new AnswerModel(newQ, initialValue,
				initialUserName);
		service.add(testModel);
		testModel.setAnswer("Different Value");
		service.update(testModel);
		long id = testModel.getId();

		Assert.assertFalse(service.get(id).getAnswer().equals(initialValue));

	}

	//@Test
	public void testUpdateItemDoesntExists() {

		AnswerService service = new AnswerService(answerDAO);
		Assert.assertFalse(service.update(new AnswerModel(newQ, "test answer",
				"username")));

	}

	//@Test
	public void testDeleteItemExists() {

		AnswerService service = new AnswerService(answerDAO);
		AnswerModel testModel = new AnswerModel(newQ, "test answer", "username");
		service.add(testModel);
		long id = testModel.getId();
		service.delete(id);
		Assert.assertFalse(service.contains(testModel));

	}

	//@Test
	public void testDeleteItemDoesntExists() {

		AnswerService service = new AnswerService(answerDAO);
		AnswerModel testModel = new AnswerModel(newQ, "test answer", "username");
		long id = testModel.getId();
		try{ 
			service.delete(id);
			Assert.assertTrue(false);
		}catch(IllegalArgumentException e){
			Assert.assertEquals(e.getMessage(), "attempt to create delete event with null entity");
		}
		
	}
}
