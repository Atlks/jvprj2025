package util.tx;

import entityx.baseObj;
import entityx.PageResult;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import static util.ArrUtil.subList2025;

import static util.dbutil.nativeQueryGetResultList;
import static util.dbutil.setPrmts4sql;

public class Pagging {

    public static int getstartPosition(int pageNumber, int pageSize) {

        int startPosition = (pageNumber - 1) * pageSize; // 计算起始行
        return startPosition;
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


        long totalPages = (long) Math.ceil((double) totalRecords / pageSize);
        return new PageResult<>(list1, totalRecords, totalPages);
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

        long totalPages = (long) Math.ceil((double) totalRecords / pageSize);
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
        long totalPages = (long) Math.ceil((double) totalRecords / pageSize);

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
