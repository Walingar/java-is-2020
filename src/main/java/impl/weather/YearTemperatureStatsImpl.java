package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private final Map<Month, MonthTemperatureStats> yearMap = new EnumMap<>(Month.class);

    @Override
    public void updateStats(DayTemperatureInfo info) {
        Objects.requireNonNull(info, "Info is null");
        var month = info.getMonth();
        yearMap.computeIfAbsent(month, key -> new MonthTemperatureStats()).updateStats(info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        Objects.requireNonNull(month, "Month is null");
        var monthStats = yearMap.get(month);
        return (monthStats == null) ? null : monthStats.getAvgTemperature();
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        return yearMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getMaxTemperature()
                ));
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        Objects.requireNonNull(month, "Month is null");
        var monthStats = yearMap.get(month);
        return (monthStats == null) ? Collections.emptyList() : monthStats.getSortedTemperature();
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        Objects.requireNonNull(month, "Month is null");
        var monthStats = yearMap.get(month);
        return (monthStats == null) ? null : monthStats.getDayInfo(day);
    }
}