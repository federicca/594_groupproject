package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.ZipCode;

import java.util.TreeMap;

public class Context {
    private Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public int executeStrategy(int zipCode, TreeMap<Integer, ZipCode> zipCodeTreeMap){
        return strategy.getAverage(zipCode, zipCodeTreeMap);
    }
}
