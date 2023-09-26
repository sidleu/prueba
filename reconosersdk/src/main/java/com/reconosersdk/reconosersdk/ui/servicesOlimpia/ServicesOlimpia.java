package com.reconosersdk.reconosersdk.ui.servicesOlimpia;

import androidx.annotation.NonNull;
import com.reconosersdk.reconosersdk.http.OlimpiaInterface;
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
import com.reconosersdk.reconosersdk.http.OlimpiaService;
import com.reconosersdk.reconosersdk.http.aceptarATDP.in.AceptarAtdpIn;
import com.reconosersdk.reconosersdk.http.consultSteps.in.ConsultStepsIn;
import com.reconosersdk.reconosersdk.http.consultValidation.in.ConsultValidationIn;
import com.reconosersdk.reconosersdk.http.enviarOTPAuthID.in.EnviarOTPAuthIDIn;
import com.reconosersdk.reconosersdk.http.login.in.LoginIn;
import com.reconosersdk.reconosersdk.http.obtainDataEasyTrack.in.ObtainDataEasyTrackIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.CancelarProceso;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.CiudadanoIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ConsultarEstadoProceso;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ConsultarFuenteIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ConsultarProceso;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.DataComparisonFace;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.DataValidateFace;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.EnviarOTPIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.GuardarBiometriaIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.GuardarDocumentoIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.GuardarLogErrorIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ObtenerToken;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.OpenSourceValidationRequest;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ProcesosPendientes;
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
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion;
import com.reconosersdk.reconosersdk.http.openSource.in.ConsultarFuentesAbiertasIn;
import com.reconosersdk.reconosersdk.http.openSource.in.SolicitudFuentesAbiertasIn;
import com.reconosersdk.reconosersdk.http.requestValidation.in.RequestValidationIn;
import com.reconosersdk.reconosersdk.http.saveDocumentSides.in.SaveDocumentSidesIn;
import com.reconosersdk.reconosersdk.http.validateine.in.ValidateIneIn;

import org.jetbrains.annotations.NotNull;

public class ServicesOlimpia implements InterfaceService {

    private static ServicesOlimpia INSTANCE = null;
    private OlimpiaService service;

    private ServicesOlimpia() {
        service = new OlimpiaService();
    }

