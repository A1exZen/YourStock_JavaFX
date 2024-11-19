package server.dao;

import server.models.ProductStock;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.Optional;

public class ProductStockDAO implements DAO<ProductStock> {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static final ProductStockDAO INSTANCE = new ProductStockDAO();

    private ProductStockDAO() {}

    public static ProductStockDAO getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(ProductStock productStock) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(productStock);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            ProductStock productStock = session.get(ProductStock.class, id);
            if (productStock != null) {
                session.delete(productStock);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ProductStock productStock) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(productStock);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<ProductStock> findById(Integer id) {
        ProductStock productStock = null;
        try (Session session = sessionFactory.openSession()) {
            productStock = session.get(ProductStock.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(productStock);
    }

    @Override
    public List<ProductStock> findAll() {
        List<ProductStock> productStocks = null;
        try (Session session = sessionFactory.openSession()) {
            productStocks = session.createQuery("from ProductStock", ProductStock.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productStocks;
    }
}
