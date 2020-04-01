package api.weather;

import java.util.Map;

public interface MonthInfo {
    void addDay(DayTemperatureInfo info);

    Double getAverage();

    Integer getMaximum();

    DayTemperatureInfo getDay(Integer day);

    Map<Integer, DayTemperatureInfo> getDays();
}
