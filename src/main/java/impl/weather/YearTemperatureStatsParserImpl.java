package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;
import api.weather.YearTemperatureStatsParser;

import java.time.Month;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class YearTemperatureStatsParserImpl implements YearTemperatureStatsParser {
    private static final Pattern PATTERN = Pattern.compile("(?<day>[0-9]{2})\\.(?<month>.*) (?<temperature>-?[0-9]+)");

    // IMHO:
    // Parser should not be responsible to update stats as well as parsing raw data. It breaks SRP.
    // This make it more difficult to test parsing logic (that converts string to DayTemperatureInfo);
    // YearTemperatureStats is not a POJO to be parsed.
    @Override
    public YearTemperatureStats parse(final Collection<String> rawData) {
        // This is not good idea to directly create (or get) instances using static point.
        // It breaks IOC principle. This makes writing tests more difficult.
        // However, this is the simplest way considering given code frame for the task.
        YearTemperatureStats stats = YearTemperatureStatsFactory.getInstance();
        for (String rawString : rawData) {
            stats.updateStats(parse(rawString));
        }
        return stats;
    }

    // IMHO:
    // This is the only what the parser should do.
    private DayTemperatureInfo parse(final String rawString) {
        Matcher matcher = PATTERN.matcher(rawString);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("string " + rawString
                    + " could not be parsed because it doesn't match to pattern \""
                    + "<day>.<month> <temperature>" + "\", example: " + "\"1.12 3\" - 3 degrees Celsius on 1 December");
        }
        // The code bellow may throw a bunch of different exceptions. It is better to generalize them and
        // create the contract describing possible exceptions to be thrown.
        int day = Integer.parseInt(matcher.group("day"));
        Month month = Month.of(Integer.parseInt(matcher.group("month")));
        int temperature = Integer.parseInt(matcher.group("temperature"));
        return new ImmutableDayTemperatureInfo(day, month, temperature);
    }
}
