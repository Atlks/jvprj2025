package model.msg;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Message {
    @Id
    private String messageId;
    private TlgrmUserx from;
    private Long date=System.currentTimeMillis();

    //chat 表示接收方（可能是用户、群、频道）——它实际上就扮演了“to”的角色。
    private ToChat chat;
    public  String toChatTgtId;
    private String text;

    // 可选字段
    private Message replyToMessage;
    private String forwardFrom;
    private String caption;

    public Message(String toChatId, String text) {

    }


    public TlgrmUserx getFrom() {
        return from;
    }

    public void setFrom(TlgrmUserx from) {
        this.from = from;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public ToChat getChat() {
        return chat;
    }

    public void setChat(ToChat chat) {
        this.chat = chat;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}