package com.megacitycab.megacitycabservice.dto.custom;

public class RevenueReportDTO {
    private String period;
    private double totalRevenue;
    private double totalDiscounts;
    private double totalTaxes;
    private double netRevenue;

    public RevenueReportDTO(String period, double totalRevenue, double totalDiscounts, double totalTaxes, double netRevenue) {
        this.period = period;
        this.totalRevenue = totalRevenue;
        this.totalDiscounts = totalDiscounts;
        this.totalTaxes = totalTaxes;
        this.netRevenue = netRevenue;
    }


    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getTotalDiscounts() {
        return totalDiscounts;
    }

    public void setTotalDiscounts(double totalDiscounts) {
        this.totalDiscounts = totalDiscounts;
    }

    public double getTotalTaxes() {
        return totalTaxes;
    }

    public void setTotalTaxes(double totalTaxes) {
        this.totalTaxes = totalTaxes;
    }

    public double getNetRevenue() {
        return netRevenue;
    }

    public void setNetRevenue(double netRevenue) {
        this.netRevenue = netRevenue;
    }

    @Override
    public String toString() {
        return "RevenueReportDTO{" +
                "period='" + period + '\'' +
                ", totalRevenue=" + totalRevenue +
                ", totalDiscounts=" + totalDiscounts +
                ", totalTaxes=" + totalTaxes +
                ", netRevenue=" + netRevenue +
                '}';
    }
}