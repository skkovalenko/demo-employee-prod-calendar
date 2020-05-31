package org.desktop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.desktop.AppData;
import org.desktop.StageInitializer;
import org.desktop.model.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javafx.scene.control.*;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Date;

@Component
@Controller
public class RedactorEmpController {


    @Value("classpath:/view/fxml/main.fxml")
    private Resource mainResource;
    @Value("classpath:/view/fxml/addEmployee.fxml")
    private Resource addEmpResource;

    @FXML private Button buttonAddRedactorEmp;
    @FXML private Button buttonEditRedactorEmp;
    @FXML private Button buttonDeleteRedactorEmp;
    @FXML private Button buttonSearсhRedactorEmp;
    @FXML private Button buttonBack;
    @FXML private TextField entryFieldForSearch;

    @FXML private TableView<Employee> tableRedactorEmp;
    @FXML private TableColumn<Employee, String> firstNameCol;
    @FXML private TableColumn<Employee, String> lastNameCol;
    @FXML private TableColumn<Employee, String> middleNameCol;
    @FXML private TableColumn<Employee, Date> dayOfBirthCol;
    @FXML private TableColumn<Employee, Integer> ageCol;
    @FXML private TableColumn<Employee, String> positionCol;
    @FXML private TableColumn<Employee, RadioButton> remoteWorkCol;
    @FXML private TableColumn<Employee, String> addressCol;

    @FXML private void initialize(){
        createTable();
    }
    private void createTable(){
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        middleNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("middleName"));
        dayOfBirthCol.setCellValueFactory(new PropertyValueFactory<Employee, Date>("dateOfBirth"));
        ageCol.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("age"));
        positionCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
        remoteWorkCol.setCellValueFactory(new PropertyValueFactory<Employee, RadioButton>("remoteWork"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Employee, String>(""));
        tableRedactorEmp.setItems(AppData.getEmployeeData());
    }

    @FXML private void clickButtonAddRedactorEmp() throws IOException {
        Stage stage = StageInitializer.getStageMain(addEmpResource);
    }
    @FXML private void clickButtonEditRedactorEmp(){}
    @FXML private void clickButtonDeleteRedactorEmp(){}
    @FXML private void clickButtonSearchRedactorEmp(){}

    @FXML private void clickButtonBack() throws IOException {
        Stage stage = StageInitializer.getStageMain(mainResource);
    }

}
