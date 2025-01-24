package util;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class UtilLucene {

    public static List<SortedMap<String, Object>> toListMap(TopDocs topDocs, IndexSearcher searcher) {
        List<SortedMap<String, Object>> listMap = new ArrayList<>();

        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            int docId = scoreDoc.doc;
            Document doc = searcher.doc(docId);
            SortedMap<String, Object> map = new TreeMap<>();

            // 遍历文档中的所有字段，并将它们添加到map中
            for (IndexableField field : doc.getFields()) {
                map.put(field.name(), field.stringValue());
            }

            listMap.add(map);
        }

        return listMap; }
}
