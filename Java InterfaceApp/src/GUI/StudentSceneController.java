package GUI;

import Domain.Student;
import Service.Service;
import Validator.ValidationException;
import event.ChangeEventType;
import GUI.View.Observer;
import event.ObjectEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StudentSceneController implements Observer<ObjectEvent<Student>> {
    private Service service;
    private Stage stage;
    private ObservableList<Student> observableList;

    @FXML private TableView<Student> tableView;
    @FXML private TableColumn<Student, String> idCol;
    @FXML private TableColumn<Student, String> numeCol;
    @FXML private TableColumn<Student, String> grupaCol;
    @FXML private TableColumn<Student, String> emailCol;

    @FXML
    private void initialize(){
        idCol.setCellValueFactory(new PropertyValueFactory<Student, String>("ID"));
        numeCol.setCellValueFactory(new PropertyValueFactory<Student, String>("nume"));
        grupaCol.setCellValueFactory(new PropertyValueFactory<Student, String>("grupa"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void setController(Service service, Stage stage ){
        this.service = service;
        this.service.addObserverE(this);
        this.stage = stage;
        observableList = FXCollections.observableList(StreamSupport.stream(service.findAllS().spliterator(), false).collect(Collectors.toList()));
        loadData();
    }

    @Override
    public void update(ObjectEvent<Student> s) {
        if(s.getType() == ChangeEventType.ADD){
            observableList.add(s.getData());
        }
        if(s.getType() == ChangeEventType.UPDATE){
            observableList.remove(s.getOldData());
            observableList.add(s.getData());
        }
        if(s.getType() == ChangeEventType.DELETE){
            observableList.remove(s.getData());
        }
    }

    public ObservableList getList(){
        return observableList;
    }

    public void addStudent(int id, String nume,int grupa, String email) throws ValidationException {
        Student s = new Student(id, nume, grupa, email);
        service.saveS(s);
    }

    public void updateStudent(int id, String nume,int grupa, String email) throws ValidationException {
        Student s = new Student(id, nume, grupa, email);
        service.updateS(s);
    }

    public void deleteStudent(int id) throws ValidationException {
        service.deleteS(id);
    }

    @FXML
    private void loadData(){
        observableList = FXCollections.observableList(StreamSupport.stream(service.findAllS().spliterator(), false).collect(Collectors.toList()));
        tableView.setItems(observableList);
     }

    private void newWindow(Student s){

        try {
            Stage stage = new Stage();
            stage.setTitle("Edit Student");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StudentSceneController.class.getResource("studentSceneEdit.fxml"));
            AnchorPane root = loader.load();

            stage.setScene(new Scene(root));
            StudentSceneEditController controller = loader.getController();
            controller.setService(service, s, stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addHandler(){
        newWindow(new Student(0, "", 0,""));
    }

    @FXML
    private void updateHandler(){
        Student s = tableView.getSelectionModel().getSelectedItem();
        if (s == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nicio entitate selectata");
            alert.show();
        }else {
            newWindow(s);
        }
    }

    @FXML
    private void deleteHandler(){
        Student s = tableView.getSelectionModel().getSelectedItem();
        if (s == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nicio entitate selectata");
            alert.show();
        }else {
            service.deleteS(s.getID());
        }
    }

    @FXML
    private void cancelHandler(){
        stage.close();
    }
}
