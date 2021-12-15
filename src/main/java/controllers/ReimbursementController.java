package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UserDto;
import io.javalin.http.Context;
import models.Reimbursement;
import models.User;
import services.ReimbursementService;
import services.UserService;

import java.time.LocalDateTime;

public class ReimbursementController {

    static ReimbursementService reimbursementService = new ReimbursementService();

   /* public ReimbursementController() {
        this.reimbursementService = new ReimbursementService();
    }*/

    // Methods
    public static void getReimbursements(Context ctx) throws JsonProcessingException {
        ctx.contentType("Application/json");
        ctx.json(reimbursementService.getReimbursements());
    }

    public static void getReimbursement(Context ctx){
        Integer reimbursementId = Integer.parseInt(ctx.pathParam("id"));
        ctx.json(reimbursementService.getReimbursement(reimbursementId));
    }

    static UserService userService = new UserService();

    public static void createReimbursement(Context ctx){
        Reimbursement reimbursement = ctx.bodyAsClass(Reimbursement.class);
        LocalDateTime now = LocalDateTime.now();

        if (reimbursement.getAmount() <= 0){
            ctx.result("The amount should be greater than 0!");

        }
        else if (reimbursement.getDateSubmitted().isAfter(now)){
            ctx.result("Check the reimbursement date!");
        }
        else {
            reimbursementService.createReimbursement(reimbursement);
            ctx.result("The reimbursement request has been successfully submitted!");
        }
    }

    public static void updateReimbursement(Context ctx){
        Reimbursement reimbursement = ctx.bodyAsClass(Reimbursement.class);
        User user = ctx.sessionAttribute("session"); //This user is the manager that is logged in.

        assert user != null;
        String usernameFromLogin = user.getUserName();
        User userFromDB = userService.getUser(usernameFromLogin);
        reimbursementService.updateReimbursement(reimbursement.getReimbursementId(), reimbursement.getDateResolved(),
                userFromDB.getUserId(), reimbursement.getStatusId());
    }

    public static void deleteReimbursement(Context ctx){
        Integer reimbursementId = Integer.parseInt(ctx.pathParam("id"));
        reimbursementService.deleteReimbursement(reimbursementId);
    }















}
