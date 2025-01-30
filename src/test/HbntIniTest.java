package test;

import apiUsr.Usr;
import org.hibernate.Session;
import utilBiz.OrmUtilBiz;

import java.sql.SQLException;

import static apiUsr.RegHandler.saveDirUsrs;

public class HbntIniTest {

    public static void main(String[] args) throws SQLException {
        Session session = OrmUtilBiz. openSession("/0dbIni/");
        //todo start tx
        session.beginTransaction();
        Usr u=new Usr();
        u.id="007";
        session.persist(u);
        session.getTransaction().commit();;
    }
}
