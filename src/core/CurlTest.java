package core;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurlTest {
    public static void main(String[] args) throws Exception {
        String url = "http://18.136.123.232:8889/wlt/findBalanceOverviewHdl";
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI5OTkiLCJpYXQiOjE3NDcxMzY3MzcsImlzcyI6ImF0aSIsInVwbiI6Ijk5OSIsInByZWZlcnJlZF91c2VybmFtZSI6Ijk5OSIsImVtYWlsIjoiYXRAdWtlLmNvbSIsInVuYW1lIjoiOTk5IiwiYXVkIjoiVVNFUiIsInJvbGUiOiJVU0VSIiwiZXhwIjoxNzU1Nzc2NzM3LCJqdGkiOiJjMjU0ZjE0YmIzMzg0Y2UwYTMyZDI4OWFmNzc2NThjNSJ9.8tge5NHcXwrl6r9r_kJ2IFRHqfZJTPTaOdk2wMagdtf0GTg_javGZea4ZLmx3IFFyapPceGdBcmD6MRtdbEiFw";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println("Response: " + response.body());
    }
}
