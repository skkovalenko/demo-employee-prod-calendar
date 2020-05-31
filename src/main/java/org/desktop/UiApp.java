package org.desktop;

import javafx.application.Application;
import org.desktop.parse.DataProdCalendar;
import org.desktop.parse.ParseCSV;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;

@SpringBootApplication
public class UiApp {
    public static void main(String[] args) throws IOException {
        Application.launch(FxApp.class, args);

    }
}
