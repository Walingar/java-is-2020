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
            int dot = str.indexOf('.');
            int space = str.indexOf(' ');

            int day = Integer.parseInt(str.substring(0, dot));
            Month month = Month.of(Integer.parseInt(str.substring(dot + 1, space)));
            int temperature = Integer.parseInt(str.substring(space + 1));
            result.updateStats(new DayTemperatureInfoImpl(day, month, temperature));
        }
        return result;
    }
}
