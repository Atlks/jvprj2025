package service.wlt;

import jakarta.validation.constraints.NotNull;

import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.CreditDebitIndicator;
import model.OpenBankingOBIE.TransactionStatus;
import model.OpenBankingOBIE.Transaction;
import util.ex.BalanceNotEnghou;
import entityx.wlt.TransDto;

import java.math.BigDecimal;

import static handler.wlt.TransHdr.curLockAcc;
import static cfg.Containr.sessionFactory;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static util.algo.GetUti.getUuid;
import static util.tx.HbntUtil.*;

/**
 *
 */
public class AccSvs {


}
