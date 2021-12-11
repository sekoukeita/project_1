package util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class H2Util {

    // connection strings
    public static String url = "jdbc:h2:./h2/db"; // where the H2 database will be stored(./h2/db)
    public static String username = "sa";
    public static String password = "sa";

    // static logger variable to log the SQLException
    public static Logger logger = Logger.getLogger(H2Util.class);


    // no constructor because the class variables and method will be called on a class scope

    // Methods
        /*
        * The application database has 5 tables and 2 views
        * When creating the look-up tables, their records should also be inserted
        * */

    public static void createTables(){
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "CREATE TABLE ERS_REIMBURSEMENT_STATUS (\n" +
                    "\tREIMB_STATUS_ID serial PRIMARY KEY,\n" +
                    "\tREIMB_STATUS varchar(10)\n" +
                    ");" +
                    "INSERT INTO ERS_REIMBURSEMENT_STATUS VALUES (DEFAULT, 'Pending');\n" +
                    "INSERT INTO ERS_REIMBURSEMENT_STATUS VALUES (DEFAULT, 'Approved');\n" +
                    "INSERT INTO ERS_REIMBURSEMENT_STATUS VALUES (DEFAULT, 'Denied');" +
                    "CREATE TABLE ERS_REIMBURSEMENT_TYPE (\n" +
                    "\tREIMB_TYPE_ID serial PRIMARY KEY,\n" +
                    "\tREIMB_TYPE varchar(10)\n" +
                    ");" +
                    "INSERT INTO ERS_REIMBURSEMENT_TYPE VALUES (DEFAULT, 'LODGING');\n" +
                    "INSERT INTO ERS_REIMBURSEMENT_TYPE VALUES (DEFAULT, 'TRAVEL');\n" +
                    "INSERT INTO ERS_REIMBURSEMENT_TYPE VALUES (DEFAULT, 'FOOD');\n" +
                    "INSERT INTO ERS_REIMBURSEMENT_TYPE VALUES (DEFAULT, 'OTHER');" +
                    "CREATE TABLE ERS_USER_ROLES (\n" +
                    "\tERS_USER_ROLE_ID serial PRIMARY KEY,\n" +
                    "\tUSER_ROLE varchar(15)\n" +
                    ");" +
                    "INSERT INTO ERS_USER_ROLES VALUES (DEFAULT, 'Employee');\n" +
                    "INSERT INTO ERS_USER_ROLES VALUES (DEFAULT, 'Finance Manager');" +
                    "CREATE TABLE ERS_USERS (\n" +
                    "\tERS_USERS_ID serial PRIMARY KEY,\n" +
                    "\tERS_USERNAME varchar(50) UNIQUE NOT NULL,\n" +
                    "\tERS_PASSWORD varchar(50) NOT NULL,\n" +
                    "\tUSER_FIRST_NAME varchar(100),\n" +
                    "\tUSER_LAST_NAME varchar(100),\n" +
                    "\tUSER_EMAIL varchar(150) UNIQUE NOT NULL,\n" +
                    "\tUSER_ROLE_ID int,\n" +
                    "\tFOREIGN KEY (USER_ROLE_ID) REFERENCES ERS_USER_ROLES (ERS_USER_ROLE_ID)\n" +
                    ");" +
                    "CREATE TABLE ERS_REIMBURSEMENT(\n" +
                    "\tREIMB_ID serial PRIMARY KEY,\n" +
                    "\tREIMB_AMOUNT double PRECISION,\n" +
                    "\tREIMB_SUBMITTED timestamp DEFAULT now(),\n" +
                    "\tREIMB_RESOLVED timestamp DEFAULT NULL,\n" +
                    "\tREIMB_DESCRIPTION varchar(250),\n" +
                    "\tREIMB_RECEIPT bytea DEFAULT NULL,\n" +
                    "\tREIMB_AUTHOR int,\n" +
                    "\tREIMB_RESOLVER int DEFAULT NULL,\n" +
                    "\tREIMB_STATUS_ID int DEFAULT 1,\n" +
                    "\tREIMB_TYPE_ID int DEFAULT 4,\n" +
                    "\tFOREIGN KEY (REIMB_AUTHOR) REFERENCES ERS_USERS (ERS_USERS_ID) ON DELETE CASCADE,\n" +
                    "\tFOREIGN KEY (REIMB_RESOLVER) REFERENCES ERS_USERS (ERS_USERS_ID) ON DELETE CASCADE,\n" +
                    "\tFOREIGN KEY (REIMB_STATUS_ID) REFERENCES ERS_REIMBURSEMENT_STATUS (REIMB_STATUS_ID),\n" +
                    "\tFOREIGN KEY (REIMB_TYPE_ID) REFERENCES ERS_REIMBURSEMENT_TYPE (REIMB_TYPE_ID)\t\n" +
                    ");" +
                    "CREATE VIEW users AS \n" +
                    "SELECT eus.ers_users_id, eus.ers_username, \n" +
                    "eus.ers_password, eus.user_first_name, \n" +
                    "eus.user_last_name, eus.user_email, eus.user_role_id,\n" +
                    "ero.user_role\n" +
                    "FROM ers_users eus  \n" +
                    "INNER JOIN ers_user_roles ero\n" +
                    "ON eus.user_role_id = ero.ers_user_role_id;" +
                    "CREATE VIEW reimbursement AS \n" +
                    "SELECT ere.reimb_id, ere.reimb_amount, \n" +
                    "ere.reimb_submitted, ere.reimb_resolved, \n" +
                    "ere.reimb_description, ere.reimb_receipt,\n" +
                    "ere.reimb_author, eus.user_first_name AS author_first_name, eus.user_last_name AS author_last_name,\n" +
                    "ere.reimb_resolver, eus2.user_first_name AS resolver_first_name , eus2.user_last_name AS resolver_last_name,\n" +
                    "ere.reimb_status_id, est.reimb_status,\n" +
                    "ere.reimb_type_id, ety.reimb_type\n" +
                    "FROM ers_reimbursement ere \n" +
                    "INNER JOIN ers_reimbursement_status est\n" +
                    "ON ere.reimb_status_id = est.reimb_status_id \n" +
                    "INNER JOIN ers_reimbursement_type ety\n" +
                    "ON ere.reimb_type_id = ety.reimb_type_id\n" +
                    "INNER JOIN ers_users eus \n" +
                    "ON ere.reimb_author = eus.ers_users_id\n" +
                    "LEFT JOIN ers_users eus2 \n" +
                    "ON ere.reimb_resolver = eus2.ers_users_id\n" +
                    "INNER JOIN ers_user_roles ero \n" +
                    "ON eus.user_role_id = ero.ers_user_role_id;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        }
        catch (SQLException e){
            logger.error(e);
        }
    }

    public static void dropTables(){
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "DROP VIEW reimbursement;" +
                    "DROP TABLE ERS_REIMBURSEMENT;" +
                    "DROP VIEW users;" +
                    "DROP TABLE ERS_USERS;" +
                    "DROP TABLE ERS_USER_ROLES;" +
                    "DROP TABLE ERS_REIMBURSEMENT_STATUS;" +
                    "DROP TABLE ERS_REIMBURSEMENT_TYPE;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        }
        catch (SQLException e){
            logger.error(e);
        }
    }
}
