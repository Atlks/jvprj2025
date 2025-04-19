package ztest;

import okhttp3.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IPFSUploader {

    //aHEb1ZroyYWBpTNorWC4NelBz8H67FkSyezL5TyJfkA0TYtD2Tawvw
    String apikey="e8717de324b94dffa1a16252b83beca4";
    private static final String IPFS_API_URL = "https://ipfs.infura.io:5001/api/v0/add"; // Infura IPFS API URL

    /** e8717de324b94dffa1a16252b83beca4    apikey
     * Uploads a file to IPFS and returns the hash.
     *
     * @param file The file to upload
     * @return The IPFS hash of the uploaded file
     * @throws IOException If an error occurs during the upload
     */
    public static String uploadFileToIPFS(File file) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Read file into byte array
        byte[] fileBytes = Files.readAllBytes(file.toPath());

        // Create request body with the file
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), RequestBody.create(fileBytes))
                .build();

        // Create the request
        Request request = new Request.Builder()
                .url(IPFS_API_URL)
                .post(body)
                .build();

        // Send the request and get the response
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            // Parse the response JSON to extract the IPFS hash
            String jsonResponse = response.body().string();
            String ipfsHash = jsonResponse.split("\"Hash\":\"")[1].split("\"")[0];
            return ipfsHash;
        } else {
            throw new IOException("Failed to upload file to IPFS. Response: " + response.message());
        }
    }

    public static void main(String[] args) {
        try {
            File file = new File("C:\\0prj\\pyprj\\doc2502\\低廉转账费用的crpt.md"); // Specify the file to upload
            String ipfsHash = uploadFileToIPFS(file);
            saveToFile(ipfsHash,"uplog"+getTimeStamp()+".log");
            System.out.println("File uploaded to IPFS! Hash: " + ipfsHash);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveToFile(String ipfsHash, String file1) {
        String timestamp = getTimeStamp();  // Get the current timestamp
        String filename = "ipfs_record_" + timestamp + ".txt";  // Generate filename

        try (FileWriter writer = new FileWriter(new File(filename))) {
            // Write IPFS hash and content to the file
            writer.write("IPFS Hash: " + ipfsHash + "\n");
            writer.write("Content: " + file1 + "\n");
            System.out.println("File saved as: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save file.");
        }

    }

    /**
     * Gets the current timestamp as a string formatted as "yyyy-MM-dd_HH-mm-ss"
     *
     * @return The formatted timestamp string
     */
    private static String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return sdf.format(new Date());
    }
}
