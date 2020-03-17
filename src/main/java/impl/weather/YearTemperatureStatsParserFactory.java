package impl.weather;

import api.weather.YearTemperatureStatsParser;

public class YearTemperatureStatsParserFactory {
    public static YearTemperatureStatsParser getInstance() {
        return new YearTemperatureStatsParserimpl();
    }
}