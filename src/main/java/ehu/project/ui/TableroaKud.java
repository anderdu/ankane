package ehu.project.ui;

import ehu.project.Klaseak.Taula;
import ehu.project.Main;
import ehu.project.db.DBKud;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class TableroaKud implements Initializable {

    public RadioMenuItem menuBiJok;
    public RadioMenuItem menuJOErraza;
    public RadioMenuItem menuJOZaila;
    public ToggleGroup toggleGroup;
    public ImageView flechaAzul;
    public ImageView flechaRoja;
    public ImageView suziri1;
    public ImageView suziri2;
    public ImageView suziri3;
    public ImageView suziri4;
    public ImageView suziri5;
    public ImageView suziri6;
    public ImageView triste;
    public Label irabaziLabel;

    //ranking-a
    public Button berrizB;
    public Button rankingB;
    public Label labelRanking;
    public Label labelIzena;
    public TextField IzenaSartu;
    public Button OK;
    //taula
    public TableView taula;
    public TableColumn<Taula,Integer> tRank;
    public TableColumn<Taula,String> tIzen;
    public TableColumn<Taula,Time> tDenb;


    private Main mainApp;

    private int jokalaria;

    private static boolean[][] tableroa;
    private static boolean[][] gorri;
    private static boolean[][] urdin;

    //botoiak
    public Button b0;
    public Button b01;
    public Button b02;
    public Button b03;
    public Button b04;
    public Button b05;
    public Button b06;
    public Button b07;
    public Button b08;

    public Button actualButton=null;


    //cronometrorako
    @FXML
    private Label timerLabel = new Label();
    public Label denboraLabel;
    private boolean stop=false;

    @FXML
    private Circle k00; //columna,linea
    @FXML
    private Circle k01;
    @FXML
    private Circle k02;
    @FXML
    private Circle k03;
    @FXML
    private Circle k04;
    @FXML
    private Circle k05;

    @FXML
    private Circle k10; //columna,linea
    @FXML
    private Circle k11;
    @FXML
    private Circle k12;
    @FXML
    private Circle k13;
    @FXML
    private Circle k14;
    @FXML
    private Circle k15;

    @FXML
    private Circle k20; //columna,linea
    @FXML
    private Circle k21;
    @FXML
    private Circle k22;
    @FXML
    private Circle k23;
    @FXML
    private Circle k24;
    @FXML
    private Circle k25;

    @FXML
    private Circle k30; //columna,linea
    @FXML
    private Circle k31;
    @FXML
    private Circle k32;
    @FXML
    private Circle k33;
    @FXML
    private Circle k34;
    @FXML
    private Circle k35;

    @FXML
    private Circle k40; //columna,linea
    @FXML
    private Circle k41;
    @FXML
    private Circle k42;
    @FXML
    private Circle k43;
    @FXML
    private Circle k44;
    @FXML
    private Circle k45;

    @FXML
    private Circle k50; //columna,linea
    @FXML
    private Circle k51;
    @FXML
    private Circle k52;
    @FXML
    private Circle k53;
    @FXML
    private Circle k54;
    @FXML
    private Circle k55;

    @FXML
    private Circle k60; //columna,linea
    @FXML
    private Circle k61;
    @FXML
    private Circle k62;
    @FXML
    private Circle k63;
    @FXML
    private Circle k64;
    @FXML
    private Circle k65;

    @FXML
    private Circle k70; //columna,linea
    @FXML
    private Circle k71;
    @FXML
    private Circle k72;
    @FXML
    private Circle k73;
    @FXML
    private Circle k74;
    @FXML
    private Circle k75;

    @FXML
    private Circle k80; //columna,linea
    @FXML
    private Circle k81;
    @FXML
    private Circle k82;
    @FXML
    private Circle k83;
    @FXML
    private Circle k84;
    @FXML
    private Circle k85;


    @FXML
    private Label title;

    @FXML
    private Label jokalariTxanda;

    private String jok1;
    private String jok2;
    private String nondik;
    private boolean blokeatuta;
    private ImageView w=new ImageView();

    private static TableroaKud instantzia = new TableroaKud();

    public static TableroaKud getInstantzia() {
        return instantzia;
    }

    public void setMainApp(Main main) {
        this.mainApp = main;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        suziri1.setVisible(false);
        suziri2.setVisible(false);
        suziri3.setVisible(false);
        suziri4.setVisible(false);
        suziri5.setVisible(false);
        suziri6.setVisible(false);
        triste.setVisible(false);
        irabaziLabel.setTextFill(Color.YELLOW); //no se por que no hace
        irabaziLabel.setVisible(false);
        berrizB.setVisible(false);
    }



    public void hasieratu(String j1,String j2,String n){
        stop=false;
        this.nondik=n;
        jokalaria = 1;
        if(nondik.equals("BiJok")){ //2 jokalariak ezarri eta txanda ezarri
            toggleGroup.selectToggle(menuBiJok);
            jok1 = j1;
            jok2 = j2;
            jokalariTxanda.setText(j1);
            title.setText("2 Jokalari");
            denboraLabel.setVisible(false);
            timerLabel.setVisible(false);
        }else if(nondik.equals("JOErraza")){ //jokalari soila. Beste jokalaria ordenagailua da
            toggleGroup.selectToggle(menuJOErraza);
            jok1 = j1;
            jok2 = "Ordenagailua";
            jokalariTxanda.setText(j1);
            title.setText("1 Jokalari VS Ordenagailu - Erraza");
            denboraLabel.setVisible(true);
            timerLabel.setVisible(true);
            start();
        }else if(nondik.equals("JOZaila")){
            toggleGroup.selectToggle(menuJOZaila);
            jok1 = j1;
            jok2 = "Ordenagailua";
            jokalariTxanda.setText(j1);
            title.setText("1 Jokalari VS Ordenagailu - Zaila");
            denboraLabel.setVisible(true);
            timerLabel.setVisible(true);
            start();
        }else{
            System.out.println("Erroreren bat egon da");
        }

        //Kasilla guztiak zuriak jarri. Berrabiarazten bada jokoa edo
        k00.setFill(Color.WHITE); k01.setFill(Color.WHITE); k02.setFill(Color.WHITE); k03.setFill(Color.WHITE); k04.setFill(Color.WHITE); k05.setFill(Color.WHITE);k10.setFill(Color.WHITE);k11.setFill(Color.WHITE);k12.setFill(Color.WHITE); k13.setFill(Color.WHITE);k14.setFill(Color.WHITE); k15.setFill(Color.WHITE); k20.setFill(Color.WHITE);  k21.setFill(Color.WHITE);k22.setFill(Color.WHITE); k23.setFill(Color.WHITE); k24.setFill(Color.WHITE);k25.setFill(Color.WHITE);k30.setFill(Color.WHITE); k31.setFill(Color.WHITE); k32.setFill(Color.WHITE);k33.setFill(Color.WHITE);k34.setFill(Color.WHITE); k35.setFill(Color.WHITE);k40.setFill(Color.WHITE); k41.setFill(Color.WHITE);k42.setFill(Color.WHITE);k43.setFill(Color.WHITE); k44.setFill(Color.WHITE);k45.setFill(Color.WHITE);k50.setFill(Color.WHITE); k51.setFill(Color.WHITE);k52.setFill(Color.WHITE);k53.setFill(Color.WHITE); k54.setFill(Color.WHITE);k55.setFill(Color.WHITE);k60.setFill(Color.WHITE); k61.setFill(Color.WHITE);k62.setFill(Color.WHITE);k63.setFill(Color.WHITE);k64.setFill(Color.WHITE);k65.setFill(Color.WHITE);k70.setFill(Color.WHITE);k71.setFill(Color.WHITE);k72.setFill(Color.WHITE);k73.setFill(Color.WHITE);k74.setFill(Color.WHITE);k75.setFill(Color.WHITE);k80.setFill(Color.WHITE); k81.setFill(Color.WHITE);k82.setFill(Color.WHITE);k83.setFill(Color.WHITE);k84.setFill(Color.WHITE);k85.setFill(Color.WHITE);
        //metodo de get izena del primero y del segundo
        tableroa = new boolean[9][6];
        gorri = new boolean[9][6];
        urdin = new boolean[9][6];
        for (int i=0;i<tableroa.length;i++){
            for (int j=0;j<tableroa[i].length;j++){
                tableroa[i][j] = false;
            }
        }

        //ranking-eko gauzak
        berrizB.setVisible(false);
        rankingB.setVisible(false);
        taula.setVisible(false);
        labelRanking.setVisible(false);
        labelIzena.setVisible(false);
        IzenaSartu.setVisible(false);
        OK.setVisible(false);

        blokeatuta=false;
    }

    public Circle kasillaLortu(int zutabea, int ilara){
        if(zutabea==0){ if(ilara==0){return k00;}else if(ilara==1){return k01;} else if(ilara==2){return k02;} else if(ilara==3){return k03;} else if(ilara==4){return k04;} else if(ilara==5){return k05;}
        } else if(zutabea==1){if(ilara==0){return k10;} else if(ilara==1){return k11;} else if(ilara==2){return k12;} else if(ilara==3){return k13;} else if(ilara==4){return k14;} else if(ilara==5){return k15;}
        } else if(zutabea==2){if(ilara==0){return k20;} else if(ilara==1){return k21;} else if(ilara==2){return k22;} else if(ilara==3){return k23;} else if(ilara==4){return k24;} else if(ilara==5){return k25;}
        } else if(zutabea==3){if(ilara==0){return k30;} else if(ilara==1){return k31;} else if(ilara==2){return k32;} else if(ilara==3){return k33;} else if(ilara==4){return k34;} else if(ilara==5){return k35;}
        } else if(zutabea==4){if(ilara==0){return k40;} else if(ilara==1){return k41;} else if(ilara==2){return k42;} else if(ilara==3){return k43;} else if(ilara==4){return k44;} else if(ilara==5){return k45;}
        } else if(zutabea==5){ if(ilara==0){return k50;} else if(ilara==1){return k51;} else if(ilara==2){return k52;} else if(ilara==3){return k53;} else if(ilara==4){return k54;} else if(ilara==5){return k55;}
        } else if(zutabea==6){if(ilara==0){return k60;} else if(ilara==1){return k61;} else if(ilara==2){return k62;} else if(ilara==3){return k63;} else if(ilara==4){return k64;} else if(ilara==5){return k65;}
        } else if(zutabea==7){ if(ilara==0){return k70;} else if(ilara==1){return k71;} else if(ilara==2){return k72;} else if(ilara==3){return k73;} else if(ilara==4){return k74;} else if(ilara==5){return k75;}
        } else if(zutabea==8){ if(ilara==0){return k80;} else if(ilara==1){return k81;} else if(ilara==2){return k82;} else if(ilara==3){return k83;} else if(ilara==4){return k84;} else if(ilara==5){return k85;}
        }

        return null;
    }


    public void fitxaSartu(int zutabea, int ilara) {
        Circle kasilla= kasillaLortu(zutabea,ilara);
        tableroa[zutabea][ilara] = true;
        if (jokalaria == 1) {
            kasilla.setFill(Color.RED);
            gorri[zutabea][ilara] = true;
            konprobatu4EnRayaDagoen(zutabea,ilara,"gorri");
            jokalaria++;
            jokalariTxanda.setText(jok2);

            if(jokalariTxanda.getText().equals("Ordenagailua")){
                Timer timer = new Timer();
                blokeatuta=true;
                timer.schedule(new RemindTask(), 1000);
            }
            if(actualButton!=null){
                actualButton.setGraphic(flechaAzul);
            }
        } else {
            kasilla.setFill(Color.BLUE);
            urdin[zutabea][ilara] = true;
            konprobatu4EnRayaDagoen(zutabea,ilara,"urdin");
            jokalaria--;
            jokalariTxanda.setText(jok1);
            if(actualButton!=null){
                actualButton.setGraphic(flechaRoja);
            }
        }
    }

    public void ordenagailuarenTxanda() {
        if(nondik.equals("JOErraza")){
            ordenagailuaZutabeBatHautatuAleatorioki(); //Ordenagailu erraza

        }else if(nondik.equals("JOZaila")){
            //Ordenagailu ZAILA
            ordenagailuaZutabeaHautatuInteligentziaArtifizialaErabiliz();

        }else{
            System.out.println("Erroreren bat egon da");
        }
    }



    public void ordenagailuaZutabeBatHautatuAleatorioki(){ //Ordenagailu ERRAZA
        Random r = new Random();//0-8 artean zenbaki bat hartuko du. Zutabe bati egingo dio erreferentzia zenbakia. Beraz ateratako zenbakian sartuko du fitxa
        int zenbakia = r.nextInt(9);
        botoiFitxaSartu(zenbakia);
    }


    public void ordenagailuaZutabeaHautatuInteligentziaArtifizialaErabiliz() {




        //Lo de IA. Elegir en que zutabe tiene que meter la ficha











    }


    public void botoiFitxaSartu(int zutabea){
        if (!tableroa[zutabea][5]) { //false --> EZ dago beteta. ilara=5, behekoa da. Beraz lehenengo, behekoa beteta dagoen begiratuko du, beteta badago, goikora joango da...
            fitxaSartu(zutabea,5);
        } else if (!tableroa[zutabea][4]) {
            fitxaSartu(zutabea,4);
        } else if (!tableroa[zutabea][3]) {
            fitxaSartu(zutabea,3);
        } else if (!tableroa[zutabea][2]) {
            fitxaSartu(zutabea,2);
        } else if (!tableroa[zutabea][1]) {
            fitxaSartu(zutabea,1);
        } else if (!tableroa[zutabea][0]) {
            fitxaSartu(zutabea,0);
        }else{
            //Beteta dago
            zutabeGuztiakBetetaKonprobatu(); //Konprobatu badaudela beteta ez dauden kasillak
            if (jokalaria == 2) {
                if (jok2.equals("Ordenagailua")) {//Ordenagailua bada zutabe hau hautatu duena, beste zutabe bat hautatu beharko du berriro ere
                    ordenagailuarenTxanda();
                } else {
                    System.out.println("Hautatu duzun zutabea beteta dago jada, beste zutabe bat hauta ezazu!");
                }
            }else {
                System.out.println("Hautatu duzun zutabea beteta dago jada, beste zutabe bat hauta ezazu!");
            }
        }
    }


    public void b0(ActionEvent actionEvent) {
        if(!blokeatuta) { botoiFitxaSartu(0); }

    }
    public void b1(ActionEvent actionEvent) {
        if(!blokeatuta) { botoiFitxaSartu(1); }
    }

    public void b2(ActionEvent actionEvent) {
        if(!blokeatuta) { botoiFitxaSartu(2); }
    }

    public void b3(ActionEvent actionEvent) {
        if(!blokeatuta) { botoiFitxaSartu(3); }
    }

    public void b4(ActionEvent actionEvent) {
        if(!blokeatuta) { botoiFitxaSartu(4); }
    }

    public void b5(ActionEvent actionEvent) {
        if(!blokeatuta) { botoiFitxaSartu(5); }
    }

    public void b6(ActionEvent actionEvent) {
        if(!blokeatuta) { botoiFitxaSartu(6); }
    }

    public void b7(ActionEvent actionEvent) {
        if(!blokeatuta) { botoiFitxaSartu(7); }
    }

    public void b8(ActionEvent actionEvent) {
        if(!blokeatuta) { botoiFitxaSartu(8); }
    }





    public void zutabeGuztiakBetetaKonprobatu(){
        if (tableroa[8][0] && tableroa[7][0] && tableroa[6][0] && tableroa[5][0] && tableroa[4][0] && tableroa[3][0] && tableroa[2][0] && tableroa[1][0] && tableroa[0][0]) {
            jokoaAmaituDa(); //Guztiak beteta daude - beraz jokoa amaitu da eta ez du inork irabazi
        }
    }


    public void jokoaAmaituDa(){
        mainApp.pantailaHanditu();
        System.out.println("Jokoa amaitu da. Ez du inork irabazi");
        //Animazioa ireki
        triste.setVisible(true);
        irabaziLabel.setText("EZ DU INORK \n IRABAZI");
        berrizB.setVisible(true);
    }





    public void konprobatu4EnRayaDagoen( int zutabea, int ilara, String kolore){
        boolean[][] m = null;

        if(kolore=="gorri"){
            m=gorri;
        }else if(kolore=="urdin"){
            m=urdin;
        }else{
            System.out.println("Erroreren bat egon da");
        }
        System.out.println("zut: "+zutabea+"  ilara:"+ilara);


        //HORIZONTALAK
        int outNon=0;
        if((zutabea-1)>=0){
            if(m[zutabea-1][ilara]){
                if((zutabea-2)>=0){
                    if(m[zutabea-2][ilara]){
                        if((zutabea-3)>=0){
                            if(m[zutabea-3][ilara]){
                                this.irabazi();
                                stop=true;
                            }else{
                                outNon=3;
                            }
                        }else{
                            outNon=3;
                        }
                    }else{
                        outNon=2;
                    }
                }else{
                    outNon=2;
                }
            }else{
                outNon=1;
            }
        }else{
            outNon=1;
        }
        if(outNon==1){
            if((zutabea+3)<=8){ //8 baino gehio bada, matrizearen mugetatik pasako da. Gainera betetzeko 3 balioak egon beharko dira
                if(m[zutabea+1][ilara]){
                    if(m[zutabea+2][ilara]){
                        if(m[zutabea+3][ilara]){
                            this.irabazi();
                            stop=true;
                        }
                    }
                }
            }
        }else if (outNon==2){
            if((zutabea+2)<=8){
                if(m[zutabea+1][ilara]){
                    if(m[zutabea+2][ilara]){
                        this.irabazi();
                        stop=true;
                    }
                }
            }
        }else if(outNon==3){
            if((zutabea+1)<=8){
                if(m[zutabea+1][ilara]){
                    this.irabazi();
                    stop=true;
                }
            }
        }



        //BERTIKALAK
        outNon=0;
        if((ilara-1)>=0){
            if(m[zutabea][ilara-1]){
                if((ilara-2)>=0){
                    if(m[zutabea][ilara-2]){
                        if((ilara-3)>=0){
                            if(m[zutabea][ilara-3]){
                                this.irabazi();
                                stop=true;
                            }else{
                                outNon=3;
                            }
                        }else{
                            outNon=3;
                        }
                    }else{
                        outNon=2;
                    }
                }else{
                    outNon=2;
                }
            }else{
                outNon=1;
            }
        }else{
            outNon=1;
        }
        if(outNon==1){
            if((ilara+3)<=5){ //8 baino gehio bada, matrizearen mugetatik pasako da. Gainera betetzeko 3 balioak egon beharko dira
                if(m[zutabea][ilara+1]){
                    if(m[zutabea][ilara+2]){
                        if(m[zutabea][ilara+3]){
                            this.irabazi();
                            stop=true;
                        }
                    }
                }
            }
        }else if (outNon==2){
            if((ilara+2)<=5){
                if(m[zutabea][ilara+1]){
                    if(m[zutabea][ilara+2]){
                        this.irabazi();
                        stop=true;
                    }
                }
            }
        }else if(outNon==3){
            if((ilara+1)<=5){
                if(m[zutabea][ilara+1]){
                    this.irabazi();
                    stop=true;
                }
            }
        }


        //DIAGONAL NAGUSI
        outNon=0;
        if((zutabea-1)>=0&&(ilara+1)<=5){
            if(m[zutabea-1][ilara+1]){
                if((zutabea-2)>=0&&(ilara+2)<=5){
                    if(m[zutabea-2][ilara+2]){
                        if((zutabea-3)>=0&&(ilara+3)<=5){
                            if(m[zutabea-3][ilara+3]){
                                this.irabazi();
                                stop=true;
                            }else{
                                outNon=3;
                            }
                        }else{
                            outNon=3;
                        }
                    }else{
                        outNon=2;
                    }
                }else{
                    outNon=2;
                }
            }else{
                outNon=1;
            }
        }else{
            outNon=1;
        }
        if(outNon==1){
            if((zutabea+3)<=8&&(ilara-3)>=0){ //6 baino gehio bada, matrizearen mugetatik pasako da. Gainera betetzeko 3 balioak egon beharko dira
                if(m[zutabea+1][ilara-1]){
                    if(m[zutabea+2][ilara-2]){
                        if(m[zutabea+3][ilara-3]){
                            this.irabazi();
                            stop=true;
                        }
                    }
                }
            }
        }else if (outNon==2){
            if((zutabea+2)<=8&&(ilara-2)>=0){
                if(m[zutabea+1][ilara-1]){
                    if(m[zutabea+2][ilara-2]){
                        this.irabazi();
                        stop=true;
                    }
                }
            }
        }else if(outNon==3){
            if((zutabea+1)<=8&&(ilara-1)>=0){
                if(m[zutabea+1][ilara-1]){
                    this.irabazi();
                    stop=true;
                }
            }
        }

        //DIAGONAL BESTEA
        outNon=0;
        if((ilara-1)>=0&&(zutabea-1)>=0){
            if(m[zutabea-1][ilara-1]){
                if((ilara-2)>=0&&(zutabea-2)>=0){
                    if(m[zutabea-2][ilara-2]){
                        if((ilara-3)>=0&&(zutabea-3)>=0){
                            if(m[zutabea-3][ilara-3]){
                                this.irabazi();
                                stop=true;
                            }else{
                                outNon=3;
                            }
                        }else{
                            outNon=3;
                        }
                    }else{
                        outNon=2;
                    }
                }else{
                    outNon=2;
                }
            }else{
                outNon=1;
            }
        }else{
            outNon=1;
        }
        if(outNon==1){
            if((ilara+3)<=5&&(zutabea+3)<=8){ //8 baino gehio bada, matrizearen mugetatik pasako da. Gainera betetzeko 3 balioak egon beharko dira
                if(m[zutabea+1][ilara+1]){
                    if(m[zutabea+2][ilara+2]){
                        if(m[zutabea+3][ilara+3]){
                            this.irabazi();
                            stop=true;
                        }
                    }
                }
            }
        }else if (outNon==2){
            if((ilara+2)<=5&&(zutabea+2)<=8){
                if(m[zutabea+1][ilara+1]){
                    if(m[zutabea+2][ilara+2]){
                        this.irabazi();
                        stop=true;
                    }
                }
            }
        }else if(outNon==3){
            if((ilara+1)<=5&&(zutabea+1)<=8){
                if(m[zutabea+1][ilara+1]){
                    this.irabazi();
                    stop=true;
                }
            }
        }

    }

    private void irabazi() { //SOLO SALEN LAS TRISTES SI GANA EL ORDENADOR, CON 2JOK SALE EL DE GANAR
        mainApp.pantailaHanditu();
        System.out.println("IRABAZI "+jokalariTxanda.getText());
        if(jokalariTxanda.getText().equals("Ordenagailua")){
            rankingB.setVisible(false);
            triste.setVisible(true);
        }else{
            suziri1.setVisible(true);
            suziri2.setVisible(true);
            suziri3.setVisible(true);
            suziri4.setVisible(true);
            suziri5.setVisible(true);
            suziri6.setVisible(true);
            irabaziLabel.setVisible(true);
            if(!nondik.equals("BiJok")){
                irabaziLabel.setText("ZORIONAK!! \n"+jokalariTxanda.getText());

            }else{
                irabaziLabel.setText("ZORIONAK!! \n"+jokalariTxanda.getText());
            }
            berrizB.setVisible(true);

            if(nondik.equals("JOErraza") || nondik.equals("JOZaila")) {
                //Ordenagailuaren kontra irabazi badu, ranking-a gordetzeko aukera izango du.
                rankingB.setVisible(true);
            }
        }
        berrizB.setVisible(true);

    }


    public void clickMenuBiJok(ActionEvent actionEvent) {
        if(!nondik.equals("BiJok")) {
            mainApp.tableroaKargatu("Jokalari 1", "Jokalari 2", "BiJok");
        }else{
            System.out.println("Joko modu horretan zaude jada");
        }
        //podemos poner que si ya esta salte una pantalla de ya estas en ese modo seguro q quieres reiniarlo?. o no reiniciarlo
    }

    public void clickMenuJOErraza(ActionEvent actionEvent) {
        if(!nondik.equals("JOErraza")) {
            mainApp.tableroaKargatu("Jokalari 1","Ordenagailua","JOErraza");
        }else{
            System.out.println("Joko modu horretan zaude jada");
        }
    }

    public void clickMenuJOZaila(ActionEvent actionEvent) {
        if(!nondik.equals("JOZaila")) {
            mainApp.tableroaKargatu("Jokalari 1","Ordenagailua","JOZaila");
        }else{
            System.out.println("Joko modu horretan zaude jada");
        }
    }

    public void clickBerrabiarazi(ActionEvent actionEvent) {
        mainApp.pantailaTxikitu();
        if(nondik.equals("BiJok")) {
            mainApp.tableroaKargatu("Jokalari 1", "Jokalari 2", "BiJok");
        }else if(nondik.equals("JOErraza")) {
            mainApp.tableroaKargatu("Jokalari 1","Ordenagailua","JOErraza");
        }else if(nondik.equals("JOZaila")) {
            mainApp.tableroaKargatu("Jokalari 1","Ordenagailua","JOZaila");
        }else{
            System.out.println("Erroreren bat egon da");
        }
    }





    //Kronometroa
    public void start() {
        Timeline timeline=null;
        final int[] min = {0};
        final int[] seg = {0};
        final int[] milli = {0};

        timeline = new Timeline(
                new KeyFrame(Duration.millis(100),
                        new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        if(!stop){
                            Duration duration = ((KeyFrame) t.getSource()).getTime();
                            milli[0] = milli[0]+(int)duration.toMillis()-99;
                            if(milli[0] >9){
                                milli[0]=0;
                                seg[0]++;
                            }
                            if(seg[0]>59){
                                seg[0]=0;
                                min[0]++;
                            }
                            if(seg[0]<=9){
                                if(min[0]<=9){
                                    timerLabel.setText(""+ 0+min[0] +":"+ 0+seg[0] +":"+0+ milli[0]);
                                }else{
                                    timerLabel.setText(""+ min[0] +":"+ 0+seg[0] +":"+0+ milli[0]);
                                }
                            }else {
                                if(min[0]<=9) {
                                    timerLabel.setText("" + 0+min[0] + ":" + seg[0] + ":" + 0 + milli[0]);
                                }else{
                                    timerLabel.setText("" + min[0] + ":" + seg[0] + ":" + 0 + milli[0]);
                                }
                            }
                        }
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }






    public void berrizClick(ActionEvent actionEvent) {
        this.clickBerrabiarazi(actionEvent);
        suziri1.setVisible(false);
        suziri2.setVisible(false);
        suziri3.setVisible(false);
        suziri4.setVisible(false);
        suziri5.setVisible(false);
        suziri6.setVisible(false);
        triste.setVisible(false);
        irabaziLabel.setVisible(false);
        berrizB.setVisible(false);
        stop=false;

    }

    public void rankingClick(ActionEvent actionEvent) {
        labelIzena.setVisible(true);
        IzenaSartu.setVisible(true);
        OK.setVisible(true);

    }

    public void okClick(ActionEvent actionEvent) {
        //izena sartu du jada
        if(!IzenaSartu.getText().equals("")){  //izen bat sartu badu
            labelIzena.setVisible(false);
            IzenaSartu.setVisible(false);
            OK.setVisible(false);
            taula.setVisible(true);
            labelRanking.setVisible(true);

            ObservableList<Taula>  taulaDatuak= DBKud.getInstantzia().rankingErakutsi(IzenaSartu.getText(),timerLabel.getText(),nondik);

            //taula bete

            tRank.setCellValueFactory(new PropertyValueFactory<>("pos"));
            tIzen.setCellValueFactory(new PropertyValueFactory<>("izena"));
            tDenb.setCellValueFactory(new PropertyValueFactory<>("denbora"));
            taula.setItems(taulaDatuak);


        }else{
            labelIzena.setText("Izena berriz sartu:");
        }
    }







//BOTOIAREN GEINEAN ZAUDELA ADIERAZI. MOUSE NAIZ KEYBOARD

    //gezien koloreak botoian
    //todos igual en el css. Pero en el css no puedes diferenciar los colores y aqui si
    //.button:hover{
    //    -fx-graphic: url("flecha.png" );
    //}

    //Zero botoiaren gainean zaudela adierazi
    public void zeroEntered(){
        if (jokalaria == 1) {
            b0.setGraphic(flechaRoja);
        }else{
            b0.setGraphic(flechaAzul);
        }
        actualButton=b0;
    }
    public void zeroExited() {
        b0.setGraphic(w);
        actualButton=null;

    }
    public void zeroEntered(MouseEvent mouseEvent) {
        zeroEntered();
    }
    public void zeroExited(MouseEvent mouseEvent) {
        zeroExited();
    }
    public void zeroKeyEntered(KeyEvent keyEvent) {
        zeroEntered();
    }
    public void zeroKeyExited(KeyEvent keyEvent) {
        zeroExited();
    }

    //bat
    public void batEntered() {
        if (jokalaria == 1) {
            b02.setGraphic(flechaRoja);
        }else{
            b02.setGraphic(flechaAzul);
        }
        actualButton=b02;
    }
    public void batExited() {
        b02.setGraphic(w);
        actualButton=null;
    }
    public void batEntered(MouseEvent mouseEvent) {
        batEntered();
    }
    public void batExited(MouseEvent mouseEvent) {
        batExited();
    }
    public void batKeyExited(KeyEvent keyEvent) {
        batExited();
    }
    public void batKeyEntered(KeyEvent keyEvent) {
        batEntered();
    }

    //bi
    public void biEntered() {
        if (jokalaria == 1) {
            b03.setGraphic(flechaRoja);
        }else{
            b03.setGraphic(flechaAzul);
        }
        actualButton=b03;
    }
    public void biExited() {
        b03.setGraphic(w);
        actualButton=null;
    }
    public void biEntered(MouseEvent mouseEvent) {
        biEntered();
    }
    public void biExited(MouseEvent mouseEvent) {
        biExited();
    }
    public void biKeyExited(KeyEvent keyEvent) {
        biExited();
    }
    public void biKeyEntered(KeyEvent keyEvent) {
        biEntered();
    }

    //hiru
    public void hiruEntered() {
        if (jokalaria == 1) {
            b04.setGraphic(flechaRoja);
        }else{
            b04.setGraphic(flechaAzul);
        }
        actualButton=b04;
    }
    public void hiruExited() {
        b04.setGraphic(w);
        actualButton=null;
    }
    public void hiruEntered(MouseEvent mouseEvent) {
        hiruEntered();
    }
    public void hiruExited(MouseEvent mouseEvent) {
        hiruExited();
    }
    public void hiruKeyExited(KeyEvent keyEvent) {
        hiruExited();
    }
    public void hiruKeyEntered(KeyEvent keyEvent) {
        hiruEntered();
    }

    //lau
    public void lauEntered() {
        if (jokalaria == 1) {
            b05.setGraphic(flechaRoja);
        }else{
            b05.setGraphic(flechaAzul);
        }
        actualButton=b05;
    }
    public void lauExited() {
        b05.setGraphic(w);
        actualButton=null;
    }
    public void lauEntered(MouseEvent mouseEvent) {
        lauEntered();
    }
    public void lauExited(MouseEvent mouseEvent) {
        lauExited();
    }
    public void lauKeyExited(KeyEvent keyEvent) {
        lauExited();
    }
    public void lauKeyEntered(KeyEvent keyEvent) {
        lauEntered();
    }

    //bost
    public void bostEntered() {
        if (jokalaria == 1) {
            b06.setGraphic(flechaRoja);
        }else{
            b06.setGraphic(flechaAzul);
        }
        actualButton=b06;
    }
    public void bostExited() {
        b06.setGraphic(w);
        actualButton=null;
    }
    public void bostEntered(MouseEvent mouseEvent) {
        bostEntered();
    }
    public void bostExited(MouseEvent mouseEvent) {
        bostExited();
    }
    public void bostKeyExited(KeyEvent keyEvent) {
        bostExited();
    }
    public void bostKeyEntered(KeyEvent keyEvent) {
        bostEntered();
    }

    //sei
    public void seiEntered() {
        if (jokalaria == 1) {
            b07.setGraphic(flechaRoja);
        }else{
            b07.setGraphic(flechaAzul);
        }
        actualButton=b07;
    }
    public void seiExited() {
        b07.setGraphic(w);
        actualButton=null;
    }
    public void seiEntered(MouseEvent mouseEvent) {
        seiEntered();
    }
    public void seiExited(MouseEvent mouseEvent) {
        seiExited();
    }
    public void seiKeyExited(KeyEvent keyEvent) {
        seiExited();
    }
    public void seiKeyEntered(KeyEvent keyEvent) {
        seiEntered();
    }

    //zazpi
    public void zazpiEntered() {
        if (jokalaria == 1) {
            b01.setGraphic(flechaRoja);
        }else{
            b01.setGraphic(flechaAzul);
        }
        actualButton=b01;
    }
    public void zazpiExited() {
        b01.setGraphic(w);
        actualButton=null;
    }
    public void zazpiEntered(MouseEvent mouseEvent) {
        zazpiEntered();
    }
    public void zazpiExited(MouseEvent mouseEvent) {
        zazpiExited();
    }
    public void zazpiKeyExited(KeyEvent keyEvent) {
        zazpiExited();
    }
    public void zazpiKeyEntered(KeyEvent keyEvent) {
        zazpiEntered();
    }

    //zortzi
    public void zortziEntered() {
        if (jokalaria == 1) {
            b08.setGraphic(flechaRoja);
        } else {
            b08.setGraphic(flechaAzul);
        }
        actualButton=b08;
    }
    public void zortziExited() {
        b08.setGraphic(w);
        actualButton=null;
    }
    public void zortziEntered(MouseEvent mouseEvent) {
        zortziEntered();
    }
    public void zortziExited(MouseEvent mouseEvent) {
        zortziExited();
    }
    public void zortziKeyExited(KeyEvent keyEvent) {
        zortziExited();
    }
    public void zortziKeyEntered(KeyEvent keyEvent) {
        zortziEntered();
    }



    public class RemindTask extends TimerTask {
        private Timer timer;
        public void run() {
            timer=new Timer();
            ordenagailuarenTxanda();
            timer.cancel(); //Terminate the timer thread
            blokeatuta=false;
        }
    }

}