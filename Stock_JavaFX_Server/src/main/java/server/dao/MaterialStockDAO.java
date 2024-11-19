package server.dao;

import server.models.MaterialStock;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.Optional;

public class MaterialStockDAO implements DAO<MaterialStock> {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static final MaterialStockDAO INSTANCE = new MaterialStockDAO();

    private MaterialStockDAO() {}

    public static MaterialStockDAO getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(MaterialStock materialStock) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(materialStock);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            MaterialStock materialStock = session.get(MaterialStock.class, id);
            if (materialStock != null) {
                session.delete(materialStock);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(MaterialStock materialStock) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(materialStock);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<MaterialStock> findById(Integer id) {
        MaterialStock materialStock = null;
        try (Session session = sessionFactory.openSession()) {
            materialStock = session.get(MaterialStock.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(materialStock);
    }

    @Override
    public List<MaterialStock> findAll() {
        List<MaterialStock> materialStocks = null;
        try (Session session = sessionFactory.openSession()) {
            materialStocks = session.createQuery("from MaterialStock", MaterialStock.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return materialStocks;
    }
}
