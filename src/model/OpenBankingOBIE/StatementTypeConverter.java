package model.OpenBankingOBIE;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatementTypeConverter implements AttributeConverter<StatementType, String> {

    @Override
    public String convertToDatabaseColumn(StatementType attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public StatementType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return StatementType.valueOf(dbData);
        } catch (IllegalArgumentException e) {
            // 数据库里有不合法的值，返回 null 或默认值
            return null;
            // 或 return StatementType.DEFAULT;  // 如果有默认枚举常量
        }
    }
}
