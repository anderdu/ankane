package ehu.project.controller.ui;

import ehu.project.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class IzenakHautatuKud implements Initializable {


    @FXML
    private Label jokalariBat;
    @FXML
    private Label jokalariBi;
    @FXML
    private TextField jokalariBatText;
    @FXML
    private TextField jokalariBiText;


    @FXML
    private Label title;




    private Main mainApp;
    private String nondik;



    private static IzenakHautatuKud instantzia = new IzenakHautatuKud();

    public static IzenakHautatuKud getInstantzia() {
        return instantzia;
    }

    public void setMainApp(Main main) {
        this.mainApp = main;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void hasieratu(String n){
        this.nondik=n;
        if(nondik.equals("BiJok")){
            jokalariBat.setText("1 Jokalariaren izena:");
            jokalariBatText.setVisible(true);
            jokalariBiText.setVisible(true);
            title.setText("2 Jokalari");
        }else if(nondik.equals("JOErraza")){
            jokalariBat.setText("Jokalariaren izena:");
            jokalariBi.setVisible(false);
            jokalariBiText.setVisible(false);
            title.setText("1 Jokalari VS Ordenagailu - Erraza");
        }else if(nondik.equals("JOZaila")){
            jokalariBat.setText("Jokalariaren izena:");
            jokalariBi.setVisible(false);
            jokalariBiText.setVisible(false);
            title.setText("1 Jokalari VS Ordenagailu - Zaila");
        }else{
            System.out.println("Erroreren bat egon da");
        }
    }


    public void ok(ActionEvent actionEvent) {
        String j1 = jokalariBat.getText();
        if (j1.length()!=0) {
            if(nondik.equals("BiJok")){ //Bigarren jokalaria dugu
                String j2 = jokalariBi.getText();
                if (j2.length()!=0) {
                    mainApp.tableroaKargatu(j1, j2,nondik);
                }
            }else{
                if(nondik.equals("JOErraza")||nondik.equals("JOZaila")){
                    mainApp.tableroaKargatu(j1,null,nondik);
                }
            }

        }
    }

}