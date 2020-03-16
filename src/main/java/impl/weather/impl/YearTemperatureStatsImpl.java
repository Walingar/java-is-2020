package impl.weather.impl;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class YearTemperatureStatsImpl implements YearTemperatureStats {

//    If we just have a list of DayTempInfo we are not gonna have O(const) for update
//    Also we can put Map of actual month days inside but that's overkill
//    We are still pretty well bound by 31 item in the nested list
//    ( we were still well bound by 12 * 31 if we put everythong into one list but whatever =) )
//    Also, we don't really care what kind of underlying list we use here.
    private Map<Month, List<DayTemperatureInfo>> dayTemperatureInfos;

    public YearTemperatureStatsImpl() {
        dayTemperatureInfos = new HashMap<>();
    }

    @Override
    public void updateStats(DayTemperatureInfo newInfo) {
        if (newInfo == null) {
            throw new IllegalArgumentException("Null passed as update data");
        }
        var month = newInfo.getMonth();
//        welp, next line sums up all java collections pretty well
//        Why is in named COMPUTE IF ABSENT, why does it properly return existing value, why it accepts lambda?
        var monthStats = dayTemperatureInfos.computeIfAbsent(month, m -> new ArrayList<>());
        var existingDayInfo = monthStats.stream()
                .filter(dayInfo -> dayInfo.getDay() == newInfo.getDay())
                .findFirst()
                .orElse(null);

        if (existingDayInfo == null) {
            monthStats.add(newInfo);
        } else {
//        picking ArrayList helps us a little here...
            monthStats.set(monthStats.indexOf(existingDayInfo), newInfo);
        }
    }

    @Override
    public Double getAverageTemperature(Month month) {
        if (!dayTemperatureInfos.containsKey(month)) {
            return null;
        }
        return dayTemperatureInfos.get(month).stream()
                .mapToDouble(DayTemperatureInfo::getTemperature)
                .average().getAsDouble(); //Yeah, not sure how can we end up without actual value here
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        return dayTemperatureInfos.entrySet().stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                v -> v.getValue().stream()
                                        .map(DayTemperatureInfo::getTemperature)
                                        .max(Integer::compareTo)
                                        .get())); // Same here, not sure how can we end up with no value, so let it roll
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
//        Here .getOrDefault substitutes for if (!.containsKey()) {return new List}
//        It's slightly slower, but bearable
        return dayTemperatureInfos.getOrDefault(month, new ArrayList<>()).stream() // Maybe move .stream to nextline?
                .sorted(Comparator.comparingInt(DayTemperatureInfo::getTemperature))
                .collect(Collectors.toList());
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        if (!dayTemperatureInfos.containsKey(month)) {
            return null;
        }
        return dayTemperatureInfos.get(month).stream()
                .filter(info -> info.getDay() == day)
                .findFirst()
                .orElse(null);
    }
}
