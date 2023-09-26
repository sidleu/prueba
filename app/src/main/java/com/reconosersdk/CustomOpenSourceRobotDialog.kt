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

class CustomOpenSourceRobotDialog {
    lateinit var dialog: Dialog
    lateinit var view: View
    var isTyba : Boolean = false
    var isProcu : Boolean = false
    var isContral : Boolean = false

    var isSisben : Boolean = false
    var isAdres : Boolean = false
    var isJemps : Boolean = false

    var isRuaf : Boolean = false
    var isPoli : Boolean = false
    var isRenapo : Boolean = false

    var isInframex : Boolean = false
    var isCurprfc : Boolean = false
    var isRenapo2 : Boolean = false

    var isRues : Boolean = false
    var isEcoJudi : Boolean = false
    var isAnteecu : Boolean = false
    var isRegisecu : Boolean = false


    fun show(context: Context): Dialog {
        return show(context, null)
    }

    fun show(context: Context, title: CharSequence?): Dialog {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.view_custom_op_robot, null)

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
        val tyba = (view.findViewById<View>(R.id.rb_tyba) as RadioButton)
        tyba.setOnClickListener {
            tyba.isChecked = !isTyba
            isTyba = !isTyba
        }

        val procu = (view.findViewById<View>(R.id.rb_procu) as RadioButton)
        procu.setOnClickListener {
            procu.isChecked = !isProcu
            isProcu = !isProcu
        }

        val contral = (view.findViewById<View>(R.id.rb_contral) as RadioButton)
        contral.setOnClickListener {
            contral.isChecked = !isContral
            isContral = !isContral
        }

        val sisben = (view.findViewById<View>(R.id.rb_sisben) as RadioButton)
        sisben.setOnClickListener {
            sisben.isChecked = !isSisben
            isSisben = !isSisben
        }

        val adres = (view.findViewById<View>(R.id.rb_adres) as RadioButton)
        adres.setOnClickListener {
            adres.isChecked = !isAdres
            isAdres = !isAdres
        }

        val rb_jepms = (view.findViewById<View>(R.id.rb_jepms) as RadioButton)
        rb_jepms.setOnClickListener {
            rb_jepms.isChecked = !isJemps
            isJemps = !isJemps
        }

        val ruaf = (view.findViewById<View>(R.id.rb_ruaf) as RadioButton)
        ruaf.setOnClickListener {
            ruaf.isChecked = !isRuaf
            isRuaf = !isRuaf
        }

        val poli = (view.findViewById<View>(R.id.rb_poli) as RadioButton)
        poli.setOnClickListener {
            poli.isChecked = !isPoli
            isPoli = !isPoli
        }

        val rb_renapo = (view.findViewById<View>(R.id.rb_renapo) as RadioButton)
        rb_renapo.setOnClickListener {
            rb_renapo.isChecked = !isRenapo
            isRenapo = !isRenapo
        }

        val inframex = (view.findViewById<View>(R.id.rb_inframex) as RadioButton)
        inframex.setOnClickListener {
            inframex.isChecked = !isInframex
            isInframex = !isInframex
        }

        val curprfc = (view.findViewById<View>(R.id.rb_curprfc) as RadioButton)
        curprfc.setOnClickListener {
            curprfc.isChecked = !isCurprfc
            isCurprfc = !isCurprfc
        }

        val rb_renapo2 = (view.findViewById<View>(R.id.rb_renapo2) as RadioButton)
        rb_renapo2.setOnClickListener {
            rb_renapo2.isChecked = !isRenapo2
            isRenapo2 = !isRenapo2
        }

        val rues = (view.findViewById<View>(R.id.rb_rues) as RadioButton)
        rues.setOnClickListener {
            rues.isChecked = !isRues
            isRues = !isRues
        }

        val econjudi = (view.findViewById<View>(R.id.rb_econjudi) as RadioButton)
        econjudi.setOnClickListener {
            econjudi.isChecked = !isEcoJudi
            isEcoJudi = !isEcoJudi
        }

        val anteecu = (view.findViewById<View>(R.id.rb_anteecu) as RadioButton)
        anteecu.setOnClickListener {
            anteecu.isChecked = !isAnteecu
            isAnteecu = !isAnteecu
        }

        val regisecu = (view.findViewById<View>(R.id.rb_regisecu) as RadioButton)
        regisecu.setOnClickListener {
            regisecu.isChecked = !isRegisecu
            isRegisecu = !isRegisecu
        }
    }

    fun getRobotValues() : List<String?>{
        var robot: MutableList<String> = mutableListOf()
        if((view.findViewById<View>(R.id.rb_tyba) as RadioButton).isChecked) { robot.add("TYBA")}
        if((view.findViewById<View>(R.id.rb_procu) as RadioButton).isChecked) { robot.add("PROCU")}
        if((view.findViewById<View>(R.id.rb_contral) as RadioButton).isChecked) {robot.add("CONTRAL")}

        if((view.findViewById<View>(R.id.rb_sisben) as RadioButton).isChecked) { robot.add("SISBEN")}
        if((view.findViewById<View>(R.id.rb_adres) as RadioButton).isChecked) { robot.add("PROCU")}
        if((view.findViewById<View>(R.id.rb_jepms) as RadioButton).isChecked) {robot.add("JEPMS")}

        if((view.findViewById<View>(R.id.rb_ruaf) as RadioButton).isChecked) { robot.add("RUAF")}
        if((view.findViewById<View>(R.id.rb_poli) as RadioButton).isChecked) { robot.add("POLI")}
        if((view.findViewById<View>(R.id.rb_renapo) as RadioButton).isChecked) {robot.add("RENAPO")}

        if((view.findViewById<View>(R.id.rb_inframex) as RadioButton).isChecked) { robot.add("INFRAMEX")}
        if((view.findViewById<View>(R.id.rb_curprfc) as RadioButton).isChecked) { robot.add("CURPRFC")}
        if((view.findViewById<View>(R.id.rb_renapo2) as RadioButton).isChecked) {robot.add("RENAPO2")}

        if((view.findViewById<View>(R.id.rb_rues) as RadioButton).isChecked) { robot.add("RUES")}
        if((view.findViewById<View>(R.id.rb_econjudi) as RadioButton).isChecked) { robot.add("ECONJUDI")}
        if((view.findViewById<View>(R.id.rb_anteecu) as RadioButton).isChecked) {robot.add("ANTEECU")}
        if((view.findViewById<View>(R.id.rb_regisecu) as RadioButton).isChecked) {robot.add("REGISECU")}

        if(robot.isEmpty()){
            robot = Collections.emptyList()
        }
        return robot
    }

    fun hideProgress() {
        if (::dialog.isInitialized && dialog.isShowing) {
            allInFalse()
            dialog.dismiss()
        }
    }

    private fun allInFalse() {
        isTyba = false
        isProcu = false
        isContral = false

        isSisben = false
        isAdres = false
        isJemps = false

        isRuaf = false
        isPoli = false
        isRenapo = false

        isInframex = false
        isCurprfc = false
        isRenapo2 = false

        isRues = false
        isEcoJudi = false
        isAnteecu = false
        isRegisecu = false
    }
}