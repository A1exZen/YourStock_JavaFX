package server.dao;

import server.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.Optional;

public class UserDAO implements DAO<User> {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static final UserDAO INSTANCE = new UserDAO();

    private UserDAO() {}

    public static UserDAO getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            user = session.get(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        List<User> users = null;
        try (Session session = sessionFactory.openSession()) {
            users = session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Optional<User> findByName(String name) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            user = session.createQuery("FROM User u WHERE u.username = :name", User.class)
                    .setParameter("name", name)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }
}
