package model.dao;

import model.entity.User;
import java.util.List;

public interface UserDao {
    void create(User user);
    void update(User user);
    void delete(int id);
    User findById(int id);
    User findByUsername(String username);
//    Optional<User> findByUsernameAndPassword(String username, String passwordHash);
    List<User> findAll();
}