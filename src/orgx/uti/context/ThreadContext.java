package orgx.uti.context;

// com.google.protobuf.AbstractProtobufList;
import com.sun.net.httpserver.HttpExchange;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;
import orgx.uti.http.HttpHeader;
import orgx.uti.orm.TupleImpl;

import java.security.Principal;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * 必须一个请求一个thrd，不然会串。。
 *
 */
public class ThreadContext {
    //servlt stande

    //不牢靠，bcs maybe thrd 复用。。
    //get from jwt is btr
    //here only for test
    @Deprecated
    public static ThreadLocal<String> remoteUser = new ThreadLocal<>();
    private static final ThreadLocal<String> userId = new ThreadLocal<>();
    private static final ThreadLocal<String> userRole = new ThreadLocal<>();
    //get frm jwt
    public static ThreadLocal<java.security.Principal> principal=new ThreadLocal<>();


    public static ThreadLocal<Connection> currDbConn=new ThreadLocal<>();




    private static final ThreadLocal<String> traceId = new ThreadLocal<>();
    private static final ThreadLocal<String> requestId = new ThreadLocal<>();
    private static final ThreadLocal<String> transactionId = new ThreadLocal<>();
    private static final ThreadLocal<String> operationMode = new ThreadLocal<>();
    private static final ThreadLocal<String> sourceIp = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> debugEnabled = new ThreadLocal<>();
    private static final ThreadLocal<String> locale = new ThreadLocal<>();
    private static final ThreadLocal<String> timeZone = new ThreadLocal<>();

    public static   ThreadLocal<EntityManager> currEttyMngr = new ThreadLocal<>();
    public static   ThreadLocal<Session> currSession = new ThreadLocal<>();
    public static   ThreadLocal<EntityTransaction> currEntityTransaction = new ThreadLocal<>();
 
  public static   ThreadLocal<HttpHeader> currHttpHeader = new ThreadLocal<>();

    public static   ThreadLocal<HttpExchange> currHttpExchange = new ThreadLocal<>();
    public static   ThreadLocal<Context> currJvlnContext = new ThreadLocal<>();

    public static   ThreadLocal< Map<String, List<String>>> currHttpParamMap = new ThreadLocal<>();
    public static ThreadLocal<Handler> beforeHdl= new ThreadLocal<>();

    public static ThreadLocal<Transaction> currTxHbnt= new ThreadLocal<>();
    public static ThreadLocal<String> currAdmin= ThreadLocal.withInitial(() -> "管理员");


    // 用户 ID
    public static void setUserId(String id) { userId.set(id); }
    public static String getUserId() { return userId.get(); }

    // 用户角色
    public static void setUserRole(String role) { userRole.set(role); }
    public static String getUserRole() { return userRole.get(); }

    // Trace ID
    public static void setTraceId(String id) { traceId.set(id); }
    public static String getTraceId() { return traceId.get(); }

    // 请求 ID
    public static void setRequestId(String id) { requestId.set(id); }
    public static String getRequestId() { return requestId.get(); }

    // 事务 ID
    public static void setTransactionId(String id) { transactionId.set(id); }
    public static String getTransactionId() { return transactionId.get(); }

    // 操作模式
    public static void setOperationMode(String mode) { operationMode.set(mode); }
    public static String getOperationMode() { return operationMode.get(); }

    // 用户来源 IP
    public static void setSourceIp(String ip) { sourceIp.set(ip); }
    public static String getSourceIp() { return sourceIp.get(); }

    // 调试模式
    public static void setDebugEnabled(boolean enabled) { debugEnabled.set(enabled); }
    public static Boolean isDebugEnabled() { return debugEnabled.get(); }

    // 国际化语言
    public static void setLocale(String loc) { locale.set(loc); }
    public static String getLocale() { return locale.get(); }

    // 时区
    public static void setTimeZone(String tz) { timeZone.set(tz); }
    public static String getTimeZone() { return timeZone.get(); }

    // **清理所有 ThreadLocal 数据**
    public static void clear() {
        userId.remove();
        userRole.remove();
        traceId.remove();
        requestId.remove();
        transactionId.remove();
        operationMode.remove();
        sourceIp.remove();
        debugEnabled.remove();
        locale.remove();
        timeZone.remove();
    }

    public static void main(String[] args) {
        ThreadContext.setUserId("123456");
        ThreadContext.setUserRole("ADMIN");
        ThreadContext.setTraceId("abcd-efgh-ijkl");
        ThreadContext.setTransactionId("txn-98765");
        ThreadContext.setOperationMode("READ");
        ThreadContext.setSourceIp("192.168.1.10");
        ThreadContext.setDebugEnabled(true);
        ThreadContext.setLocale("zh_CN");
        ThreadContext.setTimeZone("Asia/Shanghai");

        System.out.println("User ID: " + ThreadContext.getUserId());
        System.out.println("User Role: " + ThreadContext.getUserRole());
        System.out.println("Trace ID: " + ThreadContext.getTraceId());
        System.out.println("Transaction ID: " + ThreadContext.getTransactionId());
        System.out.println("Operation Mode: " + ThreadContext.getOperationMode());
        System.out.println("Source IP: " + ThreadContext.getSourceIp());
        System.out.println("Debug Enabled: " + ThreadContext.isDebugEnabled());
        System.out.println("Locale: " + ThreadContext.getLocale());
        System.out.println("Time Zone: " + ThreadContext.getTimeZone());

        ThreadContext.clear();
    }
}

