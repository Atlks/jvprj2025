package util.oo;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * 你的理解完全正确，当前在大多数现代 Java 项目中，推荐使用 OffsetDateTime、ZonedDateTime 或 Instant 等类型，而不是裸的时间戳数字。原因如下：
 */
public class TimeUti {

//    /**
//     * 计算几天前的 Unix 时间戳（秒）
//     * @param day 向前推的天数，例如 3 表示 3 天前
//     * @return Unix 时间戳（秒）字符串
//     */
//    @Deprecated
//    public static long beforeTmstmpLong(int day) {
//        // 获取当前日期减去指定天数
//        LocalDate date = LocalDate.now().minusDays(day);
//        // 转换为时间戳（秒）
//        long timestamp = date.atStartOfDay().toEpochSecond(ZoneOffset.UTC);
//        return (timestamp);
//    }

    /**
     * 计算几天前的 时间
     * @param day 向前推的天数，例如 3 表示 3 天前
     * @return  OffsetDateTime 模式
     */
    public static OffsetDateTime beforeTmstmp(int day) {
        return OffsetDateTime.now(ZoneOffset.UTC).minusDays(day);
    }
}
