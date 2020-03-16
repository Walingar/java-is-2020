package api.weather;

import java.time.Month;

public interface DayTemperatureInfo {
    int getDay();

    Month getMonth();

    int getTemperature();
}