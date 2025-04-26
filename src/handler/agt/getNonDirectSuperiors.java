package handler.agt;

import static cfg.AppConfig.sessionFactory;
import static util.tx.HbntUtil.findByHerbinate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import entityx.usr.Usr;
import jakarta.annotation.security.PermitAll;
import util.tx.findByIdExptn_CantFindData;


 // 获取非直属上级的 Usr 列表
 @PermitAll
public class getNonDirectSuperiors {


    // 假设这个方法可以根据 id 查询 Usr 对象
    public Usr getUsrById(String id) throws HibernateException, findByIdExptn_CantFindData {
         return findByHerbinate(Usr.class, id, sessionFactory.getCurrentSession());
    }

   
    public List<Usr> handleRequest(String userId) throws HibernateException, findByIdExptn_CantFindData {
        List<Usr> result = new ArrayList<>();

        Usr current = getUsrById(userId);
        if (current == null || current.invtr == null || current.invtr.isEmpty()) {
            return result;
        }

        // 找到直属上级
        Usr parent = getUsrById(current.invtr);
        if (parent == null) {
            return result;
        }

        // 从直属上级的上级开始
        while (parent.invtr != null && !parent.invtr.isEmpty()) {
            Usr upper = getUsrById(parent.invtr);
            if (upper == null) {
                break;
            }
            result.add(upper);
            parent = upper;
        }

        return result;
    }

}
