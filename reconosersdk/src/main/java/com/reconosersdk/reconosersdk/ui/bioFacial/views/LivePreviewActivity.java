package com.reconosersdk.reconosersdk.ui.bioFacial.views;

import static com.reconosersdk.reconosersdk.utils.IntentExtras.PATH_FILE_PHOTO_R;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.reconosersdk.reconosersdk.R;
import com.reconosersdk.reconosersdk.databinding.ActivityLivePreviewBinding;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ValidarBiometriaIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidarBiometria;
import com.reconosersdk.reconosersdk.libs.EventBus;
import com.reconosersdk.reconosersdk.libs.GreenRobotEventBus;
import com.reconosersdk.reconosersdk.recognition.FaceDetectorMl;
import com.reconosersdk.reconosersdk.recognition.PhotoTakenEvent;
import com.reconosersdk.reconosersdk.recognition.common.CameraSource;
import com.reconosersdk.reconosersdk.ui.LibraryReconoSer;
import com.reconosersdk.reconosersdk.ui.base.BaseActivity;
import com.reconosersdk.reconosersdk.ui.bioFacial.interfaces.LivePreviewContract;
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.ServicesOlimpia;
import com.reconosersdk.reconosersdk.utils.Constants;
import com.reconosersdk.reconosersdk.utils.ImageUtils;
import com.reconosersdk.reconosersdk.utils.IntentExtras;
import com.reconosersdk.reconosersdk.utils.JsonUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import timber.log.Timber;

public class LivePreviewActivity extends BaseActivity implements LivePreviewContract.LivePreviewMvpView {

    @Inject
    LivePreviewContract.LivePreviewPresenter presenter;

    public static final int FACE_MODE_ALL_CLASSIFICATIONS = 100;
    public static final int FACE_MODE_ALL_CONTOURS = 200;

    public static final String FACE_MOVE_RIGHT = "Gira la cabeza a tu derecha";
    public static final String FACE_MOVE_LEFT = "Gira la cabeza a tu izquierda";
    public static final String FACE_CENTER = "Gira la cabeza al centro";
    public static final String FACE_SMILING = "Sonríe";
    public static final String FACE_TESTING_SMILING = "Sonreír";
    public static final String FACE_BLINK = "Pestañea suavemente";
    public static final String FACE_OPEN_MOUTH = "Abra la boca";
    public static final String END_VALIDATION = "Verificando - Mira al centro";
    public static final String SEVERAL_FACES = "Se han detectado varios rostros";
    public static final String FACE_OUT_MOLD = "Por favor, permanezca dentro del molde";
    public static final String CORRECT = "¡Correcto!";
    private static final String TAG = "LivePreviewActivity";

    private ActivityLivePreviewBinding binding;
    private int SIZE_BAG = 1;
    private List<String> listFaceTracking, bagValidation = new ArrayList<>();

    private CameraSource cameraSource = null;
    private String fileUrl = null;
    private FaceDetectorMl faceDetectionProcessor;
    private EventBus eventBus;
    private int MASK_NORMAL;
    private int MASK_LEFT;
    private int MASK_RIGHT;
    private int MASK_OK;
    private String typeDocument = "";
    private String numDocument = "";
    private boolean doRekoggnition = false;
    private boolean validateBiometry = false;
    private boolean isFlashed = false;
    private boolean isChangeCamera = false;
    private String adviser = "";
    private String campus = "";
    private boolean logoReconoserDisabled = false;

    private ProgressDialog progressDialog;
    private String guidCiudadano = "";
    private String saveUser = "";
    private int quality;
    private String guidProcessAgreement;
    private Timer timer;
    private int numAttempts = 0;
    private int timeSeconds = 6000;
    private JSONArray data;
    private CountDownTimer countDownTimer;

    public LivePreviewActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.eventBus = GreenRobotEventBus.getInstance();
        //ViewBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_live_preview);
        //clean data before
        binding.txtMsgPhoto.setText("");

