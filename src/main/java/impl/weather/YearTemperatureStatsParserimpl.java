package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.sql.SQLSyntaxErrorException;
import java.time.Month;
import java.util.Collection;

public class YearTemperatureStatsParserimpl implements YearTemperatureStatsParser {

    @Override
    public YearTemperatureStats parse(Collection<String> rawData) {
        YearTemperatureStats status = new YearTemperatureStatsimpl();
        for (String input : rawData) {
            var split = input.split("[. ]");
            var day = Integer.parseInt(split[0]);
            var month = Month.of(Integer.parseInt(split[1]));
            var temp = Integer.parseInt(split[2]);
            DayTemperatureInfo newday = new DayTemperatureInfoImpl(day, month, temp);
            status.updateStats(newday);
        }
        return status;
    }
}
