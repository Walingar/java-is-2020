package api.weather;

import java.time.Month;
import java.util.List;
import java.util.Map;

public interface YearTemperatureStats {
    void updateStats(DayTemperatureInfo info);

    Double getAverageTemperature(Month month);

    Map<Month, Integer> getMaxTemperature();

    List<DayTemperatureInfo> getSortedTemperature(Month month);

    DayTemperatureInfo getTemperature(int day, Month month);
}