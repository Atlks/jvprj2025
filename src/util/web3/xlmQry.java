package util.web3;

import java.util.List;
import java.util.Map;

import static util.misc.Util2025.encodeJson;
import static util.web3.StellarHelper.getXlmTransactionList;

public class xlmQry {
    public static void main(String[] args) {

//        var xlmAddress="GCMO2UWRXH4QPYXVF56IP4ZOIYPL77UI6M6XBO3G4KKDM2VHUPLCLIC6";
//        List li=getXlmTransactionList(xlmAddress);


        String xlmAddress = "GCMO2UWRXH4QPYXVF56IP4ZOIYPL77UI6M6XBO3G4KKDM2VHUPLCLIC6"; // 这里填入 Stellar 地址
        List<Map<String, Object>> transactions = getXlmTransactionList(xlmAddress);
        System.out.println(encodeJson(transactions) );
    }

    /**
     * 根据给的xlm地址，查询交易记录，返回为MapList模式
     * @param xlmAddress
     * @return
     */
//    private static List<Map<String,Object>> getXlmTransactionList(String xlmAddress) {
//
//        return List.of();
//    }
}
