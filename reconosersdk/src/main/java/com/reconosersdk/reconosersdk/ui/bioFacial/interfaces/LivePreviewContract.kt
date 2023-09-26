package com.reconosersdk.reconosersdk.ui.bioFacial.interfaces

import android.content.Context
import androidx.annotation.Nullable
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.ValidarBiometriaIn
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidarBiometria
import com.reconosersdk.reconosersdk.ui.base.MvpPresenter
import com.reconosersdk.reconosersdk.ui.base.MvpView

interface LivePreviewContract {

    interface LivePreviewMvpView : MvpView {
        fun onFinish()
        fun onShowProgress(msg: Int)
        fun onHideProgress()
        fun onFinishRekognition(rekognition: Double)
        fun onRespondValidate(validateBiometry: ValidarBiometria)
        fun onCreateProcess(imageTake: String)
    }

    interface LivePreviewPresenter<V : LivePreviewMvpView> : MvpPresenter<V> {

        fun onSetName(
            guidCiudadano: String,
            typeDocument: String,
            numDocument: String,
            validateBiometry: Boolean,
            saveUser: String,
            quality: Int,
            @Nullable guidProcessAgreement: String
        )

        fun onLoadImage(imageTake: String, context: Context, advisor: String, campus: String)
        fun onSaveImage(imageTake: String)
        fun onValidateBiometry(validarBiometria: ValidarBiometriaIn)
    }
}