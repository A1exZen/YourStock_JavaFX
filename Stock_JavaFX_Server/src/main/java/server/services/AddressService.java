package server.services;

import server.dao.AddressDAO;
import server.dto.AddressDTO;
import server.models.Address;

public class AddressService {
    private final AddressDAO addressDAO;
    private static AddressService instance;

    private AddressService() {
        this.addressDAO = new AddressDAO();
    }

    public static AddressService getInstance() {
        if (instance == null) {
            instance = new AddressService();
        }
        return instance;
    }

    public Address findById(Long id) {
        return addressDAO.findById(id);
    }

    public Address findByAddress(AddressDTO address) {
        return addressDAO.findByAddress(address);
    }

    public void save(Address address) {
        addressDAO.save(address);
    }
}
