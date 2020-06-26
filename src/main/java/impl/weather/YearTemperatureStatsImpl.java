package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.MonthTemperatureStats;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private final Map<Month, MonthTemperatureStats> monthTemperatureStatsMap = new HashMap<>();

    @Override
    public void updateStats(DayTemperatureInfo info) {
        var month = info.getMonth();
        if (!monthTemperatureStatsMap.containsKey(month)) {
            monthTemperatureStatsMap.put(month, new MonthTemperatureStatsImpl(month));
        }
        var monthTemperatureStats = monthTemperatureStatsMap.get(month);
        monthTemperatureStats.updateStats(info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        var monthTemperatureStats = monthTemperatureStatsMap.get(month);
        if (monthTemperatureStats != null) {
            return monthTemperatureStats.getAverageTemperature();
        } else {
            return null;
        }
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> maxTemperatureMonthMap = new HashMap<>();
        for (var month : monthTemperatureStatsMap.keySet()) {
            maxTemperatureMonthMap.put(month, monthTemperatureStatsMap.get(month).getMaxTemperature());
        }
        return maxTemperatureMonthMap;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        var monthTemperatureStats = monthTemperatureStatsMap.get(month);
        if (monthTemperatureStats != null) {
            return monthTemperatureStats.getSortedTemperature();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        var monthTemperatureStats = monthTemperatureStatsMap.get(month);
        if (monthTemperatureStats != null) {
            return monthTemperatureStats.getTemperature(day);
        } else {
            return null;
        }
    }
}
