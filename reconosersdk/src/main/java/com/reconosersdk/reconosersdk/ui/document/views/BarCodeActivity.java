package com.reconosersdk.reconosersdk.ui.document.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cetoco.zxing_android_embedded_custom.google.zxing.client.android.Intents;
import com.cetoco.zxing_android_embedded_custom.google.zxing.integration.android.IntentIntegrator;
import com.cetoco.zxing_android_embedded_custom.google.zxing.integration.android.IntentResult;
import com.reconosersdk.reconosersdk.R;
import com.reconosersdk.reconosersdk.citizens.barcode.ColombianCitizenBarcode;
import com.reconosersdk.reconosersdk.citizens.barcode.ForeignBarcode;
import com.reconosersdk.reconosersdk.http.OlimpiaInterface;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.GuardarDocumentoIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.BarcodeDocument;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ErrorEntransaccion;
import com.reconosersdk.reconosersdk.ui.LibraryReconoSer;
import com.reconosersdk.reconosersdk.ui.base.BaseActivity;
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.ServicesOlimpia;
import com.reconosersdk.reconosersdk.utils.Constants;
import com.reconosersdk.reconosersdk.utils.ForeignStringUtils;
import com.reconosersdk.reconosersdk.utils.IntentExtras;
import com.reconosersdk.reconosersdk.utils.StringUtils;

import java.util.List;

import timber.log.Timber;

public class BarCodeActivity extends BaseActivity {
    private String BARCODE = "Barcode";
    private boolean isActive = false;

    private static final String CC = "/CC";
    private static final String TI = "/TI";
    private static final String CE = "/CE";

