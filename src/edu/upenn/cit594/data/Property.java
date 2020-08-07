package edu.upenn.cit594.data;

public class Property {
    private int zipCode;
    private int livableArea;
    private int marketValue;
    private int buildingCode;

    public Property(int zipCode, int livableArea, int marketValue, int buildingCode){
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

    public int getLiveableArea() {
        return livableArea;
    }

    public void setLiveableArea(int livableArea) {
        this.livableArea = livableArea;
    }

    public int getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(int marketValue) {
        this.marketValue = marketValue;
    }

    public int getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(int buildingCode) {
        this.buildingCode = buildingCode;
    }
}
