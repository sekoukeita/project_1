package controllers;

import dto.UserDto;
import io.javalin.http.Context;
import models.User;
import services.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserController {

    // reference the userService
    static UserService userService = new UserService();

    // Methods : implementation of handlers
    public static void getUsers(Context ctx){
        List<User> users = userService.getUsers(); //get the list of users with all variables on each user object.
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users){
            // create the list of userDtos that is user objects without password and email variables
            userDtos.add(new UserDto(user.getUserId(), user.getUserName(), user.getFirstName(), user.getLastName(),
                    user.getRoleId(), user.getRole()));
        }
        ctx.json(userDtos);
    }

    public static void getUser(Context ctx){
        Integer userId = Integer.parseInt(ctx.pathParam("id"));
        User user = userService.getUser(userId);
        UserDto userDto = new UserDto(user.getUserId(), user.getUserName(), user.getFirstName(), user.getLastName(),
                user.getRoleId(), user.getRole());
        ctx.json(userDto);
    }

    public static void createUser(Context ctx) {
        User user = ctx.bodyAsClass(User.class);

        if ((user.getUserName().length() > 50) | (userService.getListOfUsernames().contains(user.getUserName())) | (user.getUserName().isEmpty())){
            if (user.getUserName().length() > 50){
                ctx.result("The username should not be longer than 50 characters!");
            }
            if (userService.getListOfUsernames().contains(user.getUserName())){
                ctx.result("This username already exists!. Enter another one.");
            }
            if (user.getUserName().isEmpty())
                ctx.result("Enter a username, please!");
        }
        else if ((user.getPassword().length() > 50) | (user.getPassword().isEmpty())){
            if (user.getPassword().length() > 50){
                ctx.result("The password should not be longer than 50 characters!");
            }
            if (user.getPassword().isEmpty())
                ctx.result("Enter a password, please!");
        }
        else if (user.getFirstName().length() > 50){
            ctx.result("The first name should be less than 50 characters");
        }
        else if (user.getLastName().length() > 50){
            ctx.result("The last name should be less than 50 characters");
        }
        else if ((user.getEmail().length() > 50) | (userService.getListOfEmails().contains(user.getEmail())) | (user.getEmail().isEmpty())){
            if (user.getEmail().length() > 50){
                ctx.result("The email should not be longer than 50 characters!");
            }
            if (userService.getListOfEmails().contains(user.getEmail())){
                ctx.result("This email already exists!. Enter another one.");
            }
            if (user.getEmail().isEmpty())
                ctx.result("Enter an email, please!");
        }
        else{
            userService.createUser(user);
            ctx.result("Congratulation!. You have successfully been added in the ERS system");
        }
    }

    public static void updateUser(Context ctx){
        User user = ctx.bodyAsClass(User.class);
        userService.updateUser(user);
    }

    public static void deleteUser(Context ctx){
        Integer userId = Integer.parseInt(ctx.pathParam("id"));
        userService.deleteUser(userId);
        ctx.result("The user with id has been successfully deleted");
    }

}
