package util.tx;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import static util.misc.Util2025.mkdir2025;
import static util.misc.Util2025.writeFile2501;
import static util.misc.UtilEncode.encodeFilename;
import static util.tx.dbutil.*;
import static util.misc.util2026.*;

public class SessionImpIni<R, T> extends SessionImpBase implements Session {

    private final String saveDir;
    ;

    public SessionImpIni(String saveDir0) {
        //  getTransaction()
        mkdir2025(saveDir0);
        saveDir = saveDir0;
    }

    /**
     * @param obj
     */
    @Override
    public void persist(Object obj) {
// PersistenceManager
        String fname = (String) getField2025(obj, "id", "");
        //todo need fname encode
        fname = encodeFilename(fname) + ".ini";
        var collName = obj.getClass().getName();
        String fnamePath = saveDir + collName + "/" + fname;
        System.out.println("fnamePath=" + fnamePath);
        writeFile2501(fnamePath, encodeIni(obj));


    }

    /**
     * @param <T>
     * @return
     */
    @Override
    public <T> T merge(T obj) {


        persist(obj);
        return obj;

    }

    /**
     *
     */
    @Override
    public void remove(Object obj) {
        String fname = (String) getField2025(obj, "id", "");
        //todo need fname encode
        fname = encodeFilename(fname) + ".ini";

        String fnamePath = saveDir + "/" + fname;
        System.out.println("fnamePath=" + fnamePath);
        removeFile(fnamePath);
    }


    /**
     * @param aClass
     * @param <T>
     * @return
     */
    @Override
    public <T> T find(Class<T> aClass, Object id) {

        var collName = aClass.getName();
        String fname = encodeFilename(id.toString()) + ".ini";

        String collPath = saveDir + "/" + aClass.getName();
        String fnamePath = collPath + "/" + fname;
        System.out.println("fnamePath=" + fnamePath);

        var map = getObjIni(id.toString(), collPath);
        try {
            return toObj(map, aClass);
        } catch (Exception e) {
            throwEx(e);
        }
        return null;
    }

    /**
     * @param s
     * @param aClass
     * @param s1
     * @return
     */
    @Override
    public NativeQuery createNativeQuery(String s, Class aClass, String s1) {
        return null;
    }


    /**
     * @param aClass
     * @return
     */
    @Override
    public NativeQuery createNativeQuery(String sql, Class aClass) {
        NativeQueryImp4ini nq = new NativeQueryImp4ini(sql, aClass);
        nq.saveUrl = this.saveDir;
        nq.aClass = aClass;
        return nq;
    }

}
