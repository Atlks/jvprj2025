package util.excptn;

import com.alibaba.fastjson2.JSON;

import java.util.List;

import static util.misc.util2026.getStackTraceAsString;

public class ExptUtil {

    //wz qrystr
    public static ThreadLocal<String> curUrl = new ThreadLocal<>();
    public static ThreadLocal<String> curUrlPrm = new ThreadLocal<>();
    public static ThreadLocal<String> curFun4dbg = new ThreadLocal<>();

    public static ThreadLocal<Object> currFunPrms4dbg = new ThreadLocal<>();
    public static ThreadLocal<List<Throwable>> lastExsList =new ThreadLocal<>();
    public static void appendEx2lastExs(Throwable e) {
        ExptUtil.lastExsList.get().add(e);
    }
    /**
     * 使用fastjson2，，将jsonstr转换为err对象
     *
     * @param jsonStr
     * @return
     */
    public static Err toERR(String jsonStr) {

        try {
            if (jsonStr == null || jsonStr.isEmpty()) {
                return new Err("", "", "", null);
            }
            return JSON.parseObject(jsonStr, Err.class);
        } catch (Exception e) {
            //not errmsg,just str msg ,or another type
            return new Err(jsonStr, "", "", null);
        }


    }
    public static void addInfo2ex(ExceptionBase ex, Throwable e) {
        if (ex.fun.equals(""))
            ex.fun = curFun4dbg.get();
        if (ex.funPrm == null)
            ex.funPrm = currFunPrms4dbg.get();
        ex.url = curUrl.get();
        ex.urlprm = curUrlPrm.get();
        String stackTraceAsString = getStackTraceAsString(e);
        ex.stackTraceStr = stackTraceAsString;
    }
}
