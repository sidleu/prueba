package com.reconosersdk.reconosersdk.ui.document.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.reconosersdk.reconosersdk.R;
import com.reconosersdk.reconosersdk.entities.Document;
import com.reconosersdk.reconosersdk.ui.LibraryReconoSer;
import com.reconosersdk.reconosersdk.ui.base.BaseActivity;
import com.reconosersdk.reconosersdk.ui.document.interfaces.RequestDocumentContract;
import com.reconosersdk.reconosersdk.utils.Constants;
import com.reconosersdk.reconosersdk.utils.ImageUtils;
import com.reconosersdk.reconosersdk.utils.IntentExtras;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import timber.log.Timber;

public class RequestDocumentActivity extends BaseActivity implements RequestDocumentContract.RequestDocumentMvpView {

    @Inject
    RequestDocumentContract.RequestDocumentMvpPresenter presenter;
    private String reverseText;
    private String path = "";
    private boolean mexicanIne = false;
    String pathAnverse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_document);
        LibraryReconoSer.getComponent().inject(this);
        presenter.onAttach(this);
        presenter.initUI(getIntent().getExtras(), this);
    }

    @Override
    public void initUI(@NotNull String type, @NotNull String guidCiu, @NotNull String typeDoc, @NotNull String numDoc, @NotNull String saveUser, @Nullable String addData) {
        Intent intent = new Intent(this, DocumentAdActivity.class);
        intent.putExtra(IntentExtras.TEXT_SCAN, type);
        intent.putExtra(IntentExtras.GUID_CIUDADANO, guidCiu);
        intent.putExtra(IntentExtras.TYPE_DOCUMENT, typeDoc);
        intent.putExtra(IntentExtras.ADD_DATA, addData);
        intent.putExtra(IntentExtras.NUM_DOCUMENT, numDoc);
        intent.putExtra(IntentExtras.SAVE_USER, saveUser);
        startActivityForResult(intent, Constants.TAKE_PHOTO_DOCUMENT);

        //TODO TO TEST
        //Reverso
        /*String resultText1 = "CO\n" +
                "REGISTRADOR NACIONAL\n" +
                "Alexander vega KOcna\n" +
                "ICCOL011688684815001<<<<<<<<<<\n" +
                "8708173 M33 0\n" +
                "1041COL102625694 4<8\n" +
                "SANABRIA<MARTINES<<FEDERIC0<<<";
        String resultText = "011688684 8\n" +
                "CO\n" +
                "REGISTRADOR NACIONAL\n" +
                "Alexander Vega Rocha\n" +
                "ICCOLO11688684815001<<<<<<<««\n" +
                "8708173\n" +
                "M3301041COL1026256944<8\n" +
                "SANABRIA<MARTINES<<FEDERICO<<<.";*/
        /*String ocrCCDFailLastNames1 = "011688684 8\n" +
                "CO\n" +
                "REGISTRADOR NACIONAL\n" +
                "Alexander Vega Rocha\n" +
                "ICCOL011688684815001<<<<«<<<<<\n" +
                "8708173\n" +
                "M3301041COL1026256944<8\n" +
                "SANABRI\n" +
                "A<MARTINES<<FEDERIC0<<<";*/
        /*String ocrCCDFailAll = "REGISTRADOR NACIONAL\n" +
                "Aleander Vega Rocha\n" +
                "ICCOL000019675015001\n" +
                "<<<««<«<<<\n" +
                "890820 M3302251\n" +
                "COL1024499020 <<<2\n" +
                "MORENO<LATORRE <<JIM<POOL<<<<<<<<<<\n" +
                "B\n" +
                "5\n" +
                "3";*/
        //presenter.onDataRespondReverso(ocrCCDFailLastNames1, "", "");

        //Anverso
        /*String anversoCCJohan1 = "REPUBLICA DE COLOMBIA\nIDENTIFICACIÓN PERSONAL\nCEDULA DE CIUDADANIA\nRERUBL\nCOLO\nNÚMERO 1.001.043.498\nJIMENEZ RIVERA\nAPELLID0S\nJORANSEBASTIAN\nNOMBRES\nFIRMA\nREPUBL";
        String anversoCCJohan11= "DE\n$1\nMI.\nHep\ndow\nQ\nSearch\nREPUBLICA DE COLOMBIA\nIDENTIFICACION PERSONAL\nCEDULA DE CIUDADANIA\nREPUBLICA DE\nCOLOMBIA\nNUMERO\n1.001.043.498\nJIMENEZ\nRIVERA\nAPELLIDos\nJOHAN SEBASTIAN\nNOMBRES\nFIRMA\nMultimedia\nX\nMacBook Pro\n)\n\u0026\n$\n7\n6\n3\n#\nP\nT\nR";
        String anversoCCJohan12= "MILLe\nelp\nSearch\nREPUBLICA DE COLOMBIA\nIDENTIFICACIÓN PERSONAL\nCEDULA DE CIUDADANIA\nNUMESRO 1.001.043.498\nJIMENEZ\nRIVERA\nAFLLOs\nRUBCA D\nJOHAN SEBASTIAN\nNORES\nFIRMA\nMultimedia\nAa\nW\nMacBook Pro\n$\n8\n5\n4\n3\n#\nP\nU\nT\nR\nE";*/
        //String ocrCCDFailLastNames1 = "REPÜBLICA DE COLOMBIA\nNUIP 1.026.256.944\nCEOULA DE\nCIuDADANIA\nApelidos\nSANABRIA MARTINES\nFEDERICO\nNombres\nSexC\nM\nEstatura\n1.66\nNaconalidad\nCOL\nGS.\nO+\nFecha de nacimiento\n17 AGO 1987\nBOGOTAD.C. (CUNDINAMARCA)\nLugar de nacimiento\n01 SEPT 2005, BOGOTA D,C.\nFecha y lugar de expedicion\nFecha de expiración\n04 ENE 2033\nFirma";
        //presenter.onDataRespond(ocrCCDFailLastNames1, "", "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }
        if (requestCode == 2) {
            Timber.w("data %s",data);
            String errorMsg = data.getStringExtra(IntentExtras.ERROR_MSG);
            String typeBarcode = data.getStringExtra(IntentExtras.TYPE_BARCODE);
            String barcode = data.getStringExtra(IntentExtras.DATA_BARCODE);
            if (barcode == null) {
                onFinishError(Constants.ERROR_R106, Constants.ERROR_IMAGE_PROCESS, path);
            } else {
                if (typeBarcode != null && (typeBarcode.equals(Constants.PDF_417_CC) || typeBarcode.equals(Constants.PDF_417_TI)) ){
                    presenter.colombianReverseDocument(reverseText, barcode, 2, typeBarcode);
                } else  if (typeBarcode != null && typeBarcode.equals(Constants.PDF_417_CE)){
                    presenter.parseForeingCard(barcode, reverseText, typeBarcode);
                }
            }
        } else if (mexicanIne) {
            int typeDocument = data != null ? data.getIntExtra(IntentExtras.TYPE_DOCUMENT_DATA, 0) : 0;
            Timber.w("documentResult mexican ok %s", typeDocument);
            String path = data != null ? data.getStringExtra(IntentExtras.PATH_FILE_PHOTO) : "";
            if (typeDocument == Constants.MEXICAN_REVERSE_DOCUMENT || typeDocument == Constants.COLOMBIAN_TI_REVERSE_DOCUMENT){
                String pathReverso = ImageUtils.compressImageHD(path);
                Timber.w("ok compress %s", pathReverso);
                if (pathReverso != null) {
                    presenter.mexicanDocumentService(pathAnverse, pathReverso);
                }else{
                    onFinishError(Constants.ERROR_R106);
                }
            }else{
                onFinishError(Constants.ERROR_R114);
            }
        }else if (requestCode == Constants.TAKE_PHOTO_DOCUMENT && resultCode == RESULT_OK) {
            String textscan = data != null ? data.getStringExtra(IntentExtras.TEXT_SCAN) : "";
            String barcode = data != null ? data.getStringExtra(IntentExtras.DATA_BARCODE) : "";
            String typeBarcode = data != null ? data.getStringExtra(IntentExtras.TYPE_BARCODE) : "";
            int typeDocument = data != null ? data.getIntExtra(IntentExtras.TYPE_DOCUMENT_DATA, 0) : 0;
            presenter.onResultPhoto(data.getStringExtra(IntentExtras.PATH_FILE_PHOTO), textscan, barcode, typeBarcode, typeDocument);
        }
    }

    @Override
    public void onShowErrorCloud(int errorMsg) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onFinishData(@NotNull String path, @NotNull Document document) {
        Intent res = new Intent();
        res.putExtra(IntentExtras.PATH_FILE_PHOTO, path);
        res.putExtra(IntentExtras.DATA_DOCUMENT, document);
        setResult(RESULT_OK, res);
        dismissProgressDialog();
        finish();
    }

    @Override
    public void onNavigationBarCode(@NotNull String reverseText, int documentResult, String path) {
        this.reverseText = reverseText;
        this.path = path;
        Intent intent = new Intent(this, BarCodeActivity.class);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onNavigationReverso(@NotNull String path, int documentResult) {
        mexicanIne = true;
        pathAnverse = path;
        initUI("Reverso","","","","", null);
    }
}
