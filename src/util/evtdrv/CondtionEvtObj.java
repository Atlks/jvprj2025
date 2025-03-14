package util.evtdrv;

import lombok.Data;

import java.lang.reflect.Method;

@Data
public class CondtionEvtObj {

    public Class conditionClz;
    public Object cdtResult;

    public Method mthd;
}




//public Set<Method> methodSet;