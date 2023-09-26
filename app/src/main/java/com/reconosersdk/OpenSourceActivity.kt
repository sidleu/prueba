package com.reconosersdk

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.reconosersdk.databinding.ActivityOpenSourceBinding
import com.reconosersdk.reconosersdk.http.OlimpiaInterface
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion
import com.reconosersdk.reconosersdk.http.openSource.`in`.ConsultarFuentesAbiertasIn
import com.reconosersdk.reconosersdk.http.openSource.`in`.SolicitudFuentesAbiertasIn
import com.reconosersdk.reconosersdk.http.openSource.out.ConsultarFuentesAbiertasOut
import com.reconosersdk.reconosersdk.http.openSource.out.ParametroFuenteOut
import com.reconosersdk.reconosersdk.http.openSource.out.SolicitudFuentesAbiertasOut
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.ServicesOlimpia
import com.reconosersdk.reconosersdk.utils.JsonUtils
import java.util.*

class OpenSourceActivity : AppCompatActivity() {

    //Every services to implement and use
    private val SOLICITUD_FUENTES_ABIERTAS = "SOLICITUD FUENTES ABIERTAS"
    private val CONSULTAR_FUENTES_ABIERTAS = "CONSULTAR FUENTES ABIERTAS"
    private val LISTAS_FUENTES_ABIERTAS = "LISTAS FUENTES ABIERTAS"

    private lateinit var binding: ActivityOpenSourceBinding
    private var opSelected : Int = 0
    private var mProgressDialog: ProgressDialog? = null

