package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private final Map<Month, MonthTemperatureInfo> yearMap = new EnumMap<>(Month.class);

    @Override
    public void updateStats(DayTemperatureInfo info) {
        Month month = info.getMonth();
        yearMap.putIfAbsent(month, new MonthTemperatureInfo());
        yearMap.get(month).update(info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        MonthTemperatureInfo monthStats = yearMap.get(month);
        return (monthStats == null) ? null : monthStats.getAvg();
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> maxTemperatures = new HashMap<>();
        yearMap.forEach((k, v) -> maxTemperatures.put(k, v.getMax()));
        return maxTemperatures;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        MonthTemperatureInfo info = yearMap.get(month);
        if (info == null) {
            return new ArrayList<>();
        }
        return info.getSorted();
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        MonthTemperatureInfo info = yearMap.get(month);
        if (info == null) {
            return null;
        }
        return info.get(day);
    }

    private static class MonthTemperatureInfo {
        private final Map<Integer, DayTemperatureInfo> temperatures = new LinkedHashMap<>();
        private Integer max = null;
        private Double sum = 0.0;

        double getAvg() {
            return sum / temperatures.size();
        }

        int getMax() {
            return max;
        }

        void update(DayTemperatureInfo info) {
            int day = info.getDay();
            int temperature = info.getTemperature();
            if (max == null) {
                max = temperature;
            } else {
                max = Integer.max(temperature, max);
            }
            sum += temperature;
            temperatures.put(day, info);
        }

        DayTemperatureInfo get(int day) {
            return temperatures.get(day);
        }

        List<DayTemperatureInfo> getSorted() {
            List<DayTemperatureInfo> sortedDayList = new ArrayList<>(temperatures.values());
            sortedDayList.sort(java.util.Comparator.comparing(DayTemperatureInfo::getTemperature));
            return sortedDayList;
        }
    }
}
