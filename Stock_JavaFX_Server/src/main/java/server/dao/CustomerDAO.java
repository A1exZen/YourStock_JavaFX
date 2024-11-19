package server.dao;

import server.models.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.Optional;

public class CustomerDAO implements DAO<Customer> {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static final CustomerDAO INSTANCE = new CustomerDAO();

    private CustomerDAO() {}

    public static CustomerDAO getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(Customer customer) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Customer customer = session.get(Customer.class, id);
            if (customer != null) {
                session.delete(customer);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Customer customer) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(customer);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        Customer customer = null;
        try (Session session = sessionFactory.openSession()) {
            customer = session.get(Customer.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(customer);
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = null;
        try (Session session = sessionFactory.openSession()) {
            customers = session.createQuery("from Customer", Customer.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }
}
