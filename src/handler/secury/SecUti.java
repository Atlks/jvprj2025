package handler.secury;

import static util.algo.EncodeUtil.encodeUrl;
import static util.misc.Util2025.writeFile2501;

import java.io.File;

public class SecUti {

    
    public static boolean isExistIdptKey(String idempotencyKey) {
        var  idempotencyKeyFname=encodeUrl(idempotencyKey);
        var fpath=  "idptpkey/"+idempotencyKeyFname+".txt";
    
        if(new File(fpath).exists())
         return true;
        else
         return false;
    }


     public static void addIdptKey(String idempotencyKey) {
      var  idempotencyKeyFname=encodeUrl(idempotencyKey);
       var fpath=  "idptpkey/"+idempotencyKeyFname+".txt";
         writeFile2501  (fpath,idempotencyKey); 
    }


    
}
