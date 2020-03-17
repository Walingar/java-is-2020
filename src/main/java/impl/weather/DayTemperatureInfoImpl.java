package impl.weather;

import api.weather.DayTemperatureInfo;

import java.time.Month;
import java.time.MonthDay;

final class DayTemperatureInfoImpl implements DayTemperatureInfo {

    private final MonthDay monthDay;
    private final int temperature;

    DayTemperatureInfoImpl(int day, int month, int temperature) {
        this.monthDay = MonthDay.of(month, day);
        this.temperature = temperature;
    }

    @Override
    public int getDay() {
        return monthDay.getDayOfMonth();
    }

    @Override
    public Month getMonth() {
        return monthDay.getMonth();
    }

    @Override
    public int getTemperature() {
        return temperature;
    }
}
