package api.weather;

import java.time.Month;
import java.util.List;

public interface MonthTemperatureStats {
    void updateStats(DayTemperatureInfo info);

    Double getAverageTemperature();

    int getMaxTemperature();

    List<DayTemperatureInfo> getTemperatureInfoList();

    DayTemperatureInfo getTemperature(int day);
}
