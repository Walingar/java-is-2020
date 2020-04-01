package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.MonthInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private final Map<Month, MonthInfo> stats = new HashMap<>();

    @Override
    public void updateStats(DayTemperatureInfo info) {
        var month = info.getMonth();
        if (!stats.containsKey(month)) {
            stats.put(month, new MonthInfoImpl());
        }
        stats.get(month).addDay(info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        return stats.containsKey(month) ? stats.get(month).getAverage() : null;
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> maximums = new HashMap<>();
        for (var month : stats.keySet()) {
            maximums.put(month, stats.get(month).getMaximum());
        }
        return maximums;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        if (!stats.containsKey(month)) {
            return new ArrayList<>();
        }
        return stats.get(month).getDays()
                .stream()
                .sorted(Comparator.comparingInt(DayTemperatureInfo::getTemperature))
                .collect(Collectors.toList());
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        return stats.containsKey(month) ? stats.get(month).getDay(day) : null;
    }
}
