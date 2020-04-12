package impl.weather;

import api.weather.YearTemperatureStats;

public class YearTemperatureStatsFactory {
    public static YearTemperatureStats getInstance() {
        return new YearTemperatureStatsImpl();
    }
}