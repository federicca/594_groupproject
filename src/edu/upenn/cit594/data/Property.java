package edu.upenn.cit594.data;

public class Property {
    private int zipCode;
    private double livableArea;
    private double marketValue;
    private String buildingCode;

    public Property(int zipCode, double livableArea, double marketValue, String buildingCode){
        this.livableArea = livableArea;
        this.zipCode = zipCode;
        this.marketValue = marketValue;
        this.buildingCode = buildingCode;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public double getLivableArea() {
        return livableArea;
    }

    public void setLivableArea(double livableArea) {
        this.livableArea = livableArea;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }
}
