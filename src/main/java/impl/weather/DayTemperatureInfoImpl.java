package impl.weather;

import api.weather.DayTemperatureInfo;

import java.time.Month;

public class DayTemperatureInfoImpl implements DayTemperatureInfo {
    private final int day;
    private final Month month;
    private final int temperature;

    public DayTemperatureInfoImpl(int day, int month, int temperature) {
        if (month < 1 || month > 12)
            throw new IllegalArgumentException("Month number must be in [1..12]");

        int maxDaysInMonth = 0;
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            maxDaysInMonth = 30;
        } else if (month == 2) {
            maxDaysInMonth = 29;
        } else {
            maxDaysInMonth = 31;
        }
        if (day > maxDaysInMonth) {
            throw new IllegalArgumentException(
                    String.format("Incorrect day of month. Maximum value is %d", maxDaysInMonth));
        }

        this.day = day;
        this.month = Month.of(month);
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