        LibraryReconoSer.getComponent().inject(this);
        presenter.onAttach(this);

        if (!ServicesOlimpia.getInstance().isValidBiometry()) {
            onFinishError(Constants.ERROR_R101);
            return;
        }
        presenter.onAttach(this);
        initDataIntent(getIntent().getExtras());
        configureSDK(getIntent());
        setCountDownTimer();
        presenter.onSetName(guidCiudadano, typeDocument, numDocument, validateBiometry, saveUser, quality, guidProcessAgreement);

        sizeScreen();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        File photoFile;
        try {
            photoFile = createPrivateImageFile();
            fileUrl = photoFile.getAbsolutePath();
        } catch (IOException e) {
            Timber.e("The exception is: %s", e.getMessage());
            e.printStackTrace();
            return;
        }

        if (!verificationCameraFront()) {
            return;
        }
        if (allPermissionsGranted()) {
            initCamera();
        } else {
            requestPermissionsDexter();
        }
    }

    /**
     * Configuración del SDK, número de expresiones, intentos, movimientos y tiempo por movimiento
     *
     * @param intent
     */

    private void configureSDK(Intent intent) {

        if (intent.getExtras() != null && intent.getExtras().containsKey(IntentExtras.NUM_EXPRESION)) {
            SIZE_BAG = intent.getIntExtra(IntentExtras.NUM_EXPRESION, 1);
        }

        if (intent.getExtras() != null && intent.getExtras().containsKey(IntentExtras.NUM_ATTEMPTS)) {
            numAttempts = intent.getIntExtra(IntentExtras.NUM_ATTEMPTS, 1);
        }

        if (intent.getExtras() != null && intent.getExtras().containsKey(IntentExtras.TIME)) {
            timeSeconds = intent.getIntExtra(IntentExtras.TIME, 5);
            timeSeconds = timeSeconds * Constants.ONE_SECOND;
        }

        if (intent.getExtras() != null && intent.getExtras().containsKey(IntentExtras.MOVEMENTS)) {
            try {
                data = new JSONArray(intent.getStringExtra(IntentExtras.MOVEMENTS));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (SIZE_BAG < 1 || SIZE_BAG > 4) {
            Timber.i("ReconoserSDK El número de movimientos debe configurarse entre 1 y 4");
            SIZE_BAG = 1;
        }

        if (numAttempts < 1 || numAttempts > 3) {
            Timber.i("ReconoserSDK El número de intentos debe configurarse entre 1 y 3");
            numAttempts = 1;
        }

        if (timeSeconds < Constants.FACE_DELAY_CAMERA_SCREEN || timeSeconds > Constants.FACE_CAMERA_TIME_OUT) {
            Timber.i("ReconoserSDK El tiempo debe configurarse entre 5000 y 30000");
            timeSeconds = Constants.FACE_DELAY_CAMERA_SCREEN;
        }

        if (data != null && data.length() > 0) {
            Timber.i("ReconoserSDK La cantidad de movimientos seleccionados debe ser mayor o igual al número de expresiones configurado: %s", SIZE_BAG);
            if (data.length() < SIZE_BAG) {
                SIZE_BAG = data.length();
            }
        }
    }

    private void setCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(timeSeconds, Constants.ONE_SECOND) {
            public void onTick(long millisUntilFinished) {
                String v = String.format("%02d", millisUntilFinished / Constants.ONE_MINUTE);
                int va = (int) ((millisUntilFinished % Constants.ONE_MINUTE) / Constants.ONE_SECOND);
                binding.time.setText(v + ":" + String.format("%02d", va));
            }

            public void onFinish() {
                Timber.d("done Face timer: OK");
                binding.time.setText(R.string.live_preview_time_out);
                binding.txtMsgPhoto.setText("");
            }
        };
    }

    private void initDataIntent(Bundle extras) {
        if (extras != null) {
            typeDocument = extras.getString(IntentExtras.TYPE_DOCUMENT, "");
            numDocument = extras.getString(IntentExtras.NUM_DOCUMENT, "");
            guidCiudadano = extras.getString(IntentExtras.GUID_CIUDADANO, "");
            validateBiometry = extras.getBoolean(IntentExtras.VALIDATE_FACE, false);
            saveUser = extras.getString(IntentExtras.SAVE_USER, "");
            quality = extras.getInt(IntentExtras.QUALITY, Constants.IMAGE_QUALITY);
            guidProcessAgreement = extras.getString(IntentExtras.GUID_PROCESS_AGREEMENT, "");
            isFlashed = extras.getBoolean(IntentExtras.ACTIVATE_FLASH, false);
            isChangeCamera = extras.getBoolean(IntentExtras.CHANGE_CAMERA, false);
            adviser = extras.getString(IntentExtras.ADVISER, "");
            campus = extras.getString(IntentExtras.CAMPUS, "");
            logoReconoserDisabled = extras.getBoolean(IntentExtras.LOGO_RECONOSER_DISABLED, false);
        } else {
            typeDocument = "";
            numDocument = "";
            guidCiudadano = "";
        }
    }

    private String[] getRequiredPermissions() {
        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_PERMISSIONS);
            String[] ps = info.requestedPermissions;
            if (ps != null && ps.length > 0) {
                return ps;
            } else {
                return new String[0];
            }
        } catch (Exception e) {
            return new String[0];
        }
    }


    private void requestPermissionsDexter() {

        List<String> permissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        else {
            permissions.add(Manifest.permission.READ_MEDIA_IMAGES);
            permissions.add(Manifest.permission.READ_MEDIA_VIDEO);
        }
        permissions.add(Manifest.permission.CAMERA);
        Dexter.withActivity(this)
                .withPermissions(
                        permissions)
                .withListener(new MultiplePermissionsListener() {

                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            initCamera();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(error -> Toast.makeText(getApplicationContext(), "Error occurred! " + error.toString(), Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }

    private boolean allPermissionsGranted() {
        for (String permission : getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isPermissionGranted(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission granted: " + permission);
            return true;
        }
        Log.i(TAG, "Permission NOT granted: " + permission);
        return false;
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LivePreviewActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
            setResult(Activity.RESULT_CANCELED);
            finish();
        });
        builder.setCancelable(false);
        builder.show();
    }

    // navigating user to app settings

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void sizeScreen() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float ratio = ((float) metrics.heightPixels / (float) metrics.widthPixels);
        if (!logoReconoserDisabled) {
            if (ratio > Constants.SCREEN_RADIUS) {   //TODO 1.7 normal 16:9 si es mayor 18:9
                MASK_NORMAL = R.drawable.bg_face_camera_long_no_logo;
                MASK_LEFT = R.drawable.bg_face_izq_camera_long;
                MASK_RIGHT = R.drawable.bg_face_der_camera_long;
                MASK_OK = R.drawable.bg_face_ok_camera_long;

            } else {
                MASK_NORMAL = R.drawable.bg_face_camera_long_no_logo;
                MASK_LEFT = R.drawable.bg_face_izq_camera_normal;
                MASK_RIGHT = R.drawable.bg_face_der_camera_normal;
                MASK_OK = R.drawable.bg_face_ok_camera_normal;
            }

        } else {
            if (ratio > Constants.SCREEN_RADIUS) {   //TODO 1.7 normal 16:9 si es mayor 18:9

                MASK_NORMAL = R.drawable.bg_face_camera_long_no_logo;
                MASK_LEFT = R.drawable.bg_face_izq_camera_long_no_logo;
                MASK_RIGHT = R.drawable.bg_face_der_camera_long_no_logo;
                MASK_OK = R.drawable.bg_face_ok_camera_long_no_logo;

            } else {
                MASK_NORMAL = R.drawable.bg_face_camera_long_no_logo;
                MASK_LEFT = R.drawable.bg_face_izq_camera_normal;
                MASK_RIGHT = R.drawable.bg_face_der_camera_normal;
                MASK_OK = R.drawable.bg_face_ok_camera_normal;
            }

        }

    }

    public void initCamera() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //Set brightness screen high
        lp.screenBrightness = 1.0f;
        faceDetectionProcessor = new FaceDetectorMl(FACE_MODE_ALL_CLASSIFICATIONS);
        createCameraSource();
        startCameraSource();
    }

    private boolean verificationCameraFront() {
        return Camera.getNumberOfCameras() > 1;
    }

    private void createCameraSource() {
        // If there's no existing cameraSource, create one.
        if (cameraSource == null) {
            cameraSource = new CameraSource(LivePreviewActivity.this, binding.liveFaceOverlay);
            //Camera source is changed
            if (isChangeCamera) {
                cameraSource.setFacing(CameraSource.CAMERA_FACING_BACK);
            } else {
                cameraSource.setFacing(CameraSource.CAMERA_FACING_FRONT);
            }
            //Confirm the permissions and the flash param
            cameraSource.setFlash(this.getPackageManager()
                    .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH) && isFlashed);
            initFaceTracking();
        }
        Timber.i("Using Face Contour Detector Processor");
        //cameraSource.setMachineLearningFrameProcessor(faceContourDetectorProcessor); //todo
        faceDetectionProcessor.expressionForEvaluate(bagValidation.get(0));
        cameraSource.setMachineLearningFrameProcessor(faceDetectionProcessor);
    }

    private void initFaceTracking() {
        bagValidation.clear();
        if (!doRekoggnition) {
            listFaceTracking = new ArrayList<>(Arrays.asList(FACE_MOVE_LEFT, FACE_MOVE_RIGHT, FACE_SMILING, FACE_BLINK, FACE_OPEN_MOUTH));
            if (!doRekoggnition) {

                try {
                    getListFaceTracking();
                } catch (JSONException e) {
                    listFaceTracking = new ArrayList<>();
                    e.printStackTrace();
                }

                Random randomGenerator = new Random();
                int numExpression = SIZE_BAG;
                while (numExpression > 0) {

                    bagValidation.add(FACE_CENTER);
                    int random = randomGenerator.nextInt(listFaceTracking.size());
                    bagValidation.add(listFaceTracking.get(random));
                    listFaceTracking.remove(random);
                    numExpression = numExpression - 1;
                }
                bagValidation.add(END_VALIDATION);
                onChangeFaceDetection();
                changeTextTracking(bagValidation.get(0));
            }
        }
    }

    private void getListFaceTracking() throws JSONException {
        listFaceTracking = new ArrayList<>();

        if (data == null || data.length() == 0) {
            listFaceTracking = new ArrayList<>(Arrays.asList(FACE_MOVE_LEFT, FACE_MOVE_RIGHT, FACE_SMILING, FACE_BLINK, FACE_OPEN_MOUTH));
            return;
        }

        for (int i = 0; i < data.length(); i++) {

            switch (data.getString(i)) {
                case "FACE_MOVE_RIGHT":
                    listFaceTracking.add(FACE_MOVE_RIGHT);
                    break;

                case "FACE_MOVE_LEFT":
                    listFaceTracking.add(FACE_MOVE_LEFT);
                    break;

                case "FACE_SMILING":
                    listFaceTracking.add(FACE_SMILING);
                    break;

                case "FACE_BLINK":
                    listFaceTracking.add(FACE_BLINK);
                    break;

                case "FACE_OPEN_MOUTH":
                    listFaceTracking.add(FACE_OPEN_MOUTH);
                    break;
            }
        }
        Log.e("listFaceTracking", listFaceTracking.toString());
    }

    @SuppressLint("ResourceAsColor")
    private synchronized void changeTextTracking(@Nullable String textShow) {
        if (textShow != null && !textShow.isEmpty()) {
            binding.txtMsgPhoto.setText(textShow);
            binding.txtMsgPhoto.setTextColor(R.color.dark_gray);
            binding.imgMask.setImageResource(paintMask(textShow));
        } else {
            binding.txtMsgPhoto.setText(FACE_CENTER);
        }
    }

    private int paintMask(@NonNull String faceMoveValidated) {
        int move = MASK_NORMAL;
        switch (faceMoveValidated) {
            case FACE_MOVE_LEFT:
                move = MASK_LEFT;
                break;
            case FACE_MOVE_RIGHT:
                move = MASK_RIGHT;
                break;
        }
        return move;
    }

    /**
     * Starts or restarts the camera source, if it exists. If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() {
        if (cameraSource != null) {
            try {
                binding.livePreview.start(cameraSource, binding.liveFaceOverlay);
            } catch (IOException e) {
                Timber.e(e.getMessage(), "Unable to start camera source.");
                cameraSource.release();
                cameraSource = null;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (binding.livePreview.isActivated()) {
            binding.livePreview.stop();
        }
    }

    @Override
    public void onDestroy() {
        if (cameraSource != null) {
            cameraSource.release();
            if(faceDetectionProcessor!=null) {
                faceDetectionProcessor.onDestroy();
            }
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            Timber.w("onDestroy progress");
            progressDialog.dismiss();
        }
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(PhotoTakenEvent event) {
        switch (event.getType()) {
            case PhotoTakenEvent.USER_TRACKING_SUCCESS:
                //binding.faceFrame.setColorFilter(ContextCompat.getColor(this, R.color.green));
                if (bagValidation.size() != 1 && (bagValidation.size() != 2 || (!bagValidation.get(0).equals(FACE_TESTING_SMILING)))) {
                    binding.imgMask.setImageResource(MASK_OK);
                    binding.txtMsgPhoto.setText(CORRECT);
                    binding.txtMsgPhoto.setTextColor(Color.WHITE);
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        if (!bagValidation.isEmpty()) {
                            if ((bagValidation.get(0).equals(FACE_SMILING) || bagValidation.get(0).equals(FACE_CENTER) ||
                                    bagValidation.get(0).equals(FACE_OPEN_MOUTH) || bagValidation.get(0).equals(FACE_MOVE_LEFT) ||
                                    bagValidation.get(0).equals(FACE_MOVE_RIGHT) || bagValidation.get(0).equals(FACE_BLINK)) && bagValidation.size() > 0) {
                                bagValidation.remove(0);
                            }
                            /* bagValidation.remove(0);*/
                            if (!bagValidation.isEmpty()) {
                                changeTextTracking(bagValidation.get(0));
                                //initFaceTracking();
                                onChangeFaceDetection();
                            }
                        }
                    }, 1350);
                } else {
                    if (!doRekoggnition) {
                        if(faceDetectionProcessor!=null) {
                            faceDetectionProcessor.clearExpressionForEvaluate("");
                        }
                        doRekoggnition = true;
                        onShowProgress(R.string.loading_saving);
                        takePicture();
                    }
                }
                break;
            case PhotoTakenEvent.USER_TRACKING_LOST:
                if(faceDetectionProcessor!=null) {
                    faceDetectionProcessor.clearExpressionForEvaluate("");
                }
                initFaceTracking();
                break;
            case PhotoTakenEvent.MAX_FACES_TRAKING:
                if(faceDetectionProcessor!=null) {
                    faceDetectionProcessor.clearExpressionForEvaluate("");
                }
                changeTextTracking(SEVERAL_FACES);
                break;
            case PhotoTakenEvent.INIT_BAG:
                initFaceTracking();
                break;
            case PhotoTakenEvent.USER_TRACKING_FACE_OUT_MOLD:
                if(faceDetectionProcessor!=null) {
                    faceDetectionProcessor.clearExpressionForEvaluate("");
                }
                changeTextTracking(FACE_OUT_MOLD);
                break;
            case PhotoTakenEvent.CHANGE_TEXT:
                if (!bagValidation.isEmpty()) {
                    changeTextTracking(bagValidation.get(0));
                }
                break;
        }
    }

    void onChangeFaceDetection() {
        int mode = FACE_MODE_ALL_CLASSIFICATIONS;
        if (bagValidation.get(0).equals(FACE_OPEN_MOUTH)) {
            mode = FACE_MODE_ALL_CONTOURS;
        }

        faceDetectionProcessor.setFaceMode(mode);
        faceDetectionProcessor.expressionForEvaluate(bagValidation.get(0));
        cameraSource.setMachineLearningFrameProcessor(faceDetectionProcessor);

        startCameraSource();

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        countDownTimer.start();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onFinishRekognitionError(Constants.ERROR_R114);
                timer.cancel();
                timer = null;
                runOnUiThread(() -> {
                    countDownTimer.cancel();
                });
            }
        }, timeSeconds, timeSeconds);
    }

    void takePicture() {

        cameraSource.takePicture(() -> {

        }, picture -> {
            try {
                File file = new File(fileUrl);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(picture);
                fos.flush();
                fos.close();
                ImageUtils.rotationImage(file, LivePreviewActivity.this);
                presenter.onLoadImage(file.getPath(), this, adviser, campus);

            } catch (Exception e) {
                setResult(Activity.RESULT_CANCELED);
                onHideProgress();
                finish();
                doRekoggnition = false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        eventBus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        eventBus.unregister(this);
    }

    private File createPrivateImageFile() throws IOException {
        return createImageFile(false);
    }

    private File createImageFile(boolean createPublicFile) throws IOException {
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPG_SDK_" + guidCiudadano;

        File storageDir;
        if (createPublicFile) {
            storageDir = getExternalCacheDir(); //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        } else {
            storageDir = getCacheDir();
        }
//        storageDir = Application.getAppContext().getCacheDir();
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    public void onFinish() {
        eventBus.unregister(this);
        Intent res = new Intent();
        res.putExtra(PATH_FILE_PHOTO_R, fileUrl);
        setResult(RESULT_OK, res);
        onHideProgress();
        finish();
    }

    @Override
    public void onShowProgress(int msg) {
        progressDialog.setMessage(getString(msg));
        progressDialog.show();
    }

    @Override
    public void onHideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onFinishRekognition(double rekognition) {
        Intent res = new Intent();
        res.putExtra(PATH_FILE_PHOTO_R, fileUrl);
        res.putExtra(IntentExtras.REKOGNITION, rekognition);
        setResult(RESULT_OK, res);
        onHideProgress();
        finish();
    }

    public void onFinishRekognitionError(@NotNull String code) {
        runOnUiThread(() -> {
            if (numAttempts > 0) {
                numAttempts = numAttempts - 1;
                faceDetectionProcessor.clearExpressionForEvaluate("");
                initFaceTracking();
                return;
            }
            onFinishError(code);
        });
    }

    @Override
    public void onRespondValidate(@NotNull ValidarBiometria validateBiometry) {
        Intent res = new Intent();
        res.putExtra(PATH_FILE_PHOTO_R, fileUrl);
        res.putExtra(IntentExtras.VALIDATE_FACE, JsonUtils.stringObject(validateBiometry));
        setResult(RESULT_OK, res);
        onHideProgress();
        finish();
    }

    @Override
    public void onCreateProcess(@NotNull String imageTake) {
        ValidarBiometriaIn data = new ValidarBiometriaIn();
        data.setGuidCiudadano(guidCiudadano);
        data.setSubTipo("Frontal");
        data.setFormato("JPG_B64");
        data.setIdServicio(5);
        data.setBiometria(ImageUtils.getEncodedBase64FromFilePath(imageTake));
        if (guidProcessAgreement != null && !guidProcessAgreement.equals("")) {
            data.setGuidProcesoConvenio(guidProcessAgreement);
        }
        presenter.onValidateBiometry(data);
    }
}
