package util;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class LuceneQueryExample {
    public static void main(String[] args) {


        String indexPath = "/lucedeDir222";
        try {
            // 创建目录
            Directory directory = FSDirectory.open(Paths.get(indexPath));
            // 创建 IndexSearcher
            DirectoryReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);

            // 创建查询
            Query query = new TermQuery(new org.apache.lucene.index.Term("id", "222"));

            // 执行查询
            var topDocs = searcher.search(query, 10); // 获取前 10 个结果
            System.out.println("Total Hits: " + topDocs.totalHits);

            // 遍历结果
            for (var scoreDoc : topDocs.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                System.out.println("Found Document: ID = " + doc.get("id"));
                // 可以根据需要输出其他字段
            }

            // 关闭资源
            reader.close();
            directory.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
