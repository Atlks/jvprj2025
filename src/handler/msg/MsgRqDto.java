package handler.msg;

import lombok.Data;
import util.annos.CurrentUsername;

@Data
public class MsgRqDto {
    @CurrentUsername
    public  String uid;
    public String toChatTargetId;
    public String text;
  //  public TelegramChatType chatType;
}
