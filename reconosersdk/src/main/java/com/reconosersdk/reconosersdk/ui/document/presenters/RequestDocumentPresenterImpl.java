package com.reconosersdk.reconosersdk.ui.document.presenters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.reconosersdk.reconosersdk.R;
import com.reconosersdk.reconosersdk.citizens.ColombianMinorCitizen;
import com.reconosersdk.reconosersdk.citizens.EcuadorianCitizen;
import com.reconosersdk.reconosersdk.citizens.barcode.ColombianCitizenBarcode;
import com.reconosersdk.reconosersdk.citizens.barcode.ForeignBarcode;
import com.reconosersdk.reconosersdk.citizens.colombian.ColombianCCD;
import com.reconosersdk.reconosersdk.citizens.colombian.ColombianOCR;
import com.reconosersdk.reconosersdk.citizens.colombian.ForeignOCR;
import com.reconosersdk.reconosersdk.entities.DataReadDocument;
import com.reconosersdk.reconosersdk.entities.DataResult;
import com.reconosersdk.reconosersdk.entities.Document;
import com.reconosersdk.reconosersdk.entities.DocumentoAnverso;
import com.reconosersdk.reconosersdk.entities.DocumentoReverso;
import com.reconosersdk.reconosersdk.http.OlimpiaInterface;
import com.reconosersdk.reconosersdk.http.OlimpiaService;
import com.reconosersdk.reconosersdk.http.api.WebService;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.DocumentValidations;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.GuardarBiometriaIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.GuardarLogErrorIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.Validations;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ErrorEntransaccion;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.GuardarBiometria;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.GuardarLogError;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion;
import com.reconosersdk.reconosersdk.http.regula.entities.in.Anverso;
import com.reconosersdk.reconosersdk.http.regula.entities.in.Reverso;
import com.reconosersdk.reconosersdk.http.regula.entities.in.ValidarDocumentoGenericoIn;
import com.reconosersdk.reconosersdk.http.regula.entities.out.ValidarDocumentoGenericoOut;
import com.reconosersdk.reconosersdk.ui.LibraryReconoSer;
import com.reconosersdk.reconosersdk.ui.base.BasePresenter;
import com.reconosersdk.reconosersdk.ui.document.interfaces.RequestDocumentContract;
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.PreferencesSave;
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.ServicesOlimpia;
import com.reconosersdk.reconosersdk.utils.Constants;
import com.reconosersdk.reconosersdk.utils.ForeignStringUtils;
import com.reconosersdk.reconosersdk.utils.ImageUtils;
import com.reconosersdk.reconosersdk.utils.IntentExtras;
import com.reconosersdk.reconosersdk.utils.JsonUtils;
import com.reconosersdk.reconosersdk.utils.StringUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import timber.log.Timber;

import static com.reconosersdk.reconosersdk.utils.Constants.COLOMBIAN_DIGITAL_LAST_NAMES;
import static com.reconosersdk.reconosersdk.utils.Constants.COLOMBIAN_DIGITAL_NAMES;
import static com.reconosersdk.reconosersdk.utils.Constants.COLOMBIAN_LAST_NAMES;
import static com.reconosersdk.reconosersdk.utils.Constants.COLOMBIAN_NAMES;

public class RequestDocumentPresenterImpl<V extends RequestDocumentContract.RequestDocumentMvpView> extends BasePresenter<V> implements RequestDocumentContract.RequestDocumentMvpPresenter<V> {

    private static final String DEFAULT_SAVE = "0";
    private static final String NOT_SAVE = "1";
    private static final String SUM_TRANSACTION = "2";
    private static final String SAVE_NOT_GUIDCIU = "3";

    private static final int TOTAL_PERCENT = 100;

    private static final double OCR_NAMES_PERCENTAGE = 0.35;
    private static final double OCR_NAMES_AUX_PERCENTAGE = 0.70;
    private static final double OCR_NAMES_AUX_ANOTHER_PERCENTAGE = 0.50;
    private static final double OCR_NAMES_AUX_PERCENTAGE_1 = 0.20;
    private static final int DOC_STATE_INIT = 0;
    private static final int DOC_STATE_ONLY_OBVERSE = 1;
    private static final int DOC_STATE_ONLY_REVERSE = 2;
    private static final int DOC_STATE_FINISH = 3;
    private static final int FORMAT_DATE_LENGTH = 6;
    private static final double MIN_PERCENTAGE = 0.25;
    private static final double MAX_PERCENTAGE = 0.60;
    private static final int MIN_VALUE_NAME_LENGTH = 4;
    private static final int MIN_VALUE_LAST_NAMES = 3;
    private static final int MIN_VALUE_LAST_NAME_LENGTH = 8;
    private static final int MAX_VALUE_LAST_NAME_LENGTH = 12;
    private static final String TAG = "RequestDocPresenterImpl";

    //To send the validations
    private DocumentoAnverso documentoAnverso = DocumentoAnverso.getInstance();
    private DocumentoReverso documentoReverso = DocumentoReverso.CREATOR.getInstance();

    //To Colombian Document
    private ColombianOCR colombianOCR = ColombianOCR.Companion.getInstance();
    private ColombianCitizenBarcode colombianCitizenBarcode = ColombianCitizenBarcode.Companion.getInstance();
    private ColombianCCD colombianCCD = ColombianCCD.Companion.getInstance();
    private DocumentValidations documentValidations;

    //To TI
    ColombianMinorCitizen colombianMinorCitizen = new ColombianMinorCitizen();

    //Ecuadorian citizen
    private EcuadorianCitizen ecuadorianCitizen = EcuadorianCitizen.CREATOR.getInstance();

    //Foreign Document
    private ForeignOCR foreignOCR = ForeignOCR.CREATOR.getInstance();
    private ForeignBarcode foreignBarcode = ForeignBarcode.CREATOR.getInstance();

    private String dataText;
    private String barcode;
    private String typeBarcode;
    private int typeDocumentData = 0;
    private int documentResult;
    private Context context;
    private String path;
    private boolean saveAnverso;
    private int type;
    Disposable disposable;
    private String guidCiudadano;
    private String typeDocument;
    private String numDocument;
    private String saveUser;
    private String addData;
    private int quality;
    //To show in textView
    private String result;
    private List<DataReadDocument> resultsData = new ArrayList<>();
    private DataResult dataResult = new DataResult();
    private String guidProcessAgreement;

    public RequestDocumentPresenterImpl() {
    }

    @Override
    public void initUI(@NotNull Bundle extras, Context context) {
        this.context = context;

        //Set singleton into Colombian Citizen
        colombianCitizenBarcode = ColombianCitizenBarcode.Companion.getInstance();
        colombianOCR = ColombianOCR.Companion.getInstance();
        colombianCCD = ColombianCCD.Companion.getInstance();


        documentValidations = new DocumentValidations();
        if (extras != null) {
            if (!validateExtras(extras)) {
                return;
            }
            switch (Objects.requireNonNull(extras.getString(IntentExtras.TEXT_SCAN))) {
                case Constants.ANVERSO:
                    type = 1;
                    if (!ServicesOlimpia.getInstance().isValidSerDocAnv()) {
                        cleanColombianData();
                        cleanForeignData();
                        getMvpView().onFinishError(Constants.ERROR_R101);
                        return;
                    } else {

                        getMvpView().initUI(
                                Constants.ANVERSO,
                                guidCiudadano,
                                typeDocument,
                                numDocument,
                                saveUser,
                                addData);
                    }
                    break;
                case Constants.REVERSO:
                    type = 2;
                    if (!ServicesOlimpia.getInstance().isValidSerDocAnv()) {
                        cleanColombianData();
                        cleanEcuadorianData();
                        cleanForeignData();
                        getMvpView().onFinishError(Constants.ERROR_R101);
                        return;
                    } else {
                        getMvpView().initUI(
                                Constants.REVERSO,
                                guidCiudadano,
                                typeDocument,
                                numDocument,
                                saveUser,
                                addData);
                    }
                    break;
            }
        }
    }

    private boolean validateExtras(Bundle extras) {
        guidCiudadano = extras.getString(IntentExtras.GUID_CIUDADANO, "");
        typeDocument = extras.getString(IntentExtras.TYPE_DOCUMENT, "");
        numDocument = extras.getString(IntentExtras.NUM_DOCUMENT, "");
        saveUser = extras.getString(IntentExtras.SAVE_USER, "");
        quality = extras.getInt(IntentExtras.QUALITY, 85);
        addData = extras.getString(IntentExtras.ADD_DATA, null);
        guidProcessAgreement = extras.getString(IntentExtras.GUID_PROCESS_AGREEMENT, "");
        savePrefQuality(quality);
        if (DEFAULT_SAVE.equals(PreferencesSave.INSTANCE.getSavePhoto())) {
            return validateGuidCiu(guidCiudadano, saveUser);
        } else if (SAVE_NOT_GUIDCIU.equals(PreferencesSave.INSTANCE.getSavePhoto())) {
            return validateDataDocument(typeDocument, numDocument, saveUser, quality);
        }
        return true;
    }

    private void savePrefQuality(int quality) {
        if (quality < 0 || quality > 100) {
            this.quality = 70;
        }
        PreferencesSave.INSTANCE.setStorageQuality(this.quality);
    }

    private boolean validateDataDocument(String type, String numDocument, String saveUser, int quality) {
        boolean isValid = true;
        if (type.isEmpty()) {
            sendErrorFinish(Constants.ERROR_R109, Constants.ERROR_NO_PARAM_TYPE);
            isValid = false;
        }
        if (numDocument.isEmpty()) {
            sendErrorFinish(Constants.ERROR_R110, Constants.ERROR_NO_PARAM_NUM);
            isValid = false;
        } else if (numDocument.length() < 4 || numDocument.length() > 12) {
            sendErrorFinish(Constants.ERROR_R111, Constants.ERROR_PARAM_NUM);
            isValid = false;
        }
        if (saveUser.isEmpty()) {
            sendErrorFinish(Constants.ERROR_R109, Constants.ERROR_NO_PARAM_TYPE);
            isValid = false;
        }
        return isValid;
    }

    private boolean validateGuidCiu(String data, String saveUser) {
        if (data.isEmpty()) {
            sendErrorFinish(Constants.ERROR_R108, Constants.ERROR_NO_PARAM_GUICIU);
            return false;
        }
        if (saveUser.isEmpty()) {
            sendErrorFinish(Constants.ERROR_R109, Constants.ERROR_NO_PARAM_TYPE);
            return false;
        }
        return true;
    }

    private void sendErrorFinish(String code, String error) {
        if (getMvpView() != null) {
            cleanColombianData();
            cleanEcuadorianData();
            cleanForeignData();
            getMvpView().onFinishError(code, error);
        }
    }

    @Override
    public void onResultPhoto(@NotNull String pathFile, @NotNull String dataDocument, String barcode, String typeBarcode, int typeDocument) {
        path = pathFile;
        dataText = dataDocument;
        this.barcode = barcode;
        this.typeBarcode = typeBarcode;
        this.documentResult = documentResult;
        this.typeDocumentData = typeDocument;
        getMvpView().showProgessDialog(R.string.loading_saving);
        getBitmap(pathFile, typeBarcode);
    }

    public void getBitmap(String path, String typeBarcode) {
        tryReloadAndDetectInImage(typeBarcode);
    }

