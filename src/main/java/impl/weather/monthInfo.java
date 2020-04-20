package impl.weather;

import api.weather.DayTemperatureInfo;

import java.util.HashMap;
import java.util.Map;


public class monthInfo {
    private final Map<Integer, DayTemperatureInfo> info = new HashMap<>();
    private averageData averageInfo;
    private int maximumTemp;

    public monthInfo() {
        this.averageInfo = new averageData(0, 0);
        this.maximumTemp = -1000;
    }

    public void set(Integer day, DayTemperatureInfo dayInfo) {
        info.put(day, dayInfo);
    }


    public Map<Integer, DayTemperatureInfo> getInfo() {
        return this.info;
    }

    public void updateAverage(int temperature) {
        double numOfDays = (this.averageInfo.getNumOfElements());
        var oldAverage = this.averageInfo.getAverage();
        double newAverage = ((oldAverage * numOfDays) + temperature) / (numOfDays + 1);
        int counter = (int) numOfDays + 1;
        this.averageInfo = new averageData(newAverage, counter);
    }

    public Double getAverageTemp() {
        return this.averageInfo.getAverage();
    }

    public void updateMaxValue(int temperature) {
        this.maximumTemp = Math.max(temperature, this.maximumTemp);
    }

    public Integer getMaxTemperature() {
        return this.maximumTemp;
    }

    public static class averageData {
        private final double average;
        private final int numOfElements;

        public averageData(double average, int numOfElements) {
            this.average = average;
            this.numOfElements = numOfElements;
        }

        public double getAverage() {
            return this.average;
        }

        public int getNumOfElements() {
            return this.numOfElements;
        }
    }
}
