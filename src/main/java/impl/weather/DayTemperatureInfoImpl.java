package impl.weather;

import api.weather.DayTemperatureInfo;

import java.time.Month;

public final class DayTemperatureInfoImpl implements DayTemperatureInfo {
    private int day;
    private Month month;

    private int temperature;

    public DayTemperatureInfoImpl(int day, int month, int temperature) {
        this.day = day;
        this.month = Month.of(month);
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
