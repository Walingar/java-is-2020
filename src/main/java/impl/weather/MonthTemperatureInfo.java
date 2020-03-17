package impl.weather;

import api.weather.DayTemperatureInfo;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class MonthTemperatureInfo {
    private Double averageTemperature;
    private Integer maxTemperature;
    private final Map<Integer, DayTemperatureInfo> dayTemperatures = new LinkedHashMap<>();

    void updateStats(DayTemperatureInfo info) {
        var day = info.getDay();
        var temperature = info.getTemperature();

        var previousInfo = dayTemperatures.put(day, info);

        if (maxTemperature == null || temperature > maxTemperature) {
            maxTemperature = temperature;
        }

        if (averageTemperature == null) {
            averageTemperature = (double) temperature;
        } else {
            averageTemperature = (previousInfo != null) ?
                    replaceInAverage(previousInfo.getTemperature(), temperature)
                    : addToAverage(temperature);
        }

    }

    public Double getAverageTemperature() {
        return averageTemperature;
    }

    public Integer getMaxTemperature() {
        return maxTemperature;
    }

    public DayTemperatureInfo getTemperature(int day) {
        return dayTemperatures.get(day);
    }

    public List<DayTemperatureInfo> getSortedTemperature() {
        return dayTemperatures.values().stream()
                .sorted(Comparator.comparingInt(DayTemperatureInfo::getTemperature))
                .collect(Collectors.toUnmodifiableList());
    }

    private double replaceInAverage(double oldValue, double newValue) {
        var size = dayTemperatures.size();
        return (size * averageTemperature - oldValue + newValue) / size;
    }

    private double addToAverage(double value) {
        var size = dayTemperatures.size();
        return ((size - 1) * averageTemperature + value) / size;
    }
}
