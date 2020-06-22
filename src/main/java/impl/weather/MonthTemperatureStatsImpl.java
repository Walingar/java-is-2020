package impl.weather;

import api.weather.DayTemperatureInfo;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public final class MonthTemperatureStatsImpl {
    private final Map<Integer, DayTemperatureInfo> dailyTemperatures;

    private Integer maxTemperature;
    private Double averageTemperature;

    public MonthTemperatureStatsImpl() {
        dailyTemperatures = new LinkedHashMap<>();
    }

    public void updateTemperatureInfo(DayTemperatureInfo info) {
        int day = info.getDay();
        int temperature = info.getTemperature();

        dailyTemperatures.put(day, info);

        if (maxTemperature == null || temperature > maxTemperature) {
            maxTemperature = temperature;
        }

        if (averageTemperature == null) {
            averageTemperature = (double) temperature;
        } else {
            int numberOfDays = dailyTemperatures.size();
            int numberOfDaysBeforeUpdate = numberOfDays - 1;
            double sumBeforeUpdate = averageTemperature * numberOfDaysBeforeUpdate;

            averageTemperature = (sumBeforeUpdate + temperature) / numberOfDays;
        }
    }

    public Double getAverageTemperature() {
        return averageTemperature;
    }

    public Integer getMaxTemperature() {
        return maxTemperature;
    }

    public DayTemperatureInfo getDayTemperatureInfo(int day) {
        return dailyTemperatures.get(day);
    }

    public Collection<DayTemperatureInfo> getAllValues() {
        return dailyTemperatures.values();
    }
}
