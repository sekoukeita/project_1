package daos;

import models.Reimbursement;

import java.time.LocalDateTime;
import java.util.List;

public interface ReimbursementDao {

    List<Reimbursement> getReimbursements();

    Reimbursement getReimbursement(Integer reimbursementId);

    void createReimbursement(Reimbursement reimbursement);

    // put (update only dateResolved, resolverId and statusId)
    void updateReimbursement(Integer reimbursementId, LocalDateTime dateResolved, Integer resolverId, Integer statusId);

    void deleteReimbursement(Integer reimbursementId);
}
