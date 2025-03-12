package api.usr;

import org.springframework.context.ApplicationEvent;

public class LoginEvt extends ApplicationEvent {
    public LoginEvt(Object source) {
        super(source);
    }
}
