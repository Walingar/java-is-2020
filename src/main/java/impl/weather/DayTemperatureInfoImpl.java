package impl.weather;

import api.weather.DayTemperatureInfo;

import java.time.Month;

public class DayTemperatureInfoImpl implements DayTemperatureInfo {
    private final int day;
    private final Month month;
    private final int temperature;

    public DayTemperatureInfoImpl(int day, Month month, int temperature) {
        this.day = day;
        this.month = month;
        this.temperature = temperature;
    }

    public int getDay() {
        return day;
    }

    public Month getMonth() {
        return month;
    }

    public int getTemperature() {
        return temperature;
    }
}
