package ua.edu.ucu.tempseries;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class TemperatureSeriesAnalysisTest {
    private TemperatureSeriesAnalysis temperatureSeriesAnalysis;

    @Before
    public void setUp() {
        double[] temperatureSeries = {-10.0, 10.0, -1, 1, 20.0, 30.0, 50.0};
        this.temperatureSeriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
    }

    @Test
    public void testAverageWithOneElementArray() {
        // setup input data and expected result
        double[] temperatureSeries = {-1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = -1.0;

        // call tested method
        double actualResult = seriesAnalysis.average();

        // compare expected result with actual result
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAverageWithEmptyArray() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.average();
    }

    @Test
    public void testAverage() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 1.0;

        double actualResult = seriesAnalysis.average();
        
        assertEquals(expResult, actualResult, 0.00001);        
    }

    @Test
    public void testDeviation() {
        assertEquals(19.17, this.temperatureSeriesAnalysis.deviation(), 0.00001);
    }

    @Test
    public void testMin() {
        assertEquals(-10, temperatureSeriesAnalysis.min(), 0.00001);
    }

    @Test
    public void testMax() {
        assertEquals(50, temperatureSeriesAnalysis.max(), 0.00001);
    }

    @Test
    public void testFindTempClosestToZero() {
        assertEquals(1, temperatureSeriesAnalysis.findTempClosestToZero(), 0.00001);
    }

    @Test
    public void testFindTempClosestToValue() {
        assertEquals(30, temperatureSeriesAnalysis.findTempClosestToValue(32), 0.00001);
        assertEquals(1, temperatureSeriesAnalysis.findTempClosestToValue(0), 0.00001);
    }

    @Test
    public void testFindTempsLessThen() {
        double[] sample = temperatureSeriesAnalysis.findTempsLessThen(21);

        for (double temp : sample) {
            assertTrue(temp < 21);
        }

    }

    @Test
    public void testFindTempsGreaterThen() {
        double[] sample = temperatureSeriesAnalysis.findTempsGreaterThen(30);

        for (double temp : sample) {
            assertTrue(temp >= 30);
        }
    }

    @Test
    public void testSummaryStatistics() {
        TempSummaryStatistics summaryStatistics = new TempSummaryStatistics(14.29, 19.17, -10, 50);
        TempSummaryStatistics summaryStatistics1 = temperatureSeriesAnalysis.summaryStatistics();

        assertEquals(summaryStatistics.getAvgTemp(), summaryStatistics1.getAvgTemp(), 0.00001);
        assertEquals(summaryStatistics.getDevTemp(), summaryStatistics1.getDevTemp(), 0.00001);
        assertEquals(summaryStatistics.getMinTemp(), summaryStatistics1.getMinTemp(), 0.00001);
        assertEquals(summaryStatistics.getMaxTemp(), summaryStatistics1.getMaxTemp(), 0.00001);
    }

    @Test
    public void testAddTemps() {
        int n = temperatureSeriesAnalysis.getLength();
        temperatureSeriesAnalysis.addTemps(20.0);

        assertEquals(n + 1, temperatureSeriesAnalysis.getLength());
    }
}
