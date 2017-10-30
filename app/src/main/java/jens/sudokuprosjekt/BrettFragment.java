package jens.sudokuprosjekt;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.Random;

/**
 * Created by Jens on 30.10.2017.
 */

public class BrettFragment extends Fragment {
    private tallAdapter[] adapters = new tallAdapter[9];
    private int[][] tallene = new int[9][9];
    private boolean[][] disabled = new boolean[9][9];
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_brett, container, false);
        boolean ny = getArguments().getBoolean("new");
        Log.i("tagg", "Nytt spill: " + ny);
        if (ny) {
            FilBehandler fil = new FilBehandler(getActivity());
            fil.lesFraFil();
            tallene = fil.getTallene();
            disabled = fil.getDisabled();
            setTall();
        }
        else {
            lese();
        }
        return view;
    }

    public void setTallene(int[][] tallene) {
        this.tallene = tallene;
    }

    public void setDisabled(boolean[][] disabled) {
        this.disabled = disabled;
    }

    public void lagre() {
        Log.i("tagg", "lagre()");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor edit = pref.edit();
        for (int i = 0; i < adapters.length; i++) {
            //Lagre tallene
            String lagre = "";
            for (int j : adapters[i].getTallene()) {
                lagre += j + ",";
            }
            Log.i("tagg", i + " - " + lagre);
            edit.putString(Integer.toString(i), lagre);

            //Lagre om de er disabled
            String lagre2 = "";
            for (boolean j : adapters[i].getDisabled()) {
                if (j) {
                    lagre2 += "1,";
                }
                else {
                    lagre2 += "0,";
                }
            }
            edit.putString("dis" + i, lagre2);
            edit.apply();
        }
    }

    //Den leser fra minne til tallene[], for så å sette de derfra. Kan sikkert sette de direkte
    public void lese() {
        Log.i("tagg", "lese()");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        for (int i = 0; i < tallene.length; i++) {
            Log.i("tagg", i + " - " + pref.getString(Integer.toString(i), null));
            //Lese tallene
            String[] les = pref.getString(Integer.toString(i), null).split(",");
            for (int j = 0; j < les.length; j++) {
                try {
                    tallene[i][j] = Integer.parseInt(String.valueOf(les[j]));
                }
                catch (Exception e) {}
            }
            //Lese om de er disabled
            String[] les2 = pref.getString("dis" + i, null).split(",");
            for (int j = 0; j < les.length; j++) {
                if (les2[j].equals("1")) {
                    disabled[i][j] = true;
                }
                else if (les2[j].equals("0")) {
                    disabled[i][j] = false;
                }
            }
        }
        setTall();
    }
    public void setTall() {
        GridView[] grids = new GridView[9];

        grids[0] = view.findViewById(R.id.r00);
        grids[1] = view.findViewById(R.id.r01);
        grids[2] = view.findViewById(R.id.r02);

        grids[3] = view.findViewById(R.id.r10);
        grids[4] = view.findViewById(R.id.r11);
        grids[5] = view.findViewById(R.id.r12);

        grids[6] = view.findViewById(R.id.r20);
        grids[7] = view.findViewById(R.id.r21);
        grids[8] = view.findViewById(R.id.r22);

        for (int i = 0; i < 9; i++) {
            if (adapters[i] == null) {
                adapters[i] = new tallAdapter(getActivity(), tallene[i], disabled[i], null);
                grids[i].setAdapter(adapters[i]);
            }
            else {
                adapters[i].notifyDataSetChanged();
            }
        }
    }
    private void merkRuter(boolean[][] feil) {
        //Log.i("tagg", "merkRuter(" + hovedRute + ")");
        for (int i = 0; i < adapters.length; i++) {
            adapters[i].setFeil(feil[i]);
            adapters[i].notifyDataSetChanged();
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

        for (int t = 0; t < adapters.length; t++) {

            //tallene fra denne ruta
            int[] tallT = adapters[t].getTallene();

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
                for (int j = 0; j < adapters.length; j++) {
                    //Skal ikke sjekke samme boks
                    if (t != j) {
                        //Tallene fra denne boksen
                        int[] tallJ = adapters[j].getTallene();

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
