package com.reconosersdk.reconosersdk.http

import com.reconosersdk.reconosersdk.http.consultValidation.`in`.ConsultValidationIn
import com.reconosersdk.reconosersdk.http.consultValidation.out.ConsultValidationOut
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.*
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid.RespondComparasionFace
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid.RespondValidateFace
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid.openSource.RespondOpenSourceValidation
import com.reconosersdk.reconosersdk.http.openSource.`in`.ConsultarFuentesAbiertasIn
import com.reconosersdk.reconosersdk.http.openSource.`in`.SolicitudFuentesAbiertasIn
import com.reconosersdk.reconosersdk.http.openSource.out.ConsultarFuentesAbiertasOut
import com.reconosersdk.reconosersdk.http.openSource.out.ParametroFuenteOut
import com.reconosersdk.reconosersdk.http.openSource.out.SolicitudFuentesAbiertasOut
import com.reconosersdk.reconosersdk.http.requestValidation.`in`.RequestValidationIn
import com.reconosersdk.reconosersdk.http.requestValidation.out.RequestValidationOut
import com.reconosersdk.reconosersdk.http.validateine.`in`.ValidateIneIn
import com.reconosersdk.reconosersdk.http.validateine.out.ValidateIneOutX
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ReconoserIdApi {

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("Validacion/CompararRostros")
    fun compararRostros(@Body body: DataComparisonFace): Single<Response<RespondComparasionFace>>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("ValidarFuente")
    fun validarRostroInBD(@Body body: DataValidateFace): Single<Response<RespondValidateFace>>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("Validacion/ConsultarFuentesAbiertas")
    fun consultarFuentesAbiertas(@Body body: ConsultarFuentesAbiertasIn): Single<Response<ConsultarFuentesAbiertasOut>>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("Validacion/SolicitudFuentesAbiertas")
    fun solicitudFuentesAbiertas(@Body body: SolicitudFuentesAbiertasIn): Single<Response<SolicitudFuentesAbiertasOut>>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @GET("ParametroFuente")
    fun obtenerParametroFuente(): Single<Response<ParametroFuenteOut>>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("Validacion/ValFuentesDocumento")
    fun validarFuentesDocumento(@Body body: ValidateIneIn): Single<Response<ValidateIneOutX>>

    @Headers("Accept: application/json\", \"Content-Type: application/json")
    @POST("Validacion/ValDocumentoExtranjero")
    fun validarDocumentoExtranjero(@Body openSourceValidationRequest: OpenSourceValidationRequest): Single<Response<RespondOpenSourceValidation>>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("ConsultarFuente")
    fun validarConsultarFuentes(@Body body: ConsultarFuenteIn): Single<Response<RespondConsultarFuente>>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("Validacion/SolicitudValidacionEstado")
    fun solicitudValidacion(@Body body: RequestValidationIn): Single<Response<RequestValidationOut>>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("Validacion/ConsultarValidacion")
    fun consultarValidacion(@Body body: ConsultValidationIn): Single<Response<ConsultValidationOut>>

}