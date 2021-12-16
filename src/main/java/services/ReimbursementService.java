package services;

import daos.ReimbursementDao;
import daos.ReimbursementDaoImpl;
import models.Reimbursement;
import models.User;
import org.mockito.Mockito;

import java.sql.Timestamp;
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

    public List<Reimbursement> getEmployeeReimbursements(Integer employeeId){
        return reimbursementDao.getEmployeeReimbursements(employeeId);
    }

    public Reimbursement getReimbursement(Integer reimbursementId){
        return reimbursementDao.getReimbursement(reimbursementId);
    }

    public Boolean createReimbursement(Reimbursement reimbursement){
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        if (reimbursement.getAmount() <= 0 | reimbursement.getDateSubmitted().after(now)){
            if (reimbursement.getAmount() <= 0){
                System.out.println("The amount should be greater than 0!");
            }
            if (reimbursement.getDateSubmitted().after(now)){
                System.out.println("Check the reimbursement date!");
            }
            return false;
        }
        else {
            System.out.println("Reimbursement created!");
            reimbursementDao.createReimbursement(reimbursement);
            return true;
        }
    }

    public void updateReimbursement(Integer reimbursementId, Timestamp dateResolved, Integer resolverId, Integer statusId ){
        reimbursementDao.updateReimbursement(reimbursementId, dateResolved, resolverId, statusId);
    }

    public void deleteReimbursement(Integer reimbursementId){
        reimbursementDao.deleteReimbursement(reimbursementId);
    }

















}
