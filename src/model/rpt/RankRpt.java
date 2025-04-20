package model.rpt;

import java.math.BigDecimal;
import java.util.List;

public class RankRpt {

    public BigDecimal rechgSum;
    public List<MbrAmtSum>  totalRechg_mbrList;
    public List<MbrAmtSum>  totalRechg_agtList;


    public BigDecimal totalWthdr;

    public List<MbrAmtSum>  totalWthdr_mbrList;
    public List<MbrAmtSum>  totalWthdr_agtList;


    public BigDecimal totalEchxg;

    public List<MbrAmtSum> totalEchxg_mbrList;
    public List<MbrAmtSum>  totalEchxg_agtList;
    public BigDecimal totalCms;

    public List<MbrAmtSum> totalCms_mbrList;
    public List<MbrAmtSum>  totalCms_agtList;


}
