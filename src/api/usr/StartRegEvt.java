package api.usr;

import org.springframework.context.ApplicationEvent;

public class StartRegEvt extends ApplicationEvent {
    public StartRegEvt(RegDto dtoReg) {
        super(dtoReg);
    }
}
