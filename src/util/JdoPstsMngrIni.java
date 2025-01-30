package util;

import javax.jdo.*;
import javax.jdo.datastore.JDOConnection;
import javax.jdo.datastore.Sequence;
import javax.jdo.listener.InstanceLifecycleListener;

import java.io.File;
import java.util.*;

import static util.Util2025.*;
import static util.UtilEncode.encodeFilename;
import static util.dbutil.*;
import static util.util2026.getField2025;
import static util.util2026.throwEx;

public class JdoPstsMngrIni implements PersistenceManager {
    public String saveDir;

    public JdoPstsMngrIni(String saveDir0) {
        mkdir2025(saveDir0);
        saveDir=saveDir0;
    }

    public <T> T makePersistent(T obj) {
        // PersistenceManager
        String fname = (String) getField2025(obj, "id", "");
        //todo need fname encode
        fname = encodeFilename(fname) + ".ini";

        String fnamePath = saveDir + "/" + fname;
        System.out.println("fnamePath=" + fnamePath);
        writeFile2501(fnamePath, encodeIni(obj));

        return obj;
    }

    /**
     * @param aClass
     * @param id
     * @param <T>
     * @return
     */
    @Override
    public <T> T getObjectById(Class<T> aClass, Object id) {
        try {
            mkdir2025(saveDir);
            //encodeFilName todo
            String fname = id + ".ini";
            String fnamePath = saveDir + "/" + fname;
            if (new File(fnamePath).exists()) {
                String text = readTxtFrmFil(fnamePath);
                System.out.println("getobjDoc().txt=" + text);
                SortedMap<String, Object> mapFrmInicontext = toMapFrmInicontext(text);
                return (T) toObj(mapFrmInicontext, aClass);
            }

            //   if(!new File(fnamePath).exists())
            else
                return (T) toObj(toMapFrmInicontext(""), aClass);

        } catch (Exception e) {
            throwEx(e);
        }
        return null;
    }

    /**
     * @return
     */
    @Override
    public boolean isClosed() {
        return false;
    }

    /**
     *
     */
    @Override
    public void close() {

    }

    /**
     * @return
     */
    @Override
    public Transaction currentTransaction() {
        return null;
    }

    /**
     * @param o
     */
    @Override
    public void evict(Object o) {

    }

    /**
     * @param objects
     */
    @Override
    public void evictAll(Object... objects) {

    }

    /**
     * @param collection
     */
    @Override
    public void evictAll(Collection collection) {

    }

    /**
     * @param b
     * @param aClass
     */
    @Override
    public void evictAll(boolean b, Class aClass) {

    }

    /**
     *
     */
    @Override
    public void evictAll() {

    }

    /**
     * @param o
     */
    @Override
    public void refresh(Object o) {

    }

    /**
     * @param objects
     */
    @Override
    public void refreshAll(Object... objects) {

    }

    /**
     * @param collection
     */
    @Override
    public void refreshAll(Collection collection) {

    }

    /**
     *
     */
    @Override
    public void refreshAll() {

    }

    /**
     * @param e
     */
    @Override
    public void refreshAll(JDOException e) {

    }

    /**
     * @return
     */
    @Override
    public Query newQuery() {
        return null;
    }

    /**
     * @param o
     * @return
     */
    @Override
    public Query newQuery(Object o) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public Query newQuery(String s) {
        return null;
    }

    /**
     * @param s
     * @param o
     * @return
     */
    @Override
    public Query newQuery(String s, Object o) {
        return null;
    }

    /**
     * @param aClass
     * @param <T>
     * @return
     */
    @Override
    public <T> Query<T> newQuery(Class<T> aClass) {
        return null;
    }

    /**
     * @param extent
     * @param <T>
     * @return
     */
    @Override
    public <T> Query<T> newQuery(Extent<T> extent) {
        return null;
    }

    /**
     * @param aClass
     * @param collection
     * @param <T>
     * @return
     */
    @Override
    public <T> Query<T> newQuery(Class<T> aClass, Collection<T> collection) {
        return null;
    }

    /**
     * @param aClass
     * @param s
     * @param <T>
     * @return
     */
    @Override
    public <T> Query<T> newQuery(Class<T> aClass, String s) {
        return null;
    }

    /**
     * @param aClass
     * @param collection
     * @param s
     * @param <T>
     * @return
     */
    @Override
    public <T> Query<T> newQuery(Class<T> aClass, Collection<T> collection, String s) {
        return null;
    }

    /**
     * @param extent
     * @param s
     * @param <T>
     * @return
     */
    @Override
    public <T> Query<T> newQuery(Extent<T> extent, String s) {
        return null;
    }