    //To Colombian Document
    //Singleton (newInstance)
    private ColombianCitizenBarcode colombianCitizenBarcode = ColombianCitizenBarcode.Companion.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code);
        if (!ServicesOlimpia.getInstance().isValidBarCode()) {
            onFinishError(Constants.ERROR_R101);
            return;
        }
        if (savedInstanceState != null) {
            isActive = savedInstanceState.getBoolean(BARCODE);
        }
        cleanBarcode();
        initUI();
    }

    private void cleanBarcode() {
        Timber.e("Before clean barcode data %s: ", colombianCitizenBarcode.toString());
        //Clean first data
        colombianCitizenBarcode.setPrimerApellido("");
        colombianCitizenBarcode.setSegundoApellido("");
        colombianCitizenBarcode.setPrimerNombre("");
        colombianCitizenBarcode.setSegundoNombre("");
        colombianCitizenBarcode.setCedula("");
        colombianCitizenBarcode.setRh("");
        colombianCitizenBarcode.setFechaNacimiento("");
        colombianCitizenBarcode.setSexo("");
        Timber.e("After clean barcode data %s: ", colombianCitizenBarcode.toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(BARCODE, isActive);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isActive = savedInstanceState.getBoolean(BARCODE);
    }

    private void initUI() {
        //Set singleton into ColombianCitizenBarcode
        colombianCitizenBarcode = ColombianCitizenBarcode.Companion.getInstance();
        if (!isActive) {
            isActive = true;
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.PDF_417, IntentIntegrator.CODE_128);
            integrator.setCameraId(0);  // Use a specific camera of the device
            integrator.setBeepEnabled(true);
            integrator.setBarcodeImageEnabled(true);
            integrator.setOrientationLocked(true);
            integrator.setTimeout(21000);
            integrator.initiateScan();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (data != null && data.hasExtra(Intents.Scan.TIMEOUT)) {
            Intent errorRes = new Intent();
            errorRes.putExtra(IntentExtras.ERROR_MSG, "Ha superado el tiempo límite para escanear el barcode");
            setResult(IntentExtras.ERROR_INTENT, errorRes);
            finish();
            return;
        } else if (result != null) {
            if (result.getContents() == null) {
                setResult(RESULT_CANCELED);
                finish();
            } else {
                sendBarcode(result.getFormatName(), result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            finish();
        }
        isActive = false;
    }

    private void sendBarcode(String formatName, String contents) {
        GuardarDocumentoIn guardarDocumentoIn;
        if(contents==null || contents.isEmpty()){
            guardarDocumentoIn = new GuardarDocumentoIn(null, null, LibraryReconoSer.getGuidConv());
        }else if (contents.startsWith("I")){
            guardarDocumentoIn = new GuardarDocumentoIn("TI", contents, LibraryReconoSer.getGuidConv());
        }else if (Character.isDigit(contents.charAt(0))){
            guardarDocumentoIn = new GuardarDocumentoIn("CC", contents, LibraryReconoSer.getGuidConv());
        }else if (contents.startsWith("A")){
            guardarDocumentoIn = new GuardarDocumentoIn("CE", contents, LibraryReconoSer.getGuidConv());
        }else {
            guardarDocumentoIn = new GuardarDocumentoIn("", "", LibraryReconoSer.getGuidConv());
        }

        ServicesOlimpia.getInstance().guardarDocumento(guardarDocumentoIn, new OlimpiaInterface.CallbackSaveDocument() {
            @Override
            public void onSuccess(BarcodeDocument barcodeDocument) {
                navigationNext(formatName, contents, barcodeDocument);
            }

            @Override
            public void onError(List<ErrorEntransaccion> transactionResponse) {
                Intent errorRes = new Intent();
                errorRes.putExtra(IntentExtras.ERROR_MSG, "El barcode no puede ser leído, intentelo más tarde");
                setResult(IntentExtras.ERROR_INTENT, errorRes);
                finish();
            }
        });
    }

    private ColombianCitizenBarcode onCitizenColombia(String formatName, String contents, BarcodeDocument barcodeDocument) {
        ColombianCitizenBarcode colombianCitizenBarcodeAux = new ColombianCitizenBarcode();
        if (formatName.equals("PDF_417")) {
            colombianCitizenBarcodeAux = StringUtils.parseDataCode(contents, getApplicationContext());
            //Set values and save into singleton
            setColombianBarcode(colombianCitizenBarcodeAux);
            if (colombianCitizenBarcode.getPrimerNombre() == null && barcodeDocument.getPrimerNombre() != null){
                colombianCitizenBarcode.setPrimerNombre(barcodeDocument.getPrimerNombre());
                if (barcodeDocument.getSegundoNombre() != null){
                    colombianCitizenBarcode.setSegundoNombre(barcodeDocument.getSegundoNombre());
                }
                if (barcodeDocument.getPrimerApellido() != null){
                    colombianCitizenBarcode.setPrimerApellido(barcodeDocument.getPrimerApellido());
                }
                if (barcodeDocument.getSegundoApellido() != null){
                    colombianCitizenBarcode.setSegundoApellido(barcodeDocument.getSegundoApellido());
                }
            }
        }
        return colombianCitizenBarcode;
    }

    private void setColombianBarcode(ColombianCitizenBarcode colombianBarcode) {
        if (colombianBarcode == null) {
            //Avoid null values
            cleanBarcode();
        } else {
            //To save into Singleton Pattern
            colombianCitizenBarcode.setPrimerApellido(avoidNull(colombianBarcode.getPrimerApellido()));
            colombianCitizenBarcode.setSegundoApellido(avoidNull(colombianBarcode.getSegundoApellido()));
            colombianCitizenBarcode.setPrimerNombre(avoidNull(colombianBarcode.getPrimerNombre()));
            colombianCitizenBarcode.setSegundoNombre(avoidNull(colombianBarcode.getSegundoNombre()));
            colombianCitizenBarcode.setCedula(avoidNull(colombianBarcode.getCedula()));
            colombianCitizenBarcode.setRh(avoidNull(colombianBarcode.getRh()));
            colombianCitizenBarcode.setFechaNacimiento(avoidNull(colombianBarcode.getFechaNacimiento()));
            colombianCitizenBarcode.setSexo(avoidNull(colombianBarcode.getSexo()));
        }
    }

    public static String avoidNull(String nullString) {
        if (nullString == null) {
            nullString = "";
        }
        return nullString;
    }

    private void navigationNext(String formatName, String contents, BarcodeDocument barcodeDocument) {
        Intent res = new Intent();
        Log.e("BARCODE ACT",contents);
        Log.e("FORMAT",formatName);
        if (contents.substring(0,1).equals("I")){
            res.putExtra(IntentExtras.TYPE_BARCODE, formatName+TI);
            res.putExtra(IntentExtras.DATA_BARCODE, contents);
            res.putExtra(IntentExtras.DATA_DOCUMENT_CO, onCitizenColombia(formatName, contents, barcodeDocument));
            colombianCitizenBarcode.setTypeDocument(formatName+TI);
        }else if (Character.isDigit(contents.charAt(0))){
            res.putExtra(IntentExtras.TYPE_BARCODE, formatName+CC);
            res.putExtra(IntentExtras.DATA_BARCODE, contents);
            res.putExtra(IntentExtras.DATA_DOCUMENT_CO, onCitizenColombia(formatName, contents, barcodeDocument));
            colombianCitizenBarcode.setTypeDocument(formatName+CC);
        }else if (contents.substring(0,1).equals("A")){
            res.putExtra(IntentExtras.TYPE_BARCODE, formatName+CE);
            res.putExtra(IntentExtras.DATA_BARCODE, contents);
            res.putExtra(IntentExtras.DATA_DOCUMENT_CO, onForeignCitizen(formatName, contents, barcodeDocument));
            colombianCitizenBarcode.setTypeDocument(formatName+CE);
        }
        setResult(RESULT_OK, res);
        finish();
    }

    private ForeignBarcode onForeignCitizen(String formatName, String contents, BarcodeDocument barcodeDocument) {
        ForeignBarcode foreignBarcode = new ForeignBarcode();
        if (formatName.equals("PDF_417")) {
            foreignBarcode = ForeignStringUtils.parseForeignBarcode(contents);
        }
        return foreignBarcode;
    }
}
