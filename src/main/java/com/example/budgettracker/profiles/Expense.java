package com.example.budgettracker.profiles;

public class Expense {
    private String name;
    private double cost;

    /**
     * This test class is for creating expense objects that can go inside the expenses field of a profile to save
     * both they name of the expense and the cost of the expense
     */
    public Expense(String expense, double cost) {
        this.name = expense;
        this.cost = cost;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }
}
