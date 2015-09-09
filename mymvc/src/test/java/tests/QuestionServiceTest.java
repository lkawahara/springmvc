package tests;

import junit.framework.*;
import kawahara.db.QuestionDAO;
import kawahara.models.QuestionModel;
import kawahara.services.QuestionService;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "/springapp-servlet.xml" })
public class QuestionServiceTest {
	@Autowired
	private SessionFactory sessionFactory;

	public QuestionServiceTest() {
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	//@Test
	public void testCreate() {
		QuestionService service = new QuestionService(new QuestionDAO(sessionFactory));
		QuestionModel testModel = new QuestionModel("test user", "Heading", "this is a test question.");
		service.add(testModel);
		Assert.assertTrue(service.contains(testModel));
	}

	//@Test
	public void testReadItemExists() {
		QuestionService service = new QuestionService(new QuestionDAO(sessionFactory));
		QuestionModel testModel = new QuestionModel("test user", "Heading", "this is a test question.");
		service.add(testModel);
		Assert.assertTrue(service.get(testModel.getId()) == testModel);
	}
	
	//@Test
	public void testReadItemDoesntExists() {
		QuestionService service = new QuestionService(new QuestionDAO(sessionFactory));
		QuestionModel testModel = new QuestionModel("test user", "Heading", "this is a test question.");
		Assert.assertTrue(service.get(testModel.getId()) == null);
	}
	
	//@Test
	public void testUpdateItemExists() {
		QuestionService service = new QuestionService(new QuestionDAO(sessionFactory));
		String initialHeading = "test Heading";
		String initialValue = "this is a test question.";
		QuestionModel testModel = new QuestionModel("test user", "Heading", initialValue);
		service.add(testModel);
		testModel.setHeading("Different Heading");
		testModel.setQuestion("Different Value");
		service.update(testModel);
		long id = testModel.getId();
		
		Assert.assertFalse(service.get(id).getHeading().equals(initialHeading));
		Assert.assertFalse(service.get(id).getQuestion().equals(initialValue));
	}
	
	//@Test
	public void testUpdateItemDoesntExists() {
		QuestionService service = new QuestionService(new QuestionDAO(sessionFactory));
		String initialHeading = "test Heading";
		String initialValue = "this is a test question.";
		QuestionModel testModel = new QuestionModel("test user", "Heading", initialValue);
		
		testModel.setHeading("Different Heading");
		testModel.setQuestion("Different Value");
		Assert.assertFalse(service.update(testModel));
	}
	
	//@Test
	public void testDeleteItemExists() {
		QuestionService service = new QuestionService(new QuestionDAO(sessionFactory));
		QuestionModel testModel = new QuestionModel("test user", "Heading", "this is a test question.");
		service.add(testModel);
		long id = testModel.getId();
		service.delete(id);
		Assert.assertFalse(service.contains(testModel));
	}

	//@Test
	public void testDeleteItemDoesntExists() {
		QuestionService service = new QuestionService(new QuestionDAO(sessionFactory));
		QuestionModel testModel = new QuestionModel("test user", "Heading", "this is a test question.");
		long id = testModel.getId();
		service.delete(id);
		Assert.assertFalse(service.contains(testModel));
	}
	
}
