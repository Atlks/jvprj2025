package util.evt;

import model.OpenBankingOBIE.Transaction;
import util.algo.ConsumerX;
//import util.algo.ConsumerX;

import java.util.HashSet;
import java.util.Set;

public class RchgEvt {

    public static Set<ConsumerX<Transaction>> evtlist4rchg=new HashSet<>();

}
