package util;

import apiUsr.LoginHdr;
import apiUsr.RegHandler;
import apis.BaseHdr;
import org.jetbrains.annotations.NotNull;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

public class IocUtil {

    @NotNull
    public static MutablePicoContainer iniIocContainr() {
        BaseHdr.iniCfgFrmCfgfile();
        MutablePicoContainer container = new DefaultPicoContainer();


//        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
        // **使用 Provider，每次获取都是新的 `Session`**
        container.addAdapter(new SessionProvider());
        // 注册组件
        container.addComponent(RegHandler.class);
        container.addComponent(LoginHdr.class);

        return container;
    }
}
