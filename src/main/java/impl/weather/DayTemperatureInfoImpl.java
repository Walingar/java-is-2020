package impl.weather;

import api.weather.DayTemperatureInfo;

import java.time.Month;

public class DayTemperatureInfoImpl implements DayTemperatureInfo {
    private Month month;
    private int day;
    private int temperature;

    public DayTemperatureInfoImpl(Month month, int day, int temperature) {
        this.month = month;
        this.day = day;
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