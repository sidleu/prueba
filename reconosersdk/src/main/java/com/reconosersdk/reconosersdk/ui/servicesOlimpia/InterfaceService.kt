package com.reconosersdk.reconosersdk.ui.servicesOlimpia

import com.reconosersdk.reconosersdk.http.OlimpiaInterface.*
import com.reconosersdk.reconosersdk.http.aceptarATDP.`in`.AceptarAtdpIn
import com.reconosersdk.reconosersdk.http.consultSteps.`in`.ConsultStepsIn
import com.reconosersdk.reconosersdk.http.consultValidation.`in`.ConsultValidationIn
import com.reconosersdk.reconosersdk.http.enviarOTPAuthID.`in`.EnviarOTPAuthIDIn
import com.reconosersdk.reconosersdk.http.login.`in`.LoginIn
import com.reconosersdk.reconosersdk.http.obtainDataEasyTrack.`in`.ObtainDataEasyTrackIn
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.OpenSourceValidationRequest
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.*
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.ani.ConsultarAniIn
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.savelogs.LogMobileResult
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.savelogs.LogSaveBarcode
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.savelogs.LogSaveOCR
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.savetraceability.SaveTraceabilityProcessIn
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion
import com.reconosersdk.reconosersdk.http.openSource.`in`.ConsultarFuentesAbiertasIn
import com.reconosersdk.reconosersdk.http.openSource.`in`.SolicitudFuentesAbiertasIn
import com.reconosersdk.reconosersdk.http.requestValidation.`in`.RequestValidationIn
import com.reconosersdk.reconosersdk.http.saveDocumentSides.`in`.SaveDocumentSidesIn
import com.reconosersdk.reconosersdk.http.validateine.`in`.ValidateIneIn

interface InterfaceService {

    //Traer el token
    fun traerToken(obtenerToken: ObtenerToken, callbackGetToken: CallbackGetToken)

    fun consultarConvenio(guidConv: String, datos: String, callbackOlimpia: CallbackConsultAgreement)
    fun guardarCiudadano(guardarCiudadanoIn: CiudadanoIn, listener: CallbackSaveResident)
    fun guardarBiometria(guardarBiometriaIn: GuardarBiometriaIn, listener: CallbackSaveBiometry)
    fun enviarOTP(enviarOTPIn: EnviarOTPIn, listener: CallbackSendOTP)
    fun validarOTP(guidOTP: String, oTP: String, listener: CallbackValidateOTP)
    fun solicitarPreguntasDemograficas(giudCiudadano: String, listener: CallbackRequestQuestions)
    fun validarRespuestaDemograficas(validarRespuestaDemografica: ValidarRespuestaIn, listener: CallbackValidateResponse)
    fun consultarCiudadano(guidCiudadano: String, listener: CallbackConsultResident)
    fun validarBiometria(validarBiometriaIn: ValidarBiometriaIn, listener: CallbackValidateBiometry)
    fun guardarlogError(guardarLogErrorIn: GuardarLogErrorIn, listener: CallbackSaveLogError)
    fun guardarDocumento(guardarDocumentoIn: GuardarDocumentoIn, listener: CallbackSaveDocument)
    fun initServer(guidConv: String, datos: String)
    fun isValidSerDocAnv(): Boolean
    fun isValidSerDocRev(): Boolean
    fun isValidBarCode(): Boolean
    fun isValidBiometry(): Boolean
    fun getErrorCustom(code: String, descriptor: String): RespuestaTransaccion
    fun getErrorCode(code: String): RespuestaTransaccion
    fun getSourcesValidation(validateIne: ValidateIneIn, listener: CallbackValidateIne)
    fun onValidationOpenSourceDocument(openSourceValidationRequest: OpenSourceValidationRequest, listener: CallbackValidationOpenSourceDocument)

