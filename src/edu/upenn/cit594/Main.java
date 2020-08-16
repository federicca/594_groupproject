package edu.upenn.cit594;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.UserInteraction;

import java.io.IOException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) {
        if(args.length != 5) {
            System.out.println("Error in runtime arguments.");
            return;
        }
        String violationsFormat = args[0];
        String violationsFile = args[1];
        String propertyValuesFile = args[2];
        String populationFile = args[3];
        String logfile = args[4];
        
        // Create logger and log program start
        Logger logger = Logger.getInstance();
        logger.filename = logfile;
        logger.createWriter();
        logger.logProgStart(args);

        System.out.println("Please wait while we process the data...");
        
        // Create Processor object and initialize data
        Processor processor = new Processor(violationsFormat, violationsFile, propertyValuesFile,
                populationFile, logger);

        UserInteraction ui = new UserInteraction(processor, logger);
        ui.initUI();

    }
}
