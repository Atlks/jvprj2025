package Evt;

import model.OpenBankingOBIE.Transactions;
import util.algo.ConsumerX;

import java.util.HashSet;
import java.util.Set;

public class AftrCalcRchgAmtSumEvt {

    public static Set<ConsumerX<Transactions>> evtlist4aftCalcRchgAmtSum= new HashSet<>();

public Set getEvtSt()
{
    return evtlist4aftCalcRchgAmtSum;
}


}
