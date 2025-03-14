package tools;


import annos.Conditional;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import org.springframework.data.relational.core.sql.FalseCondition;
import org.springframework.data.relational.core.sql.TrueCondition;
import util.algo.ChooseEvtPublshr;

import static util.algo.ChooseEvtPublshr.iniCondtEvtMap4sngClz;

public class condtEvtTst {


    public static void main(String[] args) throws Exception {

     //   iniCondtEvtMap4sngClz(condtEvtTst.class);
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
    @Conditional({ConditionImpt4chooseEvt.class, AssertFalse.class})
    public void elseblk(Object args) {
        System.out.println("else blk");
    }

    @Conditional({ConditionImpt4chooseEvt.class, AssertTrue.class})
    public void ifblk(Object  args) {
        System.out.println("ifblk blk");
    }


    @Conditional({ConditionImpt4chooseEvt.class,AssertTrue.class})
    public void ifblk2(Object  args) {
        System.out.println("ifblk blk stmt 2");
    }
}
