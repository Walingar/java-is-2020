package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class YearTemperatureStatsImpl implements YearTemperatureStats {

    private final Map<Month, MonthTemperatureInfo> monthInfoMap = new EnumMap<>(Month.class);

    @Override
    public void updateStats(DayTemperatureInfo info) {
        Objects.requireNonNull(info, "Info should not be null");
        validateMonthDay(info.getDay(), info.getMonth());
        var month = info.getMonth();
        monthInfoMap.computeIfAbsent(month, key -> new MonthTemperatureInfo()).updateStats(info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        Objects.requireNonNull(month, "month should not be null");
        var monthInfo = monthInfoMap.get(month);
        return (monthInfo == null) ? null : monthInfo.getAvgTemperature();
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        return monthInfoMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getMaxTemperature()
                ));
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        Objects.requireNonNull(month, "Month should not be null");
        var monthInfo = monthInfoMap.get(month);
        return (monthInfo == null) ? Collections.emptyList() : monthInfo.getSortedTemperature();
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        Objects.requireNonNull(month, "Month should not be null");
        validateMonthDay(day, month);
        var monthInfo = monthInfoMap.get(month);
        return (monthInfo == null) ? null : monthInfo.getTemperature(day);
    }

    private void validateMonthDay(int day, Month month) {
        if (day < 0 || day > month.length(false)) {
            throw new IllegalArgumentException("Wrong day format");
        }
    }
}
