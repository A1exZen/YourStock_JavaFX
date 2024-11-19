package server.services;

import server.dao.SupplierDAO;
import server.models.Supplier;

import java.util.List;
import java.util.Optional;

public class SupplierService implements Service<Supplier> {

    private final SupplierDAO supplierDAO = SupplierDAO.getInstance();

    private static final SupplierService INSTANCE = new SupplierService();

    private SupplierService() {
    }

    public static SupplierService getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(Supplier supplier) {
        supplierDAO.save(supplier);
    }

    @Override
    public void deleteById(int id) {
        supplierDAO.deleteById(id);
    }

    @Override
    public void update(Supplier supplier) {
        supplierDAO.update(supplier);
    }

    @Override
    public Supplier findById(int id) {
        return supplierDAO.findById(id).orElse(null);
    }

    @Override
    public List<Supplier> findAll() {
        return supplierDAO.findAll();
    }

    public Optional<Supplier> findByName(String name) {
        return supplierDAO.findByName(name);
    }

    public boolean supplierExists(String name) {
        return supplierDAO.findByName(name).isPresent();
    }
}