    @Override
    public void tryReloadAndDetectInImage(String typeBarcode) {
        if (type == 1) {
            //No fix  106
            //String noFixFed = "HELP\nWINDOW\nDEVELOPER\nBOOKMARKS\nVIEW HISTORY\nIT\nPRC\nINFE\n6\nCLIE\nS\n403\nJWORHOR XD XD. REG\nD\nMAIL\nOUTLOOK.OFFICE.COM/MAIL/INBOX/ID/AAQKAGVMZMM1MTJILTYXZJGTNDE4MI05ZDMYLWM2ZDU3MZLWA\nFIRE\nREC\nVPN\nOQRCODE MONKEY- E... D WEBVALIDATOR\nREGISTRO DE UNIVERSI.. PDE CONVERTIR PDF A WO..\nAUTOCITA COVID. CON..\nREPÜBLICA DE COLOMBIA\nCEOULA DE\nCIUDADANIA\nNUIP 1.026.256.944\nSANABRIA MARTINES\nAPELLIDOS\nFEDERICO\nNOMBRES\nSEXO\nM\nESTATURA\n1.66\nNACIONALIDAD\nCOL\n9.S.\nFECHA DE NACIMIENTO\nO+\n17 AGO 1987\nLUGAR DE NACIMIENTO\nBOGOTA D.C. (CUNDINAMARCA)\nFECHA Y LUGAR DE EXPEDICIÓN\n01 SEPT 2005\nBOGOTA D.C.\nFECHA DE EXPIRACIÓN\n04 ENE 2033\nFMA\nW\nP\nMACBOOK PRO";
            //String noFixFed1 = "Help\nBookmarks Developer Window\nView History\nOIntoO Pro mieG\nMallWor Hor Xd xd.a |reg |40304:|cle\noutlook.office.com/mailinbox/id/AAQKAGVmZmM1MTJILTYXZİgtNDE4MIO5ZDMyLWM2ZDU3MzlWZwViZAAQAI\nVPN\nPOF Gonvertlr PDF a WO... QRCode Monkey E.. WebValidator\nAutocita Covid. Con.. Registro de Universi.\nREPUBLICA DE COLOMBIA\nCEDULA DE\nCUDADANIA\nNUIP 1.026.256.944\nSANABRIA MARTINES\nApellidos\nFEDERICO\nNombres\nSexo\nEstatura\nM\n1.66\nNacionalidad\nCOL\nG.S.\nFecha de nacimiento\nO+\n17 AGO 1987\nBOGOTA D.C.\n(CUNDINAMARCA)\nLugar de nacimiento\n01 SEPT 2005,\nBOGOTAD.C.\nFecha y lugar de expedición\nFecha de\nexpiración\n04 ENE 2033\nFima\nP\nMacBook Pro";
            //String noFixAB = "Tools Window Help\n3Preview File Edit View Go\nHighight Rotate Markup Search\nInspector Zoom Share\nView Andres Bolivar CCD Anverso.jpeg\nREPUBLICA DE COLOMBIA\nCEDULA DE\nCIUDADANÍA\nNUIP 80.240.809\nApellidos\nBOLIVAR ARIAS\nNombres\nANDRES MAURICIO\nSexo\nEstatura\nNacionalidad\nM\n1.75\nG.S.\nCOL\nO+\nFecha de nacimiento\n01 OCT 1981\nLugar de nacimiento\nBOGOTA D.C. (CUNDINAMARCA)\nFecha y lugar de expedición\n28 OCT 1999, BOGOTA D.C.\nFecha de expiración\n29 DIC 2030\nFirma\nMacBook Pro\n8\n7\n6\n5\n4\n3#\n2®\nR\nW";
            //String noFixFed2 ="Window Help\nView\nFinder File Edit\nHhilant Rotate Markup nue\nInspecto Zoom Share\nView Andres Bolivar CCD Anverso.jpeg\nREPUBLICA DE COLOMBIA\nCEDULA DE\nCIUDADANÍA\nNUIP 80.240.809\nApelidos\nBOLIVAR ARIAS\nNombres\nANDRES MAURICIO\nSexo\nEstatura\nNacionalidad\nM\n1.75\nG.S.\nCOL\nO+\nFecha de nacimiento\n01 0CT 1981\nLugar de nacimiento\nBOGOTA D.C. (CUNDINAMARCA)\nFecha y lugar de expedición\n28 OCT 1999, BOGOTA D.C.\nFecha de expiración\n29 DIC 2030\nFirma\nMacBook Pro\n%\n(o\n5\n3\n#\n2@\nR\nE\nW";
            //String noFixAB1 = "S\nTools Window Help\nView\nPreview File\nHighilight Rotate Markup Search\nLspecot cm Share\nView Andres Bolivar CcD Anverso.jpeg\nREPUBLICA DE COLOMBIA\nCÉDULA DE\nCIUDADANÍA\nNUIP 80.240.809\nApelidos\nBOLIVAR\nARIAS\nNombres\nANDRES MAURICIO\nSexo\nEstatura\nNacionalidad\nM\n1.75\nCOL\nG.S.\nFecha de nacimiento\nO+\n01 OCT 1981\nLugar de nacimiento\nBOGOTA D.C. (CUNDINAMARCA)\nFecha y lugar de\nexpedición\n28 OCT 1999, BOGOTA D.C.\nFecha de expiración\nFirma\n29 DIC 2030\nSerann chnt.9092.03.97at.RARo0\nO\nLaunch suc\nMacBook Pro\n5\n4\n3\n#\nU\nT\nR\nE\nW";
            //String noFixNelRH = "Help WindowW\nGO Tools\nEdit View\nPreview File\nHighlight Rotate Markup Sear\nInspector Zoom Share\nView CD3A.png\nREPUBLICA DE COLOMBIA\nCÉDULA DE\nCIUDADANIA\nNUIP\n60.423.436\nApellidos\nGARCIA MANOSALVA\nNELLY\nNombres\nSexo\nF\nEstatura\n1.48\nNacionalidad\nCOL\nG.S.\n0+\nFecha de nacimiento\n04 JUN 1974\nLugar de nacimiento\nTIBU (NORTE DE SANTANDER)\nFecha y lugar de expedición\n11 MAR 1993, TIBU\n09 SEPT 2032\nFecha de expiración\nFirma\nWol Gorca MonosduC\n292:13 LF\nE TODO O Problerns p Git Terminal Q Find , RunBuild ProtilerE Logcat VApp Quality InsightsApp Inspection\nU Launch succeeded (moments ago)\nW\nP\nMacBook Pro\n%\n6\n2@";
            //String noFixCPRH = "Preview Fle Edit View GO TOOls Window Help\nView CDIA.PNG\nScarch...\nHighlight Rotate Markup\nZoóm Share\nInspector\nREPÚBLICA DE COLOMBIA\nCÉDULA DE\nCIUDADANÍA\n1.090.406.237\nNUIP\nTORRES VEGA\nApellidos\nCLAUDIA PATRICIA\nNombres\nSexo\nEstatura\nNacionalidad\nF\n1.57\nCOL\nG.S.\nFecha de nacimiento\n27 NOV 1988\nCUCUTA (NORTE DE SANTANDER)\nLugar de nacimiento\n30 JUL 2007, CUCUTA\nFecha y lugar de expedición\nFecha de expiración\n16 MAR 2032\nFirma\nMacBook Pro\nese)\n\u0026\n9\n8\n6\n5\n4\n3\n#\nP\nY\nT\nR\nW";
            //Sting noFixCPRH1 = "Hep\nTOols\nView\nFle\nES )\nO View CD1A.PNG\nHighlight Rotate Marikup Sear\nInspector Zoom Share\nREPÚBLICA DE COLOMBIA\nCEDULA DE\nCIUDADANÍA\n1.090.406.237\nNUIP\nApellidos\nTORRES\nVEGA\nCLAUDIA PATRICIA\nNombres\nSexo\nEstatura\nNacionalidad\nF\n1.57\nCOL\nGS.\nFecha de nacimiento\nO+\n27 NOV 1988\nCUCUTA (NORTE DE SANTANDER)\nLugar de nacimiento\nFecha y lugar de expedición\n30 JUL 2007, CUCUTA\nFecha de expiración\n16\nMAR 2032\nFirma\nChDAecsA(O\nAPpCuaity\n2R2b charns) LF UTS 4spaces Pfeat\nMacBook Pro\n$\n9\n8\n5\n4\n3\n#\n2e\nY\nT\nR\nW\n€";
            //String noFixLCBorn = "Help\nWindow\nTools\nGO\nView\nES\nPreview File\nsdk android-String Utils.java [sdk_android.reconosersdk.main\nSearch\nHighlight Rotate Markup\nInspector Zoom Share\nView CD4A.png\nREPUBLICA DE COLOMBIA\nCEDULA DE\nCIUDADANIA\n1.070.607.051\nNUIP\nApelidos\nBARRERO CRUZ\nNombres\nLEYDI CATHERINE\nSexo\nEstatura\n1.62\nNacionalidad\nCOL\nG.S.\nO+\n24 MAYO 1992\nFecha de nacimiento\nLugar de nacimiento\nGIRARDOT (CUNDINAMARCA)\nFecha y lugar de\nexpedición\n28 JUN 2010, GIRARDOT\nFecha de expiración\n02 NOV 2031\nFirma\nMacBoo Pro\nesc\n\u0026\n7\n5\n4\n3\n#\n2\n@";
            //onDataRespond(noFixNelRH, barcode, typeBarcode);
            onDataRespond(dataText, barcode, typeBarcode);
        } else {
            onDataRespondReverso(dataText, barcode, typeBarcode);
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onDataRespond(@NotNull String text, @NonNull String barcode, @NonNull String typeBarcode) {
        //Clean first data
        cleanColombianData();
        int documentResult = typeDocumentData;
        if (typeDocumentData == 0) {
            documentResult = StringUtils.documentData(text, 1);
        }
        String[] repeatedWords;
        boolean repeatKeyword;
        path = compressImageDocument();
        if (path == null) getMvpView().onFinishError(Constants.ERROR_R106);
        //If the obverse document is here
        if (anyObverseDocument(documentResult)) {
            if (barcode == null) {
                barcode = "";
            }
            Timber.e("Barcode has a value  into obverse %s: ", barcode);
            documentoAnverso = new DocumentoAnverso();
            documentoAnverso.setDocumentoAnverso(text, barcode);
            String obverseText = JsonUtils.stringObject(Objects.requireNonNull(documentoAnverso).getstringAnverso());
            //Switch-case
            switch (documentResult) {
                //If the document is Colombian
                case Constants.COLOMBIAN_OBVERSE_DOCUMENT:
                    repeatedWords = context.getResources().getStringArray(R.array.colombian_repeatedly_cc_observe_words);
                    repeatKeyword = StringUtils.repeatedlyText(obverseText, Constants.ANVERSO, repeatedWords);
                    if (!repeatKeyword) {
                        if (colombianOCR.getOcrState() == DOC_STATE_FINISH) {
                            cleanColombianData();
                        }
                        if (foreignOCR.getOcrState() == DOC_STATE_FINISH) {
                            cleanForeignData();
                        }
                        colombianOCR.setDocumentType(Constants.COLOMBIAN_OBVERSE_DOCUMENT);
                        colombianObverseDocument(obverseText, documentResult);
                    } else {
                        cleanColombianData();
                        Timber.w("R106  Colombian CC !repeatKeyword %s", !repeatKeyword);
                        getMvpView().onFinishError(Constants.ERROR_R106);
                    }
                    break;
                //If the document is Ecuadorian
                case Constants.ECUADORIAN_OBVERSE_DOCUMENT:
                    repeatedWords = context.getResources().getStringArray(R.array.ecuadorian_repeatedly_obverse_words);
                    repeatKeyword = StringUtils.repeatedlyText(obverseText, Constants.ANVERSO, repeatedWords);
                    if (!repeatKeyword) {
                        colombianOCR.setDocumentType(Constants.ECUADORIAN_OBVERSE_DOCUMENT);
                        ecuadorianObverseDocument(obverseText, barcode, typeBarcode, documentResult);
                    } else {
                        Timber.w("R106 ECUADORIAN !repeatKeyword %s", !repeatKeyword);
                        getMvpView().onFinishError(Constants.ERROR_R106);
                    }
                    break;

                case Constants.FOREIGNER_OBVERSE_DOCUMENT:

                    if (foreignOCR.getOcrState() == DOC_STATE_FINISH) {
                        cleanForeignData();
                    }

                    colombianOCR.setDocumentType(Constants.FOREIGNER_OBVERSE_DOCUMENT);
                    documentoAnverso.setDocumentoAnverso(text, barcode);
                    parseForeignOCR(obverseText);
                    changeForeignState(Constants.ANVERSO);

                    Timber.w("foreignBarcode %s", String.valueOf(foreignBarcode.getNumber()));
                    Timber.w("foreignOCR %s", String.valueOf(foreignOCR.getNumber()));

                    resultsData.add(new DataReadDocument("Foreign OCR", JsonUtils.stringObject(foreignOCR)));
                    resultsData.add(new DataReadDocument("Foreign BARCODE", JsonUtils.stringObject(foreignBarcode)));
                    dataResult.setResult(resultsData);

                    if (JsonUtils.stringObject(foreignOCR).contains("404")) {
                        getMvpView().onFinishError(OlimpiaService.setErrorCustom(Constants.ERROR_R404, Constants.ERROR_NO_FOUND_PARAM + "\n" + JsonUtils.stringObject(foreignOCR).replaceAll("\"", "")));
                        cleanForeignData();
                    } else {
                        Document document = new Document(documentoAnverso, null, path, String.valueOf(documentResult), foreignOCR.getOcrState().toString(), dataResult, null);
                        isSavePhoto(document, path);
                    }
                    break;
                case Constants.COLOMBIAN_TI_OBVERSE_DOCUMENT:
                    fillMinorsObject(documentResult);
                    colombianOCR.setDocumentType(Constants.COLOMBIAN_TI_OBVERSE_DOCUMENT);
                    break;
                case Constants.MEXICAN_OBVERSE_DOCUMENT:
                    colombianOCR.setDocumentType(Constants.MEXICAN_OBVERSE_DOCUMENT);
                    getMvpView().onNavigationReverso(path, documentResult);
                    break;
                case Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT:

                    repeatedWords = context.getResources().getStringArray(R.array.colombian_repeatedly_ccd_observe_words);
                    repeatKeyword = StringUtils.repeatedlyText(obverseText, Constants.ANVERSO, repeatedWords);
                    if (!repeatKeyword) {
                        if (colombianCCD.getOcrState() == DOC_STATE_FINISH) {
                            cleanColombianData();
                        }
                        colombianCCD.setDocumentType(Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT);
                        colombianObverseDocument(obverseText, documentResult);
                    } else {
                        cleanColombianData();
                        Timber.w("R106 Colombian CCD !repeatKeyword %s", !repeatKeyword);
                        getMvpView().onFinishError(Constants.ERROR_R106);
                    }
                    break;
                default:
                    cleanColombianData();
                    cleanEcuadorianData();
                    cleanForeignData();
                    Timber.w("R106 Clean COLOMBIAN_OBVERSE_DOCUMENT ");
                    getMvpView().onFinishError(Constants.ERROR_R106);
                    break;
            }
        } else {
            cleanColombianData();
            cleanEcuadorianData();
            cleanForeignData();
            Timber.w("R106 Clean  ");
            getMvpView().onFinishError(Constants.ERROR_R106, Constants.ERROR_IMAGE_PROCESS, path);
        }

    }

    private boolean anyObverseDocument(int documentResult) {
        return documentResult == Constants.COLOMBIAN_OBVERSE_DOCUMENT || documentResult == Constants.COLOMBIAN_TI_OBVERSE_DOCUMENT || documentResult == Constants.ECUADORIAN_OBVERSE_DOCUMENT || documentResult == Constants.FOREIGNER_OBVERSE_DOCUMENT || documentResult == Constants.MEXICAN_OBVERSE_DOCUMENT || documentResult == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT;
    }


    private void parseForeignOCR(String obverseText) {
        foreignOCR.setNombres(ForeignStringUtils.getData("NOMBRES", obverseText));
        foreignOCR.setApellidos(ForeignStringUtils.getData("APELLIDOS", obverseText));
        foreignOCR.setNacionalidad(ForeignStringUtils.getData("NACIONALIDAD", obverseText));
        foreignOCR.setSexo(ForeignStringUtils.getData("SEXO", obverseText));
        foreignOCR.setRh(ForeignStringUtils.getData("RH", obverseText));
        foreignOCR.setFechaNacimiento(ForeignStringUtils.getData("NACIMIENTO", obverseText));
        foreignOCR.setFechaExpedicion(ForeignStringUtils.getData("EXPEDICION", obverseText));
        foreignOCR.setFechaVencimiento(ForeignStringUtils.getData("VENCE", obverseText));
        String number = ForeignStringUtils.getData("NUMERO", obverseText);
        if (number.isEmpty()) {
            number = "404";
        }
        foreignOCR.setNumber(Integer.valueOf(number));
        foreignOCR.setTipo(ForeignStringUtils.getData("TIPO", obverseText));
    }


    private void fillMinorsObject(int documentResult) {
        Document document;

        switch (documentResult) {
            case Constants.COLOMBIAN_TI_OBVERSE_DOCUMENT:
                if (colombianOCR.getOcrState() == DOC_STATE_ONLY_REVERSE) {
                    ColombianCitizenBarcode colombianCitizenBarcodeAux = StringUtils.parseDataCode(documentoReverso.getStringBarcode(), context);
                    setColombianBarcode(colombianCitizenBarcodeAux);
                    colombianMinorCitizen = StringUtils.getColombianMinorCitizen(documentoAnverso, documentoReverso, colombianCitizenBarcode, context, documentResult);
                    if (!validateBarcodeOcrTI()) {
                        goToError();
                    }
                    colombianOCR.setOcrState(StringUtils.documentState(DOC_STATE_ONLY_REVERSE, Constants.ANVERSO));
                    resultsData.add(new DataReadDocument("TI OCR", JsonUtils.stringObject(colombianMinorCitizen)));
                    resultsData.add(new DataReadDocument("TI Barcode", JsonUtils.stringObject(colombianCitizenBarcode)));
                    dataResult.setResult(resultsData);
                    document = new Document(documentoAnverso, documentoReverso, path, String.valueOf(documentResult), "0", dataResult, null);
                    isSavePhoto(document, path);
                    if (colombianOCR.getOcrState() == DOC_STATE_FINISH) {
                        cleanColombianData();
                    }
                } else if (colombianOCR.getOcrState() == DOC_STATE_INIT || colombianOCR.getOcrState() == DOC_STATE_ONLY_OBVERSE) {
                    colombianMinorCitizen = StringUtils.getColombianMinorCitizen(documentoAnverso, null, null, context, documentResult);
                    colombianOCR.setOcrState(StringUtils.documentState(DOC_STATE_INIT, Constants.ANVERSO));
                    resultsData.add(new DataReadDocument("TI OCR", JsonUtils.stringObject(colombianMinorCitizen)));
                    resultsData.add(new DataReadDocument("TI Barcode", JsonUtils.stringObject(colombianCitizenBarcode)));
                    dataResult.setResult(resultsData);
                    document = new Document(documentoAnverso, null, path, String.valueOf(documentResult), "0", dataResult, null);
                    isSavePhoto(document, path);
                }
                break;
            case Constants.COLOMBIAN_TI_REVERSE_DOCUMENT:
                if (colombianOCR.getOcrState() == DOC_STATE_ONLY_OBVERSE) {
                    colombianMinorCitizen = StringUtils.getColombianMinorCitizen(documentoAnverso, documentoReverso, colombianCitizenBarcode, context, documentResult);
                    if (!Objects.requireNonNull(documentoReverso.getStringBarcode()).isEmpty()) {
                        ColombianCitizenBarcode colombianCitizenBarcodeAux = StringUtils.parseDataCode(documentoReverso.getStringBarcode(), context);
                        setColombianBarcode(colombianCitizenBarcodeAux);
                    } else {
                        goToErrorBarcode();
                    }
                    colombianOCR.setOcrState(StringUtils.documentState(DOC_STATE_ONLY_OBVERSE, Constants.REVERSO));
                    if (!validateBarcodeOcrTI()) {
                        goToError();
                    }
                    resultsData.add(new DataReadDocument("TI OCR", JsonUtils.stringObject(colombianMinorCitizen)));
                    resultsData.add(new DataReadDocument("TI Barcode", JsonUtils.stringObject(colombianCitizenBarcode)));
                    dataResult.setResult(resultsData);
                    document = new Document(documentoAnverso, documentoReverso, path, String.valueOf(documentResult), "0", dataResult, null);
                    isSavePhoto(document, path);
                    if (colombianOCR.getOcrState() == DOC_STATE_FINISH) {
                        cleanColombianData();
                    }
                } else if (colombianOCR.getOcrState() == DOC_STATE_INIT || colombianOCR.getOcrState() == DOC_STATE_ONLY_REVERSE) {
                    colombianMinorCitizen = StringUtils.getColombianMinorCitizen(null, documentoReverso, null, context, documentResult);
                    colombianOCR.setOcrState(StringUtils.documentState(DOC_STATE_INIT, Constants.REVERSO));
                    if (!Objects.requireNonNull(documentoReverso.getStringBarcode()).isEmpty()) {
                        ColombianCitizenBarcode colombianCitizenBarcodeAux = StringUtils.parseDataCode(documentoReverso.getStringBarcode(), context);
                        setColombianBarcode(colombianCitizenBarcodeAux);
                    } else {
                        goToErrorBarcode();
                    }
                    resultsData.add(new DataReadDocument("TI OCR", JsonUtils.stringObject(colombianMinorCitizen)));
                    resultsData.add(new DataReadDocument("TI Barcode", JsonUtils.stringObject(colombianCitizenBarcode)));
                    dataResult.setResult(resultsData);
                    document = new Document(null, documentoReverso, path, String.valueOf(documentResult), "0", dataResult, null);
                    isSavePhoto(document, path);
                }
                break;
        }
    }

    private boolean validateBarcodeOcrTI() {
        boolean validate = true;
        String fullNameOCR = colombianMinorCitizen.getApellidos() + " " + colombianMinorCitizen.getNombres();
        String fullNameBarcode = colombianCitizenBarcode.getPrimerApellido() + " " + colombianCitizenBarcode.getSegundoApellido() + " " + colombianCitizenBarcode.getPrimerNombre() + " " + colombianCitizenBarcode.getSegundoNombre();
        String idOCR = colombianMinorCitizen.getNumero();
        String idBarcode = colombianCitizenBarcode.getCedula();
        if (StringUtils.computeLevenshteinDistance(fullNameBarcode, fullNameOCR) > 3) {
            validate = false;
        }
        if (StringUtils.computeLevenshteinDistance(idBarcode, idOCR) > 2) {
            validate = false;
        }
        return validate;
    }

    private boolean validateBarcodeOcrCE() {
        boolean validate = true;
        String fullNameOCR = foreignOCR.getApellidos() + " " + foreignOCR.getNombres();
        String fullNameBarcode = foreignBarcode.getNombres();
        String idOCR = foreignOCR.getNumber().toString();
        String idBarcode = foreignBarcode.getNumber().toString();
        if (StringUtils.computeLevenshteinDistance(fullNameBarcode, fullNameOCR) > 3) {
            validate = false;
        }
        if (StringUtils.computeLevenshteinDistance(idBarcode, idOCR) > 2) {
            validate = false;
        }
        return validate;
    }

    private void ecuadorianObverseDocument(String obverseText, String barcode, String typeBarcode, int documentResult) {

        boolean ecuadorianNames = findEcuadorianNames(obverseText);
        boolean ecuadorianNumber = findEcuadorianNumber(obverseText);
        boolean ecuadorianBorn = findEcuadorianBornDate(obverseText);
        boolean ecuadorianGender = findEcuadorianGender(obverseText);

        // Barcode ecuadorian document is FORMAT_CODE_128
        if (typeBarcode.equals("1")) {
            ecuadorianCitizen.setIdBarcode(barcode);
        } else {
            ecuadorianCitizen.setIdBarcode("");
        }
        barcode = ecuadorianCitizen.getIdBarcode();
        //If any values is null or empty
        if (barcode == null || barcode.isEmpty() || !ecuadorianNames || !ecuadorianNumber || !ecuadorianBorn || !ecuadorianGender) {
            //createDataError();
            //To avoid the wrong words and noise
            cleanEcuadorianData();
            cleanColombianData();
            Timber.w("R106 ecuadorianCitizen empty ");
            getMvpView().onFinishError(Constants.ERROR_R106);
        } else {
            if (!barcode.equals(ecuadorianCitizen.getDocumentId())) {
                //To avoid id isn't equals to barcode data
                cleanEcuadorianData();
                cleanColombianData();
                getMvpView().onFinishError(Constants.ERROR_R106);
            } else {
                changeEcuadorianState(Constants.ANVERSO);
                sendEcuadorianData(Constants.ANVERSO, documentResult);
            }
        }

    }

    private void sendEcuadorianData(String docPosition, int documentResult) {
        if (ecuadorianCitizen != null && ecuadorianCitizen.getEcuadorianDocState() != null && ecuadorianCitizen.getEcuadorianDocState() != DOC_STATE_INIT) {
            if (ecuadorianCitizen.getEcuadorianDocState() != DOC_STATE_FINISH) {
                resultsData.add(new DataReadDocument("Ecuadorian OCR", JsonUtils.stringObject(ecuadorianCitizen)));
                resultsData.add(new DataReadDocument("Ecuadorian BARCODE", JsonUtils.stringObject(ecuadorianCitizen)));
                dataResult.setResult(resultsData);
                result = "\n# Ecuadorian OCR: " + ecuadorianCitizen.toString()
                        + "\n# Ecuadorian BARCODE: " + ecuadorianCitizen.getIdBarcode();
            }
            if (docPosition.equals(Constants.ANVERSO)) {
                Document document = new Document(documentoAnverso, null, path, String.valueOf(documentResult), ecuadorianCitizen.getEcuadorianDocState().toString(), dataResult, null);
                saveLog(document, path);
            } else if (docPosition.equals(Constants.REVERSO)) {
                Document document = new Document(null, documentoReverso, path, String.valueOf(documentResult), ecuadorianCitizen.getEcuadorianDocState().toString(), dataResult, null);
                saveLog(document, path);
            } else {
                //To avoid the wrong words and noise
                cleanEcuadorianData();
                cleanColombianData();
                Timber.w("R106 clean ecuatorian");
                getMvpView().onFinishError(Constants.ERROR_R106);
            }
            if (ecuadorianCitizen.getEcuadorianDocState() == DOC_STATE_FINISH) {
                cleanEcuadorianData();
            }
        }
    }

    private void cleanEcuadorianData() {
        //Restart the document state
        ecuadorianCitizen.setColombianCitizen("", "", "", "", "", "", "", "", 0);
        documentoAnverso = new DocumentoAnverso();
        documentoReverso = new DocumentoReverso();
    }

    private void findCCDTotalName(String obverseText, int docType) {
        String[] stringValues;
        if (obverseText == null || obverseText.isEmpty()) {
            return;
        }
        double idPercentage1 = 0.0;
        double idPercentage2 = 0.0;
        double idPercentage3 = 0.0;
        //Find the Name and last name int the value
        //Evaluate if the document is a CC or CCD
        int documentType = 1;
        if (docType == Constants.COLOMBIAN_OBVERSE_DOCUMENT) {
            if (colombianOCR.getDocumentType() != null) {
                documentType = colombianOCR.getDocumentType();
            }
        } else if (docType == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT) {
            if (colombianCCD.getDocumentType() != null) {
                documentType = colombianCCD.getDocumentType();
            }
            //Avoid false positive
            obverseText = obverseText.toUpperCase();
            obverseText = obverseText.replace(Constants.COLOMBIAN_DIGITAL_HEIGHT, "");
            obverseText = obverseText.replace(Constants.COLOMBIAN_DIGITAL_ANOTHER_HEIGHT, "");
            obverseText = obverseText.toLowerCase();
        }
        stringValues = obverseText.split(StringUtils.BACK_LINE_REVERSO);
        if (stringValues.length == 1) {
            stringValues = obverseText.split(StringUtils.BACK_LINE_ANVERSO);
        }

        if (documentType == Constants.COLOMBIAN_OBVERSE_DOCUMENT) {
            //Delete possible wrong words
            for (int i = 0; i < stringValues.length; i++) {
                //Search the name to replace the lastName
                idPercentage1 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_NATIONALITY, stringValues[i].toUpperCase());
                idPercentage2 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_REPUBLIC, stringValues[i].toUpperCase());
                if (idPercentage1 > OCR_NAMES_AUX_PERCENTAGE) {
                    //Delete array
                    stringValues[i] = "";
                }
                if (idPercentage2 > OCR_NAMES_AUX_PERCENTAGE) {
                    //Delete array
                    stringValues[i] = "";
                }
            }
            findCCDNames(stringValues, COLOMBIAN_LAST_NAMES, documentType, true);
            findCCDNames(stringValues, COLOMBIAN_NAMES, documentType, false);
            //Improve accuracy
            fixEmptyValues(stringValues, colombianOCR.getNames(), colombianOCR.getLastNames());
            fixNamesEqualsLastNameCC(stringValues, colombianOCR.getNames(), colombianOCR.getLastNames());
            colombianOCR.setLastNames(Objects.requireNonNull(colombianOCR.getLastNames()).replace(colombianOCR.getNames(), "").trim());
        } else if (documentType == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT) {
            //Search the word "NOMBRES" is easier
            findCCDNames(stringValues, COLOMBIAN_DIGITAL_NAMES, documentType, false);
            findCCDNames(stringValues, COLOMBIAN_DIGITAL_NAMES, documentType, true);
            //Clean smell data
            colombianCCD.setLastNamesObverse(colombianCCD.getLastNamesObverse().replace("\\", "").toUpperCase().trim());
            colombianCCD.setNamesObverse(colombianCCD.getNamesObverse().replace("\\", "").toUpperCase().trim());
        }
        Log.e("Colombian names", colombianOCR.getNames());
        Log.e("Colombian lastnames", colombianOCR.getLastNames());
        Log.e("CDColombian names", colombianCCD.getNamesObverse());
        Log.e("CDColombian lastnames", colombianCCD.getLastNamesObverse());
    }

