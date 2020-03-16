package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.time.Month;
import java.util.Collection;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {

    private static final Pattern PATTERN = Pattern.compile("([\\d]+).([\\d]+) (-?[\\d]+)");

    @Override
    public YearTemperatureStats parse(Collection<String> rawData) {
        var stats = YearTemperatureStatsFactory.getInstance();
        rawData.forEach(entry -> {
            var info = parseEntry(entry);
            stats.updateStats(info);
        });
        return stats;
    }

    private DayTemperatureInfo parseEntry(String entry) {
        var matcher = PATTERN.matcher(entry);
        if (!matcher.find()) {
            throw new RuntimeException("Invalid entry");
        }
        var day = parseInt(matcher.group(1));
        var month = Month.of(parseInt(matcher.group(2)));
        var temperature = parseInt(matcher.group(3));
        return new DayTemperatureInfoImpl(day, month, temperature);
    }

    private static class DayTemperatureInfoImpl implements DayTemperatureInfo {

        private final int day;
        private final Month month;
        private final int temperature;

        public DayTemperatureInfoImpl(int day, Month month, int temperature) {
            this.day = day;
            this.month = month;
            this.temperature = temperature;
        }

        @Override
        public int getDay() {
            return day;
        }

        @Override
        public Month getMonth() {
            return month;
        }

        @Override
        public int getTemperature() {
            return temperature;
        }
    }
}
