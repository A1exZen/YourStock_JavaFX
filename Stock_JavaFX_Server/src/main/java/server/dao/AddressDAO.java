package server.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import server.dto.AddressDTO;
import server.models.Address;

import java.util.List;

public class AddressDAO {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    public Address findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Address.class, id);
        }
    }

    public Address findByAddress(AddressDTO address) {
        try (Session session = sessionFactory.openSession()) {
            List<Address> addresses = session.createQuery("FROM Address WHERE city = :city AND address = :address", Address.class)
                    .setParameter("city", address.getCity())
                    .setParameter("address", address.getAddress())
                    .list();
            return addresses.isEmpty() ? null : addresses.get(0);
        }
    }

    public void save(Address address) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(address);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
