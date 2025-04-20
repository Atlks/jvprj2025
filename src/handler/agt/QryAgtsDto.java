package handler.agt;

import lombok.Data;
import util.entty.PageDto;

import java.math.BigDecimal;

/**
 * 查询条件 DTO
 * 条件 ：代理账号、标签、佣金（区间）
 */
@Data
public class QryAgtsDto extends PageDto {

    /** 代理账号 */
    public String agentAccount;

    /** 标签 */
    public String tag;

    /** 最小佣金 */
    public BigDecimal commissionMin;

    /** 最大佣金 */
    public BigDecimal commissionMax;
}
