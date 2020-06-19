package impl.weather;

import api.weather.DayTemperatureInfo;

import java.time.Month;

public class DayTemperatureInfoImpl implements DayTemperatureInfo {
    private final int day;
    private final Month month;
    private final int temperature;

    public DayTemperatureInfoImpl(int day, int month, int temperature) {
        this.month = Month.of(month);
        if (day > this.month.maxLength()) {
            throw new IllegalArgumentException(String.format("Max amount of days in %s is %d, but was given %d",
                    this.month.toString(), this.month.maxLength(), day));

        }
        this.day = day;
        this.temperature = temperature;
    }

    @Override
    public int getDay() {
        return day;
    };

    @Override
    public Month getMonth() {
        return month;
    };

    @Override
    public int getTemperature() {
        return temperature;
    };
}
