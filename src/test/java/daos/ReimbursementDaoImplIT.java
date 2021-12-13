package daos;

import models.Reimbursement;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.H2Util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReimbursementDaoImplIT {

    ReimbursementDao reimbursementDao;
    UserDao userDao;

    // will be used by the integration test constructor to reach the H2 db rather than the production db.
    public ReimbursementDaoImplIT() {
        this.reimbursementDao = new ReimbursementDaoImpl(H2Util.url, H2Util.username, H2Util.password);
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
    void getReimbursements() {
        //ARRANGE
            // users that submit the reimbursement
        User user1 = new User( "secksonr9", "17071980", "Sekou",
                "Keita", "K_sekou9@yahoo.fr", 1);
        User user2 = new User("keisayon", "coco225", "sayon",
                "Traore", "cocog9@gmail.com", 1);

            // Reimbursements to submit
        Reimbursement reimbursement1 = new Reimbursement(1000.0, LocalDateTime.of(2021, 12, 1, 0, 0),
                "Vacation in Miami", 1, 1, 2);
        Reimbursement reimbursement2 = new Reimbursement(2000.0, LocalDateTime.of(2021, 12, 2, 0, 0),
                "Stay at Hilton New York city", 2, 1, 1);

            // Expected result (Note: Changed null by 0 for resolverIds. Maybe a constraint from H2 db)
        List<Reimbursement> expectedResult = new ArrayList<>();
        expectedResult.add(new Reimbursement(1, 1000.0, LocalDateTime.of(2021, 12, 1, 0, 0),
                null, "Vacation in Miami", null, 1, "Sekou",
                "Keita", 0, null, null, 1, "Pending", 2, "TRAVEL"));
        expectedResult.add(new Reimbursement(2, 2000.0, LocalDateTime.of(2021, 12, 2, 0, 0),
                        null, "Stay at Hilton New York city", null, 2, "sayon",
                        "Traore", 0, null, null, 1, "Pending", 1, "LODGING"));

            // feed the database
        userDao.createUser(user1);
        userDao.createUser(user2);
        reimbursementDao.createReimbursement(reimbursement1);
        reimbursementDao.createReimbursement(reimbursement2);

        //ACT
        List<Reimbursement> actualResult = reimbursementDao.getReimbursements();

        //ASSERT
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void getReimbursement() {
        //ARRANGE
            // user that submit the reimbursement
        User user = new User( "secksonr9", "17071980", "Sekou",
                "Keita", "K_sekou9@yahoo.fr", 1);

            // Reimbursements to submit
        Reimbursement reimbursement = new Reimbursement(1000.0, LocalDateTime.of(2021, 12, 1, 0, 0),
                "Vacation in Miami", 1, 1, 2);

            // Expected result (Note: Changed null by 0 for resolverIds. Maybe a constraint from H2 db)
        Reimbursement expectedResult = new Reimbursement(1, 1000.0,
                LocalDateTime.of(2021, 12, 1, 0, 0), null, "Vacation in Miami",
                null, 1, "Sekou", "Keita", 0, null,
                null, 1, "Pending", 2, "TRAVEL");

            // feed the database
        userDao.createUser(user);
        reimbursementDao.createReimbursement(reimbursement);

        //ACT
        Reimbursement actualResult = reimbursementDao.getReimbursement(expectedResult.getReimbursementId());

        //ASSERT
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void createReimbursement() {
        //ARRANGE
        // users that submit the reimbursement
        User user1 = new User( "secksonr9", "17071980", "Sekou",
                "Keita", "K_sekou9@yahoo.fr", 1);
        User user2 = new User("keisayon", "coco225", "sayon",
                "Traore", "cocog9@gmail.com", 1);

        // Reimbursements to submit
        Reimbursement reimbursement1 = new Reimbursement(1000.0, LocalDateTime.of(2021, 12, 1, 0, 0),
                "Vacation in Miami", 1, 1, 2);
        Reimbursement reimbursement2 = new Reimbursement(2000.0, LocalDateTime.of(2021, 12, 2, 0, 0),
                "Stay at Hilton New York city", 2, 1, 1);

        // Reimbursement returned from H2
        List<Reimbursement> returned = new ArrayList<>();
        returned.add(new Reimbursement(1, 1000.0, LocalDateTime.of(2021, 12, 1, 0, 0),
                null, "Vacation in Miami", null, 1, "Sekou",
                "Keita", 0, null, null, 1, "Pending", 2, "TRAVEL"));
        returned.add(new Reimbursement(2, 2000.0, LocalDateTime.of(2021, 12, 2, 0, 0),
                null, "Stay at Hilton New York city", null, 2, "sayon",
                "Traore", 0, null, null, 1, "Pending", 1, "LODGING"));

        int expectedResult = returned.size();

        // feed the database
        userDao.createUser(user1);
        userDao.createUser(user2);
        reimbursementDao.createReimbursement(reimbursement1);
        reimbursementDao.createReimbursement(reimbursement2);

        //ACT
        int actualResult = reimbursementDao.getReimbursements().size();

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updateReimbursement() {
        //ARRANGE
        // users. user1 submits the request and user2 updates(approved)
        User user1 = new User( "secksonr9", "17071980", "Sekou",
                "Keita", "K_sekou9@yahoo.fr", 1);
        User user2 = new User("keisayon", "coco225", "sayon",
                "Traore", "cocog9@gmail.com", 2);

        // Reimbursement to submit then to be updated
        Reimbursement reimbursement = new Reimbursement(1000.0, LocalDateTime.of(2021, 12, 1, 0, 0),
                "Vacation in Miami", 1, 1, 2);

        // Expected result after update
        Reimbursement expectedResult = new Reimbursement(1, 1000.0,
                LocalDateTime.of(2021, 12, 1, 0, 0), LocalDateTime.of(2021, 12, 10, 0, 0),
                "Vacation in Miami", null, 1, "Sekou", "Keita", 2, "sayon",
                "Traore", 2, "Approved", 2, "TRAVEL");

        // feed the database
        userDao.createUser(user1);
        userDao.createUser(user2);
        reimbursementDao.createReimbursement(reimbursement);

        //ACT
        reimbursementDao.updateReimbursement(1, LocalDateTime.of(2021, 12, 10, 0, 0),
                2, 2);
        Reimbursement actualResult = reimbursementDao.getReimbursement(expectedResult.getReimbursementId());

        //ASSERT
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void deleteReimbursement() {
        //ARRANGE
        // user that submit the reimbursement
        User user = new User( "secksonr9", "17071980", "Sekou",
                "Keita", "K_sekou9@yahoo.fr", 1);

        // Reimbursements to submit
        Reimbursement reimbursement = new Reimbursement(1000.0, LocalDateTime.of(2021, 12, 1, 0, 0),
                "Vacation in Miami", 1, 1, 2);

        // feed the database
        userDao.createUser(user);
        reimbursementDao.createReimbursement(reimbursement);

        //ACT
            // Only one reimbursement is created so the id = 1.
            // can't use reimbursement.getReimbursementId because the id is not used while creating the object.
        reimbursementDao.deleteReimbursement(1);
        Reimbursement actualResult = reimbursementDao.getReimbursement(1);

        //ASSERT
        assertNull(actualResult);
    }
}