package com.reconosersdk.reconosersdk.ui.bioFacial.presenters

import android.content.Context
import android.util.Log
import com.reconosersdk.reconosersdk.http.OlimpiaInterface.*
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.*
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.GuardarBiometria
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.SolicitudProcesoOut
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidarBiometria
import com.reconosersdk.reconosersdk.ui.LibraryReconoSer
import com.reconosersdk.reconosersdk.ui.base.BasePresenter
import com.reconosersdk.reconosersdk.ui.bioFacial.interfaces.LivePreviewContract.LivePreviewMvpView
import com.reconosersdk.reconosersdk.ui.bioFacial.interfaces.LivePreviewContract.LivePreviewPresenter
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.PreferencesSave.idGuidConv
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.PreferencesSave.savePhoto
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.PreferencesSave.storageQuality
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.ServicesOlimpia
import com.reconosersdk.reconosersdk.utils.Constants
import com.reconosersdk.reconosersdk.utils.ImageUtils
import com.reconosersdk.reconosersdk.utils.Miscellaneous
import java.io.File
import java.io.IOException
import java.util.*


class LivePreviewPresenterImpl<V : LivePreviewMvpView>() :
    BasePresenter<V>(), LivePreviewPresenter<V> {

    private val DEFAULT_SAVE = "0"
    private val NOT_SAVE = "1"
    private val SUM_TRANSACTION = "2"
    private val SAVE_NOT_GUIDCIU = "3"
    private var context: Context? = null
    private var guidCiudadano: String? = ""
    private var typeDocument: String? = ""
    private var numDocument: String? = ""
    private var isValidateBiometry: Boolean = false
    private var saveUser: String? = ""
    private var quality: Int? = 70
    private var guidProcessAgreement: String? = ""

    //Default values and image type (HD)
    private fun compressFileImageFace(path: String): String? {
        val f = File(path)
        try {
            val compressedImageFile = Miscellaneous.getRescaledImage(Constants.HEIGHT_FACE, Constants.WIDTH_FACE, Constants.MAX_QUALITY, f.absolutePath)
            val options = Miscellaneous.getIMGSize(compressedImageFile!!.path)
            val b64Face = ImageUtils.getEncodedBase64FromFilePath(compressedImageFile.path)
            val showText = """
            path face is in: ${compressedImageFile.path}
            image face height: ${options.outHeight}
            image face width: ${options.outWidth}
            image b64: $b64Face     
            """.trimIndent()
            //TODO Solo para pruebas
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                StringUtils.appendLog("*** Texto a visualizar después de la compresión *** " + "\n"+ LocalDateTime.now() + "\n"+ showText + " *** Fin del texto *** ");
            }*/
            Log.e("Face converted to: -> ", "a JPEG image")
            return compressedImageFile.path
        } catch (e: IOException) {
            e.printStackTrace()
            mvpView!!.onFinishError(
                ServicesOlimpia.getInstance().getErrorCustom(
                    Constants.ERROR_R104,
                    Constants.ERROR_IMAGE_LOAD
                )
            )
        }
        return null
    }

    override fun onSaveImage(imageTake: String) {

        val dataDocument = DetailDocument(typeDocument, numDocument)
        val guardarBiometriaIn = GuardarBiometriaIn()
        guardarBiometriaIn.guidCiu = guidCiudadano
        guardarBiometriaIn.idServicio = 5
        guardarBiometriaIn.subTipo = "Frontal"
        guardarBiometriaIn.formato = "JPG_B64"
        //guardarBiometriaIn.datosAdi = JsonUtils.stringObject(dataDocument)
        guardarBiometriaIn.datosAdi = ""
        guardarBiometriaIn.usuario = saveUser
        if (!guidProcessAgreement.isNullOrEmpty()) {
            guardarBiometriaIn.guidProcesoConvenio = guidProcessAgreement
        }
        guardarBiometriaIn.valor = ImageUtils.getEncodedBase64FromFilePath(imageTake)
        if (SUM_TRANSACTION == savePhoto) {
            guardarBiometriaIn.guidConvenio = idGuidConv
            guardarBiometriaIn.guidCiu = null
        }
        ServicesOlimpia.getInstance()
            .guardarBiometria(guardarBiometriaIn, object : CallbackSaveBiometry {
                override fun onSuccess(guardarBiometria: GuardarBiometria) {
                    mvpView?.onFinish()
                }

                override fun onError(respuestaTransaccion: RespuestaTransaccion) {
                    mvpView?.onHideProgress()
                    if (respuestaTransaccion.errorEntransaccion.isEmpty() or respuestaTransaccion.errorEntransaccion[0].codigo.isNullOrEmpty()) {
                        mvpView?.onFinishError(
                            ServicesOlimpia.getInstance().getErrorCustom(
                                Constants.ERROR_R105,
                                Constants.ERROR_IMAGE_SAVE
                            )
                        )
                    } else {
                        mvpView?.onFinishError(respuestaTransaccion)
                    }
                }
            })
    }

    override fun onLoadImage(imageTake: String, context: Context, advisor : String, campus : String) {
        this.context = context
        val imageCompress = compressFileImageFace(imageTake)
        if (NOT_SAVE != savePhoto) {
            if (isValidateBiometry) {
                imageCompress?.let {
                    solicitudProceso(it, advisor, campus)
                }
            } else {
                imageCompress?.let {
                    onSaveImage(it)
                }
            }
        } else {
            mvpView?.onFinish();
        }
    }

    override fun onValidateBiometry(validarBiometriaIn: ValidarBiometriaIn) {

        ServicesOlimpia.getInstance()
            .validarBiometria(validarBiometriaIn, object : CallbackValidateBiometry {
                override fun onSuccess(validateBiometry: ValidarBiometria) {
                    mvpView?.onRespondValidate(validateBiometry)
                }

                override fun onError(
                    transactionResponse: RespuestaTransaccion, intentos: Int
                ) {
                    mvpView?.onHideProgress()
                    if (transactionResponse.errorEntransaccion.isEmpty() or transactionResponse.errorEntransaccion[0].codigo.isNullOrEmpty()) {
                        mvpView?.onFinishError(
                            ServicesOlimpia.getInstance().getErrorCustom(
                                Constants.ERROR_R112,
                                "${Constants.ERROR_VALIDATE_BIOMETRY} intentos $intentos"
                            )
                        )
                    } else {
                        mvpView?.onFinishError(transactionResponse)
                    }
                }
            })
    }

    override fun onSetName(
        guidCiudadano: String,
        typeDocument: String,
        numDocument: String,
        validateBiometry: Boolean,
        saveUser: String,
        quality: Int,
        guidProcessAgreement: String
    ) {
        this.guidCiudadano = guidCiudadano
        this.typeDocument = typeDocument
        this.numDocument = numDocument
        this.isValidateBiometry = validateBiometry
        this.saveUser = saveUser
        this.guidProcessAgreement = guidProcessAgreement.let {
            it
        }
        if (quality < 0 || quality > 100) {
            this.quality = 70
        } else {
            this.quality = quality
        }
        this.quality?.let {
            storageQuality = it
        }
        when (savePhoto) {
            DEFAULT_SAVE -> {
                isValidatedGuidCiu(guidCiudadano)
                if (validateBiometry) {
                    isValidateData(typeDocument, numDocument)
                }
            }
            SUM_TRANSACTION -> {
                this.guidCiudadano = ""
            }
            SAVE_NOT_GUIDCIU -> {
                isValidateData(typeDocument, numDocument)
            }
        }
    }

    private fun sendErrorFinish(code: String, error: String) {
        mvpView?.onFinishError(code, error)
    }

    private fun isValidateData(typeDocument: String, numDocument: String) {
        if (typeDocument.isNullOrEmpty()) {
            sendErrorFinish(Constants.ERROR_R109, Constants.ERROR_NO_PARAM_TYPE)
        }
        if (numDocument.isNullOrEmpty()) {
            sendErrorFinish(Constants.ERROR_R110, Constants.ERROR_NO_PARAM_NUM)
        }
        if (numDocument.length < 4 /*|| numDocument.length > 12*/) {
            sendErrorFinish(Constants.ERROR_R111, Constants.ERROR_PARAM_NUM)
        }
    }

    private fun isValidatedGuidCiu(guidCiudadano: String) {
        if (guidCiudadano.isNullOrEmpty()) {
            sendErrorFinish(Constants.ERROR_R108, Constants.ERROR_NO_PARAM_GUICIU)
        }
    }

    private fun solicitudProceso(imageTake: String, advisor: String, campus: String) {
        if (guidProcessAgreement != null && !guidProcessAgreement.equals("")) {
            mvpView?.onCreateProcess(imageTake)
        } else {
            val codeClient = UUID.randomUUID().toString()
            val solicitudProceso = SolicitudProceso()
            if(advisor.isNullOrEmpty()){
                solicitudProceso.asesor = "testing"
            }else{
                solicitudProceso.asesor = advisor
            }
            solicitudProceso.guidConv = LibraryReconoSer.getGuidConv()
            if(advisor.isNullOrEmpty()){
                solicitudProceso.sede = "931135"
            }else{
                solicitudProceso.sede = campus
            }
            solicitudProceso.codigoCliente = codeClient
            solicitudProceso.infCandidato = ""
            solicitudProceso.isFinalizado = false
            val ciudadano =
                Ciudadano()
            ciudadano.tipoDoc = typeDocument?.toUpperCase()
            ciudadano.numDoc = numDocument
            ciudadano.email = ""
            ciudadano.celular = ""
            solicitudProceso.ciudadano = ciudadano
            solicitudProceso.estado = 2
            ServicesOlimpia.getInstance()
                .getProcessRequest(solicitudProceso, object : CallbackSolicitudProceso {
                    override fun onSuccess(solicitudProcesoOut: SolicitudProcesoOut) {
                        mvpView?.onCreateProcess(imageTake)
                        //onValidateBiometry(imageTake)
                    }

                    override fun onError(respuestaTransaccion: RespuestaTransaccion) {
                        mvpView?.onFinishError(respuestaTransaccion)
                    }
                })
        }
    }
}