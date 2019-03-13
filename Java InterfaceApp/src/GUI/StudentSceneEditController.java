package GUI;

import Domain.Student;
import Service.Service;
import Validator.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StudentSceneEditController {
    private Stage stage;
    private Service service;
    private Student currentStudent;

    @FXML private TextField idText;
    @FXML private TextField numeText;
    @FXML private TextField grupaText;
    @FXML private TextField emailText;

    @FXML private Button functionButton;

    public StudentSceneEditController(){}

    public void setService(Service service, Student s, Stage stage){
        this.service=service;
        this.currentStudent = s;
        this.stage=stage;
        fillWithDate();
    }

    private void fillWithDate(){
        if(currentStudent.getID()==0){
            functionButton.setText("Adauga");
            //idText.setText("");

        }else{
            functionButton.setText("Modifica");
            idText.setText(currentStudent.getID().toString());
            idText.setEditable(false);
            numeText.setText(currentStudent.getNume());
            grupaText.setText(currentStudent.getGrupa().toString());
            emailText.setText(currentStudent.getEmail());
        }
    }

    @FXML
    private void functionButtonHandler(){
        try {
            if(currentStudent.getID()==0) {
                service.saveS(new Student(Integer.parseInt(idText.getText()), numeText.getText(),Integer.parseInt(grupaText.getText()),emailText.getText()));

                stage.close();
            }else{
                service.updateS(new Student(Integer.parseInt(idText.getText()), numeText.getText(),Integer.parseInt(grupaText.getText()),emailText.getText()));

                stage.close();
            }
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error Message");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void clearAllHandler(){
        if(currentStudent.getID()==0){
            idText.setText("");
        }
        numeText.setText("");
        grupaText.setText("");
        emailText.setText("");
    }

    @FXML
    private void cancelHandler(){
        stage.close();
    }
}
