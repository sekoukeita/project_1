package daos;

import models.Reimbursement;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDaoImpl implements ReimbursementDao {

    // MEMBER VARIABLES: Strings needed for jdbc connection
    String url;
    String username;
    String password;

    // create the logger object to log events in the file project_1.log
    Logger logger = Logger.getLogger(ReimbursementDaoImpl.class);

    // CONSTRUCTORS

    public ReimbursementDaoImpl() {
        this.url = "jdbc:postgresql://" + System.getenv("AWS_RDS_ENDPOINT") + "/ersdatabase";
        this.username = System.getenv("RDS_USERNAME");
        this.password = System.getenv("RDS_PASSWORD");
    }

    public ReimbursementDaoImpl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Reimbursement> getReimbursements() {
        List<Reimbursement> reimbursements = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "SELECT * FROM reimbursement;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                reimbursements.add(new Reimbursement(rs.getInt(1), rs.getDouble(2),
                        // structure to manage java LocalDateTime and postgresql timestamp
                        rs.getObject(3, LocalDateTime.class), rs.getObject(4, LocalDateTime.class),
                        rs.getString(5), rs.getBytes(6), rs.getInt(7),
                        rs.getString(8),rs.getString(9), rs.getInt(10), rs.getString(11),
                        rs.getString(12), rs.getInt(13), rs.getString(14), rs.getInt(15),
                        rs.getString(16)));
            }
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return reimbursements;
    }

    @Override
    public Reimbursement getReimbursement(Integer reimbursementId) {
        Reimbursement reimbursement = null;

        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "SELECT * FROM reimbursement WHERE reimb_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, reimbursementId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                reimbursement = new Reimbursement(rs.getInt(1), rs.getDouble(2),
                        rs.getObject(3, LocalDateTime.class), rs.getObject(4, LocalDateTime.class),
                        rs.getString(5), rs.getBytes(6), rs.getInt(7),
                        rs.getString(8),rs.getString(9), rs.getInt(10), rs.getString(11),
                        rs.getString(12), rs.getInt(13), rs.getString(14), rs.getInt(15),
                        rs.getString(16));
            }
        }
        catch (SQLException e) {
            logger.error(e);
        }

        return reimbursement;
    }

    @Override
    public void createReimbursement(Reimbursement reimbursement) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            /*
            * first: The id which is serial, define by the database
            * second: the receipt is not yet processed by the program.
            * the 2 others: information about resolution of the reimbursement. Not available at the creation
            * of the reimbursement submission. Will be provided at the update ()
            *  */

            String sql = "INSERT INTO ers_reimbursement values(DEFAULT, ?, ?, DEFAULT, ?, DEFAULT, ?, DEFAULT, ?, ?);";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDouble(1, reimbursement.getAmount());
            ps.setObject(2, reimbursement.getDateSubmitted());
            ps.setString(3, reimbursement.getDescription());
            ps.setInt(4, reimbursement.getAuthorId());
            ps.setInt(5, reimbursement.getStatusId());
            ps.setInt(6, reimbursement.getTypeId());

            ps.executeUpdate();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void updateReimbursement(Integer reimbursementId, LocalDateTime dateResolved, Integer resolverId, Integer statusId) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "UPDATE ers_reimbursement SET reimb_resolved = ?, reimb_resolver = ?, reimb_status_id = ? WHERE reimb_id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setObject(1, dateResolved);
            ps.setInt(2, resolverId);
            ps.setInt(3, statusId);
            ps.setInt(4, reimbursementId);

            ps.executeUpdate();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void deleteReimbursement(Integer reimbursementId) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "DELETE FROM ers_reimbursement WHERE reimb_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, reimbursementId);

            ps.executeUpdate();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }
}
