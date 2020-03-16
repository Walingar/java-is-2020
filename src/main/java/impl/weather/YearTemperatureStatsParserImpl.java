package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.util.Collection;

public class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {

    @Override
    public YearTemperatureStats parse(Collection<String> rawData) {
        YearTemperatureStatsImpl stats = new YearTemperatureStatsImpl();
        for (String data : rawData) {
            String[] tokens = data.split("[. ]");
            DayTemperatureInfo day = new DayTemperatureInfoImpl(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
            stats.updateStats(day);
        }
        return stats;
    }
}
