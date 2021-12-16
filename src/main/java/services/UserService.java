package services;

import daos.UserDao;
import daos.UserDaoImpl;
import models.User;

import java.util.ArrayList;
import java.util.List;


public class UserService {
    // References the userDao
    UserDao userDao;

    // Constructors
    public UserService() {
        this.userDao = new UserDaoImpl();
    }

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    //Methods

    public List<User> getUsers(){
        return userDao.getUsers();
    }

    public User getUser(Integer userId){
        return userDao.getUser(userId);
    }

    public User getUser(String username){
        return userDao.getUser(username);
    }

    public Boolean createUser(User user){

        if ((user.getUserName().length() > 50) | (getListOfUsernames().contains(user.getUserName())) | (user.getUserName().isEmpty())){
            if (user.getUserName().length() > 50){
                System.out.println("The username should not be longer than 50 characters!");
            }
            if (getListOfUsernames().contains(user.getUserName())){
                System.out.println("This username already exists!. Enter another one.");
            }
            if (user.getUserName().isEmpty()){
                System.out.println("Enter a username, please!");
            }
            return false;
        }
        else if ((user.getPassword().length() > 50) | (user.getPassword().isEmpty())){
            if (user.getPassword().length() > 50){
                System.out.println("The password should not be longer than 50 characters!");
            }
            if (user.getPassword().isEmpty())
                System.out.println("Enter a password, please!");
            return false;
        }
        else if (user.getFirstName().length() > 50){
            System.out.println("The first name should be less than 50 characters");
            return false;
        }
        else if (user.getLastName().length() > 50){
            System.out.println("The last name should be less than 50 characters");
            return false;
        }
        else if ((user.getEmail().length() > 50) | (getListOfEmails().contains(user.getEmail())) | (user.getEmail().isEmpty())){
            if (user.getEmail().length() > 50){
                System.out.println("The email should not be longer than 50 characters!");
            }
            if (getListOfEmails().contains(user.getEmail())){
                System.out.println("This email already exists!. Enter another one.");
            }
            if (user.getEmail().isEmpty())
                System.out.println("Enter an email, please!");
            return false;
        }
        else{
            userDao.createUser(user);
            System.out.println("The user has been successfully added!");
            return true;
        }
    }

    public void updateUser(User user){
        userDao.updateUser(user);
    }

    public void deleteUser(Integer userId){
        userDao.deleteUser(userId);
    }


            // Helper Methods

    public List<String> getListOfUsernames(){
        List<String> listOfUserNames = new ArrayList<>();
        List<User> users = userDao.getUsers();
        for (User user : users){
            listOfUserNames.add(user.getUserName());
        }
        return listOfUserNames;
    }

    public List<String> getListOfEmails(){
        List<String> listOfEmails = new ArrayList<>();
        List<User> users = userDao.getUsers();
        for (User user : users){
            listOfEmails.add(user.getEmail());
        }
        return listOfEmails;
    }
}
