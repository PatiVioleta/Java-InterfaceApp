package GUI;

import Domain.*;
import GUI.View.Observer;
import Service.Service;
import event.ObjectEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FiltreNotaController {
    private Service service;
    private Stage stage;
    private ObservableList<Nota> observableList;
    private ObservableList<String> observableListT;

    @FXML private TableView tableView;

    @FXML private ComboBox<String> numeStudent;
    @FXML private TextField valoareNota;
    @FXML private TextField cadruDidactic;
    @FXML private TextArea textArea;

    @FXML private ComboBox<String> comboTema;
    @FXML private ComboBox<String> grupa;

    @FXML private Button filter1;
    @FXML private Button filter2;
    @FXML private Button filter3;
    @FXML private Button filter4;

    public FiltreNotaController(){}

    public void setController(Service service, Stage stage ){
        this.service = service;
        //this.service.addObserverG(this);
        this.stage = stage;
        observableList = FXCollections.observableList(StreamSupport.stream(service.findAllN().spliterator(), false).collect(Collectors.toList()));
        seteazaTeme();
        seteazaStudenti();
        seteazaGrupe();
        filter1.setTooltip(new Tooltip("Notele la o anumita tema"));
        filter2.setTooltip(new Tooltip("Notele unui student"));
        filter3.setTooltip(new Tooltip("Notele studentilor dintr-o grupa la o tema"));
        filter4.setTooltip(new Tooltip("Notele studentilor dintr-o anumita grupa"));
    }

    public void seteazaTeme(){
        observableListT = FXCollections.observableList(StreamSupport.stream(service.findAllTString().spliterator(), false).collect(Collectors.toList()));
        comboTema.setItems(observableListT);
    }

    public void seteazaStudenti(){
        observableListT = FXCollections.observableList(StreamSupport.stream(service.findAllSString().spliterator(), false).collect(Collectors.toList()));
        numeStudent.setItems(observableListT);
    }

    public void seteazaGrupe(){
        observableListT = FXCollections.observableList(StreamSupport.stream(service.findAllSGrupa().spliterator(), false).collect(Collectors.toList()));
        grupa.setItems(observableListT);
    }

    public int temaSelectata(){
        String s = comboTema.getValue();
        String[] parts = s.split("|");
        int id = Integer.parseInt(parts[0]);
        return id;
    }

    public int studentSelectat(){
        String s = numeStudent.getValue();
        return service.findOneSNume(s).getID();
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
    private void filter1Handler() {
        try {
            List<Nota> mesajeFiltrate = service.filtru1(temaSelectata());

            tableView.getColumns().clear();
            TableColumn<Nota, String> idSCol = new TableColumn<>("ID_Student");
            TableColumn<Nota, String> idTCol = new TableColumn<>("ID_Tema");
            TableColumn<Nota, String> notaCol = new TableColumn<>("Nota");
            TableColumn<Nota, String> cadruCol = new TableColumn<>("Cadru Didactic");

            tableView.getColumns().addAll(idSCol, idTCol, notaCol, cadruCol);
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            idSCol.setCellValueFactory(new PropertyValueFactory<Nota, String>("SID"));
            idTCol.setCellValueFactory(new PropertyValueFactory<Nota, String>("TID"));
            notaCol.setCellValueFactory(new PropertyValueFactory<Nota, String>("Nota"));
            cadruCol.setCellValueFactory(new PropertyValueFactory<Nota, String>("CadruDidactic"));

            tableView.setItems(FXCollections.observableList(mesajeFiltrate));
        } catch(RuntimeException e){
            errorWindow("Filtru eroare","Tema este neselectata!");
        }
    }

    @FXML
    private void filter2Handler() {
        try {
            List<Nota> mesajeFiltrate = service.filtru2(studentSelectat());

            tableView.getColumns().clear();
            TableColumn<Nota, String> idSCol = new TableColumn<>("ID_Student");
            TableColumn<Nota, String> idTCol = new TableColumn<>("ID_Tema");
            TableColumn<Nota, String> notaCol = new TableColumn<>("Nota");
            TableColumn<Nota, String> cadruCol = new TableColumn<>("Cadru Didactic");

            tableView.getColumns().addAll(idSCol, idTCol, notaCol, cadruCol);
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            idSCol.setCellValueFactory(new PropertyValueFactory<Nota, String>("SID"));
            idTCol.setCellValueFactory(new PropertyValueFactory<Nota, String>("TID"));
            notaCol.setCellValueFactory(new PropertyValueFactory<Nota, String>("Nota"));
            cadruCol.setCellValueFactory(new PropertyValueFactory<Nota, String>("CadruDidactic"));

            tableView.setItems(FXCollections.observableList(mesajeFiltrate));
        } catch(RuntimeException e){
            errorWindow("Filtru eroare","Studentul este neselectat!");
        }
    }

    @FXML
    private void filter3Handler() {
        try {
            List<Nota> mesajeFiltrate = service.filtru3(temaSelectata(),Integer.parseInt(grupa.getValue()));

            tableView.getColumns().clear();
            TableColumn<Nota, String> idSCol = new TableColumn<>("ID_Student");
            TableColumn<Nota, String> idTCol = new TableColumn<>("ID_Tema");
            TableColumn<Nota, String> notaCol = new TableColumn<>("Nota");
            TableColumn<Nota, String> cadruCol = new TableColumn<>("Cadru Didactic");

            tableView.getColumns().addAll(idSCol, idTCol, notaCol, cadruCol);
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            idSCol.setCellValueFactory(new PropertyValueFactory<Nota, String>("SID"));
            idTCol.setCellValueFactory(new PropertyValueFactory<Nota, String>("TID"));
            notaCol.setCellValueFactory(new PropertyValueFactory<Nota, String>("Nota"));
            cadruCol.setCellValueFactory(new PropertyValueFactory<Nota, String>("CadruDidactic"));

            tableView.setItems(FXCollections.observableList(mesajeFiltrate));
        } catch(RuntimeException e){
            errorWindow("Filtru eroare","Tema sau grupa este neselectata!");
        }
    }

    @FXML
    private void filter4Handler() {
        try {
            List<Student> studentiGrupa = service.filtru4Stud(Integer.parseInt(grupa.getValue()));
            List<Tema> teme =service.filtru4Teme();

            tableView.getColumns().clear();
            TableColumn<NotaDTO,String> grupaCol = new TableColumn<NotaDTO,String>("Grupa "+grupa.getValue());
            grupaCol.setCellValueFactory(new PropertyValueFactory<NotaDTO, String>("Nume"));
            tableView.getColumns().addAll(grupaCol);

            for(int i=0;i<teme.size();i++) {
                TableColumn<NotaDTO, String> temaCol = new TableColumn<NotaDTO, String>(teme.get(i).getDescriere());
                temaCol.setCellValueFactory(new PropertyValueFactory<NotaDTO, String>("T"+String.valueOf(i+1)));
                tableView.getColumns().addAll(temaCol);
            }

            List<NotaDTO> lista = service.filtru4(studentiGrupa);
            tableView.setItems(FXCollections.observableList(lista));

            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch(RuntimeException e){
            errorWindow("Filtru eroare","Tema sau grupa este neselectata!");
        }
    }

}
