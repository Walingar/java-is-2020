package impl.weather;

import api.weather.DayTemperatureInfo;

import java.time.Month;

public final class ImmutableDayTemperatureInfo implements DayTemperatureInfo {
    private final int day;
    private final Month month;
    private final int temperature;

    public ImmutableDayTemperatureInfo(final int day, final Month month, final int temperature) {
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
