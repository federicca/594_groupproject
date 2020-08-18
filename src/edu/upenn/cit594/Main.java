package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertiesReader;
import edu.upenn.cit594.datamanagement.ViolationsReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.UserInteraction;

public class Main {
    public static void main(String[] args) {
        // handle invalid number of runtime args error      
        if(args.length != 5) {
            System.out.println("Error in runtime arguments.");
            return;
        }
        
        // store args into local vars
        String violationsFormat = args[0];
        String violationsFile = args[1];
        String propertyValuesFile = args[2];
        String populationFile = args[3];
        String logfile = args[4];
        
        // handle invalid violations format
        if (!isValidViolationsFormat(violationsFormat)) {
            System.out.println("Error: File format not supported in this program.");
        }
        
        // Create logger and log program start
        Logger logger = Logger.getInstance();
        logger.filename = logfile;
        logger.createWriter();
        logger.logProgStart(args);

        // Create Reader objects
        ViolationsReader violationsReader = Processor.createViolationsReader(violationsFormat, violationsFile, logger);
        PropertiesReader propertiesReader = new PropertiesReader(propertyValuesFile, logger);
        PopulationReader populationReader = new PopulationReader(populationFile, logger);
        
        // Create Processor object and initialize data
        System.out.println("Please wait while we process the data...");
        Processor processor = new Processor(violationsReader, propertiesReader, populationReader);

        // Create UI object and run UI
        UserInteraction ui = new UserInteraction(processor, logger);
        ui.initUI();

    }
    
    /**
     * Private helper function to determine whether violations file format is legal
     * @param format args[1]6
     * @return
     */
    private static boolean isValidViolationsFormat(String format) {
        if (format.equals("csv") || format.equals("json")) {
            return true;
        } else {
            return false;
        }
    }
}
