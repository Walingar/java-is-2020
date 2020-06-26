package impl.weather;

import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.time.Month;
import java.util.Collection;
import java.util.Collections;

public class YearTemperatureStatsParserFactory {
    public static YearTemperatureStatsParser getInstance() {
        return new YearTemperatureStatsParserImpl();
    }
}