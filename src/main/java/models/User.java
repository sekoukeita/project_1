package models;

public class User {

    // MEMBER VARIABLES

    private Integer userId;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Integer roleId;
    private String role;

    // CONSTRUCTORS
        // use by jackson library to create the user object form the body using ctx.bodyAsClass();
    public User() {
    }

    public User(Integer userId, String userName, String password, String firstName, String lastName, String email,
                Integer roleId, String role) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roleId = roleId;
        this.role = role;
    }

    public User(String userName, String password, Integer roleId) {
        this.userName = userName;
        this.password = password;
        this.roleId = roleId;
    }

    // constructor without userId( because default in the sql) and role (because not in the original user table)
        // to use in the sql insert
    public User(String userName, String password, String firstName, String lastName, String email,
                Integer roleId) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roleId = roleId;
    }

    // constructor with userId( to use in the update sql) and without role (because not in the original user table)
    public User(Integer userId, String userName, String password, String firstName, String lastName, String email,
                Integer roleId) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roleId = roleId;
    }

    // GETTERS AND SETTERS

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // METHODS OVERRIDDEN

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", roleId=" + roleId +
                ", role='" + role + '\'' +
                '}';
    }
}
