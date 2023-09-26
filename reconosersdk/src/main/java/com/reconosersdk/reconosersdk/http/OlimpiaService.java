package com.reconosersdk.reconosersdk.http;

import static com.reconosersdk.reconosersdk.utils.ConstantsOlimpia.LOOP_GET_TOKEN;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reconosersdk.reconosersdk.BuildConfig;
import com.reconosersdk.reconosersdk.http.OlimpiaInterface.CallbackConsultAgreement;
import com.reconosersdk.reconosersdk.http.OlimpiaInterface.CallbackConsultResident;
import com.reconosersdk.reconosersdk.http.OlimpiaInterface.CallbackGetToken;
import com.reconosersdk.reconosersdk.http.OlimpiaInterface.CallbackRequestQuestions;
import com.reconosersdk.reconosersdk.http.OlimpiaInterface.CallbackSaveBiometry;
import com.reconosersdk.reconosersdk.http.OlimpiaInterface.CallbackSaveDocument;
import com.reconosersdk.reconosersdk.http.OlimpiaInterface.CallbackSaveLogError;
import com.reconosersdk.reconosersdk.http.OlimpiaInterface.CallbackSaveResident;
import com.reconosersdk.reconosersdk.http.OlimpiaInterface.CallbackSendOTP;
import com.reconosersdk.reconosersdk.http.OlimpiaInterface.CallbackValidateBiometry;
import com.reconosersdk.reconosersdk.http.OlimpiaInterface.CallbackValidateOTP;
import com.reconosersdk.reconosersdk.http.OlimpiaInterface.CallbackValidateResponse;
import com.reconosersdk.reconosersdk.http.OlimpiaInterface.OlimpiaApiInterface;
import com.reconosersdk.reconosersdk.http.aceptarATDP.in.AceptarAtdpIn;
import com.reconosersdk.reconosersdk.http.aceptarATDP.out.AceptarAtdpOut;
import com.reconosersdk.reconosersdk.http.api.WebService;
import com.reconosersdk.reconosersdk.http.consultAgreementProcess.out.ConsultAgreementProcessOut;
import com.reconosersdk.reconosersdk.http.consultSteps.in.ConsultStepsIn;
import com.reconosersdk.reconosersdk.http.consultSteps.out.ConsultStepsOut;
import com.reconosersdk.reconosersdk.http.consultValidation.in.ConsultValidationIn;
import com.reconosersdk.reconosersdk.http.consultValidation.out.ConsultValidationOut;
import com.reconosersdk.reconosersdk.http.enviarOTPAuthID.in.EnviarOTPAuthIDIn;
import com.reconosersdk.reconosersdk.http.enviarOTPAuthID.out.EnviarOTPAuthIDOut;
import com.reconosersdk.reconosersdk.http.login.in.LoginIn;
import com.reconosersdk.reconosersdk.http.login.out.LoginOut;
import com.reconosersdk.reconosersdk.http.obtainDataEasyTrack.in.ObtainDataEasyTrackIn;
import com.reconosersdk.reconosersdk.http.obtainDataEasyTrack.out.ObtainDataEasyTrackOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.CancelarProceso;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.CiudadanoIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ConsultarEstadoProceso;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ConsultarFuenteIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ConsultarProceso;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.DataComparisonFace;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.DataValidateFace;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.DatosOTP;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.EnviarOTPIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.GuardarBiometriaIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.GuardarDocumentoIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.GuardarLogErrorIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ObtenerToken;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.OpenSourceValidationRequest;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ProcesosPendientes;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.RespondConsultarFuente;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.SearchBiometry;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.SearchForDocument;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.SearchUser;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.SolicitudProceso;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ValidarBiometriaIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ValidarRespuestaIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ValidateDriverLicenseIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ValidateFederalDriverLicenseIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ValidateReceiptIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ani.ConsultarAniIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.savelogs.LogMobileResult;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.savelogs.LogSaveBarcode;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.savelogs.LogSaveOCR;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.savetraceability.SaveTraceabilityProcessIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.AutenticarAsesorOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.BarcodeDocument;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.CancelarProcesoOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ConsultarCiudadano;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ConsultarConvenio;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ConsultarEstadoProcesoOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ConsultarProcesoOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.DataError;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.EnviarOTP;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ErrorEntransaccion;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.GuardarBiometria;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.GuardarCiudadano;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.GuardarLogError;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.HeaderToken;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.MotivosCanceladoOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ProcesosPendientesOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespondSearchDocument;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.SedeConvenioOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.SolicitarPreguntasDemograficas;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.SolicitudProcesoOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.UserFound;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidarBiometria;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidarOTP;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidarRespuestaDemografica;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidateDriverLicenseOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidateFederalDriverLicenseOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidateReceiptOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ani.ConsultarAniOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.finish.RespondFinishProcess;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid.RespondComparasionFace;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid.RespondValidateFace;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid.openSource.RespondOpenSourceValidation;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.savelogs.RespondLogIdentity;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.savelogs.RespondLogMobileResult;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.savetraceability.SaveTraceabilityOut;
import com.reconosersdk.reconosersdk.http.openSource.in.ConsultarFuentesAbiertasIn;
import com.reconosersdk.reconosersdk.http.openSource.in.SolicitudFuentesAbiertasIn;
import com.reconosersdk.reconosersdk.http.openSource.out.ConsultarFuentesAbiertasOut;
import com.reconosersdk.reconosersdk.http.openSource.out.ParametroFuenteOut;
import com.reconosersdk.reconosersdk.http.openSource.out.SolicitudFuentesAbiertasOut;
import com.reconosersdk.reconosersdk.http.requestValidation.in.RequestValidationIn;
import com.reconosersdk.reconosersdk.http.requestValidation.out.RequestValidationOut;
import com.reconosersdk.reconosersdk.http.saveDocumentSides.in.SaveDocumentSidesIn;
import com.reconosersdk.reconosersdk.http.saveDocumentSides.in.Trazabilidad;
import com.reconosersdk.reconosersdk.http.saveDocumentSides.out.SaveDocumentSidesOut;
import com.reconosersdk.reconosersdk.http.validateine.in.ValidateIneIn;
import com.reconosersdk.reconosersdk.http.validateine.out.ValidateIneOutX;
import com.reconosersdk.reconosersdk.utils.Constants;
import com.reconosersdk.reconosersdk.utils.ConstantsOlimpia;
import com.reconosersdk.reconosersdk.utils.JsonUtils;
import com.reconosersdk.reconosersdk.utils.Miscellaneous;
import com.reconosersdk.reconosersdk.utils.PublicIpAddress;
import com.reconosersdk.reconosersdk.utils.StringUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import timber.log.Timber;

public class OlimpiaService implements OlimpiaApiInterface {

    private Disposable disposable;
    private HeaderToken newHeaderToken = HeaderToken.getInstance();
    private static final String FALSE_GUID = "00000000-0000-0000-0000-000000000000";

    //To get the token
    @Override
    public void onGetToken(@NonNull ObtenerToken obtenerToken, CallbackGetToken listener) {

        //To validate any error into obtenerToken class
        if (obtenerToken == null || obtenerToken.getClientId() == null || obtenerToken.getClientId().isEmpty() ||
                obtenerToken.getClientSecret() == null || obtenerToken.getClientSecret().isEmpty()) {
            obtenerToken = new ObtenerToken(BuildConfig.USER_TOKEN, BuildConfig.PASS_TOKEN);
        }

        //To validate any error into requestBody
        RequestBody body = JsonUtils.requestObject(obtenerToken);
        String validateJson = JsonUtils.bodyToString(body);
        if (validateJson == null || validateJson.isEmpty() || validateJson.equals("{}") || validateJson.equals("\"{}\"")) {
            body = JsonUtils.requestBody("clientId", "Angular",
                    "clientSecret", "secret");
        }

        WebService.getInstance().getServiceOlimpiaApi().traerToken(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<HeaderToken>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(HeaderToken headerToken) {
                        newHeaderToken.setHeaderToken(headerToken.getAccessToken(), headerToken.getIdentityToken(),
                                headerToken.getTokenType(), headerToken.getRefreshToken(),
                                headerToken.getErrorDescription(), headerToken.getExpiresIn());
                        if (newHeaderToken.getAccessToken() == null && newHeaderToken.getTokenType() == null) {
                            newHeaderToken.setHeaderToken("", "", "", "", "", LOOP_GET_TOKEN);
                            listener.onError(newHeaderToken);
                        } else {
                            listener.onSuccess(newHeaderToken);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        newHeaderToken.setHeaderToken("", "", "", "", "", LOOP_GET_TOKEN);
                        listener.onError(newHeaderToken);
                    }
                });
    }

