package ehu.project.ui;

import ehu.project.Klaseak.Taula;
import ehu.project.Main;
import ehu.project.db.DBKud;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import java.util.*;


public class TableroaKud implements Initializable {

    public RadioMenuItem menuBiJok,menuJOErraza,menuJOZaila;
    public ToggleGroup toggleGroup;
    public ImageView flechaAzul,flechaRoja;
    public ImageView suziri1,suziri2,suziri3,suziri4,suziri5,suziri6,triste;
    public Label irabaziLabel;

    //ranking-a
    public Button berrizB,rankingB;
    public Label labelRanking,labelIzena;
    public TextField IzenaSartu;
    public Button OK;
    //taula
    public TableView taula;
    public TableColumn<Taula,Integer> tRank;
    public TableColumn<Taula,String> tIzen;
    public TableColumn<Taula,Time> tDenb;

    //botoiak
    public Button b0,b01,b02,b03,b04,b05,b06,b07,b08;
    public Button actualButton=null;

    //cronometrorako
    @FXML
    private Label timerLabel = new Label();
    public Label denboraLabel;
    private boolean stop=false;

    //Zirkuluak: k + columna + linea
    @FXML private Circle k00,k01,k02,k03,k04,k05,k10,k11,k12,k13,k14,k15,k20,k21,k22,k23,k24,k25,k30,k31,k32,k33,k34,k35,k40,k41,k42,k43,k44,k45,k50,k51,k52,k53,k54,k55,k60,k61,k62,k63,k64,k65,k70,k71,k72,k73,k74,k75,k80,k81,k82,k83,k84,k85;

    @FXML private Label title,jokalariTxanda;

    private static boolean[][] tableroa,gorri,urdin;
    private int jokalaria;
    private String jok1,jok2,nondik;
    private boolean blokeatuta;
    private ImageView w=new ImageView();

    private Main mainApp;

    private static TableroaKud instantzia = new TableroaKud();

    public static TableroaKud getInstantzia() {
        return instantzia;
    }

