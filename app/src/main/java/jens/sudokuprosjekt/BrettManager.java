package jens.sudokuprosjekt;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by jenstobiaskaarud on 10/31/17.
 */

public class BrettManager {
    private BrettManager() {}

    public static Brett lesFraFil(Activity act, String navn) {
        //Må fikse sånn at man kan lese/lagre
        return fortsFraMinne(act);
    }

    public static Brett fortsFraMinne(Activity act) {
        int[][] tallene = new int[9][9];
        boolean[][] disabled = new boolean[9][9];
        Log.i("tagg", "lese()");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(act);
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
        Brett ret = new Brett(act, tallene, disabled);
        return ret;
    }
    public static void lagreTilMinne(Activity act, Brett brett) {
        Log.i("tagg", "lagre()");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(act);
        SharedPreferences.Editor edit = pref.edit();
        tallAdapter[] adaptere = brett.getAdaptere();
        for (int i = 0; i < adaptere.length; i++) {
            //Lagre tallene
            String lagre = "";
            for (int j : adaptere[i].getTallene()) {
                lagre += j + ",";
            }
            Log.i("tagg", i + " - " + lagre);
            edit.putString(Integer.toString(i), lagre);

            //Lagre om de er disabled
            String lagre2 = "";
            for (boolean j : adaptere[i].getDisabled()) {
                if (j) {
                    lagre2 += "1,";
                }
                else {
                    lagre2 += "0,";
                }
            }
            edit.putString("dis" + i, lagre2);
        }
        edit.apply();
    }

    public static void lagBrettFragment(Activity act, Brett brett) {
        FragmentManager fgm = act.getFragmentManager();
        BrettFragment brettFrag = new BrettFragment();
        Bundle fragBun = new Bundle();
        for (int i = 0; i < brett.getAdaptere().length; i++) {
            fragBun.putIntArray("tall" + i, brett.getAdaptere()[i].getTallene());
            fragBun.putBooleanArray("dis" + i, brett.getAdaptere()[i].getDisabled());
        }
        brettFrag.setArguments(fragBun);
        FragmentTransaction tran = fgm.beginTransaction();
        tran.replace(R.id.brettView, brettFrag);
        tran.commit();
    }
}