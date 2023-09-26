package com.reconosersdk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    private TextView btnBiometria;
    private TextView btnServicios;
    private TextView btnPreguntas;
    private TextView btnProcesos;
    private TextView btnComparaciones;
    private TextView btnOpenSource;
    private TextView btnValidationOpenSource;
    private TextView btnExtraDocuments;
    private TextView btnRequestValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnBiometria = findViewById(R.id.button5);
        btnServicios = findViewById(R.id.button6);
        btnPreguntas = findViewById(R.id.btnPreguntas);
        btnProcesos = findViewById(R.id.btnConsultas);
        btnComparaciones = findViewById(R.id.btnComparete);
        btnOpenSource = findViewById(R.id.btnOpenSources);
        btnValidationOpenSource = findViewById(R.id.btnValidateDocument);
        btnExtraDocuments = findViewById(R.id.btnExtraDocument);
        btnRequestValidation = findViewById(R.id.btnRequestValidation);
        TextView tvVersion = findViewById(R.id.tv_version);

        tvVersion.setText(BuildConfig.VERSION_NAME);

        btnBiometria.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnServicios.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ServicesActivity.class);
            startActivity(intent);
        });

        btnPreguntas.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ServiceQuestionsActivity.class);
            startActivity(intent);
        });

        btnComparaciones.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, CompareFaceActivity.class);
            startActivity(intent);
        });

        btnOpenSource.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, OpenSourceActivity.class);
            startActivity(intent);
        });

        btnValidationOpenSource.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ValidationOpenSourceDocumentActivity.class);
            startActivity(intent);
        });

        btnExtraDocuments.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ValidationExtraDocumentActivity.class);
            startActivity(intent);
        });

        btnRequestValidation.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ValidationServicesActivity.class);
            startActivity(intent);
        });

        verifyEveryProcess();
        //TODO solo para pruebas
        //hideButtons();
    }

    private void hideButtons() {
        //only Show this
        btnRequestValidation.setVisibility(View.VISIBLE);
        btnBiometria.setVisibility(View.VISIBLE);
        //hide this
        btnServicios.setVisibility(View.GONE);
        btnPreguntas.setVisibility(View.GONE);
        btnProcesos.setVisibility(View.GONE);
        btnComparaciones.setVisibility(View.GONE);
        btnOpenSource.setVisibility(View.GONE);
        btnValidationOpenSource.setVisibility(View.GONE);
        btnExtraDocuments.setVisibility(View.GONE);
    }

    private void verifyEveryProcess() {
        btnProcesos.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, GetProcessActivity.class);
            startActivity(intent);
        });
    }
}
