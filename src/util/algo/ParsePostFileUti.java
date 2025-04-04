package util.algo;

import com.sun.net.httpserver.HttpExchange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import util.entty.UploadedFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static util.algo.CutJoin.splitByBytearray;
import static util.algo.CutJoin.subByte;
import static util.algo.IndexOfUti.indexOf;
import static util.algo.IndexOfUti.indexOfFirst;
import static util.algo.ParsePostFileUti.getBytesFilecontext;


/**
 * post data
 * ------geckoformboundary34b49dbfd9a4a64fa922e966447ac390
 * Content-Disposition: form-data; name="file"; filename="download (1).jpg"
 * Content-Type: image/jpeg
 * <p>
 * fgsfgsfg
 * ------geckoformboundary34b49dbfd9a4a64fa922e966447ac390--
 * Content-Disposition: form-data; name="file"; filename="download (1).jpg"
 * * Content-Type: image/jpeg
 * * <p>
 * * fgsfgsfg
 * * ------geckoformboundary34b49dbfd9a4a64fa922e966447ac390--
 */
public class ParsePostFileUti {


    @NotBlank
    public static String getBoundary(@jakarta.validation.constraints.NotNull HttpExchange exchange) {
        String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
        String boundary = extractBoundary(contentType);
        return boundary;
    }

    public static String getFilenameFromUpdtStream(String partStr) {
        // This part contains the file data
        String contentDisposition = partStr.split("\r\n")[1];  // Content-Disposition header
        String filename = extractFilename(contentDisposition);
        return filename;
    }

    // Extract the filename from the Content-Disposition header
    public static String extractFilename(String contentDisposition) {
        String filename = null;
        String[] elements = contentDisposition.split(";");
        for (String element : elements) {
            if (element.trim().startsWith("filename=")) {
                filename = element.split("=")[1].trim().replace("\"", "");
            }
        }
        return filename;
    }

    public static String getNameByParthead(String partHead) {
        String contentDisposition = partHead.split("\r\n")[1];  // Content-Disposition header
        String filename = getName(contentDisposition);
        return filename;
    }

    /**
     * 解析name字段，
     * Content-Disposition: form-data; name="file"; filename="download (1).jpg"
     *
     * @param contentDisposition
     * @return
     */
    public static String getName(String contentDisposition) {
        if (contentDisposition == null) return null;

        // 使用正则提取 name="xxx"
        Pattern pattern = Pattern.compile("name=\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(contentDisposition);
        if (matcher.find()) {
            return matcher.group(1); // 返回匹配到的 name 值
        }

        return "";
    }

    // 从 Content-Type 中提取 boundary
    @NotBlank
    public static String extractBoundary(@NotBlank String contentType) {
        String[] parts = contentType.split(";");
        for (String part : parts) {
            if (part.trim().startsWith("boundary=")) {
                return part.trim().substring("boundary=".length());
            }
        }
        return "";
    }

    public static byte @NotNull [] getBytes4bodyPost(InputStream inputStream) throws IOException {
        ByteArrayOutputStream bufferByteArray = new ByteArrayOutputStream();
        int bytesRead;
        byte[] tempBuffer = new byte[8192];

        // Read input stream and append to bufferByteArray
        while ((bytesRead = inputStream.read(tempBuffer)) != -1) {
            bufferByteArray.write(tempBuffer, 0, bytesRead);
        }
        byte[] bodyBytes = bufferByteArray.toByteArray();
        return bodyBytes;
    }

    public static List<UploadedFile> parseUpldedFiles(HttpExchange exchange) throws Exception {
        List<UploadedFile> li = new ArrayList<>();
        InputStream inputStream = exchange.getRequestBody();
        String boundary = getBoundary(exchange);
        byte[] boundaryBytes = boundary.getBytes("UTF-8");
        byte[] bodyBytes = getBytes4bodyPost(inputStream);

        List<byte[]> parts = splitByBytearray(bodyBytes, boundaryBytes);
        for (byte[] part : parts) {
            byte[] partHead = subByte(part, 3000);
            var partHeadStr = new String(partHead, "utf-8");
            var extractContentType = extractContentType(partHeadStr);
            if (partHeadStr.contains("Content-Disposition")) {
                String filename = getFilenameFromUpdtStream(partHeadStr);
                System.out.println(filename);
                var getNameByParthead = getNameByParthead(partHeadStr);

                // Extract the file content (after the headers)
                byte[] fileContentBytes = getBytesFilecontext(part);
                UploadedFile uf = new UploadedFile(getNameByParthead, filename, extractContentType, fileContentBytes);
                li.add(uf);
            }

        }
        return li;

    }

    /**
     * 这个函数的作用是将 bodyBytes 按照 boundaryBytes 进行分割，并返回一个 List<byte[]>，其中每个 byte[] 是一个分割后的部分。
     * 流程是
     * 遍历 bodyBytes 并查找 boundaryBytes 的位置
     * <p>
     * 记录每个分段的起始索引
     * <p>
     * 将分段的字节数组存入 List<byte[]> 并返回
     *
     * @param bodyBytes
     * @param boundaryBytes
     * @return
     */
    public static List<byte[]> splitByBytearray(byte[] bodyBytes, byte[] boundaryBytes) {

        List<byte[]> parts = new ArrayList<>();
        int start = 0;
        int index;

        while ((index = indexOf(bodyBytes, boundaryBytes, start)) != -1) {
            // 提取从 start 到 boundary 开始的位置的部分
            if (index > start) {
                byte[] part = new byte[index - start];
                System.arraycopy(bodyBytes, start, part, 0, part.length);
                parts.add(part);
            }
            // 跳过 boundary
            start = index + boundaryBytes.length;
        }

        // 添加最后一部分（如果有）
        if (start < bodyBytes.length) {
            byte[] part = new byte[bodyBytes.length - start];
            System.arraycopy(bodyBytes, start, part, 0, part.length);
            if (part.length > 0)
                parts.add(part);
        }

        return parts;
    }

    /**
     * get file context by part
     *
     * @param part
     * @return
     */
    public static byte @NotNull [] getBytesFilecontext(byte[] part) {
        int fileDataStartIndex = indexOfFirst(part, "\r\n\r\n".getBytes()) + 4;

        byte[] fileContentBytes = Arrays.copyOfRange(part, fileDataStartIndex, part.length);
        return fileContentBytes;
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

    private static String extractContentType(String partHeader) {
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
