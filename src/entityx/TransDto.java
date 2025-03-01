package entityx;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
//jakarta.persistence.Entity（JPA 3.x, Jakarta EE 9 及以后）
//javax.persistence.Entity  already dep
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
@Data
public class TransDto {






    /**
     *   // 唯一标识符
     *     private Long id;
     *
     *     // 用户 ID
     *     private Long userId;
     *
     *     // 变动金额
     *     private BigDecimal changeAmount;
     *
     *     // 变动后余额
     *     private BigDecimal newBalance;
     *
     *     // 变动类型
     *     private String changeType; // 例如：RECHARGE, EXPENSE, REFUND
     *
     *     // 变动时间
     *     private LocalDateTime changeTime;
     *
     *     // 备注信息
     *     private String remark;
     */


        @Id
        public String id;
        public String uname;

        public long changeTime;
        public String changeType;
        public String changeMode;
        public BigDecimal amtBefore;

        public BigDecimal getChangeAmount() {
            return  toBigDecimal(changeAmount);
        }

        public BigDecimal changeAmount;
        public BigDecimal newBalance;
        // 备注信息
        private String remark;


    }