    public void setMainApp(Main main) {
        this.mainApp = main;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    public void hasieratu(String j1,String j2,String n){
        //System.err.close();
        //System.setErr(System.out);
        stop=false;
        this.nondik=n;
        jokalaria = 1;
        jok1 = j1;
        if(nondik.equals("BiJok")){ //2 jokalariak ezarri eta txanda ezarri
            toggleGroup.selectToggle(menuBiJok);
            jok2 = j2;
            jokalariTxanda.setText(j1);
            title.setText("2 Jokalari");
            denboraLabel.setVisible(false);
            timerLabel.setVisible(false);
        }else {  //1 vs ordenagailua
            jok2 = "Ordenagailua";  //2jokalaria ordenagailua izango da
            jokalariTxanda.setText(j1);
            denboraLabel.setVisible(true);  //kronometroa jarriko da
            timerLabel.setVisible(true);
            start();
            if(nondik.equals("JOErraza")){ //jokalari soila. Beste jokalaria ordenagailua da
                toggleGroup.selectToggle(menuJOErraza);
                title.setText("1 Jokalari VS Ordenagailu - Erraza");
            }else if(nondik.equals("JOZaila")){
                toggleGroup.selectToggle(menuJOZaila);
                title.setText("1 Jokalari VS Ordenagailu - Zaila");
            }
        }

        //Kasilla guztiak zuriak jarri. Berrabiarazten bada jokoa edo
        k00.setFill(Color.WHITE); k01.setFill(Color.WHITE); k02.setFill(Color.WHITE); k03.setFill(Color.WHITE); k04.setFill(Color.WHITE); k05.setFill(Color.WHITE);k10.setFill(Color.WHITE);k11.setFill(Color.WHITE);k12.setFill(Color.WHITE); k13.setFill(Color.WHITE);k14.setFill(Color.WHITE); k15.setFill(Color.WHITE); k20.setFill(Color.WHITE);  k21.setFill(Color.WHITE);k22.setFill(Color.WHITE); k23.setFill(Color.WHITE); k24.setFill(Color.WHITE);k25.setFill(Color.WHITE);k30.setFill(Color.WHITE); k31.setFill(Color.WHITE); k32.setFill(Color.WHITE);k33.setFill(Color.WHITE);k34.setFill(Color.WHITE); k35.setFill(Color.WHITE);k40.setFill(Color.WHITE); k41.setFill(Color.WHITE);k42.setFill(Color.WHITE);k43.setFill(Color.WHITE); k44.setFill(Color.WHITE);k45.setFill(Color.WHITE);k50.setFill(Color.WHITE); k51.setFill(Color.WHITE);k52.setFill(Color.WHITE);k53.setFill(Color.WHITE); k54.setFill(Color.WHITE);k55.setFill(Color.WHITE);k60.setFill(Color.WHITE); k61.setFill(Color.WHITE);k62.setFill(Color.WHITE);k63.setFill(Color.WHITE);k64.setFill(Color.WHITE);k65.setFill(Color.WHITE);k70.setFill(Color.WHITE);k71.setFill(Color.WHITE);k72.setFill(Color.WHITE);k73.setFill(Color.WHITE);k74.setFill(Color.WHITE);k75.setFill(Color.WHITE);k80.setFill(Color.WHITE); k81.setFill(Color.WHITE);k82.setFill(Color.WHITE);k83.setFill(Color.WHITE);k84.setFill(Color.WHITE);k85.setFill(Color.WHITE);

        //tableroak hasieratu
        tableroa = new boolean[9][6]; gorri = new boolean[9][6]; urdin = new boolean[9][6];

        //ranking-eko gauzak false-era hasieratu
        berrizB.setVisible(false); rankingB.setVisible(false); taula.setVisible(false); labelRanking.setVisible(false); labelIzena.setVisible(false); IzenaSartu.setVisible(false); OK.setVisible(false);

        //Animazioa guztiak false-ra hasieratu
        suziri1.setVisible(false); suziri2.setVisible(false); suziri3.setVisible(false); suziri4.setVisible(false); suziri5.setVisible(false); suziri6.setVisible(false); triste.setVisible(false); irabaziLabel.setVisible(false); berrizB.setVisible(false);

        //hasieran ez dago blokeatuta
        blokeatuta=false;

    }


    //ZUTABE BAKOITZEAN FITXA SARTZEKO BOTOIAN CLICK
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



    //FITXA SARTU DAGOKION LEKUAN
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
            }return null;
        }

        public void fitxaSartu(int zutabea, int ilara) {
            Circle kasilla= kasillaLortu(zutabea,ilara);
            tableroa[zutabea][ilara] = true;
            if (jokalaria == 1) {
                kasilla.setFill(Color.RED);
                gorri[zutabea][ilara] = true;
                konprobatu4EnRayaDagoen(zutabea,ilara,"gorri");
                jokalaria++;//txanda aldatu
                jokalariTxanda.setText(jok2);

                if(jokalariTxanda.getText().equals("Ordenagailua")){ //Orain ordenagailuaren txanda bada, berak egingo du
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


    //ORDENAGAILUAREN TXANDA. ERREZA ETA ZAILA
        public void ordenagailuarenTxanda() {
            if(nondik.equals("JOErraza")){ //Ordenagailu erraza
                ordenagailuaZutabeBatHautatuAleatorioki();

            }else if(nondik.equals("JOZaila")){ //Ordenagailu ZAILA
                MakinaAzkarra maki = new MakinaAzkarra(tableroa,gorri,urdin);
                int zutPos = maki.posErabaki();
                botoiFitxaSartu(zutPos);
            }else{
                System.out.println("Erroreren bat egon da");
            }
        }

        public void ordenagailuaZutabeBatHautatuAleatorioki(){ //Ordenagailu ERRAZA
            Random r = new Random();//0-8 artean zenbaki bat hartuko du. Zutabe bati egingo dio erreferentzia zenbakia. Beraz ateratako zenbakian sartuko du fitxa
            int zenbakia = r.nextInt(9);
            botoiFitxaSartu(zenbakia);
        }



    //TABLERO OSOA BETETA DAGOEN KONPROBATU. HALA BADA, JOKOA AMAITU DA ETA EZ DU INORK IRABAZI
        public void zutabeGuztiakBetetaKonprobatu(){
        if (tableroa[8][0] && tableroa[7][0] && tableroa[6][0] && tableroa[5][0] && tableroa[4][0] && tableroa[3][0] && tableroa[2][0] && tableroa[1][0] && tableroa[0][0]) {
            jokoaAmaituDa(); //Guztiak beteta daude - beraz jokoa amaitu da eta ez du inork irabazi
        }
    }

        public void jokoaAmaituDa(){
        stop=true;  //kronometroa gelditu
        blokeatuta=true;
        mainApp.pantailaHanditu();
        System.out.println("Jokoa amaitu da. Ez du inork irabazi");
        //Animazioa ireki
        triste.setVisible(true);
        irabaziLabel.setText("EZ DU INORK \n IRABAZI");
        berrizB.setVisible(true);
    }


    //KONPROBATU 4EN RAYA DAGOEN
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
                            }else{ outNon=3; }
                        }else{ outNon=3; }
                    }else{ outNon=2; }
                }else{ outNon=2; }
            }else{ outNon=1; }
        }else{ outNon=1; }
        if(outNon==1){
            if((zutabea+3)<=8){ //8 baino gehio bada, matrizearen mugetatik pasako da. Gainera betetzeko 3 balioak egon beharko dira
                if(m[zutabea+1][ilara]){
                    if(m[zutabea+2][ilara]){
                        if(m[zutabea+3][ilara]){
                            this.irabazi();
                        }
                    }
                }
            }
        }else if (outNon==2){
            if((zutabea+2)<=8){
                if(m[zutabea+1][ilara]){
                    if(m[zutabea+2][ilara]){
                        this.irabazi();
                    }
                }
            }
        }else if(outNon==3){
            if((zutabea+1)<=8){
                if(m[zutabea+1][ilara]){
                    this.irabazi();
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
                            }else{ outNon=3; }
                        }else{ outNon=3; }
                    }else{ outNon=2; }
                }else{ outNon=2; }
            }else{ outNon=1; }
        }else{ outNon=1; }

        if(outNon==1){
            if((ilara+3)<=5){ //8 baino gehio bada, matrizearen mugetatik pasako da. Gainera betetzeko 3 balioak egon beharko dira
                if(m[zutabea][ilara+1]){
                    if(m[zutabea][ilara+2]){
                        if(m[zutabea][ilara+3]){
                            this.irabazi();
                        }
                    }
                }
            }
        }else if (outNon==2){
            if((ilara+2)<=5){
                if(m[zutabea][ilara+1]){
                    if(m[zutabea][ilara+2]){
                        this.irabazi();
                    }
                }
            }
        }else if(outNon==3){
            if((ilara+1)<=5){
                if(m[zutabea][ilara+1]){
                    this.irabazi();
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
                            }else{ outNon=3; }
                        }else{ outNon=3; }
                    }else{ outNon=2; }
                }else{ outNon=2; }
            }else{ outNon=1; }
        }else{ outNon=1; }

        if(outNon==1){
            if((zutabea+3)<=8&&(ilara-3)>=0){ //6 baino gehio bada, matrizearen mugetatik pasako da. Gainera betetzeko 3 balioak egon beharko dira
                if(m[zutabea+1][ilara-1]){
                    if(m[zutabea+2][ilara-2]){
                        if(m[zutabea+3][ilara-3]){
                            this.irabazi();
                        }
                    }
                }
            }
        }else if (outNon==2){
            if((zutabea+2)<=8&&(ilara-2)>=0){
                if(m[zutabea+1][ilara-1]){
                    if(m[zutabea+2][ilara-2]){
                        this.irabazi();
                    }
                }
            }
        }else if(outNon==3){
            if((zutabea+1)<=8&&(ilara-1)>=0){
                if(m[zutabea+1][ilara-1]){
                    this.irabazi();
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
                            }else{ outNon=3; }
                        }else{ outNon=3; }
                    }else{ outNon=2; }
                }else{ outNon=2; }
            }else{ outNon=1; }
        }else{ outNon=1; }

        if(outNon==1){
            if((ilara+3)<=5&&(zutabea+3)<=8){ //8 baino gehio bada, matrizearen mugetatik pasako da. Gainera betetzeko 3 balioak egon beharko dira
                if(m[zutabea+1][ilara+1]){
                    if(m[zutabea+2][ilara+2]){
                        if(m[zutabea+3][ilara+3]){
                            this.irabazi();
                        }
                    }
                }
            }
        }else if (outNon==2){
            if((ilara+2)<=5&&(zutabea+2)<=8){
                if(m[zutabea+1][ilara+1]){
                    if(m[zutabea+2][ilara+2]){
                        this.irabazi();
                    }
                }
            }
        }else if(outNon==3){
            if((ilara+1)<=5&&(zutabea+1)<=8){
                if(m[zutabea+1][ilara+1]){
                    this.irabazi();
                }
            }
        }

    }

    //IRABAZLEA DAGO
        private void irabazi() { //SOLO SALEN LAS TRISTES SI GANA EL ORDENADOR, CON 2JOK SALE EL DE GANAR
        mainApp.pantailaHanditu();
        stop=true;  //kronometroa gelditu
        blokeatuta=true;
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


    //MENUAK. Joko era aldatu edo berrabiarazi jokoa
        public void clickMenuBiJok(ActionEvent actionEvent) {
        if(!nondik.equals("BiJok")) {
            mainApp.tableroaKargatu("Jokalari 1", "Jokalari 2", "BiJok");
        }else{
            System.out.println("Joko modu horretan zaude jada");
        }
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



    //KRONOMETROA
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


    //JOKOA AMAITZEAN, BERRIRO JOKATZEKO BOTOIA AZALDUKO DA
        public void berrizClick(ActionEvent actionEvent) {
        this.clickBerrabiarazi(actionEvent);
    }

    //JOKOA AMAITZEAN, ORDENAGAILUAREN KONTRA BADA, RANKING-A BISTARATU
        public void rankingClick(ActionEvent actionEvent) {
        //Ranking-a gorde nahi badu, izena eskatuko dio:
        labelIzena.setVisible(true);
        IzenaSartu.setVisible(true);
        OK.setVisible(true);
    }

        public void okClick(ActionEvent actionEvent) {
        //izena sartu du jada. konprobatu hutsa ez dela
        if(!IzenaSartu.getText().equals("")){  //izen bat sartu badu, izena sartzeko kutxak desgaituko ditugu, eta ranking-a erakusteko kutxak gaitu
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

        }else{//ez badu izenik sartu berriz sartzeko eskatuko du
            labelIzena.setText("Izena berriz sartu:");
        }
    }



    //ZE BOTOIAREN GAINEAN ZAUDEN ADIERAZI. GEZI URDINA EDO GORRIA TURNOAREN ARABERA, saguarekin naiz teklatuarekin mugi daiteke
    //.button:hover{-fx-graphic: url("flecha.png" );}  - Baina ezin dira desberdindu gezi urdinak eta gorriak. beraz oraingoz ez dugu erabiliko
        public Button botoiaLortu(int botoiId){
            if(botoiId==0){ return b0;}
            else if(botoiId==1){ return b01;}
            else if(botoiId==2){ return b02;}
            else if(botoiId==3){ return b03;}
            else if(botoiId==4){ return b04;}
            else if(botoiId==5){ return b05;}
            else if(botoiId==6){ return b06;}
            else if(botoiId==7){ return b07;}
            else if(botoiId==8){ return b08;}
            return null;
        }

        public void Entered(int botoiId){
            Button botoia = botoiaLortu(botoiId);
            if (jokalaria == 1) {
                botoia.setGraphic(flechaRoja);
            }else{
                botoia.setGraphic(flechaAzul);
            }
            actualButton=botoia;
        }

        public void Exited(int botoiId){
            Button botoia = botoiaLortu(botoiId);
            botoia.setGraphic(w);
            actualButton=null;
        }
        //zero botoia
        public void zeroEntered(MouseEvent mouseEvent) { Entered(0); }  //Saguarekin sartu
        public void zeroExited(MouseEvent mouseEvent) { Exited(0); }    //Saguarekin atera
        public void zeroKeyEntered(KeyEvent keyEvent) { Entered(0); }   //Teklatuarekin sartu
        public void zeroKeyExited(KeyEvent keyEvent) { Exited(0); }     //Teklatuarekin atera
        //bat botoia
        public void batEntered(MouseEvent mouseEvent) { Entered(1); }
        public void batExited(MouseEvent mouseEvent) { Exited(1); }
        public void batKeyExited(KeyEvent keyEvent) { Exited(1); }
        public void batKeyEntered(KeyEvent keyEvent) { Entered(1); }
        //bi botoia
        public void biEntered(MouseEvent mouseEvent) { Entered(2); }
        public void biExited(MouseEvent mouseEvent) { Exited(2); }
        public void biKeyExited(KeyEvent keyEvent) { Entered(2); }
        public void biKeyEntered(KeyEvent keyEvent) { Exited(2); }
        //hiru botoia
        public void hiruEntered(MouseEvent mouseEvent) { Entered(3); }
        public void hiruExited(MouseEvent mouseEvent) { Exited(3); }
        public void hiruKeyExited(KeyEvent keyEvent) { Exited(3); }
        public void hiruKeyEntered(KeyEvent keyEvent) { Entered(3); }
        //lau botoia
        public void lauEntered(MouseEvent mouseEvent) { Entered(4); }
        public void lauExited(MouseEvent mouseEvent) { Exited(4); }
        public void lauKeyExited(KeyEvent keyEvent) { Exited(4); }
        public void lauKeyEntered(KeyEvent keyEvent) { Entered(4); }
        //bost botoia
        public void bostEntered(MouseEvent mouseEvent) { Entered(5); }
        public void bostExited(MouseEvent mouseEvent) { Exited(5); }
        public void bostKeyExited(KeyEvent keyEvent) { Exited(5); }
        public void bostKeyEntered(KeyEvent keyEvent) { Entered(5); }
        //sei botoia
        public void seiEntered(MouseEvent mouseEvent) { Entered(6); }
        public void seiExited(MouseEvent mouseEvent) { Exited(6); }
        public void seiKeyExited(KeyEvent keyEvent) { Exited(6); }
        public void seiKeyEntered(KeyEvent keyEvent) { Entered(6); }
        //zazpi botoia
        public void zazpiEntered(MouseEvent mouseEvent) { Entered(7); }
        public void zazpiExited(MouseEvent mouseEvent) { Exited(7); }
        public void zazpiKeyExited(KeyEvent keyEvent) { Exited(7); }
        public void zazpiKeyEntered(KeyEvent keyEvent) { Entered(7); }
        //zortzi botoia
        public void zortziEntered(MouseEvent mouseEvent) { Entered(8); }
        public void zortziExited(MouseEvent mouseEvent) { Exited(8); }
        public void zortziKeyExited(KeyEvent keyEvent) { Exited(8); }
        public void zortziKeyEntered(KeyEvent keyEvent) { Entered(8); }


    //ORDENAGAILUA SEGUNDUAN EZ JARTZEKO FITXA, DELAY BAT SARTUKO DIOGU
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