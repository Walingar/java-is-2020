package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;

public class YearTemperatureStatsImpl implements YearTemperatureStats {

    private final Map<Month, MonthInfo> yearStats = new HashMap<>();

    @Override
    public void updateStats(DayTemperatureInfo info) {
        Month month = info.getMonth();

        if (!yearStats.containsKey(month)) {
            yearStats.put(month, new MonthInfo());
        }

        yearStats.get(month).updateMonthInfo(info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        MonthInfo monthInfo = yearStats.get(month);
        if (monthInfo == null) {
            return null;
        }
        return monthInfo.getAvgTemperature();
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> monthMaxTemperature = new HashMap<>();
        for (var monthMonthInfoEntry : yearStats.entrySet()) {
            monthMaxTemperature.put(monthMonthInfoEntry.getKey(), monthMonthInfoEntry.getValue().getMaxTemperature());
        }
        return monthMaxTemperature;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        MonthInfo monthInfo = yearStats.get(month);
        if (monthInfo == null) {
            return new ArrayList<>();
        }
        return monthInfo.getDaySortedByTemperature();
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        MonthInfo monthInfo = yearStats.get(month);
        if (monthInfo != null) {
            return monthInfo.getDayInfo(day);
        }
        return null;
    }
}
