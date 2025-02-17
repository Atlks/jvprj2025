package test;

import apiUsr.RegHandler;
import apis.BaseHdr;
import com.mysql.cj.xdevapi.Client;
import com.sun.net.httpserver.HttpExchange;
import org.hibernate.Session;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.injectors.ProviderAdapter;
import util.HttpExchangeImp;
import util.SessionProvider;
import utilBiz.OrmUtilBiz;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import static apis.BaseHdr.saveDirUsrs;

public class ioct
{

    public static void main(String[] args) throws IOException, SQLException {
//        MutablePicoContainer container = new DefaultPicoContainer(new org.picocontainer.injectors.FieldInjection());
        BaseHdr.iniCfgFrmCfgfile();

        HttpExchange he  =
               new HttpExchangeImp("http://localhost:8889/reg?uname=qq2&pwd=ppp", "uname=0093", "output2025.txt");


        MutablePicoContainer container = new DefaultPicoContainer();




//        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
        // **使用 Provider，每次获取都是新的 `Session`**
        container.addAdapter(new SessionProvider());
        // 注册组件
        container.addComponent(RegHandler.class);

        // 获取 Client 实例
        RegHandler client = container.getComponent(RegHandler.class);
        client.handle(he);
        // 手动注入 Service
       // client.setService(container.getComponent(Service.class));

    }
}
