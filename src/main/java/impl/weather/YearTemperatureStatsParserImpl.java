package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.util.Collection;

public class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {
    @Override
    public YearTemperatureStats parse(Collection<String> rawData) {
        YearTemperatureStats yearStats = new YearTemperatureStatsImpl();

        for (String dataPiece : rawData) {
            String[] dateAndTemperature = dataPiece.split("[ ]");
            String[] dayAndMonth = dateAndTemperature[0].split("[.]");

            int day = Integer.parseInt(dayAndMonth[0]);
            int month = Integer.parseInt(dayAndMonth[1]);
            int temperature = Integer.parseInt(dateAndTemperature[1]);

            DayTemperatureInfo dayTemperatureInfo = new DayTemperatureInfoImpl(day, month, temperature);
            yearStats.updateStats(dayTemperatureInfo);
        }

        return yearStats;
    }
}
