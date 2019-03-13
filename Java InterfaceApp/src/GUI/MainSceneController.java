package GUI;

import Service.Service;
import GUI.View.Controller;
import GUI.View.View;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainSceneController {
    Service service;

    @FXML private Slider slider;
    @FXML private TextField textField;

    public MainSceneController(){}

    public void setController(Service service){
        this.service = service;
        saptamanaHandler();
    }

    private void newWindowStudent(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Studenti");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainSceneController.class.getResource("studentScene.fxml"));
            AnchorPane root = loader.load();

            stage.setScene(new Scene(root));
            StudentSceneController ctrl = loader.getController();
            ctrl.setController(service, stage);
            stage.initStyle(StageStyle.UNDECORATED);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void newWindowNota(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Note");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainSceneController.class.getResource("notaScene.fxml"));
            AnchorPane root = loader.load();

            stage.setScene(new Scene(root));
            NotaSceneController ctrl = loader.getController();
            ctrl.setController(service, stage);
            stage.initStyle(StageStyle.UNDECORATED);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void newWindowTema(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Teme");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainSceneController.class.getResource("temaScene.fxml"));
            AnchorPane root = loader.load();

            stage.setScene(new Scene(root));
            TemaSceneController ctrl = loader.getController();
            ctrl.setController(service, stage);
            stage.initStyle(StageStyle.UNDECORATED);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void newWindowLab7(){
        Stage stage = new Stage();
        Controller ctrl = new Controller(service);
        View v = new View(ctrl);

        stage.setTitle("Studenti");
        stage.setScene(new Scene(v.getView(),600,400));
        stage.show();
    }

    @FXML
    private void saptamanaHandler(){
        double saptamanaCurenta = slider.getValue()+1;
        int saptamana = (int)saptamanaCurenta;
        service.setareSaptamana(saptamana);
        String s = String.valueOf(saptamana);
        textField.setText(s);
    }

    @FXML
    private void studentHandler(){
        newWindowStudent();
    }

    @FXML
    private void temaHandler(){
        newWindowTema();
    }

    @FXML
    private void notaHandler(){
        newWindowNota();
    }

    @FXML
    private void lab7Handler(){
        newWindowLab7();
    }

    @FXML
    private void closeApp(){
        Platform.exit();
    }

}
