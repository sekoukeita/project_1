package daos;

import models.Reimbursement;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface ReimbursementDao {

    List<Reimbursement> getReimbursements();

    Reimbursement getReimbursement(Integer reimbursementId);

    List<Reimbursement> getEmployeeReimbursements(Integer EmployeeId);

    void createReimbursement(Reimbursement reimbursement);

    // put (update only dateResolved, resolverId and statusId)
    void updateReimbursement(Integer reimbursementId, Timestamp dateResolved, Integer resolverId, Integer statusId);

    void deleteReimbursement(Integer reimbursementId);
}
