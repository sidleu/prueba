package com.reconosersdk

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.reconosersdk.databinding.ActivityValidationServicesBinding
import com.reconosersdk.reconosersdk.citizens.barcode.ColombianCitizenBarcode
import com.reconosersdk.reconosersdk.citizens.colombian.ColombianCCD
import com.reconosersdk.reconosersdk.citizens.colombian.ColombianOCR
import com.reconosersdk.reconosersdk.entities.Document
import com.reconosersdk.reconosersdk.http.OlimpiaInterface
import com.reconosersdk.reconosersdk.http.OlimpiaInterface.CallbackSaveBiometry
import com.reconosersdk.reconosersdk.http.OlimpiaInterface.CallbackValidateBiometry
import com.reconosersdk.reconosersdk.http.aceptarATDP.`in`.AceptarAtdpIn
import com.reconosersdk.reconosersdk.http.aceptarATDP.out.AceptarAtdpOut
import com.reconosersdk.reconosersdk.http.consultAgreementProcess.out.ConsultAgreementProcessOut
import com.reconosersdk.reconosersdk.http.consultSteps.`in`.ConsultStepsIn
import com.reconosersdk.reconosersdk.http.consultSteps.out.ConsultStepsOut
import com.reconosersdk.reconosersdk.http.consultValidation.`in`.ConsultValidationIn
import com.reconosersdk.reconosersdk.http.consultValidation.out.ConsultValidationOut
import com.reconosersdk.reconosersdk.http.login.`in`.LoginIn
import com.reconosersdk.reconosersdk.http.login.out.LoginOut
import com.reconosersdk.reconosersdk.http.obtainDataEasyTrack.`in`.ObtainDataEasyTrackIn
import com.reconosersdk.reconosersdk.http.obtainDataEasyTrack.out.ObtainDataEasyTrackOut
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.GuardarBiometriaIn
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.ValidarBiometriaIn
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.GuardarBiometria
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.HeaderToken
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidarBiometria
import com.reconosersdk.reconosersdk.http.regula.entities.out.ValidarDocumentoGenericoOut
import com.reconosersdk.reconosersdk.http.requestValidation.`in`.CiudadanoData
import com.reconosersdk.reconosersdk.http.requestValidation.`in`.RequestValidationIn
import com.reconosersdk.reconosersdk.http.requestValidation.out.RequestValidationOut
import com.reconosersdk.reconosersdk.http.saveDocumentSides.`in`.*
import com.reconosersdk.reconosersdk.http.saveDocumentSides.out.SaveDocumentSidesOut
import com.reconosersdk.reconosersdk.ui.bioFacial.views.LivePreviewActivity
import com.reconosersdk.reconosersdk.ui.document.views.GeneralDocumentActivity
import com.reconosersdk.reconosersdk.ui.document.views.RequestDocumentActivity
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.ServicesOlimpia
import com.reconosersdk.reconosersdk.utils.*
import com.reconosersdk.reconosersdk.utils.Constants.Companion.CHILE
import com.reconosersdk.reconosersdk.utils.Constants.Companion.COLOMBIA
import com.reconosersdk.reconosersdk.utils.Constants.Companion.COSTA_RICAN
import com.reconosersdk.reconosersdk.utils.Constants.Companion.ECUADOR
import com.reconosersdk.reconosersdk.utils.Constants.Companion.EL_SALVADOR
import com.reconosersdk.reconosersdk.utils.Constants.Companion.GUATEMALA
import com.reconosersdk.reconosersdk.utils.Constants.Companion.HONDURAS
import com.reconosersdk.reconosersdk.utils.Constants.Companion.MEXICO
import com.reconosersdk.reconosersdk.utils.Constants.Companion.PANAMA
import com.reconosersdk.utils.Utils
import org.json.JSONObject
import timber.log.Timber
import java.util.*

//To intents
const val DOCUMENT_FRONT = 1
const val DOCUMENT_BACK = 2

const val BARCODE_DOCUMENT = 3
const val GENERAL_DOCUMENT = 5
const val IMAGE_TYPE = "JPG_B64"
const val FACE = 6
const val CONFIGURATION = 7

const val QUALITY = 75

//To flow
const val SAVE_BIOMETRY = 1
const val VALIDATE_BIOMETRY = 2
const val FACE_BIOMETRY = 5
const val DOCUMENT_BIOMETRY = 4
const val NEW_SERVICE_ID = 5

const val FORCE_DOCUMENT_READING = "ForzarLecturaMovil"

class ValidationServicesActivity : AppCompatActivity() {

    //Every services to implement and use
    private val SOLICITUD_VALIDACION = "SOLICITUD VALIDACION"
    private val CONSULTAR_PASOS = "CONSULTAR PASOS"
    private val CONSULTAR_VALIDACION = "CONSULTAR VALIDACION"
    private val GUARDAR_AMBAS_CARAS = "GUARDAR AMBAS CARAS"

    private val BLACK_BOX = "BLACK_BOX"

    //EasyTrack Test
    private val LOGIN = "LOGIN"
    private val OBTENER_DATA_EASY_TRACK = "OBTENER DATA EASYTRACK"
    //EasyTrack Test
    private val ATDP_ACCPET = "ACEPTAR ATDP"
    private val ANOTHER_OTP = "OTRO OTP"

    private lateinit var binding: ActivityValidationServicesBinding
    private var opSelected: Int = 0
    private var mProgressDialog: ProgressDialog? = null

    //Variables
    private var procesoConvenioGuid: String? = ""
    private var guidCiudadano: String? = ""
    private var user: String? = ""
    private var pass: String? = ""
    private var pathFront: String? = ""
    private var pathBack: String? = ""
    private var docFront: String? = ""
    private var docBack: String? = ""
    private var numDoc: String? = ""
    private var typeDoc: String? = ""
    private var globalTypeDocument: String? = ""

    //Black box
    private var userBlackBox: String? = ""
    private var passBlackBox: String? = ""
    private var numDocBlackBox: String? = ""
    private var typeDocBlackBox: String? = ""
    private var procesoConvenioGuidBlacBox: String? = ""
    private var guidCiudadanoBlackBox: String? = ""
    private var COLOMBIAN_DIG_ID = "CCD"
    private var COLOMBIAN_CC = "CC"

    //Black box probe
    private var GUID_CONV_BLACK_BOX = "414bb638-f052-4b67-b8d7-fca55776865c"
    private var USER_BLACK_BOX = "UsuarioMovil"
    private var PASS_BLACK_BOX = "12345678"
    private var ADVISER = "Android"
    private var CAMPUS = "Android"
    //Banco Agrario
    /*private var GUID_CONV_BLACK_BOX = "b403b5f-a49a-412a-b6f8-52d27caf6835"
    private var USER_BLACK_BOX = "BancoAgrarioMovil_2022"
    private var PASS_BLACK_BOX = "BancoAgrarioMovil.2022*"

    private var ADVISER = "BancoAgrarioMovil_2022"
    private var CAMPUS = "BancoAgrarioMovil_2022"*/

    //OWOTECH DEVELOP
    /*private var GUID_CONV_BLACK_BOX = "e5eec12a-0e4b-43a6-8340-1360c454ec28"
    private var USER_BLACK_BOX = "OWOTWCH_MOVIL2022"
    private var PASS_BLACK_BOX = "OWOTWCH_MOVIL.2022*"

    private var ADVISER = "Android"
    private var CAMPUS = "Android"*/

    //OWOTECH PRODUCTION
    /*private var GUID_CONV_BLACK_BOX = "4b403b5f-a49a-412a-b6f8-52d27caf6835"
    private var USER_BLACK_BOX = "OWOTWCHM"
    private var PASS_BLACK_BOX = "L6VgKqKpVDCrD0U"*/

    //Custaclan
    private var GUID_CONV_BLACK_BOX_CUSTACLAN = "94d66245-dce4-4c87-bb7b-f2fcbfb15a83"
    private var USER_BLACK_BOX_CUSTACLAN = "BCUSTACM"
    private var PASS_BLACK_BOX_CUSTACLAN = "CUSTACLAN-2022*"
    private var name1: String? = ""
    private var name2: String? = ""
    private var lastName1: String? = ""
    private var lastName2: String? = ""

    //To Generic Document
    private var respondGenericDocument: ValidarDocumentoGenericoOut? = null

    //To Colombian Document
    private val colombianOCR: ColombianOCR = ColombianOCR.getInstance()!!
    private val colombianCCD: ColombianCCD = ColombianCCD.getInstance()!!
    private val colombianCitizenBarcode : ColombianCitizenBarcode = ColombianCitizenBarcode.getInstance()!!

    private var isValidateBiometry = false
    private var blackBoxActivate = false
    private var guidBio: String? = ""

    //Log Document OCR and barcode
    private var logOcrAnverso = LogOcrAnverso()
    private var logOcrReverso = LogOcrReverso()
    private var logBarcodeReader = LogBarcodeReader()

