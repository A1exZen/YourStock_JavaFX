package server.dao;

import server.models.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.Optional;

public class ProductDAO implements DAO<Product> {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static final ProductDAO INSTANCE = new ProductDAO();

    private ProductDAO() {}

    public static ProductDAO getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(Product product) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product != null) {
                session.delete(product);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Product product) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Product> findById(Integer id) {
        Product product = null;
        try (Session session = sessionFactory.openSession()) {
            product = session.get(Product.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = null;
        try (Session session = sessionFactory.openSession()) {
            products = session.createQuery("from Product", Product.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
}
