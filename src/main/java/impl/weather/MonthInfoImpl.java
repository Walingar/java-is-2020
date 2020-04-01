package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.MonthInfo;

import java.util.HashMap;
import java.util.Map;

public class MonthInfoImpl implements MonthInfo {
    private int maximum = Integer.MIN_VALUE;
    private double average = 0;
    private final Map<Integer, DayTemperatureInfo> days = new HashMap<>();

    public void addDay(DayTemperatureInfo info) {
        var day = info.getDay();
        var temperature = info.getTemperature();
        updateMaximum(temperature);
        updateAverage(day, temperature);
        days.put(day, info);
    }

    private void updateMaximum(Integer temperature) {
        maximum = Math.max(maximum, temperature);
    }

    private void updateAverage(Integer day, Integer temperature) {
        var size = days.size();
        if (days.containsKey(day)) {
            // updated day info
            var oldTemperature = days.get(day).getTemperature();
            average = (average * size - oldTemperature + temperature) / size;
        } else {
            // new day
            average = (average * size + temperature) / (size + 1);
        }
    }

    public Double getAverage() {
        return average;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public DayTemperatureInfo getDay(Integer day) {
        return days.getOrDefault(day, null);
    }

    public Map<Integer, DayTemperatureInfo> getDays() {
        return days;
    }
}
