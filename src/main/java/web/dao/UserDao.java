package web.dao;

import web.models.User;

import java.util.List;

public interface UserDao  {
    List<User> getAllUsers();

    User getUserName(String name);

    User showUserById(int id);

    void addUser(User user);

    void updateUser(User user);

    void removeUser(int id);


}
