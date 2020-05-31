package org.desktop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.desktop.model.CalendarProd;
import org.desktop.model.Department;
import org.desktop.model.Employee;
import org.desktop.model.TypeCode;
import org.desktop.parse.DataProdCalendar;
import org.desktop.parse.ParseCSV;

import java.util.*;

public class AppData {

    private static HashMap<Employee, TreeSet<CalendarProd>> employeeCalendarProdHashMap = new HashMap<>();
    private static HashMap<Integer, DataProdCalendar> dataProdCalendarHashMap;
    private static HashSet<Employee> employeeHashSet = new HashSet<>();
    private static HashSet<Department> departmentHashSet = new HashSet<>();
    private static ObservableList<Employee> employeeData = FXCollections.observableArrayList();

    public static void setEmployee(Employee employee){
        employeeCalendarProdHashMap.put(employee, employee.getCalendarProds());
        employeeHashSet.add(employee);
        departmentHashSet.add(employee.getDepartment());
        employeeData.add(employee);
    }
    public static void setEmployeeHashSet(Iterable<Employee> employeeIterable) {

        for(Employee employee : employeeIterable){
            employeeHashSet.add(employee);
            employeeCalendarProdHashMap.put(employee, employee.getCalendarProds());
            departmentHashSet.add(employee.getDepartment());
            employeeData.add(employee);
        }
    }

    public static ObservableList<Employee> getEmployeeData() {
        return employeeData;
    }

    public static HashMap<Employee, TreeSet<CalendarProd>> getEmployeeCalendarProdHashMap() {
        return employeeCalendarProdHashMap;
    }

    public static HashMap<Integer, DataProdCalendar> getDataProdCalendarHashMap() {
        if (dataProdCalendarHashMap == null) dataProdCalendarHashMap = new ParseCSV().getProdCalendar();
            return dataProdCalendarHashMap;
    }

    public static HashSet<Employee> getEmployeeHashSet() {
        return employeeHashSet;
    }

    public static HashSet<Department> getDepartmentHashSet() {
        return departmentHashSet;
    }

    public static void setDayCodeForCalendarProd(int employeeId, long calendarProdId, TypeCode typeCode){
        for(Employee emp : employeeHashSet){
            if(emp.getId() == employeeId){
                for(CalendarProd calendarProd : emp.getCalendarProds()){
                    if(calendarProd.getId() == calendarProdId){
                        calendarProd.setTypeCode(typeCode);
                        break;
                    }
                }
            }
        }
    }
}
