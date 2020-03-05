package ehu.project.db;

import ehu.project.Klaseak.Taula;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Map;

public class DBKud {

    private static DBKud instantzia = new DBKud();

    public static DBKud getInstantzia() {return instantzia;}

    private DBKud(){
    }



    public void gehituDatubasera(String izena, Time denbora, String jokoMota){  //hay q pasar la denbora a TIME
        DBKudeatzaileSQLITE dbkud = DBKudeatzaileSQLITE.getInstantzia();
        if(jokoMota.equals("JOErraza")){
            String query ="INSERT INTO JOErrazaRanking VALUES('"+izena+"','"+denbora+"');";
            dbkud.execSQL(query);
        }else if(jokoMota.equals("JOZaila")){
            String query ="INSERT INTO JOZailaRanking VALUES('"+izena+"','"+denbora+"');";
            dbkud.execSQL(query);
        }else{
            System.out.println("Erroreren bat egon da");
        }
    }

    public void ezabatuDatuBasetik(String izena, Time denbora, String jokoMota){  //hay q pasar la denbora a TIME
        DBKudeatzaileSQLITE dbkud = DBKudeatzaileSQLITE.getInstantzia();
        if(jokoMota.equals("JOErraza")){
            String query ="DELETE FROM JOErrazaRanking WHERE JokIzena='"+izena+"' AND Denbora='"+denbora+"';";
            dbkud.execSQL(query);
        }else if(jokoMota.equals("JOZaila")){
            String query ="DELETE FROM JOZailaRanking WHERE JokIzena='"+izena+"' AND Denbora='"+denbora+"';";
            dbkud.execSQL(query);
        }else{
            System.out.println("Erroreren bat egon da");
        }
    }



    public void konprobatuRankinga(String izena, Time denbora, String jokoMota){
        DBKudeatzaileSQLITE dbkud =DBKudeatzaileSQLITE.getInstantzia();
        if(jokoMota.equals("JOErraza")){
            String query ="SELECT * FROM JOErrazaRanking ORDER BY Denbora DESC";  //nos los ordena de manera descendente. para coger la denbora del del topn 10
            ResultSet rs =dbkud.execSQL(query);
            try {
                rs.next();
                String izenaRank10=rs.getString("JokIzena");
                Time denbRank10 = rs.getTime("Denbora");

                if(denbRank10.after(denbora)) {  //si el tiempo del del top10 es mas que el de denbora. tenemos que eliminar ese y meter el nuevo
                    ezabatuDatuBasetik(izenaRank10,denbRank10,jokoMota);  //datu basetik ezabatuko dugu top10
                    gehituDatubasera(izena, denbora,jokoMota); //denbora berria gehituko dugu db-ra
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }else if(jokoMota.equals("JOZaila")){
            String query ="SELECT * FROM JOZailaRanking ORDER BY Denbora DESC";  //nos los ordena de manera descendente. para coger la denbora del del topn 10
            ResultSet rs =dbkud.execSQL(query);
            try {
                rs.next();
                String izenaRank10=rs.getString("JokIzena");
                Time denbRank10 = rs.getTime("Denbora");

                if(denbRank10.after(denbora)) {  //si el tiempo del del top10 es mas que el de denbora. tenemos que eliminar ese y meter el nuevo
                    ezabatuDatuBasetik(izenaRank10,denbRank10,jokoMota);  //datu basetik ezabatuko dugu top10
                    gehituDatubasera(izena, denbora,jokoMota); //denbora berria gehituko dugu db-ra
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Erroreren bat egon da");
        }

    }


    public ObservableList<Taula> rankingErakutsi(String izena, Time denbora, String jokoMota){
        konprobatuRankinga(izena,denbora,jokoMota);

        //bete taula top10 berriarekin
        ObservableList<Taula> emaitza = FXCollections.observableArrayList();

        if(jokoMota.equals("JOErraza")) {
            int pos=0;
            DBKudeatzaileSQLITE dbkud = DBKudeatzaileSQLITE.getInstantzia();
            String query = "SELECT * FROM JOErrazaRanking ORDER BY Denbora ASC";  //nos los ordena de manera descendente. para coger la denbora del del topn 10
            ResultSet rs = dbkud.execSQL(query);
            try {
                while (rs.next()) {
                    pos++;
                    String JokIzenaT = rs.getString("JokIzena");
                    Time DenboraT = rs.getTime("Denbora");

                    Taula t = new Taula(pos,JokIzenaT, DenboraT);
                    emaitza.add(t);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }else if(jokoMota.equals("JOZaila")){
            int pos=0;
            DBKudeatzaileSQLITE dbkud = DBKudeatzaileSQLITE.getInstantzia();
            String query = "SELECT * FROM JOZailaRanking ORDER BY Denbora ASC";  //nos los ordena de manera descendente. para coger la denbora del del topn 10
            ResultSet rs = dbkud.execSQL(query);
            try {
                while (rs.next()) {
                    pos++;
                    String JokIzenaT = rs.getString("JokIzena");
                    Time DenboraT = rs.getTime("Denbora");

                    Taula t = new Taula(pos,JokIzenaT, DenboraT);
                    emaitza.add(t);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else{
            System.out.println("Erroreren bat egon da");
        }

        return emaitza;
    }

}