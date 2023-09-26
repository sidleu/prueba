package com.reconosersdk.reconosersdk.ui.document.presenters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import com.google.gson.Gson
import com.reconosersdk.reconosersdk.entities.DataReadDocument
import com.reconosersdk.reconosersdk.entities.DataResult
import com.reconosersdk.reconosersdk.entities.Document
import com.reconosersdk.reconosersdk.http.api.WebService
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.DocumentValidations
import com.reconosersdk.reconosersdk.http.regula.entities.`in`.Anverso
import com.reconosersdk.reconosersdk.http.regula.entities.`in`.Reverso
import com.reconosersdk.reconosersdk.http.regula.entities.`in`.ValidarDocumentoGenericoIn
import com.reconosersdk.reconosersdk.http.regula.entities.out.ValidarDocumentoGenericoOut
import com.reconosersdk.reconosersdk.ui.LibraryReconoSer
import com.reconosersdk.reconosersdk.ui.base.BasePresenter
import com.reconosersdk.reconosersdk.ui.document.interfaces.GeneralDocumentContract
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.ServicesOlimpia
import com.reconosersdk.reconosersdk.utils.*
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException

class GeneralDocumentPresenterImpl<V : GeneralDocumentContract.GeneralDocumentMvpView> :
    BasePresenter<V>(), GeneralDocumentContract.GeneralDocumentPresenter<V> {

    private var context: Context? = null

    private var resultsData: MutableList<DataReadDocument> = ArrayList()
    private var dataResult = DataResult()
    private var typeDocument: String? = null
    private var guidProcesoConvenio: String? = null
    private var guidCiu: String? = null

    override fun initUI(extras: Bundle) {
        extras.let {
            typeDocument = extras.getString(IntentExtras.TYPE_DOCUMENT, null).toUpperCase()
            guidProcesoConvenio = extras.getString(IntentExtras.GUID_PROCESS_AGREEMENT, null)
            guidCiu = extras.getString(IntentExtras.GUID_CIUDADANO, null)
        }
    }

    override fun onLoadImage(imageTakeFront: String, imageTakeBack: String, context: Context) {
        Timber.e("ON LOAD IMAGE")
        this.context = context
        val imageCompressFront = Miscellaneous.getRescaledImage(Constants.HEIGHT_DOCUMENT, Constants.WIDTH_DOCUMENT, Constants.MAX_QUALITY, imageTakeFront)
        val imageCompressBack = Miscellaneous.getRescaledImage(Constants.HEIGHT_DOCUMENT, Constants.WIDTH_DOCUMENT, Constants.MAX_QUALITY, imageTakeBack)
        ///////////////////////////////
        val anverso = Anverso()
        val reverso = Reverso()
        anverso.valor = ""
        anverso.formato = "JPG_64"
        reverso.valor = ""
        reverso.formato = "JPG_64"


        var convenioGuid = LibraryReconoSer.getGuidConv()
        var usuario = "movil"

        if(typeDocument == "DNIP"){
            convenioGuid = "afbc886e-072d-4dcd-8275-c6944adf5d2e"
            usuario = "echamelamanoPeruMovil"
            anverso.formato = "jpeg"
            anverso.formato = "jpeg"
        }

        val validarDocumentoGenericoIn = ValidarDocumentoGenericoIn(
            convenioGuid,
            typeDocument,
            anverso,
            reverso,
            usuario,
            guidProcesoConvenio,
            guidCiu
        )

        //To show easily the JSON request
        Timber.e("ValidarDoumentoGenerico is:  %s", validarDocumentoGenericoIn.toString())
        val gson = Gson()
        Timber.e("ValidarDoumentoGenerico is:  %s",  gson.toJson(validarDocumentoGenericoIn, ValidarDocumentoGenericoIn::class.java))
        anverso.valor = ImageUtils.getEncodedBase64FromFilePath(imageCompressFront!!.absolutePath).replace("\n", "")
        reverso.valor = ImageUtils.getEncodedBase64FromFilePath(imageCompressBack!!.absolutePath).replace("\n", "")
        validarDocumentoGenericoIn.anverso!!.valor= anverso.valor
        validarDocumentoGenericoIn.reverso!!.valor = reverso.valor

        WebService.getInstance().serviceOlimpiaApi.getDocumentValidation(validarDocumentoGenericoIn)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Response<ValidarDocumentoGenericoOut?>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(response: Response<ValidarDocumentoGenericoOut?>) {
                    if (response.isSuccessful && response.body()!!.respuestaTransaccion?.esExitosa!!) {
                        val documentValidations = DocumentValidations();
                        //resultsData = emptyList<DataReadDocument>() as MutableList<DataReadDocument>
                        resultsData.add(
                            DataReadDocument(
                                "data",
                                JsonUtils.stringObject(response.body()!!.data!!)
                            )
                        )
                        resultsData.add(
                            DataReadDocument(
                                "procesoConvenioGuid",
                                JsonUtils.stringObject(response.body()!!.procesoConvenioGuid!!)
                            )
                        )
                        resultsData.add(
                            DataReadDocument(
                                "ciudadanoGuid",
                                JsonUtils.stringObject(response.body()!!.ciudadanoGuid!!)
                            )
                        )
                        resultsData.add(
                            DataReadDocument(
                                "respuestaTransaccion",
                                JsonUtils.stringObject(response.body()!!.respuestaTransaccion?.esExitosa!!)
                            )
                        )
                        dataResult.setResult(resultsData)
                        val document = Document(
                            null, null, "", Constants.MEXICAN_OBVERSE_DOCUMENT.toString(),
                            "", dataResult, documentValidations
                        )
                        mvpView!!.onFinishData(
                            imageCompressFront.absolutePath,
                            imageCompressBack.absolutePath,
                            response.body()!!
                        )
                    } else {
                        val itemError = response.body()?.let { body ->
                            body.respuestaTransaccion?.let { respuestaTransaccion ->
                                respuestaTransaccion.errorEntransaccion?.get(0).let { item ->
                                    item?.let { ServicesOlimpia.getInstance().getErrorCustom(
                                        isNullOrEmptyValues(it.codigo), isNullOrEmptyValues(it.descripcion)) }
                                }
                            }
                        }
                        if (itemError != null) {
                            mvpView!!.onFinishError(itemError)
                        } else {
                            mvpView!!.onFinishError(Constants.ERROR_R118)
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    if (e.toString() == "timeout" || e.toString().contains("timed out")) {
                        mvpView!!.onFinishError(Constants.ERROR_R116)
                    } else {
                        mvpView!!.onFinishError(Constants.ERROR_R100)
                    }
                }
            })
    }

    private fun generateCsvFile(sFileName: String, msg: String) {
        try {
            val root: File = Environment.getExternalStorageDirectory()
            val dir = File(root.absolutePath + "/Metrics")
            dir.mkdirs()
            val gpxfile = File(dir, sFileName)
            val writer = FileWriter(gpxfile, true)
            if (gpxfile.length().toInt() == 0) {
                writer.append("type,confidence,confidenceTime (ms),colorfulness,colorfulnessTime (ms),contrast,contrastTime (ms),brightness,brightnessTime (ms),sharpness,sharpnessTime (ms),rows,cols,EXITOSO/NO EXITOSO,nombre fichero")
                writer.append('\n')
            }
            writer.append(msg)
            writer.append('\n')
            writer.flush()
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun saveImage(imageTake: String) {
        var bmp = BitmapFactory.decodeFile(imageTake)
        //bmp = OpenCVUtils.cutROI(bmp)
        var outputStream: FileOutputStream? = null
        val file = Environment.getExternalStorageDirectory()
        val dir = File(file.absolutePath + "/Metrics")
        dir.mkdirs()
        val filename = String.format("not_compressed${imageTake?.substring(imageTake.length - 13)}")
        //val filename = "PERRITO"
        val outFile = File(dir, filename)
        try {
            outputStream = FileOutputStream(outFile)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        bmp.compress(Bitmap.CompressFormat.WEBP, 100, outputStream)
        try {
            outputStream!!.flush()
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isNullOrEmptyValues(myValue : String?) : String{
        return if(myValue.isNullOrEmpty()){
            ""
        }else{
            myValue
        }
    }
}