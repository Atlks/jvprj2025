//package util;
//
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.services.gmail.Gmail;
//import com.google.api.services.gmail.GmailScopes;
//import com.google.api.services.gmail.model.ListMessagesResponse;
//import com.google.api.services.gmail.model.Message;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.security.GeneralSecurityException;
//import java.util.List;
//
//public class EmlGgl {
//
//    public static void gglML(String credentialsPath, String subjectQuery, String saveDir) throws IOException, GeneralSecurityException {
//        // Set up the Gmail client
//        Gmail service = getGmailService(credentialsPath);
//
//        // Define request parameters
//        com.google.api.services.gmail.Gmail.Users.Messages.List request = service.users().messages().list("me");
//        request.setQ("subject:" + subjectQuery);
//        request.setLabelIds("INBOX");
//        request.setIncludeSpamTrash(false);
//        request.setMaxResults(500L);
//
//        String pageToken = null;
//        do {
//            request.setPageToken(pageToken);
//            ListMessagesResponse response = request.execute();
//            List<Message> messages = response.getMessages();
//
//            if (messages != null) {
//                for (Message message : messages) {
//                    processMessage(service, message, saveDir);
//                }
//            }
//
//            pageToken = response.getNextPageToken();
//        } while (pageToken != null);
//    }
//
//    private static Gmail getGmailService(String credentialsPath) throws IOException, GeneralSecurityException {
//        // Load client secrets
//        InputStream in = EmlGgl.class.getResourceAsStream(credentialsPath);
//        GoogleCredential credential = GoogleCredential.fromStream(in, GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance())
//                .createScoped(GmailScopes.all());
//
//        // Build a new authorized API client service
//        return new Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), credential)
//                .setApplicationName("Gmail API Java Quickstart")
//                .build();
//    }
//
//    private static void processMessage(Gmail service, Message message, String saveDir) throws IOException {
//        Message emailInfoResponse = service.users().messages().get("me", message.getId()).execute();
//
//        System.out.println("Message ID: " + message.getId());
//        System.out.println("Snippet: " + emailInfoResponse.getSnippet());
//
//        // Get email subject
//        String subject = emailInfoResponse.getPayload().getHeaders().stream()
//                .filter(header -> "Subject".equals(header.getName()))
//                .findFirst()
//                .map(header -> header.getValue())
//                .orElse(null);
//
//        // Get email body
//        String body = null;
//        if (emailInfoResponse.getPayload().getBody() != null) {
//            body = emailInfoResponse.getPayload().getBody().getData();
//            // Decode Base64 encoded content
//            body = new String(Base64.getDecoder().decode(body));
//        }
//
//        // Print email subject and body
//        System.out.println("Subject: " + subject);
//        System.out.println("Body: " + (body != null ? body.substring(0, Math.min(body.length(), 200)) : ""));
//
//        // Save email body to file
//        String fileName = saveDir + "/" + subject.replaceAll("[^a-zA-Z0-9.-]", "_") + ".htm";
//        File file = new File(fileName);
//        if (!file.exists()) {
//            java.nio.file.Files.write(file.toPath(), body.getBytes());
//        }
//    }
//
//    public static void main(String[] args) {
//        try {
//            gglML("credentials.json", "subjectQuery", "saveDir");
//        } catch (IOException | GeneralSecurityException e) {
//            e.printStackTrace();
//        }
//    }
//}
