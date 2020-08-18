package edu.upenn.cit594.processor;

public class GetAverage implements AverageStrategy {

    @Override
    public int getAverage(double total, double denominator){
        return (int) (total/denominator);
    }
}
