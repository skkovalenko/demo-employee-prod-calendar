package org.desktop.parse;
import org.desktop.model.TypeDay;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Month;
import java.util.*;

public class ParseCSV {
    private static final String PATH_CSV = "data/data-20191112T1252-structure-20191112T1247.csv";
    private HashMap<Integer, DataProdCalendar> dataProdCalendars = new HashMap<>();

    public HashMap<Integer, DataProdCalendar> getProdCalendar() {

        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(PATH_CSV));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] months = new String[12];


        int countLines = 0;
        for(String line : lines){

            if(countLines++ == 0) {
                months = firstLineParse(line.split(",\""));
                continue;
            }
            String[] yearAndDays = lineParse(line.split(",\""));
            int year = Integer.parseInt(yearAndDays[0]);

            DataProdCalendar dataProdCalendar = new DataProdCalendar();
            dataProdCalendar.setYear(year);
            HashMap<String, HashSet<DayForProdCalendar>> listHashMap = new HashMap<>();

            for(int i = 0; i < yearAndDays.length; i++){
                if(i == 0){
                    continue;
                }
                HashSet<DayForProdCalendar> dayForProdCalendarArrayList = new HashSet<>();

                String[] days = yearAndDays[i].split(",");
                for(String d : days){
                    DayForProdCalendar day = new DayForProdCalendar();
                    if(d.contains("*") || d.contains("+")){
                        int n = Integer.parseInt(d.replaceAll("[*+]", ""));
                        day.setNumber(n);
                        day.setTypeDay(TypeDay.PREHOLIDAY);
                        dayForProdCalendarArrayList.add(day);
                    }else {
                        int n = Integer.parseInt(d);
                        day.setNumber(n);
                        day.setTypeDay(TypeDay.HOLIDAY);
                        dayForProdCalendarArrayList.add(day);
                    }
                }

                listHashMap.put(months[i - 1], dayForProdCalendarArrayList);
            }
            dataProdCalendar.setMapProdCalendar(listHashMap);
            dataProdCalendars.put(dataProdCalendar.getYear(), dataProdCalendar);
        }
        /*for(Map.Entry<Integer, DataProdCalendar> entry : dataProdCalendars.entrySet()){
            System.out.println(entry.getKey());
            System.out.println(entry.getValue().getMapProdCalendar().size());
            for(Map.Entry<Month, HashSet<DayForProdCalendar>> e : entry.getValue().getMapProdCalendar().entrySet()){
                System.out.println("    " + e.getKey());
                for(DayForProdCalendar day : e.getValue()){
                   System.out.println("        " + day.getNumber() + " " + day.getTypeDay());
               }
          }
        }*/
        return dataProdCalendars;
    }

    private String[] firstLineParse(String[] arr) {
        String[] months = new String[12];
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) continue;
            if (i <= 12) months[i - 1] = arr[i].replaceAll("\"", "");
            else break;
        }
        return months;
    }


    private String[] lineParse(String[] arr){
        String[] splitLine = new String[13];
        for (int i = 0; i < arr.length; i++){
            if(i <= 12){
                if(i == 12){
                    splitLine[i] = arr[i].replaceAll("\".*", "");
                    break;
                }
                splitLine[i] = arr[i].replaceAll("\"", "");
            }
            else break;
        }
        return splitLine;
    }
}
