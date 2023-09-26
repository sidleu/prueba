package com.reconosersdk.reconosersdk.ui.document.interfaces

import android.content.Context
import android.os.Bundle
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidateReceiptOut
import com.reconosersdk.reconosersdk.http.regula.entities.out.ValidarDocumentoGenericoOut
import com.reconosersdk.reconosersdk.ui.base.MvpPresenter
import com.reconosersdk.reconosersdk.ui.base.MvpView

interface ExtraDocumentContract {

    interface ExtraDocumentMvpView : MvpView {
        fun initUI(title: String, typeExtraValue: Int)
        fun onFinish(
        )

        fun onFinishData(
            pathFront: String,
            base64: String,
        )

        fun onShowProgress(msg: Int)
        fun onHideProgress()
    }

    interface ExtraDocumentPresenter<V : ExtraDocumentMvpView> : MvpPresenter<V> {
        fun initUI(extras: Bundle)
        fun onLoadImage(imageTakeFront: String, context: Context)
    }

}

