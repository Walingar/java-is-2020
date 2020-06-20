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

        for (var inputString : rawData) {
            var blankSplit = inputString.split("[ ]");
            var temperature = Integer.parseInt(blankSplit[1]);
            var pointSplit = blankSplit[0].split("[.]");
            var day = Integer.parseInt(pointSplit[0]);
            var month = Month.of(Integer.parseInt(pointSplit[1]));

            DayTemperatureInfo inputItem = new DayTemperatureInfoImpl(day, month, temperature);
            yearTemperatureStats.updateStats(inputItem);
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
