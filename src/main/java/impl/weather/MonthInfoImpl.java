package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.MonthInfo;

import java.util.ArrayList;
import java.util.List;

public class MonthInfoImpl implements MonthInfo {
    private int maximum = Integer.MIN_VALUE;
    private double average = 0;
    private final List<DayTemperatureInfo> days = new ArrayList<>();

    public void addDay(DayTemperatureInfo info) {
        var day = info.getDay();
        var temperature = info.getTemperature();

        var oldDay = getDay(day);
        if (oldDay == null) {
            updateAverageNewDay(temperature);
            days.add(info);
        } else {
            updateAverageExistingDay(oldDay.getTemperature(), temperature);
            days.set(days.indexOf(oldDay), info);
        }
        updateMaximum(temperature);
    }

    private void updateMaximum(Integer temperature) {
        maximum = Math.max(maximum, temperature);
    }

    private void updateAverageExistingDay(Integer oldTemperature, Integer temperature) {
        var size = days.size();
        average = (average * size - oldTemperature + temperature) / size;
    }

    private void updateAverageNewDay(Integer temperature) {
        var size = days.size();
        average = (average * size + temperature) / (size + 1);
    }

    public double getAverage() {
        return average;
    }

    public int getMaximum() {
        return maximum;
    }

    public DayTemperatureInfo getDay(Integer day) {
        return days.stream().filter(dayInfo -> dayInfo.getDay() == day).findFirst().orElse(null);
    }

    public List<DayTemperatureInfo> getDays() {
        return days;
    }
}
