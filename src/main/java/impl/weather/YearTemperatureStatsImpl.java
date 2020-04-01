package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private final Map<Month, Map<Integer, DayTemperatureInfo>> stats = new HashMap<>();
    private final Map<Month, Integer> maximums = new HashMap<>();
    private final Map<Month, Double> averages = new HashMap<>();

    @Override
    public void updateStats(DayTemperatureInfo info) {
        var month = info.getMonth();
        var day = info.getDay();
        var temperature = info.getTemperature();
        if (!stats.containsKey(month)) {
            stats.put(month, new LinkedHashMap<>());
        }
        updateMaximum(month, temperature);
        updateAverage(info);
        stats.get(month).put(day, info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        return averages.get(month);
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        return maximums;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        if (stats.getOrDefault(month, null) == null) {
            return new ArrayList<>();
        }

        List<DayTemperatureInfo> temperatures = new ArrayList<>();
        var monthStats = stats.get(month);
        for (var stat : monthStats.entrySet()) {
            boolean added = false;
            for (var i = 0; i < temperatures.size(); i++) {
                if (temperatures.get(i).getTemperature() > stat.getValue().getTemperature()) {
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

        /*return stats.get(month).values()
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
        return monthStat.getOrDefault(day, null);
    }

    void updateMaximum(Month month, Integer temperature) {
        if (maximums.containsKey(month)) {
            maximums.put(month, Math.max(temperature, maximums.get(month)));
        } else {
            maximums.put(month, temperature);
        }
    }

    void updateAverage(DayTemperatureInfo info) {
        var month = info.getMonth();
        var day = info.getDay();
        var temperature = info.getTemperature();
        if (averages.containsKey(month)) {
            var size = stats.get(month).size();
            double newAverage;
            if (stats.get(month).containsKey(day)) {
                // updated day info
                var oldTemperature = stats.get(month).get(day).getTemperature();
                newAverage = (averages.get(month) * size - oldTemperature + temperature) / size;
            } else {
                // new day
                newAverage = (averages.get(month) * size + temperature) / (size + 1);
            }
            averages.put(month, newAverage);
        } else {
            averages.put(month, (double) temperature);
        }
    }
}
