package com.reconosersdk

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.reconosersdk.databinding.ActivityValidationExtraDocumentBinding
import com.reconosersdk.reconosersdk.http.OlimpiaInterface
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.ValidateDriverLicenseIn
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.ValidateFederalDriverLicenseIn
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.ValidateReceiptIn
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.savetraceability.SaveTraceabilityProcessIn
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.savetraceability.Trazabilidad
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidateDriverLicenseOut
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidateFederalDriverLicenseOut
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidateReceiptOut
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.savetraceability.SaveTraceabilityOut
import com.reconosersdk.reconosersdk.ui.document.views.ExtraDocumentActivity
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.ServicesOlimpia
import com.reconosersdk.reconosersdk.utils.*
import com.reconosersdk.utils.Utils

private const val LOCATION = "UbicacionGeografica"
private const val ANDROID = "Android"
private const val KOTLIN = "Kotlin"
private const val USER = "testing"

class ValidationExtraDocumentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityValidationExtraDocumentBinding
    private var validateReceiptOut: ValidateReceiptOut? = null
    private var opSelected: Int = 0
    private var imageBase64: String? = ""
    var mProgressDialog: ProgressDialog? = null

    // inside a basic activity
    private var locationManager: LocationManager? = null
    private var latitude: String = ""
    private var longitude: String = ""
    private var ipPublic: String = ""
    private var saveTraceabilityProcessIn: SaveTraceabilityProcessIn = SaveTraceabilityProcessIn()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_validation_extra_document)

        requestPermissionsDexter()
        setSpinner()
        initListeners()
    }

    private fun requestPermissionsDexter() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    // check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        getGPS()

                    }

                    // check for permanent denial of any permission
                    if (report.isAnyPermissionPermanentlyDenied) {
                        // show alert dialog navigating to Settings
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            })
            .withErrorListener { error: DexterError ->
                Toast.makeText(
                    applicationContext,
                    "Error occurred! $error",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .onSameThread()
            .check()
    }

    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Need Permissions")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS") { dialog: DialogInterface, _: Int ->
            dialog.cancel()
            openSettings()
        }
        builder.setNegativeButton("Cancel") { dialog: DialogInterface, _: Int ->
            dialog.cancel()
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
        builder.setCancelable(false)
        val alertDialog = builder.create()
        alertDialog.show()
    }

    // navigating user to app settings
    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

    private fun setSpinner() {
        val optionsValidateExtraDocument = getEveryValidateDocuments()
        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.spinner_style, optionsValidateExtraDocument)
        // Drop down layout style
        dataAdapter.setDropDownViewResource(R.layout.custom_spinner)
        binding.spinnerExtraDocument.adapter = dataAdapter
        binding.spinnerExtraDocument.setSelection(0)
        binding.spinnerExtraDocument.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    opSelected = position
                    setValuesTxt(opSelected)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //No implementation needed
                }
            }
    }

    private fun setValuesTxt(opSelected: Int) {
        when (opSelected) {
            1 -> setValidateLicence()
            2 -> setValidateFederalLicence()
            3 -> setIPandGPS()
            else -> setValidateReceipt()
        }
    }

    private fun setIPandGPS() {
        binding.etGuidProcesoConvenio.setText("acfcff74-c4f5-4a9f-9b92-3e7da3f020b6")
        binding.etFormat.setText("")
        binding.etIdService.setText("")
        binding.etSubType.setText("")
        binding.etNumLicencia.isEnabled = false
        binding.etNumMedPreventiva.isEnabled = false
        binding.etNumLicencia.setText("")
        binding.etNumMedPreventiva.setText("")

        getGPS()
        saveTraceabilityProcessIn = SaveTraceabilityProcessIn(
            LOCATION,
            "acfcff74-c4f5-4a9f-9b92-3e7da3f020b6",
            Trazabilidad(
                ANDROID,
                ipPublic,
                latitude,
                KOTLIN,
                longitude,
                "",
                Miscellaneous.getScreenResolution(),
                Miscellaneous.getCameraResolution(),
                0,
                USER,
                ""
            )
        )
    }

    private fun setValidateReceipt() {
        binding.etGuidProcesoConvenio.setText("DC0E0BD1-32CD-4824-9C8F-127DB220221A")
        binding.etFormat.setText("JPG_B64")
        binding.etIdService.setText("28")
        binding.etSubType.setText("7")
        binding.etNumLicencia.isEnabled = false
        binding.etNumMedPreventiva.isEnabled = false
        binding.etNumLicencia.setText("")
        binding.etNumMedPreventiva.setText("")
    }

    private fun setValidateLicence() {
        binding.etGuidProcesoConvenio.setText("DC0E0BD1-32CD-4824-9C8F-127DB220221A")
        binding.etFormat.setText("JPG_B64")
        binding.etIdService.setText("34")
        binding.etSubType.setText("7")
        binding.etNumLicencia.isEnabled = false
        binding.etNumMedPreventiva.isEnabled = false
        binding.etNumLicencia.setText("")
        binding.etNumMedPreventiva.setText("")
    }

    private fun setValidateFederalLicence() {
        binding.etGuidProcesoConvenio.setText("DC0E0BD1-32CD-4824-9C8F-127DB220221A")
        binding.etFormat.setText("JPG_B64")
        binding.etIdService.setText("35")
        binding.etSubType.setText("7")
        binding.etNumLicencia.isEnabled = true
        binding.etNumMedPreventiva.isEnabled = true
        binding.etNumLicencia.setText("PUE0029482")
        binding.etNumMedPreventiva.setText("711382")
    }

    private fun getGPS() {
        // Create persistent LocationManager reference
        locationManager =
            this.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager?
        try {
            // Request location updates
            locationManager?.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0L,
                0f,
                locationListener
            )
            ipPublic = PublicIpAddress.getPublicIpAddress()!!
            print("The ip public is: $ipPublic")
        } catch (ex: SecurityException) {
            Log.e("myTag", "Security Exception, no location available")
        }
    }

    //define the listener
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            longitude = location.longitude.toString()
            latitude = location.latitude.toString()
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    //To easily test
    private fun getEveryValidateDocuments(): Array<String?> {
        return applicationContext.resources.getStringArray(R.array.validate_document_options)
    }

    private fun initListeners() {
        binding.txtResult.movementMethod = ScrollingMovementMethod()
        binding.btnPhoto1.setOnClickListener {
            Miscellaneous.hideKeyboard(this)
            launchValidateExtraDocument(opSelected)
        }
        binding.btnCompare.setOnClickListener {
            Miscellaneous.hideKeyboard(this)
            loading("")
            launchValidator()
        }
        binding.txtResult.movementMethod = ScrollingMovementMethod()
    }

    private fun launchValidateExtraDocument(opSelected: Int) {
        val intent = Intent(
            this,
            ExtraDocumentActivity::class.java
        )

        intent.putExtra(IntentExtras.VALIDATE_RECEIPT, opSelected)
        startActivityForResult(intent, MainActivity.GENERAL_DOCUMENT)
    }

    private fun avoidNulls(value: String): String {
        return if (value.isEmpty()) {
            ""
        } else {
            value
        }
    }

    private fun avoidNullsInt(value: String): Int {
        return if (value.isEmpty()) {
            0
        } else {
            value.toInt()
        }
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
            imageBase64 = data.getStringExtra(IntentExtras.IMAGE64)
            binding.txtResult.text = imageBase64
            Glide.with(this).load(path).into(binding.imgResult)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun onErrorIntent(data: Intent?) {
        if (data!!.extras!!.containsKey(IntentExtras.ERROR_MSG)) {
            binding.txtResult.text = data.getStringExtra(IntentExtras.ERROR_MSG)
        } else if (data.extras!![IntentExtras.ERROR_SDK] != null) {
            binding.txtResult.text = JsonUtils.stringObject(
                data.extras!!.getParcelable(IntentExtras.ERROR_SDK)!!
            )
        }
        validateReceiptOut = null
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

    private fun launchValidator() {
        when (opSelected) {
            1 -> launchValidateLicence()
            2 -> launchValidateFederalLicence()
            3 -> launchIPandGPS()
            else -> launchValidateReceipt()
        }
    }

    private fun launchValidateReceipt() {
        //Set values into ValidateReceiptIn
        val validateReceiptIn = ValidateReceiptIn()

        validateReceiptIn.formato = avoidNulls(binding.etFormat.text.toString())
        validateReceiptIn.guidProcesoConvenio =
            avoidNulls(binding.etGuidProcesoConvenio.text.toString())
        validateReceiptIn.idServicio = avoidNullsInt(binding.etIdService.text.toString())
        validateReceiptIn.image = imageBase64!!
        validateReceiptIn.subTipo = avoidNullsInt(binding.etSubType.text.toString())
        validateReceiptIn.usuario = avoidNulls(binding.etUser.text.toString())

        ServicesOlimpia.getInstance()
            .onValidationReceipt(
                validateReceiptIn,
                object : OlimpiaInterface.CallbackValidationReceipt {

                    override fun onSuccess(validateReceiptOut: ValidateReceiptOut?) {
                        dismissProgressDialog()
                        binding.txtResult.text = "${validateReceiptOut?.toString()}"
                    }

                    override fun onError(transactionResponse: RespuestaTransaccion?) {
                        dismissProgressDialog()
                        binding.txtResult.text = "$transactionResponse"
                    }
                })
    }

    private fun launchValidateLicence() {
        //Set values into ValidateReceiptIn
        val validateDriverLicenseIn = ValidateDriverLicenseIn()

        validateDriverLicenseIn.formato = avoidNulls(binding.etFormat.text.toString())
        validateDriverLicenseIn.guidProcesoConvenio =
            avoidNulls(binding.etGuidProcesoConvenio.text.toString())
        validateDriverLicenseIn.idServicio = avoidNullsInt(binding.etIdService.text.toString())
        validateDriverLicenseIn.image = imageBase64!!
        validateDriverLicenseIn.subTipo = avoidNullsInt(binding.etSubType.text.toString())
        validateDriverLicenseIn.usuario = avoidNulls(binding.etUser.text.toString())

        ServicesOlimpia.getInstance()
            .onValidationDriverLicense(
                validateDriverLicenseIn,
                object : OlimpiaInterface.CallbackValidationDriverLicense {

                    override fun onSuccess(validateDriverLicenseOut: ValidateDriverLicenseOut?) {
                        dismissProgressDialog()
                        binding.txtResult.text = "${validateDriverLicenseOut?.toString()}"
                    }

                    override fun onError(transactionResponse: RespuestaTransaccion?) {
                        dismissProgressDialog()
                        binding.txtResult.text = "$transactionResponse"
                    }
                })
    }

    private fun launchValidateFederalLicence() {
        //Set values into ValidateFederalDriverLicenseIn
        val validateFederalDriverLicenseIn = ValidateFederalDriverLicenseIn()

        validateFederalDriverLicenseIn.formato = avoidNulls(binding.etFormat.text.toString())
        validateFederalDriverLicenseIn.guidProcesoConvenio =
            avoidNulls(binding.etGuidProcesoConvenio.text.toString())
        validateFederalDriverLicenseIn.idServicio =
            avoidNullsInt(binding.etIdService.text.toString())
        validateFederalDriverLicenseIn.image = ""
        validateFederalDriverLicenseIn.subTipo = avoidNullsInt(binding.etSubType.text.toString())
        validateFederalDriverLicenseIn.usuario = avoidNulls(binding.etUser.text.toString())
        validateFederalDriverLicenseIn.numLicencia =
            avoidNulls(binding.etNumLicencia.text.toString())
        validateFederalDriverLicenseIn.numMedPreventiva =
            avoidNulls(binding.etNumMedPreventiva.text.toString())

        ServicesOlimpia.getInstance()
            .onValidationFederalDriverLicense(
                validateFederalDriverLicenseIn,
                object : OlimpiaInterface.CallbackValidationFederalDriverLicense {

                    override fun onSuccess(validateFederalDriverLicenseOut: ValidateFederalDriverLicenseOut?) {
                        dismissProgressDialog()
                        binding.txtResult.text = "${validateFederalDriverLicenseOut?.toString()}"
                    }

                    override fun onError(transactionResponse: RespuestaTransaccion?) {
                        dismissProgressDialog()
                        binding.txtResult.text = "$transactionResponse"
                    }
                })
    }

    private fun launchIPandGPS() {
        //Get values from saveTraceabilityProcessIn
        saveTraceabilityProcessIn.procesoConvenioGuid = avoidNulls(binding.etGuidProcesoConvenio.text.toString())

        ServicesOlimpia.getInstance()
            .onSaveTraceability(
                saveTraceabilityProcessIn,
                object : OlimpiaInterface.CallbackSaveTraceability {

                    override fun onSuccess(saveTraceabilityOut: SaveTraceabilityOut?) {
                        dismissProgressDialog()
                        binding.txtResult.text = "${saveTraceabilityOut?.toString()}"
                    }

                    override fun onError(transactionResponse: RespuestaTransaccion?) {
                        dismissProgressDialog()
                        binding.txtResult.text = "$transactionResponse"
                    }
                })
    }

}