package tests;

import junit.framework.*;
import kawahara.db.QuestionDAO;
import kawahara.models.QuestionModel;
import kawahara.services.QuestionService;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class QuestionServiceTest {
	
	@Test
	public void testCreate() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		QuestionService service = new QuestionService(new QuestionDAO(sessionFactory));
		QuestionModel testModel = new QuestionModel("test user", "Heading", "this is a test question.");
		service.add(testModel);
		Assert.assertTrue(service.contains(testModel));
	}

	@Test
	public void testReadItemExists() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		QuestionService service = new QuestionService(new QuestionDAO(sessionFactory));
		QuestionModel testModel = new QuestionModel("test user", "Heading", "this is a test question.");
		service.add(testModel);
		Assert.assertTrue(service.get(testModel.getId()) == testModel);
	}
	
	@Test
	public void testReadItemDoesntExists() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		QuestionService service = new QuestionService(new QuestionDAO(sessionFactory));
		QuestionModel testModel = new QuestionModel("test user", "Heading", "this is a test question.");
		Assert.assertTrue(service.get(testModel.getId()) == null);
	}
	
	@Test
	public void testUpdateItemExists() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
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
	
	@Test
	public void testUpdateItemDoesntExists() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		QuestionService service = new QuestionService(new QuestionDAO(sessionFactory));
		String initialHeading = "test Heading";
		String initialValue = "this is a test question.";
		QuestionModel testModel = new QuestionModel("test user", "Heading", initialValue);
		
		testModel.setHeading("Different Heading");
		testModel.setQuestion("Different Value");
		Assert.assertFalse(service.update(testModel));
	}
	
	@Test
	public void testDeleteItemExists() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		QuestionService service = new QuestionService(new QuestionDAO(sessionFactory));
		QuestionModel testModel = new QuestionModel("test user", "Heading", "this is a test question.");
		service.add(testModel);
		long id = testModel.getId();
		service.delete(id);
		Assert.assertFalse(service.contains(testModel));
	}

	@Test
	public void testDeleteItemDoesntExists() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		QuestionService service = new QuestionService(new QuestionDAO(sessionFactory));
		QuestionModel testModel = new QuestionModel("test user", "Heading", "this is a test question.");
		long id = testModel.getId();
		service.delete(id);
		Assert.assertFalse(service.contains(testModel));
	}
	
}
