package api.uti;

import com.sun.net.httpserver.HttpExchange;
import entityx.Non;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;
import util.ex.existUserEx;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static util.algo.JarClassScanner.getPrjPath;
import static util.misc.Util2025.mkdir2025;
import static util.proxy.AtProxy4api.httpExchangeCurThrd;

/**
 * login
 * //@param uname
 * //@param pwd
 */
@RestController


//组合了 @Controller 和 @ResponseBody，表示该类是 REST API 控制器，所有方法的返回值默认序列化为 JSON 或 XML。
@PermitAll
@Path("/uploads")
//   http://localhost:8889/login?uname=008&pwd=000
@NoArgsConstructor
@Data
public class UpldHr implements Icall<Non, Object>  {
    /**
     * upload file
     * @return file path
     * @throws Exception
     * @throws existUserEx
     */
    @Override
    public Object main(@BeanParam Non usr_dto) throws Exception {
        HttpExchange httpExchange= httpExchangeCurThrd.get();
        HttpExchange exchange=httpExchange;
        String uploadDir=getPrjPath()+"/res/uploads";
      //  mkdir2025(uploadDir+"/xxx.jpg");
        // 确保上传目录存在
        Files.createDirectories(Paths.get(uploadDir));



        // 提取 Content-Type 以获取 boundary
        String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
        String boundary = extractBoundary(contentType);
        if (boundary .equals("")) {
            exchange.sendResponseHeaders(400, 0); // Bad Request
            return "";
        }

        // 生成唯一文件名
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date());
        String newFileName = "upload_" + timeStamp + ".jpg";
        java.nio.file. Path filePath = Paths.get(uploadDir, newFileName);

        // 处理上传的文件
        // 读取请求体并提取文件内容
        InputStream inputStream = exchange.getRequestBody();
        parseSingleFile(inputStream,boundary,uploadDir,newFileName);


        // 发送响应
        String rltpath="/res/uploads/"+newFileName;
        return rltpath;

    }
    public static void parseSingleFile(InputStream inputStream, String boundary, String outputDir, String newFileName) throws IOException {
        // Step 1: Read the request body and split by boundary
     //   byte[] boundaryBytes = boundary.getBytes("UTF-8");
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int bytesRead;
        byte[] tempBuffer = new byte[8192];

        // Read input stream and append to buffer
        while ((bytesRead = inputStream.read(tempBuffer)) != -1) {
            buffer.write(tempBuffer, 0, bytesRead);
        }
        byte[] bodyBytes = buffer.toByteArray();

     //   bodyBytes.split  boundaryBytes

        // Step 2: Find the part with the file (based on boundary)
        String body = new String(bodyBytes, "utf-8");
        String[] parts = body.split("--" + boundary);
        for (String part : parts) {
            if (part.contains("Content-Disposition")) {
                // This part contains the file data
                String contentDisposition = part.split("\r\n")[1];  // Content-Disposition header
                String filename = extractFilename(contentDisposition);

                // Extract the file content (after the headers)
                int fileDataStartIndex = part.indexOf("\r\n\r\n") + 4;
                var spltStr="--" + boundary;
                fileDataStartIndex=fileDataStartIndex+spltStr.length();
                byte[] fileContentBytes = Arrays.copyOfRange(bodyBytes, fileDataStartIndex, bodyBytes.length-("--" + boundary).length());
                // Save the file to the output directory
                // 生成唯一文件名
//                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date());
//                String newFileName = "upload_" + timeStamp + ".jpg";
             //   java.nio.file. Path filePath = Paths.get(uploadDir, newFileName);
                saveFile(fileContentBytes, outputDir, newFileName);
                break; // Only one file, so we can stop after saving it
            }
        }
    }
    // Save file content to disk
    private static void saveFile(byte[] fileContentBytes, String outputDir, String filename) throws IOException {
        File file = new File(outputDir, filename);
        Files.write(file.toPath(), fileContentBytes);
    }
    // Save file content to disk
    private static void saveFile(String fileContent, String outputDir, String filename) throws IOException {
        File file = new File(outputDir, filename);
        Files.write(file.toPath(), fileContent.getBytes());
    }
    // Extract the filename from the Content-Disposition header
    private static String extractFilename(String contentDisposition) {
        String filename = null;
        String[] elements = contentDisposition.split(";");
        for (String element : elements) {
            if (element.trim().startsWith("filename=")) {
                filename = element.split("=")[1].trim().replace("\"", "");
            }
        }
        return filename;
    }
    // 从 Content-Type 中提取 boundary
    private String extractBoundary(String contentType) {
        String[] parts = contentType.split(";");
        for (String part : parts) {
            if (part.trim().startsWith("boundary=")) {
                return part.trim().substring("boundary=".length());
            }
        }
        return "";
    }

    private String extractFileName(String partHeader) {
        // Extract filename from Content-Disposition header
        String[] parts = partHeader.split(";");
        for (String part : parts) {
            if (part.trim().startsWith("filename=")) {
                return part.trim().substring("filename=".length()).replaceAll("\"", "");
            }
        }
        return "";
    }

    private String extractContentType(String partHeader) {
        // Extract Content-Type from the header
        String[] parts = partHeader.split(";");
        for (String part : parts) {
            if (part.trim().startsWith("Content-Type:")) {
                return part.trim().substring("Content-Type:".length()).trim();
            }
        }
        return "application/octet-stream";
    }


}
