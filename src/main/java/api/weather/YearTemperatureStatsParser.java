package api.weather;

import java.util.Collection;

public interface YearTemperatureStatsParser {
    YearTemperatureStats parse(Collection<String> rawData);
}