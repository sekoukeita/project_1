package daos;

import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.H2Util;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplIT {

    UserDao userDao;

    // constructor that creates a userDao object that rather than connecting to the production db, connects to the H2 db.
    public UserDaoImplIT() {
        this.userDao = new UserDaoImpl(H2Util.url, H2Util.username, H2Util.password);
    }

    @BeforeEach
    void setUp() {
        H2Util.createTables();
    }

    @AfterEach
    void tearDown() {
        H2Util.dropTables();
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

        // feed the table user(the create method uses the table, not the view. use the constructor for the create method (6 variables))
        userDao.createUser(new User( "secksonr9", "17071980", "Sekou",
                "Keita", "K_sekou9@yahoo.fr", 2));
        userDao.createUser(new User("alibaba", "a1b1", "ali",
                "baba", "babaali9@yahoo.fr", 1));
        userDao.createUser(new User("keisayon", "coco225", "sayon",
                "Traore", "cocog9@gmail.com", 1));

        //ACT
        List<User> actualResult = userDao.getUsers();

        //ASSERT
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void getUserById() {
        //ARRANGE
        User expectedResult = new User(1, "secksonr9", "17071980", "Sekou",
                "Keita", "K_sekou9@yahoo.fr", 2, "Finance Manager");

        userDao.createUser(new User( "secksonr9", "17071980", "Sekou",
                "Keita", "K_sekou9@yahoo.fr", 2));

        //ACT
        User actualResult = userDao.getUser(expectedResult.getUserId());

        //ASSERT
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void getUserByUserName() {
        //ARRANGE
        User expectedResult = new User(1, "secksonr9", "17071980", "Sekou",
                "Keita", "K_sekou9@yahoo.fr", 2, "Finance Manager");

        userDao.createUser(new User( "secksonr9", "17071980", "Sekou",
                "Keita", "K_sekou9@yahoo.fr", 2));

        //ACT
        User actualResult = userDao.getUser(expectedResult.getUserName());

        //ASSERT
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void createUser() {
        //ARRANGE
        User user1 = new User(1, "secksonr9", "17071980", "Sekou",
                "Keita", "K_sekou9@yahoo.fr", 2, "Finance Manager");
        User user2 = new User(2,"alibaba", "a1b1", "ali",
                "baba", "babaali9@yahoo.fr", 1, "Employee");
        User user3 = new User(3,"keisayon", "coco225", "sayon",
                "Traore", "cocog9@gmail.com", 1, "Employee");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        int expectedResult = users.size();

        userDao.createUser(new User( "secksonr9", "17071980", "Sekou",
                "Keita", "K_sekou9@yahoo.fr", 2));
        userDao.createUser(new User("alibaba", "a1b1", "ali",
                "baba", "babaali9@yahoo.fr", 1));
        userDao.createUser(new User("keisayon", "coco225", "sayon",
                "Traore", "cocog9@gmail.com", 1));

        //ACT
        int actualResult = userDao.getUsers().size();

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updateUser() {
        //ARRANGE
        User user = new User(1,"secksonr9", "17071980", "Sekou",
                "Keita", "K_sekou9@yahoo.fr", 2);

            // user after update
        User expectedResult = new User(1,"alibaba", "a1b1", "ali",
                "baba", "babaali9@yahoo.fr", 1, "Employee");

        userDao.createUser(user);

        //ACT
        userDao.updateUser(new User(1,"alibaba", "a1b1", "ali",
                "baba", "babaali9@yahoo.fr", 1));
        User actualResult = userDao.getUser(expectedResult.getUserId());

        //ASSERT
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void deleteUser() {
        //ARRANGE
        User user = new User(1,"secksonr9", "17071980", "Sekou",
                "Keita", "K_sekou9@yahoo.fr", 1);

        userDao.createUser(user);

        //ACT
        userDao.deleteUser(user.getUserId());
        User actualResult = userDao.getUser(user.getUserId());

        //ASSERT
        assertNull(actualResult);
    }
}