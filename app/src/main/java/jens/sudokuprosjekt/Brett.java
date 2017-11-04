package jens.sudokuprosjekt;

import android.app.Activity;
import android.util.Log;

/**
 * Created by jenstobiaskaarud on 10/31/17.
 */

public class Brett {
    private Activity act;
    private String navn;
    private int diff;
    private RuteTableLayout[] ruter = new RuteTableLayout[9];

    public enum feilType {
        TOMME_RUTER, FEIL_TALL, RIKTIG
    }

    //Lager et tomt brett
    public Brett(Activity act) {
        for (int i = 0; i < 9; i++) {
            int[] tall = new int[9];
            for (int j = 0; j < tall.length; j++) {
                tall[j] = -1;
            }
            RuteTableLayout rute = new RuteTableLayout(act);
            rute.setBrett(tall, new boolean[9], new boolean[9], new boolean[9]);
            ruter[i] = rute;
        }
        this.act = act;
    }

    //Lager et brett med gule merker
    public Brett(Activity act, int[][] tallene, boolean[][] disabled, boolean[][] merket, int diff, String navn) {
        for (int i = 0; i < 9; i++) {
            RuteTableLayout rute = new RuteTableLayout(act);
            rute.setBrett(tallene[i], disabled[i], merket[i], new boolean[9]);
            ruter[i] = rute;
        }
        this.diff = diff;
        this.navn = navn;
        this.act = act;
    }

    //Lager et brett, der tall som er fylt inn blir satt som disabled
    public Brett(Activity act, int[][] tallene, int diff, String navn) {
        for (int i = 0; i < 9; i++) {
            boolean[] dis = new boolean[9];
            for (int j = 0; j < tallene[i].length; j++) {
                if (tallene[i][j] > 0) {
                    dis[j] = true;
                }
            }
            RuteTableLayout rute = new RuteTableLayout(act);
            rute.setBrett(tallene[i], dis, new boolean[9], new boolean[9]);
            ruter[i] = rute;
        }
        this.diff = diff;
        this.navn = navn;
        this.act = act;
    }
    public int[][] getTallene() {
        int[][] ret = new int[9][9];
        for (int i = 0; i < 9; i++) {
            ret[i] = ruter[i].getTallene();
        }
        return ret;
    }
    public boolean[][] getDisabled() {
        boolean[][] ret = new boolean[9][9];
        for (int i = 0; i < 9; i++) {
            ret[i] = ruter[i].getDisabled();
        }
        return ret;
    }
    public boolean[][] getMerket() {
        boolean[][] ret = new boolean[9][9];
        for (int i = 0; i < 9; i++) {
            ret[i] = ruter[i].getMerket();
        }
        return ret;
    }
    public boolean[][] getFeil() {
        boolean[][] ret = new boolean[9][9];
        for (int i = 0; i < 9; i++) {
            ret[i] = ruter[i].getFeil();
        }
        return ret;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public RuteTableLayout[] getRuter() {
        return ruter;
    }

    public boolean erFylt() {
        for (RuteTableLayout r : ruter) {
            if (!r.erFylt()) {
                return false;
            }
        }
        return true;
    }

    private void merkRuter(boolean[][] feil) {
        //Log.i(MainActivity.tagg, "merkRuter()");
        for (int i = 0; i < ruter.length; i++) {
            ruter[i].setFeil(feil[i]);
            ruter[i].oppdater();
        }
    }

    //Finner hvilken rad i et 3x3-nett du er på
    private int finnRad(int i) {
        return (int)Math.floor(i/3);
    }

    //.. og hvilken søyle
    private int finnSøyle(int i) {
        return i - (finnRad(i) * 3);
    }

    public feilType sjekkSvar(boolean merk) {
        Log.i(MainActivity.tagg, "sjekkSvar(" + merk + ")");

        feilType ret = feilType.RIKTIG;

        boolean[][] feil = new boolean[9][9];

        for (int t = 0; t < ruter.length; t++) {

            //tallene fra denne ruta
            int[] tallT = ruter[t].getTallene();

            for (int i = 0; i < tallT.length; i++) {
                Log.i(MainActivity.tagg, "sjekkSvar(), tallet " + t + "-" + i + " = " + tallT[i]);
                //Sjekke samme ruta
                for (int j = 0; j < tallT.length; j++) {
                    if (tallT[j] == tallT[i] && j != i) {
                        //Bare merke de som er fylt ut
                        if (tallT[i] > 0) {
                            Log.i(MainActivity.tagg, "B-konflikt: " + tallT[i] + " i boks " + t);
                            feil[t][j] = true;
                            feil[t][i] = true;
                        }
                        //...men returnere false uansett
                        ret = feilType.FEIL_TALL;
                    }
                }

                //Sjekke samme rad og søyle
                for (int j = 0; j < ruter.length; j++) {
                    //Skal ikke sjekke samme boks
                    if (t != j) {
                        //Tallene fra denne boksen
                        int[] tallJ = ruter[j].getTallene();

                        for (int k = 0; k < tallJ.length; k++) {
                            if ((finnRad(k) == finnRad(i) && finnRad(t) == finnRad(j)) || (finnSøyle(k) == finnSøyle(i) && finnSøyle(t) == finnSøyle(j))) {
                                if (tallJ[k] == tallT[i]) {
                                    if (tallT[i] > 0) {
                                        Log.i(MainActivity.tagg, "R/S-konflikt: " + tallT[i] + " i boks " + t + " mot " + tallJ[k] + " i boks " + j);
                                        feil[j][k] = true;
                                        feil[t][i] = true;
                                    }
                                    ret = feilType.FEIL_TALL;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!erFylt()) {
            ret = feilType.TOMME_RUTER;
        }
        if (merk) {
            merkRuter(feil);
        }
        return ret;
    }

    @Override
    public String toString() {
        return "Brett{" +
                "diff=" + diff +
                ", navn='" + navn + '\'' +
                '}';
    }
}
