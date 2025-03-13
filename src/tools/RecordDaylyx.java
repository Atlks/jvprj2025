package tools;

import jakarta.json.bind.annotation.JsonbProperty;
import java.io.Serializable;

public class RecordDaylyx implements Serializable {
    @JsonbProperty("date")
    private String date;

    @JsonbProperty("amount")
    private double amount;

    @JsonbProperty("category")
    private String category;

    @JsonbProperty("description")
    private String description;

    public RecordDaylyx() {
    }

    public RecordDaylyx(String date, double amount, String category, String description) {
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

