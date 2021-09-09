package web.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import web.models.User;

import java.util.List;

public interface UserService  {
    List<User> getAllUsers();

    User getUserName(String name);

    User showUserById(int id);

    void addUser(User user);

    void updateUser(User user);

    void removeUser(int id);

    void save(User user);
}