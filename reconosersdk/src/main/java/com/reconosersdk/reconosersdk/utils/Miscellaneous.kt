package com.reconosersdk.reconosersdk.utils

import android.app.Activity
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Camera
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream


object Miscellaneous {

    fun getCameraResolution(): String {
        return try {
            val camera: Camera = Camera.open(0)
            val param: Camera.Parameters = camera.parameters
            Timber.e("Camera DENSITY: %s", param.previewSize.width.toString() + "X" + param.previewSize.height.toString())
            param.previewSize.width.toString() + "X" + param.previewSize.height.toString()
        } catch (e: Exception) {
            Timber.e("No fue posible conocer los valores de la resolución de la camara %s", e.toString())
            "No fue posible conocer los valores de la resolución de la camara"
        }
    }

    fun getScreenResolution(): String {
        return try {
            val displayMetrics: DisplayMetrics = Resources.getSystem().displayMetrics
            val width = displayMetrics.widthPixels
            val height = displayMetrics.heightPixels
            Timber.e("Screen DENSITY: %s",width.toString() + "X" + height.toString())
            width.toString() + "X" + height.toString()
        } catch (e: Exception) {
            Timber.e("No fue posible conocer los valores de la resolución de la pantalla %s", e.toString())
            "No fue posible conocer los valores de la resolución de la pantalla"
        }
    }

    @JvmStatic   // this annotation is required for caller class written in Java to recognize this method as static
    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @JvmStatic
    //Founded in https://stackoverflow.com/questions/16440863/can-i-get-image-file-width-and-height-from-uri-in-android
    fun getIMGSize(path: String): BitmapFactory.Options {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(File(path).absolutePath, options)
        val imageHeight = options.outHeight
        val imageWidth = options.outWidth
        Timber.tag("image height: ").e(imageHeight.toString())
        Timber.tag("image Width: ").e(imageWidth.toString())
        return options
    }

    //Founded in https://stackoverflow.com/questions/11688982/pick-image-from-sd-card-resize-the-image-and-save-it-back-to-sd-card
    @JvmStatic
    fun getRescaledImage(newHeight: Int, newWidth: Int, quality: Int, path: String?): File? {
        try {
            val dir = File(path!!)
            var b = BitmapFactory.decodeFile(dir.absolutePath)
            //Founded in https://stackoverflow.com/questions/8442316/bitmap-is-returning-null-from-bitmapfactory-decodefilefilename
            if (b == null) {
                val options = BitmapFactory.Options()
                options.inSampleSize = 2
                b = BitmapFactory.decodeFile(dir.absolutePath, options)
            }
            val out = Bitmap.createScaledBitmap(b, newWidth, newHeight, false)
            var myQuality = quality
            if (myQuality <= 0 || myQuality > Constants.MAX_QUALITY) {
                myQuality = Constants.MAX_QUALITY
            }
            val file = File(dir.path)
            return try {
                val fOut = FileOutputStream(file)
                out.compress(Bitmap.CompressFormat.JPEG, myQuality, fOut)
                fOut.flush()
                fOut.close()
                b.recycle()
                out.recycle()
                file
            } catch (e: java.lang.Exception) {
                Timber.e("Can't rescale image: %s", e.message)
                File(path)
            }
        } catch (e: java.lang.Exception) {
            Timber.e("Can't rescale image: %s", e.message)
            return File(path!!)
        }
    }

    @JvmStatic
    fun getTypeFile(path: String): String {
        return if (path.isNullOrEmpty()) {
            "path is null or empty"
        } else {
            val file = File(path)
            file.extension
        }
    }



}