package tools;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static util.misc.Util2025.encodeJson;

class RecordDayly {
    public String date;
    public double amount;
    public String category;
    public String description;
    // Default constructor required by Jackson for deserialization
    public RecordDayly() {}
    public RecordDayly(String date, double amount, String category, String description) {
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }
}

class CategorySummary {
    public String category;
    public double totalAmount;
    // Default constructor required by Jackson for deserialization
    public CategorySummary() {}
    public CategorySummary(String category, double totalAmount) {
        this.category = category;
        this.totalAmount = totalAmount;
    }
}

public class grpbyMth {

    public static void main(String[] args) throws IOException {

        gene2501();
    }

    private static void gene2501() throws IOException {
        String filePath = "C:\\Users\\attil\\IdeaProjects\\jvprj2025\\src\\tools\\2502mnyLg.md";
        filePath="C:\\Users\\attil\\IdeaProjects\\jvprj2025\\src\\tools\\2501mnylg.md";

        String jsonF = "2501mnylg.json";
        getClrjsonData(filePath, jsonF);
        grpbyx(jsonF, "2501mnylg_grpby.json");
    }

    private static void getClrjsonData(String filePath , String jsonF) throws IOException {
        List<RecordDaylyx> records = new ArrayList<>();
        // Read file line by line
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String lineFx = replaceDoublePlacespace(line);
                String[] fieldArr = lineFx.split(" ");

                // Ensure the array has enough elements
                if (fieldArr.length < 3) continue;

                String dscrp = String.join(" ", Arrays.copyOfRange(fieldArr, 3, fieldArr.length));

                try {
                    double amt = NumberUtils.toDouble(fieldArr[1]);
                    if (amt == 0.0) continue; // Skip if amount is 0
                    RecordDaylyx record = new RecordDaylyx(fieldArr[0], amt, fieldArr[2], dscrp);
                    records.add(record);
                } catch (Exception e) {
                    // Handle the exception gracefully
                    e.printStackTrace();
                }
            }
        }

        // Serialize to JSON and write to file
        String json = new ObjectMapper().writeValueAsString(records);

        writeToFile(json, jsonF);
    }

    //// Group by category and summarize
    // Group by category and calculate total amount
    public static void grpbyx(String filePath, String fileName) throws IOException {
        String jsonText = new String(Files.readAllBytes(Paths.get(filePath)));
        ObjectMapper objectMapper = new ObjectMapper();
        List<RecordDayly> records = objectMapper.readValue(jsonText, new TypeReference<List<RecordDayly>>() {});

        Map<String, Double> summaryMap = records.stream()
                .collect(Collectors.groupingBy(record -> record.category,
                        Collectors.summingDouble(record -> record.amount)));

        // Create a list of CategorySummary
        List<CategorySummaryx> summary = summaryMap.entrySet().stream()
                .map(entry -> new CategorySummaryx(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        // Print the summary as JSON
      //  String summaryJson = objectMapper.writeValueAsString(summary);
        String jsons = encodeJson(summary);
        System.out.println(jsons);

        writeToFile(jsons, fileName);
    }

    // Write content to a file
    public static void writeToFile(String content, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        }
    }

    // Replace multiple spaces with a single space
    public static String replaceDoublePlacespace(String line) {
        return line.trim().replaceAll("\\s+", " ");
    }
}

