import Repo.NotaFileRepository;
import Repo.StudentFileRepository;
import Repo.TemaFileRepository;
import Service.Service;
import Validator.NotaValidator;
import Validator.StudentValidator;
import Validator.TemaValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        StudentFileRepository repoS = new StudentFileRepository(new StudentValidator(),"src/data/student.txt");

        TemaValidator tV = new TemaValidator();
        TemaFileRepository repoT = new TemaFileRepository(tV,"src/data/tema.txt");

        NotaValidator nV = new NotaValidator();
        NotaFileRepository repoN = new NotaFileRepository(nV,"src/data/nota.txt");

        Service service = new Service(repoS, repoT, repoN);

        //Controller controller = new Controller(service);
        //GUI.View viewRoot = new GUI.View(controller);

        Parent root; // = FXMLLoader.load(getClass().getResource("GUI/sample.fxml"));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("GUI/mainScene.fxml"));
        root = loader.load();
        GUI.MainSceneController ctrl = loader.getController();
        ctrl.setController(service);
        primaryStage.setTitle("MainScene");
        primaryStage.setScene(new Scene(root, 546, 400));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
