package GUI.View;

import Domain.Student;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class View {

    private TableView<Student> tableView;

    private TextField idText;
    private TextField numeText;
    private TextField grupaText;
    private TextField emailText;

    private Controller controller;

    //StackPane
    //GreedPane
    //Box
    public View(Controller controller){
        tableView = new TableView<>();
        this.controller = controller;
    }

    //toata fereastra
    public BorderPane getView(){
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(createTable());
        borderPane.setRight(createStudent());
        return borderPane;
    }

    private void initTableView(){
        tableView.setItems(controller.getList());

        //creaza coloanele pentru fiecare atribut
        TableColumn<Student, String> idColumn = new TableColumn<>("Id");
        TableColumn<Student, String> numeColumn = new TableColumn<>("Nume");
        TableColumn<Student, String> grupaColumn = new TableColumn<>("Grupa");
        TableColumn<Student, String> emailColumn = new TableColumn<>("Email");

        tableView.getColumns().addAll(idColumn, numeColumn, grupaColumn, emailColumn);

        //incarca datele din repo
        idColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("ID"));
        numeColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("nume"));
        grupaColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("grupa"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));

        //incarca datele in TextFiled-uri la selectare din tabel
        tableView.getSelectionModel().selectedItemProperty().addListener((observer, oldData, newData)-> showDetails(newData));
    }

    private void showDetails(Student s){
        if(s!=null){
            idText.setText(s.getID().toString());
            numeText.setText(s.getNume());
            grupaText.setText(s.getGrupa().toString());
            emailText.setText(s.getEmail());
        }
    }

    private StackPane createTable(){
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(tableView);
        initTableView();
        return stackPane;
    }

    private GridPane createStudent(){
        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Id"), 0, 0);
        gridPane.add(idText = new TextField(), 1, 0);
        gridPane.add(new Label("Nume"), 0, 1);
        gridPane.add(numeText = new TextField(), 1, 1);
        gridPane.add(new Label("Grupa"), 0, 2);
        gridPane.add(grupaText = new TextField(), 1, 2);
        gridPane.add(new Label("Email"), 0, 3);
        gridPane.add(emailText = new TextField(), 1, 3);

        HBox buttonsBox = new HBox();
        Button add = new Button("Add");
        add.setOnAction(event -> {
            this.addHandler();
        });
        buttonsBox.getChildren().add(add);
        Button clearAll = new Button("Clear All");
        clearAll.setOnAction(event -> {
            this.clearAllHandler();
        });
        buttonsBox.getChildren().add(clearAll);

        Button update = new Button("Update");
        update.setOnAction(event -> {
            this.updateHandler();
        });
        buttonsBox.getChildren().add(update);
        Button delete = new Button("Delete");
        delete.setOnAction(event -> {
            this.deleteHandler();
        });
        buttonsBox.getChildren().add(delete);
        gridPane.add(buttonsBox, 0, 4,2, 1);
        return gridPane;
    }

    private void addHandler(){
        try {
            controller.addStudent(Integer.parseInt(idText.getText()), numeText.getText(), Integer.parseInt(grupaText.getText()), emailText.getText());
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Student error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
    private void deleteHandler(){
        try {
            controller.deleteStudent(Integer.parseInt(idText.getText()));
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Student error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private void clearAllHandler(){
        idText.setText("");
        numeText.setText("");
        grupaText.setText("");
        emailText.setText("");
    }

    private void updateHandler(){
        try {
            controller.updateStudent(Integer.parseInt(idText.getText()), numeText.getText(), Integer.parseInt(grupaText.getText()), emailText.getText());
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Student error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
}
