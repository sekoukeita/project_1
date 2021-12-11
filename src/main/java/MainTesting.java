import daos.ReimbursementDao;
import daos.ReimbursementDaoImpl;
import daos.UserDao;
import daos.UserDaoImpl;
import io.javalin.http.util.JsonEscapeUtil;
import models.Reimbursement;
import models.User;
import util.H2Util;

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

        /*User user = userDao.getUser(4);
        System.out.println(user);*/

        /*User user2 = new User("secksonr9", "17071980", "Sekou", "Keita", "K_sekou9@yahoo.fr", 2);
        userDao.createUser(user2);
        System.out.println(user2);*/

        /*User user3 = new User(2, "seckson", "17071980", "Sekou", "Keita", "K_sekou@yahoo.fr", 2);
        userDao.updateUser(user3);
        System.out.println(user3);*/

        //userDao.deleteUser(2);

        //////////////// Test ReimbursementDao ///////////////////////
        ReimbursementDao reimbDao = new ReimbursementDaoImpl();

       /* List<Reimbursement> reimbursements = reimbDao.getReimbursements();
        System.out.println(reimbursements);
        System.out.println(reimbursements.size());*/

       /* Reimbursement reimbursement = reimbDao.getReimbursement(2);
        System.out.println(reimbursement);*/

        Reimbursement reimb = new Reimbursement(2000.0, LocalDateTime.of(2021, 12, 1, 0, 0),
                "Exam prep", 6, 1, 4);
        reimbDao.createReimbursement(reimb);

        //reimbDao.deleteReimbursement(11);

       /* H2Util.createTables();
        H2Util.dropTables();*/















    }













}
