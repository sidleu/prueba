package com.reconosersdk.reconosersdk.recognition;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.util.Log;

import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceContour;
import com.google.mlkit.vision.face.FaceLandmark;
import com.reconosersdk.reconosersdk.ui.bioFacial.views.LivePreviewActivity;

public class RecognitionUtils {

    private static double calculatePointDimension(PointF point1, PointF point2) {
        float X1 = point1.x;
        float Y1 = point1.y;
        float X2 = point2.x;
        float Y2 = point2.y;

        double result = Math.sqrt(Math.pow((X2 - X1), 2) + Math.pow((Y2 - Y1), 2));
        Log.e("Point1, Point2, Result", point1 + ", " + point2 + ", " + result);
        return result;
    }

    public static boolean validateOpenMouth(Face face) {

        FaceContour faceUpperLipTop = face.getContour(FaceContour.UPPER_LIP_TOP);
        FaceContour faceUpperLipBottom = face.getContour(FaceContour.UPPER_LIP_BOTTOM);
        FaceContour faceLowerLipTop = face.getContour(FaceContour.LOWER_LIP_TOP);
        FaceContour faceLowerLipBottom = face.getContour(FaceContour.LOWER_LIP_BOTTOM);

        if (faceUpperLipTop == null || faceUpperLipBottom == null || faceLowerLipTop == null || faceLowerLipBottom == null) {
            return false;
        }

        if (faceUpperLipTop.getPoints().size() == 0 || faceUpperLipBottom.getPoints().size() == 0 || faceLowerLipTop.getPoints().size() == 0 || faceLowerLipBottom.getPoints().size() == 0) {
            return false;
        }

        PointF point1UpperLipTop = faceUpperLipTop.getPoints().get(4);
        PointF point2UpperLipTop = faceUpperLipTop.getPoints().get(5);
        PointF point3UpperLipTop = faceUpperLipTop.getPoints().get(6);

        PointF point1UpperLipBottom = faceUpperLipBottom.getPoints().get(3);
        PointF point2UpperLipBottom = faceUpperLipBottom.getPoints().get(4);
        PointF point3UpperLipBottom = faceUpperLipBottom.getPoints().get(5);

        PointF point1LowerLipTop = faceLowerLipTop.getPoints().get(5);
        PointF point2LowerLipTop = faceLowerLipTop.getPoints().get(4);
        PointF point3LowerLipTop = faceLowerLipTop.getPoints().get(3);

        PointF point1LowerLipBottom = faceLowerLipBottom.getPoints().get(5);
        PointF point2LowerLipBottom = faceLowerLipBottom.getPoints().get(4);
        PointF point3LowerLipBottom = faceLowerLipBottom.getPoints().get(3);

        double top1LipHeight = calculatePointDimension(point1UpperLipTop, point1UpperLipBottom);
        double top2LipHeight = calculatePointDimension(point2UpperLipTop, point2UpperLipBottom);
        double top3LipHeight = calculatePointDimension(point3UpperLipTop, point3UpperLipBottom);

        double bottom1LipHeight = calculatePointDimension(point1LowerLipTop, point1LowerLipBottom);
        double bottom2LipHeight = calculatePointDimension(point2LowerLipTop, point2LowerLipBottom);
        double bottom3LipHeight = calculatePointDimension(point3LowerLipTop, point3LowerLipBottom);

        double mouthHeight1 = calculatePointDimension(point1UpperLipTop, point1LowerLipBottom);
        double mouthHeight2 = calculatePointDimension(point2UpperLipTop, point2LowerLipBottom);
        double mouthHeight3 = calculatePointDimension(point3UpperLipTop, point3LowerLipBottom);

        double topLipHeight = (top1LipHeight + top2LipHeight + top3LipHeight) / 3;
        double bottomLipHeight = (bottom1LipHeight + bottom2LipHeight + bottom3LipHeight) / 3;
        double mouthHeight = ((mouthHeight1 + mouthHeight2 + mouthHeight3) / 3) - 30;
        double value = topLipHeight + bottomLipHeight;

        return mouthHeight > value;
    }

