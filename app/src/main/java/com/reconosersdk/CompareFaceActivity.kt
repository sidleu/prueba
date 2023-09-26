package com.reconosersdk

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.reconosersdk.databinding.ActivityCompareFaceBinding
import com.reconosersdk.reconosersdk.http.OlimpiaInterface
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.DataComparisonFace
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid.RespondComparasionFace
import com.reconosersdk.reconosersdk.ui.LibraryReconoSer
import com.reconosersdk.reconosersdk.ui.bioFacial.views.LivePreviewActivity
import com.reconosersdk.reconosersdk.ui.document.views.RequestDocumentActivity
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.ServicesOlimpia
import com.reconosersdk.reconosersdk.utils.ImageUtils
import com.reconosersdk.reconosersdk.utils.IntentExtras
import com.reconosersdk.reconosersdk.utils.JsonUtils

class CompareFaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompareFaceBinding
    private  var path1: String? = ""
    private  var path2: String? = ""
    var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val yourIcon =
            resources.getDrawable(R.drawable.back, null)
        supportActionBar!!.setHomeAsUpIndicator(yourIcon)

        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val inflator = LayoutInflater.from(this)
        val v = inflator.inflate(R.layout.titleview, null)

        (v.findViewById<View>(R.id.title) as TextView).text = this.title
        supportActionBar!!.customView = v

        binding = DataBindingUtil.setContentView(this, R.layout.activity_compare_face)
        initListener()
    }

    private fun initListener() {
        binding.btnPhoto1.setOnClickListener {
            binding.txtResult.text = ""
            takePhoto1()
        }
        binding.btnPhoto2.setOnClickListener {
            binding.txtResult.text = ""
            takePhot2()
        }
        binding.btnCompare.setOnClickListener {
            if (path1.isNullOrEmpty().not() && path2.isNullOrEmpty().not()) {
                comparePhoto()
            }
        }

        binding.swtFlash.setOnCheckedChangeListener { p0, p1 ->
            if (binding.swtFlash.isChecked) {
                binding.imgFlash.setColorFilter(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.yellow_bulb
                    )
                )
            } else {
                binding.imgFlash.setColorFilter(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.gray_hint
                    )
                )
            }
        }

        binding.swtCamera.setOnCheckedChangeListener { p0, p1 ->
            if (binding.swtCamera.isChecked) {
                binding.imgCamera.setColorFilter(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.yellow_bulb
                    )
                )
            } else {
                binding.imgCamera.setColorFilter(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.gray_hint
                    )
                )
            }
        }
    }

    private fun takePhoto1() {
        val intent = Intent(this, RequestDocumentActivity::class.java)
        intent.putExtra(IntentExtras.TEXT_SCAN, "Anverso")
        intent.putExtra(IntentExtras.GUID_CIUDADANO, binding.etGuid.text.toString())
        intent.putExtra(IntentExtras.TYPE_DOCUMENT, binding.etType.text.toString())
        intent.putExtra(IntentExtras.NUM_DOCUMENT, binding.etNumDoc.text.toString())
        intent.putExtra(IntentExtras.SAVE_USER, binding.etUser.text.toString())
        startActivityForResult(intent, MainActivity.DOCUMENT_REQUEST)
    }

    private fun takePhot2() {
        val intent = Intent(this, LivePreviewActivity::class.java)
        intent.putExtra(IntentExtras.GUID_CIUDADANO, binding.etGuid.text.toString())
        intent.putExtra(IntentExtras.TYPE_DOCUMENT, binding.etType.text.toString())
        intent.putExtra(IntentExtras.NUM_DOCUMENT, binding.etNumDoc.text.toString())
        intent.putExtra(IntentExtras.SAVE_USER, binding.etUser.text.toString())
        intent.putExtra(IntentExtras.ACTIVATE_FLASH, binding.swtFlash.isChecked)
        intent.putExtra(IntentExtras.CHANGE_CAMERA, binding.swtCamera.isChecked)
        startActivityForResult(intent, MainActivity.FACE)
    }

    private fun comparePhoto() {
        loading("")
        var dataCompare = DataComparisonFace(
            LibraryReconoSer.getGuidConv(),
            ImageUtils.convert64String(path1.orEmpty()),
            "JPG_B64",
            ImageUtils.convert64String(path2.orEmpty()),
            "JPG_B64",
            "",
            ""
        )
        ServicesOlimpia.getInstance()
            .getCompararionFace(
                dataCompare,
                object : OlimpiaInterface.CallbackReconoserComparasion {
                    override fun onSuccess(respondComparasionFace: RespondComparasionFace?) {
                        respondComparasionFace?.let {
                            binding.txtResult.text = JsonUtils.stringObject(it)
                            dismissProgressDialog()
                        }
                    }

                    override fun onError(transactionResponse: RespuestaTransaccion?) {
                        binding.txtResult.text = JsonUtils.stringObject(
                            transactionResponse!!
                        )
                        dismissProgressDialog()
                    }

                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == MainActivity.DOCUMENT_REQUEST) {
                if (resultCode == Activity.RESULT_OK) {
                    onRespondDoc(data)
                } else if (resultCode == IntentExtras.ERROR_INTENT) {
                    onErrorIntent(data)
                }
            } else if (requestCode == MainActivity.FACE) {
                if (resultCode == Activity.RESULT_OK) {
                    onRespondFace(data)
                } else if (resultCode == IntentExtras.ERROR_INTENT) {
                    onErrorIntent(data)
                }
            }
        }
    }

    private fun onRespondDoc(data: Intent?) {
        data?.let {
            path1 = data.getStringExtra(IntentExtras.PATH_FILE_PHOTO)
            Glide.with(this).load(path1)
                .into(
                    binding.imgResult
                )
        }
    }

    private fun onRespondFace(data: Intent?) {
        data?.let {
            path2 = it.getStringExtra(IntentExtras.PATH_FILE_PHOTO_R)
            Glide.with(this).load(path2)
                .into(
                    binding.imgBiometria
                )
        }
    }

    private fun onErrorIntent(data: Intent?) {
        if (data?.extras!!.containsKey(IntentExtras.ERROR_MSG)) {
            binding.txtResult.text = data?.getStringExtra(IntentExtras.ERROR_MSG)
        } else if (data.extras!![IntentExtras.ERROR_SDK] != null) {
            var  extras = data.extras!!.get(IntentExtras.ERROR_SDK)
            binding.txtResult.text = extras?.let {
                JsonUtils.stringObject(
                    it
                )
            }
        }
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