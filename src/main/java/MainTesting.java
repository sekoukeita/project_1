import daos.ReimbursementDao;
import daos.ReimbursementDaoImpl;
import daos.UserDao;
import daos.UserDaoImpl;
import io.javalin.http.util.JsonEscapeUtil;
import models.Reimbursement;
import models.User;
import services.ReimbursementService;
import services.UserService;
import util.H2Util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class MainTesting {

    public static void main(String[] args) {

        //////////// Test UserDao////////////////////

        UserDao userDao = new UserDaoImpl();
        /*List<User> list = userDao.getUsers();
        System.out.println(list);
        System.out.println(list.size());*/

       /* User user1 = userDao.getUser(8);
        User user2 = userDao.getUser("Asco");
        System.out.println("user 1:" + user1);
        System.out.println("user 2" + user2);*/

        /*User user2 = new User("secksonr9", "17071980", "Sekou", "Keita", "K_sekou9@yahoo.fr", 2);
        userDao.createUser(user2);
        System.out.println(user2);*/

        /*User user3 = new User(2, "seckson", "17071980", "Sekou", "Keita", "K_sekou@yahoo.fr", 2);
        userDao.updateUser(user3);
        System.out.println(user3);*/

        //userDao.deleteUser(2);

        //////////////// Test ReimbursementDao ///////////////////////
        ReimbursementDao reimbDao = new ReimbursementDaoImpl();

        /*List<Reimbursement> reimbursements = reimbDao.getReimbursements();
        System.out.println(reimbursements);
        System.out.println(reimbursements.size());
*/
        /*List<Reimbursement> reimbursements = reimbDao.getEmployeeReimbursements(6);
        System.out.println(reimbursements);
        System.out.println(reimbursements.size());*/

        Reimbursement reimbursement = reimbDao.getReimbursement(2);
        System.out.println(reimbursement);

        /*Reimbursement reimb = new Reimbursement(1.0,
                Timestamp.valueOf(LocalDateTime.of(2021, 12, 1, 10, 50, 0)),
                "Exam prep", 6, 1, 4);
        reimbDao.createReimbursement(reimb);*/

        //reimbDao.deleteReimbursement(11);

        ///////////////////////////// Test H2 //////////////////////////////
       /* H2Util.createTables();
        H2Util.dropTables();*/

        //////////////////////Test userService //////////////////////////
        UserService us = new UserService();

        /*List<User> list = us.getUsers();
        System.out.println(list);
        System.out.println(list.size());*/

        /*User user1 = us.getUser(4);
        User user2 = us.getUser("keita");
        System.out.println("user1 " + user1);
        System.out.println("user2 " + user2);*/

        /*User user = new User("huskei", "ak", "", "Keallita", "gsdfhtfkgjjn@gmail.com", 2);
        us.createUser(user);*/

        /*User user = new User(17, "s", "017071980", "Sekou", "Keita", "aK_sekou@yahoo", 1);
        us.updateUser(user);
        System.out.println(user);*/

        //us.deleteUser(14);

        //////////////////////Test reimbursementService //////////////////////////
        ReimbursementService rs = new ReimbursementService();

        /*List<Reimbursement> list = rs.getReimbursements();
        System.out.println(list);
        System.out.println(list.size());*/

       /* List<Reimbursement> list = rs.getEmployeeReimbursements(4);
        System.out.println(list);
        System.out.println(list.size());*/

        /*Reimbursement reimbursement = rs.getReimbursement(8);
        System.out.println(reimbursement);*/

       Reimbursement reimb = new Reimbursement(18.0,
               Timestamp.valueOf(LocalDateTime.of(2021, 12, 1, 15, 30)),
                "Exam prep", 6, 1, 4);
        rs.createReimbursement(reimb);

        /*rs.updateReimbursement(12, Timestamp.valueOf(LocalDateTime.now()),
                4, 2);
*/
        //rs.deleteReimbursement(15);















    }













}
