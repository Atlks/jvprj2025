package tools;

import jakarta.json.bind.annotation.JsonbProperty;
import java.io.Serializable;

public class CategorySummaryx implements Serializable {
    @JsonbProperty("category")
    private String category;

    @JsonbProperty("totalAmount")
    private double totalAmount;

    public CategorySummaryx() {
    }

    public CategorySummaryx(String category, double totalAmount) {
        this.category = category;
        this.totalAmount = totalAmount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
