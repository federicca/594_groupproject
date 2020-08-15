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
    
    public void log(String msg) {
        out.println(msg);
        out.flush();
    }
}
