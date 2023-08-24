package com.example.budgettracker.profiles;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Profile {
    @SerializedName("username")
    private String username;
    @SerializedName("expenses")
    private List<Expense> expenses;

    @SerializedName("budget")
    private int budget;

    @SerializedName("savings")
    private int savings;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
    public void addExpense(Expense expense){
        this.expenses.add(expense);
    }

    public void setBudget(int budget){
        this.budget = budget;
    }

    public void setSavings(int savings){
        this.savings = savings;
    }

    public int getBudget(){
        return budget;
    }
    public int getSavings(){
        return savings;
    }

}

