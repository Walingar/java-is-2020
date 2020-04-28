package impl.weather;

import api.weather.DayTemperatureInfo;

import java.util.HashMap;
import java.util.Map;

public class MonthInfo {
    private Map<Integer, DayTemperatureInfo> dayTemperatureMap = new HashMap<>();
    private Double sumTemperature = 0.0;
    private int count = 0;
    private int maxTemperature = 0;

    public void add(DayTemperatureInfo info) {
        dayTemperatureMap.put(info.getDay(), info);
        updateMaxTemperature(info.getTemperature());
        sumTemperature += info.getTemperature();
        count++;
    }

    private void updateMaxTemperature(int temp) {
        if (count == 0) {
            maxTemperature = temp;
        } else {
            maxTemperature = Math.max(maxTemperature, temp);
        }
    }

    public DayTemperatureInfo getTemperature(int day) {
        return dayTemperatureMap.get(day);
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public Map<Integer, DayTemperatureInfo> getDayTemperatureMap() {
        return dayTemperatureMap;
    }

    public Double getAverage() {
        return sumTemperature / count;
    }
}
