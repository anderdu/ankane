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
        int zutabe = 0;
        ArrayList<Integer> zutPunt = new ArrayList<>();
        System.out.println(tableroa.length);
        for (int pos=0;pos<tableroa.length;pos++) {//confirmar que estoy pillando zutabez y no ilaras
            int valor = calcularValor(pos);
        }
        return zutabe;
    }

    public int getMax(ArrayList<Integer> array){
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

    public int calcularValor(int pos){
        int valor = 0;
        if(bloquea()) valor = valor+10;
        if(bloqueaCritico()) valor = valor+1000;
        return valor;
    }
    public boolean bloquea(){
        boolean bloquea = false;

        return bloquea;
    }

    public boolean bloqueaCritico(){
        boolean bloquea = false;

        return bloquea;
    }

}
