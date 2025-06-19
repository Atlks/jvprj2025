

callInTransaction  jpa3.2  btr...dont 手动commit rollback  

    @Deprecated
    public static void begin() {


public class TxMng {
    public static Object callInTransaction(FunctionX<EntityManager, Object> o) {

        EntityManager em = sessionFactory.createEntityManager();
        ThreadContext.currEttyMngr.set(em);
        Session session = em.unwrap(Session.class);
        currSession.set(session);
        return session.doReturningWork(new ReturningWork<Object>() {

            @Override
            public Object execute(@UnknownKeyFor @NonNull @Initialized Connection connection) {
                try {
                    EntityTransaction transaction = em.getTransaction();
                    transaction.begin();
                    currEntityTransaction.set(transaction);


                    Object rzt = o.apply(null);



                    transaction.commit();
                    return rzt;

                } catch (Throwable e) {
                    // e.printStackTrace();
                    currEntityTransaction.get().rollback();
                    throwX(e);

                } finally {

                }
                return "";
            }
        });

        //  return o.apply(null);
    }
