package org.desktop.parse;

import java.time.Month;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
public class DataProdCalendar {
    private int year;
    private HashMap<Month, HashSet<DayForProdCalendar>> mapProdCalendar = new HashMap<>();



    public HashSet<DayForProdCalendar> getDays(Month month){
        return mapProdCalendar.get(month);
    }

    public HashMap<Month, HashSet<DayForProdCalendar>> getMapProdCalendar() {
        return mapProdCalendar;
    }

    public void setMapProdCalendar(HashMap<String, HashSet<DayForProdCalendar>> mapProdCalendar) {
        HashMap<Month, HashSet<DayForProdCalendar>> mapProdCalendarEnumKey = new HashMap<>();
        for(Map.Entry<String, HashSet<DayForProdCalendar>> entry : mapProdCalendar.entrySet()){
            switch (entry.getKey()){
                case "Январь" : {
                    mapProdCalendarEnumKey.put(Month.JANUARY, entry.getValue());
                    break;
                }
                case "Февраль" : {
                    mapProdCalendarEnumKey.put(Month.FEBRUARY, entry.getValue());
                    break;
                }
                case "Март" : {
                    mapProdCalendarEnumKey.put(Month.MARCH, entry.getValue());
                    break;
                }
                case "Апрель" : {
                    mapProdCalendarEnumKey.put(Month.APRIL, entry.getValue());
                    break;
                }
                case "Май" : {
                    mapProdCalendarEnumKey.put(Month.MAY, entry.getValue());
                    break;
                }
                case "Июнь" : {
                    mapProdCalendarEnumKey.put(Month.JUNE, entry.getValue());
                    break;
                }
                case "Июль" : {
                    mapProdCalendarEnumKey.put(Month.JULY, entry.getValue());
                    break;
                }
                case "Август" : {
                    mapProdCalendarEnumKey.put(Month.AUGUST, entry.getValue());
                    break;
                }
                case "Сентябрь" : {
                    mapProdCalendarEnumKey.put(Month.SEPTEMBER, entry.getValue());
                    break;
                }
                case "Октябрь" : {
                    mapProdCalendarEnumKey.put(Month.OCTOBER, entry.getValue());
                    break;
                }
                case "Ноябрь" : {
                    mapProdCalendarEnumKey.put(Month.NOVEMBER, entry.getValue());
                    break;
                }
                case "Декабрь" : {
                    mapProdCalendarEnumKey.put(Month.DECEMBER, entry.getValue());
                    break;
                }
                default:
                    break;
            }
        }
        this.mapProdCalendar = mapProdCalendarEnumKey;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
