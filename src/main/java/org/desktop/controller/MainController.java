package org.desktop.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.desktop.AppData;
import org.desktop.StageInitializer;
import org.desktop.model.*;
import org.desktop.model.repository.CalendarProdRepository;
import org.desktop.model.repository.DepartmentRepository;
import org.desktop.model.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
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
        loadingDataCalendarProd(yearDate, LocalDate.now().getMonth());
        year.setText("" + yearDate);
    }

    // onAction для месяцев
    @FXML public void clickJan() {
        for(Button button : monthButtons) button.setStyle(null);
        jan.setStyle("-fx-background-color: #1F96D2");
        loadingDataCalendarProd(yearDate, Month.JANUARY);
    }
    @FXML public void clickFeb(){
        for(Button button : monthButtons) button.setStyle(null);
        feb.setStyle("-fx-background-color: #1F96D2");
        loadingDataCalendarProd(yearDate, Month.FEBRUARY);
    }
    @FXML public void clickMar(){
        for(Button button : monthButtons) button.setStyle(null);
        mar.setStyle("-fx-background-color: #1F96D2");
        loadingDataCalendarProd(yearDate, Month.MARCH);
    }
    @FXML public void clickApr(){
        for(Button button : monthButtons) button.setStyle(null);
        apr.setStyle("-fx-background-color: #1F96D2");
        loadingDataCalendarProd(yearDate, Month.APRIL);
    }
    @FXML public void clickMay(){
        for(Button button : monthButtons) button.setStyle(null);
        may.setStyle("-fx-background-color: #1F96D2");
        loadingDataCalendarProd(yearDate, Month.MAY);
    }
    @FXML public void clickJun(){
        for(Button button : monthButtons) button.setStyle(null);
        jun.setStyle("-fx-background-color: #1F96D2");
        loadingDataCalendarProd(yearDate, Month.JUNE);
    }
    @FXML public void clickJul(){
        for(Button button : monthButtons) button.setStyle(null);
        jul.setStyle("-fx-background-color: #1F96D2");
        loadingDataCalendarProd(yearDate, Month.JULY);
    }
    @FXML public void clickAug(){
        for(Button button : monthButtons) button.setStyle(null);
        aug.setStyle("-fx-background-color: #1F96D2");
        loadingDataCalendarProd(yearDate, Month.AUGUST);
    }
    @FXML public void clickSep(){
        for(Button button : monthButtons) button.setStyle(null);
        sep.setStyle("-fx-background-color: #1F96D2");
        loadingDataCalendarProd(yearDate, Month.SEPTEMBER);
    }
    @FXML public void clickOct(){
        for(Button button : monthButtons) button.setStyle(null);
        oct.setStyle("-fx-background-color: #1F96D2");
        loadingDataCalendarProd(yearDate, Month.OCTOBER);
    }
    @FXML public void clickNov(){
        for(Button button : monthButtons) button.setStyle(null);
        nov.setStyle("-fx-background-color: #1F96D2");
        loadingDataCalendarProd(yearDate, Month.NOVEMBER);
    }
    @FXML public void clickDec(){
        for(Button button : monthButtons) button.setStyle(null);
        dec.setStyle("-fx-background-color: #1F96D2");
        loadingDataCalendarProd(yearDate, Month.DECEMBER);
    }

    // Метод загрузки производсвенного календаря CalendarProd на месяц
    private void loadingDataCalendarProd(int year, Month month){
        tableMain.getColumns().remove(3, tableMain.getColumns().size());
        LocalDate localDateMonth = LocalDate.of(year, month.getValue(), 1);
        while (localDateMonth.getMonth() == month){
            TableColumn<Employee, ChoiceBox<TypeCode>> calendarProdIntegerTableColumn = new TableColumn<>("" + localDateMonth.getDayOfMonth());

            LocalDate finalLocalDateMonth = localDateMonth;
            calendarProdIntegerTableColumn.setCellValueFactory(param -> {
                ChoiceBox<TypeCode> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList());
                CalendarProd calendarProd = getCalendarProdDay(finalLocalDateMonth, param.getValue());
                assert calendarProd != null;


                if(calendarProd.getTypeDay() == TypeDay.DAYOFF || calendarProd.getTypeDay() == TypeDay.HOLIDAY){
                    choiceBox.setStyle("-fx-background-color: #FFACAC");
                }
                if(calendarProd.getTypeDay() == TypeDay.PREHOLIDAY){
                    choiceBox.setStyle("-fx-background-color: #FFFB86");
                }
                if(calendarProd.getTypeDay() == TypeDay.WORKDAY){
                    choiceBox.setStyle("-fx-background-color: #AEFF86");
                }
                choiceBox.setValue(calendarProd.getTypeCode());
                choiceBox.setItems(FXCollections.observableArrayList(TypeCode.values()));
                choiceBox.setOnAction(actionEvent -> {
                    Optional<CalendarProd> forNewCode = calendarProdRepository.findById(calendarProd.getId());
                    if(forNewCode.isEmpty()){
                        return;
                    }
                    AppData.setDayCodeForCalendarProd(param.getValue().getId(), calendarProd.getId(), choiceBox.getValue());
                    forNewCode.get().setTypeCode(choiceBox.getValue());
                    calendarProdRepository.save(forNewCode.get());
                });
                return new SimpleObjectProperty<>(choiceBox);
            });
            tableMain.getColumns().add(calendarProdIntegerTableColumn);
            localDateMonth = localDateMonth.plusDays(1);
        }
    }

    private CalendarProd getCalendarProdDay(LocalDate localDate, Employee employee){
        for(CalendarProd calendarProd : AppData.getEmployeeCalendarProdHashMap().get(employee)){
            if(calendarProd.getDate().toString().equals(localDate.toString())){
                return calendarProd;
            }
        }
        return null;
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
