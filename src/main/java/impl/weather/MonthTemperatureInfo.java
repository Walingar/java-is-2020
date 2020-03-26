package impl.weather;

import api.weather.DayTemperatureInfo;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MonthTemperatureInfo {

    private Double avgTemperature;
    private Integer maxTemperature;
    private final Map<Integer, DayTemperatureInfo> dayTemperatures = new LinkedHashMap<>();

    public void updateStats(DayTemperatureInfo info) {
        var day = info.getDay();
        var temperature = info.getTemperature();

        if (dayTemperatures.containsKey(day)) {
            throw new IllegalArgumentException("Day is already in stats");
        }

        dayTemperatures.put(day, info);

        if (maxTemperature == null || temperature > maxTemperature) {
            maxTemperature = temperature;
        }

        calculateNewAverage(temperature);
    }

    private void calculateNewAverage(double value) {
        if(avgTemperature != null) {
            var size = dayTemperatures.size();
            avgTemperature = ((size - 1) * avgTemperature + value) / size;
        } else {
            avgTemperature = value;
        }
    }

    public Double getAvgTemperature() {
        return avgTemperature;
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
                .collect(Collectors.toList());
    }

}
