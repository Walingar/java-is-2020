package impl.weather;

import api.weather.DayTemperatureInfo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.Comparator.comparingDouble;

public class MonthTemperatureInfo {

    private final Map<Integer, DayTemperatureInfo> dayInfos = new LinkedHashMap<>();
    private Integer max = -273;
    private Double average = 0.0;

    public void updateMonthStats(DayTemperatureInfo dayInfo) {
        dayInfos.putIfAbsent(dayInfo.getDay(), dayInfo);
        updateAverage(dayInfo.getTemperature(), dayInfos.size());
        updateMax(dayInfo.getTemperature());
    }

    public double getAverageTemperature() {
        return average;
    }

    public Integer getMaxTemperature() {
        return max;
    }

    public List<DayTemperatureInfo> getSortedTemperature() {
        return dayInfos.values().stream().sorted(comparingDouble(DayTemperatureInfo::getTemperature)).collect(Collectors.toList());
    }

    public DayTemperatureInfo getTemperature(int day) {
        return dayInfos.get(day);
    }

    private void updateMax(int temperature) {
        if (temperature > max) {
            max = temperature;
        }
    }

    private void updateAverage(int temperature, int days) {
        if (average == 0.0) {
            average = (double) temperature;
        } else {
            average = (average * (days - 1) + temperature) / days;
        }
    }
}