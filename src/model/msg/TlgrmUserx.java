package model.msg;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class TlgrmUserx {
    @Id
    private String id;                // 用户唯一 ID
    private boolean isBot;          // 是否是机器人
    private String firstName;       // 必填字段
    private String lastName;        // 可选字段
    private String username;        // 可选字段
    private String languageCode;    // 可选，用户设置的语言
    private Boolean isPremium;      // 可选，是否是 Telegram Premium 用户
    private Boolean addedToAttachmentMenu; // 可选
    private Boolean canJoinGroups;        // 可选，适用于 bot
    private Boolean canReadAllGroupMessages; // 可选
    private Boolean supportsInlineQueries;   // 可选

    public TlgrmUserx(String uid) {
      this.id=uid;
    }

    // Getters and setters


    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean bot) {
        isBot = bot;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public Boolean getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(Boolean isPremium) {
        this.isPremium = isPremium;
    }

    public Boolean getAddedToAttachmentMenu() {
        return addedToAttachmentMenu;
    }

    public void setAddedToAttachmentMenu(Boolean addedToAttachmentMenu) {
        this.addedToAttachmentMenu = addedToAttachmentMenu;
    }

    public Boolean getCanJoinGroups() {
        return canJoinGroups;
    }

    public void setCanJoinGroups(Boolean canJoinGroups) {
        this.canJoinGroups = canJoinGroups;
    }

    public Boolean getCanReadAllGroupMessages() {
        return canReadAllGroupMessages;
    }

    public void setCanReadAllGroupMessages(Boolean canReadAllGroupMessages) {
        this.canReadAllGroupMessages = canReadAllGroupMessages;
    }

    public Boolean getSupportsInlineQueries() {
        return supportsInlineQueries;
    }

    public void setSupportsInlineQueries(Boolean supportsInlineQueries) {
        this.supportsInlineQueries = supportsInlineQueries;
    }
}
