package impl.weather;

import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.time.Month;
import java.util.Collection;

public class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {
    @Override
    public YearTemperatureStats parse(Collection<String> rawData) {
        YearTemperatureStats yearTemperatureStats = YearTemperatureStatsFactory.getInstance();

        rawData.forEach(raw -> {
            var str = raw.split("[. ]");
            yearTemperatureStats.updateStats(new DayTemperatureInfoImpl(Integer.parseInt(str[0]),
                    Month.of(Integer.parseInt(str[1])), Integer.parseInt(str[2])));
        });
        return yearTemperatureStats;
    }
}
