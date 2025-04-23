package util.tx;

import entityx.usr.Usr;
import entityx.baseObj;
import entityx.PageResult;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import util.entty.PageDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import static util.oo.ArrUtil.subList2025;

import static util.tx.dbutil.nativeQueryGetResultList;
import static util.tx.dbutil.setPrmts4sql;


/**
 *  todo to hbnt uti...
 */
public class Pagging {

    public static int getstartPosition(int pageNumber, int pageSize) {

        int startPosition = (pageNumber - 1) * pageSize; // 计算起始行
        return startPosition;
    }


    public static @NotNull PageResult<?> getPageResultByHbntV4(@NotBlank  String sql, Map<String, Object> sqlprmMap, PageDto pageobj, Session session,Class modelCls) throws SQLException {

        System.out.println("fun getPageResultByHbntV4(sql= "+sql+",modelCls="+modelCls);
        NativeQuery  nativeQuery = session.createNativeQuery(sql, modelCls);
        setPrmts4sql(sqlprmMap, nativeQuery);
        // 设置分页
        nativeQuery.setFirstResult(getstartPosition(pageobj.page, pageobj.pagesize));
        nativeQuery.setMaxResults(pageobj.pagesize);
        //       .setParameter("age", 18);
        List<?> list1 = nativeQuery.getResultList();


        //------------page
        long totalRecords = nativeQuery.getResultCount();


        int totalPages = (int) Math.ceil((double) totalRecords / pageobj.pagesize);
        return new PageResult<>(list1, totalRecords, totalPages,pageobj.page,pageobj.pagesize);
    }

    public static @NotNull PageResult<?> getPageResultByHbntRtLstmap(@NotBlank  String sql, Map<String, Object> sqlprmMap, PageDto pageobj, Session session) throws SQLException {

        System.out.println("fun getPageResultByHbntV3(sql= "+sql);
        NativeQuery<?> nativeQuery = session.createNativeQuery(sql, Map.class );
        setPrmts4sql(sqlprmMap, nativeQuery);
        // 设置分页
        nativeQuery.setFirstResult(getstartPosition(pageobj.page, pageobj.pagesize));
        nativeQuery.setMaxResults(pageobj.pagesize);
        //       .setParameter("age", 18);
        List<?> list1 = nativeQuery.getResultList();


        //------------page
        long totalRecords = nativeQuery.getResultCount();


        int totalPages = (int) Math.ceil((double) totalRecords / pageobj.pagesize);
        return new PageResult<>(list1, totalRecords, totalPages,pageobj.page,pageobj.pagesize);
    }


    //bcs use usr .class
    @Deprecated
    public static @NotNull PageResult<?> getPageResultByHbntV3(@NotBlank  String sql, Map<String, Object> sqlprmMap, PageDto pageobj, Session session) throws SQLException {

        System.out.println("fun getPageResultByHbntV3(sql= "+sql);
        NativeQuery<Usr> nativeQuery = session.createNativeQuery(sql, Usr.class);
        setPrmts4sql(sqlprmMap, nativeQuery);
        // 设置分页
        nativeQuery.setFirstResult(getstartPosition(pageobj.page, pageobj.pagesize));
        nativeQuery.setMaxResults(pageobj.pagesize);
        //       .setParameter("age", 18);
        List<Usr> list1 = nativeQuery.getResultList();


        //------------page
        long totalRecords = nativeQuery.getResultCount();


        int totalPages = (int) Math.ceil((double) totalRecords / pageobj.pagesize);
        return new PageResult<>(list1, totalRecords, totalPages,pageobj.page,pageobj.pagesize);
    }

    public static PageResult<?> getPageResultByHbntV2(String sql, Map<String, Object> sqlprmMap, int pageNumber, int pageSize, Session session) throws SQLException {


        NativeQuery nativeQuery = session.createNativeQuery(sql);
        setPrmts4sql(sqlprmMap, nativeQuery);
        // 设置分页
        nativeQuery.setFirstResult(getstartPosition(pageNumber, pageSize));
        nativeQuery.setMaxResults(pageSize);
        //       .setParameter("age", 18);
        List<?> list1 = nativeQuery.getResultList();


        //------------page
        long totalRecords = nativeQuery.getResultCount();


        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        return new PageResult<>(list1, totalRecords, totalPages,pageNumber,pageSize);
    }

    /**
     * 错误的 COUNT(*) 查询 👉 COUNT(*) 语句不能直接嵌套在 FROM (...)，如果 sql 本身包含 ORDER BY，可能会报错。
     *
     * @param sqlNoOrdby
     * @param sqlprmMap
     * @param list1
     * @param pageSize
     * @return
     * @throws SQLException
     */
//todo session 放外面
    @Deprecated
    public static PageResult<SortedMap<String, Object>> getPageResultByCntsql(String sqlNoOrdby, Map<String, Object> sqlprmMap, List list1, long pageSize, org.hibernate.Session session) throws SQLException {
        String countSql = "SELECT COUNT(*) FROM (" + sqlNoOrdby + ") t";
        System.out.println("countSql=" + countSql);
//        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);

        NativeQuery nativeQuery = session.createNativeQuery(countSql);
        setPrmts4sql(sqlprmMap, nativeQuery);
        long totalRecords = ((Number) nativeQuery
//                .setParameter(1, username)
//                .setParameter(2, minAge)
                .getSingleResult()).longValue();

        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        return new PageResult<>(list1, totalRecords, totalPages);
    }

    /**
     * back pagging
     *
     * @param list1
     * @param pageSize
     * @param pageNumber
     * @param <T>
     * @return
     */

    public static <T> PageResult<T> getPageResultBySblst(List<T> list1, int pageSize, int pageNumber) {
        long totalRecords = list1.size();
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        List<T> listPageed = subList2025(list1, pageSize, pageNumber);
        return new PageResult<>(listPageed, totalRecords, totalPages);
    }


    public static <T> PageResult<T> getPageResultBySublist(String sql, Map<String, Object> sqlprmMap, baseObj pageDto, Session session, Class class1) {
        List<T> lst = nativeQueryGetResultList(sql, sqlprmMap, session, class1);
        //    var list1 = getSortedMapsBypages( sql,pageSize, pageNumber);
        // 1️⃣ 计算总记录数
        var list1 = getPageResultBySblst(lst, pageDto.pagesize, pageDto.page);
        return list1;
    }
}
