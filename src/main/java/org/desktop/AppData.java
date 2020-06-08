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

    private static final HashMap<Integer, DataProdCalendar> dataProdCalendarHashMap = new ParseCSV().getDataProdCalendar();

    private static ObservableList<Employee> employeeData = FXCollections.observableArrayList();
    private static HashMap<Integer, TreeSet<CalendarProd>> employeeCalendarProdHashMap = new HashMap<>();
    private static HashSet<Department> departmentHashSet = new HashSet<>();

    public static void setEmployee(Employee employee){
        employeeCalendarProdHashMap.put(employee.getId(), employee.getCalendarProds());
        if(employee.getDepartment() != null){
            boolean checkDep = true;
            for(Department department : departmentHashSet){
                if(department.getName().equals(employee.getDepartment().getName())){
                    checkDep = false;
                    break;
                }
            }
            if(checkDep){
                departmentHashSet.add(employee.getDepartment());
            }
        }
        employeeData.add(employee);
    }

    public static void deleteEmployee(Employee employee){
        employeeData.remove(employee);
        employeeCalendarProdHashMap.remove(employee.getId());

        Department departmentDel = employee.getDepartment();
        boolean checkDep = false;
        if(departmentDel != null){
            for(Department department : departmentHashSet){
                if(department.getId() == departmentDel.getId()){
                    department.getEmployeeSet().remove(employee);
                    if(department.getEmployeeSet() == null){
                        checkDep = true;
                    }
                    break;
                }
            }
        }
        if(checkDep){
            departmentHashSet.remove(departmentDel);
        }

    }
    public static void setEmployeeData(Iterable<Employee> employeeIterable) {

        for(Employee employee : employeeIterable){
            employeeCalendarProdHashMap.put(employee.getId(), employee.getCalendarProds());
            if(employee.getDepartment() != null){
                departmentHashSet.add(employee.getDepartment());
            }
            employeeData.add(employee);
        }
    }

    public static ObservableList<Employee> getEmployeeData() {
        return employeeData;
    }

    public static HashMap<Integer, TreeSet<CalendarProd>> getEmployeeCalendarProdHashMap() {
        return employeeCalendarProdHashMap;
    }

    public static HashMap<Integer, DataProdCalendar> getDataProdCalendarHashMap() {
        return dataProdCalendarHashMap;
    }


    public static HashSet<Department> getDepartmentHashSet() {
        return departmentHashSet;
    }

    public static void setDayCodeForCalendarProd(int employeeId, long calendarProdId, TypeCode typeCode){
        for(Employee employee : employeeData){
            if(employee.getId() == employeeId){
                for(CalendarProd calendarProd : employee.getCalendarProds()){
                    if(calendarProd.getId() == calendarProdId){
                        calendarProd.setTypeCode(typeCode);
                        break;
                    }
                }
            }
        }
    }
}
