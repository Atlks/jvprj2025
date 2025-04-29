package model.msg;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import static util.algo.GetUti.getUuid;
import static util.misc.Util2025.encodeJson;

@Entity
@Data
@NoArgsConstructor
public class Message {
    @Id
    private String messageId=getUuid();

    public  String fromUid;
    public  String fromUserJson;
    //@Transient

    @JdbcTypeCode(SqlTypes.JSON) // Hibernate 6 推荐写法
    @Column(name = "`from`",columnDefinition = "json") // 指定 MySQL 的 json 类型
    private TlgrmUserx from;
    private Long date=System.currentTimeMillis();

    //chat 表示接收方（可能是用户、群、频道）——它实际上就扮演了“to”的角色。
   // @Transient
    @JdbcTypeCode(SqlTypes.JSON) // Hibernate 6 推荐写法
    @Column(name = "`chat`",columnDefinition = "json")
    private ToChat chat;
    public  String toChatTgtId;
    public  String toChatTgtJson;
    private String text;

    // 可选字段
    @Transient
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
        this.fromUserJson=encodeJson(from);
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
        this.toChatTgtJson=encodeJson(chat);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}