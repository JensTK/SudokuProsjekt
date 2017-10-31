package jens.sudokuprosjekt;

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

public class KnappFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_knapper, container, false);
        Button sjekkKnapp = view.findViewById(R.id.sjekkKnapp);
        final SudokuActivity act = (SudokuActivity)getActivity();
        sjekkKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act.getBrettet().sjekkSvar();
            }
        });
        return view;
    }
}
