package util.oo;

import java.time.LocalDate;
import java.time.ZoneOffset;

public class TimeUti {

    /**
     * 计算几天前的 Unix 时间戳（秒）
     * @param day 向前推的天数，例如 3 表示 3 天前
     * @return Unix 时间戳（秒）字符串
     */
    public static long beforeTmstmp(int day) {
        // 获取当前日期减去指定天数
        LocalDate date = LocalDate.now().minusDays(day);
        // 转换为时间戳（秒）
        long timestamp = date.atStartOfDay().toEpochSecond(ZoneOffset.UTC);
        return (timestamp);
    }
}
