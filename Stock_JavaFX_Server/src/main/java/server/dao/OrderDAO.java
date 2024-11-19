package server.dao;

import server.models.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.Optional;

public class OrderDAO implements DAO<Order> {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static final OrderDAO INSTANCE = new OrderDAO();

    private OrderDAO() {}

    public static OrderDAO getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(Order order) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(order);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Order order = session.get(Order.class, id);
            if (order != null) {
                session.delete(order);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Order order) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(order);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Order> findById(Integer id) {
        Order order = null;
        try (Session session = sessionFactory.openSession()) {
            order = session.get(Order.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(order);
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = null;
        try (Session session = sessionFactory.openSession()) {
            orders = session.createQuery("from Order", Order.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }
}
