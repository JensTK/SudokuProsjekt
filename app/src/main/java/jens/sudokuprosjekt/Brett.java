package jens.sudokuprosjekt;

import android.app.Activity;
import android.util.Log;

/**
 * Created by jenstobiaskaarud on 10/31/17.
 */

public class Brett {
    private tallAdapter[] adaptere = new tallAdapter[9];

    public Brett(Activity act, int[][] tallene, boolean[][] disabled) {
        for (int i = 0; i < adaptere.length; i++) {
            adaptere[i] = new tallAdapter(act, tallene[i], disabled[i], null);
        }
    }

    public tallAdapter[] getAdaptere() {
        return adaptere;
    }

    private void merkRuter(boolean[][] feil) {
        //Log.i("tagg", "merkRuter(" + hovedRute + ")");
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

    public boolean sjekkSvar() {
        boolean[][] feil = new boolean[9][9];
        boolean ret = true;

        for (int t = 0; t < adaptere.length; t++) {

            //tallene fra denne ruta
            int[] tallT = adaptere[t].getTallene();

            for (int i = 0; i < tallT.length; i++) {

                //Sjekke samme ruta
                for (int j = 0; j < tallT.length; j++) {
                    if (tallT[j] == tallT[i] && j != i) {
                        //Bare merke de som er fylt ut
                        if (tallT[i] > 0) {
                            Log.i("tagg", "B-konflikt: " + tallT[i] + " i boks " + t);
                            feil[t][j] = true;
                            feil[t][i] = true;
                        }
                        //...men returnere false uansett
                        ret = false;
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
                                        Log.i("tagg", "R/S-konflikt: " + tallT[i] + " i boks " + t + " mot " + tallJ[k] + " i boks " + j);
                                        feil[j][k] = true;
                                        feil[t][i] = true;
                                    }
                                    ret = false;
                                }
                            }
                        }
                    }
                }
            }
        }
        merkRuter(feil);
        return ret;
    }
}
