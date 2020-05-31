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
    private Map<Month, MonthTemperatureStats> monthTemperatureStatsMap;

    public YearTemperatureStatsImpl() {
        monthTemperatureStatsMap = new HashMap<>();
    }

    @Override
    public void updateStats(DayTemperatureInfo info) {
        var month = info.getMonth();
        if (!monthTemperatureStatsMap.containsKey(info.getMonth())) {
            monthTemperatureStatsMap.put(month, new MonthTemperatureStatsImpl(month));
        }
        var monthTemperatureStats = monthTemperatureStatsMap.get(month);
        monthTemperatureStats.updateStats(info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        if (monthTemperatureStatsMap.containsKey(month)) {
            return monthTemperatureStatsMap.get(month).getAverageTemperature();
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
        if (monthTemperatureStatsMap.containsKey(month)) {
            return monthTemperatureStatsMap.get(month).getSortedTemperature();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        if (monthTemperatureStatsMap.containsKey(month)) {
            return monthTemperatureStatsMap.get(month).getTemperature(day);
        } else {
            return null;
        }
    }
}
