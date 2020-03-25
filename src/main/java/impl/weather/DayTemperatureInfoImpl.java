package impl.weather;

import java.time.Month;

import api.weather.DayTemperatureInfo;

class DayTemperatureInfoImpl implements DayTemperatureInfo {
    private int _day;
    private Month _month;
    private int _temperature;

    DayTemperatureInfoImpl(int day, Month month, int temperature) {
        this._day = day;
        this._month = month;
        this._temperature = temperature;
    }

    @Override
    public int getDay() {
        return _day;
    }

    @Override
    public Month getMonth() {
        return _month;
    }

    @Override
    public int getTemperature() {
        return _temperature;
    }

}
