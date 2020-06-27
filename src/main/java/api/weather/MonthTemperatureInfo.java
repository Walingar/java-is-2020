package api.weather;

import java.util.Map;

public interface MonthTemperatureInfo {

    Integer getTemperatureDay(Integer day);

    DayTemperatureInfo getDayInfo(Integer day);

    Double getAverageTemperature();

    Double getMaxTemperature();

    Map<Integer, DayTemperatureInfo> getMonthData();

    void updateStats(DayTemperatureInfo info);


}
