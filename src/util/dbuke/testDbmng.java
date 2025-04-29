package util.dbuke;

import entityx.usr.Usr;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transaction;
import jakarta.transaction.TransactionManager;

public class testDbmng {

    public static void main(String[] args) throws Exception {
        System.out.println(Transaction.class);

        DbMng dbMng = new DbMng("/0dbx2");
        Transaction tm=  dbMng.beginTx();

        try{
            System.out.println(EntityTransaction.class);
            System.out.println(TransactionManager.class);

            Usr u=new Usr("123");
            u.email="111@uke";

            dbMng.persist(u);

            System.out.println("..");
            if("1"!="2")
                throw new RuntimeException("not eq");

            Usr u2=new Usr("345");
            u2.email="345@uke";
            dbMng.persist(u2);
            tm.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tm.rollback();
        }finally {
            //close
        }



    }
}
