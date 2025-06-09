package Usr;

import cfg.IniCfg;
import com.baomidou.mybatisplus.core.assist.ISqlRunner;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import model.usr.Usr;
import model.usr.UsrMapper;
import org.apache.ibatis.session.SqlSession;
import orgx.uti.context.ProcessContext;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static cfg.CfgSvs.buildSessionFactory;
import static cfg.IniCfg.iniContnr4cfgfile;

public class mbtsplsLmbdQryWrp {

    public static void main(String[] args) throws Exception {
        iniContnr4cfgfile();
        System.out.println(ProcessContext.jdbcUrl);
        List<Class> mapperClzs=new ArrayList<>();
        mapperClzs.add(UsrMapper.class);
        buildSessionFactory(mapperClzs);
        LambdaQueryWrapper<Usr> query = Wrappers.lambdaQuery();

        query.eq(Usr::getUname, "uuuu6666") ;   // 条件：用户名等于传入值
                   // 条件：状态等于传入值
        System.out.println("-----------");
        System.out.println(query.getSqlSegment());
        System.out.println(query.getSqlSelect());
        System.out.println(query.getTargetSql());
        System.out.println("-----------");

        // 查询满足条件的列表
        // 查询执行
        try (SqlSession session = ProcessContext.sqlSessionFactory.openSession()) {
            UsrMapper usrMapper = session.getMapper(UsrMapper.class);
            List<Usr> result = usrMapper.selectList(query);
            System.out.println(result);
        }

    }
}
