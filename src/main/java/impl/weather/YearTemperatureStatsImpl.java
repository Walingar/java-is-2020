package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private final Map<Month, Map<Integer, DayTemperatureInfo>> yearMap = new EnumMap<>(Month.class);
    private final Map<Month, Integer> maxTemperatures = new EnumMap<>(Month.class);
    private final Map<Month, Double> avgTemperatures = new EnumMap<>(Month.class);

    @Override
    public void updateStats(DayTemperatureInfo info) {
        Objects.requireNonNull(info, "Info is null");
        Month month = info.getMonth();
        int day = info.getDay();
        int temperature = info.getTemperature();
        DayTemperatureInfo oldInfo = yearMap.computeIfAbsent(month, key -> new HashMap<>()).put(day, info);
        Map<Integer, DayTemperatureInfo> monthStats = yearMap.get(month);
        int size = monthStats.size();
        double newAvg = avgTemperatures.getOrDefault(month, 0d);
        if (oldInfo == null) {
            avgTemperatures.put(month, ((size - 1) * newAvg + temperature) / size);
        } else {
            avgTemperatures.put(month, (size * newAvg - oldInfo.getTemperature() + temperature) / size);
        }
        int max = maxTemperatures.getOrDefault(month, -273);
        if (temperature > max) {
            maxTemperatures.put(month, temperature);
        }
    }

    @Override
    public Double getAverageTemperature(Month month) {
        Objects.requireNonNull(month, "Month is null");
        Map<Integer, DayTemperatureInfo> monthStats = yearMap.get(month);
        return (monthStats == null) ? null : avgTemperatures.get(month);
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        return maxTemperatures;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        Map<Integer, DayTemperatureInfo> monthStats = yearMap.get(month);
        if (monthStats != null) {
            List<DayTemperatureInfo> sortedDayList = new ArrayList<>(monthStats.values());
            sortedDayList.sort(java.util.Comparator.comparingInt(DayTemperatureInfo::getTemperature));
            return sortedDayList;
        }
        return Collections.emptyList();
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        Objects.requireNonNull(month, "Month is null");
        Map<Integer, DayTemperatureInfo> monthStats = yearMap.get(month);
        return (monthStats == null) ? null : monthStats.get(day);
    }
}
