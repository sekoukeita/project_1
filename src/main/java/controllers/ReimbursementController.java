package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import daos.ReimbursementDao;
import daos.ReimbursementDaoImpl;
import dto.JsonResponse;
import dto.UserDto;
import io.javalin.http.Context;
import models.Reimbursement;
import models.User;
import services.ReimbursementService;
import services.UserService;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ReimbursementController {

    static ReimbursementService reimbursementService = new ReimbursementService();
    static UserService userService = new UserService();


    // Methods
    public static void getReimbursements(Context ctx){
        ctx.json(reimbursementService.getReimbursements());
    }

    public static void getEmployeeReimbursements(Context ctx){
        // The user(the employee) needs to be logged first.
        User user = ctx.sessionAttribute("session");
        assert user != null;
        String usernameFromLogin = user.getUserName();
        User userLogged = userService.getUser(usernameFromLogin);
        Integer employeeId = userLogged.getUserId();
        ctx.json(reimbursementService.getEmployeeReimbursements(employeeId));
    }

    public static void getReimbursement(Context ctx){
        Integer reimbursementId = Integer.parseInt(ctx.pathParam("id"));
        ctx.json(reimbursementService.getReimbursement(reimbursementId));
    }

    public static void createReimbursement(Context ctx){
        Reimbursement reimbursement = ctx.bodyAsClass(Reimbursement.class);
        Timestamp  now = Timestamp.valueOf(LocalDateTime.now());

        if (reimbursement.getAmount() <= 0 | reimbursement.getDateSubmitted().after(now)){
            if (reimbursement.getAmount() <= 0){
                ctx.result("The amount should be greater than 0!");
            }
            if (reimbursement.getDateSubmitted().after(now)){
                ctx.result("Check the reimbursement date!");
            }
        }
        else {
            reimbursementService.createReimbursement(reimbursement);
            ctx.result("Congratulations! Your request has been successfully submitted.");
        }
    }

    public static void updateReimbursement(Context ctx){

        // The user(the manager needs to be logged first.)
        User user = ctx.sessionAttribute("session");
        assert user != null;
        String usernameFromLogin = user.getUserName();
        User userLogged = userService.getUser(usernameFromLogin); //This user is the manager that is logged in.

        Reimbursement reimbursement = ctx.bodyAsClass(Reimbursement.class);
        // the userLogged.getUserId() (the managerId) is not got from the body of the request. It is pulled from the session.
        reimbursementService.updateReimbursement(reimbursement.getReimbursementId(), reimbursement.getDateResolved(),
                userLogged.getUserId(), reimbursement.getStatusId());
    }

    public static void deleteReimbursement(Context ctx){
        Integer reimbursementId = Integer.parseInt(ctx.pathParam("id"));
        reimbursementService.deleteReimbursement(reimbursementId);
    }
}
