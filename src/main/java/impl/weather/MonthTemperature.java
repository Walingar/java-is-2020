package impl.weather;

import api.weather.DayTemperatureInfo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.toList;

public class MonthTemperature {

    private final Map<Integer, DayTemperatureInfo> dayInfos = new LinkedHashMap<>();
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

    public List<DayTemperatureInfo> getSortedTemperature() {
        return dayInfos.values().stream()
                .sorted(comparingDouble(DayTemperatureInfo::getTemperature))
                .collect(toList());
    }
}
