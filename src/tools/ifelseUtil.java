package tools;

import api.usr.ConditionContextMockImp;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import util.algo.ChooseEvtPublshr;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static cfg.IocSpringCfg.getObject;
import static util.algo.ChooseEvtPublshr.iniCondtEvtMap4sngClz;

public class ifelseUtil {


    public static void main(String[] args) throws Exception {

        iniCondtEvtMap4sngClz(ifelseUtil.class);
        new ChooseEvtPublshr().publishEvent4exeCdtn(ConditionImpt1.class);
    }

    private static void iniCondtEvtMap() {
    }








//    private void traveMethodByClass(Class<ifelseUtil> ifelseUtilClass) throws Exception {
//        Method[] ms = ifelseUtilClass.getDeclaredMethods();
//        for (Method m : ms) {
//            if (m.isAnnotationPresent(Conditional.class)) {
//                Class[] cdtClss = m.getAnnotation(Conditional.class).value();
//                for (Class c : cdtClss) {
//                    Condition cdtObj = (Condition) getObject(c);
//                    if (cdtObj.matches(null, null))
//                        m.invoke(getObjByMethod(m), null);
//                }
//            }
//
//            if (m.isAnnotationPresent(ConditionalElse.class)) {
//                Class[] cdtClss = m.getAnnotation(ConditionalElse.class).value();
//                for (Class c : cdtClss) {
//                    Condition cdtObj = (Condition) getObject(c);
//                    if (cdtObj.matches(null, null)) {
//
//                    } else {
//                        m.invoke(getObjByMethod(m), null);
//                    }
//
//                }
//            }
//        }
//    }



    // @ConditionalOnProperty(name = "my.feature.enabled", havingValue = "true")
    @ConditionalElse({ConditionImpt1.class})
    public void elseblk(Object args) {
        System.out.println("else blk");
    }

    @Conditional({ConditionImpt1.class})
    public void ifblk(Object  args) {
        System.out.println("ifblk blk");
    }


    @Conditional({ConditionImpt1.class})
    public void ifblk2(Object  args) {
        System.out.println("ifblk blk stmt 2");
    }
}
