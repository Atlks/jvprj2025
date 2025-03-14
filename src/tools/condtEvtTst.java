package tools;


import annos.Conditional;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import lombok.NoArgsConstructor;
import util.evtdrv.ChooseContionEvtPublshr;
import util.evtdrv.Condition;

import static util.evtdrv.ChooseContionEvtPublshr.addCondtEvt2evtList4sngClz;

public class condtEvtTst {

    @NoArgsConstructor
    public static class ConditionImpt66 implements Condition {
        @Override
        public Object matches(Object prm4cdt) {
            if (prm4cdt instanceof Number) { // ✅ 确保参数是数值类型
                return ((Number) prm4cdt).intValue() > 3; // ✅ 进行整数比较
            }
            return false; // 非数值类型时默认返回 false

        }
    }

    public static void main(String[] args) throws Exception {

      //  System.out.println(  ConditionImpt66.class.getConstructor().newInstance());
        addCondtEvt2evtList4sngClz(condtEvtTst.class);
     //   iniCondtEvtMap4sngClz(condtEvtTst.class);
        var a=1;
        new ChooseContionEvtPublshr().publishEvent4exeCdtn(ConditionImpt66.class,a);
    }

    private static void iniCondtEvtMap() {
    }




    // @ConditionalOnProperty(name = "my.feature.enabled", havingValue = "true")
    @Conditional({ConditionImpt66.class, AssertFalse.class})
    public void elseblk(Object args) {
        System.out.println("else blk");
    }

    @Conditional({ConditionImpt66.class, AssertTrue.class})
    public void ifblk(Object  args) {
        System.out.println("ifblk blk");
    }


    @Conditional({ConditionImpt66.class,AssertTrue.class})
    public void ifblk2(Object  args) {
        System.out.println("ifblk blk stmt 2");
    }
}
