package impl.weather;

import api.weather.DayTemperatureInfo;

import java.util.HashMap;
import java.util.Map;

public class MonthInfo {
    private final Map<Integer, DayTemperatureInfo> info = new HashMap<>();
    private AverageData averageInfo;
    private int maximumTemp;

    public MonthInfo() {
        this.averageInfo = new AverageData(0);
        this.maximumTemp = -1000;
    }

    public void set(Integer day, DayTemperatureInfo dayInfo) {
        info.put(day, dayInfo);
    }

    public Map<Integer, DayTemperatureInfo> getInfo() {
        return this.info;
    }

    public void updateAverage(int temperature) {
        int numOfDays = (this.info.size());
        var oldAverage = this.averageInfo.getAverage();
        double newAverage = ((oldAverage * numOfDays) + temperature) / (numOfDays + 1);
        this.averageInfo = new AverageData(newAverage);
    }

    public AverageData getAverageInfo() {
        return this.averageInfo;
    }

    public void updateMaxValue(int temperature) {
        this.maximumTemp = Math.max(temperature, this.maximumTemp);
    }

    public Integer getMaxTemperatureOnMonth() {
        return this.maximumTemp;
    }

    public static class AverageData {
        private final double average;

        public AverageData(double average) {
            this.average = average;
        }

        public double getAverage() {
            return this.average;
        }
    }
}
