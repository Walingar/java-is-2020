package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toMap;

public class YearTemperatureStatsImpl implements YearTemperatureStats {

    private final Map<Month, MonthTemperature> monthTemperatures = new HashMap<>();

    @Override
    public void updateStats(DayTemperatureInfo info) {
        var monthTemperature = monthTemperatures.computeIfAbsent(info.getMonth(), month -> new MonthTemperature());
        monthTemperature.add(info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        var monthTemperature = monthTemperatures.get(month);
        return monthTemperature == null ? null : monthTemperature.getAverage();
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        return monthTemperatures.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, entry -> entry.getValue().getMaximum()));
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        var monthTemperature = monthTemperatures.get(month);
        return monthTemperature == null ? emptyList() : monthTemperature.getSortedTemperature();
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        var monthTemperature = monthTemperatures.get(month);
        return monthTemperature == null ? null : monthTemperature.getDayTemperatureInfo(day);
    }
}
