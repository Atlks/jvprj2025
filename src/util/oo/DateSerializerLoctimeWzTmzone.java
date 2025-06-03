package util.oo;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

//序列化为本地时间格式字符串（如 "2025-05-30T14:23:45+08:00"）：
public class DateSerializerLoctimeWzTmzone implements JsonSerializer<Timestamp> {

    @Override
    public JsonElement serialize(Timestamp src, Type typeOfSrc, JsonSerializationContext context) {
        // 转为 OffsetDateTime 本地时间（带时区）
        ZoneId zone = ZoneId.systemDefault();
        System.out.println("line21261"+zone);
        OffsetDateTime offsetDateTime = src.toInstant().atZone(zone).toOffsetDateTime();
        String formatted = offsetDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return context.serialize(formatted);
    }



}

