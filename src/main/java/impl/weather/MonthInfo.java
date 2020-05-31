package impl.weather;

import api.weather.DayTemperatureInfo;

import java.util.HashMap;
import java.util.Map;

public class MonthInfo {
    private final Map<Integer, DayTemperatureInfo> info = new HashMap<>();
    private AverageData averageInfo;
    private int maximumTemp;

    public MonthInfo() {
        averageInfo = new AverageData(0);
        maximumTemp = -1000;
    }

    public void set(Integer day, DayTemperatureInfo dayInfo) {
        info.put(day, dayInfo);
    }

    public Map<Integer, DayTemperatureInfo> getInfo() {
        return info;
    }

    public void updateAverage(int temperature) {
        int numOfDays = (info.size());
        var oldAverage = averageInfo.getAverage();
        double newAverage = ((oldAverage * numOfDays) + temperature) / (numOfDays + 1);
        averageInfo = new AverageData(newAverage);
    }

    public AverageData getAverageInfo() {
        return averageInfo;
    }

    public void updateMaxValue(int temperature) {
        maximumTemp = Math.max(temperature, maximumTemp);
    }

    public Integer getMaxTemperatureOnMonth() {
        return maximumTemp;
    }

    public static class AverageData {
        private final double average;

        public AverageData(double average) {
            this.average = average;
        }

        public double getAverage() {
            return average;
        }
    }
}
