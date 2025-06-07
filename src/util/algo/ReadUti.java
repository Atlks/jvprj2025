package util.algo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadUti {
    /**
     * 读取文本文件，解析为json，
     * 使用gson
     * @param pathname
     * @return
     */
    public static Object ReadFileAsJsonObjOrJsonArray(String pathname) throws IOException {

        String content = Files.readString(Paths.get(pathname), StandardCharsets.UTF_8);
        return getJsonObjByJackson(pathname, content);

    }

    private static Object getJsonObjByJackson(String pathname, String content) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(content);
            if (node.isObject()) {
                return (ObjectNode) node;
            } else if (node.isArray()) {
                return (ArrayNode) node;
            } else {
                throw new RuntimeException("cache fmt err, key=" + pathname);
            }
        } catch (IOException e) {
            throw new RuntimeException("JSON parsing failed for key=" + pathname, e);
        }
    }

    private static Object getObjectJsob(String pathname, String content) {
        JsonElement element = JsonParser.parseString(content);
        if (element.isJsonObject()) {
            return element.getAsJsonObject();
        } else if (element.isJsonArray()) {
            return element.getAsJsonArray();
        } else {
            return new RuntimeException("cache fmt err,key=" + pathname);
        }
    }


    public static void wrtFile(String file, String txt) throws IOException {
        // 确保目录存在
        File f = new File(file);
        f.getParentFile().mkdirs();

        // 写入文件，覆盖旧内容
        Files.write(Paths.get(file), txt.getBytes(StandardCharsets.UTF_8));

    }

}
