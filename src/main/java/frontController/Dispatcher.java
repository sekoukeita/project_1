package frontController;

import controllers.LoginController;
import controllers.ReimbursementController;
import controllers.UserController;
import io.javalin.Javalin;

public class Dispatcher {

    public Dispatcher(Javalin app) {

        app.post("/login", LoginController::login);
        app.get("/session", LoginController::session);
        app.delete("/logout", LoginController::logout);
        app.post("/register", UserController::createUser);
        app.post("/reimbursements/request", ReimbursementController::createReimbursement);
        app.get("/reimbursements/employee", ReimbursementController::getReimbursement);
        app.get("reimbursements/all", ReimbursementController::getReimbursements);

    }
}
