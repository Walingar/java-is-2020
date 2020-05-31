package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.MonthTemperatureStats;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonthTemperatureStatsImpl implements MonthTemperatureStats {
    private Month month;
    private Map<Integer, DayTemperatureInfo> dayNumbersToTemperatureInfo;
    private Integer maxTemperature;
    private Double averageTemperature;

    public MonthTemperatureStatsImpl(Month month) {
        this.month = month;
        dayNumbersToTemperatureInfo = new HashMap<>();
    }

    @Override
    public void updateStats(DayTemperatureInfo info) {
        var infoMonth = info.getMonth();
        if (infoMonth != month) {
            throw new IllegalArgumentException("Incorrect month value.");
        }
        int infoDay = info.getDay();
        int infoTemperature = info.getTemperature();

        dayNumbersToTemperatureInfo.put(infoDay, info);
        updateMaxAndAverageTemperatures(infoTemperature);
    }

    private void updateMaxAndAverageTemperatures(int addedTemperature) {
        if (maxTemperature == null || addedTemperature > maxTemperature) {
            maxTemperature = addedTemperature;
        }
        if (averageTemperature == null) {
            averageTemperature = (double) addedTemperature;
        } else {
            int numberOfDays = dayNumbersToTemperatureInfo.size();
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
    public List<DayTemperatureInfo> getTemperatureInfoList() {
        return new ArrayList<>(dayNumbersToTemperatureInfo.values());
    }

    @Override
    public DayTemperatureInfo getTemperature(int day) {
        return dayNumbersToTemperatureInfo.get(day);
    }
}
