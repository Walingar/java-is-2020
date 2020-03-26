package impl.weather;


import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.time.Month;
import java.util.Collection;

public class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {

    @Override
    public YearTemperatureStats parse(Collection<String> rawData) {
        var stats = new YearTemperatureStatsImpl();
        rawData.forEach(entry -> {
            var dayTemperature = entry.split("\\s");
            var day = Integer.parseInt(dayTemperature[0].split("\\.")[0]);
            var month = Month.of(Integer.parseInt(dayTemperature[0].split("\\.")[1]));
            var temperature = Integer.parseInt(dayTemperature[1]);
            stats.updateStats(new DayTemperatureInfoImpl(day, month, temperature));
        });

        return stats;
    }

    private class DayTemperatureInfoImpl implements DayTemperatureInfo {

        private int day;
        private Month month;
        private int temperature;

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

