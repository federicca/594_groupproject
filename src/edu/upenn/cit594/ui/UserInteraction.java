package edu.upenn.cit594.ui;

import edu.upenn.cit594.data.Car;
import edu.upenn.cit594.data.ZipCode;
import edu.upenn.cit594.datamanagement.SuperViolationsReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.*;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class UserInteraction {
    
    protected Processor processor;
    protected Logger logger;
    
    /**
     * In N-tier architecture, UserInteraction has a dependency on Processor
     * @param processor used by this UserInteraction
     * @param logger to log user input
     */
    public UserInteraction(Processor processor, Logger logger) {
        this.processor = processor;
        this.logger = logger;
    }
    
    public void initUI (){
        String promptOpt = "\nEnter an option between 1-6, or 0 to Exit program\n" +
                "1. Total Population for All ZIP Codes\n" +
                "2. Total Fines Per Capita\n" +
                "3. Average Market Value\n" +
                "4. Average Total Livable Area\n" +
                "5. Total Residential Market Value Per Capita\n" +
                "6. Custom Feature\n" +
                "7. Get Car Parking Violations\n";
        try{
            Scanner sc = new Scanner(System.in);
            System.out.print("Welcome User!" + promptOpt);
            int userOpt;
            int zipCode;
            while ((userOpt = sc.nextInt()) != 0) {
                logger.logChoice(userOpt);
                //1. Total Population for All ZIP Codes
                if (userOpt == 1) {
                    
                    /**
                     * instead of instantiating TotalPop object, use
                     * processor's getTotalPop() method
                     */
                    
//                    TotalPopulation pop = new TotalPopulation();
                    int totalPop = processor.getTotalPop();
//                    int totalPop = pop.getTotalPop(zipCodeTreeMap);
                    System.out.println(totalPop);

                    // 2. Total Fines Per Capita
                } else if (userOpt == 2) {
                    TreeMap<Integer, ZipCode> zips = processor.getzipCodeTreeMap();
                    
                    // Pop first entry from treemap since it represents unknown zipcode
                    Entry<Integer, ZipCode> first = null;
                    if (zips.firstKey() == -1) {
                        first = zips.pollFirstEntry();
                    }
                    for (Integer zipInt : zips.keySet()) {
                        /**
                         * print out zipcode followed by space followed by totalFinesPC
                         * (4 decimal places, truncated)
                         */
                        try{
                            double totalFinesPC = processor.getTotalFinesPerCapita(zipInt);
                            if (totalFinesPC > 0) {
                                System.out.printf("%d %.4f\n", zipInt, totalFinesPC);
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("Divide by zero: Population = 0 for zipcode: " + zipInt);
                        }
                    }
                    // Add the first entry back to the TreeMap
                    if (first != null) {
                        zips.put(first.getKey(), first.getValue());
                    }

                    // 3. Average Market Value
                } else if (userOpt == 3) {
                    System.out.println("Enter a 5-digit ZipCode or 0 to go back to options");
                    if ((zipCode = sc.nextInt()) != 0) {
                        logger.logZipCode(zipCode);
                        int avgMarketValue = processor.getAverageMarketValue(zipCode);
                        System.out.println(avgMarketValue);
                    }
                    // 4. Average Total Livable Area
                } else if (userOpt == 4) {
                    System.out.println("Enter a 5-digit ZipCode or 0 to go back to options");
                    if ((zipCode = sc.nextInt()) != 0) {
                        logger.logZipCode(zipCode);
                        int avgTotalLivableArea = processor.getAverageLivableArea(zipCode);
                        System.out.println(avgTotalLivableArea);
                    }

                    // Total Residential Market Value Per Capita
                } else if (userOpt == 5) {
                    System.out.println("Enter a 5-digit ZipCode or 0 to go back to options");
                    if ((zipCode = sc.nextInt()) != 0) {
                        logger.logZipCode(zipCode);
                        int marketValuePC = processor.getTotalValuePC(zipCode);
                        System.out.println(marketValuePC);
                    }

                    // 6. Additional Feature
                } else if (userOpt == 6) {
                    TreeMap<Integer, ZipCode> zips = processor.getzipCodeTreeMap();
                    
                    // Pop first entry from treemap since it represents unknown zipcode
                    Entry<Integer, ZipCode> first = null;
                    if (zips.firstKey() == -1) {
                        first = zips.pollFirstEntry();
                    }
                    for (Integer zipInt : zips.keySet()) {
                        /**
                         * print out zipcode followed by space followed by total fines to RMV ratio
                         * (4 decimal places, truncated)
                         */
                        try{
                            double ratioFinesToRMV = processor.getTotalFinesToRMVRatio(zipInt);
                            if (ratioFinesToRMV > 0) {
                                System.out.printf("%d %.4f\n", zipInt, ratioFinesToRMV);
                            }
                        } catch (IllegalArgumentException e) {
                            // Do nothing
//                            System.out.println("Divide by zero: Avg mkt val = 0 for zipcode: " + zipInt);
                        }

                    }
                    // Add the first entry back to the TreeMap
                    if (first != null) {
                        zips.put(first.getKey(), first.getValue());
                    }
                } else if (userOpt == 7) {
                    System.out.println("Enter a 7-digit car ID to get Parking Violations (example: 1687997)");
                    int userInput;
                    if ((userInput = sc.nextInt()) != 0) {
                        HashMap<Integer, Car> cars = SuperViolationsReader.getCars();
                        processor.getViolations(userInput, cars);
                    }
                }else {
                    System.out.println("That's not a valid option. Please try again.");
                    return;
                }
                System.out.println(promptOpt);
            }

        } catch (InputMismatchException exception) {
            // Output unexpected InputMismatchExceptions.
            System.out.println("Invalid Input. Program will exit.");
        }

    }
}
