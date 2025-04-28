package util.evtdrv;


import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.Method;

//imt aws faas fun
@Data
@Accessors(chain = true)
public class EventListenerHdlr {
   public String funFullName;
   public Method handleRequestMthd;
}
