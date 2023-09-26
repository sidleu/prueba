package com.reconosersdk.reconosersdk.http;

import android.app.Activity;
import android.content.SharedPreferences;

import com.reconosersdk.reconosersdk.ui.LibraryReconoSer;
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.PreferencesSave;

class PreferencesSDK {

    private final String SDK_PREFS = "SDK_PREFS";
    private final String STATUS_CONV = "STATUS_CONV";
    private final String SERVICES_AGREEMENT = "SERVICES_AGREEMENT";
    private final String SERVICES_OTP = "SERVICES_OTP";
    private final String SERVICES_SMS = "SMS";
    private final String SERVICES_EMAIL = "EMAIL";
    private final String SERVICES_BIOMETRY = "BIOMETRY";
    private final String SERVICES_BIOMETRY_DOC = "BIOMETRY_DOC";
    private final String SERVICES_DOC_ANVERSO = "DOC_ANVERSO";
    private final String SERVICES_DOC_REVERSO = "DOC_REVERSO";
    private final String SERVICES_BIOMETRY_FACE = "BIOMETRY_FACE";
    private final String SERVICES_BARCODE = "BARCODE";
    private final String SERVICES_JSON = "SERVICES_JSON";
    private final String GUIDCONVENIO = "GUIDCONVENIO";
    private final String SERVICE_SAVE_PHOTO = "SERVICE_SAVE_PHOTO";

    private final String DEFAULT = null;
    private SharedPreferences appSharedPrefs;

    private static class SingletonHolder {
        private static final PreferencesSDK INSTANCE = new PreferencesSDK();
    }

    public static PreferencesSDK getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private PreferencesSDK() {
        appSharedPrefs = LibraryReconoSer.context.getSharedPreferences(SDK_PREFS, Activity.MODE_PRIVATE);
    }

    public void saveStatusConv(boolean status) {
        appSharedPrefs.edit().putBoolean(STATUS_CONV, status).apply();
    }

    public boolean getStatus() {
        return appSharedPrefs.getBoolean(STATUS_CONV, false);
    }

    public void saveServicesJson(String servicesJson) {
        appSharedPrefs.edit().putString(SERVICES_JSON, servicesJson).apply();
    }

    public String getSERVICES_JSON() {
        return appSharedPrefs.getString(SERVICES_JSON, "");
    }

    // -------------- OTP ----------------------//

    public void saveServicOTP(boolean status) {
        appSharedPrefs.edit().putBoolean(SERVICES_OTP, status).apply();
    }

    public boolean getServicOTP() {
        return appSharedPrefs.getBoolean(SERVICES_OTP, false);
    }


    public void saveServiceEmail(boolean status) {
        appSharedPrefs.edit().putBoolean(SERVICES_EMAIL, status).apply();
    }

    public boolean getServiceEmail() {
        return appSharedPrefs.getBoolean(SERVICES_SMS, false);
    }

    public void saveServiceSMS(boolean status) {
        appSharedPrefs.edit().putBoolean(SERVICES_SMS, status).apply();
    }

    public boolean getServiceSMS() {
        return appSharedPrefs.getBoolean(SERVICES_SMS, false);
    }

    //----------------- BIOMETRY --------------------//

    public void saveServiceBioDoc(boolean status) {
        appSharedPrefs.edit().putBoolean(SERVICES_BIOMETRY_DOC, status).apply();
    }

    public boolean getServiceBioDoc() {
        return appSharedPrefs.getBoolean(SERVICES_BIOMETRY_DOC, false);
    }

    public void saveServiceDocAnv(boolean status) {
        appSharedPrefs.edit().putBoolean(SERVICES_DOC_ANVERSO, status).apply();
    }

    public boolean getServiceDocAnv() {
        return appSharedPrefs.getBoolean(SERVICES_DOC_ANVERSO, false);
    }

    public void saveServiceDocRev(boolean status) {
        appSharedPrefs.edit().putBoolean(SERVICES_DOC_REVERSO, status).apply();
    }

    public boolean getServiceDocRev() {
        return appSharedPrefs.getBoolean(SERVICES_DOC_REVERSO, false);
    }

    public void saveServiceBarcod(boolean status) {
        appSharedPrefs.edit().putBoolean(SERVICES_BARCODE, status).apply();
    }

    public boolean getServiceBarcode() {
        return appSharedPrefs.getBoolean(SERVICES_BARCODE, false);
    }

    public void saveServiceBioFace(boolean status) {
        appSharedPrefs.edit().putBoolean(SERVICES_BIOMETRY_FACE, status).apply();
    }

    public boolean getServiceBioFace() {
        return appSharedPrefs.getBoolean(SERVICES_BIOMETRY_FACE, false);
    }

    public void saveGuidConvenio(String guidConvenio) {
        appSharedPrefs.edit().putString(GUIDCONVENIO, guidConvenio).apply();
        PreferencesSave.INSTANCE.setIdGuidConv(guidConvenio);
    }

    public String getGuidConvenio() {
        return appSharedPrefs.getString(GUIDCONVENIO, "");
    }

    public void saveSavePhoto(String statusSave) {
        PreferencesSave.INSTANCE.setSavePhoto(statusSave);
        appSharedPrefs.edit().putString(SERVICE_SAVE_PHOTO, statusSave).apply();
    }

    public String getSavePhoto() {
        return appSharedPrefs.getString(SERVICE_SAVE_PHOTO, "0");
    }

    public void removePreferences() {
        appSharedPrefs.edit().remove(SERVICES_OTP).apply();
        appSharedPrefs.edit().remove(SERVICES_SMS).apply();
        appSharedPrefs.edit().remove(SERVICES_EMAIL).apply();
        appSharedPrefs.edit().remove(SERVICES_BIOMETRY).apply();
        appSharedPrefs.edit().remove(SERVICES_BIOMETRY_FACE).apply();
        appSharedPrefs.edit().remove(SERVICES_BIOMETRY_DOC).apply();
        appSharedPrefs.edit().remove(SERVICES_DOC_ANVERSO).apply();
        appSharedPrefs.edit().remove(SERVICES_DOC_REVERSO).apply();
        appSharedPrefs.edit().remove(GUIDCONVENIO).apply();
        appSharedPrefs.edit().remove(SERVICE_SAVE_PHOTO).apply();
    }
}
