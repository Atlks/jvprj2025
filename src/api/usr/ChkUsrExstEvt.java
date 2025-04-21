package api.usr;

import handler.usr.dto.RegDto;
import org.springframework.context.ApplicationEvent;

public class ChkUsrExstEvt extends ApplicationEvent {
    public ChkUsrExstEvt(RegDto dtoReg) {
        super(dtoReg);
    }
}
