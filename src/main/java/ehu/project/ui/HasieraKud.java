package ehu.project.controller.ui;

import ehu.project.Main;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class HasieraKud implements Initializable {

    private Main mainApp;

    private static HasieraKud instantzia = new HasieraKud();

    public static HasieraKud getInstantzia() {
        return instantzia;
    }



    public void setMainApp(Main main) {
        this.mainApp = main;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void biJok(ActionEvent actionEvent) {
        mainApp.izenakHautatuKargatu("BiJok");
    }

    public void JOErraza(ActionEvent actionEvent) {
        mainApp.izenakHautatuKargatu("JOErraza");
    }

    public void JOZaila(ActionEvent actionEvent) {
        mainApp.izenakHautatuKargatu("JOZaila");
    }





}