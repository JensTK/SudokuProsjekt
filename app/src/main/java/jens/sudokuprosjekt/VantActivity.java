package jens.sudokuprosjekt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by Jens on 02.11.2017.
 */

public class VantActivity extends MainActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vant);
        Button hovedMenyKnapp = findViewById(R.id.hovedMenyKnapp);
        hovedMenyKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VantActivity.this, SpillActivity.class));
            }
        });
    }
}
