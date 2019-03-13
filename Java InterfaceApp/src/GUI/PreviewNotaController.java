package GUI;

import Service.Service;
import Validator.ValidationException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PreviewNotaController {
    Service service;
    private Stage stage;

    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label;

    private String tema;
    private String student;
    private String nota;
    private double penalizari;
    private boolean motivat;
    private String feed;
    private String cadru;
    private int idT;

    public PreviewNotaController(){
    }

    public void setController(Service service, Stage stage,String tema,String student,String nota ,double penalizari,boolean motivat, String feed, String cadru, int idT ){
        this.service = service;
        this.stage = stage;
        this.tema=tema;
        this.nota=nota;
        this.student=student;
        this.penalizari=penalizari;
        this.motivat=motivat;
        this.feed=feed;
        this.cadru=cadru;
        this.idT=idT;

        label1.setText(tema);
        label2.setText(student);
        label3.setText(nota);
        label.setText("");
        label4.setText("");
        if(penalizari>0 && !motivat){
            label.setText("Penalizari");
            Double fin =Double.parseDouble(nota)-penalizari;
            if(fin>0)
                label4.setText(String.valueOf(penalizari)+"    Nota finala: "+fin.toString());
            else
                label4.setText(String.valueOf(penalizari)+"    Nota finala: 1");

        }else if(motivat){
            label.setText("Are motivare");
        }
    }

    @FXML
    private void addHandler(){
        String idS = service.findOneSNume(student).getID().toString();
        service.saveN(idS,String.valueOf(idT),nota,cadru,feed,motivat);
        stage.close();
    }

    @FXML
    private void cancelHandler(){
        stage.close();
    }
}
