package api.usr;

import org.springframework.context.ApplicationEvent;

public class ChkUsrExstEvt extends ApplicationEvent {
    public ChkUsrExstEvt(RegDto dtoReg) {
        super(dtoReg);
    }
}
