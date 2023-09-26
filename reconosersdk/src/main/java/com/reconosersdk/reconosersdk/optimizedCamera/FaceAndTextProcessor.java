package com.reconosersdk.reconosersdk.optimizedCamera;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.reconosersdk.reconosersdk.libs.EventBus;
import com.reconosersdk.reconosersdk.libs.GreenRobotEventBus;
import com.reconosersdk.reconosersdk.recognition.PhotoTakenEvent;
import com.reconosersdk.reconosersdk.utils.Constants;
import com.reconosersdk.reconosersdk.utils.ForeignStringUtils;
import com.reconosersdk.reconosersdk.utils.StringUtils;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import timber.log.Timber;

import static com.reconosersdk.reconosersdk.utils.StringUtils.TOLERANCE_WORD;

public class FaceAndTextProcessor {

    private static final String TAG = "FaceAndTextProcessor";
    private static final int MAX_FACES = 2;
    private FaceDetector faceDetector;
    private BarcodeScanner scanner;
    private TextRecognizer recognizer;
    private String resultsText = "";
    private final AtomicBoolean shouldThrottle = new AtomicBoolean(false);
    private int screen = 0;
    private boolean detectando = false;
    private long seconds = Constants.FOUNDED_TEXT_TIME_OUT;
    private CountDownTimer countDownTimer;
    private InputImage cropMlImage;
    private Bitmap takeImage;
    private int rotate;
    private int documentType = 0;

    private static final String CC = "/CC";
    private static final String TI = "/TI";
    private static final String CE = "/CE";

    /**
     * Cedula colombiana anverso = 1
     * Cedula colombiana reverso = 2
     * Tarjeta de identidad anverso = 3
     * Tarjeta de identidad reverso = 4
     * Documento ecuatoriano anverso = 5
     * Documento ecuatoriano reverso = 6
     * Cedula de extranjeria anverso = 9
     * Cedula de extranjeria reverso = 10
     * Cedula digital colombiana anverso = 11
     * Cedula digital colombiana anverso = 12
     * <<<<<<< HEAD
     * =======
     * Documento mexicano anverso = 7
     * Documento mexicano reverso = 8
     * >>>>>>> staging
     * agregar el consecutivo del documento que se vaya a agregar
     */


    public FaceAndTextProcessor() {
        FaceDetectorOptions highAccuracyOpts =
                new FaceDetectorOptions.Builder()
                        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                        .build();


        BarcodeScannerOptions optionsBarcode =
                new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(
                                Barcode.FORMAT_CODE_128,
                                Barcode.FORMAT_PDF417)
                        .build();

        faceDetector = FaceDetection.getClient(highAccuracyOpts);
        scanner = BarcodeScanning.getClient(optionsBarcode);
        recognizer = TextRecognition.getClient();

        timeCounter();
    }

