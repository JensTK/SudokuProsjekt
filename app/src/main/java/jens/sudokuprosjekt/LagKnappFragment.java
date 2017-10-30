package jens.sudokuprosjekt;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Jens on 30.10.2017.
 */

public class LagKnappFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lagknapper, container, false);
        final LagActivity act = (LagActivity) getActivity();

        Button sjekkKnapp = view.findViewById(R.id.sjekkKnapp);
        sjekkKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act.sjekkSvar();
            }
        });

        Button lagreKnapp = view.findViewById(R.id.lagreKnapp);
        lagreKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment frag = new VanskeligFrag();
                Bundle bndl = new Bundle();
                bndl.putBoolean(VanskeligFrag.lagNavn, true);
                frag.setArguments(bndl);
                frag.show(getFragmentManager(), "");
            }
        });

        return view;
    }
}
