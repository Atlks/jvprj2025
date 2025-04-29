package model.msg;

public class TelegramChat {
    private Long id;
    private TelegramChatType type; // "private", "group", "supergroup", "channel"

    // Optional fields
    private String title;        // for groups, supergroups and channels
    private String username;     // for private chats, supergroups and channels if available
    private String firstName;    // for private chats
    private String lastName;     // for private chats

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


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