    private void timeCounter() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(Constants.FOUNDED_TEXT_TIME_OUT, Constants.ONE_SECOND) {

            public void onTick(long millisUntilFinished) {
                seconds = millisUntilFinished / Constants.ONE_SECOND;
                Timber.d("seconds remaining: %s", seconds);
            }

            public void onFinish() {
                Timber.d("done: OK");
            }

        }.start();
    }

    public void stop() {
        faceDetector.close();
        recognizer.close();
        scanner.close();
    }

    public void process(ByteBuffer data, FrameMetadata frameMetadata, GraphicOverlay graphicOverlay) throws MlKitException {

        if (shouldThrottle.get()) {
            return;
        }
        if (detectando) {
            return;
        }
        detectando = true;
        InputImage image = InputImage.fromByteBuffer(data,
                frameMetadata.getWidth(),
                frameMetadata.getHeight(),
                frameMetadata.getAngle(),
                InputImage.IMAGE_FORMAT_NV21 // or IMAGE_FORMAT_YV12
        );
        rotate = frameMetadata.getAngle(); //TODO mirar como hacer para dinamico
        takeImage = getBitmap(data, frameMetadata);

        Log.w(TAG, "process " + screen);
        //Time up
        //Obverse and Reverse
        Timber.d("done seconds remaining: %s", seconds);
        if (seconds <= 0) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            Timber.d("done seconds: %s take photo %s", seconds, resultsText);
            postEvent(PhotoTakenEvent.PHOTO_TAKE, resultsText, "", "", takeImage, documentType);
        } else {
            Bitmap bitmap = takeImage;
            Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 200, takeImage.getWidth(), takeImage.getHeight() - 400);
            InputImage cropImage = InputImage.fromBitmap(newBitmap, 0);

            if (screen == 0) {
                detectInVisionImage(image, frameMetadata, graphicOverlay);
            } else if (screen == 1) {
                detectInText(image, frameMetadata, graphicOverlay, 0);
            } else {
                detectInBarcode(cropImage, frameMetadata, graphicOverlay, documentType);
            }
        }
    }

    private void detectInBarcode(InputImage image, FrameMetadata frameMetadata, GraphicOverlay graphicOverlay, int documentType) {

        cropMlImage = image;
        if (documentType != Constants.MEXICAN_REVERSE_DOCUMENT && documentType != Constants.FOREIGNER_REVERSE_DOCUMENT) {
            Bitmap bitmap = takeImage;
            Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, (takeImage.getHeight() / 2), takeImage.getWidth(), (takeImage.getHeight() / 2));
            cropMlImage = InputImage.fromBitmap(bitmap, rotate);
        }

        Task<List<Barcode>> result = scanner.process(cropMlImage)
                .addOnSuccessListener(barcodes -> {
                    Timber.w("Barcode %1s", barcodes.size());
                    if (!barcodes.isEmpty()) {
                        for (int i = 0; i < barcodes.size(); ++i) {
                            Barcode barcode = barcodes.get(i);
                            String valueBarcodeType = String.valueOf(barcode.getFormat());
                            Log.w(TAG, "typeBarcode: " + valueBarcodeType);

                            String typeBarcodeAux = valueBarcodeType;
                            if(typeBarcodeAux.equals(String.valueOf(Barcode.FORMAT_PDF417))){
                                if(documentType == Constants.COLOMBIAN_REVERSE_DOCUMENT){
                                    typeBarcodeAux = Constants.PDF_417+CC;
                                }else if(documentType == Constants.COLOMBIAN_TI_REVERSE_DOCUMENT){
                                    typeBarcodeAux = Constants.PDF_417+TI;
                                }else if(documentType == Constants.FOREIGNER_REVERSE_DOCUMENT){
                                    typeBarcodeAux = Constants.PDF_417+CE;
                                }
                            }
                            if (barcode.getRawValue() != null && barcode.getFormat() == Barcode.FORMAT_PDF417) {
                                postEvent(PhotoTakenEvent.PHOTO_TAKE, resultsText, barcode.getRawValue(), typeBarcodeAux, takeImage, documentType);
                            } else {
                                detectando = false;
                            }
                        }
                    } else {
                        if (seconds <= 0) {
                            if (countDownTimer != null) {
                                countDownTimer.cancel();
                            }
                            postEvent(PhotoTakenEvent.PHOTO_TAKE, resultsText, "", "", takeImage, documentType);
                        }
                        detectando = false;
                    }
                })
                .addOnFailureListener(e -> detectando = false);
    }

    public Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    protected Task<List<Face>> detectInImage(InputImage image) {
        return faceDetector.process(image);
    }

    private void detectInVisionImage(InputImage image, final FrameMetadata frameMetadata, final GraphicOverlay graphicOverlay) {

        detectInImage(image).addOnSuccessListener(
                firebaseVisionFaces -> {
                    detectInText(image, frameMetadata, graphicOverlay, firebaseVisionFaces.size());
                })
                .addOnFailureListener(
                        e -> detectando = false
                );
    }

    protected Task<Text> detectText(InputImage image) {
        return recognizer.process(image);
    }

    private void detectInText(InputImage image, FrameMetadata frameMetadata, GraphicOverlay graphicOverlay, int faces) {

        detectText(image).addOnSuccessListener(
                firebaseVisionText -> {
                    graphicOverlay.clear();
                    validateResults(firebaseVisionText, image, frameMetadata, graphicOverlay, faces);
                })
                .addOnFailureListener(
                        e -> detectando = false
                );
    }

    private void validateResults(Text results, InputImage image, FrameMetadata frameMetadata, GraphicOverlay graphicOverlay, int faces) {
        switch (screen) {
            case 1:
                validateReverse(results, image, frameMetadata, graphicOverlay);
                break;
            default:
                validateObverse(results, image, frameMetadata, graphicOverlay, faces);
                break;
        }
    }

    public void documentScreen(int screen) {
        this.screen = screen;
    }

    protected void onFailure(@NonNull Exception e) {
        Log.w(TAG, "Text detection failed." + e);
    }

    @SuppressLint("LongLogTag")
    private void validateObverse(Text results, InputImage
            image, FrameMetadata frameMetadata, GraphicOverlay graphicOverlay, int faces) {
        Log.d(TAG, "validateAnverse ");
        List<Text.TextBlock> blocks = results.getTextBlocks();
        String resultText = results.getText();
        documentType = StringUtils.documentData(resultText, 1);
        //If the obverse document is COLOMBIAN or ECUADORIAN
        Timber.w("documentResult %1s", String.valueOf(documentType));
        if (faces > MAX_FACES && !(documentType == Constants.FOREIGNER_OBVERSE_DOCUMENT || documentType == Constants.MEXICAN_OBVERSE_DOCUMENT || documentType == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT)) {
            Log.w("IN FACE DocumentResult", String.valueOf(documentType));
            postEvent(PhotoTakenEvent.MAX_FACES_TRAKING, "", "", "0", takeImage, documentType);
            detectando = false;
            return;
        }

        //If the obverse document is COLOMBIAN or ECUADORIAN
        if (anyObverseDocument(documentType)) {

            for (int i = 0; i < blocks.size(); i++) {
                List<Text.Line> lines = blocks.get(i).getLines();
                boolean isValid = false;
                for (int j = 0; j < lines.size(); j++) {

                    int mexicanValue = (100 - (StringUtils.computeLevenshteinDistance(lines.get(j).getText(), Constants.MEXICAN_TITLE)) * 10);
                    int mexicanNationalValue = (100 - (StringUtils.computeLevenshteinDistance(lines.get(j).getText(), Constants.MEXICAN_NAT_TITTLE)) * 10);
                    if (mexicanValue > 80 || mexicanNationalValue > 80) {
                        j = lines.size() + 1;
                        i = blocks.size() + 1;
                        resultsText = StringUtils.orderLabel(blocks);
                        postEvent(PhotoTakenEvent.PHOTO_TAKE, resultsText, "", "", takeImage, documentType);
                        isValid = true;
                    }

                    resultsText = results.getText();

                    if (documentType == Constants.FOREIGNER_OBVERSE_DOCUMENT && lines.get(j).getText().contains(Constants.COLOMBIAN_NATIONALITY)) {
                        resultsText = ForeignStringUtils.orderLabel(blocks);
                        isValid = true;
                        j = lines.size() + 1;
                        i = blocks.size() + 1;
                        postEvent(PhotoTakenEvent.PHOTO_TAKE, resultsText, "", "", takeImage, documentType);
                    } else if (colombianObverseDocument(documentType, lines.get(j).getText())) {
                        //if (documentType == Constants.COLOMBIAN_OBVERSE_DOCUMENT && lines.get(j).getText().contains(Constants.COLOMBIAN_NATIONALITY) || documentType == Constants.COLOMBIAN_TI_OBVERSE_DOCUMENT) {
                        isValid = true;
                        j = lines.size() + 1;
                        i = blocks.size() + 1;
                        postEvent(PhotoTakenEvent.PHOTO_TAKE, resultsText, "", "", takeImage, documentType);
                    } else if (documentType == Constants.ECUADORIAN_OBVERSE_DOCUMENT) {
                        isValid = true;
                        j = lines.size() + 1;
                        i = blocks.size() + 1;
                        screen = 2;
                        detectInBarcode(image, frameMetadata, graphicOverlay, documentType);
                    }

                    if (!isValid) {
                        detectando = false;
                    }
                }
            }
        } else {
            detectando = false;
        }
    }

    private boolean anyObverseDocument(int documentType) {
        return documentType == Constants.COLOMBIAN_OBVERSE_DOCUMENT || documentType == Constants.ECUADORIAN_OBVERSE_DOCUMENT || documentType == Constants.MEXICAN_OBVERSE_DOCUMENT || documentType == Constants.COLOMBIAN_TI_OBVERSE_DOCUMENT || documentType == Constants.FOREIGNER_OBVERSE_DOCUMENT || documentType == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT;
    }

    private boolean colombianObverseDocument(int documentType, String nationality) {
        //double idPercentage = StringUtils.diceCoefficientOptimized(nationality, Constants.COLOMBIAN_NATIONALITY);
        boolean validateNationality = StringUtils.diceCoefficientOptimized(nationality, Constants.COLOMBIAN_NATIONALITY) * 100 > TOLERANCE_WORD;
        return (validateNationality) && (documentType == Constants.COLOMBIAN_OBVERSE_DOCUMENT || documentType == Constants.COLOMBIAN_TI_OBVERSE_DOCUMENT || documentType == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT);

        //return (nationality.contains(Constants.COLOMBIAN_NATIONALITY)) && (documentType == Constants.COLOMBIAN_OBVERSE_DOCUMENT || documentType == Constants.COLOMBIAN_TI_OBVERSE_DOCUMENT || documentType == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT);
    }

    private boolean findNationality(String text) {
        return text.contains(Constants.COLOMBIAN_NATIONALITY) || text.contains(Constants.ECUADORIAN_NATIONALITY) || text.contains(Constants.FOREIGNER_CC);
    }

    private void validateReverse(Text results, InputImage
            image, FrameMetadata frameMetadata, GraphicOverlay graphicOverlay) {
        List<Text.TextBlock> blocks = results.getTextBlocks();
        String resultText = results.getText();

        documentType = StringUtils.documentData(resultText, 2);
        Timber.w("documentResult validateReverse %1s", String.valueOf(documentType));

        //If the reverse document is COLOMBIAN or ECUADORIAN
        if (anyReverseDocument(documentType)) {
            for (int i = 0; i < blocks.size(); i++) {
                List<Text.Line> lines = blocks.get(i).getLines();
                boolean isReverso = false;
                for (int j = 0; j < lines.size(); j++) {

                    if (documentType == Constants.FOREIGNER_REVERSE_DOCUMENT || findReverseNationality(lines.get(j).getText())) {
                        isReverso = true;
                        resultsText = results.getText();
                        Timber.w("documentR %s   text %s  j= %s", ((documentType == Constants.ECUADORIAN_REVERSE_DOCUMENT && lines.get(j).getText().contains(Constants.ECUADORIAN_INSTRUCTION)) || (documentType == Constants.MEXICAN_REVERSE_DOCUMENT && (lines.get(j).getText().contains(Constants.FEDERAL_ELECTION) || lines.get(j).getText().contains(Constants.FEDERAL_ELECTION_IFE)  || (documentType == Constants.COLOMBIAN_DIGITAL_REVERSE_DOCUMENT)))), lines.get(j).getText(), j);
                        if ((documentType == Constants.ECUADORIAN_REVERSE_DOCUMENT && lines.get(j).getText().contains(Constants.ECUADORIAN_INSTRUCTION)) || (documentType == Constants.MEXICAN_REVERSE_DOCUMENT && (lines.get(j).getText().contains(Constants.FEDERAL_ELECTION) || lines.get(j).getText().contains(Constants.FEDERAL_ELECTION_IFE))) || (documentType == Constants.COLOMBIAN_DIGITAL_REVERSE_DOCUMENT)) {
                            postEvent(PhotoTakenEvent.PHOTO_TAKE, resultsText, "", "", takeImage, documentType);
                        } else {
                            j = lines.size() + 1;
                            i = blocks.size() + 1;
                            // resultsText = results.getText();
                            screen = 2;
                            detectInBarcode(image, frameMetadata, graphicOverlay, documentType);
                        }
                        break;
                    }
                }
                if (!isReverso) {
                    detectando = false;
                }
            }
        } else {
            detectando = false;
        }
    }

    private boolean anyReverseDocument(int documentType) {
        return documentType == Constants.COLOMBIAN_REVERSE_DOCUMENT || documentType == Constants.COLOMBIAN_TI_REVERSE_DOCUMENT || documentType == Constants.ECUADORIAN_REVERSE_DOCUMENT || documentType == Constants.MEXICAN_REVERSE_DOCUMENT || documentType == Constants.FOREIGNER_REVERSE_DOCUMENT || documentType == Constants.COLOMBIAN_DIGITAL_REVERSE_DOCUMENT;
    }


    private boolean findReverseNationality(String text) {
        int mexicanValue = (100 - (StringUtils.computeLevenshteinDistance(text, Constants.MEXICAN_REVERSE)) * 10);
        int mexicanNational = (100 - (StringUtils.computeLevenshteinDistance(text, Constants.FEDERAL_ELECTION)) * 10);
        int mexicanNational2 = (100 - (StringUtils.computeLevenshteinDistance(text, Constants.FEDERAL_ELECTION_IFE)) * 10);
        Timber.w("ok %s    %s", mexicanNational, mexicanNational2);
        return text.contains(Constants.COLOMBIAN_BORN_DATE) || text.contains(Constants.COLOMBIAN_TI_BORN_DATE) || text.contains(Constants.ECUADORIAN_INSTRUCTION) || mexicanValue > 80 || mexicanNational > 80 || mexicanNational2 > 80 || text.contains(Constants.COLOMBIAN_DIGITAL_REGISTER);
    }

    private void postEvent(int type, @NonNull String textScan,
                           @NonNull String barcodeScan, String valueBarcodeType, Bitmap bitmap, int documentType) {
        PhotoTakenEvent photoTakenEvent = new PhotoTakenEvent();
        photoTakenEvent.setType(type);
        photoTakenEvent.setTextScan(textScan);
        photoTakenEvent.setTextBarcode(barcodeScan);
        photoTakenEvent.setTypeBarcode(valueBarcodeType);
        photoTakenEvent.setBitmap(bitmap);
        photoTakenEvent.setTypeDocument(documentType);

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(photoTakenEvent);
    }

    public void onDestroy() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
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
            image.compressToJpeg(new Rect(0, 0, metadata.getWidth(), metadata.getHeight()), 100, stream);

            Bitmap bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());

            stream.close();
            return rotateBitmap(bmp, 90, false, false);
        } catch (Exception e) {
            Log.e("VisionProcessorBase", "Error: " + e.getMessage());
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