package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.MonthTemperatureInfo;

import java.util.LinkedHashMap;
import java.util.Map;

public class MonthTemperatureInfoImpl implements MonthTemperatureInfo {
    private final Map<Integer, DayTemperatureInfo> monthData = new LinkedHashMap<>();
    private double averageForMonth;
    private double maxForMonth;

    @Override
    public Integer getTemperatureDay(Integer day) {
        if (monthData.containsKey(day)) {
            return monthData.get(day).getTemperature();
        }
        return null;
    }

    @Override
    public DayTemperatureInfo getDayInfo(Integer day) {
        if (monthData.containsKey(day)) {
            return monthData.get(day);
        }
        return null;
    }

    @Override
    public Double getAverageTemperature() {
        if (!monthData.isEmpty()) {
            return averageForMonth;
        }
        return null;
    }

    @Override
    public Double getMaxTemperature() {
        if (!monthData.isEmpty()) {
            return maxForMonth;
        }
        return null;
    }

    @Override
    public Map<Integer, DayTemperatureInfo> getMonthData() {
        return monthData;
    }

    @Override
    public void updateStats(DayTemperatureInfo info) {
        if (monthData.containsKey(info.getDay())) {
            throw new IllegalArgumentException("Day is already in stats");
        } else {
            monthData.put(info.getDay(), info);
            updateAverage(info.getTemperature());
            updateMax(info.getTemperature());
        }
    }

    private void updateMax(Integer newTemperature) {
        if (maxForMonth < newTemperature) {
            maxForMonth = newTemperature;
        }
    }

    private void updateAverage(Integer newTemperature) {
        averageForMonth = (averageForMonth * (monthData.size() - 1) + newTemperature) / monthData.size();
    }
}
