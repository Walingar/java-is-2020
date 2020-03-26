package impl.weather;

import api.weather.DayTemperatureInfo;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class MonthTemperatureStats {
    private Double avgTemperature;
    private Integer maxTemperature;
    private final Map<Integer, DayTemperatureInfo> dayTemperatures = new LinkedHashMap<>();

    void updateStats(DayTemperatureInfo info) {

        int day = info.getDay();
        int temperature = info.getTemperature();
        DayTemperatureInfo outdatedDayInfo = dayTemperatures.put(day, info);
        if (avgTemperature == null) {
            avgTemperature = (double) temperature;
        } else {
            avgTemperature = (outdatedDayInfo != null) ?
                    updateAvgElement(outdatedDayInfo.getTemperature(), temperature)
                    : pushValueToAvg(temperature);
        }

        if (maxTemperature == null || temperature > maxTemperature) {
            maxTemperature = temperature;
        }
    }

    private double updateAvgElement(double oldValue, double newValue) {
        var size = dayTemperatures.size();
        return (size * avgTemperature - oldValue + newValue) / size;
    }

    private double pushValueToAvg(double value) {
        var size = dayTemperatures.size();
        return ((size - 1) * avgTemperature + value) / size;
    }

    public Double getAvgTemperature() {
        return avgTemperature;
    }

    public Integer getMaxTemperature() {
        return maxTemperature;
    }

    public DayTemperatureInfo getDayInfo(int day) {
        return dayTemperatures.get(day);
    }

    public List<DayTemperatureInfo> getSortedTemperature() {
        return dayTemperatures.values().stream()
                .sorted(Comparator.comparingInt(DayTemperatureInfo::getTemperature))
                .collect(Collectors.toList());
    }

}