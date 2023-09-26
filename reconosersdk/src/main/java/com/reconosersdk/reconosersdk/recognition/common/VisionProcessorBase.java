package com.reconosersdk.reconosersdk.recognition.common;

import android.graphics.Bitmap;
import android.media.Image;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Abstract base class for ML Kit frame processors. Subclasses need to implement {@link
 * #onSuccess(T, FrameMetadata, GraphicOverlay)} to define what they want to with the detection
 * results and {@link #detectInImage(InputImage)} to specify the detector object.
 *
 * @param <T> The type of the detected feature.
 */
public abstract class VisionProcessorBase<T> implements VisionImageProcessor {

    // Whether we should ignore process(). This is usually caused by feeding input data faster than
    // the model can handle.
    private final AtomicBoolean shouldThrottle = new AtomicBoolean(false);

    public VisionProcessorBase() {
    }

    @Override
    public void process(
            ByteBuffer data, final FrameMetadata frameMetadata, final GraphicOverlay
            graphicOverlay) {
        if (shouldThrottle.get()) {
            return;
        }
        InputImage metadata =
                InputImage.fromByteBuffer(
                        data,
                        frameMetadata.getWidth(),
                        frameMetadata.getHeight(),
                        90,
                        InputImage.IMAGE_FORMAT_NV21
                );

        detectInVisionImage(
                metadata, frameMetadata, graphicOverlay);
    }

    // Bitmap version
    @Override
    public void process(Bitmap bitmap, final GraphicOverlay
            graphicOverlay) {
        if (shouldThrottle.get()) {
            return;
        }
        detectInVisionImage(InputImage.fromBitmap(bitmap, 90), null, graphicOverlay);
    }

    /**
     * Detects feature from given media.Image
     *
     * @return created FirebaseVisionImage
     */
    @Override
    public void process(Image image, int rotation, final GraphicOverlay graphicOverlay) {
        if (shouldThrottle.get()) {
            return;
        }
        // This is for overlay display's usage
        FrameMetadata frameMetadata =
                new FrameMetadata.Builder().setWidth(image.getWidth()).setHeight(image.getHeight
                        ()).build();
        InputImage fbVisionImage =
                InputImage.fromMediaImage(image, 90);
        detectInVisionImage(fbVisionImage, frameMetadata, graphicOverlay);
    }

    private void detectInVisionImage(
            InputImage image,
            final FrameMetadata metadata,
            final GraphicOverlay graphicOverlay) {
        detectInImage(image, metadata)
                .addOnSuccessListener(
                        results -> {
                            shouldThrottle.set(false);
                            VisionProcessorBase.this.onSuccess(results, metadata,
                                    graphicOverlay);
                        })
                .addOnFailureListener(
                        e -> {
                            shouldThrottle.set(false);
                            VisionProcessorBase.this.onFailure(e);
                        });
        // Begin throttling until this frame of input has been processed, either in onSuccess or
        // onFailure.
        shouldThrottle.set(true);
    }

    @Override
    public void stop() {
    }

    protected abstract Task<T> detectInImage(InputImage image, FrameMetadata metadata);

    protected abstract void onSuccess(
            @NonNull T results,
            @NonNull FrameMetadata frameMetadata,
            @NonNull GraphicOverlay graphicOverlay);

    protected abstract void onFailure(@NonNull Exception e);
}

