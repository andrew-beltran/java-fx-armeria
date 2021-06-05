package com.mycompany.armeriafx;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        
        FXMLLoader elCargador = new FXMLLoader();
        elCargador.setLocation(MainApp.class.getResource("/fxml/Scene.fxml"));
        Parent root = elCargador.load();
        FXMLController elControlador = elCargador.getController();
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("(づ￣ 3￣)づ Armería o(^▽^)o");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
