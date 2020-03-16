package impl.weather.impl;

import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.time.Month;
import java.util.Collection;

public class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {
    @Override
    public YearTemperatureStats parse(Collection<String> rawData) {
        var stats = new YearTemperatureStatsImpl();
//        Could have wrapped this into stream forEach or something like that, but it was way too ugly
        for (var string : rawData) {
//          We can try to implement full parser here or at least regex-based scanf analog, but that's gonna be too much
            var pieces = string.split("\\.| ");
            var day = Integer.parseInt(pieces[0]);
            var month = Month.of(Integer.parseInt(pieces[1]));
            var temperature = Integer.parseInt(pieces[2]);
            stats.updateStats(new DayTemperatureInfoImpl(month, day, temperature));
        }
        return stats;
    }
}
