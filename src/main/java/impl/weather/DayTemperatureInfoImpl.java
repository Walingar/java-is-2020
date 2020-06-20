package impl.weather;

import api.weather.DayTemperatureInfo;

import java.time.Month;

public class DayTemperatureInfoImpl implements DayTemperatureInfo {
    private final int day;
    private final Month month;
    private final int temperature;

    DayTemperatureInfoImpl(int day, Month month, int temperature) {
        this.day = day;
        this.month = month;
        this.temperature = temperature;
    }

    @Override
    public Month getMonth() {
        return month;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public int getTemperature() {
        return temperature;
    }
}