    private void fixEmptyValues(String[] stringValues, String names, String lastNames) {
        //Avoid possible empty values
        if (names.isEmpty() || lastNames.isEmpty()) {
            int numberIndex = 0;
            int possibleIndex = 0;
            String integerHelper = "";
            for (int i = 0; i < stringValues.length; i++) {
                //Search the name to replace the lastName
                integerHelper = stringValues[i].replaceAll("[^0-9]", "");
                if (integerHelper.length() > possibleIndex) {
                    numberIndex = i;
                }
            }
            try {
                if (lastNames.isEmpty()) {
                    colombianOCR.setLastNames(stringValues[numberIndex + 1]);
                }
                if (names.isEmpty()) {
                    colombianOCR.setNames(stringValues[numberIndex + 2]);
                }
            } catch (Exception e) {
                Timber.e("Possible error Names and lastNames CC: %s", e.getMessage());
            }
        }
    }

    private void fixNamesEqualsLastNameCC(String[] stringValues, String names, String lastNames) {
        double idPercentage = 0.0;
        double maxIdPercentage = 0.0;
        int nameIndex = 0;
        //Avoid empty values
        if (!names.isEmpty() && !lastNames.isEmpty()) {
            idPercentage = StringUtils.diceCoefficientOptimized(names, lastNames);
        }
        //If equals the name and lastNames
        if (idPercentage > OCR_NAMES_AUX_PERCENTAGE) {
            for (int i = 0; i < stringValues.length; i++) {
                //Search the name to replace the lastName
                idPercentage = StringUtils.diceCoefficientOptimized(names, stringValues[i].toUpperCase());
                if (idPercentage > maxIdPercentage) {
                    maxIdPercentage = idPercentage;
                    nameIndex = i;
                }
            }
            try {
                colombianOCR.setLastNames(stringValues[nameIndex - 1]);
            } catch (Exception e) {
                colombianOCR.setLastNames(lastNames);
            }
        }
    }

