package impl.weather;

import api.weather.DayTemperatureInfo;

import java.util.*;
import java.util.stream.Collectors;

public class MonthInfo {
    private final Map < Integer, DayTemperatureInfo > info = new LinkedHashMap < > ();
    private Double averageInfo;
    private Integer maximumTemp;

    public MonthInfo() {
        averageInfo = 0.0;
        maximumTemp = -1000;
    }

    public void set(DayTemperatureInfo dayInfo) {
        var day = dayInfo.getDay();
        var temperature = dayInfo.getTemperature();
        info.put(day, dayInfo);
        if (maximumTemp == null || temperature > maximumTemp) {
            maximumTemp = temperature;
        }
        averageInfo = updateAverage(temperature);
    }

    public Map < Integer, DayTemperatureInfo > getInfo() {
        return info;
    }

    public Double updateAverage(int temperature) {
        int numOfDays = (info.size() - 1);
        var oldAverage = averageInfo;
        averageInfo = ((oldAverage * numOfDays) + temperature) / (numOfDays + 1);
        return averageInfo;
    }

    public Integer getMaxTemperatureOnMonth() {
        return maximumTemp;
    }

    public List < DayTemperatureInfo > getSortedTemp() {
        return info.values().stream()
                .sorted(Comparator.comparingInt(DayTemperatureInfo::getTemperature))
                .collect(Collectors.toUnmodifiableList());
    }

    public Double getAverage() {
        return averageInfo;
    }
}