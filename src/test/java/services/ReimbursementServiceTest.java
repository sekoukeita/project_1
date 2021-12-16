package services;

import daos.ReimbursementDao;
import models.Reimbursement;
import models.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReimbursementServiceTest {

    // Member variables
    ReimbursementService reimbursementService; // reference the reimbursementService
    ReimbursementDao  reimbursementDao = Mockito.mock(ReimbursementDao.class);

    // Constructor
    public ReimbursementServiceTest() { // this constructor points to the mocked dao rather than the normal one that points to daoImpl
        this.reimbursementService = new ReimbursementService(reimbursementDao);
    }

    @Test
    void getReimbursements() {
        //ARRANGE
            // Expected result
        List<Reimbursement> expectedResult = new ArrayList<>();
        expectedResult.add(new Reimbursement(1, 1000.0,
                Timestamp.valueOf(LocalDateTime.of(2021, 12, 1, 0, 0)),
                null, "Vacation in Miami", null, 1, "Sekou",
                "Keita", null, null, null, 1, "Pending", 2, "TRAVEL"));
        expectedResult.add(new Reimbursement(2, 2000.0,
                Timestamp.valueOf(LocalDateTime.of(2021, 12, 2, 0, 0)),
                null, "Stay at Hilton New York city", null, 2, "sayon",
                "Traore", null, null, null, 1, "Pending",
                1, "LODGING"));

        Mockito.when(reimbursementDao.getReimbursements()).thenReturn(expectedResult);

        //ACT
        List<Reimbursement> actualResult = reimbursementService.getReimbursements();

        //ASSERT
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void getEmployeeReimbursements() {
        //ARRANGE
        List<Reimbursement> reimbursements = new ArrayList<>();
        reimbursements.add(new Reimbursement(1, 1000.0,
                Timestamp.valueOf(LocalDateTime.of(2021, 12, 1, 0, 0)),
                null, "Vacation in Miami", null, 1, "Sekou",
                "Keita", null, null, null, 1, "Pending", 2, "TRAVEL"));
        reimbursements.add(new Reimbursement(2, 2000.0,
                Timestamp.valueOf(LocalDateTime.of(2021, 12, 2, 0, 0)),
                null, "Stay at Hilton New York city", null, 2, "sayon",
                "Traore", null, null, null, 1, "Pending",
                1, "LODGING"));

        // Expected result
        List<Reimbursement> expectedResult = new ArrayList<>();
        expectedResult.add(new Reimbursement(1, 1000.0,
                Timestamp.valueOf(LocalDateTime.of(2021, 12, 1, 0, 0)),
                null, "Vacation in Miami", null, 1, "Sekou",
                "Keita", null, null, null, 1, "Pending", 2, "TRAVEL"));

        Mockito.when(reimbursementDao.getEmployeeReimbursements(reimbursements.get(0).getAuthorId())).thenReturn(expectedResult);

        //ACT
        List<Reimbursement> actualResult = reimbursementService.getEmployeeReimbursements(reimbursements.get(0).getAuthorId());

        //ASSERT
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void getReimbursement() {
        // Expected result
        Reimbursement expectedResult = new Reimbursement(1, 1000.0,
                Timestamp.valueOf(LocalDateTime.of(2021, 12, 1, 0, 0)), null,
                "Vacation in Miami", null, 1, "Sekou", "Keita",
                null, null, null, 1, "Pending", 2, "TRAVEL");

        Mockito.when(reimbursementDao.getReimbursement(expectedResult.getReimbursementId())).thenReturn(expectedResult);

        //ACT
        Reimbursement actualResult = reimbursementService.getReimbursement(expectedResult.getReimbursementId());

        //ASSERT
        assertEquals(expectedResult.toString(), actualResult.toString());

    }

    @Test
    void createReimbursementReturnTrue() {
        //ARRANGE
        Reimbursement reimbursementToCreate = new Reimbursement(1000.0,
                Timestamp.valueOf(LocalDateTime.of(2021, 12, 2, 0, 0)),
                "Stay at Hilton New York city",
                2, 1, 1);

             /*Conditions:
                        - amount: >0
                        - dateSubmitted: not in the future
                    *
            */

        //ACT
        Boolean actualResult = reimbursementService.createReimbursement(reimbursementToCreate);

        //ASSERT
        assertTrue(actualResult);
    }

    @Test
    void createReimbursementReturnFalse() {
        //ARRANGE
            // the date is in the future
        Reimbursement reimbursementToCreate = new Reimbursement(1000.0,
                Timestamp.valueOf(LocalDateTime.of(2022, 12, 2, 0, 0)),
                "Stay at Hilton New York city",
                2, 1, 1);

            /* Conditions:
                        - amount: >0
                        - dateSubmitted: not in the future
                    **/

        //ACT
        Boolean actualResult = reimbursementService.createReimbursement(reimbursementToCreate);

        //ASSERT
        assertFalse(actualResult);
    }

    @Test
    void updateReimbursement() {
        //ARRANGE
        // user that will update (approve) the reimbursement
        User user = new User( 1,"Secksonr9", "p4ssw0rd", "Sekou",
                "Keita", "sekou.keita@revature.net", 2);

        Timestamp dateResolved = Timestamp.valueOf(LocalDateTime.parse("2022-11-01T00:00"));
        Integer statusId = 2; // for approved

        // Reimbursement to be updated
        Reimbursement reimbursement = new Reimbursement(1, 1000.0,
                Timestamp.valueOf(LocalDateTime.of(2021, 12, 1, 0, 0)),
                "Vacation in Miami", 1, 1, 2);


        //ACT
        reimbursementService.updateReimbursement(reimbursement.getReimbursementId(), dateResolved, user.getUserId(), statusId);

        //ASSERT
        // When nothing is returned, use Mockito.verify to verify that the method was invoked
        Mockito.verify(reimbursementDao, Mockito.times(1))
                .updateReimbursement(reimbursement.getReimbursementId(), dateResolved, user.getUserId(), statusId);
    }

    @Test
    void deleteReimbursement() {
        //ARRANGE
            // Reimbursement to delete
        Reimbursement reimbursement = new Reimbursement(1, 1000.0,
                Timestamp.valueOf(LocalDateTime.of(2021, 12, 1, 0, 0)),
                "Vacation in Miami", 1, 1, 2);


        //ACT
        reimbursementService.deleteReimbursement(reimbursement.getReimbursementId());

        //ASSERT
        // When nothing is returned, use Mockito.verify to verify that the method was invoked
        Mockito.verify(reimbursementDao, Mockito.times(1))
                .deleteReimbursement(reimbursement.getReimbursementId());

    }
}
