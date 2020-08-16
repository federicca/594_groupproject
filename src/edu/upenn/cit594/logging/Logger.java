package edu.upenn.cit594.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    
    private PrintWriter out;
    public static String filename;
    
    private Logger() {}
    
    private static Logger instance = new Logger();
    
    public static Logger getInstance() {
        return instance;
    }
    
    public void createWriter() {
        try {
            FileWriter fw = new FileWriter(filename, true);
            out = new PrintWriter(fw);
        } catch (IOException e) {
            System.out.println("Unable to create file writer");
            e.printStackTrace();
        }
    }
    
    /**
     * Once program starts, writes the current time (time-as-milliseconds) followed
     * by each of the runtime args
     * @param args from main
     */
    public void logProgStart(String[] args) {
        logCurrentTime();
        
        // write runtime args
        out.print("Runtime args: ");
        for (int i = 0; i < 5; i++) {
            out.print(args[i] + " ");
        }
        
        newLineAndFlush();
    }
    
    /**
     * Whenever an input file is opened for reading, write the current time and
     * the name of the file
     * @param filename
     */
    public void logOpenFile(String filename) {
        logCurrentTime();
        
        // write filename
        out.print("File opened: " + filename);
        
        newLineAndFlush();
    }
    
    /**
     * When the user makes a choice from the prompt, write the current time and
     * the user's selection
     * @param choice
     */
    public void logChoice(int choice) {
        logCurrentTime();
        
        // write user's selection
        out.print("User selected: " + choice);
        
        newLineAndFlush();
    }
    
    public void logZipCode(int zipCode) {
        logCurrentTime();
        
        // write zipcode
        out.print("User input: " + zipCode);
        
        newLineAndFlush();
    }
    
    /**
     * Helper function to log current time (time-as-milliseconds) from
     * System.currentTimeMillis()
     */
    private void logCurrentTime() {
        out.print("Current time: " + System.currentTimeMillis() + " ");
    }
    
    /**
     * Brings log file to new line and flushes current output
     */
    private void newLineAndFlush() {
        out.println();
        out.flush();
    }
    
    
}
