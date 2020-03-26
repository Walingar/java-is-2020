package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;

public class YearTemperatureStatsImpl implements YearTemperatureStats {

    private LinkedHashMap<Month, MonthTemperatureInfo> monthData = new LinkedHashMap<>();

    @Override
    public void updateStats(DayTemperatureInfo info) {
        if (monthData.containsKey(info.getMonth())) {
            monthData.get(info.getMonth()).updateMonthStats(info);
        } else {
            var monthInfo = new MonthTemperatureInfo();
            monthInfo.updateMonthStats(info);
            monthData.putIfAbsent(info.getMonth(), monthInfo);
        }
    }

    @Override
    public Double getAverageTemperature(Month month) {
        var monthTemp = monthData.get(month);
        return monthTemp != null ? monthTemp.getAverageTemperature() : null;
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> result = new HashMap<>();
        monthData.forEach((key, value) -> result.put(key, value.getMaxTemperature()));
        return result;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        var monthTemp = monthData.get(month);
        return monthTemp != null ? monthTemp.getSortedTemperature() : new LinkedList<>();
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        var monthTemp = monthData.get(month);
        return monthTemp != null ? monthTemp.getTemperature(day) : null;
    }
}