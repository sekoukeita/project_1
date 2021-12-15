package daos;

import models.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User getUser(Integer userId);

    User getUser(String username);

    void createUser(User user);

    void updateUser(User user); // update by patch. update everything but the userId

    void deleteUser(Integer userId);
}