    private var docState = 0

    //For easytrack
    var newToken: String = ""

    //Singleton
    var headerToken = HeaderToken.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validation_services)

        val yourIcon =
            resources.getDrawable(R.drawable.back, null)
        supportActionBar!!.setHomeAsUpIndicator(yourIcon)

        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val inflator = LayoutInflater.from(this)
        val v = inflator.inflate(R.layout.titleview, null)

        (v.findViewById<View>(R.id.title) as TextView).text = this.title
        supportActionBar!!.customView = v

        binding = DataBindingUtil.setContentView(this, R.layout.activity_validation_services)

        setSpinner()
        setButtonsClick()
        setCountryPicker()
        //To set to first time
        setCountrySpinner()
    }

    private fun setButtonsClick() {
        binding.buttonProcess.setOnClickListener {
            Miscellaneous.hideKeyboard(this)
            createServices()
        }
    }

    private fun setSpinner() {
        val optionsServiceValidation = getServiceValidation()
        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.spinner_style, optionsServiceValidation)
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.custom_spinner)
        binding.spinnerOpenSource.adapter = dataAdapter
        binding.spinnerOpenSource.setSelection(0)
        binding.spinnerOpenSource.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    opSelected = spinnerPosition(p0?.getItemAtPosition(position).toString())
                    validationSpinner(p0?.selectedItem.toString())
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //No implementation needed
                }
            }
    }

    private fun setCountryPicker() {
        binding.ccpCodeCountryNationality.setOnCountryChangeListener {
            setCountrySpinner()
        }
    }

    private fun setCountrySpinner() {
        val optionsCountry = getEveryGeneralDocuments()
        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.spinner_style, optionsCountry)
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.custom_spinner)
        binding.spinnerGeneralDocument.adapter = dataAdapter
        binding.spinnerGeneralDocument.setSelection(0)
        binding.spinnerGeneralDocument.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Utils.hideKeyboard(this@ValidationServicesActivity)
                    globalTypeDocument = p0?.selectedItem.toString()
                    binding.etxTypeDoc.setText(p0?.selectedItem.toString())
                    validationSpinner(p0?.selectedItem.toString())
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //No implementation needed
                }
            }
    }

    private fun validationSpinner(selectionItem: String) {
        Miscellaneous.hideKeyboard(this)
        when (selectionItem) {
            CONSULTAR_PASOS -> setConsultStep()
            SOLICITUD_VALIDACION -> setRequestValidation()
            CONSULTAR_VALIDACION -> setConsultValidation()
            GUARDAR_AMBAS_CARAS -> setDocumentBothFaces()
            LOGIN -> setLogin()
            OBTENER_DATA_EASY_TRACK -> onGetObtainDataEasyTrack()
            ATDP_ACCPET -> setAcceptAtdp()
            else -> setBlackBox()
        }
    }

    private fun onGetObtainDataEasyTrack() {
        //Info usuario
    }

    private fun setLogin() {
        //Cajas usuario
    }


    @SuppressLint("SetTextI18n")
    private fun setRequestValidation() {
        binding.linearUser.visibility = View.VISIBLE
        binding.subGeneralLinear.visibility = View.VISIBLE
        binding.imgBack.visibility = View.GONE
        binding.imgFront.visibility = View.GONE
        binding.imgFace.visibility = View.GONE
        binding.tvGuidCv.text = "GuidConvenio"
        binding.etxGuidCv.setText("97464498-D532-4964-ABF7-81DC0F884B21")
        binding.etxGuidCv.hint = "Escriba el GuidConvenio"
        //Default user
        binding.etxUser.setText("testingMobile")
        //Default pass
        binding.etxPass.setText("12345")
    }

    @SuppressLint("SetTextI18n")
    private fun setConsultStep() {
        binding.subGeneralLinear.visibility = View.GONE
        binding.linearUser.visibility = View.GONE
        binding.imgBack.visibility = View.GONE
        binding.imgFront.visibility = View.GONE
        binding.imgFace.visibility = View.GONE
        binding.tvGuidCv.text = "guidProcesoConvenio"
        if (procesoConvenioGuid.isNullOrEmpty()) {
            binding.etxGuidCv.setText("319FE7B9-8282-43E1-9E5F-59569C3E3DA8")
        } else {
            binding.etxGuidCv.setText(procesoConvenioGuid)
        }
        binding.etxGuidCv.hint = "Escriba el guidProcesoConvenio"
    }

    @SuppressLint("SetTextI18n")
    private fun setConsultValidation() {
        binding.linearUser.visibility = View.VISIBLE
        binding.subGeneralLinear.visibility = View.GONE
        binding.imgBack.visibility = View.GONE
        binding.imgFront.visibility = View.GONE
        binding.imgFace.visibility = View.GONE
        binding.tvGuidCv.text = "procesoConvenioGuid"
        binding.etxGuidCv.setText("0344FF8C-08B7-40E1-A8C8-79AAD64FAB11")
        if (procesoConvenioGuid.isNullOrEmpty()) {
            binding.etxGuidCv.setText("0344FF8C-08B7-40E1-A8C8-79AAD64FAB11")
        } else {
            binding.etxGuidCv.setText(procesoConvenioGuid)
        }
        binding.etxGuidCv.hint = "Escriba el procesoConvenioGuid"
        //Default user
        binding.etxUser.setText("jsviveroj")
        //Default pass
        binding.etxPass.setText("JVP@\$\$w0rd")
    }

    @SuppressLint("SetTextI18n")
    private fun setDocumentBothFaces() {
        binding.linearUser.visibility = View.VISIBLE
        binding.subGeneralLinear.visibility = View.VISIBLE
        binding.imgBack.visibility = View.VISIBLE
        binding.imgFront.visibility = View.VISIBLE
        binding.tvGuidCv.text = "guidProcesoConvenio"
        binding.etxGuidCv.hint = "Escriba el guidProcesoConvenio"
        //Avoid nulls
        if (procesoConvenioGuid.isNullOrEmpty()) {
            binding.etxGuidCv.setText("319FE7B9-8282-43E1-9E5F-59569C3E3DA8")
        } else {
            binding.etxGuidCv.setText(procesoConvenioGuid)
        }
        //Default user
        if (user.isNullOrEmpty()) {
            binding.etxUser.setText("testingMoblie")
        } else {
            binding.etxUser.setText(user)
        }
        //Default pass
        if (pass.isNullOrEmpty()) {
            binding.etxPass.setText("12345")
        } else {
            binding.etxPass.setText(pass)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setAcceptAtdp() {
        binding.subGeneralLinear.visibility = View.GONE
        binding.linearUser.visibility = View.GONE
        binding.imgBack.visibility = View.GONE
        binding.imgFront.visibility = View.GONE
        binding.imgFace.visibility = View.GONE
        binding.tvGuidCv.text = "guidProcesoConvenio"
        if (procesoConvenioGuid.isNullOrEmpty()) {
            binding.etxGuidCv.setText("319FE7B9-8282-43E1-9E5F-59569C3E3DA8")
        } else {
            binding.etxGuidCv.setText(procesoConvenioGuid)
        }
        binding.etxGuidCv.hint = "Escriba el guidProcesoConvenio"
    }

    private fun setBlackBox() {
        binding.linearUser.visibility = View.VISIBLE
        binding.subGeneralLinear.visibility = View.VISIBLE
        binding.imgBack.visibility = View.VISIBLE
        binding.imgFront.visibility = View.VISIBLE
        binding.imgFace.visibility = View.VISIBLE
        binding.tvGuidCv.text = "GuidConvenio BLACK BOX"
        binding.etxGuidCv.setText(GUID_CONV_BLACK_BOX)
        binding.etxGuidCv.hint = "Escriba el GuidConvenio BLACK BOX"
        //Default user
        binding.etxUser.setText(USER_BLACK_BOX)
        //Default pass
        binding.etxPass.setText(PASS_BLACK_BOX)
    }

    //To easily test
    private fun getServiceValidation(): Array<String?> {
        return applicationContext.resources.getStringArray(R.array.service_validation_options)
    }

    //To easily test
    private fun getEveryGeneralDocuments(): Array<String?> {
        return when (binding.ccpCodeCountryNationality.selectedCountryName) {
            EL_SALVADOR -> applicationContext.resources.getStringArray(R.array.validate_general_document_el_salvador)
            GUATEMALA -> applicationContext.resources.getStringArray(R.array.validate_general_document_guatemalan)
            HONDURAS -> applicationContext.resources.getStringArray(R.array.validate_general_document_honduras)
            COSTA_RICAN -> applicationContext.resources.getStringArray(R.array.validate_general_document_costa_rican)
            COLOMBIA -> applicationContext.resources.getStringArray(R.array.validate_general_document_colombian)
            PANAMA -> applicationContext.resources.getStringArray(R.array.validate_general_document_panamanian)
            ECUADOR -> applicationContext.resources.getStringArray(R.array.validate_general_document_ecuadorian)
            CHILE -> applicationContext.resources.getStringArray(R.array.validate_general_document_chilean)
            MEXICO -> applicationContext.resources.getStringArray(R.array.validate_general_document_mexican)
            else -> applicationContext.resources.getStringArray(R.array.validate_general_document_peruvian)
        }
    }

    private fun spinnerPosition(value: String): Int {
        return when (value) {
            SOLICITUD_VALIDACION -> 0
            CONSULTAR_PASOS -> 1
            CONSULTAR_VALIDACION -> 2
            GUARDAR_AMBAS_CARAS -> 3
            BLACK_BOX -> 4
            LOGIN -> 5
            OBTENER_DATA_EASY_TRACK -> 6
            else -> {
                7
            }
        }
    }

    private fun createServices() {
        //If the user select verify the document
        blackBoxActivate = opSelected == 4
        when (opSelected) {
            0 -> onRequestValidation()
            1 -> onConsultSteps()
            2 -> onConsultValidation()
            3 -> verifyFrontDocument()
            4 -> startBlackBox()
            5 -> onLogin()
            6 -> onObtainDataEasyTrack()
            else -> {
                onAcceptAtdp()
            }
        }
    }

    private fun onAcceptAtdp() {
        AlertDialog.Builder(this)
            .setMessage("Debe aceptar termino y condiciones")
            .setCancelable(false)
            .setPositiveButton("SISAS")
            { _, _ -> onFinallyAcceptAtdp() }
            .setNegativeButton("NOCAS")
            {_, _ -> onShowRejected() }
            .show()
    }

    private fun onShowRejected() {
        Toast.makeText(applicationContext, "Atdp declinado", Toast.LENGTH_SHORT).show()
        emptyValues()
    }

    private fun onFinallyAcceptAtdp() {

        loading("Aceptar ATDP")
        if ((procesoConvenioGuid.isNullOrEmpty())) {
            binding.textViewProcess.text = "Primero solicite la validación con sus datos"
            dismissProgressDialog()
        } /*else if ((procesoConvenioGuidBlacBox.isNullOrEmpty())) {
            binding.textViewProcess.text =
                "Primero solicite la validación con sus datos con el blackBox"
            dismissProgressDialog()
        } */else {

            var aceptarAtdpIn = AceptarAtdpIn()
            aceptarAtdpIn.guidProcesoConvenio = procesoConvenioGuid
            aceptarAtdpIn.aceptar = true
            aceptarAtdpIn.validadorWeb = true
            aceptarAtdpIn.trazabilidad = null


            ServicesOlimpia.getInstance().getAcceptAtdp(aceptarAtdpIn,
                object :
                    OlimpiaInterface.CallbackAceptarAtdp {
                    @SuppressLint("LogNotTimber")
                    override fun onSuccess(aceptarAtdpOut: AceptarAtdpOut?) {
                        aceptarAtdpOut?.let {
                             binding.textViewProcess.text = JsonUtils.stringObject(aceptarAtdpOut)
                            Timber.e("Atdp Aceptado")
                            dismissProgressDialog()
                            Toast.makeText(applicationContext, "Atdp Aceptado", Toast.LENGTH_SHORT).show()
                        }

                    }

                    override fun onError(transactionResponse: RespuestaTransaccion?) {
                        binding.textViewProcess.text = JsonUtils.stringObject(transactionResponse!!)
                        dismissProgressDialog()
                        Toast.makeText(applicationContext, "Atdp declinado", Toast.LENGTH_SHORT).show()
                    }

                })
            emptyValues()
        }
    }

    private fun onObtainDataEasyTrack() {

        //overwrite HeaderToken
        headerToken.accessToken = newToken

        val stringProbe = "{\"email\":\"leommorenol@gmail.com\"}"
        val gson = Gson()
        val obtainDataEasyTrackIn = gson.fromJson(
            stringProbe,
            ObtainDataEasyTrackIn::class.java
        )

        ServicesOlimpia.getInstance().getObtainDataEasyTrack(obtainDataEasyTrackIn,
            object :
                OlimpiaInterface.CallbackObtainDataEasyTrack {
                override fun onSuccess(obtainDataEasyTrackOut: ObtainDataEasyTrackOut?) {
                    obtainDataEasyTrackOut?.let {
                        binding.textViewProcess.text = JsonUtils.stringObject(it)
                        dismissProgressDialog()
                    }
                }

                override fun onError(transactionResponse: RespuestaTransaccion?) {
                    binding.textViewProcess.text = JsonUtils.stringObject(transactionResponse!!)
                    dismissProgressDialog()
                }

            })
        emptyValues()
    }

    private fun onLogin() {
        val stringProbe = "{\"Email\":\"UConsulta\",\"Password\":\"12345678\"}"
        //EasyTrack develop
        //val stringProbe ="{\"Email\":\"UConsulta\",\"Password\":\"12345678\"}"
        //EasyTrack production
        //val stringProbe ="{\"Email\":\"EasyTrack\",\"Password\":\"EasyTrack.2022*\"}"
        val gson = Gson()
        val loginIn = gson.fromJson(
            stringProbe,
            LoginIn::class.java
        )

        ServicesOlimpia.getInstance().getLogin(loginIn,
            object :
                OlimpiaInterface.CallbackLogin {
                @SuppressLint("LogNotTimber")
                override fun onSuccess(loginOut: LoginOut?) {
                    loginOut?.let {
                        binding.textViewProcess.text = JsonUtils.stringObject(it)
                        newToken = loginOut.accessToken!!
                        Log.e("Login token", newToken)
                        dismissProgressDialog()
                    }
                }

                override fun onError(transactionResponse: RespuestaTransaccion?) {
                    binding.textViewProcess.text = JsonUtils.stringObject(transactionResponse!!)
                    dismissProgressDialog()
                }

            })
        emptyValues()
    }


    private fun verifyFrontDocument() {
        //Restart the value
        docState = 0
        val intent = Intent(this, RequestDocumentActivity::class.java)
        intent.putExtra(IntentExtras.TEXT_SCAN, "Anverso")
        if (isValidateBiometry) {
            intent.putExtra(IntentExtras.GUID_CIUDADANO, guidCiudadanoBlackBox)
            intent.putExtra(IntentExtras.TYPE_DOCUMENT, typeDocBlackBox)
            intent.putExtra(IntentExtras.NUM_DOCUMENT, numDocBlackBox)
            intent.putExtra(IntentExtras.SAVE_USER, userBlackBox)
        } else {
            intent.putExtra(IntentExtras.GUID_CIUDADANO, "97464498-D532-4964-ABF7-81DC0F884B21")
            intent.putExtra(IntentExtras.TYPE_DOCUMENT, binding.etxTypeDoc.text.toString())
            intent.putExtra(IntentExtras.NUM_DOCUMENT, binding.etxDoc.text.toString())
            intent.putExtra(IntentExtras.SAVE_USER, "flujo3")
        }
        intent.putExtra(IntentExtras.ADD_DATA, "")
        intent.putExtra(IntentExtras.QUALITY, QUALITY)
        startActivityForResult(intent, DOCUMENT_FRONT)
    }

    private fun verifyBackDocument() {
        val intent = Intent(this, RequestDocumentActivity::class.java)
        intent.putExtra(IntentExtras.TEXT_SCAN, "Reverso")
        if (isValidateBiometry) {
            intent.putExtra(IntentExtras.GUID_CIUDADANO, guidCiudadanoBlackBox)
            intent.putExtra(IntentExtras.TYPE_DOCUMENT, typeDocBlackBox)
            intent.putExtra(IntentExtras.NUM_DOCUMENT, numDocBlackBox)
            intent.putExtra(IntentExtras.SAVE_USER, userBlackBox)
        } else {
            intent.putExtra(IntentExtras.GUID_CIUDADANO, "97464498-D532-4964-ABF7-81DC0F884B21")
            intent.putExtra(IntentExtras.TYPE_DOCUMENT, binding.etxTypeDoc.text.toString())
            intent.putExtra(IntentExtras.NUM_DOCUMENT, binding.etxDoc.text.toString())
            intent.putExtra(IntentExtras.SAVE_USER, "flujo3")
        }
        intent.putExtra(IntentExtras.ADD_DATA, "")
        intent.putExtra(IntentExtras.QUALITY, QUALITY)
        startActivityForResult(intent, DOCUMENT_BACK)
    }


    private fun verifyForeignDocument() {
        val intent = Intent(this, GeneralDocumentActivity::class.java)
        intent.putExtra(IntentExtras.TEXT_SCAN, "Documento")
        intent.putExtra(IntentExtras.TYPE_DOCUMENT, binding.etxTypeDoc.text.toString())
        startActivityForResult(intent, GENERAL_DOCUMENT)
    }

    private fun saveBiometryBlackBox() {
        val intent = Intent(this, LivePreviewActivity::class.java)
        intent.putExtra(IntentExtras.GUID_CIUDADANO, guidCiudadanoBlackBox)
        intent.putExtra(IntentExtras.TYPE_DOCUMENT, typeDocBlackBox)
        intent.putExtra(IntentExtras.NUM_DOCUMENT, numDocBlackBox)
        intent.putExtra(IntentExtras.SAVE_USER, userBlackBox)
        intent.putExtra(IntentExtras.QUALITY, QUALITY)
        intent.putExtra(IntentExtras.ACTIVATE_FLASH, false)
        intent.putExtra(IntentExtras.CHANGE_CAMERA, false)
        intent.putExtra(IntentExtras.ADVISER, "Asesor Android SDK 2.0")
        intent.putExtra(IntentExtras.CAMPUS, "Sede Android SDK 2.0")
        startActivityForResult(intent, FACE)
    }

    private fun validateBiometryBlackBox(data: Intent) {
        val intent = Intent(this, LivePreviewActivity::class.java)
        intent.putExtra(IntentExtras.GUID_CIUDADANO, guidCiudadanoBlackBox)
        intent.putExtra(IntentExtras.VALIDATE_FACE, true)
        intent.putExtra(IntentExtras.TYPE_DOCUMENT, typeDocBlackBox)
        intent.putExtra(IntentExtras.NUM_DOCUMENT, numDocBlackBox)
        intent.putExtra(IntentExtras.SAVE_USER, userBlackBox)
        intent.putExtra(IntentExtras.QUALITY, QUALITY)
        intent.putExtra(IntentExtras.NUM_EXPRESION, data.getIntExtra(IntentExtras.NUM_EXPRESION, 1))
        intent.putExtra(IntentExtras.NUM_ATTEMPTS, data.getIntExtra(IntentExtras.NUM_ATTEMPTS, 1))
        intent.putExtra(IntentExtras.TIME, data.getIntExtra(IntentExtras.TIME, 5)) //Seconds
        intent.putExtra(IntentExtras.MOVEMENTS, data.getStringExtra(IntentExtras.MOVEMENTS))
        intent.putExtra(IntentExtras.ACTIVATE_FLASH, false)
        intent.putExtra(IntentExtras.CHANGE_CAMERA, false)
        intent.putExtra(IntentExtras.ADVISER, "Asesor Android SDK 2.0")
        intent.putExtra(IntentExtras.CAMPUS, "Android SDK 2.0")
        startActivityForResult(intent, FACE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var data = data
        super.onActivityResult(requestCode, resultCode, data)
        //binding.btnIntent.setEnabled(true)
        Timber.e("LLEGA Y EL CODIGO ES: %s", resultCode)
        if (resultCode != RESULT_CANCELED) {
            if (data == null) {
                data = Intent()
                data.putExtra(IntentExtras.ERROR_MSG, Constants.ERROR_IMAGE_PROCESS)
                data.putExtra(IntentExtras.ERROR_SDK, Constants.ERROR_R106)
                onErrorIntent(data)
            } else if (requestCode == DOCUMENT_FRONT) {
                if (resultCode == RESULT_OK) {
                    onRespondDocFront(data)
                } else if (resultCode == IntentExtras.ERROR_INTENT) {
                    docState = 1
                    onErrorIntent(data)
                }
            } else if (requestCode == DOCUMENT_BACK) {
                if (resultCode == RESULT_OK) {
                    onRespondDocBack(data)
                } else if (resultCode == IntentExtras.ERROR_INTENT) {
                    docState = 2
                    onErrorIntent(data)
                }
            } else if (requestCode == GENERAL_DOCUMENT) {
                if (resultCode == RESULT_OK) {
                    onRespondGeneralDocument(data)
                } else if (resultCode == IntentExtras.ERROR_INTENT) {
                    onErrorIntent(data)
                }
            } else if (requestCode == FACE) {
                if (resultCode == RESULT_OK) {
                    onRespondFace(data)
                } else if (resultCode == IntentExtras.ERROR_INTENT) {
                    onErrorIntent(data)
                }
            } else if (requestCode == CONFIGURATION) {
                if (resultCode == RESULT_OK) {
                    validateBiometryBlackBox(data)
                } else if (resultCode == IntentExtras.ERROR_INTENT) {
                    onErrorIntent(data)
                }
            }
        } else {
            if (data != null) {
                onErrorIntent(data)
            } else {
                data = Intent()
                data.putExtra(IntentExtras.ERROR_MSG, Constants.ERROR_IMAGE_PROCESS)
                data.putExtra(IntentExtras.ERROR_SDK, Constants.ERROR_R106)
                onErrorIntent(data)
            }
        }
    }

    private fun onRespondFace(data: Intent) {
        val pathFace = data.getStringExtra(IntentExtras.PATH_FILE_PHOTO_R)
        Glide.with(this).load(pathFace).into(binding.imgFace)
        val options = Miscellaneous.getIMGSize(pathFace!!)
        if (isValidateBiometry) {
            validateBiometryBlackBoxServices(pathFace)
            isValidateBiometry = false
        } else {
            saveBiometryBlackBoxService(pathFace)
        }
    }

    private fun saveBiometryBlackBoxService(pathFace: String?) {
        //TODO puede ser exitosa pero puede traer error "Codigo": "103", "Descripcion": "Biometría del ciudadano ya existe, no se puede crear en BD"
        if (pathFace == null || pathFace.isEmpty()) {
            dismissProgressDialog()
            Toast.makeText(this, "No hay una imagen", Toast.LENGTH_SHORT).show()
            return
        } else {
            loading("Salvar Biometria Pasos BlackBox")
            val guardarBiometriaIn = GuardarBiometriaIn()
            guardarBiometriaIn.guidConvenio = GUID_CONV_BLACK_BOX
            guardarBiometriaIn.guidCiu = guidCiudadanoBlackBox
            guardarBiometriaIn.guidProcesoConvenio = procesoConvenioGuidBlacBox
            guardarBiometriaIn.idServicio = FACE_BIOMETRY
            guardarBiometriaIn.subTipo = "Frontal"
            guardarBiometriaIn.formato = IMAGE_TYPE
            guardarBiometriaIn.datosAdi = ""
            guardarBiometriaIn.actualizar = true
            guardarBiometriaIn.usuario = userBlackBox

            //To show easily the JSON request
            Log.e("GuardarBiometria", guardarBiometriaIn.toString())
            Log.e("GuardarBiometria JPG-> ", ImageUtils.getEncodedBase64FromFilePath(pathFace))
            val gson = Gson()
            Log.e("GuardarBiometria", gson.toJson(guardarBiometriaIn, GuardarBiometriaIn::class.java))

            guardarBiometriaIn.valor = ImageUtils.getEncodedBase64FromFilePath(pathFace)

            ServicesOlimpia.getInstance()
                .guardarBiometria(guardarBiometriaIn, object : CallbackSaveBiometry {
                    override fun onSuccess(saveBiometry: GuardarBiometria) {
                        saveBiometry.let {
                            binding.textViewProcess.text = JsonUtils.stringObject(it)
                            dismissProgressDialog()
                            //activate the blackBox
                            isValidateBiometry = true
                            //to save de guidBiometry
                            guidBio = it.guidBio!!
                            if (binding.ccpCodeCountryNationality.selectedCountryName != COLOMBIA) {
                                //Foreign document
                                verifyForeignDocument()
                            } else {
                                //Colombian Document
                                verifyFrontDocument()
                            }
                        }
                    }

                    override fun onError(transactionResponse: RespuestaTransaccion) {
                        binding.textViewProcess.text = JsonUtils.stringObject(transactionResponse)
                        dismissProgressDialog()
                        blackBoxActivate = false
                    }
                })
        }
    }

    private fun validateBiometryBlackBoxServices(pathFace: String?) {
        loading("Validar Biometria Pasos BlackBox")
        //val newPathImage = Miscellaneous.getRescaledImage(Constants.HEIGHT_FACE, Constants.WIDTH_FACE, Constants.MAX_QUALITY, pathFace)!!.path
        val newPathImage = ImageUtils.compressImageHD(pathFace);
        val options = Miscellaneous.getIMGSize(newPathImage)
        val data = ValidarBiometriaIn()
        data.guidCiudadano = guidCiudadanoBlackBox
        data.guidProcesoConvenio = procesoConvenioGuidBlacBox
        data.subTipo = "Frontal"
        data.formato = IMAGE_TYPE
        data.idServicio = NEW_SERVICE_ID
        if (newPathImage != null) {
            data.biometria = ImageUtils.getEncodedBase64FromFilePath(pathFace)
        }

        ServicesOlimpia.getInstance().validarBiometria(data, object : CallbackValidateBiometry {
            override fun onSuccess(validateBiometry: ValidarBiometria) {
                validateBiometry.let {
                    binding.textViewProcess.text = JsonUtils.stringObject(it)
                    dismissProgressDialog()
                    //activate the blackBox
                    isValidateBiometry = true
                    //to save de guidBiometry
                    guidBio = ""
                    verifyFrontDocument()
                }
            }

            override fun onError(transactionResponse: RespuestaTransaccion, intentos: Int) {
                binding.textViewProcess.text = JsonUtils.stringObject(transactionResponse)
                dismissProgressDialog()
                blackBoxActivate = false
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun onRespondDocFront(data: Intent) {
        if (data == null) {
            data.putExtra(IntentExtras.ERROR_MSG, Constants.ERROR_IMAGE_PROCESS)
            data.putExtra(IntentExtras.ERROR_SDK, Constants.ERROR_R106)
            onErrorIntent(data)
        } else {
            pathFront = data.getStringExtra(IntentExtras.PATH_FILE_PHOTO)!!
            Glide.with(this).load(pathFront).into(
                binding.imgFront
            )
            dismissProgressDialog()
            if (data.extras!!.containsKey(IntentExtras.DATA_DOCUMENT)) {

                val document: Document? = data.extras!!.getParcelable(IntentExtras.DATA_DOCUMENT)
                //docFront = JsonUtils.stringObject(document!!.documentoAnverso!!)
                docFront = document!!.documentoAnverso.toString()
                //docFront = document!!.typeDocument
                validateJsonDocumentData(document)
                if (docBack.isNullOrEmpty()) {
                    verifyBackDocument()
                }
            }

        }
    }

    private fun validateJsonDocumentData(document: Document) {

        val textscan = JsonUtils.stringObject(document.textScan!!)
        //val type = JsonUtils.stringObject(document.typeDocument!!).toInt()
        val type : Int = document.typeDocument!!.toInt()

        when (type) {
            Constants.COLOMBIAN_OBVERSE_DOCUMENT -> saveCCObverseData()
            Constants.COLOMBIAN_REVERSE_DOCUMENT -> saveCCReverseData()
            Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT -> saveCCDObverseData()
            Constants.COLOMBIAN_DIGITAL_REVERSE_DOCUMENT -> saveCCDReverseData()
        }
    }

    private fun saveCCObverseData() {
        if (blackBoxActivate) {
            logOcrAnverso.ciudadanoGuid = guidCiudadanoBlackBox
        } else {
            logOcrAnverso.ciudadanoGuid = guidCiudadano
        }
        logOcrAnverso.numeroDocumento = colombianOCR.cedula.toString()
        setNamesAndLastNames(colombianOCR.names!!, colombianOCR.lastNames!!, Constants.COLOMBIAN_OBVERSE_DOCUMENT)
    }

    private fun saveCCReverseData() {
        //Set logOcrAnverso
        logOcrAnverso.sexo = colombianCitizenBarcode.sexo
        logOcrAnverso.rh = colombianCitizenBarcode.rh
        logOcrAnverso.fechaNacimiento = colombianOCR.fechaNacimiento
        logOcrAnverso.fechaExpedicionDoc = colombianOCR.fechaExpedicion
        //Set logOcrReverso
        if (blackBoxActivate) {
            logOcrReverso.ciudadanoGuid = guidCiudadanoBlackBox
        } else {
            logOcrReverso.ciudadanoGuid = guidCiudadano
        }
        logOcrReverso.numeroDocumento = colombianCitizenBarcode.cedula.toString()
        logOcrReverso.primerNombre = colombianCitizenBarcode.primerNombre
        logOcrReverso.segundoNombre = colombianCitizenBarcode.segundoNombre
        logOcrReverso.primerApellido = colombianCitizenBarcode.primerApellido
        logOcrReverso.segundoApellido =  colombianCitizenBarcode.segundoApellido
        logOcrReverso.sexo = colombianCitizenBarcode.sexo
        logOcrReverso.rh = colombianCitizenBarcode.rh
        logOcrReverso.fechaNacimiento = colombianOCR.fechaNacimiento
        logOcrReverso.fechaExpedicionDoc = colombianOCR.fechaExpedicion
        //set logBarcodeReader
        logBarcodeReader.numeroDocumento = colombianCitizenBarcode.cedula
        logBarcodeReader.primerNombre = colombianCitizenBarcode.primerNombre
        logBarcodeReader.segundoNombre = colombianCitizenBarcode.segundoNombre
        logBarcodeReader.primerApellido = colombianCitizenBarcode.primerApellido
        logBarcodeReader.segundoApellido =  colombianCitizenBarcode.segundoApellido
        logBarcodeReader.sexo = colombianCitizenBarcode.sexo
        logBarcodeReader.rh = colombianCitizenBarcode.rh
        logBarcodeReader.fechaNacimiento = colombianCitizenBarcode.fechaNacimiento
        logBarcodeReader.fechaExpedicionDoc = colombianOCR.fechaExpedicion
    }

    private fun saveCCDObverseData() {
        //Set logOcrAnverso
        if (blackBoxActivate) {
            logOcrAnverso.ciudadanoGuid = guidCiudadanoBlackBox
        } else {
            logOcrAnverso.ciudadanoGuid = guidCiudadano
        }
        logOcrAnverso.numeroDocumento = colombianCCD.cedulaObverse.toString()
        setNamesAndLastNames(colombianCCD.namesObverse!!, colombianCCD.lastNamesObverse!!, Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT)
        logOcrAnverso.rh = colombianCCD.rH
        logOcrAnverso.fechaNacimiento = colombianCCD.fechaNacimientoObverse
        logOcrAnverso.fechaExpedicionDoc = colombianCCD.fechaExpedicion
    }

    private fun saveCCDReverseData() {
        //Set logOcrAnverso
        logOcrAnverso.sexo = colombianCCD.genderString
        //Set logOcrReverso
        if (blackBoxActivate) {
            logOcrReverso.ciudadanoGuid = guidCiudadanoBlackBox
        } else {
            logOcrReverso.ciudadanoGuid = guidCiudadano
        }
        logOcrReverso.numeroDocumento = colombianCCD.cedulaReverse
        setNamesAndLastNames(colombianCCD.namesReverse!!, colombianCCD.lastNamesReverse!!, Constants.COLOMBIAN_DIGITAL_REVERSE_DOCUMENT)
        logOcrReverso.sexo = colombianCCD.genderString
        logOcrReverso.rh = "" //Doesn't exist, is empty
        logOcrReverso.fechaNacimiento = colombianCCD.fechaNacimientoReverse
        logOcrReverso.fechaExpedicionDoc = colombianCCD.fechaExpedicion
        //set logBarcodeReader
        logBarcodeReader.numeroDocumento = colombianCCD.cedulaReverse
        //same data
        logBarcodeReader.primerNombre = logOcrReverso.primerNombre
        logBarcodeReader.segundoNombre = logOcrReverso.segundoNombre
        logBarcodeReader.primerApellido = logOcrReverso.primerApellido
        logBarcodeReader.segundoApellido =  logOcrReverso.segundoApellido
        logBarcodeReader.sexo = colombianCCD.genderString
        logBarcodeReader.rh = "" //Doesn't exist, is empty
        logBarcodeReader.fechaNacimiento = colombianCCD.fechaNacimientoReverse
        logBarcodeReader.fechaExpedicionDoc = colombianCCD.fechaExpedicion
    }

    @SuppressLint("SetTextI18n")
    private fun onRespondDocBack(data: Intent) {
        if (data == null) {
            data.putExtra(IntentExtras.ERROR_MSG, Constants.ERROR_IMAGE_PROCESS)
            data.putExtra(IntentExtras.ERROR_SDK, Constants.ERROR_R106)
            onErrorIntent(data)
        } else {
            pathBack = data.getStringExtra(IntentExtras.PATH_FILE_PHOTO)!!
            Glide.with(this).load(pathBack).into(
                binding.imgBack
            )
            dismissProgressDialog()
            if (data.extras!!.containsKey(IntentExtras.DATA_DOCUMENT)) {
                val document: Document? = data.extras!!.getParcelable(IntentExtras.DATA_DOCUMENT)
                //docBack = JsonUtils.stringObject(document!!.documentoReverso!!)
                docBack = document!!.documentoReverso.toString()
                validateJsonDocumentData(document)
                val type = JsonUtils.stringObject(
                    document!!.typeDocument!!
                )
                binding.textViewProcess.text = """
                        $type
                        $docFront
                        $docBack                 
                        """.trimIndent()
                onSaveDocumentSides()
            }

        }
    }

    private fun onRespondGeneralDocument(data: Intent?) {
        if (data == null) {
            var mData = Intent()
            mData.putExtra(IntentExtras.ERROR_MSG, Constants.ERROR_IMAGE_PROCESS)
            mData.putExtra(IntentExtras.ERROR_SDK, Constants.ERROR_R106)
            onErrorIntent(mData)
        } else {
            respondGenericDocument = data.getParcelableExtra(IntentExtras.DATA_DOCUMENT)
            //Set images values
            pathFront = data.getStringExtra(IntentExtras.PATH_FILE_ANVERSO)
            Glide.with(this).load(pathFront).into(binding.imgFront)
            pathBack = data.getStringExtra(IntentExtras.PATH_FILE_REVERSO)
            Glide.with(this).load(pathBack).into(binding.imgBack)
            binding.textViewProcess.text = respondGenericDocument.toString()
            //To save documents
            onSaveDocumentSides()
        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun onErrorIntent(data: Intent) {
        if (data == null) {
            data.putExtra(IntentExtras.ERROR_MSG, Constants.ERROR_IMAGE_PROCESS)
            data.putExtra(IntentExtras.ERROR_SDK, Constants.ERROR_R106)
            docState = 0
            onErrorIntent(data)
        } else {
            dismissProgressDialog()
            if (data.extras!!.containsKey(IntentExtras.ERROR_MSG)) {
                binding.textViewProcess.text = data.getStringExtra(IntentExtras.ERROR_MSG)
            } else if (data.extras!![IntentExtras.ERROR_SDK] != null) {
                binding.textViewProcess.text = JsonUtils.stringObject(
                    data.extras!!.getParcelable(IntentExtras.ERROR_SDK)!!
                )
            }
            //If the document fails
            when (docState) {
                1 -> {
                    pathFront = if (data.extras!![IntentExtras.PATH_FILE_PHOTO] != null) data.getStringExtra(IntentExtras.PATH_FILE_PHOTO) else ""
                    Glide.with(this).load(pathFront).into(
                        binding.imgFront
                    )
                    verifyBackDocument()
                }
                2 -> {
                    pathBack = if (data.extras!![IntentExtras.PATH_FILE_PHOTO] != null) data.getStringExtra(IntentExtras.PATH_FILE_PHOTO) else ""
                    Glide.with(this).load(pathBack).into(
                        binding.imgBack
                    )
                    onSaveDocumentSides()
                }
                else -> {
                    emptyValues()
                }
            }

        }
    }

    private fun emptyValues() {
        //Empty values
        user = ""
        pass = ""
        docFront = ""
        docBack = ""
        pathFront = ""
        pathBack = ""
    }

    private fun onRequestValidation() {
        if (isAnyRequestValidationEmpty()) {
            binding.textViewProcess.text = "Hay campos vacios"
            dismissProgressDialog()
        } else {
            loading("Solicitud validación")
            /*val stringProbe =
                "{\"guidConv\":\"97464498-D532-4964-ABF7-81DC0F884B21\",\"tipoValidacion\":4,\"asesor\":\"testing\",\"sede\":\"931135\",\"codigoCliente\":\"00000124\",\"tipoDoc\":\"CC\",\"numDoc\":\"1024494020\",\"email\":\"jim.moreno@recnoserid.com\",\"celular\":\"3124788862\",\"prefCelular\":\"57\",\"usuario\":\"testingMobile\",\"clave\":\"12345\",\"InfCandidato\":\"{\\\"company\\\":\\\"OlimpiaIt\\\"}\",\"consultaFuentes\":true,\"ciudadanoData\":{\"fechaExpDoc\":\"2022-07-12T19:09:17.582Z\",\"fechaNac\":\"2022-07-12T19:09:17.582Z\",\"primerNombre\":\"string\",\"segundoNombre\":\"string\",\"primerApellido\":\"string\",\"segundoApellido\":\"string\"},\"procesoWhatsapp\":false}"

            val gson = Gson()
            val requestValidationIn = gson.fromJson(
                stringProbe,
                RequestValidationIn::class.java
            )*/


            val stringProbeCiudadanoData =
                "{\"fechaExpDoc\":\"2022-07-12T19:09:17.582Z\",\"fechaNac\":\"2022-07-12T19:09:17.582Z\",\"primerNombre\":\"string\",\"segundoNombre\":\"string\",\"primerApellido\":\"string\",\"segundoApellido\":\"string\"}"
            val gson = Gson()
            val ciudadanoData = gson.fromJson(
                stringProbeCiudadanoData,
                CiudadanoData::class.java
            )

            //save data
            user = binding.etxUser.text.toString()
            pass = binding.etxPass.text.toString()
            numDoc = binding.etxDoc.text.toString()
            typeDoc = binding.etxTypeDoc.text.toString()
            var auxTypeDoc : String? = ""
            auxTypeDoc = if(typeDoc == COLOMBIAN_DIG_ID){
                COLOMBIAN_CC
            }else{
                typeDocBlackBox
            }

            val requestValidationIn = RequestValidationIn(
                "{\"company\":\"OlimpiaIt\"}",
                "testing",
                binding.etxCellPhone.text.toString(),
                null,
                binding.etxPass.text.toString(),
                true,
                binding.etxEmail.text.toString(),
                binding.etxGuidCv.text.toString(),
                numDoc,
                binding.ccpCodeCountryPhone.selectedCountryCode,
                false,
                "931135",
                auxTypeDoc,
                binding.etxTypeValidation.text.toString().toInt(),
                binding.etxUser.text.toString()
            )

            ServicesOlimpia.getInstance().getRequestValidation(requestValidationIn,
                object :
                    OlimpiaInterface.CallbackRequestValidation {
                    override fun onSuccess(requestValidationOut: RequestValidationOut?) {
                        requestValidationOut?.let {
                            binding.textViewProcess.text = JsonUtils.stringObject(it)
                            procesoConvenioGuid = isNullOrEmptyValues(it.data?.procesoConvenioGuid)
                            guidCiudadano = isNullOrEmptyValues(it.data?.guidCiudadano)
                            dismissProgressDialog()
                        }
                    }

                    override fun onError(transactionResponse: RespuestaTransaccion?) {
                        binding.textViewProcess.text = JsonUtils.stringObject(
                            transactionResponse!!
                        )
                        dismissProgressDialog()
                        procesoConvenioGuid = ""
                        guidCiudadano = ""
                        blackBoxActivate = false
                    }

                })
        }
    }

    private fun onConsultSteps() {
        if (binding.etxGuidCv.text.toString().isEmpty()) {
            binding.textViewProcess.text = "Hay campos vacios"
            dismissProgressDialog()
        } else {
            loading("Consultar Pasos")
            val consultStepsIn = ConsultStepsIn(
                binding.etxGuidCv.text.toString()
            )
            ServicesOlimpia.getInstance().getConsultSteps(consultStepsIn,
                object : OlimpiaInterface.CallbackConsultSteps {
                    override fun onSuccess(consultStepsOut: ConsultStepsOut?) {
                        consultStepsOut?.let {
                            binding.textViewProcess.text = JsonUtils.stringObject(it)
                            dismissProgressDialog()
                        }
                    }

                    override fun onError(transactionResponse: RespuestaTransaccion?) {
                        binding.textViewProcess.text = JsonUtils.stringObject(
                            transactionResponse!!
                        )
                        dismissProgressDialog()
                        blackBoxActivate = false
                    }

                })
        }
    }

    private fun onConsultValidation() {
        if (isAnyConsultValidationEmpty()) {
            binding.textViewProcess.text = "Hay campos vacios"
            dismissProgressDialog()
        } else {


            //{"clave":"OWOTWCH_MOVIL.2022*","codigoCliente":"","guidConv":"e5eec12a-0e4b-43a6-8340-1360c454ec28","procesoConvenioGuid":"5eda06e8-d4ac-4e59-b972-f4ef5a107850","usuario":"OWOTWCH_MOVIL2022"}
            val consultValidationIn: ConsultValidationIn

            if (blackBoxActivate) {
                loading("Consultar Validación pasos BlackBox")
                consultValidationIn = ConsultValidationIn(
                    passBlackBox,
                    "",
                    GUID_CONV_BLACK_BOX,
                    procesoConvenioGuidBlacBox,
                    userBlackBox

                )
            } else {
                loading("Consultar Validación")

                /*
                val stringProbe = "{\"guidConv\":\"0E4985FF-C13A-4C73-B3FE-50B2DF4839FE\",\"procesoConvenioGuid\":\"0344FF8C-08B7-40E1-A8C8-79AAD64FAB11\",\"codigoCliente\":\"\",\"usuario\":\"jsviveroj\",\"clave\":\"JVP@\$\$w0rd\"}"
                val stringProbe = "{\"guidConv\":\"e5eec12a-0e4b-43a6-8340-1360c454ec28\",\"procesoConvenioGuid\":\"5eda06e8-d4ac-4e59-b972-f4ef5a107850\",\"codigoCliente\":\"\",\"usuario\":\"OWOTWCH_MOVIL2022\",\"clave\":\"OWOTWCH_MOVIL.2022*\"}"
                val gson = Gson()
                consultValidationIn = gson.fromJson(
                    stringProbe,
                    ConsultValidationIn::class.java
                )*/

                consultValidationIn = ConsultValidationIn(
                    binding.etxPass.text.toString(),
                    "",
                    "0E4985FF-C13A-4C73-B3FE-50B2DF4839FE",
                    //"97464498-D532-4964-ABF7-81DC0F884B21",
                    binding.etxGuidCv.text.toString(),
                    binding.etxUser.text.toString()

                )
            }

            ServicesOlimpia.getInstance().getConsultValidation(consultValidationIn,
                object :
                    OlimpiaInterface.CallbackConsultValidation {
                    override fun onSuccess(consultValidationOut: ConsultValidationOut?) {
                        consultValidationOut?.let {
                            binding.textViewProcess.text = JsonUtils.stringObject(it)
                            dismissProgressDialog()
                        }
                    }

                    override fun onError(transactionResponse: RespuestaTransaccion?) {
                        binding.textViewProcess.text = JsonUtils.stringObject(
                            transactionResponse!!
                        )
                        dismissProgressDialog()
                        blackBoxActivate = false
                    }

                })
        }
    }

    private fun startBlackBox() {
        if (isAnyRequestValidationEmpty()) {
            binding.textViewProcess.text = "Hay campos vacios"
            dismissProgressDialog()
        } else {
            loading("Solicitud Validación Black Box")

            var auxTypeDoc : String? = ""
            numDocBlackBox = binding.etxDoc.text.toString()
            typeDocBlackBox = binding.etxTypeDoc.text.toString()
            auxTypeDoc = if(typeDocBlackBox == COLOMBIAN_DIG_ID){
                COLOMBIAN_CC
            }else{
                typeDocBlackBox
            }

            //save data
            userBlackBox = binding.etxUser.text.toString()
            passBlackBox = binding.etxPass.text.toString()

            var ciudadanoData: CiudadanoData? = CiudadanoData()
            if (binding.ccpCodeCountryNationality.selectedCountryName == COLOMBIA) {
                //Avoid backEnd Errors
                ciudadanoData = null
            } else {
                //Set FOREIGNER names
                getNamesValues(
                    binding.etxNames.text.toString(),
                    binding.etxLastnames.text.toString()
                )
                ciudadanoData!!.primerNombre = name1
                ciudadanoData.segundoNombre = name2
                ciudadanoData.primerApellido = lastName1
                ciudadanoData.segundoApellido = lastName2
                ciudadanoData.fechaNac = "2022-07-12T19:09:17.582Z"
                ciudadanoData.fechaExpDoc = "2022-07-12T19:09:17.582Z"
            }


            val requestValidationIn = RequestValidationIn(
                "{\"company\":\"OlimpiaIt\"}",
                ADVISER,
                binding.etxCellPhone.text.toString(),
                ciudadanoData,
                passBlackBox,
                true,
                binding.etxEmail.text.toString(),
                GUID_CONV_BLACK_BOX,
                numDocBlackBox,
                binding.ccpCodeCountryPhone.selectedCountryCode,
                false,
                CAMPUS,
                auxTypeDoc,
                binding.etxTypeValidation.text.toString().toInt(),
                userBlackBox
            )

            ServicesOlimpia.getInstance().getRequestValidation(requestValidationIn,
                object :
                    OlimpiaInterface.CallbackRequestValidation {
                    @SuppressLint("SetTextI18n")
                    override fun onSuccess(requestValidationOut: RequestValidationOut?) {
                        requestValidationOut?.let {
                            binding.textViewProcess.text = JsonUtils.stringObject(it)
                            procesoConvenioGuidBlacBox = isNullOrEmptyValues(it.data?.procesoConvenioGuid)
                            guidCiudadanoBlackBox = isNullOrEmptyValues(it.data?.guidCiudadano)
                            dismissProgressDialog()
                            if(it.data!!.esExitosa!!){
                                consultStepsBlackBox(procesoConvenioGuidBlacBox, guidCiudadanoBlackBox)
                            }else{
                                binding.textViewProcess.text =
                                    "Código: " + it.data!!.errorEntransaccion!!.codigo +
                                            " \nDescripción:" + it.data!!.errorEntransaccion!!.descripcion
                            }

                        }
                    }

                    override fun onError(transactionResponse: RespuestaTransaccion?) {
                        binding.textViewProcess.text = JsonUtils.stringObject(
                            transactionResponse!!
                        )
                        dismissProgressDialog()
                        procesoConvenioGuid = ""
                        guidCiudadano = ""
                        blackBoxActivate = false
                    }

                })
        }
    }

    private fun getNamesValues(namesTxt: String, lastNamesTxt: String) {
        //Clean first
        name1 = ""
        name2 = ""
        lastName1 = ""
        lastName2 = ""
        val namesTxt1 = namesTxt.trim()
        val lastNamesTxt1 = lastNamesTxt.trim()
        val names = namesTxt1.split(" ").toTypedArray()
        val lastNames = lastNamesTxt1.split(" ").toTypedArray()
        //Set each name
        for (i in names.indices) {
            if (i == 0) {
                name1 = names[i]
            } else {
                name2 = "$name2 " + names[i]
            }
        }
        for (i in lastNames.indices) {
            if (i == 0) {
                lastName1 = lastNames[i]
            } else {
                lastName2 = "$lastName2 " + lastNames[i]
            }
        }
        name1 = name1!!.trim()
        name2 = name2!!.trim()
        lastName1 = lastName1!!.trim()
        lastName2 = lastName2!!.trim()
        Log.e("Nombre Completo", "$name1 $name2 $lastName1 $lastName2")

    }

    private fun consultStepsBlackBox(
        procesoConvenioGuidBlacBox: String?,
        guidCiudadanoBlackBox: String?
    ) {
        if (procesoConvenioGuidBlacBox.isNullOrEmpty() || guidCiudadanoBlackBox.isNullOrEmpty()) {
            binding.textViewProcess.text =
                "El procesoConvenioGuid o el guidCiudadano llegarón vacios. Intentélo más tarde "
            dismissProgressDialog()
        } else {
            loading("Consultar Pasos BlackBox")
            /*val consultStepsIn = ConsultStepsIn(
                this.procesoConvenioGuidBlacBox
            )*/
            ServicesOlimpia.getInstance()
                .getListConsultAgreementProcess(this.procesoConvenioGuidBlacBox!!,
                    object : OlimpiaInterface.CallbackConsultAgreementProcess {
                        override fun onSuccess(consultAgreementProcessOut: ConsultAgreementProcessOut?) {
                            consultAgreementProcessOut?.let {
                                binding.textViewProcess.text = JsonUtils.stringObject(it)
                                dismissProgressDialog()
                                validateCases(consultAgreementProcessOut)
                            }
                        }

                        override fun onError(transactionResponse: RespuestaTransaccion?) {
                            binding.textViewProcess.text = JsonUtils.stringObject(
                                transactionResponse!!
                            )
                            dismissProgressDialog()
                            blackBoxActivate = false
                        }
                    })
        }
    }

    private fun validateCases(consultAgreementProcessOut: ConsultAgreementProcessOut) {
        when (consultAgreementProcessOut.datos?.estadoProceso) {
            1 -> {
                saveBiometryBlackBox()
            }
            2 -> {
                configurationValidateBiometry()
            }
            else -> {
                binding.textViewProcess.text = "No hay información del estado. Intentélo más tarde "
                dismissProgressDialog()
            }
        }

    }

    private fun configurationValidateBiometry() {
        //To validate Biometry
        isValidateBiometry = true
        val intent = Intent(this, ConfigurationActivity::class.java)
        startActivityForResult(intent, CONFIGURATION)
    }

    @SuppressLint("LogNotTimber")
    private fun onSaveDocumentSides() {
        if ((procesoConvenioGuid.isNullOrEmpty() || guidCiudadano.isNullOrEmpty()) && !isValidateBiometry) {
            binding.textViewProcess.text = "Primero solicite la validación con sus datos"
            dismissProgressDialog()
            emptyValues()
        } else if ((procesoConvenioGuidBlacBox.isNullOrEmpty() || guidCiudadanoBlackBox.isNullOrEmpty()) && isValidateBiometry) {
            binding.textViewProcess.text =
                "Primero solicite la validación con sus datos con el blackBox"
            dismissProgressDialog()
            emptyValues()
        } else {

            /*//To save easily the traceability
            val stringProbe =
                "{\"guidProcesoConvenio\":\"4e8305f4-7120-4a7b-91fa-16426e304fc9\",\"guidCiu\":\"e197b994-b9d6-4885-917c-830749837583\",\"datosAdi\":\"CC\",\"anverso\":{\"valor\":\"anverso\",\"formato\":\"image/jpeg\"},\"reverso\":{\"valor\":\"reverso\",\"formato\":\"image/jpeg\"},\"usuario\":\"BANESCOWEBUSER\",\"trazabilidad\":{\"tiempo\":156,\"ip\":\"181.71.0.185\",\"resolucion\":\"1400x685\",\"navegador\":\"Opera-Mac\",\"versionNavegador\":\"89.0.4447.83\",\"device\":\"Unknown\",\"userAgent\":\"Mozilla/5.0(Macintosh;IntelMacOSX10_15_7)AppleWebKit/537.36(KHTML,likeGecko)Chrome/103.0.5060.134Safari/537.36OPR/89.0.4447.83\"}}"
            val gson = Gson()
            val saveDocumentSidesIn = gson.fromJson(
                stringProbe,
                SaveDocumentSidesIn::class.java
            )*/

            val saveDocumentSidesIn = SaveDocumentSidesIn()


            if (isValidateBiometry) {
                loading("Salvar documento pasos BlackBox")
                //Constructor
                saveDocumentSidesIn.guidProcesoConvenio = procesoConvenioGuidBlacBox
                saveDocumentSidesIn.guidCiu = guidCiudadanoBlackBox
                saveDocumentSidesIn.usuario = userBlackBox
                if(typeDocBlackBox==COLOMBIAN_DIG_ID){
                    saveDocumentSidesIn.datosAdi = COLOMBIAN_CC
                }else{
                    saveDocumentSidesIn.datosAdi = typeDocBlackBox
                }
            } else {
                loading(getString(R.string.loading))
                //Constructor
                saveDocumentSidesIn.guidProcesoConvenio = procesoConvenioGuid
                saveDocumentSidesIn.guidCiu = guidCiudadano
                saveDocumentSidesIn.usuario = binding.etxUser.text.toString()
                if(typeDocBlackBox==COLOMBIAN_DIG_ID){
                    saveDocumentSidesIn.datosAdi = COLOMBIAN_CC
                }else{
                    saveDocumentSidesIn.datosAdi = typeDocBlackBox
                }
            }

            //Validate front and back document
            saveDocumentSidesIn.anverso!!.formato = IMAGE_TYPE
            saveDocumentSidesIn.reverso!!.formato = IMAGE_TYPE
            //LogOcrBarcode constructor
            val logsOcrbarcode: LogsOcrbarcode?
            //If the obverse document is DIGITAL COLOMBIAN ID or CC
            when {
                docState != 0 -> {
                    logsOcrbarcode = null
                    val myString: List<String> = listOf(FORCE_DOCUMENT_READING)
                    saveDocumentSidesIn.configuracionEspecial = myString
                }
                colombianOCR.documentType == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT ->{
                    saveDocumentSidesIn.datosAdi = COLOMBIAN_DIG_ID //To save you should send it as "CC"
                    logsOcrbarcode = LogsOcrbarcode(logOcrAnverso, logOcrReverso, logBarcodeReader)
                }
                binding.ccpCodeCountryNationality.selectedCountryName != COLOMBIA -> {
                    //Foreign Document
                    logsOcrbarcode = null
                    saveDocumentSidesIn.datosAdi = typeDocBlackBox
                }
                else -> {
                    logsOcrbarcode = LogsOcrbarcode(logOcrAnverso, logOcrReverso, logBarcodeReader)
                }
            }

            saveDocumentSidesIn.logsOcrbarcode = logsOcrbarcode

            //To show easily the JSON request
            Log.e("GuardarDoumento", saveDocumentSidesIn.toString())
            val gson = Gson()
            Log.e("GuardarDoumento", gson.toJson(saveDocumentSidesIn, SaveDocumentSidesIn::class.java))

            saveDocumentSidesIn.anverso!!.valor = ImageUtils.getEncodedBase64FromFilePath(pathFront)
            saveDocumentSidesIn.reverso!!.valor = ImageUtils.getEncodedBase64FromFilePath(pathBack)


            ServicesOlimpia.getInstance().getSaveDocumentSides(saveDocumentSidesIn,
                object :
                    OlimpiaInterface.CallbackSaveDocumentSides {
                    override fun onSuccess(saveDocumentSidesOut: SaveDocumentSidesOut?) {
                        saveDocumentSidesOut?.let {
                            // binding.textViewProcess.text = JsonUtils.stringObject(it)
                            dismissProgressDialog()
                            //Continue with blackBox environment
                            if (blackBoxActivate) {
                                onConsultValidation()
                            }
                        }
                    }

                    override fun onError(transactionResponse: RespuestaTransaccion?) {
                        binding.textViewProcess.text = JsonUtils.stringObject(transactionResponse!!)
                        dismissProgressDialog()
                        blackBoxActivate = false
                    }

                })
            emptyValues()
        }
    }

    private fun setNamesLogOcrObverse(firstName: String, secondName: String, firstLastName: String, secondLastName: String) {
        logOcrAnverso.primerNombre = firstName
        logOcrAnverso.segundoNombre = secondName
        logOcrAnverso.primerApellido = firstLastName
        logOcrAnverso.segundoApellido = secondLastName
    }

    private fun setNamesLogOcrReverse(firstName: String, secondName: String, firstLastName: String, secondLastName: String) {
        logOcrReverso.primerNombre = firstName
        logOcrReverso.segundoNombre = secondName
        logOcrReverso.primerApellido = firstLastName
        logOcrReverso.segundoApellido = secondLastName
    }

    private fun setNamesAndLastNames(names: String, lastNames: String, typeDocument: Int) {
        //Set Names and lastNames
        //var stringNames: List<String?> = mutableListOf("", "", "")
        Timber.e("NAMES is: %s", names)
        Timber.e("LAST NAMES is: %s", lastNames)
        val stringNames: List<String?>
        var firstName = ""
        var secondName = ""
        val stringLastNames: List<String?>
        var firstLastName = ""
        var secondLastName = ""
        stringNames = names.split(" ")
        stringLastNames = lastNames.split(" ")
        //Get Names
        if (stringNames.isNotEmpty() && stringNames.size > 1) {
            println("1st name:")
            println(stringNames[0])
            firstName = stringNames[0]
            for (i in 1 until stringNames.size) {
                secondName = secondName + " " + stringNames[i]
            }
            println("2nd name:")
            secondName = secondName.trim()
            println(secondName)
        } else if ( stringNames.isNotEmpty() ) {
            println("total name:")
            println(stringNames[0])
            firstName = stringNames[0]
        }
        //Get LastNames
        if (stringLastNames.isNotEmpty() && stringLastNames.size > 1) {
            println("1st lastName:")
            println(stringLastNames[0])
            firstLastName = stringLastNames[0]
            for (i in 1 until stringLastNames.size) {
                secondLastName = secondLastName + " " + stringLastNames[i]
            }
            println("2nd lastName:")
            secondLastName = secondLastName.trim()
            println(secondLastName)
        } else if ( stringLastNames.isNotEmpty() ) {
            println("total lastName:")
            println(stringLastNames[0])
            firstLastName = stringLastNames[0]
        }

        when (typeDocument) {
            Constants.COLOMBIAN_OBVERSE_DOCUMENT, Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT  ->
                setNamesLogOcrObverse(firstName, secondName, firstLastName, secondLastName)
            Constants.COLOMBIAN_REVERSE_DOCUMENT, Constants.COLOMBIAN_DIGITAL_REVERSE_DOCUMENT  ->
                setNamesLogOcrReverse(firstName, secondName, firstLastName, secondLastName)
        }
    }

    private fun isNullOrEmptyValues(myValue: String?): String {
        return if (myValue.isNullOrEmpty()) {
            ""
        } else {
            myValue
        }
    }

    private fun isAnyRequestValidationEmpty(): Boolean {
        return binding.etxGuidCv.text.toString()
            .isEmpty() || binding.etxTypeValidation.text.toString().isEmpty() ||
                binding.etxTypeDoc.text.toString().isEmpty() || binding.etxDoc.text.toString()
            .isEmpty() ||
                binding.etxEmail.text.toString().isEmpty() || binding.etxCellPhone.text.toString()
            .isEmpty() ||
                binding.etxUser.text.toString().isEmpty() || binding.etxPass.text.toString()
            .isEmpty()
        //Names or lastNames
        /*|| binding.etxNames.text.toString()
    .isEmpty() || binding.etxLastnames.text.toString().isEmpty()*/
    }

    private fun isAnyConsultValidationEmpty(): Boolean {
        return binding.etxGuidCv.text.toString().isEmpty() || binding.etxUser.text.toString()
            .isEmpty() ||
                binding.etxPass.text.toString().isEmpty()
    }


    private fun loading(msgRes: String?) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this)
            mProgressDialog!!.isIndeterminate = true
            mProgressDialog!!.setCancelable(false)
            mProgressDialog!!.setCanceledOnTouchOutside(false)
        }
        mProgressDialog!!.setMessage(msgRes)
        mProgressDialog!!.show()
    }

    fun dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
        }
    }

}
