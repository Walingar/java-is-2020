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
            var tokens = rawDayData.split(" ");
            if (tokens.length != 2) {
                throw getWrongFormatException(rawData);
            }

            var dateTokens = tokens[0].split("\\.");
            if (dateTokens.length != 2) {
                throw getWrongFormatException(rawData);
            }

            var day = Integer.parseInt(dateTokens[0]);
            var month = Integer.parseInt(dateTokens[1]);
            var temperature = Integer.parseInt(tokens[1]);
            temperatureStats.updateStats(new DayTemperatureInfoImpl(day, month, temperature));
        }

        return temperatureStats;
    }

    private static RuntimeException getWrongFormatException(Collection<String> rawData) {
        return new IllegalArgumentException("\"" + rawData + "\" does not follow \"day.month temperature\" pattern");
    }
}
