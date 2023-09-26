package com.reconosersdk.reconosersdk.http;

import androidx.annotation.NonNull;

import com.reconosersdk.reconosersdk.http.aceptarATDP.in.AceptarAtdpIn;
import com.reconosersdk.reconosersdk.http.aceptarATDP.out.AceptarAtdpOut;
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
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ConsultarFuenteIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.OpenSourceValidationRequest;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.CancelarProceso;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.CiudadanoIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ConsultarEstadoProceso;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ConsultarProceso;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.DataComparisonFace;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.DataValidateFace;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.EnviarOTPIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.GuardarBiometriaIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.GuardarDocumentoIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.GuardarLogErrorIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ObtenerToken;
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
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.EnviarOTP;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ErrorEntransaccion;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.GuardarBiometria;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.GuardarCiudadano;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.GuardarLogError;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.HeaderToken;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.MotivosCanceladoOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ProcesosPendientesOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespondSearchBiometry;
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
import com.reconosersdk.reconosersdk.http.saveDocumentSides.out.SaveDocumentSidesOut;
import com.reconosersdk.reconosersdk.http.validateine.in.ValidateIneIn;
import com.reconosersdk.reconosersdk.http.validateine.out.ValidateIneOutX;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface OlimpiaInterface {

    interface OlimpiaApiInterface {

        //To get the token
        void onGetToken(@NonNull ObtenerToken obtenerToken, CallbackGetToken callbackGetToken);

        //To consult the agreement for the first time
        void initService(@NonNull String guidConv, @NonNull String datos);

        ////Associated services to consult different process
        //To auth
        void onAuthUser(@NonNull String adviser, @NonNull String codeAdviser, CallbackAuthentication listener);

        //To get the campus
        void onSedeConvenio(@NonNull String guidConv, CallbackSedeConvenio listener);

        //To get the canceled reason
        void onMotivosCancelado(@NonNull String guidConv, CallbackMotivosCancelado listener);

        //To get the pending process
        void onProcesosPendientes(@NonNull ProcesosPendientes procesosPendientes, CallbackProcesosPendientes listener);

        //To cancel the process
        void onCancelarProceso(@NonNull CancelarProceso cancelarProceso, CallbackCancelarProceso listener);

        //To consult the state process
        void onConsultarEstadoProceso(@NonNull ConsultarEstadoProceso consultarEstadoProceso, CallbackConsultarEstadoProceso listener);

        //To process request
        void onSolicitudProceso(@NonNull SolicitudProceso solicitudProceso, CallbackSolicitudProceso listener);

        //To consult the process
        void onConsultarProceso(@NonNull ConsultarProceso consultarProceso, CallbackConsultarProceso listener);

        //To consult the agreement
        void onGetAgreement(@NonNull String guidConv, @NonNull String datos, CallbackConsultAgreement listener);

        //To save the citizen
        void onSaveResident(@NonNull CiudadanoIn guardarCiudadanoIn, CallbackSaveResident listener);

        //To save the biometry
        void onSaveBiometry(@NonNull GuardarBiometriaIn guardarBiometriaIn, CallbackSaveBiometry listener);

        //To validate the biometry
        void onValidateBiometry(@NonNull ValidarBiometriaIn validarBiometriaIn, CallbackValidateBiometry listener);

        //To save the document
        void onSaveDocument(@NonNull GuardarDocumentoIn guardarDocumentoIn, CallbackSaveDocument listener);

        //To send the OTP
        void onSendOTP(@NonNull EnviarOTPIn enviarOTPIn, CallbackSendOTP listener);

        //To validate the OTP
        void onValidateOTP(@NonNull String guidOTP, @NonNull String oTP, CallbackValidateOTP listener);

        //To request the questions
        void onRequestQuestions(@NonNull String giudCiudadano, CallbackRequestQuestions listener);

        //To validate the questions
        void onValidateResponse(@NonNull ValidarRespuestaIn validarRespuestaDemografica, CallbackValidateResponse listener);

        //To consult the citizen
        void onConsultResident(@NonNull String guidCiudadano, CallbackConsultResident listener);

        //To save tho log error
        void onSaveLogError(@NonNull GuardarLogErrorIn guardarLogErrorIn, CallbackSaveLogError listener);

        void onSearchUser(@NotNull SearchUser searchUser, CallbackSearchUser listener);

        void onSearchForDocument(@NotNull SearchForDocument searchForDocument, CallbackSearchForDocument listener);

        void onComparasionFace(@NonNull DataComparisonFace dataComparisonFace, CallbackReconoserComparasion listener);

        void onValidateFaceBD(@NonNull DataValidateFace dataValidateFace, CallbackReconoserValidate listener);

        void onFinishProcess(@NotNull String idProcess, @NotNull boolean state, CallbackFinishProcess listener);

        void onGetBiometry64(@NotNull SearchBiometry searchBiometry, CallbackGetBiometry64 listener);

        void onValidationOpenSourceDocument(@NonNull OpenSourceValidationRequest openSourceValidationRequest, CallbackValidationOpenSourceDocument listener);

        void onValidationReceipt(@NonNull ValidateReceiptIn validateReceiptIn, CallbackValidationReceipt listener);

        void onValidationDriverLicense(@NonNull ValidateDriverLicenseIn validateDriverLicenseIn, CallbackValidationDriverLicense listener);

        void onValidationFederalDriverLicense(@NonNull ValidateFederalDriverLicenseIn validateFederalDriverLicenseIn, CallbackValidationFederalDriverLicense listener);

        void onSaveTraceability(@NonNull SaveTraceabilityProcessIn saveTraceabilityProcessIn, CallbackSaveTraceability listener);

        void onGetConsultSources(@NonNull ConsultarFuenteIn consultarFuenteIn, CallbackConsultSource listener);

        void onGetSaveLogBarcode(@NonNull LogSaveBarcode logSaveBarcode, CallbackLogSaveBarcode listener);

        void onGetSaveLogOCR(@NonNull LogSaveOCR logSaveOCR, CallbackLogSaveOCR listener);

        void onGetSaveMobileResult(@NonNull LogMobileResult logMobileResult, CallbackLogMobileResult listener);

        void onGetConsultAni(@NonNull ConsultarAniIn consultarAniIn, CallbackConsultAni listener);

        boolean isServiceDocAnverso();

        boolean isServiceDocReverso();

        boolean isServiceBiometryFace();

        boolean isServiceBarcode();

        boolean isServiceSms();

        boolean isServiceEmail();

        //To validate INE
        void onValidateMexicanDocument(@NonNull ValidateIneIn validateIneIn, CallbackValidateIne listener);

        RespuestaTransaccion onGetError(Throwable e);

        RespuestaTransaccion getErrorConvenio();

        RespuestaTransaccion getErrorService();

        RespuestaTransaccion getErrorCustom(@NonNull String code, @NonNull String description);

        RespuestaTransaccion getErrorCode(@NonNull String code);

        void onRequestOpenSource(SolicitudFuentesAbiertasIn solicitudFuentesAbiertasIn, CallbackReconoserRequestOpenSource listener);

        void onConsultOpenSource(ConsultarFuentesAbiertasIn consultarFuentesAbiertasIn, CallbackReconoserConsultOpenSource listener);

        void onListOpenSource(CallbackReconoserListOpenSource listener);

        //To request the validations
        void onGetRequestValidation(@NonNull RequestValidationIn requestValidationIn, CallbackRequestValidation listener);
        //To consult the steps
        void onGetConsultSteps(@NonNull ConsultStepsIn consultStepsIn, CallbackConsultSteps listener);

        //To consult the validations
        void onGetConsultValidation(@NonNull ConsultValidationIn consultValidationIn, CallbackConsultValidation listener);

        //To consult the save document sides
        void onGetSaveDocumentSides(@NonNull SaveDocumentSidesIn saveDocumentSidesIn, CallbackSaveDocumentSides listener);

        //To consult the agreement process
        void onConsultAgreementProcess(@NotNull String procesoConvenioGuid, CallbackConsultAgreementProcess listener);

        //To consult the login
        void onGetLogin(@NonNull LoginIn loginIn, CallbackLogin listener);

        //To consult the obtainDataEasyTrack
        void onGetObtainDataEasyTrack(@NonNull ObtainDataEasyTrackIn obtainDataEasyTrackIn, CallbackObtainDataEasyTrack listener);

        //To accept ATDP
        void onAcceptATDP(@NonNull AceptarAtdpIn aceptarAtdpIn, CallbackAceptarAtdp callbackAceptarAtdp);

        //To get the token
        void onSendOTPAuthID(@NonNull EnviarOTPAuthIDIn enviarOTPAuthIDIn, CallbackEnviarOTPAuthID callbackEnviarOTPAuthID);
    }

    //To get the token
    interface CallbackGetToken {
        void onSuccess(HeaderToken headerToken);

        void onError(HeaderToken headerToken);
    }

    //To consult the agreement
    interface CallbackConsultAgreement {
        void onSuccess(boolean estado, ConsultarConvenio serviciosConv);

        void onError(boolean estado, RespuestaTransaccion transactionResponse);
    }

    //To auth
    interface CallbackAuthentication {
        void onSuccess(AutenticarAsesorOut autenticarAsesorOut);

        void onError(RespuestaTransaccion respuestaTransaccion);
    }

    //To get the campus
    interface CallbackSedeConvenio {
        void onSuccess(SedeConvenioOut sedeConvenioOut);

        void onError(RespuestaTransaccion respuestaTransaccion);
    }

    //To get the canceled reason
    interface CallbackMotivosCancelado {
        void onSuccess(MotivosCanceladoOut motivosCanceladoOut);

        void onError(RespuestaTransaccion respuestaTransaccion);
    }

    //To get the pending process
    interface CallbackProcesosPendientes {
        void onSuccess(ProcesosPendientesOut procesosPendientesOut);

        void onError(RespuestaTransaccion respuestaTransaccion);
    }

    //To cancel the process
    interface CallbackCancelarProceso {
        void onSuccess(CancelarProcesoOut cancelarProcesoOut);

        void onError(RespuestaTransaccion respuestaTransaccion);
    }

    //To consult the state process
    interface CallbackConsultarEstadoProceso {
        void onSuccess(ConsultarEstadoProcesoOut consultarEstadoProcesoOut);

        void onError(RespuestaTransaccion respuestaTransaccion);
    }

    //To process request
    interface CallbackSolicitudProceso {
        void onSuccess(SolicitudProcesoOut solicitudProcesoOut);

        void onError(RespuestaTransaccion respuestaTransaccion);
    }

    //To consult the process
    interface CallbackConsultarProceso {
        void onSuccess(ConsultarProcesoOut consultarProcesoOut);

        void onError(RespuestaTransaccion respuestaTransaccion);
    }

    //To save the citizen
    interface CallbackSaveResident {
        void onSuccess(GuardarCiudadano saveResident);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To save the biometry
    interface CallbackSaveBiometry {
        void onSuccess(GuardarBiometria saveBiometry);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To validate the biometry
    interface CallbackValidateBiometry {
        void onSuccess(ValidarBiometria validateBiometry);

        void onError(RespuestaTransaccion transactionResponse, int intentos);
    }

    //To save the document
    interface CallbackSaveDocument {
        void onSuccess(BarcodeDocument barcodeDocument);

        void onError(List<ErrorEntransaccion> transactionResponse);
    }

    //To send the OTP
    interface CallbackSendOTP {
        void onSuccess(EnviarOTP sendOTP);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To validate the OTP
    interface CallbackValidateOTP {
        void onSuccess(ValidarOTP validarOTP);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To request the questions
    interface CallbackRequestQuestions {
        void onSuccess(SolicitarPreguntasDemograficas requestQuestions);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To validate the questions
    interface CallbackValidateResponse {
        void onSuccess(ValidarRespuestaDemografica validateResponse);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To consult the citizen
    interface CallbackConsultResident {
        void onSuccess(ConsultarCiudadano consultResident);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To save tho log error
    interface CallbackSaveLogError {
        void onSuccess(GuardarLogError saveLogError);

        void onError(List<ErrorEntransaccion> transactionResponse);
    }

    //To found the citizen
    interface CallbackSearchUser {
        void onSuccess(UserFound userFound);

        void onError(RespuestaTransaccion transactionResponse);
    }

    interface CallbackSearchForDocument {
        void onSuccess(RespondSearchDocument respondSearchDocument);

        void onError(RespuestaTransaccion transactionResponse);
    }

    interface CallbackReconoserComparasion {
        void onSuccess(RespondComparasionFace respondComparasionFace);

        void onError(RespuestaTransaccion transactionResponse);
    }

    interface CallbackReconoserValidate {
        void onSuccess(RespondValidateFace respondValidateFace);

        void onError(RespuestaTransaccion transactionResponse);
    }

    interface CallbackValidateIne {
        void onSuccess(ValidateIneOutX validateIneOutX);

        void onError(RespuestaTransaccion transactionResponse);
    }

    interface CallbackFinishProcess {
        void onSuccess(boolean esExitosa);

        void onError(RespuestaTransaccion transactionResponse);
    }

    interface CallbackGetBiometry64 {
        void onSuccess(RespondSearchBiometry respondSearchBiometry);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To request the open sources
    interface CallbackReconoserRequestOpenSource {
        void onSuccess(SolicitudFuentesAbiertasOut solicitudFuentesAbiertasOut);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To consult the open sources
    interface CallbackReconoserConsultOpenSource {
        void onSuccess(ConsultarFuentesAbiertasOut consultarFuentesAbiertasOut);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To consult the open sources
    interface CallbackReconoserListOpenSource {
        void onSuccess(ParametroFuenteOut parametroFuenteOut);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To validate generic document
    interface CallbackValidationOpenSourceDocument {
        void onSuccess(RespondOpenSourceValidation respondOpenSourceValidation);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To validate mexican receipt
    interface CallbackValidationReceipt {
        void onSuccess(ValidateReceiptOut validateReceiptOut);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To validate mexican driver license
    interface CallbackValidationDriverLicense {
        void onSuccess(ValidateDriverLicenseOut validateDriverLicenseOut);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To validate mexican federal driver license
    interface CallbackValidationFederalDriverLicense {
        void onSuccess(ValidateFederalDriverLicenseOut validateFederalDriverLicenseOut);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To validate mexican receipt
    interface CallbackSaveTraceability {
        void onSuccess(SaveTraceabilityOut saveTraceabilityOut);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To consult image source
    interface CallbackConsultSource {
        void onSuccess(RespondConsultarFuente respondValidateFace);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To save barcode data
    interface CallbackLogSaveBarcode {
        void onSuccess(RespondLogMobileResult respondLogMobileResult);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To save OCR data
    interface CallbackLogSaveOCR {
        void onSuccess(RespondLogMobileResult respondLogMobileResult);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To save barcode data
    interface CallbackLogMobileResult {
        void onSuccess(RespondLogIdentity respondLogIdentity);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To save barcode data
    interface CallbackConsultAni {
        void onSuccess(ConsultarAniOut consultarAniOut);

        void onError(RespuestaTransaccion transactionResponse);
    }

    //To request the validation
    interface CallbackRequestValidation {
        void onSuccess(RequestValidationOut requestValidationOut);
        void onError(RespuestaTransaccion transactionResponse);
    }

    //To request the questions
    interface CallbackConsultSteps {
        void onSuccess(ConsultStepsOut consultStepsOut);
        void onError(RespuestaTransaccion transactionResponse);
    }

    //To consult the validation
    interface CallbackConsultValidation {
        void onSuccess(ConsultValidationOut consultValidationOut);
        void onError(RespuestaTransaccion transactionResponse);
    }

    //To consult the save document sides
    interface CallbackSaveDocumentSides {
        void onSuccess(SaveDocumentSidesOut saveDocumentSidesOut);
        void onError(RespuestaTransaccion transactionResponse);
    }

    //To consult the open sources
    interface CallbackConsultAgreementProcess{
        void onSuccess(ConsultAgreementProcessOut consultAgreementProcessOut);
        void onError(RespuestaTransaccion transactionResponse);
    }


    //To consult the login
    interface CallbackLogin {
        void onSuccess(LoginOut loginOut);
        void onError(RespuestaTransaccion transactionResponse);
    }

    //To consult the obtain data easytrack
    interface CallbackObtainDataEasyTrack {
        void onSuccess(ObtainDataEasyTrackOut obtainDataEasyTrackOut);
        void onError(RespuestaTransaccion transactionResponse);
    }

    interface CallbackAceptarAtdp{
        void onSuccess(AceptarAtdpOut aceptarAtdpOut);
        void onError(RespuestaTransaccion transactionResponse);
    }

    interface CallbackEnviarOTPAuthID{
        void onSuccess(EnviarOTPAuthIDOut enviarOTPAuthIDOut);
        void onError(RespuestaTransaccion transactionResponse);
    }

}
