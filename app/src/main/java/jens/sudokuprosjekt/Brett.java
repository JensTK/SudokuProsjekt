package jens.sudokuprosjekt;

import android.app.Activity;
import android.util.Log;

import java.util.Arrays;

/**
 * Created by jenstobiaskaarud on 10/31/17.
 */

public class Brett {
    private tallAdapter[] adaptere = new tallAdapter[9];
    private int diff;
    private String navn;
    private Activity act;

    public enum feilType {
        TOMME_RUTER, FEIL_TALL, RIKTIG
    }

    //Lager et tomt brett
    public Brett(Activity act) {
        for (int i = 0; i < adaptere.length; i++) {
            int[] tall = new int[9];
            for (int j = 0; j < tall.length; j++) {
                tall[j] = -1;
            }
            adaptere[i] = new tallAdapter(act, tall, new boolean[9], null);
        }
        this.act = act;
    }

    //Lager et brett
    public Brett(Activity act, int[][] tallene, boolean[][] disabled, int diff, String navn) {
        for (int i = 0; i < adaptere.length; i++) {
            adaptere[i] = new tallAdapter(act, tallene[i], disabled[i], null);
        }
        this.diff = diff;
        this.navn = navn;
        this.act = act;
    }

    //Lager et brett med gule merker
    public Brett(Activity act, int[][] tallene, boolean[][] disabled, boolean[][] merket, int diff, String navn) {
        for (int i = 0; i < adaptere.length; i++) {
            adaptere[i] = new tallAdapter(act, tallene[i], disabled[i], merket[i], null);
        }
        this.diff = diff;
        this.navn = navn;
        this.act = act;
    }

    //Lager et brett, der tall som er fylt inn blir satt som disabled
    public Brett(Activity act, int[][] tallene, int diff, String navn) {
        for (int i = 0; i < adaptere.length; i++) {
            boolean[] dis = new boolean[9];
            for (int j = 0; j < tallene[i].length; j++) {
                if (tallene[i][j] > 0) {
                    dis[j] = true;
                }
            }
            adaptere[i] = new tallAdapter(act, tallene[i], dis, null);
        }
        this.diff = diff;
        this.navn = navn;
        this.act = act;
    }

    public tallAdapter[] getAdaptere() {
        return adaptere;
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

    public boolean erFylt() {
        for (tallAdapter t : adaptere) {
            if (!t.erFylt()) {
                return false;
            }
        }
        return true;
    }

    private void merkRuter(boolean[][] feil) {
        //Log.i(MainActivity.tagg, "merkRuter()");
        for (int i = 0; i < adaptere.length; i++) {
            adaptere[i].setFeil(feil[i]);
            adaptere[i].notifyDataSetChanged();
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
        feilType ret = feilType.RIKTIG;

        boolean[][] feil = new boolean[9][9];

        for (int t = 0; t < adaptere.length; t++) {

            //tallene fra denne ruta
            int[] tallT = adaptere[t].getTallene();

            for (int i = 0; i < tallT.length; i++) {

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
                for (int j = 0; j < adaptere.length; j++) {
                    //Skal ikke sjekke samme boks
                    if (t != j) {
                        //Tallene fra denne boksen
                        int[] tallJ = adaptere[j].getTallene();

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
