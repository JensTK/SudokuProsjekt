package jens.sudokuprosjekt;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jens on 30.10.2017.
 */

public class KnappFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_knapper, container, false);

        Button sjekkKnapp = view.findViewById(R.id.sjekkKnapp);
        final SudokuActivity act = (SudokuActivity)getActivity();

        sjekkKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act.getBrettFrag().getBrettet().sjekkSvar(true);
                act.getBrettFrag().setTall();
            }
        });

        Button submitKnapp = view.findViewById(R.id.submitKnapp);
        submitKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Brett brettet = act.getBrettFrag().getBrettet();
                Brett.feilType feil = brettet.sjekkSvar(false);
                switch (feil) {
                    case FEIL_TALL:
                        Toast.makeText(act, getString(R.string.feilTallMsg), Toast.LENGTH_LONG).show();
                        break;
                    case TOMME_RUTER:
                        Toast.makeText(act, getString(R.string.tomRuteMsg), Toast.LENGTH_LONG).show();
                        break;
                    case RIKTIG:
                        startActivity(new Intent(".VantActivity"));
                        break;
                }
            }
        });

        return view;
    }
}
