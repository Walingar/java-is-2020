package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;
import java.time.Month;
import java.util.*;

import static java.lang.Math.max;

public class YearTemperatureStatsImpl implements YearTemperatureStats {

    private Map<Month, MonthInfo> monthInfo;

    YearTemperatureStatsImpl() {
        monthInfo = new HashMap<>();
    }

    @Override
    public void updateStats(DayTemperatureInfo info) {
        Month month = info.getMonth();
        monthInfo.putIfAbsent(month, new MonthInfo());
        monthInfo.get(month).update(info);
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        MonthInfo info = monthInfo.get(month);
        if (info == null) {
            return null;
        }
        return info.getTemperature(day);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        MonthInfo info = monthInfo.get(month);
        if (info == null) {
            return null;
        }
        return info.getAverageTemperature();
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> maxTemperatures = new HashMap<>();
        monthInfo.forEach((k, v) -> maxTemperatures.put(k, v.getMaximalTemperature()));
        return maxTemperatures;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        MonthInfo info = monthInfo.get(month);
        if (info == null) {
            return new ArrayList<>();
        }
        return info.getSortedTemperature();
    }

    private static class MonthInfo {
        private final Map<Integer, DayTemperatureInfo> temperatures;
        private Integer maximalTemperature;
        private Double sumTemperature;

        MonthInfo() {
            maximalTemperature = null;
            sumTemperature = 0.0;
            temperatures = new LinkedHashMap<>();
        }

        double getAverageTemperature() {
            return sumTemperature / temperatures.size();
        }

        int getMaximalTemperature() {
            return maximalTemperature;
        }

        void update(DayTemperatureInfo info) {
            int day = info.getDay();
            int temperature = info.getTemperature();
            if (maximalTemperature == null) {
                maximalTemperature = temperature;
            } else {
                maximalTemperature = max(temperature, maximalTemperature);
            }
            sumTemperature += temperature;
            temperatures.put(day, info);
        }

        DayTemperatureInfo getTemperature(int day) {
            return temperatures.get(day);
        }

        List<DayTemperatureInfo> getSortedTemperature() {
            List<DayTemperatureInfo> sorted = new ArrayList<>(temperatures.size());
            temperatures.forEach((k, v) -> sorted.add(v));
            sorted.sort(Comparator.comparingInt(DayTemperatureInfo::getTemperature));
            return sorted;
        }
    }
}