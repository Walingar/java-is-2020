package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;

public class YearTemperatureStatsImpl implements YearTemperatureStats {

    private HashMap<Month, LinkedHashMap<Integer, DayTemperatureInfo>> yearTemperatureInfo = new HashMap<>();
    private HashMap<Month, Integer> maxTemperatureInfo = new HashMap<>();
    private HashMap<Month, Double> averageTemperatureInfo = new HashMap<>();

    @Override
    public void updateStats(DayTemperatureInfo info) {
        int day = info.getDay();
        Month month = info.getMonth();
        int temperature = info.getTemperature();


        if (!yearTemperatureInfo.containsKey(month)) {
            yearTemperatureInfo.put(month, new LinkedHashMap<>());
            maxTemperatureInfo.put(month, temperature);
            averageTemperatureInfo.put(month, (double) temperature);

        }

        yearTemperatureInfo.get(month).put(day, info);

        int monthSize = yearTemperatureInfo.get(month).size();

        if (monthSize > 1) {
            if (temperature > maxTemperatureInfo.get(month)) {
                maxTemperatureInfo.put(month, temperature);
            }

            var oldAverageTemperature = averageTemperatureInfo.get(month);
            var newAverageTemperature = (oldAverageTemperature * (monthSize - 1) + temperature) / monthSize;
            averageTemperatureInfo.put(month, newAverageTemperature);
        }


    }

    @Override
    public Double getAverageTemperature(Month month) {
        return averageTemperatureInfo.get(month);
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        return maxTemperatureInfo;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        var monthTemperatureInfo = yearTemperatureInfo.getOrDefault(month, null);
        if (monthTemperatureInfo == null){
            return new ArrayList<>();
        }
        ArrayList<DayTemperatureInfo> result = new ArrayList<>(monthTemperatureInfo.values());
        result.sort(Comparator.comparing(DayTemperatureInfo::getTemperature));
        return result;
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        var monthTemperatureInfo = yearTemperatureInfo.getOrDefault(month, null);
        if (monthTemperatureInfo != null) {
            return monthTemperatureInfo.get(day);
        }
        return null;
    }
}
