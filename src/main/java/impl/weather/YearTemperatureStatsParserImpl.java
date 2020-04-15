package impl.weather;

import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.time.Month;
import java.util.Collection;
import java.util.regex.*;

public class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {
    public YearTemperatureStats parse(Collection<String> rawData) {
        YearTemperatureStats stats = new YearTemperatureStatsImpl();
        Pattern p = Pattern.compile("\\.| ");

        for (String data : rawData) {
            String[] data_list = p.split(data);
            int day = Integer.parseInt(data_list[0]);
            Month month = Month.of(Integer.parseInt(data_list[1]));
            int temperature = Integer.parseInt(data_list[2]);

            stats.updateStats(new DayTemperatureInfoImpl(month, day, temperature));
        }

        return stats;
    }
}