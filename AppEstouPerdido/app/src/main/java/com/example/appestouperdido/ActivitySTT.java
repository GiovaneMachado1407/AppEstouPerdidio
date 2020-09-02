package com.example.appestouperdido;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class ActivitySTT extends AppCompatActivity {

    public static MediaPlayer mp;

    TextView txtV1;
    EditText edtSTT;
    Button btnAtivar, btnDesativar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_t_t);

        mp = MediaPlayer.create(this, R.raw.sirenedepolicia);

        txtV1 = findViewById(R.id.txtV1);
        edtSTT = findViewById(R.id.edtSTT);
        btnAtivar = findViewById(R.id.btnAtivar);
        btnDesativar = findViewById(R.id.btnDesativar);

        txtV1.setMovementMethod(new ScrollingMovementMethod());

        edtSTT.setMovementMethod(new ScrollingMovementMethod());

        btnAtivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturar();
            }
        });

        btnDesativar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivitySTT.mp.stop();

            }
        });
    }

    private void capturar(){
        Intent iSTT = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        iSTT.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        iSTT.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        iSTT.putExtra(RecognizerIntent.EXTRA_PROMPT, "Socorro");
        startActivityForResult(iSTT, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> resultado = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String textoReconehcido = resultado.get(0);

                edtSTT.setText(textoReconehcido);

                if (textoReconehcido.toUpperCase().contains("CADÊ VOCÊ")){
                    ActivitySTT.mp.start();
                }
            }
        }
    }

}