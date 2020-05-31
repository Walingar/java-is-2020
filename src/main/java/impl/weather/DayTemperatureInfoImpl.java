package impl.weather;

import java.time.Month;

import api.weather.DayTemperatureInfo;

final class DayTemperatureInfoImpl implements DayTemperatureInfo {
    private int day;
    private Month month;
    private int temperature;

    DayTemperatureInfoImpl(int day, Month month, int temperature) {
        this.day = day;
        this.month = month;
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
