package util;

import javax.jdo.PersistenceManager;

import java.util.List;

import static util.Util2025.writeFile2501;
import static util.UtilEncode.encodeFilename;
import static util.dbutil.encodeIni;
import static util.util2026.getField2025;

public class JdoPstsMngrHbnt extends PersistenceManagerBase implements PersistenceManager {


    public String saveDir;
    public   List<Class>   objClassLst;

    public   JdoPstsMngrHbnt(String saveDir0, List<Class> li) {
      //  mkdir2025(saveDir0);
        saveDir=saveDir0;
        objClassLst=li;
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


}
