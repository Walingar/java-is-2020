package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;

public class YearTemperatureStatsImpl implements YearTemperatureStats {

    private final Map<Month, MonthTemperatureInfo> yearTemperatureInfo = new HashMap<>();

    @Override
    public void updateStats(DayTemperatureInfo info) {
        Month month = info.getMonth();

        if (!yearTemperatureInfo.containsKey(month)) {
            yearTemperatureInfo.put(month, new MonthTemperatureInfo());
        }

        yearTemperatureInfo.get(month).updateMonthTemperatureInfo(info);
    }

    @Override
    public Double getAverageTemperature(Month month) {

        MonthTemperatureInfo monthTemperatureInfo = yearTemperatureInfo.get(month);
        if (monthTemperatureInfo == null) {
            return null;
        }
        return monthTemperatureInfo.getAverageTemperature();
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> maxTemperature = new HashMap<>();
        for (var entry : yearTemperatureInfo.entrySet()) {
            maxTemperature.put(entry.getKey(), entry.getValue().getMaxTemperature());
        }
        return maxTemperature;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {

        MonthTemperatureInfo monthTemperatureInfo = yearTemperatureInfo.get(month);
        if (monthTemperatureInfo == null) {
            return new ArrayList<>();
        }
        return monthTemperatureInfo.getSortedDayList();
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        var monthTemperatureInfo = yearTemperatureInfo.get(month);
        if (monthTemperatureInfo != null) {
            return monthTemperatureInfo.getDayInfo(day);
        }
        return null;
    }
}
