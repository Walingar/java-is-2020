package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.MonthTemperatureStats;

import java.time.Month;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;

public class MonthTemperatureStatsImpl implements MonthTemperatureStats {
    private final Month month;
    private Map<Integer, DayTemperatureInfo> dayTemperatureInfoMap;
    private Integer maxTemperature;
    private Double averageTemperature;

    public MonthTemperatureStatsImpl(Month month) {
        this.month = month;
        dayTemperatureInfoMap = new LinkedHashMap<>();
    }

    @Override
    public void updateStats(DayTemperatureInfo info) {
        var infoMonth = info.getMonth();
        if (infoMonth != month) {
            throw new IllegalArgumentException("Incorrect month value.");
        }
        int infoDay = info.getDay();
        int infoTemperature = info.getTemperature();

        dayTemperatureInfoMap.put(infoDay, info);
        updateMaxTemperature(infoTemperature);
        updateAverageTemperature(infoTemperature);
    }

    private void updateMaxTemperature(int addedTemperature) {
        if (maxTemperature == null || addedTemperature > maxTemperature) {
            maxTemperature = addedTemperature;
        }
    }

    private void updateAverageTemperature(int addedTemperature) {
        if (averageTemperature == null) {
            averageTemperature = (double) addedTemperature;
        } else {
            int numberOfDays = dayTemperatureInfoMap.size();
            averageTemperature = ((numberOfDays - 1) * averageTemperature + addedTemperature) / numberOfDays;
        }
    }

    @Override
    public Double getAverageTemperature() {
        return averageTemperature;
    }

    @Override
    public int getMaxTemperature() {
        return maxTemperature;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature() {
        return dayTemperatureInfoMap.values()
                .stream()
                .sorted(comparingInt(DayTemperatureInfo::getTemperature))
                .collect(Collectors.toList());
    }

    @Override
    public DayTemperatureInfo getTemperature(int day) {
        return dayTemperatureInfoMap.get(day);
    }
}
