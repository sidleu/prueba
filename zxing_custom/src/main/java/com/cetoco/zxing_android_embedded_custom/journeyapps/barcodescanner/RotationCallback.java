package com.cetoco.zxing_android_embedded_custom.journeyapps.barcodescanner;

/**
 *
 */
public interface RotationCallback {
    /**
     * Rotation changed.
     *
     * @param rotation the current value of windowManager.getDefaultDisplay().getRotation()
     */
    void onRotationChanged(int rotation);
}
