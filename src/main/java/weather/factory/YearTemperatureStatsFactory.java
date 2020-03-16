package weather.factory;

import weather.api.YearTemperatureStats;
import weather.api.impl.YearTemperatureStatsImpl;

public class YearTemperatureStatsFactory {

    public static YearTemperatureStats getInstance() {
        return new YearTemperatureStatsImpl();
    }
}