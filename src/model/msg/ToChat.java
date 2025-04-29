package model.msg;

import lombok.Data;

/**
 * toChatTarget  model
 * 普通用户、群组（group）、超级群组（supergroup）、频道（channel） 虽然都使用同一个实体类 Chat（如 TelegramChat），但它们的行为和字段根据类型有所不同，通常通过 type 字段来区分：
 */
@Data
public class ToChat {
    private String id;
    private TelegramChatType type; // "private", "group", "supergroup", "channel"

    // Optional fields
    private String title;        // for groups, supergroups and channels
    private String username;     // for private chats, supergroups and channels if available
    private String firstName;    // for private chats
    private String lastName;     // for private chats



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
