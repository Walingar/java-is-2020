package impl.weather.impl;

import api.weather.DayTemperatureInfo;

import java.time.Month;

public class DayTemperatureInfoImpl implements DayTemperatureInfo {

    private final Month month;
    private final int day;
    private final int temperature;

    public DayTemperatureInfoImpl(Month month, int day, int temperature) {
        this.month = month;
        this.day = day;
        this.temperature = temperature;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public Month getMonth() {
        return month;
    }

    @Override
    public int getTemperature() {
        return temperature;
    }
}
