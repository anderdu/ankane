package ehu.project.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DBKud {

    private static DBKud instantzia = new DBKud();

    public static DBKud getInstantzia() {return instantzia;}

    private DBKud(){
    }



/*
    public ObservableList<Liburua> lortuLiburuak(){
        ObservableList<Liburua> emaitza = FXCollections.observableArrayList();
        DBKudeatzaileSQLITE dbkud =DBKudeatzaileSQLITE.getInstantzia();
        String query ="SELECT * FROM liburuak";
        ResultSet rs =dbkud.execSQL(query);
        try {
            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String egilea = rs.getString("egilea");
                String data = rs.getString("data");
                String izenburua = rs.getString("izenburua");
                String filename = rs.getString("filename");

                Liburua l= new Liburua(isbn,egilea,data,izenburua,filename);
                emaitza.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emaitza;
    }


    public Liburu bilatuLiburuaISBN(String ISBN){

        org.jsoup.nodes.Document docKb = null;
        try {
            docKb = Jsoup
                    .connect("https://openlibrary.org/api/books?bibkeys=ISBN:"+ ISBN + "&jscmd=details&format=json")
                    .ignoreContentType(true).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = docKb.body().text();

        java.lang.reflect.Type type =
                new TypeToken<Map<String, Book>>(){}.getType();

        Map<String, Book> fullJson = new Gson().fromJson(json, type);

        Book book = fullJson.get("ISBN:" + ISBN);

        //URL-a
        String fileOsoa=book.thumbnail_url;
        //orain S-a, M baten gatik aldatu behar dugu
        String fileOsoaM=fileOsoa.replace('S','M');
        //split erabili banatzeko. soilik fitxategi izena beharko dugu
        String[] parts = fileOsoaM.split("/");
        String filename = parts[5];


        Liburu l = new Liburu(ISBN, book.details.authors[0].name,book.details.publish_date,book.details.title,filename,fileOsoaM);

        //argazkia jaitzi beharko dugu ondoren lortu ahal izateko
        try {
            descargarFoto(fileOsoaM, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return l;
    }



    public void gehituDatubasera(Liburu d){
        DBKudeatzaileSQLITE dbkud = DBKudeatzaileSQLITE.getInstantzia();
        String queryS ="select * from liburuak where isbn="+d.getIsbn();
        dbkud.execSQL(queryS);
        ResultSet rs = dbkud.execSQL(queryS);
        try {
            if (rs.next()){
                System.out.println("jada datu basean sartuta dago liburu hori, beraz ez dugu berriz sartuko");
            }else{
                String query ="INSERT INTO liburuak VALUES('"+d.getIsbn()+"','"+d.getEgilea()+"','"+d.getData()+"','"+d.getIzenburua()+"','"+d.getFilename()+"');";
                dbkud.execSQL(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }


    public void descargarFoto(String url, String name) throws IOException {

        //non gorde nahi den.
        String folder = "src/main/resources/irudiak/";
        Path rutaRelativa  = Paths.get(folder);
        Path rutaAbsoluta= rutaRelativa.toAbsolutePath();

        //existitzen ez bada sortu karpeta
        File dir = new File(String.valueOf(rutaAbsoluta));

        //nahi dugun file sortu
        File file = new File(folder + name);

        //conexioa
        URLConnection conn = new URL(url).openConnection();
        conn.connect();

        //stream ireki
        InputStream in = conn.getInputStream();
        OutputStream out = new FileOutputStream(file);

        //fitxategia lortu arte irakurri eta idatzi
        int b = 0;
        while (b != -1) {
            b = in.read();
            if (b != -1)
                out.write(b);
        }

        //close
        out.close();
        in.close();
    }
*/

}
