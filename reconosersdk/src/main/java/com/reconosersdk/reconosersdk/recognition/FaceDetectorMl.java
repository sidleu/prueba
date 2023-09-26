package com.reconosersdk.reconosersdk.recognition;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.reconosersdk.reconosersdk.libs.EventBus;
import com.reconosersdk.reconosersdk.libs.GreenRobotEventBus;
import com.reconosersdk.reconosersdk.recognition.common.FrameMetadata;
import com.reconosersdk.reconosersdk.recognition.common.GraphicOverlay;
import com.reconosersdk.reconosersdk.ui.bioFacial.views.LivePreviewActivity;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import timber.log.Timber;

import static com.reconosersdk.reconosersdk.ui.bioFacial.views.LivePreviewActivity.FACE_MODE_ALL_CONTOURS;
import static com.reconosersdk.reconosersdk.ui.bioFacial.views.LivePreviewActivity.FACE_OPEN_MOUTH;

public class FaceDetectorMl {

    private static final String TAG = "FaceDetectionProcessor";
    private FaceDetector detector;
    private String lastTrackingEvaluate = "";
    private String trackingEvaluate;
    final float EYE_CLOSED_THRESHOLD = 0.3f;
    final float SMILING_THRESHOLD = 0.6f;
    private boolean isFace = true;
    private boolean faceMax = false;
    private int resultImageWidth, resultImageHeight;
    private Bitmap takeImage;
    private int rotate;
    private final AtomicBoolean shouldThrottle = new AtomicBoolean(false);

    public FaceDetectorMl(int mode) {

        if (mode == FACE_MODE_ALL_CONTOURS) {
            getFaceDetectionProcessorAllContours();
        } else {
            getFaceDetectionProcessorAllClassifications();
        }
    }

    public void setFaceMode(int mode) {
        if (mode == FACE_MODE_ALL_CONTOURS) {
            getFaceDetectionProcessorAllContours();
        } else {
            getFaceDetectionProcessorAllClassifications();
        }
    }

    private void getFaceDetectionProcessorAllClassifications() {
        FaceDetectorOptions highAccuracyOpts =
                new FaceDetectorOptions.Builder()
                        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                        .enableTracking()
                        .build();
        detector = FaceDetection.getClient(highAccuracyOpts);
    }

    private void getFaceDetectionProcessorAllContours() {

        FaceDetectorOptions highAccuracyOpts =
                new FaceDetectorOptions.Builder()
                        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                        .build();
        detector = FaceDetection.getClient(highAccuracyOpts);
    }

    public void stop() {
        Timber.w("Stop lastTrackingEvaluate %1s trackingEvaluate %2s", lastTrackingEvaluate, trackingEvaluate);
    }

    public void onDestroy() {
        detector.close();
    }

    public void process(
            ByteBuffer data, final FrameMetadata frameMetadata, final GraphicOverlay
            graphicOverlay) {
        if (shouldThrottle.get()) {
            return;
        }
        InputImage image = InputImage.fromByteBuffer(data,
                frameMetadata.getWidth(),
                frameMetadata.getHeight(),
                frameMetadata.getAngle(),
                InputImage.IMAGE_FORMAT_NV21 // or IMAGE_FORMAT_YV12
        );
        rotate = frameMetadata.getAngle();
        takeImage = getBitmap(data, frameMetadata);
        detectInVisionImage(
                image, frameMetadata, graphicOverlay);
    }

    private void detectInVisionImage(InputImage metadata, FrameMetadata frameMetadata, GraphicOverlay graphicOverlay) {
        detectInImage(metadata).addOnSuccessListener(
                listFaces -> {
                    onSuccessFaces(listFaces);
                })
                .addOnFailureListener(
                        e -> {
                            Log.e(TAG, "Face detection failed " + e);
                        }
                );
    }

    protected Task<List<Face>> detectInImage(InputImage image) {
        return detector.process(image);
    }

    void onSuccessFaces(@NonNull List<Face> faces) {

        if (faces.size() == 1) {
            if (faceMax) {
                faceMax = false;
                postEvent(PhotoTakenEvent.INIT_BAG);
            } else {
                isFace = true;
                Face face = faces.get(0);
                evaluateTracking(face);
            }
        } else if (faces.size() == 0 && isFace) {
            isFace = false;
            postEvent(PhotoTakenEvent.USER_TRACKING_LOST);
        } else if (faces.size() > 1) {
            faceMax = true;
            postEvent(PhotoTakenEvent.MAX_FACES_TRAKING);
        }
    }

    public void expressionForEvaluate(String moveFace) {
        trackingEvaluate = moveFace;
    }

    public void clearExpressionForEvaluate(String faceClear) {
        lastTrackingEvaluate = faceClear;
    }

