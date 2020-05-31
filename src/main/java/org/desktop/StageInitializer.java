package org.desktop;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<FxApp.StageReadyEvent> {

    @Value("classpath:/view/fxml/main.fxml")
    private Resource mainResource;
    private String appTitle;
    private static ApplicationContext applicationContext;

    private static Stage stage;


    public StageInitializer(@Value("${spring.application.ui.title}") String appTitle,
                            ApplicationContext applicationContext) {
        this.appTitle = appTitle;
        StageInitializer.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(FxApp.StageReadyEvent event) {
        try {
            Stage stage = event.getStage();
            FXMLLoader loader = new FXMLLoader(mainResource.getURL());
            loader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
            Parent parent = loader.load();
            stage.setScene(new Scene(parent));
            stage.setTitle(appTitle);
            stage.show();
            StageInitializer.stage = stage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Stage getStageMain(Resource resource) throws IOException {
        FXMLLoader loader = new FXMLLoader(resource.getURL());
        loader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
        Parent parent = loader.load();
        stage.setScene(new Scene(parent));
        stage.show();
        return stage;
    }
}