    /**
     * @param aClass
     * @param <T>
     * @return
     */
    @Override
    public <T> JDOQLTypedQuery<T> newJDOQLTypedQuery(Class<T> aClass) {
        return null;
    }

    /**
     * @param aClass
     * @param s
     * @param <T>
     * @return
     */
    @Override
    public <T> Query<T> newNamedQuery(Class<T> aClass, String s) {
        return null;
    }

    /**
     * @param aClass
     * @param b
     * @param <T>
     * @return
     */
    @Override
    public <T> Extent<T> getExtent(Class<T> aClass, boolean b) {
        return null;
    }

    /**
     * @param aClass
     * @param <T>
     * @return
     */
    @Override
    public <T> Extent<T> getExtent(Class<T> aClass) {
        return null;
    }

    /**
     * @param o
     * @param b
     * @return
     */
    @Override
    public Object getObjectById(Object o, boolean b) {
        return null;
    }


    /**
     * @param o
     * @return
     */
    @Override
    public Object getObjectById(Object o) {
        return null;
    }

    /**
     * @param o
     * @return
     */
    @Override
    public Object getObjectId(Object o) {
        return null;
    }

    /**
     * @param o
     * @return
     */
    @Override
    public Object getTransactionalObjectId(Object o) {
        return null;
    }

    /**
     * @param aClass
     * @param o
     * @return
     */
    @Override
    public Object newObjectIdInstance(Class aClass, Object o) {
        return null;
    }

    /**
     * @param collection
     * @param b
     * @return
     */
    @Override
    public Collection getObjectsById(Collection collection, boolean b) {
        return List.of();
    }

    /**
     * @param collection
     * @return
     */
    @Override
    public Collection getObjectsById(Collection collection) {
        return List.of();
    }

    /**
     * @param b
     * @param objects
     * @return
     */
    @Override
    public Object[] getObjectsById(boolean b, Object... objects) {
        return new Object[0];
    }

    /**
     * @param objects
     * @return
     */
    @Override
    public Object[] getObjectsById(Object... objects) {
        return new Object[0];
    }

    /**
     * @param ts
     * @param <T>
     * @return
     */
    @Override
    public <T> T[] makePersistentAll(T... ts) {
        return null;
    }

    /**
     * @param collection
     * @param <T>
     * @return
     */
    @Override
    public <T> Collection<T> makePersistentAll(Collection<T> collection) {
        return List.of();
    }

    /**
     * @param o
     */
    @Override
    public void deletePersistent(Object o) {

    }

    /**
     * @param objects
     */
    @Override
    public void deletePersistentAll(Object... objects) {

    }

    /**
     * @param collection
     */
    @Override
    public void deletePersistentAll(Collection collection) {

    }

    /**
     * @param o
     */
    @Override
    public void makeTransient(Object o) {

    }

    /**
     * @param objects
     */
    @Override
    public void makeTransientAll(Object... objects) {

    }

    /**
     * @param collection
     */
    @Override
    public void makeTransientAll(Collection collection) {

    }

    /**
     * @param o
     * @param b
     */
    @Override
    public void makeTransient(Object o, boolean b) {

    }

    /**
     * @param b
     * @param objects
     */
    @Override
    public void makeTransientAll(boolean b, Object... objects) {

    }

    /**
     * @param collection
     * @param b
     */
    @Override
    public void makeTransientAll(Collection collection, boolean b) {

    }

    /**
     * @param o
     */
    @Override
    public void makeTransactional(Object o) {

    }

    /**
     * @param objects
     */
    @Override
    public void makeTransactionalAll(Object... objects) {

    }

    /**
     * @param collection
     */
    @Override
    public void makeTransactionalAll(Collection collection) {

    }

    /**
     * @param o
     */
    @Override
    public void makeNontransactional(Object o) {

    }

    /**
     * @param objects
     */
    @Override
    public void makeNontransactionalAll(Object... objects) {

    }

    /**
     * @param collection
     */
    @Override
    public void makeNontransactionalAll(Collection collection) {

    }

    /**
     * @param o
     */
    @Override
    public void retrieve(Object o) {

    }

    /**
     * @param o
     * @param b
     */
    @Override
    public void retrieve(Object o, boolean b) {

    }

    /**
     * @param collection
     */
    @Override
    public void retrieveAll(Collection collection) {

    }

    /**
     * @param collection
     * @param b
     */
    @Override
    public void retrieveAll(Collection collection, boolean b) {

    }

    /**
     * @param objects
     */
    @Override
    public void retrieveAll(Object... objects) {

    }

