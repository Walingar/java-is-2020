package impl.weather;

import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.util.Collection;

public class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {

    @Override
    public YearTemperatureStats parse(Collection<String> rawData) {

        var temperatureStats = YearTemperatureStatsFactory.getInstance();

        for (String rawDayData : rawData) {
            var dateTemperatureArray = rawDayData.split(" ");
            if (dateTemperatureArray.length != 2) {
                throw new IllegalArgumentException("wrong string format");
            }

            var dateArray = dateTemperatureArray[0].split("\\.");
            if (dateArray.length != 2) {
                throw new IllegalArgumentException("wrong date format");
            }

            int day = Integer.parseInt(dateArray[0]);
            int month = Integer.parseInt(dateArray[1]);
            int temperature = Integer.parseInt(dateTemperatureArray[1]);
            temperatureStats.updateStats(new DayTemperatureInfoImpl(day, month, temperature));
        }

        return temperatureStats;
    }

}
