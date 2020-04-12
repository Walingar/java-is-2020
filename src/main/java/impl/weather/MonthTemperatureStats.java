package impl.weather;

import api.weather.DayTemperatureInfo;

import java.util.*;
import java.util.stream.Collectors;

public class MonthTemperatureStats {
    private final Map<Integer, DayTemperatureInfo> dayTemperatureMap = new LinkedHashMap<Integer, DayTemperatureInfo>();
    private Double avgTemperature;
    private Integer maxTemperature;

    public void updateStats(DayTemperatureInfo info) {
        int day = info.getDay();
        DayTemperatureInfo oldInfo = dayTemperatureMap.put(day, info);
        updateInfo(oldInfo, info);
    }

    public Double getAvgTemperature() {
        return avgTemperature;
    }

    public Integer getMaxTemperature() {
        return maxTemperature;
    }

    public List<DayTemperatureInfo> getSortedTemperature() {
        return dayTemperatureMap.values().stream()
                .sorted(Comparator.comparingInt(DayTemperatureInfo::getTemperature))
                .collect(Collectors.toList());
    }

    public DayTemperatureInfo getDayTemperatureInfo(int day) {
        return dayTemperatureMap.get(day);
    }

    private void updateInfo(DayTemperatureInfo oldInfo, DayTemperatureInfo newInfo) {
        var newTemperature = newInfo.getTemperature();

        if (avgTemperature == null) {
            avgTemperature = (double) newInfo.getTemperature();
        } else {
            avgTemperature = (oldInfo == null) ? pushAvg(newTemperature) : updateAvg(oldInfo.getTemperature(), newTemperature);
        }

        if (maxTemperature == null || newTemperature > maxTemperature) {
            maxTemperature = newTemperature;
        }
    }

    private double updateAvg(double oldTemperature, double newTemperature) {
        var size = dayTemperatureMap.size();
        return (size * avgTemperature - oldTemperature + newTemperature) / size;
    }

    private double pushAvg(double newTemperature) {
        var size = dayTemperatureMap.size();
        return ((size - 1) * avgTemperature + newTemperature) / size;
    }
}
