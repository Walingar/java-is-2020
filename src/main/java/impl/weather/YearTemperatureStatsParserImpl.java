package impl.weather;

import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.time.Month;
import java.util.Collection;

public class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {

    @Override
    public YearTemperatureStats parse(Collection<String> rawData) {
        YearTemperatureStats result = new YearTemperatureStatsImpl();
        for (String str : rawData) {
            int day = Integer.parseInt(str.substring(0, str.indexOf('.')));
            Month month = Month.of(Integer.parseInt(str.substring(str.indexOf('.') + 1, str.indexOf(' '))));
            int temperature = Integer.parseInt(str.substring(str.indexOf(' ') + 1));
            result.updateStats(new DayTemperatureInfoImpl(day, month, temperature));
        }
        return result;
    }
}
