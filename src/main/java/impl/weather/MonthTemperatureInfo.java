package impl.weather;

import api.weather.DayTemperatureInfo;

import java.util.*;

public class MonthTemperatureInfo {
    private Integer maxTemperature;
    private Double averageTemperature;

    private final Map<Integer, DayTemperatureInfo> daysTemperatureInfo = new LinkedHashMap<>();

    public void updateMonthTemperatureInfo(DayTemperatureInfo info) {
        int day = info.getDay();
        int temperature = info.getTemperature();

        daysTemperatureInfo.put(day, info);

        if (averageTemperature == null) {
            averageTemperature = (double) temperature;
        } else {
            int dayCounter = daysTemperatureInfo.size();
            averageTemperature = ((dayCounter - 1) * averageTemperature + temperature) / dayCounter;
        }

        if (maxTemperature == null || temperature > maxTemperature) {
            maxTemperature = temperature;
        }
    }

    public DayTemperatureInfo getDayInfo(int day) {
        return daysTemperatureInfo.get(day);
    }

    public Double getAverageTemperature() {
        return averageTemperature;
    }

    public Integer getMaxTemperature() {
        return maxTemperature;
    }

    public List<DayTemperatureInfo> getSortedDayList() {
        List<DayTemperatureInfo> sortedDayList = new ArrayList<>(daysTemperatureInfo.values());
        sortedDayList.sort(java.util.Comparator.comparing(DayTemperatureInfo::getTemperature));
        return sortedDayList;
    }
}
