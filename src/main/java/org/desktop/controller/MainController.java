package org.desktop.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.desktop.AppData;
import org.desktop.StageInitializer;
import org.desktop.model.*;
import org.desktop.model.repository.CalendarProdRepository;
import org.desktop.model.repository.DepartmentRepository;
import org.desktop.model.repository.EmployeeRepository;
import org.desktop.parse.DayForProdCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Component
@Controller
public class MainController {

    @Value("classpath:/view/fxml/redactorEmployee.fxml")
    private Resource redactorEmployee;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private CalendarProdRepository calendarProdRepository;

    @FXML private Label year;

    @FXML private VBox vBoxMainForDepartment;


    @FXML private Button jan;
    @FXML private Button feb;
    @FXML private Button mar;
    @FXML private Button apr;
    @FXML private Button may;
    @FXML private Button jun;
    @FXML private Button jul;
    @FXML private Button aug;
    @FXML private Button sep;
    @FXML private Button oct;
    @FXML private Button nov;
    @FXML private Button dec;

    @FXML private Button redactEmployee;
    @FXML private Button allDep;

    @FXML private RadioButton adminEmp;


    @FXML private TableView<Employee> tableMain;
    @FXML private TableColumn<Employee, String> nameMain;
    @FXML private TableColumn<Employee, String> positionMain;
    @FXML private TableColumn<Employee, Integer> numberMain;
    private ArrayList<Button> monthButtons = new ArrayList<>();
    private int yearDate = LocalDate.now().getYear();


    @FXML
    private void initialize() throws IOException {
        //
        if(monthButtons.isEmpty()){
            monthButtons.add(jan);
            monthButtons.add(feb);
            monthButtons.add(mar);
            monthButtons.add(apr);
            monthButtons.add(may);
            monthButtons.add(jun);
            monthButtons.add(jul);
            monthButtons.add(aug);
            monthButtons.add(sep);
            monthButtons.add(oct);
            monthButtons.add(nov);
            monthButtons.add(dec);
        }
        //

        if(AppData.getEmployeeHashSet().isEmpty()){
            AppData.setEmployeeHashSet(employeeRepository.findAll());
        }

        createDepButton();
        loadingDataEmployees(null);
        tableMain.setEditable(true);
        loadingColumnsDays(yearDate, LocalDate.now().getMonth());
        year.setText("" + yearDate);
    }

    // onAction для месяцев
    @FXML public void clickJan() {
        for(Button button : monthButtons) button.setStyle(null);
        jan.setStyle("-fx-background-color: #1F96D2");
        //loadingDataCalendarProd(yearDate, Month.JANUARY);
        loadingColumnsDays(yearDate, Month.JANUARY);
    }
    @FXML public void clickFeb(){
        for(Button button : monthButtons) button.setStyle(null);
        feb.setStyle("-fx-background-color: #1F96D2");
        //loadingDataCalendarProd(yearDate, Month.FEBRUARY);
        loadingColumnsDays(yearDate, Month.FEBRUARY);
    }
    @FXML public void clickMar(){
        for(Button button : monthButtons) button.setStyle(null);
        mar.setStyle("-fx-background-color: #1F96D2");
        //loadingDataCalendarProd(yearDate, Month.MARCH);
        loadingColumnsDays(yearDate, Month.MARCH);
    }
    @FXML public void clickApr(){
        for(Button button : monthButtons) button.setStyle(null);
        apr.setStyle("-fx-background-color: #1F96D2");
        //loadingDataCalendarProd(yearDate, Month.APRIL);
        loadingColumnsDays(yearDate, Month.APRIL);
    }
    @FXML public void clickMay(){
        for(Button button : monthButtons) button.setStyle(null);
        may.setStyle("-fx-background-color: #1F96D2");
        //loadingDataCalendarProd(yearDate, Month.MAY);
        loadingColumnsDays(yearDate, Month.MAY);
    }
    @FXML public void clickJun(){
        for(Button button : monthButtons) button.setStyle(null);
        jun.setStyle("-fx-background-color: #1F96D2");
        //loadingDataCalendarProd(yearDate, Month.JUNE);
        loadingColumnsDays(yearDate, Month.JUNE);
    }
    @FXML public void clickJul(){
        for(Button button : monthButtons) button.setStyle(null);
        jul.setStyle("-fx-background-color: #1F96D2");
        //loadingDataCalendarProd(yearDate, Month.JULY);
        loadingColumnsDays(yearDate, Month.JULY);
    }
    @FXML public void clickAug(){
        for(Button button : monthButtons) button.setStyle(null);
        aug.setStyle("-fx-background-color: #1F96D2");
        //loadingDataCalendarProd(yearDate, Month.AUGUST);
        loadingColumnsDays(yearDate, Month.AUGUST);
    }
    @FXML public void clickSep(){
        for(Button button : monthButtons) button.setStyle(null);
        sep.setStyle("-fx-background-color: #1F96D2");
        //loadingDataCalendarProd(yearDate, Month.SEPTEMBER);
        loadingColumnsDays(yearDate, Month.SEPTEMBER);
    }
    @FXML public void clickOct(){
        for(Button button : monthButtons) button.setStyle(null);
        oct.setStyle("-fx-background-color: #1F96D2");
        //loadingDataCalendarProd(yearDate, Month.OCTOBER);
        loadingColumnsDays(yearDate, Month.OCTOBER);
    }
    @FXML public void clickNov(){
        for(Button button : monthButtons) button.setStyle(null);
        nov.setStyle("-fx-background-color: #1F96D2");
        //loadingDataCalendarProd(yearDate, Month.NOVEMBER);
        loadingColumnsDays(yearDate, Month.NOVEMBER);
    }
    @FXML public void clickDec(){
        for(Button button : monthButtons) button.setStyle(null);
        dec.setStyle("-fx-background-color: #1F96D2");
        //loadingDataCalendarProd(yearDate, Month.DECEMBER);
        loadingColumnsDays(yearDate, Month.DECEMBER);

    }

