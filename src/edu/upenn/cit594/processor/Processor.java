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

    protected ViolationsReader violationsReader;
    protected PropertiesReader propertiesReader;
    protected PopulationReader populationReader;
    protected Logger logger;
    protected TreeMap<Integer, ZipCode> zipCodeTreeMap;

    /**
     * Creates all required reader objects in constructor
     * @param vr ViolationsReader
     * @param prr PropertiesReader
     * @param por PopulationReaders
     */
    public Processor(ViolationsReader vr, PropertiesReader prr, PopulationReader por) {
        
        // set instance vars
        violationsReader = vr;
        propertiesReader = prr;
        populationReader = por;

        // initialize data
        initializeData();
    }

    public TreeMap<Integer, ZipCode> getzipCodeTreeMap() {
        return zipCodeTreeMap;
    }

    /**
     * Instantiates the TreeMap and populates ArrayList fields of ZipCode objects
     * for data analysis
     */
    private void initializeData() {
        // Create TreeMap and read in population
        try {
            zipCodeTreeMap = populationReader.processPop();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Populate ArrayList fields of zipCode objects step 1: Properties
        try {
            propertiesReader.processProperties(zipCodeTreeMap);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Populate ArrayList fields of zipCode objects step 2: Parking Violations
        violationsReader.getAllViolations(zipCodeTreeMap);

    }

    /**
     * Creates an object that implements the ViolationsReader interface,
     * the type of which depends on format
     * @param format passed by args[0] in main
     * @return
     */
    public static ViolationsReader createViolationsReader(String format, String violationsFilename, Logger logger) {
        if (format.equals("csv")) {
            return new CsvViolationsReader(violationsFilename, logger);
        } else if (format.equals("json")) {
            return new JsonViolationsReader(violationsFilename, logger);
        } else {
            return null;
        }
    }

    /**
     * Q1
     * Calculates the total population of all the combined zipcodes in the dataset
     * @return total population
     */
    private int memPop = 0; //memoization por getTotalPop
    public int getTotalPop () {
        if (memPop == 0) {
            int totalPop = 0;
            for (Map.Entry<Integer, ZipCode> entry : zipCodeTreeMap.entrySet()) {
                totalPop += entry.getValue().getPopulation();
            }
            memPop = totalPop;
            return totalPop;
        }else{
            return memPop;
        }
    }

    /**
     * Q2
     * Calculates total fines per capita by dividing total sum of fines in given zipcode
     * by total population
     * Note: must ignore non-PA license plates
     * @param zipCode
     * @return fines per capita
     */
    private HashMap<Integer, Double> memFinesPC = new HashMap<>();
    public double getTotalFinesPerCapita(int zipCode) throws IllegalArgumentException {
        if (memFinesPC.containsKey(zipCode)) {
            return memFinesPC.get(zipCode);
        } else {
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

    private HashMap<Integer, Integer> memValue = new HashMap<>(); //Memoization for Average Market value
    public int getAverageMarketValue (int zipCode) {
        if (memValue.containsKey(zipCode)){
            return memValue.get(zipCode);
        }else {
            double sumMarketValue = 0;
            int averageMarketValue = 0;
            if (zipCodeTreeMap.containsKey(zipCode)) {
                ZipCode code = zipCodeTreeMap.get(zipCode);
                for (Property property : code.getProperties()) {
                    sumMarketValue += property.getMarketValue();
                }
                GetAverage average = new GetAverage(); // Implement Strategy
                averageMarketValue = average.getAverage(sumMarketValue, code.getProperties().size());

            } else {
                System.out.println("No data available or invalid ZipCode");
            }
            memValue.put(zipCode, averageMarketValue);
            return averageMarketValue;
        }
    }

    /**
     * Q4
     * Calculates the average total liveable area of homes in a given zipcode
     * *MUST IMPLEMENT STRATEGY DESIGN PATTERN*
     * @param zipCode
     * @return average liveable area
     */
    private HashMap<Integer, Integer> memArea = new HashMap<>(); //Memoization
    public int getAverageLivableArea(int zipCode) {
        if (memArea.containsKey(zipCode)){
            return memArea.get(zipCode);
        }else {
            double sumLivableArea = 0;
            int averageLivableArea = 0;
            if (zipCodeTreeMap.containsKey(zipCode)) {
                ZipCode code = zipCodeTreeMap.get(zipCode);
                for (Property property : code.getProperties()) {
                    sumLivableArea += property.getLivableArea();
                }
                GetAverage average = new GetAverage(); // Implement Strategy
                averageLivableArea = average.getAverage(sumLivableArea, code.getProperties().size());
            } else {
                System.out.println("No data available or invalid ZipCode");
            }
            memArea.put(zipCode, averageLivableArea);
            return averageLivableArea;
        }
    }

    /**
     * Q5
     * Calculates the total residential market value per capita in a given zipcode
     * @param zipCode
     * @return total RMV per capita
     */
    private HashMap<Integer, Integer> memValuePC = new HashMap<>(); //Memoization
    public int getTotalValuePC(int zipCode){
        if (memValuePC.containsKey(zipCode)){
            return memValuePC.get(zipCode);
        }else {
            double sumMarketValue = 0;
            int marketValuePC = 0;
            if (!zipCodeTreeMap.containsKey(zipCode)) {
                return 0;
            }
            ZipCode code = zipCodeTreeMap.get(zipCode);
            for (Property property : code.getProperties()) {
                sumMarketValue += property.getMarketValue();
            }
            if (sumMarketValue == 0 || code.getPopulation() == 0) {
                return 0;
            }
            marketValuePC = (int) (sumMarketValue / code.getPopulation());
            memValuePC.put(zipCode, marketValuePC);
            return marketValuePC;
        }
    }

    /**
     * Q6: Additional Feature
     * Calculates ratio of average fine to residential market value for a given zipcode
     * @param zipCode
     * @return ratio
     */
    private HashMap<Integer, Double> memFinesToRMVRatio = new HashMap<>();
    public double getTotalFinesToRMVRatio(int zipCode) throws IllegalArgumentException {
        if (memFinesToRMVRatio.containsKey(zipCode)) {
            return memFinesToRMVRatio.get(zipCode);
        }
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

    private HashMap<Integer, ArrayList<String>> memCarViolations = new HashMap<>(); //Memoization
    public void getViolations(int input, HashMap<Integer, Car> cars) {
        if (memCarViolations.containsKey(input)) {
            for (String string : memCarViolations.get(input)) {
                System.out.println(string);
            }
        } else {
            int i = 1;
            int totalDue = 0;
            ArrayList<String> outputs = new ArrayList<>();
            if (cars.containsKey(input)) {
                if (cars.get(input).getViolations().isEmpty()) {
                    System.out.println("Car has no Parking Violations");
                } else {
                    HashSet<ParkingViolation> violations = cars.get(input).getViolations();
                    for (ParkingViolation violation : violations) {
                        StringBuilder str = new StringBuilder();
                        str.append(i);
                        str.append(". TimeStamp: ");
                        str.append(violation.getTimeStamp().toString());
                        str.append(" Violation ID: ");
                        str.append(violation.getViolationID());
                        str.append(" Description: ");
                        str.append(violation.getDescription());
                        str.append(" Fine due: $");
                        str.append(violation.getFineDue());

                        i++;
                        totalDue += violation.getFineDue();
                        System.out.println(str);
                        outputs.add(str.toString());
                    }
                    StringBuilder total = new StringBuilder();
                    total.append("\nTotal due for car ID ");
                    total.append(input);
                    total.append(": $");
                    total.append(totalDue);

                    System.out.println(total);
                    outputs.add(total.toString());
                    memCarViolations.put(input, outputs);
                }
            } else {
                System.out.println("Invalid Car ID");
            }
        }
    }

}
