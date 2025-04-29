package model.msg;

public class TelegramMessage {
    private Long messageId;
    private TelegramUser from;
    private Long date=System.currentTimeMillis();

    //chat 表示接收方（可能是用户、群、频道）——它实际上就扮演了“to”的角色。
    private TelegramChat chat;
    private String text;

    // 可选字段
    private TelegramMessage replyToMessage;
    private String forwardFrom;
    private String caption;

    // Getters and setters
    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public TelegramUser getFrom() {
        return from;
    }

    public void setFrom(TelegramUser from) {
        this.from = from;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public TelegramChat getChat() {
        return chat;
    }

    public void setChat(TelegramChat chat) {
        this.chat = chat;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}