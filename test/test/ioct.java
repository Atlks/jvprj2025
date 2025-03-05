package test;

import api.usr.RegHandler;
import cfg.IocPicoCfg;
import com.sun.net.httpserver.HttpExchange;
import org.picocontainer.MutablePicoContainer;
import util.misc.HttpExchangeImp;

import java.io.IOException;
import java.sql.SQLException;

 

public class ioct
{

    public static void main(String[] args) throws IOException, SQLException {
//        MutablePicoContainer container = new DefaultPicoContainer(new org.picocontainer.injectors.FieldInjection());


        HttpExchange he  =
               new HttpExchangeImp("http://localhost:8889/reg?uname=qq2&pwd=ppp", "uname=0093", "output2025.txt");


        MutablePicoContainer container = IocPicoCfg.iniIocContainr();

        // 获取 Client 实例
        RegHandler client = container.getComponent(RegHandler.class);
      //  client.handle(he);
        // 手动注入 Service
       // client.setService(container.getComponent(Service.class));

    }


}
