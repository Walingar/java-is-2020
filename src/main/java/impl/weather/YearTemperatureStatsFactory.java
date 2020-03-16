package impl.weather;

import api.weather.YearTemperatureStats;
import impl.weather.impl.YearTemperatureStatsImpl;

public class YearTemperatureStatsFactory {
    public static YearTemperatureStats getInstance() {
        return new YearTemperatureStatsImpl();
    }
}