    lateinit var customOpenSourceRobotDialog: CustomOpenSourceRobotDialog
    lateinit var customOpenSourceListDialog: CustomOpenSourceListDialog
    var isShowingRobot: Boolean = false
    var isShowingList: Boolean = false
    var robot: List<String?> = mutableListOf()
    var list: List<String?> = mutableListOf()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_open_source)

        val yourIcon =
                resources.getDrawable(R.drawable.back, null)
        supportActionBar!!.setHomeAsUpIndicator(yourIcon)

        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val inflator = LayoutInflater.from(this)
        val v = inflator.inflate(R.layout.titleview, null)

        (v.findViewById<View>(R.id.title) as TextView).text = this.title
        supportActionBar!!.customView = v

        binding = DataBindingUtil.setContentView(this, R.layout.activity_open_source)
        customOpenSourceRobotDialog = CustomOpenSourceRobotDialog()
        robot = emptyList()
        list = emptyList()
        customOpenSourceListDialog = CustomOpenSourceListDialog()

        setSpinner()
        setButtonsClick()
    }

    private fun setButtonsClick() {
        binding.buttonProcess.setOnClickListener {
            loading(getString(R.string.loading))
            createServices()
        }
        binding.etxOsRobot.setOnClickListener {
            openRobotOpenSources()
        }
        binding.etxOsList.setOnClickListener {
            openListOpenSources()
        }
    }

    private fun openRobotOpenSources() {
        if(!isShowingRobot){
            isShowingRobot = true
            customOpenSourceRobotDialog.show(this)
        }
        if (::customOpenSourceRobotDialog.isInitialized && customOpenSourceRobotDialog.dialog.isShowing) {

            customOpenSourceRobotDialog.changeRadioValues()

            (customOpenSourceRobotDialog.view.findViewById<View>(R.id.btn_Cancel) as Button).setOnClickListener {
                //hideKeyboardFrom(this)
                customOpenSourceRobotDialog.hideProgress()
                isShowingRobot = false
            }

            (customOpenSourceRobotDialog.view.findViewById<View>(R.id.btn_Send) as Button).setOnClickListener {
                //hideKeyboardFrom(this)
                binding.etxOsRobot.setText(customOpenSourceRobotDialog.getRobotValues().toString())
                robot = customOpenSourceRobotDialog.getRobotValues()
                customOpenSourceRobotDialog.hideProgress()
                isShowingRobot = false
            }
        }
    }

    private fun openListOpenSources() {
        if(!isShowingList){
            isShowingList = true
            customOpenSourceListDialog.show(this)
        }
        if (::customOpenSourceListDialog.isInitialized && customOpenSourceListDialog.dialog.isShowing) {

            customOpenSourceListDialog.changeRadioValues()

            (customOpenSourceListDialog.view.findViewById<View>(R.id.btn_Cancel) as Button).setOnClickListener {
                //hideKeyboardFrom(this)
                customOpenSourceListDialog.hideProgress()
                isShowingList = false
            }

            (customOpenSourceListDialog.view.findViewById<View>(R.id.btn_Send) as Button).setOnClickListener {
                //hideKeyboardFrom(this)
                binding.etxOsList.setText(customOpenSourceListDialog.getRobotValues().toString())
                list = customOpenSourceListDialog.getRobotValues()
                customOpenSourceListDialog.hideProgress()
                isShowingList = false
            }
        }
    }


    private fun createServices() {
        when (opSelected) {
            0 -> requestOpenSources()
            1 -> consultOpenSources()
            else -> {
                listOpenSources()
            }
        }
    }

    private fun requestOpenSources() {
        if(isAnyEmpty() ){
            binding.textViewProcess.text = "Hay campos vacios"
            dismissProgressDialog()
        }else{
            getRequestOpenSources()
        }
    }


    private fun consultOpenSources() {
        if(isAnyEmpty() || binding.etxOsGuid.text.toString().isEmpty()  ){
            binding.textViewProcess.text = "Hay campos vacios"
            dismissProgressDialog()
        }else{
            getConsultOpenSources()
        }
    }

    private fun getRequestOpenSources() {
        val solicitudFuentesAbiertasIn = SolicitudFuentesAbiertasIn(
                binding.etxGuidCv.text.toString(),
                binding.etxTypeDoc.text.toString(),
                binding.etxDoc.text.toString(),
                robot,
                list,
                "",
                binding.etxUser.text.toString(),
                binding.etxPass.text.toString()
        )
        ServicesOlimpia.getInstance().getRequestOpenSource(solicitudFuentesAbiertasIn,
                object : OlimpiaInterface.CallbackReconoserRequestOpenSource {
                    override fun onSuccess(solicitudFuentesAbiertasOut: SolicitudFuentesAbiertasOut?) {
                        solicitudFuentesAbiertasOut?.let {
                            binding.textViewProcess.text = JsonUtils.stringObject(it)
                            binding.etxOsGuid.setText(it.data?.transaccionGuid)
                            dismissProgressDialog()
                        }
                    }

                    override fun onError(transactionResponse: RespuestaTransaccion?) {
                        binding.textViewProcess.text = JsonUtils.stringObject(
                                transactionResponse!!
                        )
                        dismissProgressDialog()
                    }

                })
    }

    private fun getConsultOpenSources() {
        val consultarFuentesAbiertasIn = ConsultarFuentesAbiertasIn(
                binding.etxGuidCv.text.toString(),
                binding.etxOsGuid.text.toString(),
                binding.etxTypeDoc.text.toString(),
                binding.etxDoc.text.toString(),
                Collections.emptyList(),
                Collections.emptyList(),
                "",
                binding.etxUser.text.toString(),
                binding.etxPass.text.toString()
        )
        ServicesOlimpia.getInstance().getConsultOpenSource(consultarFuentesAbiertasIn,
                object : OlimpiaInterface.CallbackReconoserConsultOpenSource {
                    override fun onSuccess(consultarFuentesAbiertasOut: ConsultarFuentesAbiertasOut?) {
                        consultarFuentesAbiertasOut?.let {
                            binding.textViewProcess.text = JsonUtils.stringObject(it)
                            dismissProgressDialog()
                        }
                    }

                    override fun onError(transactionResponse: RespuestaTransaccion?) {
                        binding.textViewProcess.text = JsonUtils.stringObject(
                                transactionResponse!!
                        )
                        dismissProgressDialog()
                    }

                })
    }

    private fun listOpenSources(){
        ServicesOlimpia.getInstance().getListOpenSource(
                object : OlimpiaInterface.CallbackReconoserListOpenSource {
                    override fun onSuccess(parametroFuenteOut: ParametroFuenteOut?) {
                        parametroFuenteOut?.let {
                            binding.textViewProcess.text = JsonUtils.stringObject(it)
                            dismissProgressDialog()
                        }
                    }

                    override fun onError(transactionResponse: RespuestaTransaccion?) {
                        binding.textViewProcess.text = JsonUtils.stringObject(
                                transactionResponse!!
                        )
                        dismissProgressDialog()
                    }

                })
    }

    private fun isAnyEmpty(): Boolean{
        return binding.etxGuidCv.text.toString().isEmpty() || binding.etxTypeDoc.text.toString().isEmpty() ||
                binding.etxDoc.text.toString().isEmpty() || binding.etxUser.text.toString().isEmpty() ||
                binding.etxPass.text.toString().isEmpty()
    }

    private fun setSpinner() {
        val optionsOpenSource = getOpenSources()
        val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, R.layout.spinner_style, optionsOpenSource)
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.custom_spinner)
        binding.spinnerOpenSource.adapter = dataAdapter
        binding.spinnerOpenSource.setSelection(0)
        binding.spinnerOpenSource.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                opSelected = spinnerPosition(p0?.getItemAtPosition(position).toString())
                if(p0?.selectedItem.toString()==LISTAS_FUENTES_ABIERTAS){
                    binding.generalLinear.visibility = View.GONE
                }else {
                    binding.generalLinear.visibility = View.VISIBLE
                    if (p0?.selectedItem.toString() == SOLICITUD_FUENTES_ABIERTAS) {
                        binding.linearListSources.visibility = View.VISIBLE
                        binding.linearOpGuid.visibility = View.GONE
                    } else {
                        binding.linearListSources.visibility = View.GONE
                        binding.linearOpGuid.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //No implementation needed
            }
        }
    }

    private fun spinnerPosition(value: String): Int {
        return when (value) {
            SOLICITUD_FUENTES_ABIERTAS -> 0
            CONSULTAR_FUENTES_ABIERTAS -> 1
            else -> {
                2
            }
        }
    }

    //To easily test
    private fun getOpenSources(): Array<String?> {
        return applicationContext.resources.getStringArray(R.array.open_sources_options)
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