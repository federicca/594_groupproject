package edu.upenn.cit594.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.upenn.cit594.datamanagement.CsvViolationsReader;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertiesReader;
import edu.upenn.cit594.datamanagement.ViolationsReader;

class ProcessorTest {
    
    Processor p;
    String violationsFilename = "src\\sample_files\\parking_test.csv";
    String propertiesFilename = "src\\sample_files\\properties_small.csv";
    String populationFilename = "src\\sample_files\\population.txt";
    
    ViolationsReader vr;
    PropertiesReader prr;
    PopulationReader por;
    
    @BeforeEach
    void setUp() {
        vr = new CsvViolationsReader(violationsFilename, null);
        prr = new PropertiesReader(propertiesFilename, null);
        por = new PopulationReader(populationFilename, null);
        p = new Processor(vr, prr, por);
    }

    @Test
    void testGetTotalPop() {
        fail("Not yet implemented");
    }

    @Test
    void testGetTotalFinesPerCapita() {
        int zip0 = 19103;
        double finesPC_0 = p.getTotalFinesPerCapita(zip0);
        assertEquals("Incorrect fines per capita for ZC: " + zip0, 329.0/21908.0, finesPC_0, 0.0000001);
    }

    @Test
    void testGetAverageMarketValue() {
        fail("Not yet implemented");
    }

    @Test
    void testGetAverageLiveableArea() {
        fail("Not yet implemented");
    }

    @Test
    void testGetTotalValuePC() {
        fail("Not yet implemented");
    }

}
