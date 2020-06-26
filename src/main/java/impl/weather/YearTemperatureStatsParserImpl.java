package impl.weather;

import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.time.Month;
import java.util.Collection;

public class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {
    @Override
    public YearTemperatureStats parse(Collection<String> rawData) {

        YearTemperatureStats yearTemperatureStats = YearTemperatureStatsFactory.getInstance();

        for (String raw : rawData) {
            String[] data = raw.split("[. ]");
            yearTemperatureStats.updateStats(new DayTemperatureInfoImpl(Integer.parseInt(data[0]),
                    Month.of(Integer.parseInt(data[1])), Integer.parseInt(data[2])));
        }
        return yearTemperatureStats;
    }
}
