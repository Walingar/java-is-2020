package impl.weather.impl;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class YearTemperatureStatsImpl implements YearTemperatureStats {

    //    If we just have a list of DayTempInfo we are not gonna have O(const) for update
//    Also we can put Map of actual month days inside but that's overkill
//    We are still pretty well bound by 31 item in the nested list
//    ( we were still well bound by 12 * 31 if we put everythong into one list but whatever =) )
//    Also, we don't really care what kind of underlying list we use here.
    private final Map<Month, List<DayTemperatureInfo>> dayTemperatureInfos;
    private final Map<Month, Double> averageStatsCache;
    private final Map<Month, Integer> maxStatsCache;

    private void invalidateCaches(Month month) {
//        containsKey check is unnecessary here...
        maxStatsCache.remove(month);
        averageStatsCache.remove(month);
    }

    private Integer getMonthMaxTemperature(Map.Entry<Month, List<DayTemperatureInfo>> mapEntry) {
        var month = mapEntry.getKey();
        if (maxStatsCache.containsKey(month)) {
            return maxStatsCache.get(month);
        }
        var value = calculateMaxTempOrThrow(mapEntry.getValue());
        maxStatsCache.put(month, value);
        return value;
    }

    private Integer calculateMaxTempOrThrow(List<DayTemperatureInfo> dayTemperatureInfos) {
        return dayTemperatureInfos.stream()
                .map(DayTemperatureInfo::getTemperature)
                .max(Integer::compareTo)
                .orElseThrow();
    }

    public YearTemperatureStatsImpl() {
        dayTemperatureInfos = new HashMap<>();
//        Here we hope that Months are properly hashed...
        averageStatsCache = new HashMap<>();
        maxStatsCache = new HashMap<>();
    }

    @Override
    public void updateStats(DayTemperatureInfo newInfo) {
        if (newInfo == null) {
            throw new IllegalArgumentException("Null passed as update data");
        }
        var month = newInfo.getMonth();
        var monthStats = dayTemperatureInfos.computeIfAbsent(month, m -> new ArrayList<>());
        var existingDayInfo = monthStats.stream()
                .filter(dayInfo -> dayInfo.getDay() == newInfo.getDay())
                .findFirst()
                .orElse(null);

        if (existingDayInfo == null) {
            monthStats.add(newInfo);
        } else {
//        picking ArrayList helps us a little here...
            monthStats.set(monthStats.indexOf(existingDayInfo), newInfo);
        }
        invalidateCaches(month);
    }


    @Override
    public Double getAverageTemperature(Month month) {
        if (!dayTemperatureInfos.containsKey(month)) {
            return null;
        }
        if (averageStatsCache.containsKey(month)) {
            return averageStatsCache.get(month);
        }
        var average = dayTemperatureInfos.get(month).stream()
                .mapToDouble(DayTemperatureInfo::getTemperature)
                .average(); //Absolutely barbaric not to have proper `.orElse...` method here...
        if (average.isPresent()) {
            var value = average.getAsDouble();
            averageStatsCache.put(month, value);
            return value;
        } else {
            return null;
        }
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        return dayTemperatureInfos.entrySet().stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                this::getMonthMaxTemperature
                        ));
    }


    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
//        Here .getOrDefault substitutes for if (!.containsKey()) {return new List}
//        It's slightly slower, but bearable
        return dayTemperatureInfos.getOrDefault(month, new ArrayList<>()).stream() // Maybe move .stream to nextline?
                .sorted(Comparator.comparingInt(DayTemperatureInfo::getTemperature))
                .collect(Collectors.toList());
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        if (!dayTemperatureInfos.containsKey(month)) {
            return null;
        }
        return dayTemperatureInfos.get(month).stream()
                .filter(info -> info.getDay() == day)
                .findFirst()
                .orElse(null);
    }
}
