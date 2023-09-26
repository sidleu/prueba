package com.reconosersdk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.reconosersdk.databinding.ActivityConfigurationBinding;
import com.reconosersdk.reconosersdk.utils.IntentExtras;

import org.json.JSONArray;

import static com.reconosersdk.R.layout.activity_configuration;

public class ConfigurationActivity extends AppCompatActivity {

    private ActivityConfigurationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, activity_configuration);
        binding.buttonVerify.setOnClickListener(v -> {

            int numMovements = binding.seekBar1.getProgress();
            int numAttempts = binding.seekBar2.getProgress();
            int timeSeconds = binding.seekBar3.getProgress();
            JSONArray data = new JSONArray();
            if (binding.checkBox1.isChecked()) {
                data.put("FACE_BLINK");
            }
            if (binding.checkBox2.isChecked()) {
                data.put("FACE_OPEN_MOUTH");
            }
            if (binding.checkBox3.isChecked()) {
                data.put("FACE_MOVE_LEFT");
            }
            if (binding.checkBox4.isChecked()) {
                data.put("FACE_MOVE_RIGHT");
            }
            if (binding.checkBox5.isChecked()) {
                data.put("FACE_SMILING");
            }

            Intent res = new Intent();
            res.putExtra(IntentExtras.NUM_EXPRESION, numMovements);
            res.putExtra(IntentExtras.NUM_ATTEMPTS, numAttempts);
            res.putExtra(IntentExtras.TIME, timeSeconds);
            res.putExtra(IntentExtras.MOVEMENTS, data.toString());
            setResult(RESULT_OK, res);
            finish();
        });
    }
}
