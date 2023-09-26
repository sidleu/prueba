package com.reconosersdk.reconosersdk.ui.document.interfaces

import android.graphics.Bitmap
import android.os.Binder
import android.os.Bundle
import com.reconosersdk.reconosersdk.ui.base.MvpPresenter
import com.reconosersdk.reconosersdk.ui.base.MvpView

interface DocumentAdContract {

    interface DocumentAdMvpView : MvpView {

        fun initUI(title: Int, msg: Int, screen: Int)
        fun onNext()
        fun onShowButton()
        fun onHideButton()
        fun onTakePhoto(textScan: String, barcode: String, typeBarcode: String, bitmap: Bitmap, document: Int)
        fun onErrorNextFinish(msg: Int)
        fun onShowMaxFaces()
    }

    interface DocumentAdMvpPresenter<V : DocumentAdMvpView> : MvpPresenter<V> {

        fun onStart()
        fun onStop()
        fun initUI(extras: Bundle)
    }
}