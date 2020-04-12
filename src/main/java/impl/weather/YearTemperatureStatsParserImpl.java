package impl.weather;

import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.util.Collection;
import java.util.Objects;

public class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {
    @Override
    public YearTemperatureStats parse(Collection<String> rawData) {
        Objects.requireNonNull(rawData,"Data could not be null");

        var result = YearTemperatureStatsFactory.getInstance();
        for (var rawDay : rawData) {
            var tokens = rawDay.split(" ");
            if (tokens.length == 0) {
                throw new IllegalArgumentException("Invalid data format");
            }

            var splittedDate = tokens[0].split("\\.");
            try {
                var day = Integer.parseInt(splittedDate[0]);
                var month = Integer.parseInt(splittedDate[1]);
                var temperature = Integer.parseInt(tokens[1]);
                result.updateStats(new DayTemperature(day, month, temperature));
            } catch (NumberFormatException ex) {
                throw new NumberFormatException("Invalid number format");
            }
        }
        return result;
    }
}
