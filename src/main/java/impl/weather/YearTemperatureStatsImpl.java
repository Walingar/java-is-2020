package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    static private class MonthTemperatureItem {
        public MonthTemperatureItem(Double avg, Integer max, Integer count) {
            this.avg = avg;
            this.max = max;
            this.count = count;
        }

        public Double avg;
        public Integer max;
        public Integer count;
    }

    static private class OrderedDayTemperatureInfo {
        public OrderedDayTemperatureInfo(DayTemperatureInfo info, int order) {
            this.info = info;
            this.order = order;
        }

        public DayTemperatureInfo info;
        public int order;
    }

    static public class OrderedDayTemperatureInfoComparator implements Comparator<OrderedDayTemperatureInfo> {
        public int compare(OrderedDayTemperatureInfo a, OrderedDayTemperatureInfo b) {
            if (a.info.getTemperature() < b.info.getTemperature()) {
                return -1;
            }

            if (a.info.getTemperature() > b.info.getTemperature()) {
                return 1;
            }

            return Integer.compare(a.order, b.order);
        }
    }

    private Map<Month, MonthTemperatureItem> tempInfoPerMon;
    private Map<Month, Map<Integer, OrderedDayTemperatureInfo>> tempPerDay;
    private int add_order_count;

    public YearTemperatureStatsImpl() {
        tempInfoPerMon = new HashMap<>();
        tempPerDay = new HashMap<>();
        add_order_count = 0;
    }

    public void updateStats(DayTemperatureInfo info) {
        MonthTemperatureItem tempInfo = tempInfoPerMon.get(info.getMonth());

        if (tempInfo == null) {
            tempInfo = new MonthTemperatureItem(0.0, null, 0);
        }

        tempInfo.avg = (tempInfo.avg * tempInfo.count + info.getTemperature()) / (tempInfo.count + 1);
        tempInfo.max = (tempInfo.max == null || tempInfo.max < info.getTemperature()) ? info.getTemperature()
                                                                                         : tempInfo.max;
        tempInfo.count++;
        tempInfoPerMon.put(info.getMonth(), tempInfo);

        Map<Integer, OrderedDayTemperatureInfo> monthInfo = tempPerDay.get(info.getMonth());

        if (monthInfo == null) {
            monthInfo = new HashMap<>();
        }

        monthInfo.put(info.getDay(), new OrderedDayTemperatureInfo(info, add_order_count));
        add_order_count++;
        tempPerDay.put(info.getMonth(), monthInfo);

    }

    public Double getAverageTemperature(Month month) {
        MonthTemperatureItem tempInfo = tempInfoPerMon.get(month);

        if (tempInfo == null) {
            return null;
        }

        return tempInfo.avg;
    }

    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> map = new HashMap<>();

        for (Map.Entry<Month, MonthTemperatureItem> entry : tempInfoPerMon.entrySet()) {
            map.put(entry.getKey(), entry.getValue().max);
        }

        return map;
    }

    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        List<DayTemperatureInfo> list = new LinkedList<>();
        Map<Integer, OrderedDayTemperatureInfo> monthInfo = tempPerDay.get(month);

        if (monthInfo == null) {
            return list;
        }

        Set<OrderedDayTemperatureInfo> sorted_info =
                new TreeSet<>(new OrderedDayTemperatureInfoComparator());

        sorted_info.addAll(monthInfo.values());

        for (OrderedDayTemperatureInfo info : sorted_info) {
            list.add(info.info);
        }

        return list;
    }

    public DayTemperatureInfo getTemperature(int day, Month month) {
        Map<Integer, OrderedDayTemperatureInfo> monthInfo = tempPerDay.get(month);

        if (monthInfo == null) {
            return null;
        }

        OrderedDayTemperatureInfo day_info = monthInfo.get(day);

        if (day_info == null) {
            return null;
        }

        return day_info.info;
    }
}