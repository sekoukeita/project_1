package daos;

import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    // MEMBER VARIABLES: Strings needed for jdbc connection
    String url;
    String username;
    String password;

    // CONSTRUCTORS

    public UserDaoImpl() {
        this.url = "jdbc:postgresql://" + System.getenv("AWS_RDS_ENDPOINT") + "/ersdatabase";
        this.username = System.getenv("RDS_USERNAME");
        this.password = System.getenv("RDS_PASSWORD");
    }
        // to use with the H2
    public UserDaoImpl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "SELECT * FROM users;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getInt(7), rs.getString(8)));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User getUser(Integer userId) {
        User user = null;

        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "SELECT * FROM users WHERE ers_users_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                user = new User(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getInt(7), rs.getString(8));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void createUser(User user) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "INSERT INTO ers_users values(DEFAULT, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getEmail());
            ps.setInt(6, user.getRoleId());

            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "UPDATE ers_users\n" +
                    "SET ers_username = ?,\n" +
                    "ers_password = ?,\n" +
                    "user_first_name = ?,\n" +
                    "user_last_name = ?,\n" +
                    "user_email = ?,\n" +
                    "user_role_id = ?\n" +
                    "WHERE ers_users_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getEmail());
            ps.setInt(6, user.getRoleId());
            ps.setInt(7, user.getUserId());

            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(Integer userId) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "DELETE FROM ers_users WHERE ers_users_id  = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, userId);

            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
