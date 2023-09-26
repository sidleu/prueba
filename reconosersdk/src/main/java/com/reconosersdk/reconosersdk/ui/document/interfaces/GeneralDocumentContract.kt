package com.reconosersdk.reconosersdk.ui.document.interfaces

import android.content.Context
import android.os.Bundle
import com.reconosersdk.reconosersdk.http.regula.entities.out.ValidarDocumentoGenericoOut
import com.reconosersdk.reconosersdk.ui.base.MvpPresenter
import com.reconosersdk.reconosersdk.ui.base.MvpView

interface GeneralDocumentContract {

    interface GeneralDocumentMvpView : MvpView {
        fun initUI()
        fun onFinish(
        )
        fun onFinishData(
                pathFront: String,
                pathBack: String,
                response: ValidarDocumentoGenericoOut
        )
        fun onShowProgress(msg: Int)
        fun onHideProgress()
    }

    interface GeneralDocumentPresenter <V : GeneralDocumentMvpView> : MvpPresenter<V> {
        fun initUI(extras: Bundle)
        fun onLoadImage(imageTakeFront: String, imageTakeBack: String, context: Context)
    }

}