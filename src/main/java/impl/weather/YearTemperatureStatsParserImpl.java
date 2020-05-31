package impl.weather;

import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.util.Collection;

public class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {
    @Override
    public YearTemperatureStats parse(Collection<String> rawData) {
        if (rawData == null) {
            throw new IllegalArgumentException("`rawData` must be not null");
        }

        var yearTemperatureStats = YearTemperatureStatsFactory.getInstance();

        for (var data : rawData) {
            String[] tokens = data.split("[. ]");
            int day = Integer.parseInt(tokens[0]);
            int month = Integer.parseInt(tokens[1]);
            int temperature = Integer.parseInt(tokens[2]);
            yearTemperatureStats.updateStats(new DayTemperatureInfoImpl(
                    day,
                    month,
                    temperature
            ));
        }

        return yearTemperatureStats;
    }
}
