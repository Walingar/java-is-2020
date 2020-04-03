package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;

public class YearTemperatureStatsImpl implements YearTemperatureStats {

    private Map<Month, Integer> maxTemperature = new HashMap<>();
    private Map<Month, Double> avgTemperature = new HashMap<>();
    private Map<Month, LinkedHashMap<Integer, DayTemperatureInfo>> yearStats = new HashMap<>();

    @Override
    public void updateStats(DayTemperatureInfo info) {
        Month month = info.getMonth();
        int day = info.getDay();
        int temperature = info.getTemperature();

        if (!yearStats.containsKey(month)) {
            maxTemperature.put(month, temperature);
            avgTemperature.put(month, (double) temperature);
            yearStats.put(month, new LinkedHashMap<>());
        }

        yearStats.get(month).put(day, info);
        int dayCounter = yearStats.get(month).size();

        if (dayCounter != 1) {
            if (maxTemperature.get(month) < temperature) {
                maxTemperature.put(month, temperature);
            }

            double newAverageTemperature = (avgTemperature.get(month) * (dayCounter - 1) + temperature) / dayCounter;

            avgTemperature.put(month, newAverageTemperature);
        }
    }

    @Override
    public Double getAverageTemperature(Month month) {
        return avgTemperature.get(month);
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        return maxTemperature;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        if (yearStats.getOrDefault(month, null) == null) {
            return new ArrayList<>();
        }
        HashMap<Integer, DayTemperatureInfo> temp = yearStats.getOrDefault(month, null);
        ArrayList<DayTemperatureInfo> List = new ArrayList<>(temp.values());
        List.sort(java.util.Comparator.comparing(DayTemperatureInfo::getTemperature));
        return List;
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        var stat = yearStats.getOrDefault(month, null);
        if (stat != null) {
            return stat.get(day);
        }
        return null;
    }
}
