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
    private Brett brettet;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_brett, container, false);
        int[][] tallene = new int[9][9];
        boolean[][] disabled = new boolean[9][9];

        for (int i = 0; i < 9; i++) {
            tallene[i] = getArguments().getIntArray(MainActivity.tall + i);
            disabled[i] = getArguments().getBooleanArray(MainActivity.disable + i);
        }
        int diff = getArguments().getInt(MainActivity.diff);
        String navn = getArguments().getString(MainActivity.navn);
        brettet = new Brett(getActivity(), tallene, disabled, diff, navn);
        setTall();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        BrettManager.lagreTilMinne(getActivity(), brettet);
    }

    @Override
    public void onResume() {
        super.onResume();
        brettet = BrettManager.fortsFraMinne(getActivity());
        setTall();
    }

    private void setTall() {
        tallAdapter[] adapters = brettet.getAdaptere();

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
            grids[i].setAdapter(adapters[i]);
        }
    }

}
