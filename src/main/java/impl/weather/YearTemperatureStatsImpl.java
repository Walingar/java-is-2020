package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private HashMap<Month, LinkedHashMap<Integer, DayTemperatureInfo>> stats = new HashMap<>();

    @Override
    public void updateStats(DayTemperatureInfo info) {
        var month = info.getMonth();
        var day = info.getDay();
        if (!stats.containsKey(month)) {
            stats.put(month, new LinkedHashMap<>());
        }
        stats.get(month).put(day, info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        var days = stats.getOrDefault(month, null);
        if (days == null){
            return null;
        }
        double average = 0;
        for (DayTemperatureInfo day : days.values()) {
            average += day.getTemperature();
        }
        return average / days.size();
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        HashMap<Month, Integer> maximums = new HashMap<>();
        for (Month month : stats.keySet()) {
            var maximum = Integer.MIN_VALUE;
            for (DayTemperatureInfo day : stats.get(month).values()) {
                if (day.getTemperature() > maximum) {
                    maximum = day.getTemperature();
                }
            }
            maximums.put(month, maximum);
        }
        return maximums;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        if (stats.getOrDefault(month,null) == null) {
            return new LinkedList<>();
        }

        LinkedList<DayTemperatureInfo> temperatures = new LinkedList<>();
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
        var monthStat = stats.getOrDefault(month,null);
        if (monthStat == null) {
            return null;
        }
        return monthStat.getOrDefault(day, null);
    }
}
