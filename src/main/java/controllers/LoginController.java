package controllers;

import dto.JsonResponse;
import dto.UserDto;
import io.javalin.http.Context;
import models.User;
import services.UserService;

import java.util.Objects;

public class LoginController {

    static UserService userService = new UserService();

    // Methods
    public static void login(Context ctx){
        User user = ctx.bodyAsClass(User.class); // user object gets username, password and role id form the request body

        String passwordFromLogin = user.getPassword();
        String usernameFromLogin = user.getUserName();

        String passwordFromDb = userService.getUser(usernameFromLogin).getPassword();
        String usernameFromDb = userService.getUser(usernameFromLogin).getUserName();

        if (Objects.equals(passwordFromLogin, passwordFromDb) && Objects.equals(usernameFromLogin, usernameFromDb)){
            ctx.sessionAttribute("session", user);
            User userFromDB = userService.getUser(usernameFromLogin);
            UserDto userDto = new UserDto(userFromDB.getUserId(), userFromDB.getUserName(), userFromDB.getFirstName(),
                    userFromDB.getLastName(), userFromDB.getRoleId(), userFromDB.getRole());
            ctx.json(new JsonResponse(true, "Login successful", userDto));
        }
        else {
            ctx.result("Wrong username or password");
        }
    }

    public static void session(Context ctx){
        User user = ctx.sessionAttribute("session");

        if (user == null){
            ctx.json(new JsonResponse(false, "No session found", null));
        }
        else {
            String usernameFromLogin = user.getUserName();
            User userFromDB = userService.getUser(usernameFromLogin);
            UserDto userDto = new UserDto(userFromDB.getUserId(), userFromDB.getUserName(), userFromDB.getFirstName(),
                    userFromDB.getLastName(), userFromDB.getRoleId(), userFromDB.getRole());
            ctx.json(new JsonResponse(true, "Session found", userDto));
        }
    }

    public static void logout(Context ctx){
        ctx.sessionAttribute("session", null);
        ctx.json(new JsonResponse(false, "You have successfully been logged out!", null));
    }

}
