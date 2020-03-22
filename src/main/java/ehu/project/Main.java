package ehu.project;

import ehu.project.Klaseak.Taula;
import ehu.project.db.DBKud;
import ehu.project.db.DBKudeatzaileSQLITE;
import ehu.project.ui.TableroaKud;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Time;

public class Main extends Application {

  private Parent tableroa;

  private Stage stage;

  private TableroaKud tableroaKud;


  @Override
  public void start(Stage primaryStage) throws Exception {
    stage = primaryStage;
    pantailakKargatu();

    stage.setTitle("4ncane");
    tableroaKud.hasieratu("Jokalari 1","Jokalari 2","BiJok");

    stage.setScene(new Scene(tableroa));
    stage.setMaximized(true);
    stage.setWidth(610);
    stage.setHeight(640);
    stage.show();
  }

  private void pantailakKargatu() throws IOException {

    FXMLLoader loaderTablero = new FXMLLoader(getClass().getResource("/view/tableroa.fxml"));
    tableroa = (Parent) loaderTablero.load();
    tableroaKud = loaderTablero.getController();
    tableroaKud.setMainApp(this);
  }


  public static void main(String[] args) {
    launch(args);
  }


  public void tableroaKargatu(String j1,String j2,String nondik){
    try {
      pantailakKargatu();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Parent root = new AnchorPane(tableroa);
    tableroaKud.hasieratu(j1,j2,nondik);
    stage.setScene(new Scene(root));
    stage.show();
  }

public void pantailaTxikitu(){
 // stage.setWidth(610);
 // stage.setHeight(640);
}
public void pantailaHanditu(){
  //stage.setWidth(910);
  //stage.setHeight(640);
}


}