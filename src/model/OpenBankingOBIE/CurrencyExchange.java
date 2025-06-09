package model.OpenBankingOBIE;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class CurrencyExchange {

    // 源货币（即实际付款货币）
    private String sourceCurrency;

    // 目标货币（账户记账货币）
    private String targetCurrency;

    // 汇率类型，如 Actual、Agreed、Indicative 等
    private String unitCurrency;

    // 汇率（1 单位 sourceCurrency 对应的 targetCurrency）
    private BigDecimal exchangeRate;

    // 汇率类型说明
    private String rateType;

    // 汇率协定时间（如果是事先协定的汇率）
    private OffsetDateTime contractIdentification;

    // 汇率协定标识符（可选）
    private String exchangeRateContract;

    // 时间戳（汇率生效时间）
    private OffsetDateTime quotationDate;

    // Getter 和 Setter（可用 Lombok 简化）
    // 你也可以加 @JsonProperty 注解用于 Jackson 映射
}
