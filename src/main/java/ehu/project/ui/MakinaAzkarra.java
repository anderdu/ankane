package ehu.project.ui;

import java.util.ArrayList;

public class MakinaAzkarra {

    private boolean[][] tableroa;
    private boolean[][] gorri;
    private boolean[][] urdin;
    public MakinaAzkarra (boolean[][] ptableroa,boolean[][] pgorri,boolean[][] purdin){
        tableroa=ptableroa;
        gorri=pgorri;
        urdin=purdin;
    }

    public int posErabaki(){
        ArrayList<Integer> zutPunt = new ArrayList<>();
        for (int zutabe=0;zutabe<tableroa.length;zutabe++) {//confirmar que estoy pillando zutabez y no ilaras
            int valor = calcularValor(zutabe);
            zutPunt.add(valor);
            //System.out.println("valor conseguido  "+valor);
        }
        System.out.println(zutPunt);
        System.out.println("zutabe elegida: "+getMaxIndex(zutPunt));
        return getMaxIndex(zutPunt);
    }

    public int getMaxIndex(ArrayList<Integer> array){
        int pos = 0;
        int max = -100;
        for (int i=0;i<array.size();i++) {
            int actualNum = array.get(i);
            if(actualNum>max){
                max=actualNum;
                pos=i;
            }
        }
        return pos;
    }

    public int calcularValor(int zutabe){
        int valor = 0;
        int ilara = zutabeanLibreaLortu(tableroa[zutabe]);
        System.out.println("punto mirando zut: "+zutabe+"  il: "+ilara);
        if(ilara!=-1){
            ArrayList<ArrayList<Boolean>> rojas = conseguirEntorno(zutabe,ilara,gorri,0);
            ArrayList<ArrayList<Boolean>> rojasRela1 = conseguirEntorno(zutabe,ilara,gorri,1);
            ArrayList<ArrayList<Boolean>> rojasRela2 = conseguirEntorno(zutabe,ilara,gorri,2);
            ArrayList<ArrayList<Boolean>> azulesRela1 = conseguirEntorno(zutabe,ilara,urdin,1);
            ArrayList<ArrayList<Boolean>> azulesRela2 = conseguirEntorno(zutabe,ilara,urdin,2);
            ArrayList<ArrayList<Boolean>> azulesRela3 = conseguirEntorno(zutabe,ilara,urdin,3);
            ArrayList<ArrayList<Boolean>> tabla = conseguirEntorno(zutabe,ilara,tableroa,0);


            if(regalaVictoria(zutabe,ilara)) valor = valor-5000;
            valor = valor+bloqueos(rojas,tabla);
            valor = valor+cantidadRojasCerca(rojasRela1);
            if(bloquea3(rojas,rojasRela2)) valor = valor+10000;
            if(bloquea2seguidas(rojasRela2)) valor = valor+20;
            if(bloquea2separadas(rojasRela1)) valor = valor+50;

            if(pone2seguidas(azulesRela1)) valor = valor+15;
            if(pone3seguidas(azulesRela2)) valor = valor+25;
            if(pone4seguidas(azulesRela3)) valor = valor+10000000;
        }

        //if(bloqueaCritico()) valor = valor+1000;
        return valor;
    }
    public boolean regalaVictoria(int zutabe,int ilara){
        //mira en caso de que se ponga la ficha si regalaria la partida
        //System.out.println("mirando regalos");
        int fila = ilara-1;
        //int ilarakop = gorri[0].length-1;
        if(fila!=-1) {
            ArrayList<ArrayList<Boolean>> rojas = conseguirEntorno(zutabe,fila,gorri,0);
            ArrayList<ArrayList<Boolean>> rojasRela = conseguirEntorno(zutabe,fila,gorri,2);
            return bloquea3(rojas,rojasRela);
            //System.out.println("resultado "+resultado);
        }
        return false;
    }

