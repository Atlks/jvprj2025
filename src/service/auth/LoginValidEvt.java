package service.auth;

import org.springframework.context.ApplicationEvent;

public class LoginValidEvt  extends ApplicationEvent {
    public LoginValidEvt(Object source) {
        super(source);
    }
}
