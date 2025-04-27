package handler.cms;

import model.OpenBankingOBIE.Transactions;
import util.algo.ConsumerX;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class AftrCalcRchgAmtSumEvt {

    public static Set<ConsumerX<Transactions>> evtlist4aftCalcRchgAmtSum= new HashSet<>();

public Set getEvtSt()
{
    return evtlist4aftCalcRchgAmtSum;
}


}