    public int bloqueos(ArrayList<ArrayList<Boolean>> rojas, ArrayList<ArrayList<Boolean>> tabla){
        //bloquea una posible futura jugada de 4 en raya
        int bloqueos=0;
        for (int i=0;i<rojas.size()-1;i++){
           if(rojas.get(i).size()>3){
               ArrayList<Boolean> rstdNor = nor(rojas.get(i),tabla.get(i));
               int cont = 0;
               for (Boolean val:rstdNor) {
                   if(val) cont++;
                   else cont=0;
                   if(cont==4){
                       bloqueos++;
                       break;
                   }
               }
           }
        }
        //nor(tableroa,gorri);
        return bloqueos;
    }

    public int cantidadRojasCerca(ArrayList<ArrayList<Boolean>> fichas){
        int cont = 0;
        for (int i = 0; i < fichas.size() - 1; i++) {
            ArrayList<Boolean> mirar = fichas.get(i);
            if (mirar.size() > 0) {
                for (Boolean val : mirar) {
                    if (val) cont++;
                    }
                }
            }
        return cont;
    }
    public boolean bloquea2separadas(ArrayList<ArrayList<Boolean>> rojas){
        ArrayList<Boolean> nueva = new ArrayList<>();
        for (ArrayList<Boolean> dentro:rojas) {
            if(dentro.size()==0) nueva.add(false);
            else nueva.add(dentro.get(0));
        }
        System.out.println("nueva: " +nueva);
        if(nueva.get(2) && nueva.get(6)) return true;
        if(nueva.get(1) && nueva.get(7)) return true;
        if(nueva.get(3) && nueva.get(8)) return true;
        return false;
    }

