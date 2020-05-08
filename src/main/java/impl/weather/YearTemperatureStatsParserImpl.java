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

        rawData.forEach(raw -> {
            var str = raw.split("[. ]");
            yearTemperatureStats.updateStats(new DayTemperatureInfoImpl(Integer.parseInt(str[0]),
                    Month.of(Integer.parseInt(str[1])), Integer.parseInt(str[2])));
        });

        return yearTemperatureStats;
    }

    static class DayTemperatureInfoImpl implements DayTemperatureInfo {
        private final int day;
        private final Month month;
        private int temperature;

        DayTemperatureInfoImpl(int day, Month month, int temperature) {
            this.day = day;
            this.month = month;
            this.temperature = temperature;
        }

        @Override
        public Month getMonth() {
            return month;
        }

        @Override
        public int getDay() {
            return day;
        }

        @Override
        public int getTemperature() {
            return temperature;
        }
    }

}
