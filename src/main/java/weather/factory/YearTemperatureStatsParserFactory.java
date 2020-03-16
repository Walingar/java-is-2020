package weather.factory;

import weather.api.YearTemperatureStatsParser;
import weather.api.impl.YearTemperatureStatsParserImpl;

public class YearTemperatureStatsParserFactory {

    public static YearTemperatureStatsParser getInstance() {
        return new YearTemperatureStatsParserImpl();
    }
}