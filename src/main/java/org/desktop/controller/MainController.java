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
import org.desktop.parse.DataProdCalendar;
import org.desktop.parse.DayForProdCalendar;
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

    @FXML private Button jan; @FXML private Button feb; @FXML private Button mar; @FXML private Button apr;
    @FXML private Button may; @FXML private Button jun; @FXML private Button jul; @FXML private Button aug;
    @FXML private Button sep; @FXML private Button oct; @FXML private Button nov; @FXML private Button dec;

    @FXML private Button redactEmployee;
    @FXML private Button allDep;

    @FXML private RadioButton adminEmp;
    @FXML private RadioButton timekeeper;

    @FXML private TableView<Employee> tableMain;
    @FXML private TableColumn<Employee, String> nameMain;
    @FXML private TableColumn<Employee, String> positionMain;
    @FXML private TableColumn<Employee, Integer> numberMain;

    private int yearDate = LocalDate.now().getYear();

    private static final ArrayList<Button> MONTH_BUTTONS = new ArrayList<>();

    @FXML
    private void initialize() {

        MONTH_BUTTONS.clear();
        MONTH_BUTTONS.add(jan); MONTH_BUTTONS.add(feb); MONTH_BUTTONS.add(mar); MONTH_BUTTONS.add(apr);
        MONTH_BUTTONS.add(may); MONTH_BUTTONS.add(jun); MONTH_BUTTONS.add(jul); MONTH_BUTTONS.add(aug);
        MONTH_BUTTONS.add(sep); MONTH_BUTTONS.add(oct); MONTH_BUTTONS.add(nov); MONTH_BUTTONS.add(dec);

        if(AppData.getEmployeeData().isEmpty()){
            AppData.setEmployeeData(employeeRepository.findAll());
        }

        createDepartmentButton();
        loadingDataEmployees(null);
        loadingColumnsDays(yearDate, LocalDate.now().getMonth());
        year.setText("" + yearDate);
    }

    // onAction для месяцев
    @FXML public void clickJan(){
        for(Button button : MONTH_BUTTONS) button.setStyle(null);
        jan.setStyle("-fx-background-color: #1F96D2");
        loadingColumnsDays(yearDate, Month.JANUARY);
    }
    @FXML public void clickFeb(){
        for(Button button : MONTH_BUTTONS) button.setStyle(null);
        feb.setStyle("-fx-background-color: #1F96D2");
        loadingColumnsDays(yearDate, Month.FEBRUARY);
    }
    @FXML public void clickMar(){
        for(Button button : MONTH_BUTTONS) button.setStyle(null);
        mar.setStyle("-fx-background-color: #1F96D2");
        loadingColumnsDays(yearDate, Month.MARCH);
    }
    @FXML public void clickApr(){
        for(Button button : MONTH_BUTTONS) button.setStyle(null);
        apr.setStyle("-fx-background-color: #1F96D2");
        loadingColumnsDays(yearDate, Month.APRIL);
    }
    @FXML public void clickMay(){
        for(Button button : MONTH_BUTTONS) button.setStyle(null);
        may.setStyle("-fx-background-color: #1F96D2");
        loadingColumnsDays(yearDate, Month.MAY);
    }
    @FXML public void clickJun(){
        for(Button button : MONTH_BUTTONS) button.setStyle(null);
        jun.setStyle("-fx-background-color: #1F96D2");
        loadingColumnsDays(yearDate, Month.JUNE);
    }
    @FXML public void clickJul(){
        for(Button button : MONTH_BUTTONS) button.setStyle(null);
        jul.setStyle("-fx-background-color: #1F96D2");
        loadingColumnsDays(yearDate, Month.JULY);
    }
    @FXML public void clickAug(){
        for(Button button : MONTH_BUTTONS) button.setStyle(null);
        aug.setStyle("-fx-background-color: #1F96D2");
        loadingColumnsDays(yearDate, Month.AUGUST);
    }
    @FXML public void clickSep(){
        for(Button button : MONTH_BUTTONS) button.setStyle(null);
        sep.setStyle("-fx-background-color: #1F96D2");
        loadingColumnsDays(yearDate, Month.SEPTEMBER);
    }
    @FXML public void clickOct(){
        for(Button button : MONTH_BUTTONS) button.setStyle(null);
        oct.setStyle("-fx-background-color: #1F96D2");
        loadingColumnsDays(yearDate, Month.OCTOBER);
    }
    @FXML public void clickNov(){
        for(Button button : MONTH_BUTTONS) button.setStyle(null);
        nov.setStyle("-fx-background-color: #1F96D2");
        loadingColumnsDays(yearDate, Month.NOVEMBER);
    }
    @FXML public void clickDec(){
        for(Button button : MONTH_BUTTONS) button.setStyle(null);
        dec.setStyle("-fx-background-color: #1F96D2");
        loadingColumnsDays(yearDate, Month.DECEMBER);

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
                calendarProdRepository.save(calendarProdDB.get());AppData.setDayCodeForCalendarProd(employee.getId(), calendarProd.getId(), typeCodeNew);
            });
            tableMain.getColumns().add(column);
            localDateMonth = localDateMonth.plusDays(1);
        }
    }
    // Поиск даты
    private CalendarProd getCalendarProdDay(LocalDate localDate, Employee employee){
        TreeSet<CalendarProd> calendarProdTreeSet = AppData.getEmployeeCalendarProdHashMap().get(employee.getId());
        CalendarProd[] calendarProd = new CalendarProd[calendarProdTreeSet.size()];
        calendarProd = calendarProdTreeSet.toArray(calendarProd);
        return bS(calendarProd, localDate);
    }
    private CalendarProd bS(CalendarProd[] calendarProds, LocalDate date){
        CalendarProd day = null;
        if(calendarProds[0].getDate().toString().equals(date.toString())){
            day = calendarProds[0];
        }else {
            int i = calendarProds.length / 2;
            LocalDate dateFromArr = calendarProds[i].getDate();
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

    // Action для Radio button
    @FXML public void clickAdminEmp(){
        tableMain.setEditable(false);
        if(timekeeper.selectedProperty().getValue()) timekeeper.selectedProperty().setValue(false);

    }
    @FXML public void clickTimekeeper(){
        tableMain.setEditable(true);
        if(adminEmp.selectedProperty().getValue()) adminEmp.selectedProperty().setValue(false);
    }

    //Загрузка всех департаментов
    @FXML public void clickAllDep(){
        loadingDataEmployees(null);
    }

    //Переход на страницу для редактирования сотрудников
    @FXML public void clickRedEmployee() throws IOException {
        if(adminEmp.selectedProperty().get()){
            Stage stage = StageInitializer.getStageMain(redactorEmployee);
        }
    }

    // Метод загрузки данных сотрудников в таблицу по департаментам (пр инициализаци по умолчанию загружаются все департаменты)
    private void loadingDataEmployees(Department department){

        positionMain.setCellValueFactory(new PropertyValueFactory<>("position"));
        nameMain.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        numberMain.setCellValueFactory(new PropertyValueFactory<>("id"));

        if(department == null){
            tableMain.setItems(AppData.getEmployeeData());
        }

        else {
            ObservableList<Employee> employeeDataDepartment = FXCollections.observableArrayList();
            for(Employee employee : AppData.getEmployeeData())
                if(employee.getDepartment() != null && employee.getDepartment().getId() == department.getId()) {
                    employeeDataDepartment.add(employee);
                }
            tableMain.setItems(employeeDataDepartment);
        }
    }

    // Добавление button существующих департаментов на панель VBox, добалвение onAction к button департамента
    private void createDepartmentButton(){
        for(Department department : AppData.getDepartmentHashSet()){
            /*if(department == null){
                continue;
            }*/

            Button button = new Button();
            button.setText(department.getName());
            button.setOnAction(actionEvent -> loadingDataEmployees(department));
            vBoxMainForDepartment.getChildren().add(button);
        }
    }
}
