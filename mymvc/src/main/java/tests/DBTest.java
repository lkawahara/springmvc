package tests;

import junit.framework.*;
import kawahara.models.QuestionModel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/springapp-servlet.xml" })
public class DBTest {
	@Autowired
	private SessionFactory sessionFactory;

	public DBTest() {
		// this.sessionFactory = sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	//@Test
	public void testAddGet() {
		QuestionModel model = new QuestionModel("username", "heading", "value");
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		long id = (Long) session.save(model);
		session.getTransaction().commit();
		session.close();

		Session sess2 = sessionFactory.openSession();
		QuestionModel testGet = (QuestionModel) sess2.get(QuestionModel.class,
				id);

		sess2.close();
		Assert.assertEquals(model, testGet);
	}

	//@Test
	public void testUpdate() {
		String newHeading = "newHeading", newUsername = "newUsername", newValue = "newValue";

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		QuestionModel testModel = new QuestionModel("username2", "heading2",
				"value2");
		long id = (Long) session.save(testModel);
		session.getTransaction().commit();
		session.close();

		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();
		QuestionModel toUpdate = (QuestionModel) session2.get(
				QuestionModel.class, id);

		toUpdate.setHeading(newHeading);
		toUpdate.setUserName(newUsername);
		toUpdate.setQuestion(newValue);

		session2.save(toUpdate);
		session2.getTransaction().commit();
		session2.close();

		Session session3 = sessionFactory.openSession();
		session3.beginTransaction();
		QuestionModel shouldBeUpdated = (QuestionModel) session3.get(
				QuestionModel.class, id);
		session3.close();

		Assert.assertEquals(shouldBeUpdated.getHeading(), newHeading);
		Assert.assertEquals(shouldBeUpdated.getName(), newUsername);
		Assert.assertEquals(shouldBeUpdated.getQuestion(), newValue);
	}

	//
	@Test
	public void testDelete() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		QuestionModel toDelete = new QuestionModel("toDelete", "toDelete",
				"toDelete");
		long id = (Long) session.save(toDelete);
		session.getTransaction().commit();
		session.close();

		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();
		QuestionModel gotten = (QuestionModel) session2.get(
				QuestionModel.class, id);
		session2.delete(gotten);
		session2.getTransaction().commit();
		session2.close();

		Session session3 = sessionFactory.openSession();
		session3.beginTransaction();
		Assert.assertNull(session3.get(QuestionModel.class, id));
	}

}
