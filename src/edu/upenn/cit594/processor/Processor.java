package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Car;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.data.ZipCode;
import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.logging.Logger;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class Processor {

    protected PopulationReader populationReader;
    protected PropertiesReader propertiesReader;
    protected ViolationsReader violationsReader;
    protected String violationsFilename;
    protected Logger logger;
    protected TreeMap<Integer, ZipCode> zipCodeTreeMap;

    /**
     * Creates all required reader objects in constructor
     * @param format of Parking Violations file (csv or json)
     */
    public Processor(String format, String violationsFilename, String propertiesFilename,
            String populationFilename, Logger logger) {

        // set instance vars
        this.violationsFilename = violationsFilename;
        this.logger = logger;

        // instantiate readers
        populationReader = new PopulationReader(populationFilename);
        propertiesReader = new PropertiesReader(propertiesFilename);
        violationsReader = createViolationsReader(format);

        // initialize data
        initializeData();
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
    private void initializeData() {
        // Create TreeMap and read in population
        try {
            zipCodeTreeMap = populationReader.processPop(logger);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Populate ArrayList fields of zipCode objects step 1: Properties
        try {
            propertiesReader.processProperties(zipCodeTreeMap, logger);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Populate ArrayList fields of zipCode objects step 2: Parking Violations
        violationsReader.getAllViolations(zipCodeTreeMap, logger);

    }

    /**
     * Creates an object that implements the ViolationsReader interface,
     * the type of which depends on format
     * @param format passed by args[0] in main
     * @return
     */
    private ViolationsReader createViolationsReader(String format) {
        if (format.equals("csv")) {
            return new CsvViolationsReader(violationsFilename);
        } else if (format.equals("json")) {
            return new JsonViolationsReader(violationsFilename);
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
     * Note: must ignore non-PA license plates
     * @param zipCode
     * @return fines per capita
     */
    public double getTotalFinesPerCapita(int zipCode) throws IllegalArgumentException {

        ZipCode zc = zipCodeTreeMap.get(zipCode);
        ArrayList<ParkingViolation> violations = zc.getParkingViolations();

        // Calculate total fines using Strategy method
        int totalFines = getTotalFines(violations, new PAComparator());

        // Get population
        int population = zc.getPopulation();
        if (population == 0) {
            throw new IllegalArgumentException();
        }

        // Return total fines / population
        return (double) totalFines / population;
    }

    /**
     * Private helper method to get total fines, based on strategy
     * @param violations
     * @param comparator
     * @return
     */
    private int getTotalFines(ArrayList<ParkingViolation> violations, StateComparator comparator) {
        int totalFines = 0;
        for (ParkingViolation violation : violations) {
            String carState = violation.getCarState();
            if(comparator.equals(carState)) {
                totalFines += violation.getFineDue();
            }
        }
        return totalFines;
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

    /**
     * Q6: Additional Feature
     * Calculates ratio of average fine to residential market value for a given zipcode
     * @param zipCode
     * @return ratio
     */
    public double getTotalFinesToRMVRatio(int zipCode) throws IllegalArgumentException {
        ZipCode zc = zipCodeTreeMap.get(zipCode);
        ArrayList<ParkingViolation> violations = zc.getParkingViolations();

        // Calculate total fines using Strategy method
        int totalFines = getTotalFines(violations, new TrueComparator());

        // Calculate average residential market value using method from Q3
        int avgMktVal = getAverageMarketValue(zipCode);
        if (avgMktVal == 0) {
            throw new IllegalArgumentException();
        }

        return (double) totalFines / avgMktVal;
    }


    public void getViolations(int input, HashMap<Integer, Car> cars){
        int i = 1;
        int totalDue = 0;
        if(cars.containsKey(input)){
            if(cars.get(input).getViolations().isEmpty()){
                System.out.println("Car has no Parking Violations");
            }else {
                HashSet<ParkingViolation> violations = cars.get(input).getViolations();
                for (ParkingViolation violation : violations) {
                    System.out.println(i + ". " + "Timestamp: " + violation.getTimeStamp().toString() + " ViolationID: "
                            + violation.getViolationID() + " Description: " + violation.getDescription()
                            + " Fine due: $" + violation.getFineDue());
                    i++;
                    totalDue += violation.getFineDue();
                }
                System.out.println("\nTotal due for car ID " + input + ": $" + totalDue);
            }
        }else {
            System.out.println("Invalid Car ID");
        }
    }

}
