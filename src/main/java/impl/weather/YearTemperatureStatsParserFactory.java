package impl.weather;

import api.weather.YearTemperatureStatsParser;
import impl.weather.impl.YearTemperatureStatsParserImpl;

public class YearTemperatureStatsParserFactory {
    public static YearTemperatureStatsParser getInstance() {
        return new YearTemperatureStatsParserImpl();
    }
}