    public static synchronized ServicesOlimpia getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServicesOlimpia();
        }
        return (INSTANCE);
    }

    /**
     * Method for checking whether the customer's agreement is active according to the id supplied
     * @param guidConv Unique agreement identifier
     * @param datos Additional information
     * @param callbackOlimpia callback to find out if the service was successful or failed to consult agreement
     */


    @Override
    public void consultarConvenio(@NotNull String guidConv, @NotNull String datos, @NotNull CallbackConsultAgreement callbackOlimpia) {
        service.onGetAgreement(guidConv, datos, callbackOlimpia);
    }

    /**
     * Method for storing information from a resident's record
     * @param guardarCiudadanoIn An object is sent to keep citizen
     * @param listener callback to find out if the service was successful or failed to save person
     */
    @Override
    public void guardarCiudadano(@NotNull CiudadanoIn guardarCiudadanoIn, @NotNull CallbackSaveResident listener) {
        service.onSaveResident(guardarCiudadanoIn, listener);
    }

    /**
     * Method for storing a person's biometric information
     * @param guardarBiometriaIn a savebiometry object is sent
     * @param listener callback to find out if the service was successful or failed to save biometry
     */
    @Override
    public void guardarBiometria(@NotNull GuardarBiometriaIn guardarBiometriaIn, @NotNull CallbackSaveBiometry listener) {
        service.onSaveBiometry(guardarBiometriaIn, listener);
    }

    /**
     * Method requesting to the server he sent of an OTP, for the validation of a mail or mobile number
     //* @param guidCiudadano It's the person's unique identifier
     //* @param guidProcesoConvenio It's the person's  process unique identifier
     * @param enviarOTPIn It's a chain that contains a JSON with OTP data
     * @param listener callback to find out if the service was successful or failed to send OTP
     */

    @Override
    public void enviarOTP(@NotNull EnviarOTPIn enviarOTPIn, @NotNull CallbackSendOTP listener) {
        service.onSendOTP(enviarOTPIn, listener);
    }

    /**
     * Method for validating the OTP of the server sent by the individual
     * @param guidOTP It's the OTP's unique identifier.
     * @param oTP It's a string that contains a verification code
     * @param listener callback to find out if the service was successful or failed to validate OTP
     */
    @Override
    public void validarOTP(@NotNull String guidOTP, @NotNull String oTP, @NotNull CallbackValidateOTP listener) {
        service.onValidateOTP(guidOTP, oTP, listener);
    }

    /**
     * Method for requesting the socio-demographic questions corresponding to a person for subsequent validation
     * @param giudCiudadano It's the person's unique identifier
     * @param listener callback to find out if the service was successful or failed to request the questions
     */
    @Override
    public void solicitarPreguntasDemograficas(@NotNull String giudCiudadano, @NotNull CallbackRequestQuestions listener) {
        service.onRequestQuestions(giudCiudadano, listener);
    }

    /**
     * Method to validate that the answers sent by the citizen are those expected to process a score (SCORE) and determine if the ok is given in the validation.
     * @param validarRespuestaDemografica It's a string that contains a JSON with a list of answers.
     * @param listener callback to find out if the service was successful or failed to
     */
    @Override
    public void validarRespuestaDemograficas(@NotNull ValidarRespuestaIn validarRespuestaDemografica, @NotNull CallbackValidateResponse listener) {
        service.onValidateResponse(validarRespuestaDemografica, listener);
    }

    /**
     * Method to validate a resident's status, to determine what stage they are in, and what services have been completed.
     * @param guidCiudadano It's the person's unique identifier
     * @param listener callback to find out if the service was successful or failed to
     */
    @Override
    public void consultarCiudadano(@NotNull String guidCiudadano, @NotNull CallbackConsultResident listener) {
        service.onConsultResident(guidCiudadano, listener);
    }

    /**
     * Method that validates the authenticity of a biometric vs. the one that is
     * @param validarBiometriaIn
     * @param listener callback to find out if the service was successful or failed to
     */
    @Override
    public void validarBiometria(@NotNull ValidarBiometriaIn validarBiometriaIn, @NotNull CallbackValidateBiometry listener) {
        service.onValidateBiometry(validarBiometriaIn, listener);
    }

    /**
     * Method for Saving Application Error Log
     * @param guardarLogErrorIn
     * @param listener callback to find out if the service was successful or failed to
     */
    @Override
    public void guardarlogError(@NotNull GuardarLogErrorIn guardarLogErrorIn, @NotNull CallbackSaveLogError listener) {
        service.onSaveLogError(guardarLogErrorIn, listener);
    }


    @Override
    public void traerToken(@NotNull ObtenerToken obtenerToken, @NotNull CallbackGetToken listener) {
        service.onGetToken(obtenerToken, listener);
    }

    @Override
    public void initServer(@NotNull String guidConv, @NotNull String datos) {
        service.initService(guidConv, datos);
    }

    @Override
    public boolean isValidSerDocAnv() {
        return service.isServiceDocAnverso();
    }

    @Override
    public boolean isValidSerDocRev() {
        return service.isServiceDocReverso();
    }

    @Override
    public boolean isValidBiometry() {
        return service.isServiceBiometryFace();
    }

    @NotNull
    @Override
    public RespuestaTransaccion getErrorCustom(@NotNull String code, @NotNull String descriptor) {
        return service.getErrorCustom(code, descriptor);
    }

    @NotNull
    @Override
    public RespuestaTransaccion getErrorCode(@NotNull String code) {
        return service.getErrorCode(code);
    }

    @Override
    public boolean isValidBarCode() {
        return service.isServiceBarcode();
    }

    /**
     *
     * @param guardarDocumentoIn
     * @param listener
     */
    @Override
    public void guardarDocumento(@NotNull GuardarDocumentoIn guardarDocumentoIn, @NotNull CallbackSaveDocument listener) {
        service.onSaveDocument(guardarDocumentoIn, listener);
    }

    //To auth User
    @Override
    public void authAdviser(@NotNull String adviser, @NotNull String codeAdviser, @NotNull OlimpiaInterface.CallbackAuthentication listener) {
        service.onAuthUser(adviser, codeAdviser, listener);
    }

    //To get the campus
    @Override
    public void getSedeConvenio(@NotNull String guidConv,  @NotNull OlimpiaInterface.CallbackSedeConvenio listener) {
        service.onSedeConvenio(guidConv, listener);
    }

    //To get the canceled reason
    @Override
    public void getCanceledReason(@NotNull String guidConv, @NotNull OlimpiaInterface.CallbackMotivosCancelado listener) {
        service.onMotivosCancelado(guidConv, listener);
    }

    //To get the pending process
    @Override
    public void getPendingProcess(@NotNull ProcesosPendientes procesosPendientes, @NotNull OlimpiaInterface.CallbackProcesosPendientes listener) {
        service.onProcesosPendientes(procesosPendientes, listener);
    }

    //To cancel the process
    @Override
    public void getCancelProcess(@NotNull CancelarProceso cancelarProceso, @NotNull OlimpiaInterface.CallbackCancelarProceso listener) {
        service.onCancelarProceso(cancelarProceso, listener);
    }


    //To consult the state process
    @Override
    public void getConsultProcessState(@NotNull ConsultarEstadoProceso consultarEstadoProceso, @NotNull OlimpiaInterface.CallbackConsultarEstadoProceso listener) {
        service.onConsultarEstadoProceso(consultarEstadoProceso, listener);
    }

    //To process request
    @Override
    public void getProcessRequest(@NotNull SolicitudProceso solicitudProceso, @NotNull OlimpiaInterface.CallbackSolicitudProceso listener) {
        service.onSolicitudProceso(solicitudProceso, listener);
    }

    //To consult the process
    @Override
    public void getConsultProcess(@NotNull ConsultarProceso consultarProceso, @NotNull OlimpiaInterface.CallbackConsultarProceso listener) {
        service.onConsultarProceso(consultarProceso, listener);
    }

    @Override
    public void getClientFound(@NotNull SearchUser searchUser, @NotNull OlimpiaInterface.CallbackSearchUser listener) {
        service.onSearchUser(searchUser, listener);
    }

    @Override
    public void getSearchForDocument(@NotNull SearchForDocument searchId, @NotNull OlimpiaInterface.CallbackSearchForDocument listener) {
        service.onSearchForDocument(searchId, listener);
    }

    @Override
    public void getCompararionFace(@NotNull DataComparisonFace dataComparisonFace, @NotNull OlimpiaInterface.CallbackReconoserComparasion listener) {
        service.onComparasionFace(dataComparisonFace, listener);
    }

    @Override
    public void getValidationFaceBD(@NotNull DataValidateFace dataValidateFace, @NotNull OlimpiaInterface.CallbackReconoserValidate listener) {
        service.onValidateFaceBD(dataValidateFace, listener);
    }

    @Override
    public void getSourcesValidation(@NotNull ValidateIneIn validateIne, @NotNull OlimpiaInterface.CallbackValidateIne listener) {
        service.onValidateMexicanDocument(validateIne, listener);
    }

    @Override
    public void onFinishProcess(@NotNull String idProceso, @NotNull boolean state, @NotNull OlimpiaInterface.CallbackFinishProcess listener) {
        service.onFinishProcess(idProceso, state, listener);
    }

    @Override
    public void getRequestOpenSource(@NotNull SolicitudFuentesAbiertasIn solicitudFuentesAbiertasIn, @NotNull OlimpiaInterface.CallbackReconoserRequestOpenSource listener) {
        service.onRequestOpenSource(solicitudFuentesAbiertasIn, listener);
    }

    @Override
    public void getConsultOpenSource(@NotNull ConsultarFuentesAbiertasIn consultarFuentesAbiertasIn, @NotNull OlimpiaInterface.CallbackReconoserConsultOpenSource listener) {
        service.onConsultOpenSource(consultarFuentesAbiertasIn, listener);
    }

    @Override
    public void getListOpenSource(@NotNull OlimpiaInterface.CallbackReconoserListOpenSource listener) {
        service.onListOpenSource(listener);
    }

    @Override
    public void onValidationOpenSourceDocument(@NotNull OpenSourceValidationRequest openSourceValidationRequest, @NotNull OlimpiaInterface.CallbackValidationOpenSourceDocument listener) {
        service.onValidationOpenSourceDocument(openSourceValidationRequest, listener);
    }

    @Override
    public void onValidationReceipt(@NotNull ValidateReceiptIn validateReceiptIn, @NotNull OlimpiaInterface.CallbackValidationReceipt listener) {
        service.onValidationReceipt(validateReceiptIn, listener);
    }

    @Override
    public void onValidationDriverLicense(@NotNull ValidateDriverLicenseIn validateDriverLicenseIn, @NotNull OlimpiaInterface.CallbackValidationDriverLicense listener) {
        service.onValidationDriverLicense(validateDriverLicenseIn, listener);
    }

    @Override
    public void onValidationFederalDriverLicense(@NotNull ValidateFederalDriverLicenseIn validateFederalDriverLicenseIn, @NotNull OlimpiaInterface.CallbackValidationFederalDriverLicense listener) {
        service.onValidationFederalDriverLicense(validateFederalDriverLicenseIn, listener);
    }

    @Override
    public void onSaveTraceability(@NotNull SaveTraceabilityProcessIn saveTraceabilityProcessIn, @NotNull OlimpiaInterface.CallbackSaveTraceability listener) {
        service.onSaveTraceability(saveTraceabilityProcessIn, listener);
    }

    @Override
    public void consultarFuente(@NotNull ConsultarFuenteIn consultarFuenteIn, @NotNull OlimpiaInterface.CallbackConsultSource listener) {
        service.onGetConsultSources(consultarFuenteIn, listener);
    }

    @Override
    public void guardarLogBarcode(@NotNull LogSaveBarcode logSaveBarcode, @NotNull OlimpiaInterface.CallbackLogSaveBarcode listener) {
        service.onGetSaveLogBarcode(logSaveBarcode, listener);
    }

    @Override
    public void guardarLogOCRDocumento(@NotNull LogSaveOCR logSaveOCR, @NotNull OlimpiaInterface.CallbackLogSaveOCR listener) {
        service.onGetSaveLogOCR(logSaveOCR, listener);
    }

    @Override
    public void guardarLogResultadoMovil(@NotNull LogMobileResult logMobileResult, @NotNull OlimpiaInterface.CallbackLogMobileResult listener) {
        service.onGetSaveMobileResult(logMobileResult, listener);
    }

    @Override
    public void consultarAni(@NotNull ConsultarAniIn consultarAniIn, @NotNull OlimpiaInterface.CallbackConsultAni listener) {
        service.onGetConsultAni(consultarAniIn, listener);
    }

    @Override
    public void getRequestValidation(@NotNull RequestValidationIn requestValidationIn, @NotNull OlimpiaInterface.CallbackRequestValidation listener) {
        service.onGetRequestValidation(requestValidationIn, listener);
    }

    @Override
    public void getConsultSteps(@NotNull ConsultStepsIn consultStepsIn, @NotNull OlimpiaInterface.CallbackConsultSteps listener) {
        service.onGetConsultSteps(consultStepsIn, listener);
    }

    @Override
    public void getConsultValidation(@NotNull ConsultValidationIn consultValidationIn, @NotNull OlimpiaInterface.CallbackConsultValidation listener) {
        service.onGetConsultValidation(consultValidationIn, listener);
    }


    @Override
    public void getSaveDocumentSides(@NotNull SaveDocumentSidesIn saveDocumentSidesIn, @NotNull OlimpiaInterface.CallbackSaveDocumentSides listener) {
        service.onGetSaveDocumentSides(saveDocumentSidesIn, listener);
    }

    @Override
    public void getListConsultAgreementProcess(@NotNull String procesoConvenioGuid, @NotNull OlimpiaInterface.CallbackConsultAgreementProcess listener) {
        service.onConsultAgreementProcess(procesoConvenioGuid, listener);
    }


    @Override
    public void getLogin(@NotNull LoginIn loginIn, @NotNull OlimpiaInterface.CallbackLogin listener) {
        service.onGetLogin(loginIn, listener);
    }

    @Override
    public void getObtainDataEasyTrack(@NotNull ObtainDataEasyTrackIn obtainDataEasyTrackIn, @NotNull OlimpiaInterface.CallbackObtainDataEasyTrack listener) {
        service.onGetObtainDataEasyTrack(obtainDataEasyTrackIn, listener);
    }

    /**
     * Method for Accept ATDP politics
     * @param aceptarAtdpIn
     * @param listener callback to find out if the service was successful or failed to
     */
    @Override
    public void getAcceptAtdp(@NonNull AceptarAtdpIn aceptarAtdpIn, @NonNull OlimpiaInterface.CallbackAceptarAtdp listener) {
        service.onAcceptATDP(aceptarAtdpIn, listener);
    }

    /**
     * Method for Accept ATDP politics
     * @param enviarOTPAuthIDIn
     * @param listener callback to find out if the service was successful or failed to
     */
    @Override
    public void getSendOTPAuthID(@NonNull EnviarOTPAuthIDIn enviarOTPAuthIDIn, @NonNull OlimpiaInterface.CallbackEnviarOTPAuthID listener) {
        service.onSendOTPAuthID(enviarOTPAuthIDIn, listener);
    }
}
