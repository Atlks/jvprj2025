package model.msg;

public enum TelegramChatType {
    PRIVATE("private"),
    GROUP("group"),
    SUPERGROUP("supergroup"),
    CHANNEL("channel");

    private final String value;

    TelegramChatType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TelegramChatType fromValue(String value) {
        for (TelegramChatType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown chat type: " + value);
    }
}
