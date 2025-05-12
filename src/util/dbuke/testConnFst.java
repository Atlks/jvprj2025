package util.dbuke;

import model.usr.Usr;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transaction;
import jakarta.transaction.TransactionManager;

public class testConnFst {

    public static void main(String[] args) throws Exception {
        System.out.println(Transaction.class);
        //dont need ssnFctry
        ConnSession conn1 = new ConnSession("/0dbx2");
        Transaction tx=  conn1.beginTx();

        try{
            System.out.println(EntityTransaction.class);
            System.out.println(TransactionManager.class);

            Usr u=new Usr("123");
            u.email="111@uke";

            conn1.merge(u);

            System.out.println("..");


            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally {
            //close
        }



    }
}
