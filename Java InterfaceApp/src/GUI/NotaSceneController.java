package GUI;

import Domain.Nota;
import Domain.Tema;
import GUI.View.Observer;
import Service.Service;
import Validator.NotaValidator;
import Validator.RepetitionException;
import Validator.ServiceException;
import Validator.ValidationException;
import event.ChangeEventType;
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
import javafx.stage.StageStyle;
import javafx.util.Pair;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class NotaSceneController implements Observer<ObjectEvent<Nota>> {
    private Service service;
    private Stage stage;
    private ObservableList<Nota> observableList;
    private ObservableList<String> observableListT;

    @FXML private TableView<Nota> tableView;
    @FXML private TableColumn<Nota, String> idSCol;
    @FXML private TableColumn<Nota, String> idTCol;
    @FXML private TableColumn<Nota, String> notaCol;
    @FXML private TableColumn<Nota, String> cadruCol;

    @FXML private ComboBox<String> comboTema;

    @FXML private RadioButton motivRadioBtn;
    @FXML private RadioButton nemotivRadioBtn;
    private ToggleGroup toggleRadio = new ToggleGroup();

    @FXML private TextField saptamanaCurenta;
    @FXML private ComboBox<String> numeStudent;
    @FXML private TextField valoareNota;
    @FXML private TextField cadruDidactic;
    @FXML private TextArea textArea;

    public NotaSceneController(){}

    @FXML
    private void initialize(){
        idSCol.setCellValueFactory(new PropertyValueFactory<Nota, String>("SID"));
        idTCol.setCellValueFactory(new PropertyValueFactory<Nota, String>("TID"));
        notaCol.setCellValueFactory(new PropertyValueFactory<Nota, String>("Nota"));
        cadruCol.setCellValueFactory(new PropertyValueFactory<Nota, String>("CadruDidactic"));
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        motivRadioBtn.setToggleGroup(toggleRadio);
        nemotivRadioBtn.setToggleGroup(toggleRadio);

        saptamanaCurenta.setText(String.valueOf(service.getSaptamana()));
    }

    public void setController(Service service, Stage stage ){
        this.service = service;
        this.service.addObserverG(this);
        this.stage = stage;
        observableList = FXCollections.observableList(StreamSupport.stream(service.findAllN().spliterator(), false).collect(Collectors.toList()));
        loadData();
        seteazaTeme();
        seteazaStudenti();
        nemotivRadioBtn.fire();
    }

    public void seteazaTeme(){
        observableListT = FXCollections.observableList(StreamSupport.stream(service.findAllTString().spliterator(), false).collect(Collectors.toList()));
        comboTema.setItems(observableListT);
        try {
            Tema x = service.findOneTPredare();
            comboTema.setValue(x.getID() + " | Descriere: " + x.getDescriere() + " | Primire: " + x.getPrimire() + " | Predare: " + x.getDeadline());
        }catch (RuntimeException err){}
    }

    public void seteazaStudenti(){
        observableListT = FXCollections.observableList(StreamSupport.stream(service.findAllSString().spliterator(), false).collect(Collectors.toList()));
        numeStudent.setItems(observableListT);
    }

    public int temaSelectata(){
        String s = comboTema.getValue();
        String[] parts = s.split("|");
        int id = Integer.parseInt(parts[0]);
        return id;
    }

    @FXML
    public void actualizareFeed(){
        try {
            double puncte = service.puncteMinus(temaSelectata());
            if (puncte > 0 && nemotivRadioBtn.isSelected())
                textArea.setText("Nota a fost diminuata cu " + String.valueOf(puncte) + " puncte datorita intarzierilor");
            else
                textArea.setText("");
        }
        catch (ServiceException e){
            errorWindow("Nota Eroare",e.getMessage());
        }
        catch (RuntimeException e){
            //errorWindow("Nota Eroare",e.getMessage());
        }
    }

    @Override
    public void update(ObjectEvent<Nota> s) {
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

    @FXML
    private void loadData(){
        observableList = FXCollections.observableList(StreamSupport.stream(service.findAllN().spliterator(), false).collect(Collectors.toList()));
        tableView.setItems(observableList);
    }

    @FXML
    private void addHandler(){
        try{
            String tema = comboTema.getValue();
            String student = numeStudent.getValue();
            String nota = valoareNota.getText();
            String cadru = cadruDidactic.getText();
            double penalizari = service.puncteMinus(temaSelectata());
            boolean motivat=motivRadioBtn.isSelected();
            String feed = textArea.getText();

            Pair<Integer,Integer> id = new Pair<>(service.findOneSNume(student).getID(), temaSelectata());
            Nota n= new Nota(id,Float.parseFloat(nota),cadru);
            NotaValidator v = new NotaValidator();
            v.validate(n);
            if(service.findOneN(id)!=null)
                throw new RepetitionException("Tema a fost deja predata!");
            Double fin =Double.parseDouble(nota)-penalizari;

            Stage stage = new Stage();
            stage.setTitle("Preview");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainSceneController.class.getResource("previewNota.fxml"));
            AnchorPane root = loader.load();

            stage.setScene(new Scene(root));
            PreviewNotaController ctrl = loader.getController();
            ctrl.setController(service, stage, tema, student, nota , penalizari, motivat, feed, cadru, temaSelectata());
            stage.initStyle(StageStyle.UNDECORATED);

            stage.show();
        }
        catch (IOException e) {
            //e.printStackTrace();
        }
        catch (NumberFormatException e){
            errorWindow("Nota Eroare","Nota invalida!");
        }
        catch (RuntimeException e){
            errorWindow("Nota Eroare",e.getMessage());
        }
    }

    private void errorWindow(String text, String m){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(text);
        alert.setContentText(m);
        alert.show();
    }

    @FXML
    private void cancelHandler(){
        stage.close();
    }

    @FXML
    private void filtreHandler() {
        try {
            Stage stage = new Stage();
            stage.setTitle("Filtre nota");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(NotaSceneController.class.getResource("filtreNota.fxml"));
            AnchorPane root = loader.load();

            stage.setScene(new Scene(root));
            FiltreNotaController controller = loader.getController();
            controller.setController(service, stage);
            stage.initStyle(StageStyle.UNDECORATED);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
