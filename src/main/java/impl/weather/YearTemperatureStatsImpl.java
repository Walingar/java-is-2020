package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public final class YearTemperatureStatsImpl implements YearTemperatureStats {
    private final Map<Month, Map<Integer, DayTemperatureInfo>> data = new EnumMap<>(Month.class);
    private final Map<Month, CacheEntry> cache = new EnumMap<>(Month.class);

    public YearTemperatureStatsImpl() {
        for (Month month : Month.values()) {
            cache.put(month, new CacheEntry());
        }
    }

    @Override
    public void updateStats(final DayTemperatureInfo info) {
        Objects.requireNonNull(info);
        Month month = info.getMonth();
        Map<Integer, DayTemperatureInfo> temperaturePerDay = data.computeIfAbsent(month, m -> new LinkedHashMap<>());
        if (temperaturePerDay.containsKey(info.getDay())) {
            throw new IllegalArgumentException("stats for " + info.getDay() + " " + month
                    + " has been already counted, update is not available");
        }
        temperaturePerDay.put(info.getDay(), info);
        cache.get(month).update(info.getTemperature());
    }

    @Override
    public Double getAverageTemperature(final Month month) {
        Objects.requireNonNull(month);
        CacheEntry cacheEntry = cache.get(month);
        if (!cacheEntry.hasData()) {
            return null;
        }
        return 1.0 * cacheEntry.sumTemperature / cacheEntry.daysWithinStats;
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> max = new EnumMap<>(Month.class);
        for (Map.Entry<Month, CacheEntry> entry : cache.entrySet()) {
            if (entry.getValue().hasData()) {
                max.put(entry.getKey(), entry.getValue().maxTemperature);
            }
        }
        return max;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(final Month month) {
        Objects.requireNonNull(month);
        return data.getOrDefault(month, Collections.emptyMap())
                .values().stream()
                .sorted(Comparator.comparingInt(DayTemperatureInfo::getTemperature))
                .collect(Collectors.toList());
    }

    @Override
    public DayTemperatureInfo getTemperature(final int day, final Month month) {
        Objects.requireNonNull(month);
        return Optional.ofNullable(data.get(month))
                .map(temperaturePerDay -> temperaturePerDay.get(day))
                .orElse(null);
    }

    private static final class CacheEntry {
        private int maxTemperature;
        private int sumTemperature;
        private int daysWithinStats;

        private void update(final int temperature) {
            if (!hasData()) {
                maxTemperature = temperature;
            } else {
                maxTemperature = Math.max(maxTemperature, temperature);
            }
            sumTemperature += temperature;
            daysWithinStats++;
        }

        private boolean hasData() {
            return daysWithinStats > 0;
        }
    }
}
