package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.logging.Logger;

public abstract class SuperReader {
    
    protected String filename;
    
    public SuperReader(String filename) {
        this.filename = filename;
    }

    /**
     * Call logOpenFile method from Logger class
     * @param logger
     */
    protected void logOpenFile(Logger logger) {
        if (logger == null) {
            return;
        }
        logger.logOpenFile(filename);
    }

}
