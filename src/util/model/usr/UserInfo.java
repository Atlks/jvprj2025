package util.model.usr;

import com.fasterxml.jackson.annotation.JsonProperty;

//oauth openid
public class UserInfo {

    private String sub;

    private String name;

    @JsonProperty("given_name")
    private String givenName;

    @JsonProperty("family_name")
    private String familyName;

    @JsonProperty("middle_name")
    private String middleName;

    private String nickname;

    @JsonProperty("preferred_username")
    private String preferredUsername;

    private String profile;

    private String picture;

    private String website;

    private String email;

    @JsonProperty("email_verified")
    private Boolean emailVerified;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("phone_number_verified")
    private Boolean phoneNumberVerified;

    private String locale;

    @JsonProperty("updated_at")
    private Long updatedAt;

    // Getters and Setters omitted for brevity

    // toString override (optional, for debugging)
    @Override
    public String toString() {
        return "UserInfo{" +
                "sub='" + sub + '\'' +
                ", name='" + name + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", preferredUsername='" + preferredUsername + '\'' +
                ", profile='" + profile + '\'' +
                ", picture='" + picture + '\'' +
                ", website='" + website + '\'' +
                ", email='" + email + '\'' +
                ", emailVerified=" + emailVerified +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", phoneNumberVerified=" + phoneNumberVerified +
                ", locale='" + locale + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
