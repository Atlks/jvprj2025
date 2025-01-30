package test;

import apiUsr.Usr;
import util.JdoPstsMngrIni;

import javax.jdo.PersistenceManager;

public class jdoTest {

    public static void main(String[] args) {
        PersistenceManager pm=newPersistenceManager("/0db");
        Usr u=new Usr();
        u.uname="777";
        u.id=u.uname;
        pm.makePersistent(u);
    }

    private static PersistenceManager newPersistenceManager(String url) {
          if(url.startsWith("jdbc:mysql")){

          }
          return  new JdoPstsMngrIni(url);

    }
}
