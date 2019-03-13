package GUI;

import Domain.Student;
import Domain.Tema;
import GUI.View.Observer;
import Service.Service;
import Validator.ValidationException;
import event.ChangeEventType;
import event.ObjectEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TemaSceneController implements Observer<ObjectEvent<Tema>> {
    private Service service;
    private Stage stage;
    private ObservableList<Tema> observableList;

    @FXML
    private TableView<Tema> tableView;
    @FXML private TableColumn<Tema, String> idCol;
    @FXML private TableColumn<Tema, String> descCol;
    @FXML private TableColumn<Tema, String> deadCol;
    @FXML private TableColumn<Tema, String> primireCol;
    @FXML
    private void initialize(){
        idCol.setCellValueFactory(new PropertyValueFactory<Tema, String>("ID"));
        descCol.setCellValueFactory(new PropertyValueFactory<Tema, String>("Descriere"));
        deadCol.setCellValueFactory(new PropertyValueFactory<Tema, String>("Deadline"));
        primireCol.setCellValueFactory(new PropertyValueFactory<Tema, String>("Primire"));
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void setController(Service service, Stage stage ){
        this.service = service;
        this.service.addObserverF(this);
        this.stage = stage;
        observableList = FXCollections.observableList(StreamSupport.stream(service.findAllT().spliterator(), false).collect(Collectors.toList()));
        loadData();
    }

    @Override
    public void update(ObjectEvent<Tema> s) {
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

    public void addTema(int id, String desc,int dead, int prim) throws ValidationException {
        Tema s = new Tema(id, desc, dead, prim);
        service.saveT(s);
    }

    @FXML
    private void loadData(){
        observableList = FXCollections.observableList(StreamSupport.stream(service.findAllT().spliterator(), false).collect(Collectors.toList()));
        tableView.setItems(observableList);
    }

    @FXML
    private void addHandler(){

    }

    @FXML
    private void cancelHandler(){
        stage.close();
    }
}
