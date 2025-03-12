package util.evtdrv;

import lombok.Data;
import lombok.Getter;

@Data
public class MyEvent {
    private final Object message;

    public MyEvent(Object message) {
        this.message = message;
    }

}

