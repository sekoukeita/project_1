package services;

import daos.UserDao;
import models.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    // reference the userService and the mocked userDao
    UserDao userDao = Mockito.mock(UserDao.class);
    UserService userService;

    // Constructors
    public UserServiceTest() {
        this.userService = new UserService(userDao);
    }

    @Test
    void getUsers() {
        //ARRANGE
        // users returned from the db (returned using the view, full variables' constructor)
        User user1 = new User(1, "secksonr9", "17071980", "Sekou",
                "Keita", "K_sekou9@yahoo.fr", 2, "Finance Manager");
        User user2 = new User(2,"alibaba", "a1b1", "ali",
                "baba", "babaali9@yahoo.fr", 1, "Employee");
        User user3 = new User(3,"keisayon", "coco225", "sayon",
                "Traore", "cocog9@gmail.com", 1, "Employee");

        List<User> expectedResult = new ArrayList<>();
        expectedResult.add(user1);
        expectedResult.add(user2);
        expectedResult.add(user3);

        Mockito.when(userDao.getUsers()).thenReturn(expectedResult);

        //ACT
        List<User> actualResult = userService.getUsers();

        //ASSERT
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void getUser() {
        //ARRANGE
        User expectedResult = new User(1, "secksonr9", "17071980", "Sekou",
                "Keita", "K_sekou9@yahoo.fr", 2, "Finance Manager");

        Mockito.when(userDao.getUser(expectedResult.getUserId())).thenReturn(expectedResult);

        //ACT
        User actualResult = userService.getUser(expectedResult.getUserId());

        //ASSERT
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void createUserReturnsTrue() {
        //ARRANGE

        User userToCreate = new User("secksonr9", "17071980", "Sekou",
                "Keita", "K_sekou9@yahoo.fr", 2);

        /* Conditions:
            - username: length <= 50, unique, not null
            - password: length <= 50, not null
            - firstName: length <= 100
            - lastName: length <= 100
            - email: length <= 150, unique, not null
        * */

        //ACT
        Boolean actualResult = userService.createUser(userToCreate);

        //ASSERT
        assertTrue(actualResult);
    }
    @Test
    void createUserReturnsFalse() {
        //ARRANGE
            // password longer than 50 characters.
        User userToCreate = new User("secksonr9", "1707111111111111111111111111111111111111111111111111111111111111980qqqqq",
                "Sekou", "Keita", "K_sekou9@yahoo.fr", 2);

        /* Conditions:
            - username: length <= 50, unique, not null
            - password: length <= 50, not null
            - firstName: length <= 100
            - lastName: length <= 100
            - email: length <= 150, unique, not null
        * */

        //ACT
        Boolean actualResult = userService.createUser(userToCreate);

        //ASSERT
        assertFalse(actualResult);
    }

    @Test
    void updateUser() {
        //ARRANGE
            // user to update
        User user = new User(1,"secksonr9", "17071980", "Sekou",
                "Keita", "K_sekou9@yahoo.fr", 2);

        //ACT
        userService.updateUser(user);

        //ASSERT
            // When nothing is returned, use Mockito.verify to verify that the method was invoked
        Mockito.verify(userDao, Mockito.times(1)).updateUser(user);
    }

    @Test
    void deleteUser() {
        //ARRANGE
        // user to delete.
        User user = new User(1,"secksonr9", "17071980", "Sekou",
                "Keita", "K_sekou9@yahoo.fr", 2);

        //ACT
        userService.deleteUser(user.getUserId());

        //ASSERT
        // When nothing is returned, use Mockito.verify to verify that the method was invoked
        Mockito.verify(userDao, Mockito.times(1)).deleteUser(user.getUserId());
    }
}