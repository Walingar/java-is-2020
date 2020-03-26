package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.time.Month;
import java.util.Collection;

public class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {
    @Override
    public YearTemperatureStats parse(Collection<String> rawData) {

        YearTemperatureStats yearStats = new YearTemperatureStatsImpl();

        for (var rowDataItem : rawData) {

            var parts = rowDataItem.split("[. ]");
            int temperature = Integer.parseInt(parts[2]);
            int day = Integer.parseInt(parts[0]);
            Month month = Month.of(Integer.parseInt(parts[1]));

            DayTemperatureInfo inputDay = new DayTemperatureInfoImpl(day, month, temperature);
            yearStats.updateStats(inputDay);

        }
        return yearStats;
    }
}
