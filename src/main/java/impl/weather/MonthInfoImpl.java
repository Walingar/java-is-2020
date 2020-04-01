package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.MonthInfo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MonthInfoImpl implements MonthInfo {
    private int maximum = Integer.MIN_VALUE;
    private double average = 0;
    private final Map<Integer, DayTemperatureInfo> days = new LinkedHashMap<>();

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

    public double getAverage() {
        return average;
    }

    public int getMaximum() {
        return maximum;
    }

    public DayTemperatureInfo getDay(Integer day) {
        return days.getOrDefault(day, null);
    }

    public List<DayTemperatureInfo> getDays() {
        return new ArrayList<>(days.values());
    }
}
