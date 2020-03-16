package weather.api;

import java.time.Month;

public interface DayTemperatureInfo {
    int getDay();

    Month getMonth();

    int getTemperature();
}