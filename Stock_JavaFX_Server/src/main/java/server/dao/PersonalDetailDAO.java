package server.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import server.models.PersonalDetail;

public class PersonalDetailDAO {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    public PersonalDetail findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(PersonalDetail.class, id);
        }
    }

    public void save(PersonalDetail personalDetail) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(personalDetail);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
