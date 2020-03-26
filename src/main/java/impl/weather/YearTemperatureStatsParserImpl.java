package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.time.Month;
import java.util.Collection;

public class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {
    @Override
    public YearTemperatureStats parse(Collection<String> rawData) {
        YearTemperatureStats output = new YearTemperatureStatsImpl();

        for (var i : rawData) {
            var blankSplit = i.split("[ ]");
            var temperature = Integer.parseInt(blankSplit[1]);
            var pointSplit = blankSplit[0].split("[.]");
            var day = Integer.parseInt(pointSplit[0]);
            var month = Month.of(Integer.parseInt(pointSplit[1]));

            DayTemperatureInfo inputItem = new DayTemperatureInfoImpl(day, month, temperature);
            output.updateStats(inputItem);
        }
        return output;
    }
}