    //Associated services to consult different process
    fun authAdviser(adviser: String, codeAdviser: String, listener: CallbackAuthentication)
    fun getSedeConvenio(guidConv: String, listener: CallbackSedeConvenio)
    fun getCanceledReason(guidConv: String, listener: CallbackMotivosCancelado)
    fun getPendingProcess(procesosPendientes: ProcesosPendientes, listener: CallbackProcesosPendientes)
    fun getCancelProcess(cancelarProceso: CancelarProceso, listener: CallbackCancelarProceso)
    fun getConsultProcessState(consultarEstadoProceso: ConsultarEstadoProceso, listener: CallbackConsultarEstadoProceso)
    fun getProcessRequest(solicitudProceso: SolicitudProceso, listener: CallbackSolicitudProceso)
    fun getConsultProcess(consultarProceso: ConsultarProceso, listener: CallbackConsultarProceso)

    fun getClientFound(searchUser: SearchUser, listener: CallbackSearchUser)
    fun getSearchForDocument(searchId: SearchForDocument, listener: CallbackSearchForDocument)
    fun getCompararionFace(dataComparisonFace: DataComparisonFace, listener: CallbackReconoserComparasion)
    fun getValidationFaceBD(dataValidateFace: DataValidateFace, listener: CallbackReconoserValidate)
    fun onFinishProcess(idProceso: String, state: Boolean, listener: CallbackFinishProcess)
    fun consultarFuente(consultarFuenteIn: ConsultarFuenteIn, listener: CallbackConsultSource)
    fun guardarLogBarcode(logSaveBarcode: LogSaveBarcode, listener: CallbackLogSaveBarcode)
    fun guardarLogOCRDocumento(logSaveOCR: LogSaveOCR, listener: CallbackLogSaveOCR)
    fun guardarLogResultadoMovil(logMobileResult: LogMobileResult, listener: CallbackLogMobileResult)
    fun consultarAni(consultarAniIn : ConsultarAniIn, listener: CallbackConsultAni)
    //Open sources
    fun getRequestOpenSource(solicitudFuentesAbiertasIn : SolicitudFuentesAbiertasIn, listener: CallbackReconoserRequestOpenSource )
    fun getConsultOpenSource(consultarFuentesAbiertasIn: ConsultarFuentesAbiertasIn , listener: CallbackReconoserConsultOpenSource )
    fun getListOpenSource(listener: CallbackReconoserListOpenSource )
    fun onValidationReceipt(validateReceiptIn : ValidateReceiptIn, listener: CallbackValidationReceipt)
    fun onValidationDriverLicense(validateDriverLicenseIn : ValidateDriverLicenseIn, listener: CallbackValidationDriverLicense)
    fun onValidationFederalDriverLicense(validateFederalDriverLicenseIn : ValidateFederalDriverLicenseIn, listener: CallbackValidationFederalDriverLicense)
    fun onSaveTraceability(saveTraceabilityProcessIn : SaveTraceabilityProcessIn, listener: CallbackSaveTraceability)
    //Consult and validate steps
    fun getRequestValidation(requestValidationIn : RequestValidationIn, listener: CallbackRequestValidation )
    //Consult and validate steps
    fun getConsultSteps(consultStepsIn : ConsultStepsIn, listener: CallbackConsultSteps )
    //Consult the validation
    fun getConsultValidation(consultValidationIn : ConsultValidationIn, listener: CallbackConsultValidation)
    //Consult the save document sides
    fun getSaveDocumentSides(saveDocumentSidesIn : SaveDocumentSidesIn, listener: CallbackSaveDocumentSides)
    //Consult the agreement process
    fun getListConsultAgreementProcess(procesoConvenioGuid : String, listener: CallbackConsultAgreementProcess )
    //Consult the login
    fun getLogin(loginIn : LoginIn, listener: CallbackLogin)
    //Consult the obtainDataEasyTrack
    fun getObtainDataEasyTrack(obtainDataEasyTrackIn : ObtainDataEasyTrackIn, listener: CallbackObtainDataEasyTrack)

    //Aceptar Atdp
    fun getAcceptAtdp(aceptarAtdpIn: AceptarAtdpIn, callbackAceptarAtdp: CallbackAceptarAtdp)

    //Enviar OT PAuthID
    fun getSendOTPAuthID(enviarOTPAuthIDIn: EnviarOTPAuthIDIn, callbackEnviarOTPAuthID: CallbackEnviarOTPAuthID)

}