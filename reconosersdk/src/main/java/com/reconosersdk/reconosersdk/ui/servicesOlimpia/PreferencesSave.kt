package com.reconosersdk.reconosersdk.ui.servicesOlimpia

import android.content.SharedPreferences
import com.reconosersdk.reconosersdk.ui.LibraryReconoSer
import com.reconosersdk.reconosersdk.ui.LibraryReconoSer.context

object PreferencesSave {

    val PREFS_SAVE = "SDK_RECONOCER_PREFS"
    val MODE_SAVE = "MODE_SAVE"
    val STORAGE_QUALITY = "STORAGE_QUALITY"
    val ID_GUIDCONV = "ID_GUIDCONV"

    val DEFAULT: String? = "0"
    val EMPTY = ""
    val GUID_CONVENIO = LibraryReconoSer.getGuidConv()

    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_SAVE, 0)

    var savePhoto: String?
        get() = prefs.getString(MODE_SAVE, DEFAULT)
        set(value) = prefs.edit().putString(MODE_SAVE, value).apply()

    var storageQuality: Int
        get() = prefs.getInt(STORAGE_QUALITY, 70)
        set(value) = prefs.edit().putInt(STORAGE_QUALITY, value).apply()

    var idGuidConv: String?
        //get() = prefs.getString(ID_GUIDCONV, EMPTY)
        get() = prefs.getString(ID_GUIDCONV, GUID_CONVENIO)
        set(value) = prefs.edit().putString(ID_GUIDCONV, value).apply()
}