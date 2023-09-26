package com.reconosersdk.reconosersdk.ui.document.interfaces

import android.content.Context
import android.os.Bundle
import com.reconosersdk.reconosersdk.entities.Document
import com.reconosersdk.reconosersdk.ui.base.MvpPresenter
import com.reconosersdk.reconosersdk.ui.base.MvpView

interface RequestDocumentContract {

    interface RequestDocumentMvpView : MvpView {

        fun initUI(type: String, guidCiu: String, typeDoc: String, numDoc: String, saveUser: String, addData: String)
        fun onShowErrorCloud(errorMsg: Int)
        fun onFinishData(
            path: String,
            document: Document
        )
        fun onNavigationBarCode(
            reverseText: String,
            documentResult: Int,
            path: String,
        )
        fun onNavigationReverso(
            path: String,
            documentResult: Int
        )
    }

    interface RequestDocumentMvpPresenter<V : RequestDocumentMvpView> : MvpPresenter<V> {

        fun initUI(extras: Bundle, context: Context)
        fun onResultPhoto(
            pathFile: String,
            dataDocument: String,
            barcode: String,
            typeBarcode: String,
            typeDocument: Int
        )
        fun getBitmap(path: String, typeBarcode : String)
        fun tryReloadAndDetectInImage(typeBarcode : String)
        fun onDataRespond(text: String, barcode: String, typeBarcode: String)
        fun onDataRespondReverso(text: String, barcode: String, typeBarcode: String)
        fun onSavePhoto(path: String, document: Document)
        fun onDestroy()
        fun onParseBarCode(barcode: String): String
        fun colombianReverseDocument(reverseText: String, barcode: String, documentResult: Int, typePDF417 : String)
        fun parseForeingCard(barcode: String, reverseText: String, typeBarcode: String)
        fun mexicanDocumentService(pathAnverse: String, pathReverse: String)
    }
}