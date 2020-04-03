package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;
import java.util.List;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private final Map<Month, Map<Integer, DayTemperatureInfo>> status = new HashMap<>();
    private final Average average = new Average();
    private final Max maxTemperature = new Max();

    @Override
    public void updateStats(DayTemperatureInfo info) {
        int day = info.getDay();
        Month month = info.getMonth();
        int temperature = info.getTemperature();
        if (!status.containsKey(month)) {
            status.put(month, new HashMap<>());
        }
        status.get(month).put(day, info);
        average.updateAverage(month, temperature);
        maxTemperature.updateMaxValue(month, temperature);

    }

    @Override
    public Double getAverageTemperature(Month month) {
        return this.average.getAverageTemp(month);
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        return this.maxTemperature.getMaxTemperature();
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        if (status.getOrDefault(month, null) == null) {
            return new ArrayList<>();
        }
        Map<Integer, DayTemperatureInfo> temperatureInfo;
        temperatureInfo = status.getOrDefault(month, null);
        ArrayList<DayTemperatureInfo> sortedTemperature = new ArrayList<>(temperatureInfo.values());
        sortedTemperature.sort(new DayTemperatureInfoComperator());
        return sortedTemperature;
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        Map<Integer, DayTemperatureInfo> temperatureInfo = status.getOrDefault(month, null);
        if (temperatureInfo == null) {
            return null;
        }
        return temperatureInfo.getOrDefault(day, null);
    }
}
