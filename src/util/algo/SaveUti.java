package util.algo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SaveUti {


    // Save file content to disk
    public static void saveFile(byte[] fileContentBytes, String outputDir, String filename) throws IOException {
        File file = new File(outputDir, filename);
        Files.write(file.toPath(), fileContentBytes);
    }
    // Save file content to disk
    public static void saveFile(String fileContent, String outputDir, String filename) throws IOException {
        File file = new File(outputDir, filename);
        Files.write(file.toPath(), fileContent.getBytes());
    }
}
