package ua.edu.ucu.tempseries;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    private double[] temperatureSeries;
    private int n;
    private int capacity;

    public TemperatureSeriesAnalysis() {
        this.temperatureSeries = new double[2];
        this.capacity = 2;
        this.n = 0;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        this.capacity = temperatureSeries.length;
        this.n = this.capacity;
        this.temperatureSeries = new double[temperatureSeries.length];
        final int CONSTANT = -273;

        for (int i = 0; i < capacity; i++) {
            if (temperatureSeries[i] < CONSTANT) {
                throw new InputMismatchException();
            } else {
                this.temperatureSeries[i] = temperatureSeries[i];
            }
        }
    }

    public double average() {
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        double sum = this.getSum();
        final double CONSTANT = 100.0;

        return  Math.round((sum / capacity) * CONSTANT) / CONSTANT;
    }

    public double deviation() {
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        double mean = this.average();
        double squareError = 0;
        final double CONSTANT = 100.0;

        for (double temp : temperatureSeries) {
            squareError += (temp - mean) * (temp - mean);
        }

        return Math.round((Math.sqrt(squareError / n)) * CONSTANT) / CONSTANT;
    }

    public double min() {
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        double minimal = Double.POSITIVE_INFINITY;

        for (double temp : temperatureSeries) {
            if (temp < minimal) {
                minimal = temp;
            }
        }

        return minimal;
    }

    public double max() {
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        double maximal = Double.NEGATIVE_INFINITY;

        for (double temp : temperatureSeries) {
            if (temp > maximal) {
                maximal = temp;
            }
        }

        return maximal;
    }

    public double findTempClosestToZero() {
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        double closest = Double.POSITIVE_INFINITY;

        for (double temp : temperatureSeries) {
            if (Math.abs(temp) < closest) {
                closest = temp;
            } else if (Math.abs(temp) == Math.abs(closest)) {
                closest = Math.max(temp, closest);
            }
        }

        return closest;
    }

    public double findTempClosestToValue(double tempValue) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        double closest = Double.POSITIVE_INFINITY;
        double distance;

        for (double temp : temperatureSeries) {
            distance = Math.abs(closest - tempValue);
            if (Math.abs(temp - tempValue) < distance) {
                closest = temp;
            } else if (Math.abs(temp - tempValue) == distance) {
                closest = Math.max(temp, closest);
            }
        }

        return closest;
    }

    public double[] findTempsLessThen(double tempValue) {
        if (capacity < 1) {
            throw new IllegalArgumentException();
        }

        int size = 0;

        for (int i = 0; i < capacity; i++) {
            if (temperatureSeries[i] < tempValue) {
                size++;
            }
        }

        double [] tempLess = new double[size];
        int counter = 0;

        for (int i = 0; i < capacity; i++) {
            if (temperatureSeries[i] < tempValue) {
                tempLess[counter] = temperatureSeries[i];
                counter++;
            }
        }

        return tempLess;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        if (capacity < 1) {
            throw new IllegalArgumentException();
        }

        int size = 0;

        for (int i = 0; i < capacity; i++) {
            if (temperatureSeries[i] >= tempValue) {
                size++;
            }
        }

        double [] tempGreater = new double[size];
        int counter = 0;

        for (int i = 0; i < capacity; i++) {
            if (temperatureSeries[i] >= tempValue) {
                tempGreater[counter] = temperatureSeries[i];
                counter++;
            }
        }

        return tempGreater;
    }

    public TempSummaryStatistics summaryStatistics() {
        if (capacity < 1) {
            throw new IllegalArgumentException();
        }

        return new TempSummaryStatistics(this.average(),
                                         this.deviation(),
                                         this.min(),
                                         this.max());
    }

    public int addTemps(double... temps) {

        int sizeToAdd = temps.length;

        for (int i = 0; i < sizeToAdd; i++) {
            if (capacity == n) {
                this.increaseSize();
            }

            temperatureSeries[n] = temps[i];
            n++;
        }

        return (int) this.getSum();
    }

    public int getLength() {
        return n;
    }

    private double getSum() {
        double sum = 0;

        for (double temp : temperatureSeries) {
            sum += temp;
        }

        return sum;
    }

    private void increaseSize() {
        double[] newArr = new double[capacity * 2];
        capacity *= 2;

        for (int i = 0; i < n; i++) {
            newArr[i] = temperatureSeries[i];
        }

        temperatureSeries = newArr;
    }
}