    private CalendarProd getCalendarProdDay(LocalDate localDate, Employee employee){

        TreeSet<CalendarProd> calendarProdTreeSet = AppData.getEmployeeCalendarProdHashMap().get(employee);
        CalendarProd[] calendarProd = new CalendarProd[calendarProdTreeSet.size()];
        calendarProd = calendarProdTreeSet.toArray(calendarProd);
        return bS(calendarProd, localDate);
    }

    private CalendarProd bS(CalendarProd[] calendarProds, LocalDate date){
        //System.out.println(date);
        CalendarProd day = null;
        if(calendarProds.length < 6){
            for(CalendarProd calendarProd : calendarProds){
                if(calendarProd.getDate().toString().equals(date.toString())){
                     day = calendarProd;
                }
            }
        }else {
            int i = calendarProds.length / 2;
            LocalDate dateFromArr = LocalDate.parse(calendarProds[i].getDate().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if(date.isBefore(dateFromArr)){
                CalendarProd[] calendarProds1 = new CalendarProd[i];
                System.arraycopy(calendarProds, 0, calendarProds1, 0, i);
                day = bS(calendarProds1, date);

            }else {
                CalendarProd[] calendarProds1 = new CalendarProd[(calendarProds.length - i)];
                System.arraycopy(calendarProds, i , calendarProds1, 0, calendarProds.length - i);
                day = bS(calendarProds1, date);
            }

        }
        return day;

    }

    // Метод загрузки производсвенного календаря CalendarProd на месяц
    private void loadingColumnsDays(int year, Month month){
        tableMain.getColumns().remove(3, tableMain.getColumns().size());
        LocalDate localDateMonth = LocalDate.of(year, month.getValue(), 1);
        ObservableList<TypeCode> typeCodes = FXCollections.observableArrayList(TypeCode.values());
        Set<DayForProdCalendar> dayForProdCalendarSet = AppData.getDataProdCalendarHashMap().get(year).getDays(month);

        while (localDateMonth.getMonth() == month){

            TableColumn<Employee, TypeCode> column = new TableColumn<>("" + localDateMonth.getDayOfMonth());
            column.setStyle("-fx-background-color: rgba(0,255,35, 0.1)");
            for(DayForProdCalendar day : dayForProdCalendarSet){
                if(day.getNumber() == localDateMonth.getDayOfMonth()){
                    if(day.getTypeDay() == TypeDay.DAYOFF || day.getTypeDay() == TypeDay.HOLIDAY){
                        column.setStyle("-fx-background-color: rgba(255,0,0, 0.1)");
                    }
                    if(day.getTypeDay() == TypeDay.PREHOLIDAY){
                        column.setStyle("-fx-background-color: rgba(255,213,0, 0.1)");
                    }
                }
            }

            LocalDate finalLocalDateMonth = localDateMonth;
            column.setCellValueFactory(param -> {
                Employee employee = param.getValue();
                CalendarProd calendarProd = getCalendarProdDay(finalLocalDateMonth, employee);
                TypeCode typeCode = calendarProd.getTypeCode();
                return new SimpleObjectProperty<>(typeCode);
            });
            column.setCellFactory(ComboBoxTableCell.forTableColumn(typeCodes));
            column.setOnEditCommit((TableColumn.CellEditEvent<Employee, TypeCode> event) -> {
                TablePosition<Employee, TypeCode> position = event.getTablePosition();
                int row = position.getRow();
                TypeCode typeCodeNew = event.getNewValue();
                Employee employee = event.getTableView().getItems().get(row);
                CalendarProd calendarProd = getCalendarProdDay(finalLocalDateMonth, employee);
                Optional<CalendarProd> calendarProdDB = calendarProdRepository.findById(calendarProd.getId());
                calendarProdDB.get().setTypeCode(typeCodeNew);
                calendarProdRepository.save(calendarProdDB.get());
                AppData.setDayCodeForCalendarProd(employee.getId(), calendarProd.getId(), typeCodeNew);


            });

            tableMain.getColumns().add(column);
            localDateMonth = localDateMonth.plusDays(1);
        }
    }

    /// Radio button
    @FXML public void clickAdminEmp(){
    }
    ///

    @FXML public void clickAllDep(){
        loadingDataEmployees(null);
    }

    @FXML public void clickRedEmployee() throws IOException {
        if(adminEmp.selectedProperty().get()){
            Stage stage = StageInitializer.getStageMain(redactorEmployee);
        }
    }

    // Метод загрузки данных employees в таблицу tableMain
    private void loadingDataEmployees(Department department){
        positionMain.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
        nameMain.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
        numberMain.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        if(department == null){
            tableMain.setItems(AppData.getEmployeeData());

        }
        else {
            ObservableList<Employee> employeeDataDepartment = FXCollections.observableArrayList();
            for(Employee employee : AppData.getEmployeeHashSet())
                if(employee.getDepartment() != null && employee.getDepartment().getId() == department.getId()) {
                    employeeDataDepartment.add(employee);
                }
            tableMain.setItems(employeeDataDepartment);
        }


    }

    // Добавление button существующих департаментов на панель VBox, добалвение onAction к button департамента с использование метода loadingDataEmployees
    private void createDepButton(){
        for(Department department : AppData.getDepartmentHashSet()){
            Button button = new Button();
            button.setText(department.getName());
            button.setOnAction(actionEvent -> loadingDataEmployees(department));
            vBoxMainForDepartment.getChildren().add(button);
        }
    }
}
