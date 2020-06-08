package org.desktop.controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.skin.DateCellSkin;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.desktop.AppData;
import org.desktop.StageInitializer;
import org.desktop.controller.custom.DatePickerCell;
import org.desktop.model.Department;
import org.desktop.model.Employee;
import org.desktop.model.repository.DepartmentRepository;
import org.desktop.model.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javafx.scene.control.*;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Component
@Controller
public class RedactorEmpController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Value("classpath:/view/fxml/main.fxml")
    private Resource mainResource;
    @Value("classpath:/view/fxml/addEmployee.fxml")
    private Resource addEmpResource;

    @FXML private Button buttonAddRedactorEmp;
    @FXML private Button buttonDeleteRedactorEmp;
    @FXML private Button buttonSearсhRedactorEmp;
    @FXML private Button buttonBack;
    @FXML private TextField entryFieldForSearch;

    @FXML private TableView<Employee> tableRedactorEmp;
    @FXML private TableColumn<Employee, String> firstNameCol;
    @FXML private TableColumn<Employee, String> lastNameCol;
    @FXML private TableColumn<Employee, String> middleNameCol;
    @FXML private TableColumn<Employee, LocalDate> dayOfBirthCol;
    @FXML private TableColumn<Employee, Integer> ageCol;
    @FXML private TableColumn<Employee, String> positionCol;
    @FXML private TableColumn<Employee, Boolean> remoteWorkCol;
    @FXML private TableColumn<Employee, String> cityCol;
    @FXML private TableColumn<Employee, String> streetCol;
    @FXML private TableColumn<Employee, String> houseCol;
    @FXML private TableColumn<Employee, String> apartCol;

    @FXML private void initialize(){
        tableRedactorEmp.setEditable(true);
        createTable();
    }
    private void createTable(){
        //Редактироание ФИО
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {
            TablePosition<Employee, String> position = event.getTablePosition();
            String firstNameNew = event.getNewValue();
            int row = position.getRow();
            Employee employee = event.getTableView().getItems().get(row);
            employee.setFirstName(firstNameNew);
            Optional<Employee> employeeBefore = employeeRepository.findById(employee.getId());
            employeeBefore.get().setFirstName(firstNameNew);
            employeeRepository.save(employeeBefore.get());
        });
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {
            TablePosition<Employee, String> position = event.getTablePosition();
            String lastNameNew = event.getNewValue();
            int row = position.getRow();
            Employee employee = event.getTableView().getItems().get(row);
            employee.setLastName(lastNameNew);
            Optional<Employee> employeeBefore = employeeRepository.findById(employee.getId());
            employeeBefore.get().setFirstName(lastNameNew);
            employeeRepository.save(employeeBefore.get());
        });
        middleNameCol.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        middleNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        middleNameCol.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {
            TablePosition<Employee, String> position = event.getTablePosition();
            String middleNameNew = event.getNewValue();
            int row = position.getRow();
            Employee employee = event.getTableView().getItems().get(row);
            employee.setMiddleName(middleNameNew);
            Optional<Employee> employeeBefore = employeeRepository.findById(employee.getId());
            employeeBefore.get().setFirstName(middleNameNew);
            employeeRepository.save(employeeBefore.get());
        });

        //день рождения
        dayOfBirthCol.setCellValueFactory(param -> {
            Employee employee = param.getValue();
            SimpleObjectProperty<LocalDate> simpleObjectProperty = new SimpleObjectProperty<>(employee.getDateOfBirth());
            simpleObjectProperty.addListener((observableValue, beforeValue, afterValue) -> {
                Optional<Employee> employeeBefore = employeeRepository.findById(employee.getId());
                employeeBefore.get().setDateOfBirth(afterValue.toString());
                employeeRepository.save(employeeBefore.get());
                employee.setDateOfBirth(afterValue.toString());
            });
            return simpleObjectProperty;
        });
        dayOfBirthCol.setCellFactory(employeeDateTableColumn -> {
            DatePickerCell<Employee> datePickerCell = new DatePickerCell<>(employeeDateTableColumn);
            return datePickerCell;
        });

        //возраст
        ageCol.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("age"));

        //должность
        positionCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
        positionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        positionCol.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {
            TablePosition<Employee, String> position = event.getTablePosition();
            String positionEmployeeNew = event.getNewValue();
            int row = position.getRow();
            Employee employee = event.getTableView().getItems().get(row);
            employee.setPosition(positionEmployeeNew);
            Optional<Employee> employeeBefore = employeeRepository.findById(employee.getId());
            employeeBefore.get().setFirstName(positionEmployeeNew);
            employeeRepository.save(employeeBefore.get());
        });

        //чек-бокс удаленная работа
        remoteWorkCol.setCellValueFactory(param -> {
            Employee employee = param.getValue();
            SimpleBooleanProperty simpleBooleanProperty = new SimpleBooleanProperty(employee.isRemoteWork());
            simpleBooleanProperty.addListener((observableValue, beforeValue, afterValue) -> {
                Optional<Employee> employeeBefore = employeeRepository.findById(employee.getId());
                employeeBefore.get().setRemoteWork(afterValue);
                employeeRepository.save(employeeBefore.get());
                employee.setRemoteWork(afterValue);
            });
            return simpleBooleanProperty;
        });
        remoteWorkCol.setCellFactory(employeeBooleanTableColumn -> {
            CheckBoxTableCell<Employee, Boolean> checkBoxTableCell = new CheckBoxTableCell<>();
            checkBoxTableCell.setAlignment(Pos.CENTER);
            return checkBoxTableCell;
        });

        //адрес
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        cityCol.setCellFactory(TextFieldTableCell.forTableColumn());
        cityCol.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {
            TablePosition<Employee, String> position = event.getTablePosition();
            String cityNew = event.getNewValue();
            int row = position.getRow();
            Employee employee = event.getTableView().getItems().get(row);
            employee.getAddress().setCity(cityNew);
            Optional<Employee> employeeBefore = employeeRepository.findById(employee.getId());
            employeeBefore.get().getAddress().setCity(cityNew);
            employeeRepository.save(employeeBefore.get());
        });
        streetCol.setCellValueFactory(new PropertyValueFactory<>("street"));
        streetCol.setCellFactory(TextFieldTableCell.forTableColumn());
        streetCol.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {
            TablePosition<Employee, String> position = event.getTablePosition();
            String streetNew = event.getNewValue();
            int row = position.getRow();
            Employee employee = event.getTableView().getItems().get(row);
            employee.getAddress().setStreet(streetNew);
            Optional<Employee> employeeBefore = employeeRepository.findById(employee.getId());
            employeeBefore.get().getAddress().setStreet(streetNew);
            employeeRepository.save(employeeBefore.get());
        });
        houseCol.setCellValueFactory(new PropertyValueFactory<>("house"));
        houseCol.setCellFactory(TextFieldTableCell.forTableColumn());
        houseCol.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {
            TablePosition<Employee, String> position = event.getTablePosition();
            String houseNew = event.getNewValue();
            int row = position.getRow();
            Employee employee = event.getTableView().getItems().get(row);
            employee.getAddress().setHouse(houseNew);
            Optional<Employee> employeeBefore = employeeRepository.findById(employee.getId());
            employeeBefore.get().getAddress().setHouse(houseNew);
            employeeRepository.save(employeeBefore.get());
        });
        apartCol.setCellValueFactory(new PropertyValueFactory<>("apart"));
        apartCol.setCellFactory(TextFieldTableCell.forTableColumn());
        apartCol.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {
            TablePosition<Employee, String> position = event.getTablePosition();
            String apartNew = event.getNewValue();
            int row = position.getRow();
            Employee employee = event.getTableView().getItems().get(row);
            employee.getAddress().setApp(apartNew);
            Optional<Employee> employeeBefore = employeeRepository.findById(employee.getId());
            employeeBefore.get().getAddress().setApp(apartNew);
            employeeRepository.save(employeeBefore.get());
        });

        tableRedactorEmp.setItems(AppData.getEmployeeData());
    }

    @FXML private void clickButtonAddRedactorEmp() throws IOException {
        Stage stage = StageInitializer.getStageMain(addEmpResource);
    }

    @FXML private void clickButtonDeleteRedactorEmp(){
        Employee employeeForDelete = tableRedactorEmp.getSelectionModel().getSelectedItem();
        tableRedactorEmp.getItems().remove(employeeForDelete);
        employeeRepository.deleteById(employeeForDelete.getId());

        if(employeeForDelete.getDepartment() != null){
            Optional<Department> department = departmentRepository.findById(employeeForDelete.getDepartment().getId());
            for(Employee employee : department.get().getEmployeeSet()){
                if(employee.getId() == employeeForDelete.getId()){
                    employeeForDelete = employee;
                }
            }
            if(department.get().getEmployeeSet().isEmpty()){
                departmentRepository.delete(department.get());
            }else {
                department.get().getEmployeeSet().remove(employeeForDelete);
                departmentRepository.save(department.get());
            }
        }
        AppData.deleteEmployee(employeeForDelete);
    }
    @FXML private void clickButtonSearchRedactorEmp(){}

    @FXML private void clickButtonBack() throws IOException {
        Stage stage = StageInitializer.getStageMain(mainResource);
    }

}
