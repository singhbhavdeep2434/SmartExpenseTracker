package com.j2ee.smartexpensetracker.model;

import com.j2ee.smartexpensetracker.enums.Category;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public class Expense {

    @Id
    private String id;

    private String description;
    private Category category;
    private double amount;
    private LocalDate date;

    public Expense(String id, String description, Category category, double amount, LocalDate date) {
        this.id = id;
        this.description = description;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public Expense(){}



    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
