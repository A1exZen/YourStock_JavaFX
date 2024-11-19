package server.services;

import server.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface Service<T> {
    void save(T object);

    void deleteById(int id);
    void update(T object);
    T findById(int id);
    List<T> findAll();
}
