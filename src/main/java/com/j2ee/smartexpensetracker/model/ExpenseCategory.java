package com.j2ee.smartexpensetracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "categories")
public class ExpenseCategory {

    @Id
    private String id;
    private String name;
    private List<String> subCategories; // List of subcategories

    public ExpenseCategory() {}

    public ExpenseCategory(String id, String name, List<String> subCategories) {
        this.id = id;
        this.name = name;
        this.subCategories = subCategories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<String> subCategories) {
        this.subCategories = subCategories;
    }
}
