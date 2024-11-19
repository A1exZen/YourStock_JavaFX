package server.dao;

import org.hibernate.Transaction;
import server.models.Supplier;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import server.models.User;

import java.util.List;
import java.util.Optional;

public class SupplierDAO implements DAO<Supplier> {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static final SupplierDAO INSTANCE = new SupplierDAO();

    private SupplierDAO() {}

    public static SupplierDAO getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(Supplier supplier) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(supplier);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Supplier supplier = session.get(Supplier.class, id);

            if (supplier != null) {
                session.delete(supplier);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Supplier supplier) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            // Извлечение существующего поставщика по id
            Supplier existingSupplier = session.get(Supplier.class, supplier.getId());

            if (existingSupplier != null) {
                existingSupplier.setName(supplier.getName());
                existingSupplier.setPersonalDetails(supplier.getPersonalDetails());
                // Сохранение изменений
                session.update(existingSupplier);
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Supplier> findById(Integer id) {
        Supplier supplier = null;
        try (Session session = sessionFactory.openSession()) {
            supplier = session.get(Supplier.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(supplier);
    }

//    @Override
//    public List<Supplier> findAll() {
//        List<Supplier> suppliers = null;
//        try (Session session = sessionFactory.openSession()) {
//            suppliers = session.createQuery("from Supplier", Supplier.class).list();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return suppliers;
//    }
    @Override
    public List<Supplier> findAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Supplier> suppliers = session.createQuery("FROM Supplier", Supplier.class).list();
        session.getTransaction().commit();
        session.close();
        return suppliers;
    }
    @Override
    public Optional<Supplier> findByName(String name) {
        Supplier supplier = null;
        try (Session session = sessionFactory.openSession()) {
            supplier = session.createQuery("FROM Supplier s WHERE s.name = :name", Supplier.class)
                    .setParameter("name", name)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(supplier);
    }
}
