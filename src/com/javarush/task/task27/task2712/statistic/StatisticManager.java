package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticManager {

    private static StatisticManager instance;
    private StatisticStorage statisticStorage = new StatisticStorage();
    //private Set<Cook> cooks = new HashSet<>();

    public static StatisticManager getInstance() {
        if (instance == null) {
            instance = new StatisticManager();
        }
        return instance;
    }

    private StatisticManager() {
    }

    public void register(EventDataRow data) {
        if (data != null) statisticStorage.put(data);
    }

    private class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage = new HashMap<>();

        public StatisticStorage() {
            for (EventType eventType : EventType.values()) {
                storage.put(eventType, new ArrayList<EventDataRow>());
            }
        }

        private void put(EventDataRow data) {
            storage.get(data.getType()).add(data);
        }

        private Map<EventType, List<EventDataRow>> getData() {
            return storage;
        }

        public Map<EventType, List<EventDataRow>> getStorage() {
            return storage;
        }

    }

//    public void register(Cook cook) {
//        cooks.add(cook);
//    }

//    public Set<Cook> getCooks() {
//        return cooks;
//    }

    public TreeMap<Date, Long> AdvertisementProfit(){
        List<EventDataRow> listsVideoEvent  = statisticStorage.getStorage().get(EventType.SELECTED_VIDEOS); // список событий по показу рекламы.
        Set<Date> dateSet = new HashSet<>(); // создаем Set для формирования списка дат
        SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-yyyy", Locale.ENGLISH); //для преобразования даты, чтобы по ней фильтровать суммы за день
        // проходим по списку событий чтобы получить даты
        for (EventDataRow edr :listsVideoEvent){
            dateSet.add(getParse(sdf, edr)); // этим способом мы убираем минуты и оставляем только дату
        }
        //создаем TreeMap где key - дата, а value - сумма стоимости всех показов рекламы в этот день.
        TreeMap<Date, Long> mapVideo = new TreeMap<>(Comparator.reverseOrder()); // сортируем по убыванию
        for (Date date: dateSet){
            long amountSum = 0;
            for (EventDataRow edr :listsVideoEvent){
                VideoSelectedEventDataRow videoSelectedEventDataRow = (VideoSelectedEventDataRow) edr; // приводим событие к видео
                if(getParse(sdf, edr).equals(date)) amountSum+=videoSelectedEventDataRow.getAmount();
            }
            mapVideo.put(date,amountSum);

        }
        return mapVideo;
    }

    private Date getParse(SimpleDateFormat sdf, EventDataRow edr) {
        Date date = null;
        try {
            date = sdf.parse(sdf.format(edr.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Map<String, Map<String, Integer>> getCookStatistic() {
        Map<String, Map<String, Integer>> result = new TreeMap<>(Collections.reverseOrder());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        List<EventDataRow> list = statisticStorage.getData().get(EventType.COOKED_ORDER);

        for (EventDataRow eventDataRow : list) {
            CookedOrderEventDataRow cookedOrderEventDataRow = (CookedOrderEventDataRow) eventDataRow;
            String date = simpleDateFormat.format(cookedOrderEventDataRow.getDate());
            String cookName = cookedOrderEventDataRow.getCookName();
            int cookingTime = cookedOrderEventDataRow.getTime();
            int cookingTimeMin = (cookingTime % 60 == 0) ? (cookingTime /60) : (cookingTime /60 + 1);

            if (result.containsKey(date)) {
                Map<String, Integer> temp = result.get(date);
                if (temp.containsKey(cookName)) temp.put(cookName, temp.get(cookName) + cookingTimeMin);
                else temp.put(cookName, cookingTimeMin);
                result.put(date, temp);
            }
            else {
                Map<String, Integer> temp = new TreeMap<>();
                temp.put(cookName, cookingTimeMin);
                result.put(date, temp);
            }
        }
        return result;
    }

}
