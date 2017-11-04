package jens.sudokuprosjekt;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by jenstobiaskaarud on 10/31/17.
 */

public class BrettManager {
    private BrettManager() {}

    public static Brett fortsFraMinne(Activity act, boolean spilles) {
        int[][] tallene = new int[9][9];
        boolean[][] disabled = new boolean[9][9];
        boolean[][] merket = new boolean[9][9];
        Log.i(MainActivity.tagg, "lese(" + spilles + ")");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(act);
        for (int i = 0; i < tallene.length; i++) {
            //Log.i(MainActivity.tagg, spilles + Integer.toString(i) + " - " + pref.getString(spilles + Integer.toString(i), null));
            //Lese tallene
            String[] les = pref.getString(spilles + Integer.toString(i), null).split(",");
            for (int j = 0; j < les.length; j++) {
                try {
                    tallene[i][j] = Integer.parseInt(String.valueOf(les[j]));
                }
                catch (Exception e) {}
            }
            //Lese om de er disabled
            String[] les2 = pref.getString(spilles + MainActivity.disable + i, null).split(",");
            for (int j = 0; j < les2.length; j++) {
                if (les2[j].equals("1")) {
                    disabled[i][j] = true;
                }
                else if (les2[j].equals("0")) {
                    disabled[i][j] = false;
                }
            }
            //Lese om de er merket
            String[] les3 = pref.getString(spilles + MainActivity.merket + i, null).split(",");
            for (int j = 0; j < les3.length; j++) {
                if (les3[j].equals("1")) {
                    merket[i][j] = true;
//                    Log.i(MainActivity.tagg, "Lest merket: " + i + ", " + j);
                }
                else if (les3[j].equals("0")) {
                    merket[i][j] = false;
                }
            }
        }
        return new Brett(act, tallene, disabled, merket, pref.getInt(spilles + MainActivity.diff, 0), pref.getString(spilles + MainActivity.navn, ""));
    }
    public static void lagreTilMinne(Activity act, Brett brett, boolean spilles) {
        Log.i(MainActivity.tagg, "lagre(" + spilles + ")");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(act);
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt(spilles + MainActivity.diff, brett.getDiff());
        edit.putString(spilles + MainActivity.navn, brett.getNavn());

        RuteTableLayout[] adaptere = brett.getRuter();
        for (int i = 0; i < adaptere.length; i++) {
            //Lagre tallene
            String lagre = "";
            for (int j : adaptere[i].getTallene()) {
                lagre += j + ",";
            }
            //Log.i(MainActivity.tagg, spilles + Integer.toString(i) + " - " + lagre);
            edit.putString(spilles + Integer.toString(i), lagre);

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
            edit.putString(spilles + MainActivity.disable + i, lagre2);

            //Lagre om de er merket
            String lagre3 = "";
            for (boolean j : adaptere[i].getMerket()) {
                if (j) {
                    lagre3 += "1,";
                }
                else {
                    lagre3 += "0,";
                }
            }
//            Log.i(MainActivity.tagg, "Merker: " + i + " - " + lagre3);
            edit.putString(spilles + MainActivity.merket + i, lagre3);
        }
        edit.apply();
    }

    public static BrettFragment lagBrettFragment(Activity act) {
        FragmentManager fgm = act.getFragmentManager();
        BrettFragment brettFrag = new BrettFragment();
        FragmentTransaction tran = fgm.beginTransaction();
        tran.replace(R.id.brettView, brettFrag);
        tran.commit();
        return brettFrag;
    }
}