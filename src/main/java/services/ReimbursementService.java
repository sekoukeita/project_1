package services;

import daos.ReimbursementDao;
import daos.ReimbursementDaoImpl;
import models.Reimbursement;
import models.User;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

public class ReimbursementService {

    // References the dao
    ReimbursementDao reimbursementDao;

    // Constructor
    public ReimbursementService() {
        this.reimbursementDao = new ReimbursementDaoImpl();
    }

        // used by the ReimbursementService test
    public ReimbursementService(ReimbursementDao reimbursementDao) {
        this.reimbursementDao = reimbursementDao;
    }

    // Methods
    public List<Reimbursement> getReimbursements(){
        return reimbursementDao.getReimbursements();
    }

    public Reimbursement getReimbursement(Integer reimbursementId){
        return reimbursementDao.getReimbursement(reimbursementId);
    }

    public Boolean createReimbursement(Reimbursement reimbursement){
        LocalDateTime now = LocalDateTime.now();

        if (reimbursement.getAmount() <= 0){
            System.out.println("The amount should be greater than 0!");
            return false;
        }
        else if (reimbursement.getDateSubmitted().isAfter(now)){
            System.out.println("Check the reimbursement date!");
            return false;
        }
        else {
            reimbursementDao.createReimbursement(reimbursement);
            return true;
        }
    }

    public void updateReimbursement(Integer reimbursementId, LocalDateTime dateResolved, Integer resolverId, Integer statusId ){
        reimbursementDao.updateReimbursement(reimbursementId, dateResolved, resolverId, statusId);
    }

    public void deleteReimbursement(Integer reimbursementId){
        reimbursementDao.deleteReimbursement(reimbursementId);
    }

















}
