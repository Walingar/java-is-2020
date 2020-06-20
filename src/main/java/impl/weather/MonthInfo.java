package impl.weather;

import api.weather.DayTemperatureInfo;

import java.time.Month;
import java.util.*;

public class MonthInfo {
    private Double temperatureAverage;
    private Integer temperatureMax;

    private final Map<Integer, DayTemperatureInfo> monthInfo = new LinkedHashMap<>();

    MonthInfo(DayTemperatureInfo info) {
        updateMonthInfo(info);
    }

    public void updateMonthInfo(DayTemperatureInfo info) {
        monthInfo.put(info.getDay(), info);
        updateTemperatureAverage(info.getTemperature());
        updateTemperatureMax(info.getTemperature());
    }

    public DayTemperatureInfo getTemperature(int day) {
        return monthInfo.get(day);
    }

    public Double getTemperatureAverage() {
        return temperatureAverage;
    }

    public Integer getTemperatureMax() {
        return temperatureMax;
    }

    public List<DayTemperatureInfo> sortTemperature(Month month) {
        List<DayTemperatureInfo> sortedTemperature = new ArrayList<>();
        monthInfo.forEach((key, value) -> sortedTemperature.add(value));
        sortedTemperature.sort(Comparator.comparing(DayTemperatureInfo::getTemperature));
        return sortedTemperature;
    }

    private void updateTemperatureAverage(int temperature) {
        if (temperatureAverage == null) {
            temperatureAverage = temperature + 0.0;
        } else {
            int daysCount = monthInfo.size();
            temperatureAverage = (temperatureAverage * (daysCount - 1) + temperature) / daysCount;
        }
    }

    private void updateTemperatureMax(int temperature) {
        if (temperatureMax == null) {
            temperatureMax = temperature;
        } else {
            temperatureMax = Math.max(temperatureMax, temperature);
        }
    }

}