    //To consult the agreement for the first time
    @Override
    public void initService(@NonNull String guidConv, @NonNull String datos) {
        RequestBody body = JsonUtils.requestBody(ConstantsOlimpia.GUID_CONV, guidConv,
                ConstantsOlimpia.DATOS, datos);

        WebService.getInstance().getServiceOlimpiaApi().consultarConvenio(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ConsultarConvenio>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ConsultarConvenio consultarConvenio) {
                        PreferencesSDK.getInstance().saveStatusConv(consultarConvenio.isEstadoActivo());
                        if (consultarConvenio.isEstadoActivo()) {
                            PreferencesSDK.getInstance().saveGuidConvenio(guidConv);
                            savePreference(consultarConvenio);
                        } else {
                            PreferencesSDK.getInstance().removePreferences();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        PreferencesSDK.getInstance().removePreferences();
                    }
                });
    }

    //To auth
    @Override
    public void onAuthUser(@NonNull String adviser, @NonNull String codeAdviser, OlimpiaInterface.CallbackAuthentication listener) {

        RequestBody body = JsonUtils.requestBody(ConstantsOlimpia.AUTHENTICATION, adviser,
                ConstantsOlimpia.AUTH_ID, codeAdviser);

        WebService.getInstance().getServiceOlimpiaApi().autenticar(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AutenticarAsesorOut>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(AutenticarAsesorOut autenticarAsesorOut) {
                        if (autenticarAsesorOut.getRespuestaTransaccion() != null) {
                            if (autenticarAsesorOut.getRespuestaTransaccion().isEsExitosa()) {
                                listener.onSuccess(autenticarAsesorOut);
                            } else {
                                listener.onError(autenticarAsesorOut.getRespuestaTransaccion());
                            }
                        } else {
                            listener.onError(getErrorCode(Constants.ERROR_102));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    //To get the campus
    @Override
    public void onSedeConvenio(@NonNull String guidConv, OlimpiaInterface.CallbackSedeConvenio listener) {

        RequestBody body = JsonUtils.requestBody(ConstantsOlimpia.GUID_CONV, guidConv);

        WebService.getInstance().getServiceOlimpiaApi().sedeConvenio(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SedeConvenioOut>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(SedeConvenioOut sedeConvenioOut) {
                        if (sedeConvenioOut.getRespuestaTransaccion() != null) {
                            if (sedeConvenioOut.getRespuestaTransaccion().isEsExitosa()) {
                                listener.onSuccess(sedeConvenioOut);
                            } else {
                                listener.onError(sedeConvenioOut.getRespuestaTransaccion());
                            }
                        } else {
                            listener.onError(getErrorCode(Constants.ERROR_102));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    //To get the canceled reason
    @Override
    public void onMotivosCancelado(@NonNull String guidConv, OlimpiaInterface.CallbackMotivosCancelado listener) {
        RequestBody body = JsonUtils.requestBody(ConstantsOlimpia.GUID_CONV, guidConv);

        WebService.getInstance().getServiceOlimpiaApi().motivosCancelado(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MotivosCanceladoOut>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(MotivosCanceladoOut motivosCanceladoOut) {
                        if (motivosCanceladoOut.getRespuestaTransaccion() != null) {
                            if (motivosCanceladoOut.getRespuestaTransaccion().isEsExitosa()) {
                                listener.onSuccess(motivosCanceladoOut);
                            } else {
                                listener.onError(motivosCanceladoOut.getRespuestaTransaccion());
                            }
                        } else {
                            listener.onError(getErrorCode(Constants.ERROR_102));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    //To get the pending process
    @Override
    public void onProcesosPendientes(@NonNull ProcesosPendientes procesosPendientes, OlimpiaInterface.CallbackProcesosPendientes listener) {
        RequestBody body = JsonUtils.requestObject(procesosPendientes);

        WebService.getInstance().getServiceOlimpiaApi().procesosPendientes(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ProcesosPendientesOut>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ProcesosPendientesOut procesosPendientesOut) {
                        if (procesosPendientesOut.getRespuestaTransaccion() != null) {
                            if (procesosPendientesOut.getRespuestaTransaccion().isEsExitosa()) {
                                listener.onSuccess(procesosPendientesOut);
                            } else {
                                listener.onError(procesosPendientesOut.getRespuestaTransaccion());
                            }
                        } else {
                            listener.onError(getErrorCode(Constants.ERROR_102));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    //To cancel the process
    @Override
    public void onCancelarProceso(@NonNull CancelarProceso cancelarProceso, OlimpiaInterface.CallbackCancelarProceso listener) {
        RequestBody body = JsonUtils.requestObject(cancelarProceso);

        WebService.getInstance().getServiceOlimpiaApi().cancelarProceso(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CancelarProcesoOut>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(CancelarProcesoOut cancelarProcesoOut) {
                        if (cancelarProcesoOut.getRespuestaTransaccion() != null) {
                            if (cancelarProcesoOut.getRespuestaTransaccion().isEsExitosa()) {
                                listener.onSuccess(cancelarProcesoOut);
                            } else {
                                listener.onError(cancelarProcesoOut.getRespuestaTransaccion());
                            }
                        } else {
                            listener.onError(getErrorCode(Constants.ERROR_102));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    //To consult the state process
    @Override
    public void onConsultarEstadoProceso(@NonNull ConsultarEstadoProceso consultarEstadoProceso, OlimpiaInterface.CallbackConsultarEstadoProceso listener) {
        RequestBody body = JsonUtils.requestObject(consultarEstadoProceso);

        WebService.getInstance().getServiceOlimpiaApi().consultarEstadoProceso(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ConsultarEstadoProcesoOut>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(ConsultarEstadoProcesoOut consultarEstadoProcesoOut) {
                        if (consultarEstadoProcesoOut.getRespuestaTransaccion() != null) {
                            if (consultarEstadoProcesoOut.getRespuestaTransaccion().isEsExitosa()) {
                                listener.onSuccess(consultarEstadoProcesoOut);
                            } else {
                                listener.onError(consultarEstadoProcesoOut.getRespuestaTransaccion());
                            }
                        } else {
                            listener.onError(getErrorCode(Constants.ERROR_102));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }

                    @Override
                    public void onComplete() {
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }
                });
    }

    //To process request
    @Override
    public void onSolicitudProceso(@NonNull SolicitudProceso solicitudProceso, OlimpiaInterface.CallbackSolicitudProceso listener) {
        RequestBody body = JsonUtils.requestObject(solicitudProceso);

        WebService.getInstance().getServiceOlimpiaApi().solicitudProceso(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SolicitudProcesoOut>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //No implementation needed
                    }

                    @Override
                    public void onSuccess(SolicitudProcesoOut solicitudProcesoOut) {
                        if (solicitudProcesoOut.getRespuestaTransaccion() != null) {
                            if (solicitudProcesoOut.getRespuestaTransaccion().isEsExitosa()) {
                                listener.onSuccess(solicitudProcesoOut);
                            } else {
                                listener.onError(solicitudProcesoOut.getRespuestaTransaccion());
                            }
                        } else {
                            listener.onError(getErrorCode(Constants.ERROR_102));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    //To consult the process
    @Override
    public void onConsultarProceso(@NonNull ConsultarProceso consultarProceso, OlimpiaInterface.CallbackConsultarProceso listener) {
        RequestBody body = JsonUtils.requestObject(consultarProceso);

        WebService.getInstance().getServiceOlimpiaApi().consultarProceso(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ConsultarProcesoOut>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //No implementation needed
                    }

                    @Override
                    public void onSuccess(ConsultarProcesoOut consultarProcesoOut) {
                        if (consultarProcesoOut.getRespuestaTransaccion() != null) {
                            if (consultarProcesoOut.getRespuestaTransaccion().isEsExitosa()) {
                                listener.onSuccess(consultarProcesoOut);
                            } else {
                                listener.onError(consultarProcesoOut.getRespuestaTransaccion());
                            }
                        } else {
                            listener.onError(getErrorCode(Constants.ERROR_102));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }

                });
    }


    @Override
    public boolean isServiceDocAnverso() {
        return PreferencesSDK.getInstance().getServiceDocAnv();
    }

    @Override
    public boolean isServiceDocReverso() {
        return PreferencesSDK.getInstance().getServiceDocRev();
    }

    @Override
    public boolean isServiceBiometryFace() {
        return PreferencesSDK.getInstance().getServiceBioFace();
    }

    @Override
    public boolean isServiceBarcode() {
        return PreferencesSDK.getInstance().getServiceBarcode();
    }

    @Override
    public boolean isServiceSms() {
        return PreferencesSDK.getInstance().getServiceEmail();
    }

    @Override
    public boolean isServiceEmail() {
        return PreferencesSDK.getInstance().getServiceEmail();
    }

    @Override
    public void onValidateMexicanDocument(@NonNull ValidateIneIn validateIneIn, OlimpiaInterface.CallbackValidateIne listener) {

        WebService.getInstance().getReconoserIdApi().validarFuentesDocumento(validateIneIn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ValidateIneOutX>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull Response<ValidateIneOutX> validateIneOutXResponse) {
                        if (validateIneOutXResponse.isSuccessful()) {
                            listener.onSuccess(validateIneOutXResponse.body());
                        } else {
                            listener.onError(getErrorCode(Constants.ERROR_R117));
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    //To consult the agreement
    @Override
    public void onGetAgreement(@NonNull String guidConv, @NonNull String datos, CallbackConsultAgreement listener) {

        RequestBody body = JsonUtils.requestBody(ConstantsOlimpia.GUID_CONV, guidConv,
                ConstantsOlimpia.DATOS, datos);

        WebService.getInstance().getServiceOlimpiaApi().consultarConvenio(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ConsultarConvenio>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ConsultarConvenio consultarConvenio) {
                        PreferencesSDK.getInstance().saveStatusConv(consultarConvenio.isEstadoActivo());
                        if (consultarConvenio.isEstadoActivo()) {
                            PreferencesSDK.getInstance().saveGuidConvenio(guidConv);
                            savePreference(consultarConvenio);
                            listener.onSuccess(consultarConvenio.isEstadoActivo(), consultarConvenio);
                        } else {
                            listener.onError(consultarConvenio.isEstadoActivo(), consultarConvenio.getRespuestaTransaccion());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(false, onGetError(e));
                    }
                });
    }

    private void savePreference(ConsultarConvenio consultarConvenio) {
        saveStatusSavePhoto(consultarConvenio);
        saveJsonPrefer(consultarConvenio);
        preferenceEmail();
        preferenceSMS();
        preferenceBiometry();
    }

    private void saveStatusSavePhoto(ConsultarConvenio consultarConvenio) {
        if (consultarConvenio.getPersonalizacion() != null && !consultarConvenio.getPersonalizacion().isEmpty()) {
            boolean isSave = false;
            for (int i = 0; i < consultarConvenio.getPersonalizacion().size(); i++) {
                if (consultarConvenio.getPersonalizacion().get(i).getNombre().equals(Constants.SOLO_ROSTRO_VIVO)) {
                    PreferencesSDK.getInstance().saveSavePhoto(consultarConvenio.getPersonalizacion().get(i).getValor());
                    // PreferencesSDK.getInstance().saveSavePhoto("0"); //TODO pruebas convenio 0
                    i = consultarConvenio.getPersonalizacion().size();
                    isSave = true;
                }
            }
            if (!isSave) {
                PreferencesSDK.getInstance().saveSavePhoto("0");
            }
        } else {
            PreferencesSDK.getInstance().saveSavePhoto("0");
        }
    }

    private void saveJsonPrefer(ConsultarConvenio consultarConvenio) {
        Gson gson = new Gson();
        String json = gson.toJson(consultarConvenio);
        PreferencesSDK.getInstance().saveServicesJson(json);
    }

    private void preferenceEmail() {
        ConsultarConvenio consultarConvenio = new Gson().fromJson(PreferencesSDK.getInstance().getSERVICES_JSON(), ConsultarConvenio.class);

        for (int i = 0; i < consultarConvenio.getServiciosConv().size(); i++) {
            if (consultarConvenio.getServiciosConv().get(i).getNombreServicio().equals(ConstantsOlimpia.OTP) &&
                    consultarConvenio.getServiciosConv().get(i).getTipoServicio().equals(ConstantsOlimpia.EMAIL)) {
                PreferencesSDK.getInstance().saveServiceEmail(true);
                PreferencesSDK.getInstance().saveServicOTP(true);
            }
        }
    }

    private void preferenceSMS() {
        ConsultarConvenio consultarConvenio = new Gson().fromJson(PreferencesSDK.getInstance().getSERVICES_JSON(), ConsultarConvenio.class);

        for (int i = 0; i < consultarConvenio.getServiciosConv().size(); i++) {
            if (consultarConvenio.getServiciosConv().get(i).getNombreServicio().equals(ConstantsOlimpia.OTP) &&
                    consultarConvenio.getServiciosConv().get(i).getTipoServicio().equals(ConstantsOlimpia.SMS)) {
                PreferencesSDK.getInstance().saveServiceSMS(true);
                PreferencesSDK.getInstance().saveServicOTP(true);
            }
        }
    }

    private void preferenceBiometry() {
        ConsultarConvenio consultarConvenio = new Gson().fromJson(PreferencesSDK.getInstance().getSERVICES_JSON(), ConsultarConvenio.class);

        for (int i = 0; i < consultarConvenio.getServiciosConv().size(); i++) {
            if (consultarConvenio.getServiciosConv().get(i).getNombreServicio().toUpperCase().equals(ConstantsOlimpia.BIOMETRIA_CON.toUpperCase()) &&
                    (consultarConvenio.getServiciosConv().get(i).getTipoServicio().toUpperCase().equals(ConstantsOlimpia.BIOMETRIA_DOC.toUpperCase()) || consultarConvenio.getServiciosConv().get(i).getTipoServicio().toUpperCase().equals(ConstantsOlimpia.BIOMETRIA_FACE.toUpperCase()))) {
                PreferencesSDK.getInstance().saveServiceBioDoc(true);
                if (consultarConvenio.getServiciosConv().get(i).getSubTipo().contains(ConstantsOlimpia.DOC_ANVERSO)) {
                    PreferencesSDK.getInstance().saveServiceDocAnv(true);
                }
                if (consultarConvenio.getServiciosConv().get(i).getSubTipo().contains(ConstantsOlimpia.DOC_REVERSO)) {
                    PreferencesSDK.getInstance().saveServiceDocRev(true);
                }
                if (consultarConvenio.getServiciosConv().get(i).getSubTipo().contains(ConstantsOlimpia.BIOMETRIA_FACE_FRONT)) {
                    PreferencesSDK.getInstance().saveServiceBioFace(true);
                }
                if (consultarConvenio.getServiciosConv().get(i).getSubTipo().contains(ConstantsOlimpia.BIOMETRIA_BARCODE)) {
                    PreferencesSDK.getInstance().saveServiceBarcod(true);
                }
            }
        }
    }

    //To save the citizen
    @Override
    public void onSaveResident(@NonNull CiudadanoIn guardarCiudadanoIn, CallbackSaveResident listener) {
        if (!PreferencesSDK.getInstance().getStatus()) {
            listener.onError(getErrorConvenio());
            return;
        }

        RequestBody body = JsonUtils.requestObject(guardarCiudadanoIn);

        WebService.getInstance().getServiceOlimpiaApi().guardarCiudadano(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GuardarCiudadano>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GuardarCiudadano guardarCiudadano) {
                        if (guardarCiudadano.getRespuestaTransaccion() != null) {
                            if (guardarCiudadano.getRespuestaTransaccion().isEsExitosa()) {
                                listener.onSuccess(guardarCiudadano);
                            } else {
                                listener.onError(guardarCiudadano.getRespuestaTransaccion());
                            }
                        } else {
                            listener.onError(getErrorCode(Constants.ERROR_102));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    //To save the biometry
    @Override
    public void onSaveBiometry(@NonNull GuardarBiometriaIn guardarBiometriaIn, CallbackSaveBiometry listener) {
        if (!PreferencesSDK.getInstance().getStatus()) {
            listener.onError(getErrorConvenio());
            return;
        }

        WebService.getInstance().getServiceOlimpiaApi().guardarBiometria(guardarBiometriaIn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GuardarBiometria>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GuardarBiometria guardarBiometria) {
                        if (guardarBiometria.getRespuestaTransaccion().isEsExitosa()) {
                            listener.onSuccess(guardarBiometria);
                        } else {
                            listener.onError(guardarBiometria.getRespuestaTransaccion());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    //To validate the biometry
    @Override
    public void onValidateBiometry(@NonNull ValidarBiometriaIn validarBiometriaIn, CallbackValidateBiometry listener) {
        if (!PreferencesSDK.getInstance().getStatus()) {
            listener.onError(getErrorConvenio(), -2);
            return;
        }

        WebService.getInstance().getServiceOlimpiaApi().validarBiometria(validarBiometriaIn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ValidarBiometria>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(ValidarBiometria validarBiometria) {
                        if (validarBiometria.getRespuestaTransaccion().isEsExitosa()) {
                            listener.onSuccess(validarBiometria);
                        } else {
                            listener.onError(validarBiometria.getRespuestaTransaccion(), validarBiometria.getIntentos());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e), -2);
                    }

                    @Override
                    public void onComplete() {
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }
                });
    }

    //To save the document
    @Override
    public void onSaveDocument(@NonNull GuardarDocumentoIn guardarDocumentoIn, CallbackSaveDocument listener) {

        RequestBody body = JsonUtils.requestObject(guardarDocumentoIn);

        WebService.getInstance().getServiceOlimpiaApi().guardarDocumento(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<BarcodeDocument>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BarcodeDocument barcodeDocument) {
                        if (barcodeDocument.getRespuestaTransaccion().isEsExitosa()) {
                            listener.onSuccess(barcodeDocument);
                        } else {
                            listener.onError(barcodeDocument.getRespuestaTransaccion().getErrorEntransaccion());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e).getErrorEntransaccion());
                    }
                });
    }

    //To send the OTP
    @Override
    public void onSendOTP(@NonNull EnviarOTPIn enviarOTPIn, CallbackSendOTP listener) {
        if (!PreferencesSDK.getInstance().getStatus()) {
            listener.onError(getErrorConvenio());
            return;
        }
        if (!isValidServiceOTP(enviarOTPIn.getDatosOTP())) {
            listener.onError(getErrorService());
            return;
        }

        String dataOTP = JsonUtils.stringObject(enviarOTPIn.getDatosOTP());
        RequestBody body = JsonUtils.requestBody(ConstantsOlimpia.GUID_CIUDADANO, enviarOTPIn.getGuidCiudadano(),
                ConstantsOlimpia.GUID_PROCESO_CONVENIO, enviarOTPIn.getGuidProcesoConvenio(), ConstantsOlimpia.DATOS_OTP, dataOTP);

        WebService.getInstance().getServiceOlimpiaApi().enviarOTP(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<EnviarOTP>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(EnviarOTP enviarOTP) {
                        if (enviarOTP.getRespuestaTransaccion().isEsExitosa()) {
                            listener.onSuccess(enviarOTP);
                        } else {
                            listener.onError(enviarOTP.getRespuestaTransaccion());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    private boolean isValidServiceOTP(DatosOTP datosOTP) {
        if (!PreferencesSDK.getInstance().getServicOTP()) {
            return false;
        }
        if (datosOTP.getTipoOTP().equals(ConstantsOlimpia.SMS) && PreferencesSDK.getInstance().getServiceSMS()) {
            return true;
        } else
            return datosOTP.getTipoOTP().equals(ConstantsOlimpia.MAIL) && PreferencesSDK.getInstance().getServiceEmail();
    }

    //To validate the OTP
    @Override
    public void onValidateOTP(@NonNull String guidOTP, @NonNull String oTP, CallbackValidateOTP listener) {
        if (!PreferencesSDK.getInstance().getStatus()) {
            listener.onError(getErrorConvenio());
            return;
        }

        RequestBody body = JsonUtils.requestBody(ConstantsOlimpia.GUID_OTP, guidOTP, ConstantsOlimpia.OTP, oTP);

        WebService.getInstance().getServiceOlimpiaApi().validarOTP(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ValidarOTP>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ValidarOTP validarOTP) {
                        if (validarOTP.isEsValida()) {
                            listener.onSuccess(validarOTP);
                        } else {
                            listener.onError(validarOTP.getRespuestaTransaccion());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    //To request the questions
    @Override
    public void onRequestQuestions(@NonNull String giudCiudadano, CallbackRequestQuestions listener) {
        if (!PreferencesSDK.getInstance().getStatus()) {
            listener.onError(getErrorConvenio());
            return;
        }

        RequestBody body = JsonUtils.requestBody(ConstantsOlimpia.GUID_CIUDADANO, giudCiudadano);

        WebService.getInstance().getServiceOlimpiaApi().solicitarPreguntasDemograficas(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SolicitarPreguntasDemograficas>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(SolicitarPreguntasDemograficas solicitarPreguntasDemograficas) {
                        if (solicitarPreguntasDemograficas.getRespuestaTransaccion().isEsExitosa()) {
                            listener.onSuccess(solicitarPreguntasDemograficas);
                        } else {
                            listener.onError(solicitarPreguntasDemograficas.getRespuestaTransaccion());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    //To validate the questions
    @Override
    public void onValidateResponse(@NonNull ValidarRespuestaIn validarRespuestaDemografica, CallbackValidateResponse listener) {
        if (!PreferencesSDK.getInstance().getStatus()) {
            listener.onError(getErrorConvenio());
            return;
        }
        String data = JsonUtils.stringObject(validarRespuestaDemografica);


        RequestBody body = JsonUtils.requestBody("repuestas", data);

        WebService.getInstance().getServiceOlimpiaApi().validarRespuestaDemografica(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ValidarRespuestaDemografica>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ValidarRespuestaDemografica validarRespuestaDemografica) {
                        if (validarRespuestaDemografica.getRespuestaTransaccion().isEsExitosa()) {
                            listener.onSuccess(validarRespuestaDemografica);
                        } else {
                            listener.onError(validarRespuestaDemografica.getRespuestaTransaccion());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    //To consult the citizen
    @Override
    public void onConsultResident(@NonNull String guidCiudadano, CallbackConsultResident listener) {
        if (!PreferencesSDK.getInstance().getStatus()) {
            listener.onError(getErrorConvenio());
            return;
        }
        RequestBody body = JsonUtils.requestBody(ConstantsOlimpia.GUID_CIUDADANO, guidCiudadano);

        WebService.getInstance().getServiceOlimpiaApi().consultarCiudadano(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ConsultarCiudadano>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ConsultarCiudadano consultarCiudadano) {
                        if (consultarCiudadano.getRespuestaTransaccion().isEsExitosa()) {
                            listener.onSuccess(consultarCiudadano);
                        } else {
                            listener.onError(consultarCiudadano.getRespuestaTransaccion());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    //To save tho log error
    @Override
    public void onSaveLogError(@NonNull GuardarLogErrorIn guardarLogErrorIn, CallbackSaveLogError listener) {
        if (!PreferencesSDK.getInstance().getStatus()) {
            listener.onError(getErrorConvenio().getErrorEntransaccion());
            return;
        }
        RequestBody body = JsonUtils.requestObject(guardarLogErrorIn);

        WebService.getInstance().getServiceOlimpiaApi().guardarlogError(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<GuardarLogError>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GuardarLogError guardarLogError) {
                        if (guardarLogError.getRespuestaTransaccion().isEsExitosa()) {
                            listener.onSuccess(guardarLogError);
                        } else {
                            listener.onError(guardarLogError.getRespuestaTransaccion().getErrorEntransaccion());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e).getErrorEntransaccion());
                    }
                });
    }

    @Override
    public void onSearchUser(@NotNull SearchUser searchUser, OlimpiaInterface.CallbackSearchUser listener) {

        WebService.getInstance().getServiceOlimpiaApi().obtenerUserDemo(searchUser)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<UserFound>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<UserFound> userFoundResponse) {
                        System.out.println(userFoundResponse);
                        //userFoundResponse.body.data
                        if (userFoundResponse.isSuccessful()) {
                            listener.onSuccess(userFoundResponse.body());
                        } else {
                            try {
                                Gson gson = new Gson();
                                String jsonRequest = gson.toJson(searchUser);
                                listener.onError(mapperRespond(userFoundResponse.errorBody(), jsonRequest));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onSearchForDocument(@NotNull SearchForDocument searchForDocument, OlimpiaInterface.CallbackSearchForDocument listener) {
        WebService.getInstance().getServiceOlimpiaApi().consultarCiudadanoDocumento(searchForDocument)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<RespondSearchDocument>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(RespondSearchDocument respondSearchDocument) {
                        if (respondSearchDocument.getGuidCiudadano() == null || respondSearchDocument.getGuidCiudadano().isEmpty()) {
                            listener.onError(respondSearchDocument.getRespuestaTransaccion());
                        } else {
                            listener.onSuccess(respondSearchDocument);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onComparasionFace(@NonNull DataComparisonFace dataComparisonFace, OlimpiaInterface.CallbackReconoserComparasion listener) {
        WebService.getInstance().getReconoserIdApi().compararRostros(dataComparisonFace)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<RespondComparasionFace>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<RespondComparasionFace> respondComparasionFaceResponse) {
                        if (respondComparasionFaceResponse.isSuccessful()) {
                            listener.onSuccess(respondComparasionFaceResponse.body());
                        } else {
                            try {
                                Gson gson = new Gson();
                                String jsonRequest = gson.toJson(dataComparisonFace);
                                listener.onError(mapperRespond(respondComparasionFaceResponse.errorBody(), jsonRequest));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onValidateFaceBD(@NonNull DataValidateFace dataValidateFace, OlimpiaInterface.CallbackReconoserValidate listener) {
        WebService.getInstance().getReconoserIdApi().validarRostroInBD(dataValidateFace)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<RespondValidateFace>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<RespondValidateFace> respondValidateFaceResponse) {
                        if (respondValidateFaceResponse.isSuccessful()) {
                            listener.onSuccess(respondValidateFaceResponse.body());
                        } else {
                            try {
                                Gson gson = new Gson();
                                String jsonRequest = gson.toJson(dataValidateFace);
                                listener.onError(mapperRespond(respondValidateFaceResponse.errorBody(), jsonRequest));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onFinishProcess(@NotNull String idProcess, @NotNull boolean state, OlimpiaInterface.CallbackFinishProcess listener) {
        WebService.getInstance().getServiceOlimpiaApi().finishProcess(idProcess, state)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<RespondFinishProcess>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull Response<RespondFinishProcess> respondFinishProcessResponse) {
                        if (respondFinishProcessResponse.isSuccessful()) {
                            if (respondFinishProcessResponse.body() != null) {
                                listener.onSuccess(respondFinishProcessResponse.body().getData().getEsExitosa());
                            } else {
                                listener.onSuccess(false);
                            }
                        } else {
                            try {
                                String jsonRequest = "{idProcess:" + idProcess + ";state:"
                                        + state + "";
                                listener.onError(mapperRespond(respondFinishProcessResponse.errorBody(), jsonRequest));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onGetBiometry64(@NotNull SearchBiometry searchBiometry, OlimpiaInterface.CallbackGetBiometry64 listener) {

    }

    @Override
    public void onValidationOpenSourceDocument(@NonNull @NotNull OpenSourceValidationRequest openSourceValidationRequest, OlimpiaInterface.CallbackValidationOpenSourceDocument listener) {
        WebService.getInstance().getReconoserIdApi().validarDocumentoExtranjero(openSourceValidationRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<RespondOpenSourceValidation>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull Response<RespondOpenSourceValidation> respondOpenSourceValidation) {
                        if (respondOpenSourceValidation.isSuccessful()) {
                            if (respondOpenSourceValidation.body() != null) {
                                if (respondOpenSourceValidation.body().getData() != null) {
                                    listener.onSuccess(respondOpenSourceValidation.body());
                                } else {
                                    try {
                                        Gson gson = new Gson();
                                        String jsonRequest = gson.toJson(openSourceValidationRequest);
                                        listener.onError(mapperRespond(respondOpenSourceValidation.errorBody(), jsonRequest));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        listener.onError(onGetError(e));
                                    }
                                }
                            }
                        } else {
                            try {
                                Gson gson = new Gson();
                                String jsonRequest = gson.toJson(respondOpenSourceValidation);
                                listener.onError(mapperRespond(respondOpenSourceValidation.errorBody(), jsonRequest));
                            } catch (IOException e) {
                                e.printStackTrace();
                                listener.onError(onGetError(e));
                            }
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onValidationReceipt(@NonNull @NotNull ValidateReceiptIn validateReceiptIn, OlimpiaInterface.CallbackValidationReceipt listener) {
        WebService.getInstance().getServiceOlimpiaApi().getValidateReceipt(validateReceiptIn)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<ValidateReceiptOut>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull Response<ValidateReceiptOut> respondValidateReceiptOut) {
                        if (respondValidateReceiptOut.isSuccessful()) {
                            if (respondValidateReceiptOut.body() != null) {
                                listener.onSuccess(respondValidateReceiptOut.body());
                            }
                        } else {
                            try {
                                if (respondValidateReceiptOut.errorBody() != null) {
                                    Gson gson = new Gson();
                                    String jsonRequest = gson.toJson(validateReceiptIn);
                                    listener.onError(mapperRespond(respondValidateReceiptOut.errorBody(), jsonRequest));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                listener.onError(onGetError(e));
                            }
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onValidationDriverLicense(@NonNull @NotNull ValidateDriverLicenseIn validateDriverLicenseIn, OlimpiaInterface.CallbackValidationDriverLicense listener) {
        WebService.getInstance().getServiceOlimpiaApi().getValidateDriverLicense(validateDriverLicenseIn)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<ValidateDriverLicenseOut>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull Response<ValidateDriverLicenseOut> respondValidateDriverLicenseOut) {
                        if (respondValidateDriverLicenseOut.isSuccessful()) {
                            if (respondValidateDriverLicenseOut.body() != null) {
                                listener.onSuccess(respondValidateDriverLicenseOut.body());
                            }
                        } else {
                            try {
                                if (respondValidateDriverLicenseOut.errorBody() != null) {
                                    Gson gson = new Gson();
                                    String jsonRequest = gson.toJson(validateDriverLicenseIn);
                                    listener.onError(mapperRespond(respondValidateDriverLicenseOut.errorBody(), jsonRequest));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                listener.onError(onGetError(e));
                            }
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onValidationFederalDriverLicense(@NonNull @NotNull ValidateFederalDriverLicenseIn validateFederalDriverLicenseIn, OlimpiaInterface.CallbackValidationFederalDriverLicense listener) {
        WebService.getInstance().getServiceOlimpiaApi().getValidateFederalDriverLicense(validateFederalDriverLicenseIn)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<ValidateFederalDriverLicenseOut>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull Response<ValidateFederalDriverLicenseOut> respondValidateFederalDriverLicenseOut) {
                        if (respondValidateFederalDriverLicenseOut.isSuccessful()) {
                            if (respondValidateFederalDriverLicenseOut.body() != null) {
                                listener.onSuccess(respondValidateFederalDriverLicenseOut.body());
                            }
                        } else {
                            try {
                                if (respondValidateFederalDriverLicenseOut.errorBody() != null) {
                                    Gson gson = new Gson();
                                    String jsonRequest = gson.toJson(validateFederalDriverLicenseIn);
                                    listener.onError(mapperRespond(respondValidateFederalDriverLicenseOut.errorBody(), jsonRequest));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                listener.onError(onGetError(e));
                            }
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onSaveTraceability(@NonNull @NotNull SaveTraceabilityProcessIn saveTraceabilityProcessIn, OlimpiaInterface.CallbackSaveTraceability listener) {
        WebService.getInstance().getServiceOlimpiaApi().getSaveTraceability(saveTraceabilityProcessIn)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<SaveTraceabilityOut>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull Response<SaveTraceabilityOut> saveTraceabilityOut) {
                        if (saveTraceabilityOut.isSuccessful()) {
                            if (saveTraceabilityOut.body() != null) {
                                listener.onSuccess(saveTraceabilityOut.body());
                            }
                        } else {
                            try {
                                if (saveTraceabilityOut.errorBody() != null) {
                                    Gson gson = new Gson();
                                    String jsonRequest = gson.toJson(saveTraceabilityProcessIn);
                                    listener.onError(mapperRespond(saveTraceabilityOut.errorBody(), jsonRequest));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                listener.onError(onGetError(e));
                            }
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onGetConsultSources(@NonNull @NotNull ConsultarFuenteIn consultarFuenteIn, OlimpiaInterface.CallbackConsultSource listener) {
        WebService.getInstance().getReconoserIdApi().validarConsultarFuentes(consultarFuenteIn)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<RespondConsultarFuente>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull Response<RespondConsultarFuente> respondConsultarFuente) {
                        if (respondConsultarFuente.isSuccessful()) {
                            if (respondConsultarFuente.body() != null) {
                                listener.onSuccess(respondConsultarFuente.body());
                            }
                        } else {
                            try {
                                if (respondConsultarFuente.errorBody() != null) {
                                    Gson gson = new Gson();
                                    String jsonRequest = gson.toJson(consultarFuenteIn);
                                    listener.onError(mapperRespond(respondConsultarFuente.errorBody(), jsonRequest));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                listener.onError(onGetError(e));
                            }
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onGetSaveLogBarcode(@NonNull @NotNull LogSaveBarcode logSaveBarcode, OlimpiaInterface.CallbackLogSaveBarcode listener) {
        WebService.getInstance().getServiceOlimpiaApi().getSaveLogBarcode(logSaveBarcode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<RespondLogMobileResult>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull Response<RespondLogMobileResult> respondLogMobileResult) {
                        if (respondLogMobileResult.isSuccessful()) {
                            if (respondLogMobileResult.body() != null) {
                                listener.onSuccess(respondLogMobileResult.body());
                            }
                        } else {
                            try {
                                if (respondLogMobileResult.errorBody() != null) {
                                    Gson gson = new Gson();
                                    String jsonRequest = gson.toJson(logSaveBarcode);
                                    listener.onError(mapperRespond(respondLogMobileResult.errorBody(), jsonRequest));
                                    ;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                listener.onError(onGetError(e));
                            }
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onGetSaveLogOCR(@NonNull @NotNull LogSaveOCR logSaveOCR, OlimpiaInterface.CallbackLogSaveOCR listener) {
        WebService.getInstance().getServiceOlimpiaApi().getSaveLogOCR(logSaveOCR)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<RespondLogMobileResult>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull Response<RespondLogMobileResult> respondLogMobileResult) {
                        if (respondLogMobileResult.isSuccessful()) {
                            if (respondLogMobileResult.body() != null) {
                                listener.onSuccess(respondLogMobileResult.body());
                            }
                        } else {
                            try {
                                if (respondLogMobileResult.errorBody() != null) {
                                    Gson gson = new Gson();
                                    String jsonRequest = gson.toJson(logSaveOCR);
                                    listener.onError(mapperRespond(respondLogMobileResult.errorBody(), jsonRequest));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                listener.onError(onGetError(e));
                            }
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onGetSaveMobileResult(@NonNull @NotNull LogMobileResult logMobileResult, OlimpiaInterface.CallbackLogMobileResult listener) {
        WebService.getInstance().getServiceOlimpiaApi().getSaveLogMobileResults(logMobileResult)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<RespondLogIdentity>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull Response<RespondLogIdentity> respondLogIdentity) {
                        if (respondLogIdentity.isSuccessful()) {
                            if (respondLogIdentity.body() != null) {
                                listener.onSuccess(respondLogIdentity.body());
                            }
                        } else {
                            try {
                                if (respondLogIdentity.errorBody() != null) {
                                    Gson gson = new Gson();
                                    String jsonRequest = gson.toJson(logMobileResult);
                                    listener.onError(mapperRespond(respondLogIdentity.errorBody(), jsonRequest));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                listener.onError(onGetError(e));
                            }
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onGetConsultAni(@NonNull @NotNull ConsultarAniIn consultarAniIn, OlimpiaInterface.CallbackConsultAni listener) {
        WebService.getInstance().getServiceOlimpiaApi().getConsultAni(consultarAniIn)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<ConsultarAniOut>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull Response<ConsultarAniOut> consultarAniOut) {
                        if (consultarAniOut.isSuccessful()) {
                            if (consultarAniOut.body() != null) {
                                listener.onSuccess(consultarAniOut.body());
                            }
                        } else {
                            try {
                                if (consultarAniOut.errorBody() != null) {
                                    Gson gson = new Gson();
                                    String jsonRequest = gson.toJson(consultarAniIn);
                                    listener.onError(mapperRespond(consultarAniOut.errorBody(), jsonRequest));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                listener.onError(onGetError(e));
                            }
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onRequestOpenSource(SolicitudFuentesAbiertasIn solicitudFuentesAbiertasIn, OlimpiaInterface.CallbackReconoserRequestOpenSource listener) {
        WebService.getInstance().getReconoserIdApi().solicitudFuentesAbiertas(solicitudFuentesAbiertasIn)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<SolicitudFuentesAbiertasOut>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<SolicitudFuentesAbiertasOut> solicitudFuentesAbiertasOut) {
                        if (solicitudFuentesAbiertasOut.isSuccessful()) {
                            listener.onSuccess(solicitudFuentesAbiertasOut.body());
                        } else {
                            try {
                                Gson gson = new Gson();
                                String jsonRequest = gson.toJson(solicitudFuentesAbiertasIn);
                                listener.onError(mapperRespond(solicitudFuentesAbiertasOut.errorBody(), jsonRequest));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onConsultOpenSource(ConsultarFuentesAbiertasIn consultarFuentesAbiertasIn, OlimpiaInterface.CallbackReconoserConsultOpenSource listener) {
        if (consultarFuentesAbiertasIn.getFuentesAbiertasGuid().equals(FALSE_GUID)) {
            listener.onError(getErrorCode(Constants.ERROR_102));
        } else {
            WebService.getInstance().getReconoserIdApi().consultarFuentesAbiertas(consultarFuentesAbiertasIn)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<Response<ConsultarFuentesAbiertasOut>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(Response<ConsultarFuentesAbiertasOut> consultarFuentesAbiertasOut) {
                            if (consultarFuentesAbiertasOut.isSuccessful()) {
                                listener.onSuccess(consultarFuentesAbiertasOut.body());
                            } else {
                                try {
                                    Gson gson = new Gson();
                                    String jsonRequest = gson.toJson(consultarFuentesAbiertasIn);
                                    listener.onError(mapperRespond(consultarFuentesAbiertasOut.errorBody(), jsonRequest));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            listener.onError(onGetError(e));
                        }
                    });
        }
    }

    @Override
    public void onListOpenSource(OlimpiaInterface.CallbackReconoserListOpenSource listener) {
        WebService.getInstance().getReconoserIdApi().obtenerParametroFuente()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<ParametroFuenteOut>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<ParametroFuenteOut> parametroFuenteOut) {
                        if (parametroFuenteOut.isSuccessful()) {
                            listener.onSuccess(parametroFuenteOut.body());
                        } else {
                            try {
                                String jsonRequest = "ParametroFuente";
                                listener.onError(mapperRespond(parametroFuenteOut.errorBody(), jsonRequest));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onGetRequestValidation(@NonNull @NotNull RequestValidationIn requestValidationIn, OlimpiaInterface.CallbackRequestValidation listener) {
        WebService.getInstance().getReconoserIdApi().solicitudValidacion(requestValidationIn)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<RequestValidationOut>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull Response<RequestValidationOut> requestValidationOut) {
                        if (requestValidationOut.isSuccessful()) {
                            RequestValidationOut fixRequestValidationOut = requestValidationOut.body();
                            if(fixRequestValidationOut != null && fixRequestValidationOut.getData() != null ){
                                if( fixRequestValidationOut.getData().getProcesoConvenioGuid() == null){
                                    fixRequestValidationOut.getData().setProcesoConvenioGuid(StringUtils.isNullValues(fixRequestValidationOut.getData().getProcesoConvenioGuid()));
                                }
                                if( fixRequestValidationOut.getData().getUrl() == null){
                                    fixRequestValidationOut.getData().setUrl(StringUtils.isNullValues(fixRequestValidationOut.getData().getUrl()));
                                }
                                if( fixRequestValidationOut.getData().getGuidCiudadano() == null){
                                    fixRequestValidationOut.getData().setGuidCiudadano(StringUtils.isNullValues(fixRequestValidationOut.getData().getGuidCiudadano()));
                                }
                            }
                            listener.onSuccess(fixRequestValidationOut);
                        } else {
                            try {
                                Gson gson = new Gson();
                                String jsonRequest = gson.toJson(requestValidationIn);
                                listener.onError(mapperRespond(requestValidationOut.errorBody(), jsonRequest));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onGetConsultSteps(@NonNull @NotNull ConsultStepsIn consultStepsIn, OlimpiaInterface.CallbackConsultSteps listener) {
        WebService.getInstance().getServiceOlimpiaApi().consultarPasos(consultStepsIn)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<ConsultStepsOut>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull Response<ConsultStepsOut> consultStepsOut) {
                        if (consultStepsOut.isSuccessful()) {
                            listener.onSuccess(consultStepsOut.body());
                        } else {
                            try {
                                Gson gson = new Gson();
                                String jsonRequest = gson.toJson(consultStepsIn);
                                listener.onError(mapperRespond(Objects.requireNonNull(consultStepsOut.errorBody()), jsonRequest));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onGetConsultValidation(@NonNull @NotNull ConsultValidationIn consultValidationIn, OlimpiaInterface.CallbackConsultValidation listener) {
        WebService.getInstance().getReconoserIdApi().consultarValidacion(consultValidationIn)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<ConsultValidationOut>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull Response<ConsultValidationOut> consultValidationOut) {
                        if (consultValidationOut.isSuccessful()) {
                            listener.onSuccess(consultValidationOut.body());
                        } else {
                            try {
                                Gson gson = new Gson();
                                String jsonRequest = gson.toJson(consultValidationIn);
                                listener.onError(mapperRespond(consultValidationOut.errorBody(), jsonRequest));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onGetSaveDocumentSides(@NonNull @NotNull SaveDocumentSidesIn saveDocumentSidesIn, OlimpiaInterface.CallbackSaveDocumentSides listener) {

        //To save easily the traceability
        Trazabilidad trazabilidad = new Trazabilidad();
        trazabilidad.setDevice("Android");
        trazabilidad.setIp(PublicIpAddress.INSTANCE.getPublicIpAddress());
        trazabilidad.setNavegador("");
        trazabilidad.setResolucion(Miscellaneous.INSTANCE.getScreenResolution());
        trazabilidad.setTiempo(0);
        trazabilidad.setUserAgent("");
        trazabilidad.setVersionNavegador("");
        Timber.e("com.reconosersdk.reconosersdk.http.aceptarATDP.`in`.Trazabilidad GDAC: %s", trazabilidad.toString());

        //Avoid null into logOCRBarcode class
        if (saveDocumentSidesIn.getLogsOcrbarcode() == null) {
            //set configuration
            List<String> forceRead = new ArrayList<>();
            forceRead.add(Constants.FORCE_DOCUMENT_READING);
            saveDocumentSidesIn.setConfiguracionEspecial(forceRead);
        } else {
            //set empty List
            saveDocumentSidesIn.setConfiguracionEspecial(java.util.Collections.emptyList());
        }

        saveDocumentSidesIn.setTrazabilidad(trazabilidad);

        //TODO Solo para pruebas
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String jsonRequest = gson.toJson(saveDocumentSidesIn);
        String showText = "\n"+ "Guardar documento ambas caras Json request:  " + jsonRequest + "\n";
        //TODO Solo para pruebas
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            StringUtils.appendLog("*** Texto a visualizar del servicio Guardar documento ambas caras *** " + "\n"+ LocalDateTime.now() + "\n"+ showText + " *** Fin del texto *** ", false);
        }*/


        //To validate any error into requestBody
        RequestBody body = JsonUtils.requestObject(saveDocumentSidesIn);

        WebService.getInstance().getServiceOlimpiaApi().guardarDocumentoAmbasCaras(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<SaveDocumentSidesOut>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull Response<SaveDocumentSidesOut> saveDocumentSidesOut) {
                        if (saveDocumentSidesOut.isSuccessful()) {
                            listener.onSuccess(saveDocumentSidesOut.body());
                        } else {
                            try {
                                Gson gson = new Gson();
                                String jsonRequest = gson.toJson(saveDocumentSidesIn);
                                listener.onError(mapperRespond(saveDocumentSidesOut.errorBody(), jsonRequest));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onConsultAgreementProcess(@NotNull String procesoConvenioGuid, OlimpiaInterface.CallbackConsultAgreementProcess listener) {
        WebService.getInstance().getServiceOlimpiaApi().consultarProcesoConvenio(procesoConvenioGuid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<ConsultAgreementProcessOut>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<ConsultAgreementProcessOut> consultAgreementProcessOut) {
                        if (consultAgreementProcessOut.isSuccessful()) {
                            listener.onSuccess(consultAgreementProcessOut.body());
                        } else {
                            try {
                                String jsonRequest = procesoConvenioGuid;
                                listener.onError(mapperRespond(consultAgreementProcessOut.errorBody(), jsonRequest));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onGetLogin(@NonNull @NotNull LoginIn loginIn, OlimpiaInterface.CallbackLogin listener) {

        WebService.getInstance().getServiceOlimpiaApi().login(loginIn)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<LoginOut>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull Response<LoginOut> LoginOut) {
                        if (LoginOut.isSuccessful()) {
                            listener.onSuccess(LoginOut.body());
                        } else {
                            try {
                                Gson gson = new Gson();
                                String jsonRequest = gson.toJson(loginIn);
                                listener.onError(mapperRespond(LoginOut.errorBody(), jsonRequest));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });

    }

    @Override
    public void onGetObtainDataEasyTrack(@NonNull @NotNull ObtainDataEasyTrackIn obtainDataEasyTrackIn, OlimpiaInterface.CallbackObtainDataEasyTrack listener) {

        Log.e("verify easytrack", obtainDataEasyTrackIn.toString());
        WebService.getInstance().getServiceOlimpiaApi().obtainDataEasyTrack(obtainDataEasyTrackIn.getEmail())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<ObtainDataEasyTrackOut>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull Response<ObtainDataEasyTrackOut> obtainDataEasyTrackOut) {
                        if (obtainDataEasyTrackOut.isSuccessful()) {
                            listener.onSuccess(obtainDataEasyTrackOut.body());
                        } else {
                            try {
                                Gson gson = new Gson();
                                String jsonRequest = gson.toJson(obtainDataEasyTrackIn);
                                listener.onError(mapperRespond(obtainDataEasyTrackOut.errorBody(), jsonRequest));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onAcceptATDP(@NonNull AceptarAtdpIn aceptarAtdpIn, OlimpiaInterface.CallbackAceptarAtdp listener) {
        if (aceptarAtdpIn.getTrazabilidad() == null) {
            com.reconosersdk.reconosersdk.http.aceptarATDP.in.Trazabilidad trazabilidad = new com.reconosersdk.reconosersdk.http.aceptarATDP.in.Trazabilidad();
            aceptarAtdpIn.setTrazabilidad(trazabilidad);
        }

        Timber.e("verify Accept ATDP: %s", aceptarAtdpIn.toString());
        WebService.getInstance().getServiceOlimpiaApi().aceptarATDP(aceptarAtdpIn)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<Void>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<Void> voidResponse) {
                        if (voidResponse.isSuccessful()) {
                            listener.onSuccess(new AceptarAtdpOut());
                            //listener.onSuccess(voidResponse.body());
                        } else {
                            try {
                                Gson gson = new Gson();
                                String jsonRequest = gson.toJson(voidResponse);
                                listener.onError(mapperRespond(voidResponse.errorBody(), jsonRequest));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }
                });
    }

    @Override
    public void onSendOTPAuthID(@NonNull EnviarOTPAuthIDIn enviarOTPAuthIDIn, OlimpiaInterface.CallbackEnviarOTPAuthID listener) {
        if (!PreferencesSDK.getInstance().getStatus()) {
            listener.onError(getErrorConvenio());
            return;
        }
        if (!isValidServiceOTP(enviarOTPAuthIDIn.getDatosOTP())) {
            listener.onError(getErrorService());
            return;
        }

        String dataOTP = JsonUtils.stringObject(Objects.requireNonNull(enviarOTPAuthIDIn.getDatosOTP()));
        RequestBody body = JsonUtils.requestBody(ConstantsOlimpia.NUMERO_TELEFONO, Objects.requireNonNull(enviarOTPAuthIDIn.getNumeroTelefono()),
                ConstantsOlimpia.CORREO, Objects.requireNonNull(enviarOTPAuthIDIn.getCorreo()), ConstantsOlimpia.GUID_CONVENIO, Objects.requireNonNull(enviarOTPAuthIDIn.getGuidConvenio()),
                ConstantsOlimpia.DATOS_OTP, dataOTP, ConstantsOlimpia.PROVEEDOR, Objects.requireNonNull(enviarOTPAuthIDIn.getProveedor()).toString() );

        WebService.getInstance().getServiceOlimpiaApi().enviarOTPAuthID(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<EnviarOTPAuthIDOut>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<EnviarOTPAuthIDOut> enviarOTPAuthIDOut) {
                        if (enviarOTPAuthIDOut.isSuccessful()) {
                            if (Objects.requireNonNull(Objects.requireNonNull(enviarOTPAuthIDOut.body()).getRespuestaTransaccion()).isEsExitosa()) {
                                listener.onSuccess(enviarOTPAuthIDOut.body());
                            } else {
                                listener.onError(enviarOTPAuthIDOut.body().getRespuestaTransaccion());
                            }
                        } else {
                            try {
                                Gson gson = new Gson();
                                String jsonRequest = gson.toJson(enviarOTPAuthIDOut);
                                listener.onError(mapperRespond(enviarOTPAuthIDOut.errorBody(), jsonRequest));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(onGetError(e));
                    }

                });
    }

    private RespuestaTransaccion mapperRespond(ResponseBody errorBody, String jsonRequest) throws IOException {
        if (errorBody == null) {
            ErrorEntransaccion errorEntransaccion = new ErrorEntransaccion();
            errorEntransaccion.setCodigo(String.valueOf(400));
            String jsonDescriptionString = "Bad Request, responseBody empty " + jsonRequest;
            errorEntransaccion.setDescripcion(jsonDescriptionString);

            List<ErrorEntransaccion> list = new ArrayList<>();
            list.add(errorEntransaccion);

            RespuestaTransaccion transaccion = new RespuestaTransaccion();
            transaccion.setEsExitosa(false);

            transaccion.setErrorEntransaccion(list);
            return transaccion;
        } else {
            try {
                String error = errorBody.string();
                Gson gson = new Gson();
                DataError dataError = gson.fromJson(error, DataError.class);
                ErrorEntransaccion errorEntransaccion = new ErrorEntransaccion();
                errorEntransaccion.setCodigo(String.valueOf(400));
                String jsonDescriptionError = "Bad Request, error request \n" + dataError.getData().getMesage() + "\nConcat request:\n" + jsonRequest;
                errorEntransaccion.setDescripcion(jsonDescriptionError);

                List<ErrorEntransaccion> list = new ArrayList<>();
                list.add(errorEntransaccion);

                RespuestaTransaccion transaccion = new RespuestaTransaccion();
                transaccion.setEsExitosa(false);

                transaccion.setErrorEntransaccion(list);
                return transaccion;
            } catch (Exception e) {
                ErrorEntransaccion errorEntransaccion = new ErrorEntransaccion();
                errorEntransaccion.setCodigo(String.valueOf(400));
                String jsonDescriptionError = "Bad Request, code or description null into error \n" + e.getMessage() + "\nConcat request:\n" + jsonRequest;
                errorEntransaccion.setDescripcion(jsonDescriptionError);

                List<ErrorEntransaccion> list = new ArrayList<>();
                list.add(errorEntransaccion);

                RespuestaTransaccion transaccion = new RespuestaTransaccion();
                transaccion.setEsExitosa(false);

                transaccion.setErrorEntransaccion(list);
                return transaccion;
            }

        }
    }

    @Override
    public RespuestaTransaccion onGetError(Throwable e) {
        RespuestaTransaccion error = new RespuestaTransaccion();
        error.setEsExitosa(false);
        ErrorEntransaccion errorEntransaccion = new ErrorEntransaccion();
        switch (e.getMessage()) {
            case Constants.ERROR_SERVER:
                errorEntransaccion.setCodigo(Constants.ERROR_R103);
                errorEntransaccion.setDescripcion(Constants.ERROR_SERVER);
                break;
            default:
                errorEntransaccion.setCodigo(Constants.ERROR_R100);
                errorEntransaccion.setDescripcion(Constants.ERROR_HOST);
                break;
        }

        List<ErrorEntransaccion> list = new ArrayList<>();
        list.add(errorEntransaccion);
        error.setErrorEntransaccion(list);
        return error;
    }

    public static RespuestaTransaccion setErrorCustom(String code, String msg) {
        RespuestaTransaccion error = new RespuestaTransaccion();
        error.setEsExitosa(false);
        ErrorEntransaccion errorEntransaccion = new ErrorEntransaccion();
        errorEntransaccion.setCodigo(code);
        errorEntransaccion.setDescripcion(msg);
        List<ErrorEntransaccion> list = new ArrayList<>();
        list.add(errorEntransaccion);
        error.setErrorEntransaccion(list);
        return error;
    }

    @Override
    public RespuestaTransaccion getErrorConvenio() {
        return setErrorCustom(Constants.ERROR_R101, Constants.ERROR_CONV);
    }

    @Override
    public RespuestaTransaccion getErrorService() {
        return setErrorCustom(Constants.ERROR_R102, Constants.ERROR_SERVICE_API);
    }

    @Override
    public RespuestaTransaccion getErrorCustom(@NonNull String code, @NonNull String description) {
        return setErrorCustom(code, description);
    }

    @Override
    public RespuestaTransaccion getErrorCode(@NonNull String code) {
        switch (code) {
            case Constants.ERROR_R99:
                return setErrorCustom(Constants.ERROR_R99, Constants.ERROR_NO_INIT);
            case Constants.ERROR_R101:
                return setErrorCustom(Constants.ERROR_R101, Constants.ERROR_CONV);
            case Constants.ERROR_R102:
                return setErrorCustom(Constants.ERROR_R102, Constants.ERROR_SERVICE_API);
            case Constants.ERROR_R103:
                return setErrorCustom(Constants.ERROR_R103, Constants.ERROR_SERVER);
            case Constants.ERROR_R104:
                return setErrorCustom(Constants.ERROR_R104, Constants.ERROR_IMAGE_LOAD);
            case Constants.ERROR_R105:
                return setErrorCustom(Constants.ERROR_R105, Constants.ERROR_IMAGE_SAVE);
            case Constants.ERROR_R106:
                return setErrorCustom(Constants.ERROR_R106, Constants.ERROR_IMAGE_PROCESS);
            case Constants.ERROR_R107:
                return setErrorCustom(Constants.ERROR_R107, Constants.ERROR_NO_PARAM);
            case Constants.ERROR_R108:
                return setErrorCustom(Constants.ERROR_R108, Constants.ERROR_NO_PARAM_GUICIU);
            case Constants.ERROR_R109:
                return setErrorCustom(Constants.ERROR_R109, Constants.ERROR_NO_PARAM_TYPE);
            case Constants.ERROR_R110:
                return setErrorCustom(Constants.ERROR_R110, Constants.ERROR_NO_PARAM_NUM);
            case Constants.ERROR_R111:
                return setErrorCustom(Constants.ERROR_R111, Constants.ERROR_PARAM_NUM);
            case Constants.ERROR_102:
                return setErrorCustom(Constants.ERROR_102, Constants.ERROR_NO_VALID);
            case Constants.ERROR_R116:
                return setErrorCustom(Constants.ERROR_R116, Constants.ERROR_TIMEOUT);
            case Constants.ERROR_R404:
                return setErrorCustom(Constants.ERROR_R404, Constants.ERROR_NO_FOUND_PARAM);
            case Constants.ERROR_R114:
                return setErrorCustom(Constants.ERROR_R114, Constants.ERROR_DOCUMENT_NOT_SIMILITUD);
            case Constants.ERROR_R115:
                return setErrorCustom(Constants.ERROR_R115, Constants.ERROR_DOCUMENT_NOT_BARCODE);
            case Constants.ERROR_R117:
                return setErrorCustom(Constants.ERROR_R117, Constants.ERROR_NO_FOUND);
            case Constants.ERROR_R118:
                return setErrorCustom(Constants.ERROR_R118, Constants.ERROR_REQUEST_MEX);
            case Constants.ERROR_R119:
                return setErrorCustom(Constants.ERROR_R119, Constants.ERROR_NOT_READ_BARCODE);
            case Constants.ERROR_R120:
                return setErrorCustom(Constants.ERROR_R120, Constants.ERROR_NOT_READ_DATE);
            case Constants.ERROR_R121:
                return setErrorCustom(Constants.ERROR_R121, Constants.ERROR_DOCUMENT_VALIDATION);
            default:
                return setErrorCustom(Constants.ERROR_R100, Constants.ERROR_HOST);
        }
    }
}
