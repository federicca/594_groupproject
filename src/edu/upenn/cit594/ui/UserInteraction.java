package edu.upenn.cit594.ui;

import java.util.InputMismatchException;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import edu.upenn.cit594.data.ZipCode;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.AverageLiveableArea;
import edu.upenn.cit594.processor.AverageMarketValue;
import edu.upenn.cit594.processor.Context;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.processor.TotalValuePC;

public class UserInteraction {
    
    protected Processor processor;
    protected Logger logger;
    
    /**
     * In N-tier architecture, UserInteraction has a dependency on Processor
     * @param processor used by this UserInteraction
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
                "6. Custom Feature\n";
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
                    
                    // 
                    Entry<Integer, ZipCode> first = null;
                    if (zips.firstKey() == -1) {
                        first = zips.pollFirstEntry();
                    }
                    for (Integer zipInt : zips.keySet()) {
                        /**
                         * print out zipcode followed by space followed by totalFinesPC
                         * (4 decimal places, truncated)
                         */
                        double totalFinesPC = processor.getTotalFinesPerCapita(zipInt);
                        System.out.printf("%d %.4f\n", zipInt, totalFinesPC);
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
                        
                        Context context = new Context(new AverageMarketValue());
                        int avgMarketValue = processor.getAverageMarketValue(zipCode);
//                        System.out.println(context.executeStrategy(zipCode, zipCodeTreeMap));
                        System.out.println(avgMarketValue);
                    }
                    // 4. Average Total Livable Area
                } else if (userOpt == 4) {
                    System.out.println("Enter a 5-digit ZipCode or 0 to go back to options");
                    if ((zipCode = sc.nextInt()) != 0) {
                        logger.logZipCode(zipCode);

                        Context context = new Context(new AverageLiveableArea());
                        int avgTotalLiveableArea = processor.getAverageMarketValue(zipCode);
//                        System.out.println(context.executeStrategy(zipCode, zipCodeTreeMap));
                        System.out.println(avgTotalLiveableArea);
                    }

                    // Total Residential Market Value Per Capita
                } else if (userOpt == 5) {
                    System.out.println("Enter a 5-digit ZipCode or 0 to go back to options");
                    if ((zipCode = sc.nextInt()) != 0) {
                        logger.logZipCode(zipCode);

                        TotalValuePC valuePC = new TotalValuePC();
//                        System.out.println(valuePC.getTotalValuePC(zipCode, zipCodeTreeMap));
                        int marketValuePC = processor.getTotalValuePC(zipCode);
                        System.out.println(marketValuePC);
                    }

                    // 6. Additional Feature
                } else if (userOpt == 6) {

                } else {
                    System.out.println("That's not a valid option. Please try again.");
                }
                System.out.println(promptOpt);
            }

        } catch (InputMismatchException exception) {
            // Output unexpected InputMismatchExceptions.
            System.out.println("Invalid Input. Program will exit.");
        }

    }
}
