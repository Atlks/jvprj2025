package orgx.uti.orm;

import jakarta.persistence.AttributeConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import orgx.u.Eml;

import java.io.IOException;

public class EmlConverter implements AttributeConverter<Eml, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Eml eml) {
        try {
            return objectMapper.writeValueAsString(eml); // 转换为 JSON 存储
        } catch (IOException e) {
            throw new RuntimeException("JSON 转换失败", e);
        }
    }

    @Override
    public Eml convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, Eml.class); // 从 JSON 解析回对象
        } catch (IOException e) {
            throw new RuntimeException("JSON 解析失败", e);
        }
    }
}
