package Evt;

import entityx.usr.Usr;
import model.OpenBankingOBIE.Transactions;
import util.algo.ConsumerX;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class RchgEvt {

    public static Set<ConsumerX<Transactions>> evtlist4rchg=new HashSet<>();

}
