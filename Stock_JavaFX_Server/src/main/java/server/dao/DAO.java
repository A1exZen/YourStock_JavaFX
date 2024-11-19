package server.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    void save(T object);
    void deleteById(Integer id);
    void update(T object);
    Optional<T> findById(Integer id);
    default Optional<T> findByName(String username) {
        return Optional.empty();
    }
    List<T> findAll();
}