package server.services;

import server.dao.EmployeeDAO;
import server.enums.RoleType;
import server.models.Employee;
import server.models.Employee;
import server.utils.PasswordEncoder;

import java.util.List;
import java.util.Optional;


public class EmployeeService implements Service<Employee> {

    private final EmployeeDAO employeeDAO = EmployeeDAO.getInstance();

    private static final EmployeeService INSTANCE = new EmployeeService();

    private EmployeeService() {
    }

    public static EmployeeService getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(Employee Employee) {
        employeeDAO.save(Employee);
    }


    @Override
    public void deleteById(int id) {
        employeeDAO.deleteById(id);
    }

    @Override
    public void update(Employee Employee) {
        employeeDAO.update(Employee);
    }

    @Override
    public Employee findById(int id) {
        return employeeDAO.findById(id).orElse(null);
    }

    @Override
    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    public Optional<Employee> findByName(String name) {
        return employeeDAO.findByName(name);
    }

    public boolean nameExists(String name) {
        return employeeDAO.findByName(name).isPresent();
    }
}
