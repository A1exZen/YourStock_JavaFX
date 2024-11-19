package server.dao;

import server.models.Material;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.Optional;

public class MaterialDAO implements DAO<Material> {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static final MaterialDAO INSTANCE = new MaterialDAO();

    private MaterialDAO() {}

    public static MaterialDAO getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(Material material) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(material);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Material material = session.get(Material.class, id);
            if (material != null) {
                session.delete(material);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Material material) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(material);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Material> findById(Integer id) {
        Material material = null;
        try (Session session = sessionFactory.openSession()) {
            material = session.get(Material.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(material);
    }

    @Override
    public List<Material> findAll() {
        List<Material> materials = null;
        try (Session session = sessionFactory.openSession()) {
            materials = session.createQuery("from Material", Material.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return materials;
    }
}
