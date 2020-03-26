package impl.weather;

import api.weather.YearTemperatureStatsParser;
import api.weather.YearTemperatureStats;

import java.util.Collection;
import java.util.Objects;

public class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {

    @Override
    public YearTemperatureStats parse(Collection<String> rawData) {
        Objects.requireNonNull(rawData, "Input data is null");

        YearTemperatureStats temperatureStats = YearTemperatureStatsFactory.getInstance();

        for (var rawDayData : rawData) {
            var splittedTokens = rawDayData.split(" ");
            if (splittedTokens.length != 2) {
                throw new IllegalArgumentException("wrong data format");
            }

            var splittedDate = splittedTokens[0].split("\\.");
            if (splittedDate.length != 2) {
                throw new IllegalArgumentException("wrong date format");
            }
            try {
                var day = Integer.parseInt(splittedDate[0]);
                var month = Integer.parseInt(splittedDate[1]);
                var temperature = Integer.parseInt(splittedTokens[1]);
                temperatureStats.updateStats(new DayTemperatureInfoImpl(day, month, temperature));
            } catch (NumberFormatException e) {
                throw new ParseError("Illegal number format format");
            }
        }
        return temperatureStats;
    }
}
