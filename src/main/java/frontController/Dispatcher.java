package frontController;

import controllers.LoginController;
import controllers.ReimbursementController;
import controllers.UserController;
import io.javalin.Javalin;

public class Dispatcher {

    public Dispatcher(Javalin app) {

        // Endpoints that won't be directly used in the project
        app.get("/users", UserController::getUsers);
        app.get("/users/{id}", UserController::getUser);



        app.post("/login", LoginController::login);
        app.get("/session", LoginController::session);
        app.delete("/logout", LoginController::logout);
        app.post("/register", UserController::createUser);
        app.post("/reimbursements/request", ReimbursementController::createReimbursement);
        /*
        * get the all reimbursements for the logged employee. The employee id is got from the session and feed directly
        * into the controller method. The id does not appear in the endpoint.
        * */
        app.get("/reimbursements/employee", ReimbursementController::getEmployeeReimbursements);
        app.get("reimbursements/all", ReimbursementController::getReimbursements);
        /*
         *
         * */
        app.patch("reimbursements/decision", ReimbursementController::updateReimbursement);

    }
}
