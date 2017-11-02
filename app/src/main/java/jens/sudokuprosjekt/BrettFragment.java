package jens.sudokuprosjekt;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by Jens on 30.10.2017.
 */

public class BrettFragment extends Fragment {
    private Brett brettet;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_brett, container, false);
        if (!getActivity().getIntent().getBooleanExtra(MainActivity.ny, true)) {
            brettet = BrettManager.fortsFraMinne(getActivity());
        }
        else {
            brettet = new FilBehandler(getActivity()).getBrett(getActivity().getIntent().getStringExtra(MainActivity.navn));
        }
        getActivity().getIntent().putExtra(MainActivity.ny, false);
        setTall();
        return view;
    }
    @Override
    public void onPause() {
        super.onPause();
        BrettManager.lagreTilMinne(getActivity(), brettet);
    }

    public void setBrettet(Brett brettet) {
        this.brettet = brettet;
    }

    public Brett getBrettet() {
        return brettet;
    }

    public void setTall() {
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
