package Evt;

import lombok.Data;
import lombok.experimental.Accessors;
import model.OpenBankingOBIE.Transactions;
import util.algo.ConsumerX;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
@lombok.Builder
@Data
@Accessors(chain = true)  // 开启链式调用
public class AftrCalcRchgAmtSumEvt {

    //evt type not need ,bcs clz name is type

//    public PrmType data;
//    public Map<String,Object> datas=new HashMap<>() ;
//
//    public static Set<ConsumerX<Transactions>> evtlist4aftCalcRchgAmtSum= new HashSet<>();
//
//public Set getEvtSt()
//{
//    return evtlist4aftCalcRchgAmtSum;
//}


}
