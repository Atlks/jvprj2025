package handler.agt;

import model.usr.Usr;
import handler.ivstAcc.dto.QueryDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import org.hibernate.HibernateException;
import util.tx.findByIdExptn_CantFindData;

import java.util.ArrayList;
import java.util.List;

import static cfg.Containr.sessionFactory;
import static util.tx.HbntUtil.findByHerbinate;

@Path("/agt/listSupHdl")
// 获取非直属上级的 Usr 列表
@PermitAll
public class getSuperiors {




    // 获取所有上级的 Usr 列表
   public List<Usr> handleRequest(QueryDto dto) throws HibernateException, findByIdExptn_CantFindData {
       List<Usr> result = new ArrayList<>();

       Usr current = getUsrById(dto.uname);
       if (current == null || current.invtr == null || current.invtr.isEmpty()) {
           return result;
       }




       // 注意这里不再需要 parent 变量
       while (current.invtr != null && !current.invtr.isEmpty()) {
           Usr upper = getUsrById(current.invtr); // 使用 current.invtr 获取上级 ID
           if (upper == null) {
               break; // 如果找不到上级，则停止向上查找
           }
           result.add(upper);
           current = upper; // 将 current 指向上级，继续向上查找
       }

       return result;
   }


    // 假设这个方法可以根据 id 查询 Usr 对象
    public Usr getUsrById(String id) throws HibernateException, findByIdExptn_CantFindData {
        return findByHerbinate(Usr.class, id, sessionFactory.getCurrentSession());
    }

}
