package apis;

import com.sun.net.httpserver.HttpExchange;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import static biz.BaseBiz.saveDir;
import static util.Util2025.encodeJson;
import static util.UtilLucene.toListMap;
import static util.dbutil.*;

import static util.util2026.*;

public class QueryUsrHdr extends BaseHdr {

    public static void main(String[] args) throws IOException {

        Map<String, String> queryParams = Map.of(
                "uname", "u2",
                "key2", "value2"
        );
        var list1 = qryuser(queryParams);
        System.out.println(encodeJson(list1));
    }

    @Override
    public void handle2(HttpExchange exchange) throws Exception {


        String uname = getcookie("uname", exchange);
        uname="ttt";
        if (uname.equals("")) {
            //need login
            wrtResp(exchange, "needLogin");
            return;
        }

        //blk login ed
        // qryuser(exchange);
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
        var list1 = qryuser(queryParams);
        wrtResp(exchange, encodeJson(list1));
    }

    private static List<SortedMap<String, Object>> qryuser(Map<String, String> queryParams) throws IOException {
        var expression = "";
        String uname = queryParams.get("uname");
        if (saveDir.startsWith("jdbc:mysql") || saveDir.startsWith("jdbc:sqlite")) {
            return getSortedMapsSql(queryParams);
        } else if (saveDir.startsWith("lucene:")) {
            var idxDir=saveDir+"usrs";
            return getSortedMapsLucene(idxDir,queryParams);
        } else {
            //json doc ,ini ,redis ,lucene
            return getSortedMapsIni(queryParams);
        }
        //   return new ArrayList<SortedMap<String, Object>>();
    }

    private static List<SortedMap<String, Object>> getSortedMapsLucene(String saveDir, Map<String, String> queryParams) throws IOException {
        String uname = queryParams.get("uname");
        Directory directory = FSDirectory.open(Paths.get(saveDir));
        // 创建 IndexSearcher
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        // 创建查询
        //类似ssql 条件 uname like '%{uname}%
        // 创建查询，使用WildcardQuery实现模糊匹配
        // 创建MatchAllDocsQuery来查询所有文档
        Query query = new MatchAllDocsQuery();
        if (!uname.equals("")) {
            Term term = new Term("uname", "*" + uname + "*");
              query = new WildcardQuery(term);
        }

        // 执行查询
        TopDocs topDocs = searcher.search(query, 10); // 获取前 10 个结果
        System.out.println("Total Hits: " + topDocs.totalHits);

        return toListMap(topDocs,searcher);
    }



    private static List<SortedMap<String, Object>> getSortedMapsSql(Map<String, String> queryParams) {
        String uname = queryParams.get("uname");
        var expression = "";

        //addObjMysql(obj, collName, saveDir);
        if (!uname.equals("")) {
            expression = "uname like '%{uname}%'";
        }
        var sql = "select * from usrs where 1=1 and " + expression;
        var list1 = qrySql(sql, saveDir);
        return (List<SortedMap<String, Object>>) list1;
    }

    private static List<SortedMap<String, Object>> getSortedMapsIni(Map<String, String> queryParams) {
        String uname = (String) getField2025(queryParams,"uname","");
        var expression = "";
        if (!uname.equals("")) {
            //
            // 转义正则表达式特殊字符
            String escapedUname = uname.replaceAll("([\\\\+*?\\[\\](){}|.^$])", "\\\\$1");
            // 使用转义后的uname变量
            expression = "#this['uname'] matches '.*" + escapedUname + ".*'";
        }
        var list1 = getObjs(saveDir + "usrs", expression);
        return list1;
    }


}
