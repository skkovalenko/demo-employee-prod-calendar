package org.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.desktop.AppData;
import org.desktop.StageInitializer;
import org.desktop.model.*;
import org.desktop.model.repository.CalendarProdRepository;
import org.desktop.model.repository.DepartmentRepository;
import org.desktop.model.repository.EmployeeRepository;
import org.desktop.parse.DataProdCalendar;
import org.desktop.parse.DayForProdCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.TreeSet;

@Component
@Controller
public class AddEmployeeController {
    @Value("classpath:/view/fxml/redactorEmployee.fxml")
    private Resource resourceRedactorEmp;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private CalendarProdRepository calendarProdRepository;


    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField middleName;
    @FXML private DatePicker dateOfBirth;
    @FXML private TextField position;
    @FXML private TextField department;
    @FXML private TextField city;
    @FXML private TextField street;
    @FXML private TextField house;
    @FXML private TextField apart;
    @FXML private Button buttonSaveEmployee;
    @FXML private Button buttonBackRedactorEmp;

    @FXML private void clickButtonSaveEmployee(){
        Employee employee = employeeCreate();
        employee = employeeRepository.save(employee);
        AppData.setEmployee(createProdCalendarEmployee(employee));
    }

    @FXML private void clickButtonBackRedactorEmp() throws IOException {
        StageInitializer.getStageMain(resourceRedactorEmp);
    }

    private Employee employeeCreate() {
        Employee employee = new Employee();
        employee.setFirstName(firstName.getText());
        employee.setLastName(lastName.getText());
        employee.setMiddleName(middleName.getText());
        employee.setDateOfBirth(dateOfBirth.getValue().toString());
        employee.setPosition(position.getText());
        Department departmentForEmployee = getDepartment(department.getText());
        if(departmentForEmployee != null){
            employee.setDepartment(departmentForEmployee);
        }
        employee.setAddress(addressCreate(city.getText(), street.getText(), house.getText(), apart.getText(), employee));
        return employee;
    }
    private Employee createProdCalendarEmployee(Employee employee){
        LocalDate localDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        int nextYear = localDate.getYear() + 1;
        DataProdCalendar year = AppData.getDataProdCalendarHashMap().get(localDate.getYear());
        Month monthCheck = localDate.getMonth();
        TreeSet<CalendarProd> calendarProds = new TreeSet<>();
        Set<DayForProdCalendar> daysMonth = year.getDays(localDate.getMonth());
        while (localDate.getYear() < nextYear){
            if(monthCheck != localDate.getMonth()){
                monthCheck = localDate.getMonth();
                daysMonth.clear();
                daysMonth = year.getDays(localDate.getMonth());
            }
            CalendarProd calendarProd = new CalendarProd();
            calendarProd.setDate(localDate);
            calendarProd.setEmployee(employee);
            if(localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY){
                calendarProd.setTypeDay(TypeDay.DAYOFF);
            }else calendarProd.setTypeDay(TypeDay.WORKDAY);

            for(DayForProdCalendar day : daysMonth){

                if(day.getNumber() == localDate.getDayOfMonth()){
                    calendarProd.setTypeDay(day.getTypeDay());
                }
            }
            calendarProdRepository.save(calendarProd);
            calendarProds.add(calendarProd);
            localDate = localDate.plusDays(1);
        }
        employee.setCalendarProds(calendarProds);
        return employee;
    }

    private Address addressCreate(String city, String street, String house, String apart, Employee employee){
        Address address = new Address();
        address.setCity(city);
        address.setStreet(street);
        address.setHouse(house);
        address.setApp(apart);
        address.setEmployee(employee);
        return address;
    }
    private Department getDepartment(String nameDepartment){
        if(nameDepartment.isEmpty()){
            return null;
        }
        Iterable<Department> departmentIterable = departmentRepository.findAll();
        for(Department department : departmentIterable){
            if(department.getName().equals(nameDepartment)){
                return department;
            }
        }
        Department department = new Department();
        department.setName(nameDepartment);
        departmentRepository.save(department);
        return department;
    }
}
