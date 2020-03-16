package weather.api.impl;

import java.time.Month;
import java.util.Collection;
import weather.api.YearTemperatureStats;
import weather.api.YearTemperatureStatsParser;
import weather.factory.DayTemperatureInfoFactory;
import weather.factory.YearTemperatureStatsFactory;

public class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {

    @Override
    public YearTemperatureStats parse(Collection<String> rawData) {
        YearTemperatureStats yearTemperatureStats = YearTemperatureStatsFactory.getInstance();

        for (String dateStr : rawData) {
            String[] processingData = dateStr.replace('.', ' ').split(" ");
            yearTemperatureStats.updateStats(
                DayTemperatureInfoFactory.getInstance(
                    Integer.parseInt(processingData[0]),
                    Month.of(Integer.parseInt(processingData[1])),
                    Integer.parseInt(processingData[2])
                )
            );
        }

        return yearTemperatureStats;
    }
}