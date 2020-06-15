package impl.weather;

import api.weather.DayTemperatureInfo;

import java.util.LinkedHashMap;
import java.util.Map;

public class MonthInfo {
    private final Map<Integer, DayTemperatureInfo> dayTemperatureMap = new LinkedHashMap<>();
    private Double sumTemperature = 0.0;
    private int maxTemperature = 0;

    public void add(DayTemperatureInfo info) {
        dayTemperatureMap.put(info.getDay(), info);
        updateMaxTemperature(info.getTemperature());
        sumTemperature += info.getTemperature();
    }

    private void updateMaxTemperature(int temp) {
        if (dayTemperatureMap.size() == 1) {
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
        return sumTemperature / dayTemperatureMap.size();
    }
}
