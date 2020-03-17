package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private final Map<Month, MonthTemperatureInfo> map = new EnumMap<>(Month.class);

    @Override
    public void updateStats(DayTemperatureInfo info) {
        Objects.requireNonNull(info, "Info should not be null");
        var month = info.getMonth();
        map.computeIfAbsent(month, key -> new MonthTemperatureInfo()).updateStats(info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        Objects.requireNonNull(month, "Month should not be null");
        var monthInfo = map.get(month);
        return (monthInfo == null) ? null : monthInfo.getAverageTemperature();
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        return map.entrySet().stream()
                .collect(Collectors.toUnmodifiableMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getMaxTemperature()
                ));
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        Objects.requireNonNull(month, "Month should not be null");
        var monthInfo = map.get(month);
        return (monthInfo == null) ? Collections.emptyList() : monthInfo.getSortedTemperature();
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        Objects.requireNonNull(month, "Month should not be null");
        var monthInfo = map.get(month);
        return (monthInfo == null) ? null : monthInfo.getTemperature(day);
    }
}
