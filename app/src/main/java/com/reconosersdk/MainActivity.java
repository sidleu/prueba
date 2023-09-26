package com.reconosersdk;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.reconosersdk.databinding.ActivityMainBinding;
import com.reconosersdk.reconosersdk.citizens.barcode.ColombianCitizenBarcode;
import com.reconosersdk.reconosersdk.citizens.barcode.ForeignBarcode;
import com.reconosersdk.reconosersdk.citizens.colombian.ColombianCCD;
import com.reconosersdk.reconosersdk.citizens.colombian.ColombianOCR;
import com.reconosersdk.reconosersdk.entities.Document;
import com.reconosersdk.reconosersdk.http.OlimpiaInterface;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.DataValidateFace;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.GuardarBiometriaIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.GuardarDocumentoIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ValidarBiometriaIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.BarcodeDocument;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ErrorEntransaccion;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.GuardarBiometria;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidarBiometria;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid.RespondValidateFace;
import com.reconosersdk.reconosersdk.http.regula.entities.out.ValidarDocumentoGenericoOut;
import com.reconosersdk.reconosersdk.http.validateine.in.ValidateIneIn;
import com.reconosersdk.reconosersdk.http.validateine.out.ValidateIneOutX;
import com.reconosersdk.reconosersdk.ui.LibraryReconoSer;
import com.reconosersdk.reconosersdk.ui.bioFacial.views.LivePreviewActivity;
import com.reconosersdk.reconosersdk.ui.document.views.BarCodeActivity;
import com.reconosersdk.reconosersdk.ui.document.views.GeneralDocumentActivity;
import com.reconosersdk.reconosersdk.ui.document.views.RequestDocumentActivity;
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.ServicesOlimpia;
import com.reconosersdk.reconosersdk.utils.Constants;
import com.reconosersdk.reconosersdk.utils.ImageUtils;
import com.reconosersdk.reconosersdk.utils.IntentExtras;
import com.reconosersdk.reconosersdk.utils.JsonUtils;
import com.reconosersdk.reconosersdk.utils.Miscellaneous;
import com.reconosersdk.reconosersdk.utils.ObtainImei;
import com.reconosersdk.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    static final int DOCUMENT_REQUEST = 1;
    static final int BARCODE_DOCUMENT = 2;
    static final int GENERAL_DOCUMENT = 5;
    static final int FACE = 3;
    static final int CONFIGURATION = 4;


    private static final String TAKE_ANVERSO = "Anverso (Frontal)";
    private static final String TAKE_REVERSO = "Reverso";
    private static final String TAKE_DOCUMENT = "Documento";
    private static final String TAKE_BARCODE = "Barcode";
    private static final String BIOMETRIA = "Biometría";
    private static final String BIOMETRIA_VALIDATED = "Validación Biometría";
    private static final String BIOMETRIA_BD = "Validación BD";
    private static final int NEW_SERVICE_ID = 5;


    //Biometry
    private static final int FACE_BIOMETRY = 5;
    private static final int DOCUMENT_BIOMETRY = 4;
    private static final String IMAGE_TYPE = "JPG_B64";


    //String for OK Validation
    private static final String ECUADORIAN = "ECUADORIAN";
    private static final String COLOMBIAN = "COLOMBIAN";

    //To Colombian Document
    private ColombianOCR colombianOCR = ColombianOCR.Companion.getInstance();
    private ColombianCitizenBarcode colombianCitizenBarcode = ColombianCitizenBarcode.Companion.getInstance();
    private ColombianCCD colombianCCD = ColombianCCD.Companion.getInstance();


    //Barcode
    private String barcode = "";
    private String typeBarcode = "";

    private String selectedModel;
    private String path = "";
    private String pathFace = "";
    private int service = 0;
    private ActivityMainBinding binding;
    private String subtype = "Anverso";
    public ProgressDialog mProgressDialog;
    private Boolean isValidateBiometry = false;
    private String resultGen = "";

    private String textClip = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String myImei = ObtainImei.getImei(this);
        Toast.makeText(this, "This IMEI is: " + myImei, Toast.LENGTH_LONG ).show();

        final Drawable yourIcon = getResources().getDrawable(R.drawable.back, null);
        getSupportActionBar().setHomeAsUpIndicator(yourIcon);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.titleview, null);

        ((TextView) v.findViewById(R.id.title)).setText(this.getTitle());
        getSupportActionBar().setCustomView(v);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //Set singleton into Colombian Citizen
        colombianCitizenBarcode = ColombianCitizenBarcode.Companion.getInstance();
        colombianOCR = ColombianOCR.Companion.getInstance();
        colombianCCD = ColombianCCD.Companion.getInstance();

        binding.txtResult.setMovementMethod(new ScrollingMovementMethod());
        binding.btnIntent.setOnClickListener(v1 -> {
            Miscellaneous.hideKeyboard(this);
            binding.btnIntent.setEnabled(false);
            nextIntent();
        });

        binding.btnCopy.setOnClickListener(v1 -> {
            Utils.hideKeyboard(this);
            /*binding.btnIntent.setEnabled(false);
            nextIntent();*/
            setCopyClipboard();
        });

        List<String> options = new ArrayList<>();
        options.add("Anverso (Frontal)");
        options.add("Reverso");
        options.add(TAKE_DOCUMENT);
        options.add("Barcode");
        options.add("Biometría");
        options.add("Validación Biometría");
        options.add("Validación BD");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner, options);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.custom_spinner);
        // attaching data adapter to spinner
        binding.spinner2.setAdapter(dataAdapter);
        binding.spinner2.setOnItemSelectedListener(this);

        binding.btnService.setOnClickListener(v13 -> {
            loading("Guardando");
            Miscellaneous.hideKeyboard(this);
            //Utils.hideKeyboard(this);
            saveBiometry();
        });
        binding.btnRekognition.setOnClickListener(v12 -> nextNavigation4());
        if (savedInstanceState != null) {
            resultGen = savedInstanceState.getString("result");
            binding.txtResult.setText(resultGen);
            binding.txtResult.setVisibility(View.VISIBLE);
        }

        binding.swtFlash.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (binding.swtFlash.isChecked()) {
                binding.imgFlash.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.yellow_bulb));
            } else {
                binding.imgFlash.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.gray_hint));
            }
        });

        binding.swtCamera.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (binding.swtCamera.isChecked()) {
                binding.imgCamera.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.yellow_bulb));
            } else {
                binding.imgCamera.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.gray_hint));
            }
        });

    }

    private void setCopyClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if( textClip==null || textClip.isEmpty()){
            Toast.makeText(this, "Texto no copiado\n: " + "no hay algo para copiar", Toast.LENGTH_LONG).show();
        }else{
            ClipData clip = ClipData.newPlainText("Copied Text", textClip);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Texto copiado\n: " + clip.toString(), Toast.LENGTH_LONG).show();
        }//{"stringAnverso":"REPUBLICA DE COLOMBIA\nIDENTIFICACION PERSONAL\nCEDUA DE CIUDADANIA\n1.024.494.020\nMORENO LATORRE\nUIM POOL","stringAnversoBarcode":""}
    }

    private void loading(String msgRes) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);

            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }

        mProgressDialog.setMessage(msgRes);
        mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void saveBiometry() {
        switch (selectedModel) {
            case TAKE_ANVERSO:
                saveBiometria(path, "Anverso", DOCUMENT_BIOMETRY);
                break;
            case BIOMETRIA:
                //saveBiometria(pathFace, "Rostro Vivo", FACE_BIOMETRY);
                saveBiometria(pathFace, "Frontal", FACE_BIOMETRY);
                break;
            case TAKE_REVERSO:
                saveBiometria(path, "Reverso", DOCUMENT_BIOMETRY);
                break;
            case TAKE_BARCODE:
                saveDocument(barcode, typeBarcode);
                break;
            case BIOMETRIA_VALIDATED:
                validateBiometry(pathFace);
                break;
            case BIOMETRIA_BD:
                validateBD(pathFace);
                break;
        }
    }

    private void saveDocument(String barcode, String typeBarcode) {
        GuardarDocumentoIn guardarDocumentoIn = new GuardarDocumentoIn(typeBarcode, barcode, LibraryReconoSer.getGuidConv());

        ServicesOlimpia.getInstance().guardarDocumento(guardarDocumentoIn, new OlimpiaInterface.CallbackSaveDocument() {
            @Override
            public void onSuccess(BarcodeDocument barcodeDocument) {
                binding.txtResult.setText(JsonUtils.stringObject(barcodeDocument));
                dismissProgressDialog();
            }

            @Override
            public void onError(List<ErrorEntransaccion> transactionResponse) {
                binding.txtResult.setText(JsonUtils.stringObject(transactionResponse));
                dismissProgressDialog();
            }
        });
    }

    private void nextIntent() {
        path = "";
        switch (selectedModel) {
            case TAKE_ANVERSO:
                nextNavigation2();
                break;
            case BIOMETRIA_BD:
            case BIOMETRIA:
                nextNavigation4();
                break;
            case TAKE_REVERSO:
                nextNavigation();
                break;
            case TAKE_BARCODE:
                nextNavigation3();
                break;
            case TAKE_DOCUMENT:
                nextNavidation6();
                break;
            case BIOMETRIA_VALIDATED:
                isValidateBiometry = true;
                nextNavigation5();
                break;
        }
    }

    private void nextNavidation6() {
        Intent intent = new Intent(this, GeneralDocumentActivity.class);
        intent.putExtra(IntentExtras.TEXT_SCAN, "Documento");
        intent.putExtra(IntentExtras.TYPE_DOCUMENT, binding.etType.getText().toString());/*
        intent.putExtra(IntentExtras.GUID_CIUDADANO, binding.etGuid.getText().toString());
        intent.putExtra(IntentExtras.NUM_DOCUMENT, binding.etNumDoc.getText().toString());
        intent.putExtra(IntentExtras.SAVE_USER, binding.etUser.getText().toString());
        intent.putExtra(IntentExtras.QUALITY, Integer.parseInt(binding.etQuality.getText().toString()));*/
        startActivityForResult(intent, GENERAL_DOCUMENT);
    }

    private void nextNavigation4() {
        Intent intent = new Intent(this, LivePreviewActivity.class);
        intent.putExtra(IntentExtras.GUID_CIUDADANO, binding.etGuid.getText().toString());
        intent.putExtra(IntentExtras.TYPE_DOCUMENT, binding.etType.getText().toString());
        intent.putExtra(IntentExtras.NUM_DOCUMENT, binding.etNumDoc.getText().toString());
        intent.putExtra(IntentExtras.SAVE_USER, binding.etUser.getText().toString());
        intent.putExtra(IntentExtras.QUALITY, Integer.parseInt(binding.etQuality.getText().toString()));
        intent.putExtra(IntentExtras.ACTIVATE_FLASH, binding.swtFlash.isChecked());
        intent.putExtra(IntentExtras.CHANGE_CAMERA, binding.swtCamera.isChecked());
        intent.putExtra(IntentExtras.ADVISER, "Asesor Android");
        intent.putExtra(IntentExtras.CAMPUS, "Sede Android");
        startActivityForResult(intent, FACE);
    }

    private void nextNavigation5() {
        Intent intent = new Intent(this, ConfigurationActivity.class);
        startActivityForResult(intent, CONFIGURATION);
    }

    private void nextNavigation2() {
        //Restart the values
        colombianOCR.setOcrState(0);
        colombianCCD.setOcrState(0);
        Intent intent = new Intent(this, RequestDocumentActivity.class);
        intent.putExtra(IntentExtras.TEXT_SCAN, "Anverso");
        intent.putExtra(IntentExtras.GUID_CIUDADANO, binding.etGuid.getText().toString());
        intent.putExtra(IntentExtras.TYPE_DOCUMENT, binding.etType.getText().toString());
        intent.putExtra(IntentExtras.NUM_DOCUMENT, binding.etNumDoc.getText().toString());
        intent.putExtra(IntentExtras.SAVE_USER, binding.etUser.getText().toString());
        intent.putExtra(IntentExtras.ADD_DATA, binding.etDatos.getText().toString());
        intent.putExtra(IntentExtras.QUALITY, Integer.parseInt(binding.etQuality.getText().toString()));
        startActivityForResult(intent, DOCUMENT_REQUEST);
    }

    private void nextNavigation() {
        Intent intent = new Intent(this, RequestDocumentActivity.class);
        intent.putExtra(IntentExtras.TEXT_SCAN, "Reverso");
        intent.putExtra(IntentExtras.GUID_CIUDADANO, binding.etGuid.getText().toString());
        intent.putExtra(IntentExtras.TYPE_DOCUMENT, binding.etType.getText().toString());
        intent.putExtra(IntentExtras.NUM_DOCUMENT, binding.etNumDoc.getText().toString());
        intent.putExtra(IntentExtras.SAVE_USER, binding.etUser.getText().toString());
        intent.putExtra(IntentExtras.ADD_DATA, binding.etDatos.getText().toString());
        intent.putExtra(IntentExtras.QUALITY, Integer.parseInt(binding.etQuality.getText().toString()));
        startActivityForResult(intent, DOCUMENT_REQUEST);
    }

    private void nextNavigation3() {
        Intent intent = new Intent(this, BarCodeActivity.class);
        startActivityForResult(intent, BARCODE_DOCUMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        binding.btnIntent.setEnabled(true);
        Timber.e("LLEGA Y EL CODIGO ES: %s", resultCode);
        //Empty clipBoard
        textClip = "";
        if (resultCode != RESULT_CANCELED) {

            binding.txtResult.setVisibility(View.VISIBLE);
            binding.data.setVisibility(View.GONE);

            binding.noData1.setVisibility(View.VISIBLE);
            binding.noData2.setVisibility(View.GONE);

            if (data == null) {
                data = new Intent();
                data.putExtra(IntentExtras.ERROR_MSG, Constants.ERROR_IMAGE_PROCESS);
                data.putExtra(IntentExtras.ERROR_SDK, Constants.ERROR_R106);
                onErrorIntent(data);
            } else if (requestCode == DOCUMENT_REQUEST) {
                if (resultCode == RESULT_OK) {
                    onRespondDoc(data);
                } else if (resultCode == IntentExtras.ERROR_INTENT) {
                    onErrorIntent(data);
                }
            } else if (requestCode == FACE) {
                if (resultCode == RESULT_OK) {
                    onRespondFace(data);
                } else if (resultCode == IntentExtras.ERROR_INTENT) {
                    onErrorIntent(data);
                }
            } else if (requestCode == BARCODE_DOCUMENT) {
                if (resultCode == RESULT_OK) {
                    onRespondBarcode(data);
                } else if (resultCode == IntentExtras.ERROR_INTENT) {
                    onErrorIntent(data);
                }
            } else if (requestCode == CONFIGURATION) {
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent(this, LivePreviewActivity.class);
                    intent.putExtra(IntentExtras.GUID_CIUDADANO, binding.etGuid.getText().toString());
                    intent.putExtra(IntentExtras.VALIDATE_FACE, true);
                    intent.putExtra(IntentExtras.TYPE_DOCUMENT, binding.etType.getText().toString());
                    intent.putExtra(IntentExtras.NUM_DOCUMENT, binding.etNumDoc.getText().toString());
                    intent.putExtra(IntentExtras.SAVE_USER, binding.etUser.getText().toString());
                    intent.putExtra(IntentExtras.QUALITY, Integer.parseInt(binding.etQuality.getText().toString()));
                    intent.putExtra(IntentExtras.NUM_EXPRESION, data.getIntExtra(IntentExtras.NUM_EXPRESION, 1));
                    intent.putExtra(IntentExtras.NUM_ATTEMPTS, data.getIntExtra(IntentExtras.NUM_ATTEMPTS, 1));
                    intent.putExtra(IntentExtras.TIME, data.getIntExtra(IntentExtras.TIME, 5)); //Segundos
                    intent.putExtra(IntentExtras.MOVEMENTS, data.getStringExtra(IntentExtras.MOVEMENTS));
                    intent.putExtra(IntentExtras.ACTIVATE_FLASH, binding.swtFlash.isChecked());
                    intent.putExtra(IntentExtras.CHANGE_CAMERA, binding.swtCamera.isChecked());
                    intent.putExtra(IntentExtras.ADVISER, "Asesor Android");
                    intent.putExtra(IntentExtras.CAMPUS, "Sede Android");
                    startActivityForResult(intent, FACE);
                } else if (resultCode == IntentExtras.ERROR_INTENT) {
                    onErrorIntent(data);
                }
            } else if (requestCode == GENERAL_DOCUMENT) {
                if (resultCode == RESULT_OK) {
                    onRespondGeneralDocument(data);
                } else if (resultCode == IntentExtras.ERROR_INTENT) {
                    onErrorIntent(data);
                }
            }
        }
    }

    private void onRespondGeneralDocument(Intent data) {
        if (data == null) {
            data = new Intent();
            data.putExtra(IntentExtras.ERROR_MSG, Constants.ERROR_IMAGE_PROCESS);
            data.putExtra(IntentExtras.ERROR_SDK, Constants.ERROR_R106);
            onErrorIntent(data);
        } else {
            path = data.getStringExtra(IntentExtras.PATH_FILE_ANVERSO);
            ValidarDocumentoGenericoOut document = data.getParcelableExtra(IntentExtras.DATA_DOCUMENT);
            resultGen = document.toString();
            binding.txtResult.setText(resultGen);
            Glide.with(this).load(path).into(binding.imgResult);
        }
    }

    private void onRespondBarcode(Intent data) {
        if (data == null) {
            data = new Intent();
            data.putExtra(IntentExtras.ERROR_MSG, Constants.ERROR_IMAGE_PROCESS);
            data.putExtra(IntentExtras.ERROR_SDK, Constants.ERROR_R106);
            onErrorIntent(data);
            barcode = "";
            typeBarcode = "";
        } else {
            String barcodeType = data.getStringExtra(IntentExtras.TYPE_BARCODE);
            String barcode = data.getStringExtra(IntentExtras.DATA_BARCODE);
            this.barcode = barcode;
            this.typeBarcode = caseBarcode(barcodeType);
            String result;
            //if (barcodeType.equals(Constants.PDF_417_TI) || barcodeType.equals(Constants.PDF_417_CC)) {
            if (Objects.requireNonNull(colombianCitizenBarcode.getTypeDocument()).equals(Constants.PDF_417_TI) || Objects.requireNonNull(colombianCitizenBarcode.getTypeDocument()).equals(Constants.PDF_417_CC)) {
                ColombianCitizenBarcode colombianCitizenBarcodeAux;
                colombianCitizenBarcodeAux = data.getParcelableExtra(IntentExtras.DATA_DOCUMENT_CO);
                result = "Barcode \n\n" + barcode + "\n\nType barcode \n" + barcodeType + "\n\n " + colombianCitizenBarcode.toString();
                binding.txtResult.setText(result);
                Log.e("ACTIVITYBARC-CCTI", colombianCitizenBarcodeAux.toString());
            } //else if (barcodeType.equals(Constants.PDF_417_CE)) {
                else if (Objects.requireNonNull(colombianCitizenBarcode.getTypeDocument()).equals(Constants.PDF_417_CE)) {
                ForeignBarcode foreignBarcode = new ForeignBarcode();
                foreignBarcode = data.getParcelableExtra(IntentExtras.DATA_DOCUMENT_CO);
                Log.e("ACTIVITYBARC-CE", foreignBarcode.toString());
                result = "Barcode \n\n" + barcode + "\n\nType barcode \n" + barcodeType + "\n\n " + foreignBarcode.toString();
                binding.txtResult.setText(result);
            } else {
                result = "Barcode \n\n" + barcode + "\n\nType barcode \n" + barcodeType + "\n\n ";
                binding.txtResult.setText(result);
            }
        }
    }

    private String caseBarcode(String barcodeType) {
        if (barcodeType == null) {
            return "";
        }
        String type;
        switch (barcodeType) {
            case "PDF_417/TI":
                type = "TI";
                break;
            case "PDF_417/CC":
                type = "CC";
                break;
            case "PDF_417/CE":
                type = "CE";
                break;
            default:
                type = "";
                break;
        }
        return type;
    }

    private void onErrorIntent(Intent data) {
        if (data.getExtras().containsKey(IntentExtras.ERROR_MSG)) {
            binding.txtResult.setText(data.getStringExtra(IntentExtras.ERROR_MSG));
        } else if (data.getExtras().get(IntentExtras.ERROR_SDK) != null) {
            binding.txtResult.setText(JsonUtils.stringObject(data.getExtras().getParcelable(IntentExtras.ERROR_SDK)));
        }

        binding.imgResult.setImageDrawable(getResources().getDrawable(R.drawable.placeholder, null));

        binding.txtResult.setVisibility(View.VISIBLE);
        binding.data.setVisibility(View.GONE);

        binding.noData1.setVisibility(View.VISIBLE);
        binding.noData2.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    public void onRespondFace(Intent data) {
        if (isValidateBiometry) {
            pathFace = (data != null ? data.getStringExtra(IntentExtras.PATH_FILE_PHOTO_R) : "");
            String isvalido = (data != null ? data.getStringExtra(IntentExtras.VALIDATE_FACE) : "");
            Glide.with(this).load(pathFace).into(binding.imgResult);
            BitmapFactory.Options options =  Miscellaneous.getIMGSize(pathFace);
            String showText = "path face is in: " + pathFace + "\n" + "image face height: " + options.outHeight+  "\n" + "image face width: " + options.outWidth;
            binding.txtResult.setText(showText + "\n" + isvalido);
            isValidateBiometry = false;
        } else {
            try {
                analyzeFace(data);
            } catch (Exception e) {
                Timber.tag("tag").e(e);
            }
        }
    }

    private void analyzeFace(Intent data) {
        pathFace = data.getStringExtra(IntentExtras.PATH_FILE_PHOTO_R);
        Bitmap bitmap = BitmapFactory.decodeFile(pathFace);
        Glide.with(this).load(pathFace).into(binding.imgResult);
        if ((data != null && Objects.requireNonNull(data.getExtras()).containsKey(IntentExtras.REKOGNITION))) {
            pathFace = (data != null ? data.getStringExtra(IntentExtras.PATH_FILE_PHOTO_R) : "");
            double rekognition = data.getDoubleExtra(IntentExtras.REKOGNITION, 0);
            String txt = "SIMILITUD : " + rekognition;
            binding.txtResult.setText(txt);
        }
    }

    private void onRespondDoc(Intent data) {
        if (data == null) {
            data = new Intent();
            data.putExtra(IntentExtras.ERROR_MSG, Constants.ERROR_IMAGE_PROCESS);
            data.putExtra(IntentExtras.ERROR_SDK, Constants.ERROR_R106);
            onErrorIntent(data);
        } else {
            path = data.getStringExtra(IntentExtras.PATH_FILE_PHOTO);
            BitmapFactory.Options options = Miscellaneous.getIMGSize(path);
            String b64Face = ImageUtils.getEncodedBase64FromFilePath(path);
            Glide.with(this).load(path).into(binding.imgResult);
            String[] repeatedWords;
            boolean repeatKeyword;
            if (data.getExtras().containsKey(IntentExtras.DATA_DOCUMENT)) {

                binding.txtResult.setVisibility(View.VISIBLE);
                binding.data.setVisibility(View.VISIBLE);

                binding.noData1.setVisibility(View.GONE);
                binding.noData2.setVisibility(View.GONE);

                Document document = data.getExtras().getParcelable(IntentExtras.DATA_DOCUMENT);
                String obverse = JsonUtils.stringObject(document.getDocumentoAnverso());
                String reverse = JsonUtils.stringObject(document.getDocumentoReverso());
                String validations = JsonUtils.stringObject(document.getDocumentValidations());
                String state = JsonUtils.stringObject(document.getStateDocument());
                String path = JsonUtils.stringObject(document.getPath());
                String textscan = JsonUtils.stringObject(document.getTextScan());
                String type = JsonUtils.stringObject(document.getTypeDocument());

                String type1 = document.getTypeDocument();

                Timber.e("Type document JSON is: %s", type);
                Timber.e("Type document get Type is: %s", type1);

                final String sourceValidation = "";

                try {
                    JSONArray documentData = new JSONObject(textscan).getJSONArray("textScan");
                    for (int i = 0; i < documentData.length(); i++) {

                        JSONObject dataDocument = documentData.getJSONObject(i);
                        Log.w("dataDocument", dataDocument.toString());
                        String documentType = dataDocument.getString("type");


                        int typeDocument = Integer.parseInt(type.replace("\"", ""));
                        Log.w("typeDocument", String.valueOf(typeDocument));

                        if ( (documentType.equals("Colombian OCR") || documentType.equals(Constants.COLOMBIAN_DIGITAL_TYPE)) && (typeDocument == Constants.COLOMBIAN_OBVERSE_DOCUMENT || typeDocument == Constants.COLOMBIAN_REVERSE_DOCUMENT || typeDocument == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT) ) {
                            if (setCCObverseData(new JSONObject(dataDocument.getString("value")),  typeDocument)) {
                                textClip = obverse;
                                Timber.e("Documento obverse is:  %s: ", textClip);
                                break;
                            }
                        }
                        if ( (documentType.equals("Colombian BARCODE") && typeDocument == Constants.COLOMBIAN_REVERSE_DOCUMENT) || (documentType.equals(Constants.COLOMBIAN_DIGITAL_TYPE) && typeDocument == Constants.COLOMBIAN_DIGITAL_REVERSE_DOCUMENT) ){
                            textClip = reverse;
                            Timber.e("Documento reverse is:  %s: ", textClip);
                            setCCReverseData(new JSONObject(dataDocument.getString("value")), typeDocument);
                        }

                        if (documentType.equals("TI OCR") && (typeDocument == Constants.COLOMBIAN_TI_OBVERSE_DOCUMENT || typeDocument == Constants.COLOMBIAN_TI_REVERSE_DOCUMENT)) {
                            if (setTIObverseData(new JSONObject(dataDocument.getString("value")))) {
                                break;
                            }
                        }

                        if (documentType.equals("TI Barcode") && typeDocument == Constants.COLOMBIAN_TI_REVERSE_DOCUMENT) {
                            textClip = reverse;
                            Timber.e("Documento reverse is:  %s: ", textClip);
                            setTIReverseData(new JSONObject(dataDocument.getString("value")));
                        }

                        if (documentType.equals("Foreign BARCODE") && typeDocument == Constants.FOREIGNER_REVERSE_DOCUMENT) {
                            setCEObverseData(new JSONObject(dataDocument.getString("value")));
                        }

                        if (documentType.equals("Foreign OCR") && typeDocument == Constants.FOREIGNER_OBVERSE_DOCUMENT) {
                            setCEObverseData(new JSONObject(dataDocument.getString("value")));
                        }

                        if (typeDocument == Constants.MEXICAN_OBVERSE_DOCUMENT) {
                            if (documentType.equals("data")) {
                                JSONObject regulaResponse = new JSONObject(dataDocument.getString("value"));
                                Timber.e("JSON: %s", regulaResponse.toString());
                                Timber.e("Nombre: %s", regulaResponse.getString("documentType"));
                            }
                            if (documentType.equals("procesoConvenioGuid")) {
                                String procesoConvenioGuid = dataDocument.getString("value");
                                Timber.e("GUID: %s", procesoConvenioGuid);
                                ValidateIneIn validateIneIn = new ValidateIneIn(procesoConvenioGuid.replace("\"", ""), 1);
                                ServicesOlimpia.getInstance().getSourcesValidation(validateIneIn, new OlimpiaInterface.CallbackValidateIne() {
                                    @Override
                                    public void onSuccess(ValidateIneOutX validateIneOutX) {

                                    }

                                    @Override
                                    public void onError(RespuestaTransaccion transactionResponse) {

                                    }
                                });
                            }


                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                binding.txtResult.setText(
                        type + "\n" +
                        textscan + "\n" +
                        state + "\n"
                );
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        if (resultGen != null && !resultGen.isEmpty()) {
            outState.putString("result", resultGen);
        }
        super.onSaveInstanceState(outState);
    }

    private void setCEObverseData(JSONObject documentJSON) throws JSONException {

        if (!documentJSON.getString("numero").equals("0")) {
            binding.foreignData.setVisibility(View.VISIBLE);
            binding.country.setText("Colombia");
            binding.type.setText("CE");
            binding.number.setText(documentJSON.getString("numero"));
            if (documentJSON.has("apellidos")) {
                binding.lastname.setText(documentJSON.getString("apellidos"));
                binding.lastnameContainer.setVisibility(View.VISIBLE);
            } else {
                binding.lastnameContainer.setVisibility(View.GONE);
            }
            binding.expeditionDate.setText(documentJSON.getString("fechaExpedicion"));
            binding.dateOfBirth.setText(documentJSON.getString("fechaNacimiento"));
            binding.expirationDate.setText(documentJSON.getString("fechaVencimiento"));
            binding.names.setText(documentJSON.getString("nombres"));
            binding.rh.setText(documentJSON.getString("rh"));
            binding.gender.setText(documentJSON.getString("sexo"));
            binding.nationality.setText(documentJSON.getString("nacionalidad"));
            binding.typeUser.setText(documentJSON.getString("tipo"));
            binding.birthPlaceContainer.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    private Boolean setCCObverseData(JSONObject documentJSON, int typeDocument) throws JSONException {
        binding.foreignData.setVisibility(View.GONE);
        boolean isOCRStateOne = false;
        binding.country.setText("Colombia");
        binding.expirationDateContainer.setVisibility(View.GONE);
        binding.birthPlaceContainer.setVisibility(View.GONE);

        if (documentJSON.getString("ocrState") != null && documentJSON.getString("ocrState").equals("1")) {
            binding.dateOfBirthContainer.setVisibility(View.GONE);
            binding.sexContainer.setVisibility(View.GONE);
            binding.expeditionDateContainer.setVisibility(View.GONE);
            binding.elaborationDateContainer.setVisibility(View.GONE);
            binding.namesContainer.setVisibility(View.VISIBLE);
            binding.lastnameContainer.setVisibility(View.VISIBLE);
            if(typeDocument == Constants.COLOMBIAN_OBVERSE_DOCUMENT){
                binding.type.setText("CC");
                binding.number.setText(Objects.requireNonNull(colombianOCR.getCedula()).toString());
                binding.names.setText(Objects.requireNonNull(colombianOCR.getNames()));
                binding.lastname.setText(Objects.requireNonNull(colombianOCR.getLastNames()));
                binding.rhContainer.setVisibility(View.GONE);
            }else if(typeDocument == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT){
                binding.type.setText("CCD");
                binding.number.setText(Objects.requireNonNull(colombianCCD.getCedulaObverse()).toString());
                binding.names.setText(Objects.requireNonNull(colombianCCD.getNamesObverse()));
                binding.lastname.setText(Objects.requireNonNull(colombianCCD.getLastNamesObverse()));
                binding.rhContainer.setVisibility(View.VISIBLE);
                binding.rh.setText(Objects.requireNonNull(colombianCCD.getRH()));
            }

            isOCRStateOne = true;
        } else {
            //Reverse data
            binding.gender.setText(colombianOCR.getSexo());
            binding.dateOfBirth.setText(colombianOCR.getFechaNacimiento());
            binding.expeditionDate.setText(colombianOCR.getFechaExpedicion());
            binding.elaborationDate.setText(colombianOCR.getFechaElaboracion());
            binding.dateOfBirthContainer.setVisibility(View.VISIBLE);
            binding.sexContainer.setVisibility(View.VISIBLE);
            binding.expeditionDateContainer.setVisibility(View.VISIBLE);
            binding.elaborationDateContainer.setVisibility(View.VISIBLE);
            binding.namesContainer.setVisibility(View.VISIBLE);
            binding.lastnameContainer.setVisibility(View.VISIBLE);
            binding.rhContainer.setVisibility(View.VISIBLE);
            binding.gender.setText(colombianOCR.getSexo());
            binding.dateOfBirth.setText(colombianOCR.getFechaNacimiento());
            binding.expeditionDate.setText(colombianOCR.getFechaExpedicion());
            binding.elaborationDate.setText(colombianOCR.getFechaElaboracion());

        }
        return isOCRStateOne;
    }

    @SuppressLint("SetTextI18n")
    private void setCCReverseData(JSONObject documentJSON, int documentType) throws JSONException {
        binding.country.setText("Colombia");
        binding.foreignData.setVisibility(View.GONE);
        binding.dateOfBirthContainer.setVisibility(View.VISIBLE);
        if (documentType == Constants.COLOMBIAN_REVERSE_DOCUMENT) {
            binding.elaborationDateContainer.setVisibility(View.VISIBLE);
            binding.expeditionDateContainer.setVisibility(View.VISIBLE);
            binding.expirationDateContainer.setVisibility(View.GONE);
            binding.birthPlaceContainer.setVisibility(View.GONE);
            binding.type.setText("CC");

            binding.number.setText(colombianCitizenBarcode.getCedula());
            binding.names.setText(colombianCitizenBarcode.getPrimerNombre() + " " + colombianCitizenBarcode.getSegundoNombre());
            binding.lastname.setText(colombianCitizenBarcode.getPrimerApellido() + " " + colombianCitizenBarcode.getSegundoApellido());
            binding.dateOfBirth.setText(colombianCitizenBarcode.getFechaNacimiento());
            binding.gender.setText(colombianCitizenBarcode.getSexo());
            binding.rh.setText(colombianCitizenBarcode.getRh());

        } else if  (documentType == Constants.COLOMBIAN_DIGITAL_REVERSE_DOCUMENT) {
            binding.elaborationDateContainer.setVisibility(View.GONE);
            binding.expeditionDateContainer.setVisibility(View.GONE);
            binding.expirationDateContainer.setVisibility(View.VISIBLE);
            binding.birthPlaceContainer.setVisibility(View.GONE);
            binding.type.setText("CCD");

            binding.number.setText(colombianCCD.getCedulaReverse());
            binding.names.setText(colombianCCD.getNamesReverse());
            binding.lastname.setText(colombianCCD.getLastNamesReverse());
            binding.dateOfBirth.setText(colombianCCD.getFechaNacimientoReverse());
            binding.gender.setText(colombianCCD.getGenderString());
            binding.expirationDate.setText(colombianCCD.getFechaExpiracionReverse());
            binding.rh.setText("");
            binding.rhContainer.setVisibility(View.GONE);
        } else {
            binding.elaborationDateContainer.setVisibility(View.VISIBLE);
            binding.expirationDateContainer.setVisibility(View.VISIBLE);
            binding.expeditionDateContainer.setVisibility(View.VISIBLE);
            binding.type.setText("TI");

            binding.number.setText(documentJSON.getString("cedula"));
            binding.names.setText(documentJSON.getString("primerNombre") + " " + documentJSON.getString("segundoNombre"));
            binding.lastname.setText(documentJSON.getString("primerApellido") + " " + documentJSON.getString("segundoApellido"));
            binding.dateOfBirth.setText(documentJSON.getString("fechaNacimiento"));
            binding.gender.setText(documentJSON.getString("sexo"));
            binding.rh.setText(documentJSON.getString("rh"));
        }
    }

    private Boolean setTIObverseData(JSONObject documentJSON) throws JSONException {
        binding.foreignData.setVisibility(View.GONE);
        Boolean isOCRStateOne = false;
        binding.country.setText("Colombia");
        binding.type.setText("TI");
        binding.number.setText(documentJSON.getString("numero"));
        binding.names.setText(documentJSON.getString("nombres"));
        binding.lastname.setText(documentJSON.getString("apellidos"));

        if (documentJSON.getString("fechaNacimiento").isEmpty()) {
            binding.dateOfBirthContainer.setVisibility(View.GONE);
            binding.sexContainer.setVisibility(View.GONE);
            binding.expeditionDateContainer.setVisibility(View.GONE);
            binding.elaborationDateContainer.setVisibility(View.GONE);
            binding.rhContainer.setVisibility(View.GONE);
            binding.expirationDateContainer.setVisibility(View.GONE);
            binding.birthPlaceContainer.setVisibility(View.GONE);
            isOCRStateOne = true;
        } else {
            binding.dateOfBirth.setText(documentJSON.getString("fechaNacimiento"));
            binding.gender.setText(documentJSON.getString("sexo"));
            binding.expeditionDate.setText(documentJSON.getString("fechaExpedicion"));
            binding.expirationDate.setText(documentJSON.getString("fechaVencimiento"));
            binding.rh.setText(documentJSON.getString("rh"));
            binding.birthPlace.setText(documentJSON.getString("lugarNacimiento"));

            binding.dateOfBirthContainer.setVisibility(View.VISIBLE);
            binding.sexContainer.setVisibility(View.VISIBLE);
            binding.expeditionDateContainer.setVisibility(View.VISIBLE);
            binding.expirationDateContainer.setVisibility(View.VISIBLE);
            binding.rhContainer.setVisibility(View.VISIBLE);
            binding.birthPlaceContainer.setVisibility(View.VISIBLE);
        }
        return isOCRStateOne;
    }

    private void setTIReverseData(JSONObject documentJSON) throws JSONException {
        setCCReverseData(documentJSON, Constants.COLOMBIAN_TI_REVERSE_DOCUMENT);
    }

    private void saveBiometria(String stringExtra, String subtipo, int biometry) {
        //TODO puede ser exitosa pero puede traer error "Codigo": "103", "Descripcion": "Biometría del ciudadano ya existe, no se puede crear en BD"

        if (stringExtra == null || stringExtra.isEmpty()) {
            dismissProgressDialog();
            Toast.makeText(this, "No hay una imagen", Toast.LENGTH_SHORT).show();
            return;
        }
        GuardarBiometriaIn guardarBiometriaIn = new GuardarBiometriaIn();
        guardarBiometriaIn.setGuidConvenio(LibraryReconoSer.getGuidConv());
        guardarBiometriaIn.setGuidCiu(binding.etGuid.getText().toString());
        guardarBiometriaIn.setGuidProcesoConvenio(binding.etGuidProcs.getText().toString());
        guardarBiometriaIn.setIdServicio(biometry);
        guardarBiometriaIn.setSubTipo(subtipo);
        guardarBiometriaIn.setFormato(IMAGE_TYPE);
        guardarBiometriaIn.setDatosAdi(binding.etDatos.getText().toString());
        guardarBiometriaIn.setActualizar(true);
        /*** Product
         * guardarBiometriaIn.setUsuario("admin");
         * To Develop and Staging
         * guardarBiometriaIn.setUsuario("testing");
         ***/
        guardarBiometriaIn.setUsuario(binding.etUserTest.getText().toString());
        guardarBiometriaIn.setValor(ImageUtils.getEncodedBase64FromFilePath(stringExtra));

        ServicesOlimpia.getInstance().guardarBiometria(guardarBiometriaIn, new OlimpiaInterface.CallbackSaveBiometry() {
            @Override
            public void onSuccess(GuardarBiometria saveBiometry) {
                binding.txtResult.setText(JsonUtils.stringObject(saveBiometry));
                dismissProgressDialog();
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse) {
                binding.txtResult.setText(JsonUtils.stringObject(transactionResponse));
                dismissProgressDialog();
            }
        });
    }

    private void validateBiometry(String pathFace) {
        File newPathImage = Miscellaneous.getRescaledImage(Constants.HEIGHT_FACE, Constants.WIDTH_FACE, Constants.MAX_QUALITY, pathFace);
        ValidarBiometriaIn data = new ValidarBiometriaIn();
        data.setGuidCiudadano(binding.etGuid.getText().toString());
        data.setGuidProcesoConvenio(binding.etGuidProcs.getText().toString());
        //data.setSubTipo("Rostro Vivo");
        data.setSubTipo("Frontal");
        data.setFormato(IMAGE_TYPE);
        data.setIdServicio(NEW_SERVICE_ID);
        if (newPathImage != null) {
            data.setBiometria(ImageUtils.getEncodedBase64FromFilePath(newPathImage.getAbsolutePath()));
        }

        ServicesOlimpia.getInstance().validarBiometria(data, new OlimpiaInterface.CallbackValidateBiometry() {
            @Override
            public void onSuccess(ValidarBiometria validateBiometry) {
                binding.txtResult.setText(JsonUtils.stringObject(validateBiometry));
                dismissProgressDialog();
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse, int intentos) {
                binding.txtResult.setText(JsonUtils.stringObject(transactionResponse));
                dismissProgressDialog();
            }
        });
    }

    private void validateBD(@NonNull String pathFace) {
        String newPathImage = ImageUtils.compressImageHD(pathFace);
        //String newPathImage = Objects.requireNonNull(Miscellaneous.getRescaledImage(Constants.HEIGHT_FACE, Constants.WIDTH_FACE, Constants.MAX_QUALITY, pathFace)).getPath();
        DataValidateFace validateFace = new DataValidateFace();
        validateFace.setCodigoPais("57");
        validateFace.setTipoDocumento(binding.etType.getText().toString());
        validateFace.setNumDocumento(binding.etNumDoc.getText().toString());
        validateFace.setFormato(IMAGE_TYPE);
        validateFace.setGuidConvenio(LibraryReconoSer.getGuidConv());
        if (newPathImage != null && !newPathImage.isEmpty()) {
            validateFace.setImagen(ImageUtils.getEncodedBase64FromFilePath(newPathImage));
        }
        ServicesOlimpia.getInstance().getValidationFaceBD(validateFace, new OlimpiaInterface.CallbackReconoserValidate() {
            @Override
            public void onSuccess(RespondValidateFace respondValidateFace) {
                binding.txtResult.setText(JsonUtils.stringObject(respondValidateFace));
                dismissProgressDialog();
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse) {
                binding.txtResult.setText(JsonUtils.stringObject(transactionResponse));
                dismissProgressDialog();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        onClearRespond();
        selectedModel = parent.getItemAtPosition(position).toString();
        createrServices(selectedModel);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void createrServices(String selectedModel) {

        binding.txtResult.setVisibility(View.GONE);
        binding.data.setVisibility(View.GONE);

        binding.noData1.setVisibility(View.VISIBLE);
        binding.noData2.setVisibility(View.VISIBLE);

        switch (selectedModel) {
            case TAKE_ANVERSO:
                service = 0;
                binding.linearChanges.setVisibility(View.GONE);
                subtype = "Anverso";
                binding.containerType.setVisibility(View.VISIBLE);
                binding.btnIntent.setText("Capturar");
                binding.imgResult.setImageDrawable(getResources().getDrawable(R.drawable.placeholder, null));
                break;
            case TAKE_REVERSO:
                service = 1;
                subtype = "Reverso";
                binding.linearChanges.setVisibility(View.GONE);
                binding.textView5.setText("GuidCiu");
                binding.etGuid.setText("0304c7b2-d8a3-4f1b-aa51-03c1baf452be");
                binding.containerType.setVisibility(View.VISIBLE);
                binding.btnIntent.setText("Capturar");
                binding.imgResult.setImageDrawable(getResources().getDrawable(R.drawable.placeholder, null));
                break;
            case TAKE_BARCODE:
                service = 2;
                subtype = "Reverso";
                binding.linearChanges.setVisibility(View.GONE);
                binding.containerType.setVisibility(View.GONE);
                binding.btnIntent.setText("Capturar");
                binding.imgResult.setImageDrawable(getResources().getDrawable(R.drawable.placeholder, null));
                break;
            case BIOMETRIA:
                service = 0;
                subtype = "Facial";
                binding.linearChanges.setVisibility(View.VISIBLE);
                binding.btnIntent.setText("Capturar");
                binding.containerType.setVisibility(View.VISIBLE);
                binding.imgResult.setImageDrawable(getResources().getDrawable(R.drawable.placeholder, null));
                break;
            case BIOMETRIA_VALIDATED:
                service = 3;
                binding.linearChanges.setVisibility(View.VISIBLE);
                binding.etGuid.setText("14daa2bd-e579-439c-a1a5-526b4abba374");
                binding.containerType.setVisibility(View.VISIBLE);
                binding.btnIntent.setText("Capturar");
                binding.imgResult.setImageDrawable(getResources().getDrawable(R.drawable.placeholder, null));
                break;
            case BIOMETRIA_BD:
                service = 0;
                subtype = "Facial";
                binding.linearChanges.setVisibility(View.VISIBLE);
                binding.btnIntent.setText("Capturar");
                binding.containerType.setVisibility(View.VISIBLE);
                binding.imgResult.setImageDrawable(getResources().getDrawable(R.drawable.placeholder, null));
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void onClearRespond() {
        binding.txtResult.setText("");
    }
}