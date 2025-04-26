package handler.agt;

import static cfg.AppConfig.sessionFactory;
import static util.tx.HbntUtil.findByHerbinate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import entityx.usr.Usr;
import handler.ylwlt.dto.QueryDto;
import jakarta.annotation.security.PermitAll;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import util.tx.findByIdExptn_CantFindData;


 // 获取非直属上级的 Usr 列表
 @PermitAll
public class GetSubIds {


  
   
    public Object handleRequest(QueryDto dto) throws HibernateException, findByIdExptn_CantFindData {
        

        return findAllSubordinateIds(dto.uname);
    }


    
   /**
     * 查询指定用户的所有下属ID（不包含自己）
     * @param userId 用户ID
     * @return 下属ID列表
     */
    public List<String> findAllSubordinateIds(String userId) {
        String sql = """
            WITH RECURSIVE subordinates AS (
                SELECT id
                FROM usr
                WHERE id = :userId

                UNION ALL

                SELECT u.id
                FROM usr u
                INNER JOIN subordinates s ON u.invtr = s.id
            )
            SELECT id FROM subordinates WHERE id != :userId
            """;
  // 获取当前Session
        Session session = sessionFactory.getCurrentSession();

        // 转换Session为EntityManager
        EntityManager entityManager = session.unwrap(EntityManager.class);
        Query nativeQuery = entityManager.createNativeQuery(sql);
        nativeQuery.setParameter("userId", userId);

        @SuppressWarnings("unchecked")
        List<String> resultList = nativeQuery.getResultList();

        return resultList;
    }



}
