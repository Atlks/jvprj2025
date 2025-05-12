package util.dbuke;

import model.usr.Usr;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transaction;
import jakarta.transaction.TransactionManager;

public class testConn {

    public static void main(String[] args) throws Exception {
        System.out.println(Transaction.class);
        //dont need ssnFctry
        ConnSession conn1 = new ConnSession("/0dbx2");
        Transaction tx=  conn1.beginTx();

        try{
            System.out.println(EntityTransaction.class);
            System.out.println(TransactionManager.class);

            Usr u=new Usr("123");
            u.email="111new@uke";

            conn1.merge(u);

            System.out.println("..");
            if("1"!="2")
                throw new RuntimeException("not eq");

            Usr u2=new Usr("345");
            u2.email="345@uke";
            conn1.merge(u2);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally {
            //close
        }



    }
}
