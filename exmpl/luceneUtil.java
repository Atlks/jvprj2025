package util;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;


public class luceneUtil {


    public static void main(String[] args) throws IOException {
        // 指定索引文件存储的路径
      //  String indexPath = "/luceneDir/index111"; // 替换为实际路径

        // Example Map to insert
        Map<String, Object> exampleMap = Map.of(
                "id", "111",
                "age", 30,
                "city", "New York"
        );

        // Example dynamic database URL
        String dbUrl = "/lucedeDir222";

        AddRow(exampleMap, dbUrl);



        exampleMap = Map.of(
                "id", "222",
                "age", 30

        );

        AddRow(exampleMap, dbUrl);



        System.out.println(dbUrl);

         //   System.out.println("Documents indexed successfully in: " + indexPath);
    }


    /**
     * updateDocument uniq idx
     * @param exampleMap
     * @param dbUrl
     * @throws IOException
     */
    private static void AddRow(Map<String, Object> exampleMap, String dbUrl) throws IOException {


      //  openIndexWriter
        // 创建分析器
        StandardAnalyzer analyzer = new StandardAnalyzer();

        // 创建目录
        Directory directory = FSDirectory.open(Paths.get(dbUrl));

        // 创建索引写入器配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);

        // 创建文档并添加到索引
        Document doc1 =convertMapToDocument(exampleMap);
        Term termId = new Term("id", exampleMap.get("id").toString());
        indexWriter .updateDocument(    termId, doc1);
        indexWriter.commit();

        // 提交并关闭索引写入器
        indexWriter.close();

    }


    static List<SortedMap<String, Object>> qryuserLucene(Map<String, String> queryParams) throws IOException {

        var idxDir= saveDirUsrs  ;
        String uname = queryParams.get("uname");
        Directory directory = FSDirectory.open(Paths.get(idxDir));
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


    private static void addObjLucene(Object obj, String collName, String saveDir) throws Exception {
        var map1 = (Map<String, Object>) obj;
        //  openIndexWriter
        // 创建分析器
        StandardAnalyzer analyzer = new StandardAnalyzer();

        // 创建目录
        Directory directory = FSDirectory.open(Paths.get(saveDir));

        // 创建索引写入器配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);

        // 创建文档并添加到索引
        Document doc1 = convertMapToDocument(map1);
        Term termId = new Term("id", map1.get("id").toString());
        indexWriter.updateDocument(termId, doc1);
        indexWriter.commit();

        // 提交并关闭索引写入器
        indexWriter.close();
    }

    public static Document convertMapToDocument(Map<String, Object> map) {
        Document document = new Document();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {
                document.add(new StringField(key, (String) value, Field.Store.YES));
            } else if (value instanceof Integer) {
                document.add(new StringField(key, String.valueOf(value), Field.Store.YES));
            } else if (value instanceof Long) {
                document.add(new StringField(key, String.valueOf(value), Field.Store.YES));
            } else if (value instanceof Double) {
                document.add(new StringField(key, String.valueOf(value), Field.Store.YES));
            } else if (value instanceof Boolean) {
                document.add(new StringField(key, String.valueOf(value), Field.Store.YES));
            }
            // 可以根据需要添加更多类型处理
        }

        return document;
    }
}