    private void findCCDNames(String[] stringValues, String colombianDigitalNames, Integer documentType, boolean isLastNames) {
        double maxIdPercentage = 0.0;
        double idPercentage;
        int nameIndex = 0;
        for (int i = 0; i < stringValues.length; i++) {
            //Search the word "NOMBRES" is easier
            if (stringValues[i].toUpperCase().contains(COLOMBIAN_DIGITAL_NAMES) && documentType == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT) {
                Log.e("find string before", stringValues[i]);
                stringValues[i] = stringValues[i].replaceAll("\\.", "");
                //stringValues[i] = stringValues[i].replaceAll(ONLY_NUMBERS, "");
                Log.e("find string after", stringValues[i]);
            }
            idPercentage = StringUtils.diceCoefficientOptimized(colombianDigitalNames, stringValues[i].toUpperCase());
            if (idPercentage > maxIdPercentage) {
                maxIdPercentage = idPercentage;
                nameIndex = i + 1;
            }
        }
        //Array Length
        if (nameIndex > stringValues.length - 1) {
            if (colombianDigitalNames.equals(COLOMBIAN_DIGITAL_LAST_NAMES)) {
                if (documentType == Constants.COLOMBIAN_OBVERSE_DOCUMENT) {
                    colombianOCR.setLastNames("");
                } else if (documentType == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT) {
                    colombianCCD.setLastNamesObverse("");
                }
            } else if (colombianDigitalNames.equals(COLOMBIAN_DIGITAL_NAMES)) {
                if (documentType == Constants.COLOMBIAN_OBVERSE_DOCUMENT) {
                    colombianOCR.setNames("");
                } else if (documentType == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT) {
                    colombianCCD.setNamesObverse("");
                }

            }
        } else {
            if (maxIdPercentage < OCR_NAMES_PERCENTAGE) {
                if (isLastNames) {
                    if (documentType == Constants.COLOMBIAN_OBVERSE_DOCUMENT) {
                        colombianOCR.setLastNames("");
                    } else if (documentType == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT) {

                        double idLastNamePercentage = 0.0;
                        for (int i = 0; i < stringValues.length; i++) {
                            //Search the word "APELLIDO" is easier
                            idPercentage = StringUtils.diceCoefficientOptimized(COLOMBIAN_LAST_NAMES, stringValues[i].toUpperCase());
                            if (idPercentage > maxIdPercentage) {
                                maxIdPercentage = idPercentage;
                                nameIndex = i + 1;
                            }
                        }
                        //REPUBLICA DE COLOMBIA\n NUIP\n APELLIDOS\
                        try {
                            int lastNameIndexAux = nameIndex - 2;
                            double idLastNamePercentageAux = 0.0;
                            idLastNamePercentage = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_NATIONALITY, stringValues[lastNameIndexAux].toUpperCase());
                            idLastNamePercentageAux = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_NATIONALITY, stringValues[lastNameIndexAux - 1].toUpperCase());

                            //if(idLastNamePercentage < OCR_NAMES_AUX_PERCENTAGE && stringValues[]){
                            String findPattern = ".*\\d.*";
                            //to back
                            if ((idLastNamePercentage < OCR_NAMES_AUX_PERCENTAGE && !stringValues[lastNameIndexAux].isEmpty() && !stringValues[lastNameIndexAux].matches(findPattern)
                                    && !stringValues[lastNameIndexAux].contains(Constants.COLOMBIAN_DIGITAL_NUIP) && !stringValues[lastNameIndexAux].contains(Constants.COLOMBIAN_DIGITAL_NUIP)) &&
                                    (idLastNamePercentageAux < OCR_NAMES_AUX_PERCENTAGE && !stringValues[lastNameIndexAux - 1].isEmpty() && !stringValues[lastNameIndexAux - 1].matches(findPattern)
                                            && !stringValues[lastNameIndexAux - 1].contains(Constants.COLOMBIAN_DIGITAL_NUIP) && !stringValues[lastNameIndexAux - 1].contains(Constants.COLOMBIAN_DIGITAL_NUIP))) {
                                Timber.w("lastNameIndex IS: %s", lastNameIndexAux);
                                colombianCCD.setLastNamesObverse(stringValues[lastNameIndexAux - 1]);
                            } else {
                                //to front
                                for (int lastNameIndex = nameIndex + 2; lastNameIndex < stringValues.length; lastNameIndex++) {
                                    //Improve search
                                    idLastNamePercentage = StringUtils.diceCoefficientOptimized(COLOMBIAN_LAST_NAMES, stringValues[lastNameIndex].toUpperCase());
                                    //If the string contains "APELLIDO" or is empty or contains any number
                                    findPattern = ".*\\d.*";
                                    if (idLastNamePercentage < OCR_NAMES_AUX_PERCENTAGE && !stringValues[lastNameIndex].isEmpty() && !stringValues[lastNameIndex].matches(findPattern)) {
                                        Timber.w("lastNameIndex IS: %s", lastNameIndex);
                                        //Improve Accuracy
                                        String[] words = stringValues[lastNameIndex].split(" ");
                                        if (words.length == 1) {
                                            try {
                                                String lastNameAux = stringValues[lastNameIndex] + " " + stringValues[lastNameIndex + 1];
                                                lastNameAux = lastNameAux.trim();
                                                Timber.w("Finder CCD name obverse IS: %s", lastNameAux);
                                                colombianCCD.setLastNamesObverse(lastNameAux);
                                            } catch (Exception e) {
                                                Timber.w("Finder CCD name obverse IS: %s", stringValues[lastNameIndex]);
                                                colombianCCD.setLastNamesObverse(stringValues[lastNameIndex]);
                                            }
                                        } else {
                                            Timber.w("Finder CCD name obverse IS: %s", stringValues[lastNameIndex]);
                                            colombianCCD.setLastNamesObverse(stringValues[lastNameIndex]);
                                        }
                                        break;
                                    } else {
                                        try {
                                            Timber.w("Finder CCD name obverse IS: %s", stringValues[nameIndex - 1]);
                                            colombianCCD.setNamesObverse(stringValues[nameIndex - 1]);
                                        } catch (Exception e) {
                                            colombianCCD.setNamesObverse("");
                                        }
                                    }
                                }//end for
                            }
                        } catch (Exception e) {
                            Timber.w("Exception IS: %s", e.getMessage());
                            colombianCCD.setLastNamesObverse("");
                        }//end catch Exception
                    }
                    //End isLastName
                } else {
                    if (documentType == Constants.COLOMBIAN_OBVERSE_DOCUMENT) {
                        colombianOCR.setNames("");
                    } else if (documentType == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT) {
                        for (int i = 0; i < stringValues.length; i++) {
                            //Search the word "APELLIDO" is easier
                            idPercentage = StringUtils.diceCoefficientOptimized(COLOMBIAN_LAST_NAMES, stringValues[i].toUpperCase());
                            if (idPercentage > maxIdPercentage) {
                                maxIdPercentage = idPercentage;
                                nameIndex = i + 1;
                            }
                        }
                        try {
                            String findPattern = ".*\\d.*";
                            //to back
                            if (!stringValues[nameIndex + 1].isEmpty() && !stringValues[nameIndex + 1].matches(findPattern) && !stringValues[nameIndex + 2].isEmpty() && !stringValues[nameIndex + 2].matches(findPattern)) {
                                Timber.w("Finder CCD name obverse IS: %s", stringValues[nameIndex + 4]);
                                colombianCCD.setNamesObverse(stringValues[nameIndex + 4]);
                            } else {
                                //to front
                                //Find word "SEXO" three position later
                                double idSexPercentage1 = 0.0;
                                double idSexPercentage2 = 0.0;
                                idSexPercentage1 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_SEX, stringValues[nameIndex + 3].toUpperCase());
                                idSexPercentage2 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_SEX, stringValues[nameIndex + 1].toUpperCase());
                                if (!stringValues[nameIndex + 3].isEmpty() && !stringValues[nameIndex + 3].contains(Constants.COLOMBIAN_DIGITAL_SEX)
                                        && !stringValues[nameIndex + 1].contains(Constants.COLOMBIAN_DIGITAL_ANOTHER_SEX) && !stringValues[nameIndex + 1].contains(Constants.COLOMBIAN_DIGITAL_ANOTHER_SEX1)
                                        && idSexPercentage1 < OCR_NAMES_AUX_ANOTHER_PERCENTAGE && idSexPercentage2 < OCR_NAMES_AUX_ANOTHER_PERCENTAGE) {
                                    Timber.w("Finder CCD name obverse IS: %s", stringValues[nameIndex + 1]);
                                    colombianCCD.setNamesObverse(stringValues[nameIndex + 1]);
                                    ;
                                } else {
                                    Timber.w("CCD name obverse not founded");
                                    colombianCCD.setNamesObverse("");
                                }
                            }
                        } catch (Exception e) {
                            Timber.w("Exception IS: %s", e.getMessage());
                            colombianCCD.setLastNamesObverse("");
                        }//end catch Exception
                    }
                }
                //End maxIdPercentage < OCR_NAMES_PERCENTAGE
            } else {
                if (isLastNames) {
                    if (documentType == Constants.COLOMBIAN_OBVERSE_DOCUMENT) {
                        try {
                            //reverse for loop
                            double idLastNamePercentage = 0.0;
                            double idLastNamePercentage1 = 0.0;
                            for (int lastNameIndex = nameIndex - 2; lastNameIndex >= -1; lastNameIndex--) {
                                if (lastNameIndex < 0) {
                                    Timber.w("lastNameIndex IS: %s", lastNameIndex);
                                    colombianOCR.setLastNames("");
                                    break;
                                } else {
                                    idLastNamePercentage = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_LAST_NAMES_AUX, stringValues[lastNameIndex].toUpperCase());
                                    //If the string contains "NUMERO" or is empty
                                    if (idLastNamePercentage < OCR_NAMES_AUX_PERCENTAGE && !stringValues[lastNameIndex].isEmpty() && !stringValues[lastNameIndex].contains(COLOMBIAN_LAST_NAMES)) {
                                        Timber.w("lastNameIndex IS: %s", lastNameIndex);

                                        //Improve Accuracy
                                        String[] words = stringValues[lastNameIndex].split(" ");
                                        if (words.length == 1) {
                                            try {
                                                idLastNamePercentage = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_LAST_NAMES_AUX, stringValues[lastNameIndex - 1].toUpperCase());
                                                if (idLastNamePercentage < OCR_NAMES_AUX_PERCENTAGE && !stringValues[lastNameIndex - 1].isEmpty() && !stringValues[lastNameIndex - 1].contains(COLOMBIAN_LAST_NAMES)) {
                                                    String lastNameAux = stringValues[lastNameIndex];
                                                    idLastNamePercentage = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_LAST_NAMES_AUX1, lastNameAux.toUpperCase());
                                                    if (lastNameAux.length() < MIN_VALUE_NAME_LENGTH || idLastNamePercentage > OCR_NAMES_AUX_PERCENTAGE || lastNameAux.contains(":")) {
                                                        lastNameAux = stringValues[lastNameIndex - 1].trim();
                                                    } else {
                                                        boolean isNumber = false;
                                                        //If the last name is separated by \n or contains word "NUMERO"
                                                        idLastNamePercentage = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_LAST_NAMES_AUX, stringValues[lastNameIndex - 3].toUpperCase());
                                                        idLastNamePercentage1 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_LAST_NAMES_AUX, stringValues[lastNameIndex - 2].toUpperCase());
                                                        if( stringValues[lastNameIndex - 2].matches(".*\\d.*") || idLastNamePercentage1 > OCR_NAMES_AUX_PERCENTAGE ) {
                                                            //@ just is aux
                                                            lastNameAux = stringValues[lastNameIndex - 1] + " @" + stringValues[lastNameIndex];
                                                            //Improve accuracy
                                                            lastNameAux = lastNameAux.trim();
                                                        } else if ( (stringValues[lastNameIndex - 3].matches(".*\\d.*") && !stringValues[lastNameIndex - 2].matches(".*\\d.*")) || (idLastNamePercentage > OCR_NAMES_AUX_PERCENTAGE && idLastNamePercentage1 < OCR_NAMES_AUX_PERCENTAGE) ) {
                                                            //@ just is aux
                                                            lastNameAux = stringValues[lastNameIndex - 2] + " " + stringValues[lastNameIndex - 1] + " @" + stringValues[lastNameIndex];
                                                            //Improve accuracy
                                                            lastNameAux = lastNameAux.trim();
                                                            isNumber = true;
                                                        }
                                                        //If there is any short word
                                                        /*if( (stringValues[lastNameIndex - 2].matches(".*\\d.*") || idLastNamePercentage1> OCR_NAMES_AUX_PERCENTAGE) ){
                                                            //" @" just is aux
                                                            lastNameAux = stringValues[lastNameIndex - 1] + " @" + stringValues[lastNameIndex];
                                                            lastNameAux = lastNameAux.trim();
                                                            isNumber = true;
                                                        }*/
                                                        //If there is any short word
                                                        words = lastNameAux.split("@");
                                                        if (words.length > 1 && words.length <= MIN_VALUE_LAST_NAMES) {
                                                            if (isNumber || words.length > 2 || words[0].length()>MAX_VALUE_LAST_NAME_LENGTH ) {
                                                                int position = lastNameAux.indexOf("@");
                                                                lastNameAux = lastNameAux.substring(0, position);
                                                            }else{
                                                                lastNameAux = lastNameAux.replace("@", "");
                                                            }
                                                            //lastNameAux = stringValues[lastNameIndex - 1].trim();
                                                        }
                                                    }
                                                    colombianOCR.setLastNames(lastNameAux.trim());
                                                } else {
                                                    colombianOCR.setLastNames(stringValues[lastNameIndex]);
                                                }
                                            } catch (Exception e) {
                                                Timber.w("Exception IS: %s", e.getMessage());
                                                colombianOCR.setLastNames(stringValues[lastNameIndex]);
                                            }
                                        } else {
                                            //Avoid set names into lastNames
                                            String findPattern = ".*\\d.*";
                                            idLastNamePercentage = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_LAST_NAMES_AUX, stringValues[lastNameIndex - 2].toUpperCase());
                                            double idLastNamePercentageAux = 0.0;
                                            idLastNamePercentageAux = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_LAST_NAMES_AUX, stringValues[lastNameIndex - 1].toUpperCase());
                                            if (idLastNamePercentage > OCR_NAMES_AUX_PERCENTAGE || idLastNamePercentageAux > OCR_NAMES_AUX_PERCENTAGE || stringValues[lastNameIndex - 2].matches(findPattern) || stringValues[lastNameIndex - 1].matches(findPattern)) {
                                                colombianOCR.setLastNames(stringValues[lastNameIndex]);
                                            } else {
                                                String lastNameAux = stringValues[lastNameIndex - 2] + " " + stringValues[lastNameIndex - 1];
                                                //Improve accuracy
                                                lastNameAux = lastNameAux.trim();
                                                //If there is any short word
                                                words = lastNameAux.split(" ");
                                                if (words.length <= MIN_VALUE_LAST_NAMES) {
                                                    lastNameAux = stringValues[lastNameIndex - 2].trim();
                                                }
                                                if (lastNameAux.length() < MIN_VALUE_LAST_NAMES) {
                                                    lastNameAux = stringValues[lastNameIndex - 3].trim();
                                                }
                                                colombianOCR.setLastNames(lastNameAux.trim());
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                        } catch (Exception e) {
                            colombianOCR.setLastNames("");
                        }
                    } else if (documentType == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT) {
                        //reverse for loop
                        double idLastNamePercentage = 0.0;
                        for (int lastNameIndex = nameIndex - 2; lastNameIndex >= -1; lastNameIndex--) {
                            if (lastNameIndex < 0) {
                                Timber.w("lastNameIndex IS: %s", lastNameIndex);
                                colombianCCD.setLastNamesObverse("");
                                break;
                            } else {
                                stringValues[lastNameIndex] = stringValues[lastNameIndex].toUpperCase();
                                double idLastNamePercentageAux = 0.0;
                                double idLastNamePercentageAux1 = 0.0;
                                //Improve search
                                String findPattern = ".*\\d.*";
                                try {
                                    idLastNamePercentageAux = StringUtils.diceCoefficientOptimized(COLOMBIAN_LAST_NAMES, stringValues[lastNameIndex - 1].toUpperCase());
                                    idLastNamePercentageAux1 = StringUtils.diceCoefficientOptimized(COLOMBIAN_LAST_NAMES, stringValues[lastNameIndex - 2].toUpperCase());
                                } catch (Exception e) {
                                    idLastNamePercentageAux = 0.0;
                                    idLastNamePercentageAux1 = 0.0;
                                }
                                idLastNamePercentage = StringUtils.diceCoefficientOptimized(COLOMBIAN_LAST_NAMES, stringValues[lastNameIndex].toUpperCase());
                                //If the string contains "APELLIDO" or is empty or contains any number
                                boolean isPossibleName = (idLastNamePercentageAux > OCR_NAMES_AUX_ANOTHER_PERCENTAGE) || (idLastNamePercentageAux1 > OCR_NAMES_AUX_ANOTHER_PERCENTAGE);
                                if (!isPossibleName && idLastNamePercentage < OCR_NAMES_AUX_PERCENTAGE && !stringValues[lastNameIndex].isEmpty()) {
                                    Timber.w("lastNameIndex IS: %s", lastNameIndex);
                                    //Improve Accuracy if contains NUIP
                                    if (stringValues[lastNameIndex].contains(Constants.COLOMBIAN_DIGITAL_NUIP) || stringValues[lastNameIndex].contains(Constants.COLOMBIAN_DIGITAL_ANOTHER_NUIP)) {
                                        lastNameIndex = lastNameIndex + 1;
                                        //Avoid false positives
                                        idLastNamePercentage = StringUtils.diceCoefficientOptimized(COLOMBIAN_LAST_NAMES, stringValues[lastNameIndex].toUpperCase());
                                        if (idLastNamePercentage > OCR_NAMES_AUX_PERCENTAGE) {
                                            lastNameIndex = lastNameIndex + 1;
                                        } else if (idLastNamePercentage > OCR_NAMES_AUX_ANOTHER_PERCENTAGE) {
                                            lastNameIndex = lastNameIndex - 1;
                                        }
                                    } else if (!stringValues[lastNameIndex].contains(Constants.COLOMBIAN_DIGITAL_NUIP) && !stringValues[lastNameIndex].contains(Constants.COLOMBIAN_DIGITAL_ANOTHER_NUIP) ) {
                                        //Avoid false positives it finds possible numbers or possible "CEDULA DE CUIDADANIA" text
                                        idLastNamePercentage = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_ANOTHER_CEDULA, stringValues[lastNameIndex].toUpperCase());
                                        idLastNamePercentageAux = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_ANOTHER_CIUDADANIA, stringValues[lastNameIndex].toUpperCase());
                                        if(stringValues[lastNameIndex].matches(findPattern) || stringValues[lastNameIndex].contains(Constants.COLOMBIAN_DIGITAL_ANOTHER_CEDULA) || stringValues[lastNameIndex].contains(Constants.COLOMBIAN_DIGITAL_ANOTHER_CIUDADANIA)
                                            || idLastNamePercentage > OCR_NAMES_AUX_ANOTHER_PERCENTAGE || idLastNamePercentageAux > OCR_NAMES_AUX_ANOTHER_PERCENTAGE ) {
                                            lastNameIndex = lastNameIndex + 2;
                                        }
                                    } else if (stringValues[lastNameIndex - 1].contains(Constants.COLOMBIAN_DIGITAL_NUIP) || stringValues[lastNameIndex - 1].contains(Constants.COLOMBIAN_DIGITAL_ANOTHER_NUIP)) {
                                        lastNameIndex = lastNameIndex + 1;
                                    }

                                    String[] words = stringValues[lastNameIndex].split(" ");
                                    if (words.length == 1) {
                                        try {
                                            String lastNameAux = "";
                                            idLastNamePercentage = StringUtils.diceCoefficientOptimized(COLOMBIAN_LAST_NAMES, stringValues[lastNameIndex + 1].toUpperCase());
                                            idLastNamePercentageAux = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_NAMES_OK, stringValues[lastNameIndex + 1].toUpperCase());
                                            if (idLastNamePercentage > OCR_NAMES_AUX_PERCENTAGE && idLastNamePercentageAux > OCR_NAMES_AUX_PERCENTAGE) {
                                                lastNameAux = stringValues[lastNameIndex - 1] + " " + stringValues[lastNameIndex];
                                            } else {
                                                lastNameAux = stringValues[lastNameIndex] + " " + stringValues[lastNameIndex - 1];
                                            }
                                            lastNameAux = lastNameAux.trim();
                                            //Improve accuracy avoid word "APELLIDOS" and "NOMBRES"
                                            words = lastNameAux.split(" ");
                                            lastNameAux = "";
                                            for (String word : words) {
                                                idLastNamePercentage = StringUtils.diceCoefficientOptimized(COLOMBIAN_LAST_NAMES, word.toUpperCase());
                                                idLastNamePercentageAux = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_NAMES_OK, word.toUpperCase());
                                                idLastNamePercentageAux1 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_ANOTHER_LAST_NAMES, word.toUpperCase());
                                                if (idLastNamePercentage < OCR_NAMES_AUX_ANOTHER_PERCENTAGE && idLastNamePercentageAux < OCR_NAMES_AUX_ANOTHER_PERCENTAGE
                                                        && idLastNamePercentageAux1 < OCR_NAMES_AUX_ANOTHER_PERCENTAGE) {
                                                    lastNameAux = lastNameAux + " " + word;
                                                }
                                            }
                                            Timber.w("Finder CCD lastName obverse IS: %s", lastNameAux);
                                            lastNameAux = lastNameAux.trim();
                                            colombianCCD.setLastNamesObverse(lastNameAux);
                                        } catch (Exception e) {
                                            //Improve accuracy
                                            String lastNameAux = "";
                                            idLastNamePercentage = StringUtils.diceCoefficientOptimized(COLOMBIAN_LAST_NAMES, stringValues[lastNameIndex].toUpperCase());
                                            if (idLastNamePercentage < OCR_NAMES_AUX_ANOTHER_PERCENTAGE) {
                                                lastNameAux = stringValues[lastNameIndex];
                                            } else {
                                                lastNameAux = stringValues[lastNameIndex - 1];
                                            }
                                            Timber.w("Finder CCD lastName obverse IS: %s", lastNameAux);
                                            colombianCCD.setLastNamesObverse(lastNameAux);
                                        }
                                    } else {
                                        //Improve accuracy
                                        String lastNameAux = "";
                                        idLastNamePercentage = StringUtils.diceCoefficientOptimized(COLOMBIAN_LAST_NAMES, stringValues[lastNameIndex].toUpperCase());
                                        if (idLastNamePercentage < OCR_NAMES_AUX_ANOTHER_PERCENTAGE) {
                                            lastNameAux = stringValues[lastNameIndex];
                                        } else {
                                            lastNameAux = stringValues[lastNameIndex - 1];
                                        }
                                        Timber.w("Finder CCD lastName obverse IS: %s", lastNameAux);
                                        colombianCCD.setLastNamesObverse(lastNameAux.trim());
                                    }
                                    break;
                                }
                            }//else lastNameIndex > 0
                        }//end for
                    } //end if (documentType == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT) {
                }// End isLastName
                //Set Names into document
                else {
                    if (documentType == Constants.COLOMBIAN_OBVERSE_DOCUMENT) {
                        try {
                            double idLastNamePercentage1 = 0.0;
                            double idLastNamePercentage2 = 0.0;
                            double idLastNamePercentage3 = 0.0;
                            double idLastNamePercentage4 = 0.0;
                            double idLastNamePercentage5 = 0.0;
                            boolean nameAssigned = false;
                            for (int nameIndexAux = nameIndex; nameIndexAux < stringValues.length; nameIndexAux++) {
                                idLastNamePercentage1 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_LAST_NAMES_AUX, stringValues[nameIndexAux].toUpperCase());
                                idLastNamePercentage2 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_NAMES_AUX1, stringValues[nameIndexAux].toUpperCase());
                                idLastNamePercentage3 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_NAMES_AUX2, stringValues[nameIndexAux].toUpperCase());
                                //If the string contains "NUMERO" or is empty
                                if ((idLastNamePercentage1 < OCR_NAMES_AUX_PERCENTAGE && idLastNamePercentage2 < OCR_NAMES_AUX_PERCENTAGE && idLastNamePercentage3 < OCR_NAMES_AUX_PERCENTAGE) && (!stringValues[nameIndexAux].isEmpty())) {
                                    Timber.w("nameIndexAux IS: %s", nameIndexAux);
                                    String[] onlyChar;
                                    onlyChar = stringValues[nameIndexAux].split(" ");
                                    boolean isOnlyChar = false;
                                    for (String s : onlyChar) {
                                        if (s.length() <= 1) {
                                            isOnlyChar = true;
                                        }
                                    }
                                    idLastNamePercentage3 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_NAMES_AUX4, stringValues[nameIndexAux].toUpperCase());
                                    if (isOnlyChar || stringValues[nameIndexAux].contains(Constants.COLOMBIAN_NATIONALITY) || idLastNamePercentage3 > OCR_NAMES_AUX_ANOTHER_PERCENTAGE) {
                                        nameIndexAux = nameIndexAux + 1;
                                    }
                                    //Improve Accuracy
                                    String nameAux = "";
                                    nameAux = colombianOCR.getNames();
                                    nameAux = nameAux.trim();
                                    String[] words;
                                    if (nameAux.isEmpty()) {
                                        words = stringValues[nameIndexAux].split(" ");
                                    } else {
                                        words = nameAux.split(" ");
                                    }
                                    if (words.length == 1) {
                                        try {
                                            //Find if the next word is "NUMERO" or similar
                                            idLastNamePercentage1 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_LAST_NAMES_AUX, stringValues[nameIndexAux + 1].toUpperCase());
                                            idLastNamePercentage2 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_NAMES_AUX1, stringValues[nameIndexAux + 1].toUpperCase());
                                            idLastNamePercentage3 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_NAMES_AUX2, stringValues[nameIndexAux + 1].toUpperCase());
                                            idLastNamePercentage4 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_NAMES_AUX3, stringValues[nameIndexAux + 1].toUpperCase());
                                            if (idLastNamePercentage1 < OCR_NAMES_AUX_PERCENTAGE_1 && idLastNamePercentage2 < OCR_NAMES_AUX_PERCENTAGE_1 && idLastNamePercentage3 < OCR_NAMES_AUX_PERCENTAGE_1 && idLastNamePercentage4 < OCR_NAMES_AUX_PERCENTAGE_1 && !stringValues[nameIndexAux + 1].isEmpty() && stringValues[nameIndexAux].length() > MAX_VALUE_LAST_NAME_LENGTH) {
                                                //KoSRES
                                                if (nameAux.isEmpty()) {
                                                    nameAux = stringValues[nameIndexAux];
                                                }
                                                nameAux = nameAux + " " + stringValues[nameIndexAux + 1];
                                                Timber.w("true name IS: %s", nameAux);
                                                colombianOCR.setNames(nameAux);
                                            } else if (colombianOCR.getNames() != null || colombianOCR.getNames().isEmpty()) {
                                                if (nameAux.isEmpty()) {
                                                    nameAux = stringValues[nameIndexAux];
                                                }
                                                //Update accuracy
                                                idLastNamePercentage4 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_COUNTRY, nameAux.toUpperCase());
                                                idLastNamePercentage5 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_NAMES_OK, nameAux.toUpperCase());
                                                if (idLastNamePercentage5 > OCR_NAMES_AUX_PERCENTAGE_1) {
                                                    colombianOCR.setNames(stringValues[nameIndexAux - 2]);
                                                } else if (idLastNamePercentage4 > OCR_NAMES_AUX_PERCENTAGE_1 || nameAux.length() <= MIN_VALUE_NAME_LENGTH) {
                                                    colombianOCR.setNames(stringValues[nameIndexAux + 1]);
                                                } else {
                                                    colombianOCR.setNames(nameAux);
                                                }
                                                nameAssigned = true;
                                                break;
                                            } else {
                                                //Update accuracy
                                                idLastNamePercentage5 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_NAMES_OK, stringValues[nameIndexAux].toUpperCase());
                                                if (idLastNamePercentage5 > OCR_NAMES_AUX_PERCENTAGE_1) {
                                                    colombianOCR.setNames(stringValues[nameIndexAux - 2]);
                                                } else {
                                                    colombianOCR.setNames(nameAux);
                                                }
                                                colombianOCR.setNames(stringValues[nameIndexAux]);
                                                nameAssigned = true;
                                                break;
                                            }
                                        } catch (Exception e) {
                                            Timber.w("Exception IS: %s", e.getMessage());
                                            colombianOCR.setNames(stringValues[nameIndexAux]);
                                            nameAssigned = true;
                                            break;
                                        }
                                    } else {
                                        if (nameAux.isEmpty()) {
                                            nameAux = stringValues[nameIndexAux];
                                        }
                                        colombianOCR.setNames(nameAux);
                                        nameAssigned = true;
                                        break;
                                    }
                                }
                            }//End For
                            //If name wasn't founded
                            if (!nameAssigned) {
                                colombianOCR.setNames("");
                            }
                        } catch (Exception e) {
                            colombianOCR.setNames("");
                        }
                        //End CC document
                    } else if (documentType == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT) {
                        Timber.w("setLastNamesObverse IS: %s", stringValues[nameIndex]);
                        int nameIndexAux = nameIndex;
                        try {
                            double idNamePercentage = 0.0;
                            double idSexPercentage1 = 0.0;
                            double idSexPercentage2 = 0.0;
                            double idSexPercentage3 = 0.0;
                            double idNameHeightPercentage = 0.0;
                            idSexPercentage1 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_SEX, stringValues[nameIndexAux].toUpperCase());
                            idSexPercentage2 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_SEX, stringValues[nameIndexAux + 1].toUpperCase());
                            idSexPercentage3 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_SEX, stringValues[nameIndexAux + 2].toUpperCase());
                            idNameHeightPercentage = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_HEIGHT, stringValues[nameIndexAux + 1].toUpperCase());
                            String nameAux = stringValues[nameIndexAux].toUpperCase().trim();
                            String[] wordsAux = nameAux.split(" ");
                            boolean isNotPossibleName = (nameAux.contains(Constants.COLOMBIAN_DIGITAL_SEX) || nameAux.contains(Constants.COLOMBIAN_DIGITAL_ANOTHER_SEX)
                                    || nameAux.contains(Constants.COLOMBIAN_DIGITAL_ANOTHER_SEX1) || idSexPercentage1 > OCR_NAMES_AUX_ANOTHER_PERCENTAGE || wordsAux[0].length() <= 2
                                    || (idNameHeightPercentage > OCR_NAMES_AUX_ANOTHER_PERCENTAGE && wordsAux.length <= 1));
                            String findPattern = ".*\\d.*";
                            boolean avoidStringSex = (nameAux.contains(Constants.COLOMBIAN_DIGITAL_SEX) || nameAux.contains(Constants.COLOMBIAN_DIGITAL_ANOTHER_SEX)
                                    || nameAux.contains(Constants.COLOMBIAN_DIGITAL_ANOTHER_SEX1) || idSexPercentage1 > OCR_NAMES_AUX_ANOTHER_PERCENTAGE || idSexPercentage2 > OCR_NAMES_AUX_ANOTHER_PERCENTAGE
                                    || idSexPercentage3 > OCR_NAMES_AUX_ANOTHER_PERCENTAGE || stringValues[nameIndexAux].length() <= 2);
                            if (!nameAux.isEmpty() && !nameAux.matches(findPattern) && !isNotPossibleName) {
                                String[] words = nameAux.split(" ");
                                if (words.length == 1) {
                                    try {
                                        double idNameNationalityPercentage = 0.0;
                                        double idNameNationalityPercentage1 = 0.0;
                                        double idNameHeightPercentage1 = 0.0;
                                        double idNamePercentageAux = 0.0;
                                        try {
                                            idNameNationalityPercentage = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_COUNTRY_CITIZEN, stringValues[nameIndexAux + 1].toUpperCase());
                                            idNameHeightPercentage = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_HEIGHT, stringValues[nameIndexAux + 1].toUpperCase());
                                            idNameNationalityPercentage1 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_COUNTRY_CITIZEN, stringValues[nameIndexAux + 2].toUpperCase());
                                            idNameHeightPercentage1 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_HEIGHT, stringValues[nameIndexAux + 2].toUpperCase());
                                            idNamePercentageAux = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_NAMES, stringValues[nameIndexAux + 1].toUpperCase());
                                        } catch (Exception e) {
                                            idNameNationalityPercentage = 0.0;
                                            idNameHeightPercentage = 0.0;
                                            idNameNationalityPercentage1 = 0.0;
                                            idNameHeightPercentage1 = 0.0;
                                            idNamePercentageAux = 0.0;
                                        }
                                        //Search possible second Name
                                        boolean isPossibleSecondName = (idNameNationalityPercentage < OCR_NAMES_AUX_PERCENTAGE) || (idNameHeightPercentage < OCR_NAMES_AUX_PERCENTAGE) ||
                                                (idNameNationalityPercentage1 < OCR_NAMES_AUX_PERCENTAGE) || (idNameHeightPercentage1 < OCR_NAMES_AUX_PERCENTAGE)
                                                || avoidStringSex || (idNamePercentageAux < OCR_NAMES_AUX_PERCENTAGE);
                                        String nameAux1 = "";
                                        if (!isPossibleSecondName) {
                                            if ((stringValues[nameIndexAux].length() > 2)) {
                                                nameAux1 = stringValues[nameIndexAux] + " " + stringValues[nameIndexAux + 1];
                                            } else {
                                                nameAux1 = stringValues[nameIndexAux];
                                            }
                                        } else {
                                            nameAux1 = stringValues[nameIndexAux];
                                        }
                                        nameAux1 = nameAux1.trim();
                                        Timber.w("Finder CCD name obverse IS: %s", nameAux1);
                                        colombianCCD.setNamesObverse(nameAux1);
                                    } catch (Exception e) {
                                        Timber.w("Exception IS: %s", e.getMessage());
                                        colombianCCD.setNamesObverse(nameAux);
                                    }
                                } else {
                                    Timber.w("Finder CCD name obverse IS: %s", nameAux);
                                    colombianCCD.setNamesObverse(nameAux);
                                }
                            } else {
                                //to back
                                for (int nameIndexAux2 = nameIndex - 2; nameIndexAux2 >= -1; nameIndexAux2--) {
                                    if (nameIndexAux2 < 0) {
                                        Timber.w("nameIndex IS: %s", nameIndexAux2);
                                        colombianCCD.setNamesObverse("");
                                        break;
                                    } else {
                                        //Improve search
                                        idNamePercentage = StringUtils.diceCoefficientOptimized(COLOMBIAN_LAST_NAMES, stringValues[nameIndexAux2].toUpperCase());
                                        //If the string contains "APELLIDO" or is empty or contains any number
                                        findPattern = ".*\\d.*";
                                        if (idNamePercentage < OCR_NAMES_AUX_PERCENTAGE && !stringValues[nameIndexAux2].isEmpty() && !stringValues[nameIndexAux2].matches(findPattern)) {
                                            //Improve Accuracy
                                            String[] words = stringValues[nameIndexAux2].split(" ");
                                            if (words.length == 1) {
                                                try {
                                                    double idNameNationalityPercentage = 0.0;
                                                    double idNameNationalityPercentage1 = 0.0;
                                                    double idNameHeightPercentage1 = 0.0;
                                                    double idNamePercentageAux = 0.0;
                                                    try {
                                                        idNameNationalityPercentage = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_COUNTRY_CITIZEN, stringValues[nameIndexAux2 + 1].toUpperCase());
                                                        idNameHeightPercentage = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_HEIGHT, stringValues[nameIndexAux2 + 1].toUpperCase());
                                                        idNameNationalityPercentage1 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_COUNTRY_CITIZEN, stringValues[nameIndexAux2 + 2].toUpperCase());
                                                        idNameHeightPercentage1 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_HEIGHT, stringValues[nameIndexAux2 + 2].toUpperCase());
                                                        idNamePercentageAux = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_NAMES, stringValues[nameIndexAux2 + 1].toUpperCase());
                                                    } catch (Exception e) {
                                                        idNameNationalityPercentage = 0.0;
                                                        idNameHeightPercentage = 0.0;
                                                        idNameNationalityPercentage1 = 0.0;
                                                        idNameHeightPercentage1 = 0.0;
                                                        idNamePercentageAux = 0.0;
                                                    }
                                                    //Search possible second Name
                                                    String nameAux1 = stringValues[nameIndexAux2].toUpperCase().trim();
                                                    //Find word "SEXO" three position later
                                                    idSexPercentage1 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_SEX, nameAux1.toUpperCase());
                                                    avoidStringSex = !nameAux1.contains(Constants.COLOMBIAN_DIGITAL_SEX) || !nameAux1.contains(Constants.COLOMBIAN_DIGITAL_ANOTHER_SEX)
                                                            || !nameAux1.contains(Constants.COLOMBIAN_DIGITAL_ANOTHER_SEX1) || idSexPercentage1 < OCR_NAMES_AUX_ANOTHER_PERCENTAGE;
                                                    boolean isPossibleSecondName = (idNameNationalityPercentage < OCR_NAMES_AUX_PERCENTAGE) || (idNameHeightPercentage < OCR_NAMES_AUX_PERCENTAGE) ||
                                                            (idNameNationalityPercentage1 < OCR_NAMES_AUX_PERCENTAGE) || (idNameHeightPercentage1 < OCR_NAMES_AUX_PERCENTAGE)
                                                            || !avoidStringSex && (idNamePercentageAux < OCR_NAMES_AUX_PERCENTAGE);
                                                    //clear the flag data
                                                    nameAux1 = "";
                                                    if (isPossibleSecondName) {
                                                        if ((stringValues[nameIndexAux2 + 1].length() > 1) && idNamePercentageAux < OCR_NAMES_AUX_PERCENTAGE) {
                                                            nameAux1 = stringValues[nameIndexAux2] + " " + stringValues[nameIndexAux2 + 1];
                                                        } else {
                                                            nameAux1 = stringValues[nameIndexAux2];
                                                        }
                                                    } else {
                                                        nameAux1 = stringValues[nameIndexAux2];
                                                    }
                                                    nameAux1 = nameAux1.trim();
                                                    Timber.w("Finder CCD name obverse IS: %s", nameAux1);
                                                    colombianCCD.setNamesObverse(nameAux1);
                                                    break;
                                                } catch (Exception e) {
                                                    Timber.w("Finder CCD name obverse IS: %s", nameIndexAux2);
                                                    colombianCCD.setNamesObverse(stringValues[nameIndexAux2]);
                                                    break;
                                                }
                                            } else {
                                                Timber.w("Finder CCD name obverse IS: %s", nameIndexAux2);
                                                colombianCCD.setNamesObverse(stringValues[nameIndexAux2]);
                                                break;
                                            }
                                        }
                                    }
                                }//end for
                            }
                        } catch (Exception e) {
                            Timber.w("Exception IS: %s", e.getMessage());
                            colombianCCD.setNamesObverse("");
                        }//end catch Exception
                    } //End CCD document
                } //End setNames
            }
        }
    } //End findCCDNames

    private void findCCDrH(String obverseText, int documentType) {
        String[] stringValues;
        if (obverseText == null || obverseText.isEmpty()) {
            return;
        }
        if (documentType != 0) {
            documentType = StringUtils.documentData(obverseText, 1);
        }
        //Avoid false positive
        obverseText = obverseText.replace("5", "S");
        obverseText = obverseText.replace("3", "S");
        obverseText = obverseText.replace("9", "G");
        stringValues = obverseText.split(StringUtils.BACK_LINE_REVERSO);
        if (stringValues.length == 1) {
            stringValues = obverseText.split(StringUtils.BACK_LINE_ANVERSO);
        }
        //Find the rh or equals
        if (documentType == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT) {
            double idPercentage;
            double idPercentage1;
            double idPercentage2;
            double idPercentage3;
            double idPercentage4;
            String rhObverse = "";
            for (int i = 0; i < stringValues.length; i++) {
                idPercentage = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_GS, stringValues[i].toUpperCase());
                idPercentage1 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_GS1, stringValues[i].toUpperCase());
                idPercentage2 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_GS2, stringValues[i].toUpperCase());
                idPercentage3 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_GS3, stringValues[i].toUpperCase());
                idPercentage4 = StringUtils.diceCoefficientOptimized(Constants.COLOMBIAN_DIGITAL_GS4, stringValues[i].toUpperCase());
                boolean isGSPercentage = (idPercentage > 0.9 || idPercentage1 > 0.9 || idPercentage2 > 0.9 || idPercentage3 > 0.9 || idPercentage4 > 0.9);
                if (isGSPercentage || isGStrue(stringValues[i].toUpperCase())) {
                    //the next values is possible than it contains the rH (next 7 values)
                    for (int j = 1; j < 7; j++) {
                        try {
                            rhObverse = StringUtils.fixCCDRH(stringValues[i + j].toUpperCase().replace("0", "O"), context);
                        } catch (Exception e) {
                            rhObverse = "";
                        }
                        //rH was founded
                        if (!rhObverse.isEmpty()) {
                            break;
                        }
                    }
                }
                //rH was founded
                if (!rhObverse.isEmpty()) {
                    break;
                }
            }
            colombianCCD.setRH(rhObverse);
        }
    }

    private boolean isGStrue(String currentString) {
        return currentString.contains(Constants.COLOMBIAN_DIGITAL_GS) || currentString.contains(Constants.COLOMBIAN_DIGITAL_GS1) || currentString.contains(Constants.COLOMBIAN_DIGITAL_GS2) || currentString.contains(Constants.COLOMBIAN_DIGITAL_GS3) || currentString.contains(Constants.COLOMBIAN_DIGITAL_GS4);
    }


    //Find the value APELLIDOS Y NOMBRES on Ecuadorian Document
    private boolean findEcuadorianNames(String obverseText) {
        String[] stringValues;
        if (obverseText == null || obverseText.isEmpty()) {
            return false;
        }
        stringValues = obverseText.split(StringUtils.BACK_LINE_REVERSO);
        if (stringValues.length == 1) {
            stringValues = obverseText.split(StringUtils.BACK_LINE_ANVERSO);
        }
        double maxIdPercentage = 0.0;
        double idPercentage;
        int nameIndex = 0;

        //Find the Name and last name int the value APELLIDOS Y NOMBRES
        for (int i = 0; i < stringValues.length; i++) {
            idPercentage = StringUtils.diceCoefficientOptimized(Constants.ECUADORIAN_NAMES, stringValues[i]);
            if (idPercentage > maxIdPercentage) {
                maxIdPercentage = idPercentage;
                nameIndex = i + 1;
            }
        }
        if (nameIndex >= stringValues.length - 1) {
            ecuadorianCitizen.setApellidos("");
            ecuadorianCitizen.setNombres("");
            return false;
        } else {
            if (maxIdPercentage < 0.70) {
                ecuadorianCitizen.setApellidos("");
                ecuadorianCitizen.setNombres("");
                return false;
            } else {
                ecuadorianCitizen.setApellidos(stringValues[nameIndex]);
                ecuadorianCitizen.setNombres(stringValues[nameIndex + 1]);
                if (avoidBornPlaceName(ecuadorianCitizen.getApellidos(), ecuadorianCitizen.getNombres())) {
                    return true;
                } else {
                    ecuadorianCitizen.setApellidos("");
                    ecuadorianCitizen.setNombres("");
                    return false;
                }
            }
        }

    }

    private boolean avoidBornPlaceName(String lastName, String names) {
        double idPercentageName;
        double idPercentageLastName;
        idPercentageName = StringUtils.diceCoefficientOptimized(Constants.ECUADORIAN_BORN_PLACE, names);
        idPercentageLastName = StringUtils.diceCoefficientOptimized(Constants.ECUADORIAN_BORN_PLACE, lastName);
        return (idPercentageName <= 0.70) && (idPercentageLastName <= 0.70);
    }

    private boolean findEcuadorianNumber(String obverseText) {
        String[] repeatedWords = context.getResources().getStringArray(com.reconosersdk.reconosersdk.R.array.ecuadorian_repeatedly_obverse_words);
        boolean repeatKeyword = StringUtils.repeatedlyText(obverseText, Constants.ANVERSO, repeatedWords);
        if (!repeatKeyword) {
            int docNumber;
            docNumber = StringUtils.getDocumentNumber(obverseText, documentResult);
            if (docNumber <= 0) {
                //createDataError();
                ecuadorianCitizen.setDocumentId("");
                return false;
            } else {
                if (String.valueOf(docNumber).length() <= Constants.MINIMUM_LENGTH_ECUADORIAN_DOCUMENT) {
                    ecuadorianCitizen.setDocumentId("0" + docNumber);
                } else {
                    ecuadorianCitizen.setDocumentId(String.valueOf(docNumber));
                }
                return true;
            }
        } else {
            ecuadorianCitizen.setDocumentId("");
            return false;
        }
    }

    private boolean findEcuadorianBornDate(String obverseText) {
        String[] bornDate;
        if (obverseText == null || obverseText.isEmpty()) {
            return false;
        }
        bornDate = StringUtils.getBornAgeCaseTwo(obverseText, context);
        if (bornDate == null || bornDate[0] == null || bornDate[0].isEmpty()) {
            ecuadorianCitizen.setFechaNacimiento("");
            return false;
        } else {
            ecuadorianCitizen.setFechaNacimiento(bornDate[0]);
            return true;
        }
    }

    private boolean findEcuadorianGender(String text) {
        String[] stringValues;
        if (text == null || text.isEmpty()) {
            return false;
        }
        stringValues = text.split(StringUtils.BACK_LINE_REVERSO);
        if (stringValues.length == 1) {
            stringValues = text.split(StringUtils.BACK_LINE_ANVERSO);
        }
        int nameIndex = 0;

        //Find the value SEXO
        for (int i = 0; i < stringValues.length; i++) {
            if (stringValues[i].contains(Constants.ECUADORIAN_GENDER)) {
                nameIndex = i;
                break;
            }
        }
        //Find the female gender
        if (stringValues[nameIndex].contains("MUJER") || stringValues[nameIndex].contains("F")) {
            ecuadorianCitizen.setSexo("MUJER");
            return true;
        } else if (stringValues[nameIndex].contains("HOMBRE") || stringValues[nameIndex].contains("M")) {
            //Find the male gender
            ecuadorianCitizen.setSexo("HOMBRE");
            return true;
        } else {
            ecuadorianCitizen.setSexo("");
            return false;
        }
    }


    @SuppressLint("TimberArgCount")
    @Override
    public void onDataRespondReverso(@NotNull String text, @NotNull String barcode, @NotNull String typeBarcode) {
        int documentResult = StringUtils.documentData(text, 2);
        String[] repeatedWords;
        boolean repeatKeyword;

        path = compressImageDocument();
        //if (path == null) getMvpView().onFinishError(Constants.ERROR_R106);

        //If the reverse document is COLOMBIAN or ECUADORIAN
        if (anyReverseDocument(documentResult)) {
            documentoReverso.setDocumentoReverso(text, barcode);

            String reverseText = documentoReverso.getStringReverso();
            if (colombianOCR.getDocumentType() != 0) {
                if (!documentTypeValidation(documentResult)) {
                    return;
                }
            }
            //Switch-case
            switch (documentResult) {
                //If the document is Colombian (Yellow)
                case Constants.COLOMBIAN_REVERSE_DOCUMENT:
                    repeatedWords = context.getResources().getStringArray(R.array.colombian_repeatedly_cc_reverse_words);
                    repeatKeyword = StringUtils.repeatedlyText(reverseText, Constants.REVERSO, repeatedWords);
                    if (!repeatKeyword) {
                        if (colombianOCR.getOcrState() == DOC_STATE_FINISH) {
                            cleanColombianData();
                        }
                        if (foreignOCR.getOcrState() == DOC_STATE_FINISH) {
                            cleanForeignData();
                        }
                        colombianOCR.setDocumentType(Constants.COLOMBIAN_REVERSE_DOCUMENT);
                        if (barcode == null || barcode.isEmpty()) {
                            getMvpView().onNavigationBarCode(reverseText, documentResult, path);
                        } else {
                            colombianReverseDocument(reverseText, barcode, documentResult, typeBarcode);
                        }
                    } else {
                        cleanColombianData();
                        Timber.w("R106 COLOMBIAN_REVERSE_DOCUMENT !repeatKeyword %s", repeatKeyword);
                        getMvpView().onFinishError(Constants.ERROR_R106);
                    }
                    break;
                //If the document is Ecuadorian
                case Constants.ECUADORIAN_REVERSE_DOCUMENT:
                    repeatedWords = context.getResources().getStringArray(R.array.ecuadorian_repeatedly_reverse_words);
                    repeatKeyword = StringUtils.repeatedlyText(reverseText, Constants.REVERSO, repeatedWords);
                    if (!repeatKeyword) {
                        ecuadorianReverseDocument(reverseText, documentResult);
                        colombianOCR.setDocumentType(Constants.ECUADORIAN_REVERSE_DOCUMENT);
                    } else {
                        Timber.w("R106 ECUATORIAN_REVERSE_DOCUMENT !repeatKeyword %s", repeatKeyword);
                        getMvpView().onFinishError(Constants.ERROR_R106);
                    }
                    break;

                case Constants.FOREIGNER_REVERSE_DOCUMENT:

                    if (foreignOCR.getOcrState() == DOC_STATE_FINISH) {
                        cleanForeignData();
                    }
                    if (barcode.equals("") || barcode.isEmpty()) {
                        getMvpView().onNavigationBarCode(reverseText, documentResult, path);
                    } else {
                        parseForeingCard(barcode, reverseText, typeBarcode);
                    }
                    changeForeignState(Constants.REVERSO);

                    Log.w("foreignBarcode", String.valueOf(foreignBarcode.getNumber()));
                    Log.w("foreignOCR", String.valueOf(foreignOCR.getNumber()));


                    resultsData.add(new DataReadDocument("Foreign OCR", JsonUtils.stringObject(foreignOCR)));
                    resultsData.add(new DataReadDocument("Foreign BARCODE", JsonUtils.stringObject(foreignBarcode)));
                    dataResult.setResult(resultsData);
                    Document document = new Document(null, documentoReverso, path, String.valueOf(documentResult), foreignOCR.getOcrState().toString(), dataResult, null);
                    isSavePhoto(document, path);
                    break;
                case Constants.COLOMBIAN_TI_REVERSE_DOCUMENT:
                    if (colombianOCR.getOcrState() == DOC_STATE_FINISH) {
                        cleanColombianData();
                    }
                    if (foreignOCR.getOcrState() == DOC_STATE_FINISH) {
                        cleanForeignData();
                    }
                    colombianOCR.setDocumentType(Constants.COLOMBIAN_TI_REVERSE_DOCUMENT);
                    fillMinorsObject(documentResult);
                    break;
                case Constants.MEXICAN_REVERSE_DOCUMENT:
                    Timber.e("LLEGA HASTA ACA REVERSO");
                    Timber.w("documentResult %s", String.valueOf(documentResult));
                    break;
                //If the document is Colombian (Yellow)
                case Constants.COLOMBIAN_DIGITAL_REVERSE_DOCUMENT:
                    repeatedWords = context.getResources().getStringArray(R.array.colombian_repeatedly_ccd_reverse_words);
                    repeatKeyword = StringUtils.repeatedlyText(reverseText, Constants.REVERSO, repeatedWords);
                    if (!repeatKeyword) {
                        if (colombianCCD.getOcrState() == DOC_STATE_FINISH) {
                            cleanColombianData();
                        }
                        colombianCCD.setDocumentType(Constants.COLOMBIAN_DIGITAL_REVERSE_DOCUMENT);
                        colombianDigitalReverseDocument(reverseText, documentResult);
                    } else {
                        cleanColombianData();
                        Timber.w("R106 COLOMBIAN_DIGITAL_REVERSE_DOCUMENT !repeatKeyword %s", repeatKeyword);
                        getMvpView().onFinishError(Constants.ERROR_R106);
                    }
                    break;
                default:
                    cleanColombianData();
                    cleanEcuadorianData();
                    cleanForeignData();
                    Timber.w("R106 REVERSE_DOCUMENT ");
                    getMvpView().onFinishError(Constants.ERROR_R106);
                    break;
            }
        } else {
            cleanEcuadorianData();
            cleanColombianData();
            cleanForeignData();
            Timber.w("R106 NOT Document");
            //getMvpView().onFinishError(Constants.ERROR_R106);
            getMvpView().onFinishError(Constants.ERROR_R106, Constants.ERROR_IMAGE_PROCESS, path);
        }
    }

    private boolean anyReverseDocument(int documentType) {
        return documentType == Constants.COLOMBIAN_REVERSE_DOCUMENT || documentType == Constants.COLOMBIAN_TI_REVERSE_DOCUMENT || documentType == Constants.ECUADORIAN_REVERSE_DOCUMENT || documentType == Constants.MEXICAN_REVERSE_DOCUMENT || documentType == Constants.FOREIGNER_REVERSE_DOCUMENT || documentType == Constants.COLOMBIAN_DIGITAL_REVERSE_DOCUMENT;
    }


    @Override
    public void parseForeingCard(String barcode, String reverseText, String typeBarcode) {
        colombianOCR.setDocumentType(Constants.FOREIGNER_REVERSE_DOCUMENT);
        documentoReverso.setDocumentoReverso("", barcode);
        parseForeignBarcode(barcode);
        changeForeignState(Constants.REVERSO);
        foreignBarcode.setTypeDocument(typeBarcode);

        Timber.w("foreignBarcode %s", String.valueOf(foreignBarcode.getNumber()));
        Timber.w("foreignOCR %s", String.valueOf(foreignOCR.getNumber()));

        resultsData.add(new DataReadDocument("Foreign OCR", JsonUtils.stringObject(foreignOCR)));
        resultsData.add(new DataReadDocument("Foreign BARCODE", JsonUtils.stringObject(foreignBarcode)));
        dataResult.setResult(resultsData);

        Document document = new Document(null, documentoReverso, path, "8", foreignOCR.getOcrState().toString(), dataResult, null);
        isSavePhoto(document, path);
    }

    private boolean documentTypeValidation(int documentResult) {
        boolean validate = true;
        switch (documentResult) {
            case Constants.COLOMBIAN_OBVERSE_DOCUMENT:
                if (colombianOCR.getDocumentType() != Constants.COLOMBIAN_REVERSE_DOCUMENT && colombianOCR.getDocumentType() != Constants.COLOMBIAN_TI_OBVERSE_DOCUMENT) {
                    goToError();
                    validate = false;
                }
                break;
            case Constants.COLOMBIAN_REVERSE_DOCUMENT:
                if (colombianOCR.getDocumentType() != Constants.COLOMBIAN_OBVERSE_DOCUMENT && colombianOCR.getDocumentType() != Constants.COLOMBIAN_REVERSE_DOCUMENT && colombianOCR.getDocumentType() != Constants.COLOMBIAN_DIGITAL_REVERSE_DOCUMENT) {
                    goToError();
                    validate = false;
                }
                break;
            case Constants.COLOMBIAN_TI_OBVERSE_DOCUMENT:
                if (colombianOCR.getDocumentType() != Constants.COLOMBIAN_TI_REVERSE_DOCUMENT && colombianOCR.getDocumentType() != Constants.COLOMBIAN_TI_OBVERSE_DOCUMENT) {
                    goToError();
                    validate = false;
                }
                break;
            case Constants.COLOMBIAN_TI_REVERSE_DOCUMENT:
                if (colombianOCR.getDocumentType() != Constants.COLOMBIAN_TI_OBVERSE_DOCUMENT && colombianOCR.getDocumentType() != Constants.COLOMBIAN_TI_REVERSE_DOCUMENT) {
                    goToError();
                    validate = false;
                }
                break;
            case Constants.ECUADORIAN_OBVERSE_DOCUMENT:
                if (colombianOCR.getDocumentType() != Constants.ECUADORIAN_REVERSE_DOCUMENT && colombianOCR.getDocumentType() != Constants.ECUADORIAN_OBVERSE_DOCUMENT) {
                    goToError();
                    validate = false;
                }
                break;
            case Constants.ECUADORIAN_REVERSE_DOCUMENT:
                if (colombianOCR.getDocumentType() != Constants.ECUADORIAN_OBVERSE_DOCUMENT && colombianOCR.getDocumentType() != Constants.ECUADORIAN_REVERSE_DOCUMENT) {
                    goToError();
                    validate = false;
                }
                break;
            case Constants.FOREIGNER_OBVERSE_DOCUMENT:
                if (colombianOCR.getDocumentType() != Constants.FOREIGNER_REVERSE_DOCUMENT && colombianOCR.getDocumentType() != Constants.FOREIGNER_OBVERSE_DOCUMENT) {
                    goToError();
                    validate = false;
                }
                break;
            case Constants.FOREIGNER_REVERSE_DOCUMENT:
                if (colombianOCR.getDocumentType() != Constants.FOREIGNER_OBVERSE_DOCUMENT && colombianOCR.getDocumentType() != Constants.FOREIGNER_REVERSE_DOCUMENT) {
                    goToError();
                    validate = false;
                }
                break;
            case Constants.MEXICAN_OBVERSE_DOCUMENT:
                if (colombianOCR.getDocumentType() != Constants.MEXICAN_REVERSE_DOCUMENT && colombianOCR.getDocumentType() != Constants.MEXICAN_OBVERSE_DOCUMENT) {
                    goToError();
                    validate = false;
                }
                break;
            case Constants.MEXICAN_REVERSE_DOCUMENT:
                if (colombianOCR.getDocumentType() != Constants.MEXICAN_OBVERSE_DOCUMENT && colombianOCR.getDocumentType() != Constants.MEXICAN_REVERSE_DOCUMENT) {
                    goToError();
                    validate = false;
                }
                break;
            default:
                goToError();
                validate = false;
                break;
        }
        return validate;
    }

    private void goToError() {
        cleanEcuadorianData();
        cleanForeignData();
        cleanColombianData();
        getMvpView().onFinishError(Constants.ERROR_R114);
    }

    private void goToErrorBarcode() {
        cleanEcuadorianData();
        cleanForeignData();
        cleanColombianData();
        getMvpView().onFinishError(Constants.ERROR_R115);
    }


    private void parseForeignBarcode(String barcode) {
        String alphaAndDigits = barcode.replaceAll("[^\\p{Alpha}\\p{Digit}\\+\\_]+", " ");
        String[] splitStr = alphaAndDigits.split("\\s+");
        String fullName = "";
        boolean lastItem = false;
        int i = 0;
        for (String splited : splitStr) {
            if (lastItem) {
                foreignBarcode.setNacionalidad(splited);
                break;
            }
            if (splited.contains("+") || splited.contains("-")) {
                String birthDate = splited.substring(0, 8);
                String expeditionDate = splited.substring(9, 17);
                String expirationDate = splited.substring(17, 25);
                String rh = splited.substring(25);
                foreignBarcode.setNombres(fullName);
                foreignBarcode.setFechaNacimiento(birthDate);
                foreignBarcode.setFechaExpedicion(expeditionDate);
                foreignBarcode.setFechaVencimiento(expirationDate);
                foreignBarcode.setRh(rh);

                if (splited.contains("M")) {
                    foreignBarcode.setSexo("M");
                } else {
                    foreignBarcode.setSexo("F");
                }
                lastItem = true;
            }
            if (splited.contains("MIGRANTE")) {
                foreignBarcode.setTipo("MIGRANTE");
            }
            if (splited.contains("RESIDENTE")) {
                foreignBarcode.setTipo("RESIDENTE");
            }
            if (splited.contains("CE") && splited.length() > 18) {
                String cedula = splited.substring(2, 20);
                fullName = splited.substring(20);
                Integer document = Integer.parseInt(cedula);
                foreignBarcode.setNumber(document);

            }
            if (i > 1 && !(splited.contains("+") || splited.contains("-"))) {
                fullName = fullName + " " + splited;
            }
            i++;
        }
    }

    private void ecuadorianReverseDocument(String reverseText, int documentResult) {
        boolean ecuDates = findEcuadorianReverseDates(reverseText);
        //If the values is nulls or empty
        if (!ecuDates) {
            cleanEcuadorianData();
            cleanColombianData();
            getMvpView().onFinishError(Constants.ERROR_R106);
        } else {
            String result = "\n OCR \n" + reverseText + "\n " + ecuadorianCitizen.toString();
            Timber.w("->>>>>>%s", ecuadorianCitizen.toString());
            changeEcuadorianState(Constants.REVERSO);
            sendEcuadorianData(Constants.REVERSO, documentResult);
        }
    }

    private void changeEcuadorianState(String docPosition) {
        ecuadorianCitizen.setEcuadorianDocState(StringUtils.documentState(ecuadorianCitizen.getEcuadorianDocState(), docPosition));
        if (ecuadorianCitizen.getEcuadorianDocState() != null && ecuadorianCitizen.getEcuadorianDocState() == 3) {
            resultsData.add(new DataReadDocument("Validation ok", JsonUtils.stringObject(ecuadorianCitizen)));
            dataResult.setResult(resultsData);
            result = "\n Validation ok: " + ecuadorianCitizen.toString() + "\n";
        }
    }

    private boolean findEcuadorianReverseDates(String reverseText) {
        String[] ecuadorianDates;
        ecuadorianDates = StringUtils.getBornAgeCaseTwo(reverseText, context);
        int expeditionDate;
        int expirateDate;
        if (ecuadorianDates[0] == null || ecuadorianDates[0].isEmpty() || ecuadorianDates[1] == null || ecuadorianDates[1].isEmpty()) {
            ecuadorianCitizen.setFechaExpedecion("");
            ecuadorianCitizen.setFechaExpiracion("");
            return false;
        } else {
            expeditionDate = Integer.parseInt(ecuadorianDates[0].replace("-", ""));
            expirateDate = Integer.parseInt(ecuadorianDates[1].replace("-", ""));
            //If the expirationDate is major
            if (expirateDate > expeditionDate) {
                ecuadorianCitizen.setFechaExpedecion(ecuadorianDates[0]);
                ecuadorianCitizen.setFechaExpiracion(ecuadorianDates[1]);
                return true;
            } else {
                ecuadorianCitizen.setFechaExpedecion("");
                ecuadorianCitizen.setFechaExpiracion("");
                return false;
            }
        }
    }

    private void colombianObverseDocument(String obverseText, int documentResult) {
        findNumberInText(obverseText, documentResult);
    }

    private void findNumberInText(String obverseText, int documentResult) {
        String[] repeatedWords = context.getResources().getStringArray(com.reconosersdk.reconosersdk.R.array.colombian_repeatedly_cc_observe_words);
        boolean repeatKeyword = StringUtils.repeatedlyText(obverseText, Constants.ANVERSO, repeatedWords);
        //if (!repeatKeyword && documentResult != 0) {
        if (documentResult != 0) {
            //Set the id number
            int documentNumber = StringUtils.getDocumentNumber(obverseText, documentResult);
            Timber.w("DocumentNumber ID: %s", documentNumber);
            if (documentResult == Constants.COLOMBIAN_OBVERSE_DOCUMENT) {
                colombianOCR.setCedula(documentNumber);
            } else if (documentResult == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT) {
                colombianCCD.setCedulaObverse(documentNumber);
            }
            if (documentNumber == 0) {
                cleanEcuadorianData();
                cleanColombianData();
                Timber.w("R106 findNumberInText documentNumber %s", documentNumber);
                getMvpView().onFinishError(Constants.ERROR_R106);
            } else {
                if (documentResult == Constants.COLOMBIAN_OBVERSE_DOCUMENT) {
                    //Show the value in a TextView
                    String citizenGender = StringUtils.getGender(documentNumber);
                    colombianOCR.setGenderNumber(citizenGender);
                    changeColombianState(Constants.ANVERSO);
                } else if (documentResult == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT) {
                    Timber.e("CCD obverse is: %s", obverseText);
                    //Search the document dates
                    findBornDateCCD(obverseText, documentResult);
                    changeColombianCCDState(Constants.ANVERSO);
                }
                //Find Names and LastNames
                findCCDTotalName(obverseText, documentResult);
                //Find rh and LastNames
                findCCDrH(obverseText, documentResult);
                if (colombianOCR != null && colombianOCR.getOcrState() != DOC_STATE_INIT) {
                    if (colombianOCR.getOcrState() != DOC_STATE_FINISH) {
                        resultsData.add(new DataReadDocument("Colombian OCR", JsonUtils.stringObject(colombianOCR)));
                        resultsData.add(new DataReadDocument("Colombian BARCODE", JsonUtils.stringObject(colombianCitizenBarcode)));
                        dataResult.setResult(resultsData);
                        result = "\n# Colombian OCR: " + colombianOCR.toString()
                                + "\n# Colombian BARCODE: " + colombianCitizenBarcode.toString();
                    }
                    Document document = new Document(documentoAnverso, null, path, String.valueOf(documentResult), colombianOCR.getOcrState().toString(), dataResult, documentValidations);
                    saveLog(document, path);
                    if (colombianOCR.getOcrState() == DOC_STATE_FINISH) {
                        cleanColombianData();
                    }
                } else if (colombianCCD != null && colombianCCD.getOcrState() != DOC_STATE_INIT) {
                    if (colombianCCD.getOcrState() != DOC_STATE_FINISH) {
                        resultsData.add(new DataReadDocument(Constants.COLOMBIAN_DIGITAL_TYPE, JsonUtils.stringObject(colombianCCD)));
                        resultsData.add(new DataReadDocument("Colombian BARCODE", JsonUtils.stringObject(colombianCitizenBarcode)));
                        dataResult.setResult(resultsData);
                        result = "\n" + Constants.COLOMBIAN_DIGITAL_TYPE + colombianCCD.toString()
                                + "\n# Colombian BARCODE: " + colombianCitizenBarcode.toString();
                    }
                    Document document = new Document(documentoAnverso, null, path, String.valueOf(documentResult), colombianCCD.getOcrState().toString(), dataResult, documentValidations);
                    saveLog(document, path);
                    if (colombianCCD.getOcrState() == DOC_STATE_FINISH) {
                        cleanColombianData();
                    }
                }
            }
        } else {
            cleanEcuadorianData();
            cleanColombianData();
            Timber.w("R106 findNumberInText, repeat Word");
            getMvpView().onFinishError(Constants.ERROR_R106);
        }
    }

    private void findBornDateCCD(String obverseText, int documentResult) {
        String[] bornDate;
        bornDate = StringUtils.getBornAgeCaseOne(obverseText, context, documentResult);
        try {
            colombianCCD.setFechaNacimientoObverse(avoidNull(bornDate[0]));
        } catch (Exception exception) {
            colombianCCD.setFechaNacimientoObverse(Constants.COLOMBIAN_DIGITAL_NOT_DATE);
        }
        try {
            colombianCCD.setFechaExpedicion(avoidNull(bornDate[1]));
        } catch (Exception exception) {
            colombianCCD.setFechaExpedicion(Constants.COLOMBIAN_DIGITAL_NOT_DATE);
        }
        try {
            colombianCCD.setFechaExpiracionObverse(avoidNull(bornDate[2]));
        } catch (Exception exception) {
            colombianCCD.setFechaExpiracionObverse(Constants.COLOMBIAN_DIGITAL_NOT_DATE);
        }
    }

    private void changeColombianState(String docPosition) {
        colombianOCR.setOcrState(StringUtils.documentState(colombianOCR.getOcrState(), docPosition));
        if (colombianOCR.getOcrState() == DOC_STATE_FINISH) {
            barcodeVsOCR();
        }
    }

    private void cleanForeignData() {
        //Restart the document state
        foreignOCR.setForeignOCR(0, "", "", "", "", "", "", "", "", "", 0);
        foreignBarcode.setForeignBarcode(0, "", "", "", "", "", "", "", "", "");
        documentoAnverso = new DocumentoAnverso();
        documentoReverso = new DocumentoReverso();
        documentoAnverso = DocumentoAnverso.getInstance();
        documentoReverso = DocumentoReverso.CREATOR.getInstance();
        colombianOCR = new ColombianOCR();

    }

    private void changeForeignState(String docPosition) {
        foreignOCR.setOcrState(StringUtils.documentState(foreignOCR.getOcrState(), docPosition));
        if (foreignOCR.getOcrState() == DOC_STATE_FINISH) {
            if (!validateBarcodeOcrCE()) {
                goToError();
            }
        }
    }

    private void cleanColombianData() {
        //Restart the document state
        cleanCCdata();
        //colombianCCD = new ColombianCCD();
        cleanCCDdata();
        documentoAnverso = DocumentoAnverso.getInstance();
        documentoReverso = DocumentoReverso.CREATOR.getInstance();
        colombianCitizenBarcode = new ColombianCitizenBarcode("", "", "", "", "", "", "", "", "");
        colombianMinorCitizen.setColombianMinorCitizen("", "", "", "", "", "", "", "", "");
    }

    private void cleanCCdata() {
        colombianOCR.setCedula(0);
        colombianOCR.setFechaNacimiento("");
        colombianOCR.setFechaExpedicion("");
        colombianOCR.setFechaElaboracion("");
        colombianOCR.setSexo("");
        colombianOCR.setGenderString("");
        colombianOCR.setGenderNumber("");
        colombianOCR.setOcrState(0);
        colombianOCR.setDocumentType(0);
        colombianOCR.setNames("");
        colombianOCR.setLastNames("");
    }

    private void cleanCCDdata() {
        colombianCCD.setCedulaObverse(0);
        colombianCCD.setCedulaReverse("0");
        colombianCCD.setFechaNacimientoObverse("");
        colombianCCD.setFechaNacimientoReverse("");
        colombianCCD.setFechaExpedicion("");
        colombianCCD.setFechaExpiracionObverse("");
        colombianCCD.setFechaExpiracionReverse("");
        colombianCCD.setSexo("");
        colombianCCD.setGenderString("");
        colombianCCD.setGenderNumber("");
        colombianCCD.setOcrState(0);
        colombianCCD.setDocumentType(0);
        colombianCCD.setNamesObverse("");
        colombianCCD.setNamesReverse("");
        colombianCCD.setLastNamesObverse("");
        colombianCCD.setLastNamesReverse("");
        colombianCCD.setRH("");
    }


    @SuppressLint("TimberArgCount")
    private void barcodeVsOCR() {

        boolean validateName = false;
        boolean validateDocument;
        String obverseText = "";
        if (documentoAnverso != null) {
            obverseText = documentoAnverso.getstringAnverso();
        }
        String reverseText = "";
        if (documentoReverso != null) {
            reverseText = documentoReverso.getStringReverso();
        }
        //If every element was searched
        //validate name and last name
        validateName = StringUtils.onGetTotalName(obverseText, colombianCitizenBarcode);
        Timber.w("VALIDACION NOMBRE %s", String.valueOf(validateName));
        //validate number
        Timber.w("Document OCR: %s", colombianOCR.getCedula() + " barcode: " + colombianCitizenBarcode.getCedula());
        double total = StringUtils.diceCoefficientOptimized(colombianCitizenBarcode.getCedula(), String.valueOf(colombianOCR.getCedula()));
        validateDocument = total > Constants.DOUBLE_WORD_TOLERANCE;
        if (validateDocument) {
            //validate dates
            boolean validateDates;
            validateDates = notEmptyDates(colombianOCR.getFechaNacimiento(), colombianOCR.getFechaExpedicion());
            String[] bornDate = new String[2];
            bornDate[0] = colombianOCR.getFechaNacimiento();
            bornDate[1] = colombianOCR.getFechaExpedicion();
            validateDates = StringUtils.eigthteenYearsOld(bornDate, colombianCitizenBarcode.getFechaNacimiento());
            //validate gender
            boolean validateGender;
            validateGender = notEmptyDates(colombianOCR.getFechaNacimiento(), colombianOCR.getFechaExpedicion());
            String personGender = "";
            if (colombianCitizenBarcode.getSexo() != null && !colombianCitizenBarcode.getSexo().isEmpty()) {
                personGender = String.valueOf(colombianCitizenBarcode.getSexo().charAt(0));
            }
            String[] genderUser = new String[2];
            genderUser[0] = colombianOCR.getSexo();
            genderUser[1] = colombianOCR.getGenderString();
            //If the document is old
            if (colombianOCR.getCedula() <= Constants.SECOND_MALE) {
                validateGender = StringUtils.validateOldGenderStrings(genderUser, personGender, colombianOCR.getGenderNumber());
            } else {
                validateGender = StringUtils.validateGenderStrings(genderUser, personGender);
            }

            //if (!validateDocument || !validateName) {
            //Caution, only validate if the obverse and reverse are the same document
            /*if (!validateDocument) {
                Timber.w("VALIDACION DOCUMENT %s : ", String.valueOf(validateDocument) + " VALIDACION NOMBRE %s", String.valueOf(validateName));
                goToError();
            }*/

            boolean[] arrayValidation = new boolean[Constants.ARRAY_BOOLEAN];
            //fill with false boolean
            List<Validations> validations = new ArrayList<Validations>();
            validations.add(new Validations("document", validateDocument));
            validations.add(new Validations("gender", validateGender));
            validations.add(new Validations("name", validateName));
            validations.add(new Validations("esMayor18", validateDates));
            documentValidations.setValidation(validations);
            int counterTrue = 0;
            for (int i = 0; i < validations.size(); i++) {
                if (validations.get(i).getResult()) {
                    counterTrue++;
                }
            }
            documentValidations.setValidation_tested(counterTrue + "/" + Constants.ARRAY_BOOLEAN);
            double totalValidations = counterTrue;
            totalValidations = (totalValidations / Constants.ARRAY_BOOLEAN) * TOTAL_PERCENT;
            documentValidations.setTotal(StringUtils.formatePercetage(totalValidations) + "%");

            String resultValidations = JsonUtils.stringObject(documentValidations);
            resultsData.add(new DataReadDocument("Validation ok", String.valueOf(counterTrue)));
            resultsData.add(new DataReadDocument("Validation", resultValidations));
            resultsData.add(new DataReadDocument("Colombian OCR", JsonUtils.stringObject(colombianOCR)));
            resultsData.add(new DataReadDocument("Colombian BARCODE", JsonUtils.stringObject(colombianCitizenBarcode)));
            dataResult.setResult(resultsData);

            result = "\n Validation ok: " + counterTrue +
                    "\n Colombian OCR: " + colombianOCR.toString()
                    + "\n Colombian BARCODE: " + colombianCitizenBarcode.toString()
                    + "\n" + resultValidations;
            Timber.w("Validatiion JSON %s", result);
        } else {
            cleanColombianData();
            getMvpView().onFinishError(Constants.ERROR_R114);
        }
    }

    private boolean notEmptyDates(String s0, String s1) {
        //If the values is empty or null
        return s0 != null && s1 != null && !s0.isEmpty() && !s1.isEmpty();
    }

    @Override
    public void colombianReverseDocument(String reverseText, @Nullable String barcode, int documentResult, String typeBarcode) {
        if (barcode == null || barcode.isEmpty()) {
            goToBarcodeNull();
        } else {
            //To get the PDF417 barcode
            cleanBarcode();
            ColombianCitizenBarcode colombianCitizenBarcodeAux = StringUtils.parseDataCode(barcode, context);
            setColombianBarcode(colombianCitizenBarcodeAux);
            colombianCitizenBarcode.setTypeDocument(typeBarcode);
            String resultBarcode = "Barcode \n" + colombianCitizenBarcode.toString() + "\n OCR \n" + reverseText;
            //Search the document dates
            findBornDate(reverseText, documentResult);
            //Search the citizen's gender
            findGender(reverseText);

            changeColombianState(Constants.REVERSO);
            if (colombianOCR != null && colombianOCR.getOcrState() != DOC_STATE_INIT) {
                if (colombianOCR.getOcrState() != DOC_STATE_FINISH) {
                    resultsData.add(new DataReadDocument("Colombian OCR", JsonUtils.stringObject(colombianOCR)));
                    resultsData.add(new DataReadDocument("Colombian BARCODE", JsonUtils.stringObject(colombianCitizenBarcode)));
                    dataResult.setResult(resultsData);
                    result = "\n# Colombian OCR: " + colombianOCR.toString()
                            + "\n# Colombian BARCODE: " + colombianCitizenBarcode.toString();
                }
                Document document = new Document(null, documentoReverso, path, String.valueOf(documentResult),
                        colombianOCR.getOcrState().toString(), dataResult, documentValidations);
                saveLog(document, path);
                /*if (colombianOCR.getOcrState() == DOC_STATE_FINISH) {
                    cleanColombianData();
                }*/
            }
        }
    }

    public void colombianDigitalReverseDocument(String reverseText, int documentResult) {
        String[] stringValues;
        if (reverseText == null || reverseText.isEmpty()) {
            return;
        }
        stringValues = reverseText.split(StringUtils.BACK_LINE_REVERSO);
        if (stringValues.length == 1) {
            stringValues = reverseText.split(StringUtils.BACK_LINE_ANVERSO);
        }
        //Search the names
        findDigitalReverseId(stringValues);
        //Search the names
        findDigitalReverseNames(stringValues);
        //Search the citizen's date of born
        findDateBorn(stringValues);
        //Search the citizen's gender
        findCCDGender(stringValues);
        //Search the citizen's date of document expiration
        findDateExpiration(stringValues);
        //findGender(reverseText);
        changeColombianCCDState(Constants.REVERSO);
        if (colombianCCD != null && colombianCCD.getOcrState() != null && colombianCCD.getOcrState() != DOC_STATE_INIT) {
            if (colombianCCD.getOcrState() != DOC_STATE_FINISH) {
                resultsData.add(new DataReadDocument(Constants.COLOMBIAN_DIGITAL_TYPE, JsonUtils.stringObject(colombianCCD)));
                resultsData.add(new DataReadDocument("Colombian BARCODE", JsonUtils.stringObject(colombianCitizenBarcode)));
                dataResult.setResult(resultsData);
                result = "\n" + Constants.COLOMBIAN_DIGITAL_TYPE + colombianCCD.toString()
                        + "\n# Colombian BARCODE: " + colombianCitizenBarcode.toString();
            }
            Document document = new Document(null, documentoReverso, path, String.valueOf(documentResult),
                    Objects.requireNonNull(colombianCCD.getOcrState()).toString(), dataResult, documentValidations);
            saveLog(document, path);
            if (colombianCCD.getOcrState() == DOC_STATE_FINISH) {
                cleanColombianData();
            }
        }
    }

    //Find into MRZ
    private void findDigitalReverseId(String[] stringValues) {
        String auxFinder = "";
        for (int i = 0; i < stringValues.length; i++) {
            auxFinder = stringValues[i].toUpperCase();
            //Avoid false positives
            auxFinder = auxFinder.replace("0", "O");
            auxFinder = auxFinder.replace(" ", "");
            boolean possibleException = false;
            //Contains COL word, MRZ symbol (<) and any number
            //if ( (auxFinder.contains(Constants.COLOMBIAN_DIGITAL_ICCOL) || auxFinder.contains(Constants.COLOMBIAN_DIGITAL_ICC0L) ) && auxFinder.contains("<") && auxFinder.matches(".*\\d.*")) {
            if (auxFinder.contains(Constants.COLOMBIAN_DIGITAL_ICCOL) && auxFinder.contains("<") && auxFinder.matches(".*\\d.*")) {
                //Avoid nulls or false positives
                int findNumberPosition = 0;
                String findPattern = ".*\\d.*";
                findPattern = "<" + findPattern;
                Pattern p = Pattern.compile(findPattern);
                try {
                    for (int j = i + 1; j < stringValues.length; j++) {
                        Matcher m = p.matcher(stringValues[j].toUpperCase());
                        //Secure finder
                        if (m.find() || stringValues[j].toUpperCase().contains("<<")) {
                            findNumberPosition = j;
                            break;
                        }
                    }
                } catch (Exception e) {
                    //Default position
                    findNumberPosition = i + 1;
                    possibleException = true;
                }
                if (findNumberPosition < stringValues.length) {
                    //Avoid false positives
                    if (possibleException || !stringValues[findNumberPosition - 1].contains("<<<<")) {
                        //Avoid false positives
                        auxFinder = stringValues[findNumberPosition - 1].toUpperCase() + "<";
                        if (auxFinder.contains(Constants.COLOMBIAN_DIGITAL_ICCOL) || auxFinder.contains(Constants.COLOMBIAN_DIGITAL_ICC0L)) {
                            auxFinder = stringValues[findNumberPosition].toUpperCase();
                        }
                    } else {
                        auxFinder = stringValues[findNumberPosition].toUpperCase();
                    }
                    //Avoid possible array skips
                    if (!Character.isDigit(auxFinder.charAt(0)) || !Character.isDigit(auxFinder.charAt(1))) {
                        auxFinder = stringValues[findNumberPosition + 1].toUpperCase();
                        if (!Character.isDigit(auxFinder.charAt(0)) && !Character.isDigit(auxFinder.charAt(1))) {
                            auxFinder = stringValues[findNumberPosition - 1].toUpperCase();
                        }
                    } else {
                        auxFinder = stringValues[findNumberPosition].toUpperCase();
                    }
                    auxFinder = auxFinder.replace("O", "0");
                    auxFinder = auxFinder.replace("i", "1");
                    auxFinder = auxFinder.replace("I", "1");
                    auxFinder = auxFinder.replace("l", "1");
                    auxFinder = auxFinder.replace("L", "1");
                    auxFinder = auxFinder.replace("S", "5");
                    auxFinder = auxFinder.replace("A", "4");
                    //Upgrade accuracy
                    String colombianReverseId = "";
                    if (auxFinder.contains(Constants.COLOMBIAN_DIGITAL_FAKE_COL)) {
                        colombianReverseId = StringUtils.getSubString(auxFinder, Constants.COLOMBIAN_DIGITAL_FAKE_COL, "<"); //MRZ symbol <
                    } else if (auxFinder.contains(Constants.COLOMBIAN_DIGITAL_FAKE_COL1)) {
                        colombianReverseId = StringUtils.getSubString(auxFinder, Constants.COLOMBIAN_DIGITAL_FAKE_COL1, "<"); //MRZ symbol <
                    } else if (auxFinder.contains(Constants.COLOMBIAN_DIGITAL_FAKE_COL2)) {
                        colombianReverseId = StringUtils.getSubString(auxFinder, Constants.COLOMBIAN_DIGITAL_FAKE_COL2, "<"); //MRZ symbol <
                    }
                    //Replace spaces
                    colombianReverseId = colombianReverseId.replace(" ", "");
                    colombianCCD.setCedulaReverse(colombianReverseId);
                    break;
                }
            }
        }
    }

    //Find into MRZ
    private void findDigitalReverseNames(String[] stringValues) {
        String auxFinder = "";
        for (int i = 0; i < stringValues.length; i++) {
            auxFinder = stringValues[i].toUpperCase();
            //Avoid false positives
            auxFinder = auxFinder.replace("0", "O");
            auxFinder = auxFinder.replace(" ", "");
            //Contains COL word, MRZ symbol (<) and any number
            if (auxFinder.contains(Constants.COLOMBIAN_DIGITAL_ICCOL) && auxFinder.contains("<") && auxFinder.matches(".*\\d.*")) {
                //Avoid nulls or false positives
                int findNumberPosition = 0;

                try {
                    boolean findAgainPattern = false;
                    boolean isConcatStringNames = false;
                    for (int j = i + 2; j < stringValues.length; j++) {
                        //Only has a char "<"
                        if (stringValues[j].toUpperCase().contains("<") && !stringValues[j].toUpperCase().contains("<<") && !isConcatStringNames) {
                            isConcatStringNames = true;
                        }
                        if (stringValues[j].toUpperCase().contains("<<") && !stringValues[j + 1].toUpperCase().contains("<<")) {
                            if (findAgainPattern) {
                                findNumberPosition = j;
                                if (isConcatStringNames) {
                                    if (stringValues[j].toUpperCase().contains("<<")) {
                                        //Concat data
                                        stringValues[j] = stringValues[j - 1] + stringValues[j];
                                    } else {
                                        //Concat data
                                        stringValues[j] = stringValues[j - 2] + stringValues[j - 1];
                                    }
                                }
                                break;
                            } else {
                                if (isConcatStringNames) {
                                    //Concat data
                                    stringValues[j] = stringValues[j - 1] + stringValues[j];
                                    findNumberPosition = j;
                                    break;
                                } else {
                                    findAgainPattern = true;
                                    findNumberPosition = j;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    //Default position
                    findNumberPosition = i + 2;
                    if (!stringValues[findNumberPosition].toUpperCase().contains("<<")) {
                        //Concat data
                        stringValues[findNumberPosition] = stringValues[findNumberPosition - 1] + stringValues[findNumberPosition];
                    }
                    //Avoid possible "\n"
                    if (Character.isDigit(stringValues[findNumberPosition].charAt(0))) {
                        try {
                            //Concat data, avoid possible "\n" into ID and dates position array
                            findNumberPosition = findNumberPosition + 1;
                            auxFinder = stringValues[findNumberPosition].toUpperCase();
                            //Avoid possible error in get the names or lastNames
                            if(auxFinder.length()< MIN_VALUE_LAST_NAME_LENGTH || !auxFinder.toUpperCase().contains("<<")){
                                //Add the next value
                                stringValues[findNumberPosition]=stringValues[findNumberPosition]+stringValues[findNumberPosition+1];
                            }
                        } catch (Exception e1) {
                            findNumberPosition = findNumberPosition + 1;
                            Timber.e("Exception in reverse is: %s", e1.getMessage());
                        }
                    }
                }
                //Doesn't change it
                if (findNumberPosition == 0) {
                    findNumberPosition = i + 2;
                }
                if (findNumberPosition <= stringValues.length) {
                    //Avoid false positives
                    boolean isSameChar = false;
                    auxFinder = stringValues[findNumberPosition].toUpperCase();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if (auxFinder.chars().distinct().count() == 1 || auxFinder.length() == 1) {
                            isSameChar = true;
                            auxFinder = stringValues[findNumberPosition - 1].toUpperCase();
                        }
                    }
                    //Avoid possible array skips
                    if (Character.isDigit(auxFinder.charAt(0)) && Character.isDigit(auxFinder.charAt(1))) {
                        if (isSameChar) {
                            auxFinder = stringValues[findNumberPosition - 2].toUpperCase();
                            if (auxFinder.length() < MIN_VALUE_LAST_NAME_LENGTH) {
                                auxFinder = auxFinder + "K" + stringValues[findNumberPosition - 1].toUpperCase();
                            }
                        } else {
                            auxFinder = stringValues[findNumberPosition - 1].toUpperCase();
                            if (auxFinder.length() < MIN_VALUE_LAST_NAME_LENGTH) {
                                auxFinder = auxFinder + "K" + stringValues[findNumberPosition].toUpperCase();
                            }
                        }

                    }


                    auxFinder = auxFinder.replace("0", "O");
                    auxFinder = auxFinder.replace(" ", "");

                    //Fixed bug when the Pattern "<<" is missing
                    if (!auxFinder.contains("<<")) {
                        boolean isPattern = false;
                        char findPattern = '<';
                        for (int j = 0; j < auxFinder.length(); j++) {
                            //Find Pattern again
                            if (isPattern && auxFinder.charAt(j) == findPattern) {
                                //Add Value
                                auxFinder = auxFinder.substring(0, j) + findPattern + auxFinder.substring(j);
                                break;
                            }
                            if (auxFinder.charAt(j) == findPattern) {
                                isPattern = true;
                            }
                        }
                    }
                    auxFinder += "<";
                    auxFinder = auxFinder.replace("K<", "<<");
                    //Get possible false positive
                    int index = auxFinder.indexOf("<<"); //MRZ symbols (<<)
                    index += "<<".length();
                    String namesFixed = auxFinder.substring(index);
                    for (int j = 0; j < namesFixed.length() - 2; j++) {
                        String findPattern = "<K<";
                        String replacePattern = "<<<";
                        for (int k = 0; k <= j; k++) {
                            auxFinder = StringUtils.replacePattern(auxFinder, findPattern, replacePattern);
                            findPattern = findPattern.replace("K<", "KK<");
                        }
                    }
                    if (index <= 0) {
                        colombianCCD.setLastNamesReverse("");
                        colombianCCD.setNamesReverse("");
                    } else {
                        auxFinder = auxFinder.replace("K<", "<<");
                        String names = auxFinder.substring(0, index);
                        //Clean Data
                        names = names.replace("<", " ").trim();
                        String[] words = names.split(" ");
                        if (words.length != 2) {
                            //Find possible fake 'K'
                            names = names.replace("K", " ").trim();
                            words = names.split(" ");
                            if (words.length == 2) {
                                names = names.replace("K", " ").trim();
                            }
                        }
                        //Possible clean data
                        colombianCCD.setLastNamesReverse(names);
                        //Get Names
                        index = auxFinder.indexOf("<<"); //MRZ symbols (<<)
                        index += "<<".length();
                        names = auxFinder.substring(index);
                        //Clean Data
                        names = names.replace("K<", " ").trim();
                        names = names.replace("<", " ").trim();
                        names = names.replace(".", " ").trim();
                        colombianCCD.setNamesReverse(names);
                    }
                    break;
                }
            }
        }
    }

    //Find into MRZ
    private void findDateBorn(String[] stringValues) {
        String auxFinder = "";
        for (int i = 0; i < stringValues.length; i++) {
            auxFinder = stringValues[i].toUpperCase();
            //Avoid false positives
            auxFinder = auxFinder.replace("0", "O");
            auxFinder = auxFinder.replace(" ", "");
            //Contains COL word, MRZ symbol (<) and any number
            if (auxFinder.contains(Constants.COLOMBIAN_DIGITAL_ICCOL) && auxFinder.contains("<") && auxFinder.matches(".*\\d.*")) {
                //Avoid nulls or false positives
                int findNumberPosition = 0;
                String findPattern = ".*\\d.*";
                findPattern = "<" + findPattern;
                Pattern p = Pattern.compile(findPattern);
                try {
                    for (int j = i + 1; j < stringValues.length; j++) {
                        Matcher m = p.matcher(stringValues[j].toUpperCase());
                        //Secure finder
                        if (m.find() || stringValues[j].toUpperCase().contains("<")) {
                            findNumberPosition = j;
                            break;
                        }
                    }
                } catch (Exception e) {
                    //Default position
                    findNumberPosition = i + 1;
                }
                //Doesn't change it
                if (findNumberPosition == 0) {
                    findNumberPosition = i + 1;
                }
                if (findNumberPosition < stringValues.length) {
                    //Avoid false positives
                    //Initial text isn't a number
                    if (!Character.isDigit(stringValues[findNumberPosition].toUpperCase().charAt(0)) || !Character.isDigit(stringValues[findNumberPosition].toUpperCase().charAt(1))) {
                        auxFinder = stringValues[findNumberPosition - 1].toUpperCase();
                        if (!Character.isDigit(auxFinder.charAt(0)) || !Character.isDigit(auxFinder.charAt(1))) {
                            auxFinder = stringValues[findNumberPosition - 2].toUpperCase();
                        }
                    } else {
                        auxFinder = stringValues[findNumberPosition].toUpperCase();
                    }
                    auxFinder = auxFinder.replace("O", "0");
                    auxFinder = auxFinder.replace("I", "1");
                    auxFinder = auxFinder.replace(" ", "");
                    //auxFinder = auxFinder.replace("A", "4");
                    //Format data founded YY-MM-DD
                    String bornDate = "";
                    try {
                        bornDate = auxFinder.substring(0, FORMAT_DATE_LENGTH);
                    } catch (Exception e) {
                        bornDate = Constants.COLOMBIAN_DIGITAL_NOT_DATE;
                    }
                    //String bornDate = auxFinder.substring(0, FORMAT_DATE_LENGTH);
                    String bornDateFormat = StringUtils.getBornDate(bornDate, context, true);
                    colombianCCD.setFechaNacimientoReverse(bornDateFormat);
                    break;
                }
            }
        }
    }

    private void findCCDGender(String[] stringValues) {
        String auxFinder = "";
        for (int i = 0; i < stringValues.length; i++) {
            auxFinder = stringValues[i].toUpperCase();
            //Avoid false positives
            auxFinder = auxFinder.replace("0", "O");
            auxFinder = auxFinder.replace(" ", "");
            //Contains COL word, MRZ symbol (<) and any number
            if (auxFinder.contains(Constants.COLOMBIAN_DIGITAL_ICCOL) && auxFinder.contains("<") && auxFinder.matches(".*\\d.*")) {
                //Avoid nulls or false positives
                int findNumberPosition = 0;
                String findPattern = ".*\\d.*";
                findPattern = "<" + findPattern;
                Pattern p = Pattern.compile(findPattern);
                try {
                    for (int j = i + 1; j < stringValues.length; j++) {
                        Matcher m = p.matcher(stringValues[j].toUpperCase());
                        //Secure finder
                        if (m.find() || stringValues[j].toUpperCase().contains("<<")) {
                            findNumberPosition = j;
                            break;
                        }
                    }
                } catch (Exception e) {
                    //Default position
                    findNumberPosition = i + 1;
                }
                //Doesn't change it
                if (findNumberPosition == 0) {
                    findNumberPosition = i + 1;
                }
                if (findNumberPosition < stringValues.length) {
                    //Avoid false positives
                    auxFinder = stringValues[findNumberPosition].toUpperCase();
                    //Search gender between M, F or T
                    String formatGender = StringUtils.getGenderCCD(auxFinder, context);
                    colombianCCD.setGenderString(formatGender);
                    break;
                }
            }
        }
    }

    private void findDateExpiration(String[] stringValues) {
        String auxFinder = "";
        for (int i = 0; i < stringValues.length; i++) {
            auxFinder = stringValues[i].toUpperCase();
            //Avoid false positives
            auxFinder = auxFinder.replace("0", "O");
            auxFinder = auxFinder.replace(" ", "");
            //Contains COL word, MRZ symbol (<) and any number
            if (auxFinder.contains(Constants.COLOMBIAN_DIGITAL_ICCOL) && auxFinder.contains("<") && auxFinder.matches(".*\\d.*")) {
                //Avoid nulls or false positives
                int findNumberPosition = 0;
                String findPattern = ".*\\d.*";
                findPattern = "<" + findPattern;
                Pattern p = Pattern.compile(findPattern);
                try {
                    for (int j = i + 1; j < stringValues.length; j++) {
                        Matcher m = p.matcher(stringValues[j].toUpperCase());
                        //Secure finder
                        if (m.find() || stringValues[j].toUpperCase().contains("<<")) {
                            findNumberPosition = j;
                            break;
                        }
                    }
                } catch (Exception e) {
                    //Default position
                    findNumberPosition = i + 1;
                }
                //Doesn't change it
                if (findNumberPosition == 0) {
                    findNumberPosition = i + 1;
                }
                if (findNumberPosition < stringValues.length) {
                    //Avoid false positives
                    auxFinder = stringValues[findNumberPosition].toUpperCase();
                    //Search gender between M, F or T
                    String dateExpirationGender = StringUtils.getGenderCCD(auxFinder, context);
                    String dateExpiration = "";
                    if (dateExpirationGender.equals(Constants.COLOMBIAN_DIGITAL_GENDER_404)) {
                        dateExpiration = Constants.COLOMBIAN_DIGITAL_NOT_DATE;
                    } else {
                        //Avoid false positives
                        auxFinder = stringValues[findNumberPosition].toUpperCase();
                        auxFinder = auxFinder.replace("O", "0");
                        auxFinder = auxFinder.replace("I", "1");
                        auxFinder = auxFinder.replace(" ", "");
                        //auxFinder = auxFinder.replace("A", "4");
                        //Avoid
                        int expirationCharReference = auxFinder.indexOf(dateExpirationGender);
                        //Data not founded
                        if (expirationCharReference == -1) {
                            dateExpiration = Constants.COLOMBIAN_DIGITAL_NOT_DATE;
                        } else {
                            //Format data founded YY-MMM-DD
                            auxFinder = auxFinder.substring(expirationCharReference + 1, expirationCharReference + FORMAT_DATE_LENGTH + 1);
                            dateExpiration = StringUtils.getBornDate(auxFinder, context, false);
                        }
                    }
                    colombianCCD.setFechaExpiracionReverse(dateExpiration);
                    break;
                }
            }
        }
    }

    private void changeColombianCCDState(String docPosition) {
        colombianCCD.setOcrState(StringUtils.documentState(colombianOCR.getOcrState(), docPosition));
    }

    private void goToBarcodeNull() {
        Timber.w("R106 barcode empty ");
        Document document = new Document(null, documentoReverso, path, String.valueOf(documentResult),
                colombianOCR.getOcrState().toString(), dataResult, documentValidations);
        isSavePhoto(document, path);
    }

    private void setColombianBarcode(ColombianCitizenBarcode colombianBarcode) {
        if (colombianBarcode == null) {
            //Avoid null values
            colombianCitizenBarcode = new ColombianCitizenBarcode();
            colombianCitizenBarcode.setPrimerApellido("");
            colombianCitizenBarcode.setSegundoApellido("");
            colombianCitizenBarcode.setPrimerNombre("");
            colombianCitizenBarcode.setSegundoNombre("");
            colombianCitizenBarcode.setCedula("");
            colombianCitizenBarcode.setRh("");
            colombianCitizenBarcode.setFechaNacimiento("");
            colombianCitizenBarcode.setSexo("");
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

    private void cleanBarcode() {
        //Clean first data
        colombianCitizenBarcode.setPrimerApellido("");
        colombianCitizenBarcode.setSegundoApellido("");
        colombianCitizenBarcode.setPrimerNombre("");
        colombianCitizenBarcode.setSegundoNombre("");
        colombianCitizenBarcode.setCedula("");
        colombianCitizenBarcode.setRh("");
        colombianCitizenBarcode.setFechaNacimiento("");
        colombianCitizenBarcode.setSexo("");
    }

    public static String avoidNull(String nullString) {
        if (nullString == null) {
            nullString = "";
        }
        return nullString;
    }


    private void findBornDate(String reverseText, int documentResult) {
        String[] repeatedWords = context.getResources().getStringArray(com.reconosersdk.reconosersdk.R.array.colombian_repeatedly_cc_reverse_words);
        boolean repeatKeyword = StringUtils.repeatedlyText(reverseText, Constants.REVERSO, repeatedWords);
        String[] bornDate;
        if (!repeatKeyword) {
            bornDate = StringUtils.getBornAgeCaseOne(reverseText, context, documentResult);
            colombianOCR.setFechaNacimiento(avoidNull(bornDate[0]));
            colombianOCR.setFechaExpedicion(avoidNull(bornDate[1]));
            if (colombianOCR.getFechaNacimiento() == null || colombianOCR.getFechaNacimiento().isEmpty()) {
                colombianOCR.setFechaNacimiento(Constants.COLOMBIAN_CC_NOT_DATE);
            }
            if (colombianOCR.getFechaExpedicion() == null || colombianOCR.getFechaExpedicion().isEmpty()) {
                colombianOCR.setFechaExpedicion(Constants.COLOMBIAN_CC_NOT_DATE);
            }
        }
    }

    private void findGender(String reverseText) {
        String[] repeatedWords = context.getResources().getStringArray(com.reconosersdk.reconosersdk.R.array.colombian_repeatedly_cc_reverse_words);
        boolean repeatKeyword = StringUtils.repeatedlyText(reverseText, Constants.REVERSO, repeatedWords);
        String[] genderUser;
        boolean validateGender = false;
        if (!repeatKeyword) {
            //To validate the gender between two string
            genderUser = StringUtils.getCharGender(reverseText);
            colombianOCR.setSexo(genderUser[0]);
            colombianOCR.setGenderString(genderUser[1]);
            //Set gender
            setColombianGender(colombianOCR.getSexo(), colombianOCR.getGenderString());
        }
    }

    private void setColombianGender(String gender, String genderString) {
        if (gender == null || gender.isEmpty()) {
            if (genderString == null || genderString.isEmpty()) {
                colombianOCR.setSexo("");
            } else {
                colombianOCR.setSexo(genderString);
            }
        }
    }

    private boolean fixDateElaboration(String reverseText, String expeditionDateStr) {
        String dateElaborationStr = StringUtils.parseDateElaboration(reverseText);
        if (dateElaborationStr == null || dateElaborationStr.isEmpty()) {
            return false;
        }

        Date dateExpedition = StringUtils.parseDateYMD(expeditionDateStr);
        Date dateElaboration = StringUtils.parseDateYMD(dateElaborationStr);
        if (dateElaboration == null || dateExpedition == null) {
            return false;
        }

        colombianOCR.setFechaElaboracion(dateElaborationStr);
        return StringUtils.validateOCRDateElaboration(dateExpedition, dateElaboration);
    }

    @Override
    public void onSavePhoto(@NotNull String path, Document document) {

        String image64 = ImageUtils.getEncodedBase64FromFilePath(path);
        String subtipo = "Anverso";
        if (type == 2) {
            subtipo = "Reverso";
        }

        //DetailDocument dataDocument = new DetailDocument(typeDocument, numDocument);
        GuardarBiometriaIn guardarBiometriaIn = new GuardarBiometriaIn();
        guardarBiometriaIn.setDatosAdi(addData);
        guardarBiometriaIn.setGuidCiu(guidCiudadano);
        guardarBiometriaIn.setIdServicio(4);
        guardarBiometriaIn.setSubTipo(subtipo);
        guardarBiometriaIn.setFormato("JPG_B64");
        guardarBiometriaIn.setUsuario(saveUser);
        guardarBiometriaIn.setValor(image64);
        if (guidProcessAgreement != null && !guidProcessAgreement.equals("")) {
            guardarBiometriaIn.setGuidProcesoConvenio(guidProcessAgreement);
        }
        if (SUM_TRANSACTION.equals(PreferencesSave.INSTANCE.getSavePhoto())) {
            guardarBiometriaIn.setGuidConvenio(PreferencesSave.INSTANCE.getIdGuidConv());
            guardarBiometriaIn.setGuidCiu(null);
        }

        ServicesOlimpia.getInstance().guardarBiometria(guardarBiometriaIn, new OlimpiaInterface.CallbackSaveBiometry() {
            @Override
            public void onSuccess(GuardarBiometria saveBiometry) {
                getMvpView().onFinishData(path, document);
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse) {
                cleanColombianData();
                cleanEcuadorianData();
                getMvpView().onFinishError(transactionResponse);
            }
        });
    }

    //Default values and image type (FULL HD)
    private String compressImageDocument() {
        if (path == null || path.isEmpty()) {
            setException();
        } else {
            File f = new File(path);
            try {
                File compressedImageFile = ImageUtils.getResizeRescaledImage(Constants.HEIGHT_DOCUMENT, Constants.WIDTH_DOCUMENT, Constants.MAX_QUALITY, f);
                if (compressedImageFile != null) {
                    return compressedImageFile.getPath();
                } else {
                    setException();
                }
            } catch (Exception e) {
                e.printStackTrace();
                setException();
            }
        }
        return null;
    }

    private void setException() {
        cleanColombianData();
        cleanEcuadorianData();
        getMvpView().onFinishError(Constants.ERROR_R105);
    }

    @Override
    public void onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public String onParseBarCode(@NotNull String stringBarCode) {
        String datos = "";
        if (barcode != null) {

            String barCode = stringBarCode;

            String primerApellido = "", segundoApellido = "", primerNombre = "", segundoNombre = "", cedula = "", rh = "", fechaNacimiento = "", sexo = "";

            String alphaAndDigits = barCode.replaceAll("[^\\p{Alpha}\\p{Digit}\\+\\_]+", " ");
            String[] splitStr = alphaAndDigits.split("\\s+");

            if (!alphaAndDigits.contains("PubDSK")) {
                int corrimiento = 0;

                Pattern pat = Pattern.compile("[A-Z]");
                Matcher match = pat.matcher(splitStr[2 + corrimiento]);
                int lastCapitalIndex = -1;
                if (match.find()) {
                    lastCapitalIndex = match.start();
                    Timber.d("match.start: %s", match.start());
                    Timber.d("match.end: %s", match.end());
                    Timber.d("splitStr: %s", splitStr[2 + corrimiento]);
                    Timber.d("splitStr length: %s", splitStr[2 + corrimiento].length());
                    Timber.d("lastCapitalIndex: %s", lastCapitalIndex);
                }
                cedula = splitStr[2 + corrimiento].substring(lastCapitalIndex - 10, lastCapitalIndex);
                primerApellido = splitStr[2 + corrimiento].substring(lastCapitalIndex);
                segundoApellido = splitStr[3 + corrimiento];
                primerNombre = splitStr[4 + corrimiento];
                /**
                 * Se verifica que contenga segundo nombre
                 */
                if (Character.isDigit(splitStr[5 + corrimiento].charAt(0))) {
                    corrimiento--;
                } else {
                    segundoNombre = splitStr[5 + corrimiento];
                }
                sexo = splitStr[6 + corrimiento];
                if (sexo.contains("M")) {
                    sexo = "M";
                } else if (sexo.contains("F")) {
                    sexo = "F";
                } else {
                    sexo = "T";
                }
                //sexo = splitStr[6 + corrimiento].contains("M") ? "Masculino" : "Femenino";
                rh = splitStr[6 + corrimiento].substring(splitStr[6 + corrimiento].length() - 2);
                rh = StringUtils.fixRHBarcode(rh, context);
                fechaNacimiento = splitStr[6 + corrimiento].substring(2, 10);

            } else {
                int corrimiento = 0;
                Pattern pat = Pattern.compile("[A-Z]");
                if (splitStr[2 + corrimiento].length() > 7) {
                    corrimiento--;
                }

                Matcher match = pat.matcher(splitStr[3 + corrimiento]);
                int lastCapitalIndex = -1;
                if (match.find()) {
                    lastCapitalIndex = match.start();
                }

                cedula = splitStr[3 + corrimiento].substring(lastCapitalIndex - 10, lastCapitalIndex);
                primerApellido = splitStr[3 + corrimiento].substring(lastCapitalIndex);
                segundoApellido = splitStr[4 + corrimiento];
                primerNombre = splitStr[5 + corrimiento];
                segundoNombre = splitStr[6 + corrimiento];
                sexo = splitStr[7 + corrimiento];
                if (sexo.contains("M")) {
                    sexo = "M";
                } else if (sexo.contains("F")) {
                    sexo = "F";
                } else {
                    sexo = "T";
                }
                //sexo = splitStr[7 + corrimiento].contains("M") ? "Masculino" : "Femenino";
                rh = splitStr[7 + corrimiento].substring(splitStr[7 + corrimiento].length() - 2);
                rh = StringUtils.fixRHBarcode(rh, context);
                fechaNacimiento = splitStr[7 + corrimiento].substring(2, 10);

            }
            /**
             * Se setea el objeto con los datos
             */

            datos = datos + (primerNombre) + "\n";
            datos = datos + (segundoNombre) + "\n";
            datos = datos + (primerApellido) + "\n";
            datos = datos + (segundoApellido) + "\n";
            datos = datos + (cedula) + "\n";
            datos = datos + (sexo) + "\n";
            datos = datos + (fechaNacimiento) + "\n";
            datos = datos + (rh) + "\n";

        } else {
            Timber.d("No barcode capturado");
            return datos;
        }

        return datos;
    }

    private void isSavePhoto(Document document, String path) {
        if (NOT_SAVE.equals(PreferencesSave.INSTANCE.getSavePhoto())) {
            getMvpView().onFinishData(path, document);
        } else {
            onSavePhoto(this.path, document);
        }
    }

    private void saveLog(Document document, String path) {
        ServicesOlimpia.getInstance().guardarlogError(
                new GuardarLogErrorIn(
                        PreferencesSave.INSTANCE.getIdGuidConv(),
                        JsonUtils.stringObject(document),
                        "document",
                        "ReconoSerID"
                ), new OlimpiaInterface.CallbackSaveLogError() {
                    @Override
                    public void onSuccess(GuardarLogError saveLogError) {
                        isSavePhoto(document, path);
                    }

                    @Override
                    public void onError(List<ErrorEntransaccion> transactionResponse) {
                        isSavePhoto(document, path);
                    }
                }
        );
    }

    @Override
    public void mexicanDocumentService(@NotNull String pathAnverse, @NotNull String pathReverse) {
        Anverso anverso = new Anverso();
        Reverso reverso = new Reverso();
        anverso.setValor(ImageUtils.getEncodedBase64FromFilePath(pathAnverse));
        anverso.setFormato("JPG_64");
        reverso.setValor(ImageUtils.getEncodedBase64FromFilePath(pathReverse));
        reverso.setFormato("JPG_64");
        ValidarDocumentoGenericoIn validarDocumentoGenericoIn = new ValidarDocumentoGenericoIn();
        validarDocumentoGenericoIn.setAnverso(anverso);
        validarDocumentoGenericoIn.setReverso(reverso);
        validarDocumentoGenericoIn.setUsuario("movil");
        validarDocumentoGenericoIn.setDatosAdi("CURP");
        validarDocumentoGenericoIn.setConvenioGuid(LibraryReconoSer.getGuidConv());

        WebService.getInstance().getServiceOlimpiaApi().getDocumentValidation(validarDocumentoGenericoIn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ValidarDocumentoGenericoOut>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull Response<ValidarDocumentoGenericoOut> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null)
                                if (response.body().getRespuestaTransaccion().getEsExitosa()) {
                                    resultsData.add(new DataReadDocument("data", JsonUtils.stringObject(response.body().getData())));
                                    resultsData.add(new DataReadDocument("procesoConvenioGuid", JsonUtils.stringObject(response.body().getProcesoConvenioGuid())));
                                    resultsData.add(new DataReadDocument("ciudadanoGuid", JsonUtils.stringObject(response.body().getCiudadanoGuid())));
                                    resultsData.add(new DataReadDocument("respuestaTransaccion", JsonUtils.stringObject(response.body().getRespuestaTransaccion().getEsExitosa())));
                                    dataResult.setResult(resultsData);
                                    Document document = new Document(null, null, "", String.valueOf(Constants.MEXICAN_OBVERSE_DOCUMENT),
                                            "", dataResult, documentValidations);
                                    getMvpView().onFinishData(pathAnverse, document);
                                } else {
                                    getMvpView().onFinishError(Constants.ERROR_R118);
                                }
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable t) {
                        if (t.toString().equals("timeout") || t.toString().contains("timed out")) {
                            getMvpView().onFinishError(Constants.ERROR_R116);
                        } else {
                            getMvpView().onFinishError(Constants.ERROR_R100);
                        }
                    }
                });
    }
}