package com.cetoco.zxing_android_embedded_custom.journeyapps.barcodescanner.camera;

import com.cetoco.zxing_android_embedded_custom.journeyapps.barcodescanner.SourceData;

/**
 * Callback for camera previews.
 */
public interface PreviewCallback {
    void onPreview(SourceData sourceData);
    void onPreviewError(Exception e);
}
