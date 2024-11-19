package server.services;

import server.dao.UserDAO;
import server.enums.RoleType;
import server.models.Employee;
import server.models.User;
import server.utils.PasswordEncoder;

import java.util.List;
import java.util.Optional;


public class UserService implements Service<User> {

    private final UserDAO userDAO = UserDAO.getInstance();
    private final EmployeeService employeeService = EmployeeService.getInstance();
    private static final UserService INSTANCE = new UserService();

    private UserService() {
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(User user) {
        user.setPassword(PasswordEncoder.hashPassword(user.getPassword()));
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole(RoleType.USER.name());
        }
        if (!user.getRole().equals(RoleType.USER.name()) && user.getEmployee() != null) {
            // Сохраняем Employee, если передан в User
            Employee employee = user.getEmployee();

            // Проверка на наличие id у Employee, чтобы избежать дублирования
            if (employee.getId() == null) {
                employeeService.save(employee);
            }
            user.setEmployee(employee);
        }
        userDAO.save(user);
    }


    @Override
    public void deleteById(int id) {
        userDAO.deleteById(id);
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public User findById(int id) {
        return userDAO.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    public Optional<User> findByUsername(String name) {
        return userDAO.findByName(name);
    }

    public boolean usernameExists(String username) {
        return userDAO.findByName(username).isPresent();
    }
}
