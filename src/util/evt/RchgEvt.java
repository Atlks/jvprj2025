package util.evt;

import model.OpenBankingOBIE.Transactions;
import util.algo.ConsumerX;

import java.util.HashSet;
import java.util.Set;

public class RchgEvt {

    public static Set<ConsumerX<Transactions>> evtlist4rchg=new HashSet<>();

}
