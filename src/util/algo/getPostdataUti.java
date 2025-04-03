package util.algo;

import com.sun.net.httpserver.HttpExchange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static util.algo.IndexOfUti.indexOfFirst;

public class getPostdataUti {





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
    public static byte @NotNull [] getBytesFilecontext(byte[] part) {
        int fileDataStartIndex =indexOfFirst(part,"\r\n\r\n".getBytes()) + 4;

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