    @SuppressLint("LongLogTag")
    public static boolean outOfMoldWithLandmark(Face mFace, int resultImageWidth, int resultImageHeight, String trackingEvaluate) {

        FaceLandmark leftEye = mFace.getLandmark(FaceLandmark.LEFT_EYE);
        FaceLandmark rightEye = mFace.getLandmark(FaceLandmark.RIGHT_EYE);
        FaceLandmark mouth = mFace.getLandmark(FaceLandmark.MOUTH_BOTTOM);
        Boolean outOfMold, leftEyeInMold, rightEyeInMold, mouthInMold;

        int tempDistanceWidth = resultImageWidth / 4;
        int tempDistanceHeight = resultImageHeight / 4;

        if (leftEye != null) {
            PointF pos = leftEye.getPosition();
            // Se valida la posición en X del ojo izquierdo
            Boolean leftEyeInMoldX = ((pos.x > tempDistanceWidth) && (pos.x < (resultImageWidth - tempDistanceWidth)));
            leftEyeInMold = leftEyeInMoldX;
        } else {
            leftEyeInMold = false;
        }

        if (rightEye != null) {
            PointF pos = rightEye.getPosition();
            // Se valida la posición en X del ojo derecho
            Boolean rightEyeInMoldX = ((pos.x > tempDistanceWidth) && (pos.x < (resultImageWidth - tempDistanceWidth)));
            rightEyeInMold = rightEyeInMoldX;
        } else {
            rightEyeInMold = false;
        }

        if (mouth != null) {
            // Se valida la posición en Y de la boca
            PointF pos = mouth.getPosition();
            Log.w("outOfMoldWithLandmark", String.valueOf(mouth.getPosition()));
            mouthInMold = ((pos.y > tempDistanceHeight) && (pos.y < (resultImageHeight - (tempDistanceHeight - 50))));
        } else {
            mouthInMold = false;
        }

        if (trackingEvaluate.equals(LivePreviewActivity.FACE_MOVE_LEFT)) {
            outOfMold = !rightEyeInMold || !mouthInMold;
        } else if (trackingEvaluate.equals(LivePreviewActivity.FACE_MOVE_RIGHT)) {
            outOfMold = !leftEyeInMold || !mouthInMold;
        } else {
            outOfMold = !leftEyeInMold || !rightEyeInMold || !mouthInMold;
        }

        Log.w("outOfMoldWithLandmark", "leftEyeInMold-" + leftEyeInMold + "-rightEyeInMold-" + rightEyeInMold + "-mouthInMold-" + mouthInMold + "-resultImageHeight-" + resultImageHeight);
        return outOfMold;
    }

    public static boolean outOfMoldWithContour(Face mFace, int resultImageWidth, int resultImageHeight, String trackingEvaluate) {

        FaceContour leftEye = mFace.getContour(FaceContour.LEFT_EYE);
        FaceContour rightEye = mFace.getContour(FaceContour.RIGHT_EYE);
        FaceContour mouth = mFace.getContour(FaceContour.UPPER_LIP_BOTTOM);

        Boolean outOfMold, leftEyeInMold, rightEyeInMold, mouthInMold;

        int tempDistanceWidth = resultImageWidth / 4;
        int tempDistanceHeight = resultImageHeight / 4;

        if (leftEye != null) {
            PointF leftEyePoint = leftEye.getPoints().get(4);
            Boolean leftEyeInMoldX = ((leftEyePoint.x > tempDistanceWidth) && (leftEyePoint.x < (resultImageWidth - tempDistanceWidth)));
            leftEyeInMold = leftEyeInMoldX;
        } else {
            leftEyeInMold = false;
        }

        if (rightEye != null) {
            PointF rightEyePoint = rightEye.getPoints().get(4);
            Boolean rightEyeInMoldX = ((rightEyePoint.x > tempDistanceWidth) && (rightEyePoint.x < (resultImageWidth - tempDistanceWidth)));
            rightEyeInMold = rightEyeInMoldX;
        } else {
            rightEyeInMold = false;
        }

        if (mouth != null) {
            PointF mouthPoint = mouth.getPoints().get(4);
            mouthInMold = ((mouthPoint.y > tempDistanceHeight) && (mouthPoint.y < (resultImageHeight - (tempDistanceHeight - 50))));
        } else {
            mouthInMold = false;
        }

        if (trackingEvaluate.equals(LivePreviewActivity.FACE_MOVE_LEFT)) {
            outOfMold = !rightEyeInMold || !mouthInMold;
        } else if (trackingEvaluate.equals(LivePreviewActivity.FACE_MOVE_RIGHT)) {
            outOfMold = !leftEyeInMold || !mouthInMold;
        } else {
            outOfMold = !leftEyeInMold || !rightEyeInMold || !mouthInMold;
        }

        return outOfMold;
    }
}
