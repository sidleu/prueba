package com.reconosersdk.reconosersdk.http

import com.reconosersdk.reconosersdk.http.aceptarATDP.`in`.AceptarAtdpIn
import com.reconosersdk.reconosersdk.http.consultAgreementProcess.out.ConsultAgreementProcessOut
import com.reconosersdk.reconosersdk.http.consultSteps.`in`.ConsultStepsIn
import com.reconosersdk.reconosersdk.http.consultSteps.out.ConsultStepsOut
import com.reconosersdk.reconosersdk.http.enviarOTPAuthID.out.EnviarOTPAuthIDOut
import com.reconosersdk.reconosersdk.http.login.`in`.LoginIn
import com.reconosersdk.reconosersdk.http.login.out.LoginOut
import com.reconosersdk.reconosersdk.http.obtainDataEasyTrack.out.ObtainDataEasyTrackOut
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.*
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.ani.ConsultarAniIn
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.savelogs.LogMobileResult
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.savelogs.LogSaveBarcode
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.savelogs.LogSaveOCR
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.savetraceability.SaveTraceabilityProcessIn
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.*
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ani.ConsultarAniOut
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.finish.RespondFinishProcess
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.savelogs.RespondLogIdentity
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.savelogs.RespondLogMobileResult
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.savetraceability.SaveTraceabilityOut
import com.reconosersdk.reconosersdk.http.regula.entities.`in`.ValidarDocumentoGenericoIn
import com.reconosersdk.reconosersdk.http.regula.entities.out.ValidarDocumentoGenericoOut
import com.reconosersdk.reconosersdk.http.saveDocumentSides.`in`.SaveDocumentSidesIn
import com.reconosersdk.reconosersdk.http.saveDocumentSides.out.SaveDocumentSidesOut
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ServiceOlimpiaApi {
    //To get the token
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("TraerToken")
    fun traerToken(@Body body: RequestBody?): Single<HeaderToken?>?

    //To consult the agreement
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("ConsultarConvenio")
    fun consultarConvenio(@Body body: RequestBody?): Single<ConsultarConvenio?>?

    //To auth
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("AutenticarAsesor")
    fun autenticar(@Body body: RequestBody?): Single<AutenticarAsesorOut?>?

    //To get the campus
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("SedeConvenio")
    fun sedeConvenio(@Body body: RequestBody?): Single<SedeConvenioOut?>?

    //To get the canceled reason
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("MotivosCancelado")
    fun motivosCancelado(@Body body: RequestBody?): Single<MotivosCanceladoOut?>?

    //To get the pending process
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("ProcesosPendientes")
    fun procesosPendientes(@Body body: RequestBody?): Single<ProcesosPendientesOut?>?

    //To cancel the process
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("CancelarProceso")
    fun cancelarProceso(@Body body: RequestBody?): Single<CancelarProcesoOut?>?

    //To consult the state process
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("ConsultarEstadoProceso")
    fun consultarEstadoProceso(@Body body: RequestBody?): Observable<ConsultarEstadoProcesoOut?>?

    //To process request
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("SolicitudProceso")
    fun solicitudProceso(@Body body: RequestBody?): Single<SolicitudProcesoOut?>?

    //To consult the process
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("ConsultarProceso")
    fun consultarProceso(@Body body: RequestBody?): Single<ConsultarProcesoOut?>?

    //To save the citizen
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("GuardarCiudadano")
    fun guardarCiudadano(@Body body: RequestBody?): Single<GuardarCiudadano?>?

    //To save the biometry
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("GuardarBiometria")
    fun guardarBiometria(@Body body: GuardarBiometriaIn): Single<GuardarBiometria>

    //To validate the biometry
    @POST("ValidarBiometria")
    fun validarBiometria(@Body body: ValidarBiometriaIn?): Observable<ValidarBiometria?>?

    //To save the document
    @POST("GuardarDocumento")
    fun guardarDocumento(@Body body: RequestBody?): Single<BarcodeDocument?>?

    //To send the OTP
    @POST("EnviarOTP")
    fun enviarOTP(@Body body: RequestBody?): Single<EnviarOTP?>?

    //To validate the OTP
    @POST("ValidarOTP")
    fun validarOTP(@Body body: RequestBody?): Single<ValidarOTP?>?

    //To request the questions
    @POST("SolicitarPreguntasDemograficas")
    fun solicitarPreguntasDemograficas(@Body body: RequestBody?): Single<SolicitarPreguntasDemograficas?>?

    //To validate the questions
    @POST("ValidarRespuestaDemografica")
    fun validarRespuestaDemografica(@Body body: RequestBody?): Single<ValidarRespuestaDemografica?>?

    //To consult the citizen
    @POST("ConsultarCiudadano")
    fun consultarCiudadano(@Body body: RequestBody?): Single<ConsultarCiudadano?>?

    //To save tho log error
    @POST("GuardarlogError")
    fun guardarlogError(@Body body: RequestBody?): Single<GuardarLogError?>?

    //To get and search user
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("ObtenerUsuario")
    fun obtenerUserDemo(@Body user: SearchUser?): Single<Response<UserFound>>

    //To search any citizen by document
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("ConsultarCiudadanoDocumento")
    fun consultarCiudadanoDocumento(@Body idDocument: SearchForDocument): Single<RespondSearchDocument>

    //To validate de citizen document
    @POST("ValidarDocumentoMovil")
    fun getDocumentValidation(@Body body: ValidarDocumentoGenericoIn?): Single<Response<ValidarDocumentoGenericoOut?>>

    //To finish the process than has started
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("FinalizarProceso/{procesoConvenioGuid}/{estado}")
    fun finishProcess(
        @Path("procesoConvenioGuid") idProcess: String,
        @Path("estado") state: Boolean
    ): Single<Response<RespondFinishProcess>>

    //To consult the citizen biometry
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("ConsultarBiometria")
    fun getBiometry64(@Body body: SearchBiometry): Single<Response<RespondSearchBiometry>>

    //To validate a receipt
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("ValidarRecibo")
    fun getValidateReceipt(@Body body: ValidateReceiptIn?): Single<Response<ValidateReceiptOut?>>

    //To validate a driver license
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("ValidarLicenciaConduccion")
    fun getValidateDriverLicense(@Body body: ValidateDriverLicenseIn?): Single<Response<ValidateDriverLicenseOut?>>

    //To validate a federal driver license
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("ValidarLicFederalConduccion")
    fun getValidateFederalDriverLicense(@Body body: ValidateFederalDriverLicenseIn?): Single<Response<ValidateFederalDriverLicenseOut?>>

    //To save the traceability
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("GuardarTrazabilidadProceso")
    fun getSaveTraceability(@Body body: SaveTraceabilityProcessIn?): Single<Response<SaveTraceabilityOut?>>

    //To save the barcode data
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("GuardarLogBarcode")
    fun getSaveLogBarcode(@Body body: LogSaveBarcode?): Single<Response<RespondLogMobileResult?>>

    //To save the OCR data
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("GuardarLogOCRDocumento")
    fun getSaveLogOCR(@Body body: LogSaveOCR?): Single<Response<RespondLogMobileResult?>>

    //To save the mobile (document and biometry) data
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("GuardarLogResultadosMovil")
    fun getSaveLogMobileResults(
        @Body logMobileResult: LogMobileResult
    ): Single<Response<RespondLogIdentity>>

    //To save the mobile (document and biometry) data
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("ConsultarAni")
    fun getConsultAni(
        @Body consultarAniIn: ConsultarAniIn
    ): Single<Response<ConsultarAniOut>>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("ConsultarPasos")
    fun consultarPasos(@Body body: ConsultStepsIn): Single<Response<ConsultStepsOut>>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("GuardarDocumentoAmbasCaras")
    fun guardarDocumentoAmbasCaras(@Body body: RequestBody?): Single<Response<SaveDocumentSidesOut?>>?

    //To consult the process
    //Example
    //https://demorcs.olimpiait.com:6317/ConsultarProcesoConvenio?procesoConvenioGuid=5ff7bf85-0aa3-4250-96f0-031c4098ad3d
    @Headers("Accept: application/json", "Content-Type: application/json")
    @GET("ConsultarProcesoConvenio")
    fun consultarProcesoConvenio(@Query( "procesoConvenioGuid") guidProcesoConvenio : String): Single<Response<ConsultAgreementProcessOut>>


    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("Login")
    fun login(@Body body: LoginIn): Single<Response<LoginOut>>

    //To consult email data easytrack
    //https://demorcs.olimpiait.com:6302/Api/ObtenerDataEasyTrack?Correo=correo
    @Headers("Accept: application/json", "Content-Type: application/json")
    @GET("ObtenerDataEasyTrack/")
    fun obtainDataEasyTrack(@Query("Correo")email:String): Single<Response<ObtainDataEasyTrackOut>>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("AceptarATDP")
    fun aceptarATDP(@Body body: AceptarAtdpIn): Single<Response<Void>>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("EnviarOTPAuthID")
    fun enviarOTPAuthID(@Body body: RequestBody?): Single<Response<EnviarOTPAuthIDOut?>>?

}