package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.MonthInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;

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
        var monthStat = stats.getOrDefault(month, null);
        return monthStat == null ? null : monthStat.getAverage();
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
        if (stats.getOrDefault(month, null) == null) {
            return new ArrayList<>();
        }

        List<DayTemperatureInfo> temperatures = new ArrayList<>();
        var monthStats = stats.get(month).getDays();
        for (var stat : monthStats.entrySet()) {
            boolean added = false;
            for (var i = 0; i < temperatures.size(); i++) {
                if (temperatures.get(i).getTemperature() >= stat.getValue().getTemperature()) {
                    temperatures.add(i, stat.getValue());
                    added = true;
                    break;
                }
            }
            if (!added) {
                temperatures.add(stat.getValue());
            }
        }
        return temperatures;

        /*return stats.get(month).getDays().values()
                .stream()
                .sorted(Comparator.comparingInt(DayTemperatureInfo::getTemperature))
                .collect(Collectors.toList());*/
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        var monthStat = stats.getOrDefault(month, null);
        if (monthStat == null) {
            return null;
        }
        return monthStat.getDay(day);
    }
}
