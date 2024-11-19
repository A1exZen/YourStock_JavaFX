package server.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import server.models.Employee;

import java.util.List;
import java.util.Optional;

public class EmployeeDAO implements DAO<Employee> {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static final EmployeeDAO INSTANCE = new EmployeeDAO();

    private EmployeeDAO() {}

    public static EmployeeDAO getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(Employee employee) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(employee);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteById(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Employee employee = session.get(Employee.class, id);
        if (employee != null) {
            session.delete(employee);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Employee employee) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(employee);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Optional<Employee> findById(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Employee employee = session.get(Employee.class, id);
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(employee);
    }

    @Override
    public List<Employee> findAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Employee> employees = session.createQuery("from Employee", Employee.class).getResultList();
        session.getTransaction().commit();
        session.close();
        return employees;
    }

    public List<Employee> findByPosition(String position) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Employee> employees = session.createQuery(
                        "from Employee e where e.position = :position", Employee.class)
                .setParameter("position", position)
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return employees;
    }
}
