package handler.agt;

import static cfg.Containr.sessionFactory;
import static util.Oosql.SqlBldr.*;

import java.util.List;

import jakarta.ws.rs.Path;
import model.agt.Agent;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import handler.ivstAcc.dto.QueryDto;
import jakarta.annotation.security.PermitAll;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import util.tx.findByIdExptn_CantFindData;

@Path("/apiv1/agt/listSubHdl")
 // 获取下级
 @PermitAll
public class GetSubIds {


  
   
    public Object handleRequest(QueryAgtDto dto) throws HibernateException, findByIdExptn_CantFindData {
        

        return findAllSubordinate(dto.agentAccount);
    }

    /**
     * 查询指定用户的所有下属ID（不包含自己）
     * @param userId 用户ID
     * @return 下属ID列表
     */
    public List<Agent> findAllSubordinate(String userId) {
        String sql = selectFrom(Agent.class)+where(Agent.Fields.parent_agent_id,"=",userId); //
        // 获取当前Session
        Session session = sessionFactory.getCurrentSession();

        // 转换Session为EntityManager
        EntityManager entityManager = session.unwrap(EntityManager.class);
        Query nativeQuery = entityManager.createNativeQuery(sql,Agent.class);
       // nativeQuery.setParameter("userId", userId);

        @SuppressWarnings("unchecked")
        List<Agent> resultList = nativeQuery.getResultList();

        return resultList;
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
