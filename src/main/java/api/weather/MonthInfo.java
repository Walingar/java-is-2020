package api.weather;

import java.util.List;

public interface MonthInfo {
    void addDay(DayTemperatureInfo info);

    double getAverage();

    int getMaximum();

    DayTemperatureInfo getDay(Integer day);

    List<DayTemperatureInfo> getDays();
}
