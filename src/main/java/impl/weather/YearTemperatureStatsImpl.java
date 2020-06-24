package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private final Map<Month, MonthTemperatureStats> yearMonthMap = new HashMap<Month, MonthTemperatureStats>();

    @Override
    public void updateStats(DayTemperatureInfo info) {
        Objects.requireNonNull(info, "Info is null");
        var month = info.getMonth();
        yearMonthMap.computeIfAbsent(month, k -> new MonthTemperatureStats()).updateStats(info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        Objects.requireNonNull(month, "Month could not be null");
        var monthInfo = yearMonthMap.get(month);
        return (monthInfo == null) ? null : monthInfo.getAvgTemperature();
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        var result = new HashMap<Month, Integer>();
        for (Map.Entry<Month, MonthTemperatureStats> entry : yearMonthMap.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getMaxTemperature());
        }
        return result;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        Objects.requireNonNull(month, "Month could not be null");
        var monthInfo = yearMonthMap.get(month);
        return (monthInfo == null) ? Collections.emptyList() : monthInfo.getSortedTemperature();
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        Objects.requireNonNull(month, "Month could not be null");
        var monthInfo = yearMonthMap.get(month);
        return (monthInfo == null) ? null : monthInfo.getDayTemperatureInfo(day);
    }
}
