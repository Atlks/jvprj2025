package util.oo;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 你的理解完全正确，当前在大多数现代 Java 项目中，推荐使用 OffsetDateTime、ZonedDateTime 或 Instant 等类型，而不是裸的时间戳数字。原因如下：
 */
public class TimeUti {

    public static void main(String[] args) {
        System.out.println(calcEndtime("2025-11"));
    }


    /**
     *计算月末时间
     * @param geneStatmtRefCurrMth  格式 2025-11
     * @return
     */
    public static  OffsetDateTime calcEndtime(String geneStatmtRefCurrMth) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            YearMonth yearMonth = YearMonth.parse(geneStatmtRefCurrMth, formatter);

            // 获取当月最后一天的 LocalDate
            LocalDate lastDay = yearMonth.atEndOfMonth();

            // 设置为当天的 23:59:59.999999999（纳秒级最大值）
            LocalTime endOfDay = LocalTime.MAX;

            return OffsetDateTime.of(lastDay, endOfDay, ZoneOffset.UTC);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("参数格式应为 yyyy-MM，例如 2025-11", e);
        }

    }

    /**
     *计算月初开始时间
     * @param geneStatmtRefCurrMth  格式 2025-11
     * @return
     */
    public static   OffsetDateTime calcStarttime(String geneStatmtRefCurrMth) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            YearMonth yearMonth = YearMonth.parse(geneStatmtRefCurrMth, formatter);
            LocalDate firstDay = yearMonth.atDay(1); // 得到该月第一天

            return firstDay.atStartOfDay().atOffset(ZoneOffset.UTC);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("参数格式应为 yyyy-MM，例如 2025-11", e);
        }
    }

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

    public static Timestamp beforeTmstmpTmstpFmt(int day) {
        OffsetDateTime time=beforeTmstmp(day);
        return   Timestamp.from(time.toInstant());
    }

    public static String beforeTmSqlPrmFmt(int day) {
        OffsetDateTime offsetDateTime = OffsetDateTime.now(ZoneOffset.UTC).minusDays(day);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String tsStr =offsetDateTime.format(fmt);
        return "'"+tsStr+"'";
    }


}
