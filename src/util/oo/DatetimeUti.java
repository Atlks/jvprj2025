package util.oo;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DatetimeUti {
    /**
     * 得到本月月末日期   格式2025-05-31
     *
     * @param yearMonth 202505
     * @return
     */
    @Deprecated
    public static String getMonthEndDatetime(int yearMonth) {
        int year = yearMonth / 100;
        int month = yearMonth % 100;
        YearMonth ym = YearMonth.of(year, month);
        return ym.atEndOfMonth().format(FORMATTER);
    }
    public static String getMonthEndDatetime(String yearMonth) {
        YearMonth ym = YearMonth.parse(yearMonth); // 例如 "2025-05"
        return ym.atEndOfMonth().toString(); // 自动输出为 yyyy-MM-dd 格式
    }

    /**
     * 功能，计算以前几个月的月份
     * 得到月份 格式  yyyy-mm
     * @param n  当前月要减去的月份  1-50
     * @return
     */
    public static String lastMonth(int n) {

        LocalDate targetMonth = LocalDate.now().minusMonths(n);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        return targetMonth.format(formatter);
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    /**
     * 得到月初的字符串格式  2025-05-01
     *
     * @param yearMonth 2025-05
     * @return
     */
    public static String getMonthStartDatetime(String yearMonth) {
        LocalDate date = LocalDate.parse(yearMonth + "-01");
        return date.toString(); // 自动格式为 yyyy-MM-dd

    }



    /**
     * 得到月初的字符串格式  2025-05-01
     *
     * @param yearMonth 202505
     * @return
     */
    @Deprecated
    public static  String getMonthStartDatetime(int yearMonth) {
        int year = yearMonth / 100;
        int month = yearMonth % 100;
        YearMonth ym = YearMonth.of(year, month);
        return ym.atDay(1).format(FORMATTER);
    }

    //格式202505  ,获取当钱年份月份
    public static int getCurrYearMonth() {
        LocalDate now = LocalDate.now();
        return now.getYear() * 100 + now.getMonthValue();
    }
}
