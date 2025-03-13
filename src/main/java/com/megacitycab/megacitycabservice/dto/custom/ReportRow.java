package com.megacitycab.megacitycabservice.dto.custom;
public class ReportRow {
    private int id;
    private String description;
    private double totalRevenue;
    private double discounts;
    private double taxes;
    private double netRevenue;

    public ReportRow(int i, String totalRevenue, double totalRevenue1, double totalDiscounts, double totalTaxes, double v) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getDiscounts() {
        return discounts;
    }

    public void setDiscounts(double discounts) {
        this.discounts = discounts;
    }

    public double getTaxes() {
        return taxes;
    }

    public void setTaxes(double taxes) {
        this.taxes = taxes;
    }

    public double getNetRevenue() {
        return netRevenue;
    }

    public void setNetRevenue(double netRevenue) {
        this.netRevenue = netRevenue;
    }

    @Override
    public String toString() {
        return "ReportRow{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", totalRevenue=" + totalRevenue +
                ", discounts=" + discounts +
                ", taxes=" + taxes +
                ", netRevenue=" + netRevenue +
                '}';
    }
}