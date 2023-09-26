package com.reconosersdk

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.reconosersdk.databinding.ActivityValidationOpenSourceDocumentBinding
import com.reconosersdk.reconosersdk.http.OlimpiaInterface
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.OpenSourceValidationRequest
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid.openSource.RespondOpenSourceValidation
import com.reconosersdk.reconosersdk.http.regula.entities.out.ValidarDocumentoGenericoOut
import com.reconosersdk.reconosersdk.ui.document.views.GeneralDocumentActivity
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.ServicesOlimpia
import com.reconosersdk.reconosersdk.utils.Constants
import com.reconosersdk.reconosersdk.utils.IntentExtras
import com.reconosersdk.reconosersdk.utils.JsonUtils

class ValidationOpenSourceDocumentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityValidationOpenSourceDocumentBinding
    private var respondGenericDocument: ValidarDocumentoGenericoOut? = null
    var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_validation_open_source_document)

        setSpinner()
        initListeners()
    }

    private fun initListeners() {
        binding.btnPhoto1.setOnClickListener {
            launchGenericDocument(binding.etType.text.toString())
        }
        binding.btnCompare.setOnClickListener {
            respondGenericDocument?.procesoConvenioGuid?.let {
                loading("")
                launchValidator(it)
            }
        }
        binding.txtResult.movementMethod = ScrollingMovementMethod()
    }

    private fun setSpinner() {
        val optionsValidateGeneralDocument = getEveryGeneralDocuments()
        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.spinner_style, optionsValidateGeneralDocument)
        // Drop down layout style
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
                    binding.etType.setText( binding.spinnerGeneralDocument.selectedItem.toString() )
                    /*opSelected = position
                    setValuesTxt(opSelected)*/
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //No implementation needed
                }
            }
    }

    //To easily test
    private fun getEveryGeneralDocuments(): Array<String?> {
        return applicationContext.resources.getStringArray(R.array.validate_general_document)
    }

    private fun launchValidator(processGuid: String) {
        respondGenericDocument?.procesoConvenioGuid?.let {
        }
        ServicesOlimpia.getInstance()
            .onValidationOpenSourceDocument(
                OpenSourceValidationRequest(processGuid, 0),
                object : OlimpiaInterface.CallbackValidationOpenSourceDocument {
                    override fun onSuccess(respondOpenSourceValidation: RespondOpenSourceValidation?) {
                        dismissProgressDialog()
                        binding.txtResult.text = "${respondOpenSourceValidation?.data}"
                    }

                    override fun onError(transactionResponse: RespuestaTransaccion?) {
                        dismissProgressDialog()
                        binding.txtResult.text = "$transactionResponse"
                    }
                })
    }

    private fun launchGenericDocument(etType: String) {
        val intent = Intent(
            this,
            GeneralDocumentActivity::class.java
        )
        intent.putExtra(IntentExtras.TEXT_SCAN, "Documento")
        intent.putExtra(IntentExtras.TYPE_DOCUMENT, etType)
        //Documento Peruano
        if(etType == "DNIP"){
            intent.putExtra(IntentExtras.GUID_PROCESS_AGREEMENT, "046d3755-b7f9-4163-b6d5-fb3e09dc3f63")
            intent.putExtra(IntentExtras.GUID_CIUDADANO, "E370780F-7EB6-4C78-9DD9-E66E546C223E")
        }
        startActivityForResult(intent, MainActivity.GENERAL_DOCUMENT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            onRespondGeneralDocument(data)
        } else if (resultCode == IntentExtras.ERROR_INTENT) {
            onErrorIntent(data)
        }
    }

    private fun onRespondGeneralDocument(data: Intent?) {
        if (data == null) {
            var mData = Intent()
            mData.putExtra(IntentExtras.ERROR_MSG, Constants.ERROR_IMAGE_PROCESS)
            mData.putExtra(IntentExtras.ERROR_SDK, Constants.ERROR_R106)
            onErrorIntent(mData)
        } else {
            val path = data.getStringExtra(IntentExtras.PATH_FILE_ANVERSO)
            respondGenericDocument = data.getParcelableExtra(IntentExtras.DATA_DOCUMENT)

            binding.txtResult.text = respondGenericDocument.toString()
            Glide.with(this).load(path).into(binding.imgResult)
        }
    }

    private fun onErrorIntent(data: Intent?) {
        if (data!!.extras!!.containsKey(IntentExtras.ERROR_MSG)) {
            binding.txtResult.text = data.getStringExtra(IntentExtras.ERROR_MSG)
        } else if (data.extras!![IntentExtras.ERROR_SDK] != null) {
            binding.txtResult.text = JsonUtils.stringObject(
                data.extras!!.getParcelable(IntentExtras.ERROR_SDK)!!
            )
        }
        respondGenericDocument = null
        binding.imgResult.setImageDrawable(resources.getDrawable(R.drawable.placeholder, null))
    }

    private fun loading(msgRes: String) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this)
            //mProgressDialog.setMessage(getString(R.string.loading));
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