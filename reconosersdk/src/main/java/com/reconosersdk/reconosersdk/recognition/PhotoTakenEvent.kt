package com.reconosersdk.reconosersdk.recognition

import android.graphics.Bitmap

class PhotoTakenEvent {

    companion object {
        const val USER_TRACKING_SUCCESS = 1
        const val USER_TRACKING_LOST = 2
        const val MAX_FACES_TRAKING = 3
        const val INIT_BAG = 4
        const val TEXT_DETECT = 5
        const val NOT_TEXT_DETECT = 6
        const val PHOTO_TAKE = 7
        const val USER_TRACKING_FACE_OUT_MOLD = 8
        const val CHANGE_TEXT = 9
    }

    private var type: Int = 0
    private var textScan: String? = ""
    private var textBarcode: String? = ""
    private var typeBarcode: String? = ""
    private var bitmapImage: Bitmap? = null
    private var typeDocument: Int = 0

    fun getType(): Int {
        return type
    }

    fun getTypeDocument(): Int {
        return typeDocument
    }

    fun setTypeDocument(typeDocument: Int) {
        this.typeDocument = typeDocument
    }

    fun setType(type: Int) {
        this.type = type
    }

    fun getTextScan(): String {
        return textScan!!
    }

    fun setTextScan(textScan: String) {
        this.textScan = textScan
    }

    fun getTextBarcode(): String {
        return textBarcode!!
    }

    fun setTextBarcode(textBarcode: String) {
        this.textBarcode = textBarcode
    }

    fun getTypeBarcode(): String {
        return typeBarcode!!
    }

    fun setTypeBarcode(typeBarcode: String) {
        this.typeBarcode = typeBarcode
    }

    fun setBitmap(bitmap: Bitmap) {
        this.bitmapImage = bitmap
    }

    fun getBitmap(): Bitmap {
        return bitmapImage!!
    }


}