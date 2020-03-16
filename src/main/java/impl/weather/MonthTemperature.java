package impl.weather;

import api.weather.DayTemperatureInfo;

import java.util.HashMap;
import java.util.Map;

public class MonthTemperature {

    private final Map<Integer, DayTemperatureInfo> dayInfos = new HashMap<>();
    private Integer maximum;
    private Double average;

    public void add(DayTemperatureInfo info) {
        dayInfos.put(info.getDay(), info);
        updateMaximum(info.getTemperature());
        updateAverage(info.getTemperature(), dayInfos.size());
    }

    private void updateMaximum(int newTemperature) {
        if (maximum == null || newTemperature > maximum) {
            maximum = newTemperature;
        }
    }

    private void updateAverage(int newTemperature, int newCount) {
        if (average == null) {
            average = (double) newTemperature;
        } else {
            average = (average * (newCount - 1) + newTemperature) / newCount;
        }
    }

    public DayTemperatureInfo getDayTemperatureInfo(int day) {
        return dayInfos.get(day);
    }

    public Integer getMaximum() {
        return maximum;
    }

    public Double getAverage() {
        return average;
    }
}
