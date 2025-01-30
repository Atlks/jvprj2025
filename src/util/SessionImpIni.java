package util;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import static util.Util2025.mkdir2025;
import static util.Util2025.writeFile2501;
import static util.UtilEncode.encodeFilename;
import static util.dbutil.*;
import static util.util2026.*;

public class SessionImpIni extends SessionImpBase implements Session {

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

        String fnamePath = saveDir + "/" + fname;
        System.out.println("fnamePath=" + fnamePath);
        writeFile2501(fnamePath, encodeIni(obj));


    }

    /**
     * @param t
     * @param <T>
     * @return
     */
    @Override
    public <T> T merge(T obj) {


        persist(obj);
        return obj;

    }

    /**
     * @param o
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
     * @param o
     * @param <T>
     * @return
     */
    @Override
    public <T> T find(Class<T> aClass, Object id) {


        String fname = encodeFilename(id.toString()) + ".ini";

        String collPath = saveDir + "/" + aClass.getSimpleName();
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
     * @return
     */
    @Override
    public NativeQuery createNativeQuery(String s, Class aClass) {
        return null;
    }
}
