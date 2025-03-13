package cfg;

import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import util.excptn.ExceptionBase;
import util.excptn.ExceptionBaseRtm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import static util.misc.util2026.copyProps;
import static util.proxy.SprUtil.injectAll4spr;

public class EvtPublisherObsv {
    public void notifyObsvrs(Set<Method> obsSet, Object prm) {

        for (Method m : obsSet) {
            Object obj = null;
            try {
                obj = m.getDeclaringClass().getConstructor().newInstance();
                injectAll4spr(obj);
                System.out.println("▶\uFE0Ffun " + m.getName());
                m.invoke(obj, prm);
                System.out.println("✅endfun " + m.getName());

            } catch (InstantiationException | IllegalAccessException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {

                Throwable oriEx = e.getCause();
                if (oriEx instanceof ExceptionBase oriEx1) {
                    ExceptionBaseRtm ex = new ExceptionBaseRtm();

                    copyProps(oriEx1, ex);
                    ex.errmsg = oriEx1.getErrmsg();
                    ex.errcode = oriEx1.getClass().getName();
                    ex.type = oriEx1.getClass().getName();
                    throw ex;

                } else
                    throw new RuntimeException(oriEx.getMessage(), oriEx);
            }

        }

    }

}
