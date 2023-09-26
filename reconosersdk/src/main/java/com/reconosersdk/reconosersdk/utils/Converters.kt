package com.reconosersdk.reconosersdk.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Base64
import android.util.Log
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


object Converters {
    // This subscription needs to be disposed off to release the system resources primarily held for purpose.

    @JvmStatic   // this annotation is required for caller class written in Java to recognize this method as static
    fun convertBitmapToFile(bitmap: Bitmap, onBitmapConverted: (File) -> Unit): Disposable {
        return Single.fromCallable {
            compressBitmap(bitmap)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    Log.i("convertedPicturePath", it.path)
                    onBitmapConverted(it)
                }
            }, { it.printStackTrace() })
    }

    private fun compressBitmap(bitmap: Bitmap): File? {
        //create a file to write bitmap data

        try {
            val myStuff =
                File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "Android Custom Camera"
                )
            if (!myStuff.exists())
                myStuff.mkdirs()
            val picture = File(myStuff, "Mobin-" + System.currentTimeMillis() + ".jpeg")

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos)
            val bitmapData = bos.toByteArray()

            //write the bytes in file
            val fos = FileOutputStream(picture)
            fos.write(bitmapData)
            fos.flush()
            fos.close()
            return picture
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    @JvmStatic   // this annotation is required for caller class written in Java to recognize this method as static
    fun convertFile64(file: File): String? {
        val bm = BitmapFactory.decodeFile(file.getPath())
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos) //bm is the bitmap object
        val b = baos.toByteArray()
        val encodedImage = Base64.encodeToString(b, Base64.DEFAULT)
        return encodedImage
    }

    @JvmStatic   // this annotation is required for caller class written in Java to recognize this method as static
    fun convertString64(path: String): String? {
        val bm = BitmapFactory.decodeFile(path)
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos) //bm is the bitmap object
        val b = baos.toByteArray()
        val encodedImage = Base64.encodeToString(b, Base64.DEFAULT)
        return encodedImage
    }

    /* BASE64 */
    fun getEncodedBase64FromFilePath(filePath: String?): String? { //TODO Funciona
        val bitmap = BitmapFactory.decodeFile(filePath)
        return encodeImage(bitmap)
    }

    fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
}
