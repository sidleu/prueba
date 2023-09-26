package com.reconosersdk;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.reconosersdk.reconosersdk.ui.questions.view.QuestionActivity;
import com.reconosersdk.reconosersdk.utils.Constants;
import com.reconosersdk.reconosersdk.utils.IntentExtras;
import com.reconosersdk.reconosersdk.utils.JsonUtils;

public class ServiceQuestionsActivity extends AppCompatActivity {

    private TextView result;
    private EditText guid;
    private Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_questions);

        final Drawable yourIcon = getResources().getDrawable(R.drawable.back, null);
        getSupportActionBar().setHomeAsUpIndicator(yourIcon);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.titleview, null);

        ((TextView) v.findViewById(R.id.title)).setText(this.getTitle());
        getSupportActionBar().setCustomView(v);

        guid = findViewById(R.id.editText);
        result = findViewById(R.id.textView);
        enviar = findViewById(R.id.button);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initQuestions();
            }
        });
    }

    private void initQuestions() {

        Intent intent = new Intent(ServiceQuestionsActivity.this, QuestionActivity.class);
        intent.putExtra(IntentExtras.GUID, guid.getText().toString());
        startActivityForResult(intent, Constants.VIEW_QUESTIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.VIEW_QUESTIONS) {
                result.setText(JsonUtils.stringObject(data.getParcelableExtra(IntentExtras.RESULT_VALIDATE)));
            }
        } else if (resultCode == IntentExtras.ERROR_INTENT) {
            result.setText(JsonUtils.stringObject(data.getParcelableExtra(IntentExtras.ERROR_SDK)));

        }
    }
}
