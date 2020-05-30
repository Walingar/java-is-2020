package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private Map<Integer, MonthTemperatureStatsImpl> monthlyTemperatures;

    public YearTemperatureStatsImpl() {
        monthlyTemperatures = new HashMap<>();
    }

    @Override
    public void updateStats(DayTemperatureInfo info) {
        int monthNumber = info.getMonth().getValue();
        MonthTemperatureStatsImpl monthStat = GetOrCreateMonthTemperatureStat(monthNumber);

        monthStat.updateTemperatureInfo(info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        int monthNumber = month.getValue();
        MonthTemperatureStatsImpl monthStat = GetOrCreateMonthTemperatureStat(monthNumber);

        return monthStat.getAverageTemperature();
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> monthlyMaxTemperatures = new HashMap<>();
        Set<Map.Entry<Integer, MonthTemperatureStatsImpl>> knownMonths = monthlyTemperatures.entrySet();

        for (Map.Entry<Integer, MonthTemperatureStatsImpl> monthStat : knownMonths) {
            Month month = Month.of(monthStat.getKey());
            int maxTemperature = monthStat.getValue().getMaxTemperature();

            monthlyMaxTemperatures.put(month, maxTemperature);
        }

        return monthlyMaxTemperatures;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        int monthNumber = month.getValue();
        MonthTemperatureStatsImpl monthStat = GetOrCreateMonthTemperatureStat(monthNumber);

        Collection<DayTemperatureInfo> dayTemperatureInfos = monthStat.getAllValues();
        List<DayTemperatureInfo> listForSorting = new ArrayList<>(dayTemperatureInfos);

        Comparator<DayTemperatureInfo> comparator = Comparator.comparing(DayTemperatureInfo::getTemperature);
        listForSorting.sort(comparator);

        return listForSorting;
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        int monthNumber = month.getValue();
        MonthTemperatureStatsImpl monthStat = GetOrCreateMonthTemperatureStat(monthNumber);

        return monthStat.getDayTemperatureInfo(day);
    }

    private MonthTemperatureStatsImpl GetOrCreateMonthTemperatureStat(int month) {
        MonthTemperatureStatsImpl monthTemperatureStats = monthlyTemperatures.get(month);
        if (monthTemperatureStats == null) {
            monthTemperatureStats = new MonthTemperatureStatsImpl();
            monthlyTemperatures.put(month, monthTemperatureStats);
        }

        return monthTemperatureStats;
    }
}
