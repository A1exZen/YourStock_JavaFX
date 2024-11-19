package server.services;

import server.dao.PersonalDetailDAO;
import server.models.PersonalDetail;

public class PersonalDetailService {
    private final PersonalDetailDAO personalDetailDAO;
    private static PersonalDetailService instance;

    private PersonalDetailService() {
        this.personalDetailDAO = new PersonalDetailDAO();
    }

    public static PersonalDetailService getInstance() {
        if (instance == null) {
            instance = new PersonalDetailService();
        }
        return instance;
    }

    public PersonalDetail findById(Long id) {
        return personalDetailDAO.findById(id);
    }

    public void save(PersonalDetail personalDetail) {
        personalDetailDAO.save(personalDetail);
    }
}
