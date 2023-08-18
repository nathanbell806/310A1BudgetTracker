package com.example.budgettracker.profiles;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Profile {
    @SerializedName("username")
    private String username;
    @SerializedName("color")
    private String color;
    @SerializedName("expenses")
    private List<Expense> expenses;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}

