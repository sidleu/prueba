package com.reconosersdk.reconosersdk.ui.document.presenters

import android.content.Context
import android.os.Bundle
import com.reconosersdk.reconosersdk.entities.DataReadDocument
import com.reconosersdk.reconosersdk.entities.DataResult
import com.reconosersdk.reconosersdk.http.regula.entities.`in`.Anverso
import com.reconosersdk.reconosersdk.ui.base.BasePresenter
import com.reconosersdk.reconosersdk.ui.document.interfaces.ExtraDocumentContract
import com.reconosersdk.reconosersdk.utils.Constants
import com.reconosersdk.reconosersdk.utils.ImageUtils
import com.reconosersdk.reconosersdk.utils.IntentExtras
import com.reconosersdk.reconosersdk.utils.Miscellaneous
import timber.log.Timber
import java.util.*

const val NOTHING_VALUE = -1

class ExtraDocumentPresenterImpl<V : ExtraDocumentContract.ExtraDocumentMvpView> :
    BasePresenter<V>(), ExtraDocumentContract.ExtraDocumentPresenter<V> {

    private var context: Context? = null

    private var resultsData: MutableList<DataReadDocument> = ArrayList()
    private var dataResult = DataResult()
    private var typeDocument: String? = null
    private var typeExtraValue: Int? = NOTHING_VALUE
    private var guidProcesoConvenio: String? = ""
    private var formato: String? = ""
    private var usuario: String? = ""
    private var idServicio: Int? = 0
    private var subTipo: Int? = 0
    private var guidBillProcess: String? = ""
    private var numLicencia: String? = ""
    private var numMedPreventiva: String? = ""
    private var guidCiu: String? = ""

    override fun initUI(extras: Bundle) {
        extras.let {
            typeExtraValue = extras.getInt(IntentExtras.VALIDATE_RECEIPT, NOTHING_VALUE)
            guidProcesoConvenio = extras.getString(IntentExtras.GUID_PROCESS_AGREEMENT, "")
            /*formato = extras.getString(IntentExtras.FORMAT, "")
            usuario = extras.getString(IntentExtras.USER, "")
            idServicio = extras.getInt(IntentExtras.IDSERVICE, 0)
            subTipo = extras.getInt(IntentExtras.SUBTYPE, 0)
            guidBillProcess = extras.getString(IntentExtras.GUIDBILLPROCESS, "")
            numLicencia = extras.getString(IntentExtras.NUM_LICENSE, "")
            numMedPreventiva = extras.getString(IntentExtras.NUM_PREVENTIVE_MED, "")*/
        }

        when (typeExtraValue) {
            0 -> mvpView!!.initUI("Validar Recibo", typeExtraValue!!)
            1 -> mvpView!!.initUI("Validar Licencia de conducción", typeExtraValue!!)
            2 -> mvpView!!.initUI("Validar Licencia Federal de conducción", typeExtraValue!!)
            else -> { // Note the block
                mvpView!!.initUI(
                    "Ahora ubica el reverso de tu documento dentro de la máscara",
                    typeExtraValue!!
                )
            }
        }
    }

    override fun onLoadImage(imageCamera: String, context: Context) {
        Timber.e("ON LOAD IMAGE")
        this.context = context
        val imageCompressFront = Miscellaneous.getRescaledImage(Constants.HEIGHT_DOCUMENT, Constants.WIDTH_DOCUMENT, Constants.MAX_QUALITY, imageCamera)
        ///////////////////////////////
        val anverso = Anverso()
        anverso.valor = ImageUtils.getEncodedBase64FromFilePath(imageCompressFront!!.absolutePath).replace("\n", "")
        anverso.formato = "JPG_B64"

        when (typeExtraValue) {
            1 -> validateReceipt(anverso, imageCompressFront.absolutePath)
            2 -> validateReceipt(anverso, imageCompressFront.absolutePath)
            else -> validateReceipt(anverso, imageCompressFront.absolutePath)
        }
    }

    private fun validateReceipt(anverso: Anverso, imageCompressFront: String) {
        //Return image and base64 value
        mvpView!!.onFinishData(
            imageCompressFront,
            anverso.valor
        )
    }


}