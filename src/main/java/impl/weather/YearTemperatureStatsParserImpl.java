package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.time.Month;
import java.util.Collection;

public class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {

    @Override
    public YearTemperatureStats parse(Collection < String > rawData) {
        YearTemperatureStats status = new YearTemperatureStatsImpl();
        for (String input: rawData) {
            var split = input.split("[. ]");
            var day = Integer.parseInt(split[0]);
            var month = Month.of(Integer.parseInt(split[1]));
            var temperature = Integer.parseInt(split[2]);
            DayTemperatureInfo newDay = new DayTemperatureInfoImpl(day, month, temperature);
            status.updateStats(newDay);
        }
        return status;
    }
}