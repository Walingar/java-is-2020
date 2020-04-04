package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.time.Month;
import java.util.Collection;

public class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {
    @Override
    public YearTemperatureStats parse(Collection<String> rawData) {
        YearTemperatureStats yearTemperatureStats = new YearTemperatureStatsImpl();
        for (String raw : rawData) {
            int dot = raw.indexOf('.');
            int space = raw.indexOf(' ');

            int day = Integer.parseInt(raw.substring(0, dot));
            Month month = Month.of(Integer.parseInt(raw.substring(dot + 1, space)));
            int temperature = Integer.parseInt(raw.substring(space + 1));

            yearTemperatureStats.updateStats(new DayTemperatureInfoImpl(day, month, temperature));
        }
        return yearTemperatureStats;
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
