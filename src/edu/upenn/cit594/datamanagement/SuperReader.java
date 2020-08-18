package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.logging.Logger;

public abstract class SuperReader {
    
    protected String filename;
    protected Logger logger;
    
    public SuperReader(String filename, Logger logger) {
        this.filename = filename;
        this.logger = logger;
    }

    /**
     * Call logOpenFile method from Logger class
     * @param logger
     */
    protected void logOpenFile() {
        if (logger == null) {
            return;
        }
        logger.logOpenFile(filename);
    }

}
