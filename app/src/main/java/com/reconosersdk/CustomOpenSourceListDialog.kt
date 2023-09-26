package com.reconosersdk

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.*

class CustomOpenSourceListDialog {
    lateinit var dialog: Dialog
    lateinit var view: View
    var isCLFSUK : Boolean = false
    var isUETERR : Boolean = false
    var isSDN : Boolean = false

    var isWORLDBANK : Boolean = false
    var isBIS : Boolean = false
    var isONU : Boolean = false

    var isIPSCAM : Boolean = false
    var isHIBP : Boolean = false


    fun show(context: Context): Dialog {
        return show(context, null)
    }

    fun show(context: Context, title: CharSequence?): Dialog {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.view_custom_op_list, null)

        (view.findViewById<View>(R.id.customAlertDialog) as ConstraintLayout).setBackgroundColor(Color.parseColor("#70000000")) // Background Color
        (view.findViewById<View>(R.id.cad_cardview) as CardView).setCardBackgroundColor(Color.parseColor("#FFFFFF")) // Box Color
        dialog = Dialog(context, R.style.CustomProgressBarTheme)
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        if(!dialog.isShowing) {
            dialog.show()
        }
        return dialog
    }

    fun changeRadioValues() {
        val clfsuk = (view.findViewById<View>(R.id.rb_clfsuk) as RadioButton)
        clfsuk.setOnClickListener {
            clfsuk.isChecked = !isCLFSUK
            isCLFSUK = !isCLFSUK
        }

        val ueterr = (view.findViewById<View>(R.id.rb_ueterr) as RadioButton)
        ueterr.setOnClickListener {
            ueterr.isChecked = !isUETERR
            isUETERR = !isUETERR
        }

        val sdn = (view.findViewById<View>(R.id.rb_sdn) as RadioButton)
        sdn.setOnClickListener {
            sdn.isChecked = !isSDN
            isSDN = !isSDN
        }

        val worldbank = (view.findViewById<View>(R.id.rb_worldbank) as RadioButton)
        worldbank.setOnClickListener {
            worldbank.isChecked = !isWORLDBANK
            isWORLDBANK = !isWORLDBANK
        }

        val bis = (view.findViewById<View>(R.id.rb_bis) as RadioButton)
        bis.setOnClickListener {
            bis.isChecked = !isBIS
            isBIS = !isBIS
        }

        val onu = (view.findViewById<View>(R.id.rb_onu) as RadioButton)
        onu.setOnClickListener {
            onu.isChecked = !isONU
            isONU = !isONU
        }

        val ipscam = (view.findViewById<View>(R.id.rb_ipscam) as RadioButton)
        ipscam.setOnClickListener {
            ipscam.isChecked = !isIPSCAM
            isIPSCAM = !isIPSCAM
        }

        val hibp = (view.findViewById<View>(R.id.rb_hibp) as RadioButton)
        hibp.setOnClickListener {
            hibp.isChecked = !isHIBP
            isHIBP = !isHIBP
        }

    }

    fun getRobotValues() : List<String?>{
        var list: MutableList<String> = mutableListOf()
        if((view.findViewById<View>(R.id.rb_clfsuk) as RadioButton).isChecked) { list.add("CLFSUK")}
        if((view.findViewById<View>(R.id.rb_ueterr) as RadioButton).isChecked) { list.add("UETERR")}
        if((view.findViewById<View>(R.id.rb_sdn) as RadioButton).isChecked) {list.add("SDN")}
        if((view.findViewById<View>(R.id.rb_worldbank) as RadioButton).isChecked) { list.add("WORLDBANK")}
        if((view.findViewById<View>(R.id.rb_bis) as RadioButton).isChecked) { list.add("BIS")}
        if((view.findViewById<View>(R.id.rb_onu) as RadioButton).isChecked) {list.add("ONU")}
        if((view.findViewById<View>(R.id.rb_ipscam) as RadioButton).isChecked) { list.add("IPSCAM")}
        if((view.findViewById<View>(R.id.rb_hibp) as RadioButton).isChecked) { list.add("HIBP")}

        if(list.isEmpty()){
            list = Collections.emptyList()
        }
        return list
    }

    fun hideProgress() {
        if (::dialog.isInitialized && dialog.isShowing) {
            allInFalse()
            dialog.dismiss()
        }
    }

    private fun allInFalse() {
        isCLFSUK = false
        isUETERR = false
        isSDN = false
        isWORLDBANK = false
        isBIS = false
        isONU = false
        isIPSCAM = false
        isHIBP = false
    }
}