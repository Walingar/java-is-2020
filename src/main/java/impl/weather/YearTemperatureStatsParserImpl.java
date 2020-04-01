package impl.weather;

import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.util.Collection;
import java.util.Objects;

public class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {
    @Override
    public YearTemperatureStats parse(Collection<String> rawData) {
        Objects.requireNonNull(rawData, "RawData should not be null");

        var temperatureStats = YearTemperatureStatsFactory.getInstance();

        for (var rawDayData : rawData) {
            var tokens = splitIntoTokens(rawDayData, " ");
            var dateTokens = splitIntoTokens(tokens[0], "\\.");

            var day = Integer.parseInt(dateTokens[0]);
            var month = Integer.parseInt(dateTokens[1]);
            var temperature = Integer.parseInt(tokens[1]);
            temperatureStats.updateStats(new DayTemperatureInfoImpl(day, month, temperature));
        }

        return temperatureStats;
    }

    private static String[] splitIntoTokens(String rawDayData, String pattern) {
        var tokens = rawDayData.split(pattern);
        if (tokens.length != 2) {
            throw new IllegalArgumentException(
                    "\"" + rawDayData + "\" does not follow \"day.month temperature\" pattern");
        }
        return tokens;
    }
}
