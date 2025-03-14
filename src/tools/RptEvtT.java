package tools;


import annos.Repeat;
import annos.RepeatUtil;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import util.evtdrv.Condition;
import util.evtdrv.RptEvtPublshr;


import static util.evtdrv.RptEvtPublshr.addRptEvt2evtList4sngClz;

public class RptEvtT {

    @NoArgsConstructor
    public static class ConditionImpt88 implements Condition {
        @Override
        public Object matches(Object prm4cdt) {

            System.out.println("\n\n============");
            if (prm4cdt instanceof Number) { // ✅ 确保参数是数值类型
                boolean b = ((Number) prm4cdt).intValue() > 3;
                return !b; // ✅ 进行整数比较

            }
            return false; // 非数值类型时默认返回 false

        }
    }

    public  static  ThreadLocal<Integer> nmm=new ThreadLocal<Integer>();
    @Scope("thread")
    public static void main(String[] args) throws Exception {

      //  System.out.println(  ConditionImpt66.class.getConstructor().newInstance());
        addRptEvt2evtList4sngClz(RptEvtT.class);
     //   iniCondtEvtMap4sngClz(condtEvtTst.class);
        var a=1;
        nmm.set(1);
        new RptEvtPublshr().publishEvent4rpt4exeCdtn(ConditionImpt88.class,1);
    }

    private static void iniCondtEvtMap() {
    }



    // @ConditionalOnProperty(name = "my.feature.enabled", havingValue = "true")
    @Repeat(ConditionImpt88.class)
    public void foreachInnerStmt(Object args) {
        System.out.println("for innnerr...");
    }

    @RepeatUtil(ConditionImpt88.class)
    public void stepNutilStmt(Object  args) throws Exception {
        System.out.println(" utilStmt");

        nmm.set(nmm.get()+1);
        new RptEvtPublshr().publishEvent4rpt4exeCdtn(ConditionImpt88.class,nmm.get());

    }


}
