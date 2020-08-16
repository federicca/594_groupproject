package edu.upenn.cit594.processor;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.data.ZipCode;
import edu.upenn.cit594.datamanagement.CsvViolationsReader;
import edu.upenn.cit594.datamanagement.JsonViolationsReader;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertiesReader;
import edu.upenn.cit594.datamanagement.ViolationsReader;

public class Processor {
    
    protected PopulationReader populationReader;
    protected PropertiesReader propertiesReader;
    protected ViolationsReader violationsReader;
    protected String violationsFilename;
    protected TreeMap<Integer, ZipCode> zipCodeTreeMap;
    
    /**
     * Creates all required reader objects in constructor
     * @param format of Parking Violations file (csv or json)
     */
    public Processor(String format, String violationsFilename, String propertiesFilename,
            String populationFilename) {
        
        // instantiate readers
        populationReader = new PopulationReader();
        propertiesReader = new PropertiesReader();
        violationsReader = createViolationsReader(format);
        
        // initialize data
        initializeData(violationsFilename, propertiesFilename, populationFilename);
    }
    
    public TreeMap<Integer, ZipCode> getzipCodeTreeMap() {
        return zipCodeTreeMap;
    }
    
    /**
     * Instantiates the TreeMap and populates ArrayList fields of ZipCode objects
     * for data analysis
     * @param violationsFilename
     * @param propertiesFilename
     * @param populationFilename
     */
    private void initializeData(String violationsFilename, String propertiesFilename,
            String populationFilename) {
        // Create TreeMap
        try {
            zipCodeTreeMap = populationReader.processPop(populationFilename);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // Populate ArrayList fields of zipCode objects step 1: Properties
        try {
            propertiesReader.processProperties(propertiesFilename, zipCodeTreeMap);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // Populate ArrayList fields of zipCode objects step 2: Parking Violations
        violationsReader.readViolationsIntoZipCode(violationsFilename, zipCodeTreeMap);
        
    }
    
    /**
     * Creates an object that implements the ViolationsReader interface,
     * the type of which depends on format
     * @param format passed by args[0] in main
     * @return
     */
    private ViolationsReader createViolationsReader(String format) {
        if (format.equals("csv")) {
            return new CsvViolationsReader();
        } else if (format.equals("json")) {
            return new JsonViolationsReader();
        } else {
            return null;
        }
    }
    
    /**
     * Q1
     * Calculates the total population of all the combined zipcodes in the dataset
     * @return total population
     */
    public int getTotalPop () {
        int totalPop = 0;
        for (Map.Entry<Integer, ZipCode> entry : zipCodeTreeMap.entrySet()) {
            totalPop += entry.getValue().getPopulation();
        }
        return totalPop;
    }
    
    /**
     * Q2
     * Calculates total fines per capita by dividing total sum of fines in given zipcode
     * by total population
     * @param zipCode
     * @return fines per capita
     */
    public double getTotalFinesPerCapita(int zipCode) {
        ZipCode zc = zipCodeTreeMap.get(zipCode);
        ArrayList<ParkingViolation> violations = zc.getParkingViolations();
        
        // Calculate total fines
        int totalFines = 0;
        for (ParkingViolation violation : violations) {
            totalFines += violation.getFineDue();
        }
        
        // Get population
        int population = zc.getPopulation();
        
        // Return total fines / population
        return (double) totalFines / population;
    }
    
    /**
     * Q3
     * Calculates the average market value of homes in a given zipcode
     * *MUST IMPLEMENT STRATEGY DESIGN PATTERN*
     * @param zipCode input by user
     * @return average market value
     */
    public int getAverageMarketValue (int zipCode) {
        double sumMarketValue = 0;
        int averageMarketValue = 0;
        if (zipCodeTreeMap.containsKey(zipCode)) {
            ZipCode code = zipCodeTreeMap.get(zipCode);
            for (Property property : code.getProperties()) {
                sumMarketValue += property.getMarketValue();
            }
            averageMarketValue = (int) (sumMarketValue / code.getProperties().size());

        } else {
            System.out.println("No data available or invalid ZipCode");
        }
        return averageMarketValue;
    }
    
    /**
     * Q4
     * Calculates the average total liveable area of homes in a given zipcode
     * *MUST IMPLEMENT STRATEGY DESIGN PATTERN* 
     * @param zipCode
     * @return average liveable area
     */
    public int getAverageLiveableArea(int zipCode) {
        double sumLivableArea = 0;
        int averageLivableArea = 0;
        if(zipCodeTreeMap.containsKey(zipCode)) {
            ZipCode code = zipCodeTreeMap.get(zipCode);
            for (Property property : code.getProperties()) {
                sumLivableArea += property.getLivableArea();
            }
            averageLivableArea = (int) (sumLivableArea / code.getProperties().size());
        }else{
            System.out.println("No data available or invalid ZipCode");
        }
        return averageLivableArea;
    }
    
    /**
     * Q5
     * Calculates the total residential market value per capita in a given zipcode
     * @param zipCode
     * @return total RMV per capita
     */
    public int getTotalValuePC(int zipCode){
        double sumMarketValue = 0;
        int marketValuePC = 0;
        if(!zipCodeTreeMap.containsKey(zipCode)){
            return 0;
        }
        ZipCode code = zipCodeTreeMap.get(zipCode);
        for (Property property : code.getProperties()){
            sumMarketValue += property.getMarketValue();
        }
        if(sumMarketValue == 0 || code.getPopulation() == 0){
            return 0;
        }
        marketValuePC = (int) (sumMarketValue / code.getPopulation());
        return marketValuePC;
    }
    
    

}