    /**
     * @param b
     * @param objects
     */
    @Override
    public void retrieveAll(boolean b, Object... objects) {

    }

    /**
     * @param o
     */
    @Override
    public void setUserObject(Object o) {

    }

    /**
     * @return
     */
    @Override
    public Object getUserObject() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public PersistenceManagerFactory getPersistenceManagerFactory() {
        return null;
    }

    /**
     * @param aClass
     * @return
     */
    @Override
    public Class getObjectIdClass(Class aClass) {
        return null;
    }

    /**
     * @param b
     */
    @Override
    public void setMultithreaded(boolean b) {

    }

    /**
     * @return
     */
    @Override
    public boolean getMultithreaded() {
        return false;
    }

    /**
     * @param b
     */
    @Override
    public void setIgnoreCache(boolean b) {

    }

    /**
     * @return
     */
    @Override
    public boolean getIgnoreCache() {
        return false;
    }

    /**
     * @param integer
     */
    @Override
    public void setDatastoreReadTimeoutMillis(Integer integer) {

    }

    /**
     * @return
     */
    @Override
    public Integer getDatastoreReadTimeoutMillis() {
        return 0;
    }

    /**
     * @param integer
     */
    @Override
    public void setDatastoreWriteTimeoutMillis(Integer integer) {

    }

    /**
     * @return
     */
    @Override
    public Integer getDatastoreWriteTimeoutMillis() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public boolean getDetachAllOnCommit() {
        return false;
    }

    /**
     * @param b
     */
    @Override
    public void setDetachAllOnCommit(boolean b) {

    }

    /**
     * @return
     */
    @Override
    public boolean getCopyOnAttach() {
        return false;
    }

    /**
     * @param b
     */
    @Override
    public void setCopyOnAttach(boolean b) {

    }

    /**
     * @param t
     * @param <T>
     * @return
     */
    @Override
    public <T> T detachCopy(T t) {
        return null;
    }

    /**
     * @param collection
     * @param <T>
     * @return
     */
    @Override
    public <T> Collection<T> detachCopyAll(Collection<T> collection) {
        return List.of();
    }

    /**
     * @param ts
     * @param <T>
     * @return
     */
    @Override
    public <T> T[] detachCopyAll(T... ts) {
        return null;
    }

    /**
     * @param o
     * @param o1
     * @return
     */
    @Override
    public Object putUserObject(Object o, Object o1) {
        return null;
    }

    /**
     * @param o
     * @return
     */
    @Override
    public Object getUserObject(Object o) {
        return null;
    }

    /**
     * @param o
     * @return
     */
    @Override
    public Object removeUserObject(Object o) {
        return null;
    }

    /**
     *
     */
    @Override
    public void flush() {

    }

    /**
     *
     */
    @Override
    public void checkConsistency() {

    }

    /**
     * @return
     */
    @Override
    public FetchPlan getFetchPlan() {
        return null;
    }

    /**
     * @param aClass
     * @param <T>
     * @return
     */
    @Override
    public <T> T newInstance(Class<T> aClass) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public Sequence getSequence(String s) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public JDOConnection getDataStoreConnection() {
        return null;
    }

    /**
     * @param instanceLifecycleListener
     * @param classes
     */
    @Override
    public void addInstanceLifecycleListener(InstanceLifecycleListener instanceLifecycleListener, Class... classes) {

    }

    /**
     * @param instanceLifecycleListener
     */
    @Override
    public void removeInstanceLifecycleListener(InstanceLifecycleListener instanceLifecycleListener) {

    }

    /**
     * @return
     */
    @Override
    public Date getServerDate() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public Set getManagedObjects() {
        return Set.of();
    }

    /**
     * @param enumSet
     * @return
     */
    @Override
    public Set getManagedObjects(EnumSet<ObjectState> enumSet) {
        return Set.of();
    }

    /**
     * @param classes
     * @return
     */
    @Override
    public Set getManagedObjects(Class... classes) {
        return Set.of();
    }

    /**
     * @param enumSet
     * @param classes
     * @return
     */
    @Override
    public Set getManagedObjects(EnumSet<ObjectState> enumSet, Class... classes) {
        return Set.of();
    }

    /**
     * @param aClass
     * @param s
     * @return
     */
    @Override
    public FetchGroup getFetchGroup(Class aClass, String s) {
        return null;
    }

    /**
     * @param s
     * @param o
     */
    @Override
    public void setProperty(String s, Object o) {

    }

    /**
     * @return
     */
    @Override
    public Map<String, Object> getProperties() {
        return Map.of();
    }

    /**
     * @return
     */
    @Override
    public Set<String> getSupportedProperties() {
        return Set.of();
    }
}
