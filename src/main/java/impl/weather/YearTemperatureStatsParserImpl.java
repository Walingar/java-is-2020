package impl.weather;

import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.time.Month;
import java.util.Collection;

public class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {
    @Override
    public YearTemperatureStats parse(Collection<String> rawData) {
        YearTemperatureStats result = YearTemperatureStatsFactory.getInstance();
        for (String str : rawData) {
            var rawStr = str.split("[. ]");
            result.updateStats(new DayTemperatureInfoImpl(Integer.parseInt(rawStr[0]), Month.of(Integer.parseInt(rawStr[1])), Integer.parseInt((rawStr[2]))));
        }
        return result;
    }
}
