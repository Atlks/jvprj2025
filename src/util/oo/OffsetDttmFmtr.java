package util.oo;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public    class OffsetDttmFmtr implements JsonSerializer<OffsetDateTime> {

    @Override
    public JsonElement serialize(OffsetDateTime inputTime, Type typeOfSrc, JsonSerializationContext context) {
        // 获取系统默认时区（如 Asia/Shanghai）
        ZoneId systemZone = ZoneId.systemDefault();

        // 转换为系统时区的 OffsetDateTime
        OffsetDateTime converted = inputTime
                .toInstant()
                .atZone(systemZone)
                .toOffsetDateTime();

        // 格式化为标准 ISO_OFFSET_DATE_TIME
        String formatted =converted.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return new JsonPrimitive(formatted);
//        // 标准化偏移，只保留小时和分钟
//        ZoneOffset offset = ZoneOffset.ofTotalSeconds(src.getOffset().getTotalSeconds() / 60 * 60);
//
//        // 转为合法 offset
//        OffsetDateTime fixedOffsetDateTime = src.withOffsetSameInstant(offset);
//
//        String formatted = getStringIsoTime(fixedOffsetDateTime);
//
//        return new JsonPrimitive(formatted);
    }

    /**
     * 格式化为当前系统时区的时间
     * @param
     * @return
     */
    @NotNull
    private static String getStringIsoTime(OffsetDateTime inputTime) {
        // 获取系统默认时区（如 Asia/Shanghai）
        ZoneId systemZone = ZoneId.systemDefault();

        // 转换为系统时区的 OffsetDateTime
        OffsetDateTime converted = inputTime
                .toInstant()
                .atZone(systemZone)
                .toOffsetDateTime();

        // 格式化为标准 ISO_OFFSET_DATE_TIME
        return converted.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