    private void evaluateTracking(Face mFace) {

      /*  if (!trackingEvaluate.equals(LivePreviewActivity.FACE_CENTER) && !trackingEvaluate.equals(LivePreviewActivity.FACE_BLINK)) {
            Boolean isOutOfMold;
            if (trackingEvaluate.equals(FACE_OPEN_MOUTH)) {
                isOutOfMold = RecognitionUtils.outOfMoldWithContour(mFace, resultImageWidth, resultImageHeight, trackingEvaluate);
            } else {
                isOutOfMold = RecognitionUtils.outOfMoldWithLandmark(mFace, resultImageWidth, resultImageHeight, trackingEvaluate);
            }

            if (isOutOfMold) {
                postEvent(PhotoTakenEvent.USER_TRACKING_FACE_OUT_MOLD);
                return;
            }
        }*/

        if (!lastTrackingEvaluate.equals(trackingEvaluate)) {

            postEvent(PhotoTakenEvent.CHANGE_TEXT);

            switch (trackingEvaluate) {
                case LivePreviewActivity.END_VALIDATION:
                case LivePreviewActivity.FACE_CENTER:
                    isCenter(mFace);
                    break;
                case LivePreviewActivity.FACE_MOVE_LEFT:
                    isLeft(mFace);
                    break;
                case LivePreviewActivity.FACE_MOVE_RIGHT:
                    isRight(mFace);
                    break;
                case LivePreviewActivity.FACE_SMILING:
                    isSimiling(mFace);
                    break;
                case LivePreviewActivity.FACE_BLINK:
                    isBlink(mFace);
                    break;
                case FACE_OPEN_MOUTH:
                    isOpeningHisMouth(mFace);
                    break;
            }
        }
    }

    boolean isLeftEyeOpen(float leftOpenScore) {
        return (leftOpenScore > EYE_CLOSED_THRESHOLD);
    }

    boolean isRightEyeOpen(float rightOpenScore) {
        return (rightOpenScore > EYE_CLOSED_THRESHOLD);
    }

    private void isCenter(Face mFace) {
        Timber.w("Stop mFaceX %1s mFaceY %2s eyeLeft %3s eyeRight %4s" ,mFace.getHeadEulerAngleX(), mFace.getHeadEulerAngleY(), mFace.getLeftEyeOpenProbability(), mFace.getRightEyeOpenProbability());
        if (mFace.getLeftEyeOpenProbability() == null || mFace.getRightEyeOpenProbability() == null) return;
        if (mFace.getHeadEulerAngleY() >= -8 && mFace.getHeadEulerAngleY() <= 8 && isLeftEyeOpen(mFace.getLeftEyeOpenProbability()) && isRightEyeOpen(mFace.getRightEyeOpenProbability())) {
            postEvent(PhotoTakenEvent.USER_TRACKING_SUCCESS);
            lastTrackingEvaluate = trackingEvaluate;
        }
    }

    private void isLeft(Face mFace) {
        if (mFace.getHeadEulerAngleY() > 23) {
            postEvent(PhotoTakenEvent.USER_TRACKING_SUCCESS);
            lastTrackingEvaluate = trackingEvaluate;
        }
    }

    private void isRight(Face mFace) {
        if (mFace.getHeadEulerAngleY() < -23) {
            postEvent(PhotoTakenEvent.USER_TRACKING_SUCCESS);
            lastTrackingEvaluate = trackingEvaluate;
        }
    }

    private void isSimiling(Face mFace) {
        if (mFace.getHeadEulerAngleY() >= -8 && mFace.getHeadEulerAngleY() <= 8 && isLeftEyeOpen(mFace.getLeftEyeOpenProbability()) && isRightEyeOpen(mFace.getRightEyeOpenProbability()) && mFace.getSmilingProbability() > SMILING_THRESHOLD) {
            postEvent(PhotoTakenEvent.USER_TRACKING_SUCCESS);
            lastTrackingEvaluate = trackingEvaluate;
        }
    }

    private void isBlink(Face mFace) {
        if (mFace.getHeadEulerAngleY() >= -8 && mFace.getHeadEulerAngleY() <= 8) {
            if (!isLeftEyeOpen(mFace.getLeftEyeOpenProbability()) && !isRightEyeOpen(mFace.getRightEyeOpenProbability())) {
                postEvent(PhotoTakenEvent.USER_TRACKING_SUCCESS);
                lastTrackingEvaluate = trackingEvaluate;
            }
        }
    }

    private void isOpeningHisMouth(Face mFace) {
        if (mFace.getHeadEulerAngleY() >= -8 && mFace.getHeadEulerAngleY() <= 8 && RecognitionUtils.validateOpenMouth(mFace)) {
            postEvent(PhotoTakenEvent.USER_TRACKING_SUCCESS);
            lastTrackingEvaluate = trackingEvaluate;
        }
    }

    private void postEvent(int type) {
        PhotoTakenEvent photoTakenEvent = new PhotoTakenEvent();
        photoTakenEvent.setType(type);

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(photoTakenEvent);
    }

    @Nullable
    public static Bitmap getBitmap(ByteBuffer data, FrameMetadata metadata) {
        data.rewind();
        byte[] imageInBuffer = new byte[data.limit()];
        data.get(imageInBuffer, 0, imageInBuffer.length);
        try {
            YuvImage image =
                    new YuvImage(
                            imageInBuffer, ImageFormat.NV21, metadata.getWidth(), metadata.getHeight(), null);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compressToJpeg(new Rect(0, 0, metadata.getWidth(), metadata.getHeight()), 80, stream);

            Bitmap bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());

            stream.close();
            return rotateBitmap(bmp, 90, false, false);
        } catch (Exception e) {
            Timber.e(e.getMessage(),"VisionProcessorBase");
        }
        return null;
    }

    private static Bitmap rotateBitmap(
            Bitmap bitmap, int rotationDegrees, boolean flipX, boolean flipY) {
        Matrix matrix = new Matrix();

        // Rotate the image back to straight.
        matrix.postRotate(rotationDegrees);

        // Mirror the image along the X or Y axis.
        matrix.postScale(flipX ? -1.0f : 1.0f, flipY ? -1.0f : 1.0f);
        Bitmap rotatedBitmap =
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        // Recycle the old bitmap if it has changed.
        if (rotatedBitmap != bitmap) {
            bitmap.recycle();
        }
        return rotatedBitmap;
    }
}