    public boolean bloquea3(ArrayList<ArrayList<Boolean>> fichas,ArrayList<ArrayList<Boolean>> fichasRel){
        //Bloque una jugada de victoria
        for (int i=0;i<fichas.size()-1;i++){
            ArrayList<Boolean> mirar = fichas.get(i);
            if(mirar.size()>2){
                int cont = 0;
                for (Boolean val:mirar) {
                    if(val) cont++;
                    else cont=0;
                    if(cont==3){
                        if(!bloquea2seguidas(fichasRel)) return false;
                        System.out.println("bloqueando 3");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean bloquea2seguidas(ArrayList<ArrayList<Boolean>> fichas) {
        //mira si esta molestando a dos fichas que esten juntas
        //solo con entorno relativo
        for (int i = 0; i < fichas.size() - 1; i++) {
            ArrayList<Boolean> mirar = fichas.get(i);
            if (mirar.size() > 1) {
                int cont = 0;
                for (Boolean val : mirar) {
                    if (val) cont++;
                    else cont = 0;
                    if (cont == 2) {
                        System.out.println("bloqueando 2 seguidas");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean pone2seguidas(ArrayList<ArrayList<Boolean>> fichas) {
        //mira si esta molestando a dos fichas que esten juntas
        //solo con entorno relativo
        for (int i = 0; i < fichas.size() - 1; i++) {
            ArrayList<Boolean> mirar = fichas.get(i);
            if (mirar.size() > 1) {
                int cont = 0;
                for (Boolean val : mirar) {
                    if (val) cont++;
                    else cont = 0;
                    if (cont == 2) {
                        System.out.println("poniendo 2 seguidas");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean pone3seguidas(ArrayList<ArrayList<Boolean>> fichas) {
        //mira si esta molestando a dos fichas que esten juntas
        //solo con entorno relativo
        for (int i = 0; i < fichas.size() - 1; i++) {
            ArrayList<Boolean> mirar = fichas.get(i);
            if (mirar.size() > 1) {
                int cont = 0;
                for (Boolean val : mirar) {
                    if (val) cont++;
                    else cont = 0;
                    if (cont == 2) {
                        //System.out.println("poniendo 3 seguidas");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean pone4seguidas(ArrayList<ArrayList<Boolean>> fichas) {
        //mira si esta molestando a dos fichas que esten juntas
        //solo con entorno relativo
        for (int i = 0; i < fichas.size() - 1; i++) {
            ArrayList<Boolean> mirar = fichas.get(i);
            if (mirar.size() > 2) {
                int cont = 0;
                for (Boolean val : mirar) {
                    if (val) cont++;
                    else cont = 0;
                    if (cont == 3) {
                        System.out.println("poniendo 4 seguidas");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean mejora(){
        boolean a = true;
        //aumenta las posibilidades de victoria
        return a;
    }
    public boolean gana(){
        boolean a = true;
        //le hace ganar
        return a;
    }

    public ArrayList<Boolean> nor(ArrayList<Boolean> l1, ArrayList<Boolean> l2){
        //devuelve nor de dos listas boolean
        //normalmente l1: bool tabla, l2 bool rojas
        ArrayList<Boolean> emaitza = new ArrayList<Boolean>();
        if(l1.size()!=l2.size()){
            return null;
        }else{
            for(int i=0; i<l1.size();i++){
                if(l1.get(i)==l2.get(i)) emaitza.add(true);
                  else emaitza.add(false);
            }
            return emaitza;
        }
    }
    public boolean nor(boolean a,boolean b){
        if(a==b) return true;
        else return false;
    }
    public ArrayList<Boolean> or(ArrayList<Boolean> l1, ArrayList<Boolean> l2){
        ArrayList<Boolean> emaitza = new ArrayList<Boolean>();
        if(l1.size()!=l2.size()){
            return null;
        }else{
            for(int i=0; i<l1.size();i++){
                emaitza.add(or(l1.get(i),l2.get(i)));
            }
            return emaitza;
        }
    }
    public boolean or(boolean a,boolean b){
        if(a||b) return true;
        else return false;
    }

    public int zutabeanLibreaLortu (boolean[] zutabea){
        for(int i=zutabea.length-1; i>=0;i--){
            if(!zutabea[i]) return i;
        }
        return -1;
    }

    public ArrayList<ArrayList<Boolean>> conseguirEntorno(int zut, int ila, boolean[][] tabla,int forma){
        /* b=zut        a=ila
        Entornos posiciones:    [a,b]
            1. abajo        a+1
            2. abajo izq    a+1 b-1
            3. izq              b-1
            4. arriba izq   a-1 b-1
            5. arriba       a-1
            6. arriba der   a-1 b+1
            7. der              b+1
            8. abajo der    a+1 b+1
         */


        //posibles entornos
        ArrayList<Boolean> ent1 = new ArrayList();
        ArrayList<Boolean> ent2 = new ArrayList();
        ArrayList<Boolean> ent3 = new ArrayList();
        ArrayList<Boolean> ent4 = new ArrayList();
        ArrayList<Boolean> ent5 = new ArrayList();
        ArrayList<Boolean> ent6 = new ArrayList();
        ArrayList<Boolean> ent7 = new ArrayList();
        ArrayList<Boolean> ent8 = new ArrayList();

        int ilarakop = tabla[0].length-1;
        int zutkop = tabla.length-1;

        //1. abajo        a+1
        int newZut = zut;
        int newIla = ila;
        while(true){
            newIla = newIla+1;
            if(newIla>ilarakop) break;
            else{
                ent1.add(tabla[newZut][newIla]);
            }
        }
        //2. abajo izq    a+1 b-1
        newZut = zut;
        newIla = ila;
        while(true){
            newZut = newZut-1;
            newIla = newIla+1;
            if(newIla>ilarakop || newZut<0) break;
            else{
                ent2.add(tabla[newZut][newIla]);
            }
        }
        //3. izq              b-1
        newZut = zut;
        newIla = ila;
        while(true){
            newZut = newZut-1;
            if(newZut<0) break;
            else{
                ent3.add(tabla[newZut][newIla]);
            }
        }
        //4. arriba izq   a-1 b-1
        newZut = zut;
        newIla = ila;
        while(true){
            newZut = newZut-1;
            newIla = newIla-1;
            if(newIla<0||newZut<0) break;
            else{
                ent4.add(tabla[newZut][newIla]);
            }
        }
        //5. arriba       a-1
        newZut = zut;
        newIla = ila;
        while(true){
            newIla = newIla-1;
            if(newIla<0) break;
            else{
                ent5.add(tabla[newZut][newIla]);
            }
        }
        //6. arriba der   a-1 b+1
        newZut = zut;
        newIla = ila;
        while(true){
            newZut = newZut+1;
            newIla = newIla-1;
            if(newIla<0||newZut>zutkop) break;
            else{
                ent6.add(tabla[newZut][newIla]);
            }
        }
        //7. der              b+1
        newZut = zut;
        newIla = ila;
        while(true){
            newZut = newZut+1;
            if(newZut>zutkop) break;
            else{
                ent7.add(tabla[newZut][newIla]);
            }
        }
        //8. abajo der    a+1 b+1
        newZut = zut;
        newIla = ila;
        while(true){
            newZut = newZut+1;
            newIla = newIla+1;
            if(newIla>ilarakop||newZut>zutkop) break;
            else{
                ent8.add(tabla[newZut][newIla]);
            }
        }

        ArrayList<ArrayList<Boolean>> emaitzak = new ArrayList<>();
        if(forma==0){
            //muestra los entornos de la posicion unidos
            //sirve para ver posibles bloqueos o jagadas teneindo en cuenta fichas separadas
            ent1 = invertir(ent1);
            ent2 = invertir(ent2);
            ent3 = invertir(ent3);
            ent4 = invertir(ent4);
            ArrayList<Boolean> vertical = elkartu(ent1,ent5);
            ArrayList<Boolean> parriba = elkartu(ent2,ent6);
            ArrayList<Boolean> horizontal = elkartu(ent3,ent7);
            ArrayList<Boolean> pabajo = elkartu(ent4,ent8);

            emaitzak.add(vertical);
            emaitzak.add(parriba);
            emaitzak.add(horizontal);
            emaitzak.add(pabajo);
            //pruebas
            if(tabla.equals(tableroa)){
                //System.out.println(vertical);
                //System.out.println(parriba);
                //System.out.println(horizontal);
                //System.out.println(pabajo);
            }
        }
        else{
            int dist = forma;
            //en caso contrario damos por hecho de que hay un numero, por lo que el caso es relativo y la vision de la ficha tiene una distancia limitada

            //muestra los entornos de la posicion desde el munto de vista de la posicion analizada
            //es para ver lo que la ficha tiene mas cerca y para fijarse bien si hay filas continuadas justo al lado
            //sirve para ver posibles bloqueos o jagadas teneindo en cuenta las fichas adyacentes
            emaitzak.add(acortar(ent1,dist));
            emaitzak.add(acortar(ent2,dist));
            emaitzak.add(acortar(ent3,dist));
            emaitzak.add(acortar(ent4,dist));
            emaitzak.add(acortar(ent5,dist));
            emaitzak.add(acortar(ent6,dist));
            emaitzak.add(acortar(ent7,dist));
            emaitzak.add(acortar(ent8,dist));
        }





        /*
        if(tabla.equals(tableroa)){
            System.out.println(ent1);
            System.out.println(ent2);
            System.out.println(ent3);
            System.out.println(ent4);
            System.out.println(ent5);
            System.out.println(ent6);
            System.out.println(ent7);
            System.out.println(ent8);
            System.out.println(elkartu(ent3,ent7));
        }

        emaitzak.add(ent1);
        emaitzak.add(ent2);
        emaitzak.add(ent3);
        emaitzak.add(ent4);
        emaitzak.add(ent5);
        emaitzak.add(ent6);
        emaitzak.add(ent7);
        emaitzak.add(ent8);
         */


        return emaitzak;
    }
    public ArrayList<Boolean> elkartu(ArrayList<Boolean> a,ArrayList<Boolean> b){
        for (Boolean val:b) {
            a.add(val);
        }
        return a;
    }
    public ArrayList<Boolean> invertir(ArrayList<Boolean> a){
        ArrayList<Boolean> nueva = new ArrayList<Boolean>();
        for(int i=a.size()-1;i>=0;i--)
            nueva.add(a.get(i));
        return nueva;
    }
    public ArrayList<Boolean> acortar(ArrayList<Boolean> a,int dist){
        ArrayList<Boolean> nueva = new ArrayList<Boolean>();
        int fin=0;
        if(dist>a.size()) fin=a.size();
        else fin=dist;
        for(int i=0;i<fin;i++)
            nueva.add(a.get(i));
        return nueva;
    }

}
