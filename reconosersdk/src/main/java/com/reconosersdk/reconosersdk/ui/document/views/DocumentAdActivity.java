package com.reconosersdk.reconosersdk.ui.document.views;

import static com.reconosersdk.reconosersdk.utils.Constants.DELAY_CAMERA_SCREEN;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
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
import com.reconosersdk.reconosersdk.databinding.ActivityDocumentPhotoBinding;
import com.reconosersdk.reconosersdk.optimizedCamera.CameraSource;
import com.reconosersdk.reconosersdk.optimizedCamera.FaceAndTextProcessor;
import com.reconosersdk.reconosersdk.ui.LibraryReconoSer;
import com.reconosersdk.reconosersdk.ui.base.BaseActivity;
import com.reconosersdk.reconosersdk.ui.document.interfaces.DocumentAdContract;
import com.reconosersdk.reconosersdk.utils.Constants;
import com.reconosersdk.reconosersdk.utils.ImageUtils;
import com.reconosersdk.reconosersdk.utils.IntentExtras;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import timber.log.Timber;

public class DocumentAdActivity extends BaseActivity implements DocumentAdContract.DocumentAdMvpView {

    @Inject
    DocumentAdContract.DocumentAdMvpPresenter presenter;

    private CameraSource cameraSource = null;
    private static final String TEXT_DETECTION = "Text Detection";
    private final String selectedModel = TEXT_DETECTION;
    private static final String TAG = "DocumentAdActivity";
    private static final int PERMISSION_REQUESTS = 1;

    private ActivityDocumentPhotoBinding binding;
    private String fileUrl = null;
    private FaceAndTextProcessor textRecognitionProcessor;
    private int screen;
    private boolean savePhoto = false;
    private boolean isVisible = false;
    private CountDownTimer countDownTimer;
    private long seconds = Constants.CAMERA_TIME_OUT;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LibraryReconoSer.getComponent().inject(this);
        presenter.onAttach(this);

        //ViewBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_document_photo);
        presenter.initUI(getIntent().getExtras());

        File photoFile;
        try {
            photoFile = createPrivateImageFile();
            fileUrl = photoFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (!verificationCameraBack()) {
            return;
        }

        textRecognitionProcessor = new FaceAndTextProcessor();
        if (allPermissionsGranted()) {
            initCamera();
        } else {
            requestPermissionsDexter();
        }
    }

    private void timeCounter() {
        //Avoid timer nulls
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        final boolean[] isFirstTime = {false};
        countDownTimer = new CountDownTimer(Constants.CAMERA_TIME_OUT, Constants.ONE_SECOND) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                if (!isFirstTime[0]) {
                    Handler handler = new Handler();
                    handler.postDelayed(() -> isFirstTime[0] = true, DELAY_CAMERA_SCREEN);
                } else {
                    seconds = (millisUntilFinished + Constants.ONE_SECOND) / Constants.ONE_SECOND;
                    Timber.d("seconds remaining: %s", seconds);
                    if (seconds == 1) {
                        binding.tvTime.setText("Tiempo restante: " + seconds + " segundo");
                    } else {
                        binding.tvTime.setText("Tiempo restante: " + seconds + " segundos");
                    }
                }
            }

            public void onFinish() {
                Timber.d("done Document timer: OK");
                binding.tvTime.setText(R.string.live_preview_time_out);
            }

        }.start();
    }

    private void createCameraSource() {
        if (cameraSource == null) {
            cameraSource = new CameraSource(DocumentAdActivity.this, binding.fireFaceOverlay);
            cameraSource.setFacing(CameraSource.CAMERA_FACING_BACK);
        }
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            textRecognitionProcessor.documentScreen(screen);
            cameraSource.setMachineLearningFrameProcessor(textRecognitionProcessor);
        }, DELAY_CAMERA_SCREEN);
    }

    /**
     * Starts or restarts the camera source, if it exists. If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() {
        if (cameraSource != null) {
            try {
                binding.firePreview.start(cameraSource, binding.fireFaceOverlay);
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
        Log.d(TAG, "onResume");
        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        binding.firePreview.stop();
    }

    @Override
    public void onDestroy() {
        if (cameraSource != null) {
            cameraSource.release();
        }
        textRecognitionProcessor.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    private String[] getRequiredPermissions() {
        try {
            PackageInfo info =
                    this.getPackageManager()
                            .getPackageInfo(this.getPackageName(), PackageManager.GET_PERMISSIONS);
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

    private boolean allPermissionsGranted() {
        for (String permission : getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, @NonNull int[] grantResults) {
        Log.i(TAG, "Permission granted!");
        if (allPermissionsGranted()) {
            createCameraSource();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private static boolean isPermissionGranted(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission granted: " + permission);
            return true;
        }
        Log.i(TAG, "Permission NOT granted: " + permission);
        return false;
    }

    @Override
    public void onNext() {
    }

    @Override
    public void initUI(@NonNull int title, @NonNull int msg, int screen) {
        binding.tvTitle.setText(title);
        binding.tvMessage.setText(msg);
        timeCounter();
        this.screen = screen;
    }

    @Override
    public void onShowButton() {
        binding.tvTake.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideButton() {
        binding.tvTake.setVisibility(View.GONE);
    }

    @Override
    public void onTakePhoto(@NotNull String textScan, @NotNull String barcode, @NotNull String typeBarcode, @NonNull Bitmap bitmap, int typeDocument) {
        if (!savePhoto) {
            cameraSource.takePicture(() -> {
            }, picture -> {
                try {
                    savePhoto = true;
                    File file = new File(fileUrl);
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(picture);
                    fos.flush();
                    fos.close();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, Constants.MAX_QUALITY, fos);
                    ImageUtils.rotationImage(file, DocumentAdActivity.this);
                    //TODO pruebas
                    /*BitmapFactory.Options options1 = Miscellaneous.getIMGSize(file.getPath());
                    double auxRadiusImage = (double) options1.outHeight / options1.outWidth;
                    String showText1 = "\n" + "Original path document is in: " + file.getPath() + "\n" +
                            "image document height: " + options1.outHeight + "\n" +
                            "image document width: " + options1.outWidth + "\n" +
                            "image document radius: " + auxRadiusImage + "\n";
                    Timber.e("Show compress before image is: %s", showText1);*/
                    File compressedImageFile = ImageUtils.getResizeRescaledImage(Constants.HEIGHT_DOCUMENT, Constants.WIDTH_DOCUMENT, Constants.MAX_QUALITY, file);
                    //Avoid nulls
                    if (compressedImageFile != null) {
                        Intent res = new Intent();
                        res.putExtra(IntentExtras.PATH_FILE_PHOTO, compressedImageFile.getPath());
                        res.putExtra(IntentExtras.TEXT_SCAN, textScan);
                        res.putExtra(IntentExtras.DATA_BARCODE, barcode);
                        res.putExtra(IntentExtras.TYPE_BARCODE, typeBarcode);
                        res.putExtra(IntentExtras.TYPE_DOCUMENT_DATA, typeDocument);
                        setResult(RESULT_OK, res);
                        //TODO Solo para pruebas
                       /* BitmapFactory.Options options = Miscellaneous.getIMGSize(compressedImageFile.getPath());
                        String b64Doc = ImageUtils.getEncodedBase64FromFilePath(compressedImageFile.getPath());
                        String mimeType = ImageUtils.showMimeTypeB64(b64Doc);
                        auxRadiusImage = (double) options.outHeight / options.outWidth;
                        String showText = "\n" + "BackEnd path document is in: " + compressedImageFile.getPath() + "\n" +
                                "image document height: " + options.outHeight + "\n" +
                                "image document width: " + options.outWidth + "\n" +
                                "image b64 mimeType: " + mimeType + "\n" +
                                "image document radius: " + auxRadiusImage + "\n";
                        //"image b64: " + b64Doc+ "\n";
                        Timber.e("Show compress after image is: %s", showText);*/
                        //TODO Solo para pruebas
                        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            StringUtils.appendLog("*** Texto a visualizar después de la compresión *** " + "\n"+ LocalDateTime.now() + "\n"+ showText + " *** Fin del texto *** ", true);
                        }*/
                        finish();
                    } else {
                        setResult(Activity.RESULT_CANCELED);
                        finish();
                    }
                } catch (Exception e) {
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                }
            });
        }
    }

    private File createPrivateImageFile() throws IOException {
        return createImageFile(false);
    }

    private File createImageFile(boolean createPublicFile) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPG_SDK_FRONT " + timeStamp;
        if (screen == 1) {
            imageFileName = "JPG_SDK_BACK " + timeStamp;
        }

        File storageDir;
        if (createPublicFile) {
            storageDir = getExternalCacheDir(); //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        } else {
            storageDir = getCacheDir();
        }
        //storageDir = Application.getAppContext().getCacheDir();
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    public void onErrorNextFinish(int msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Intent res = new Intent();
        res.putExtra(IntentExtras.TEXT_SCAN, msg);
        setResult(RESULT_CANCELED, res);
        finish();
    }

    @Override
    public void onShowMaxFaces() {
        if (!isVisible) {
            isVisible = true;
            binding.txtMaxFaces.setVisibility(View.VISIBLE);
            binding.tvMessage.setVisibility(View.INVISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                binding.tvMessage.setVisibility(View.VISIBLE);
                binding.txtMaxFaces.setVisibility(View.INVISIBLE);
                isVisible = false;
            }, 2000);
        }
    }

    private void requestPermissionsDexter() {

        List<String> permissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            permissions.add(Manifest.permission.READ_MEDIA_IMAGES);
            permissions.add(Manifest.permission.READ_MEDIA_VIDEO);
        }
        permissions.add(Manifest.permission.CAMERA);
        Dexter.withActivity(this)
                .withPermissions(permissions)
                .withListener(new MultiplePermissionsListener() {

                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            createCameraSource();
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

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DocumentAdActivity.this);
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


    public void initCamera() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //Set brightness screen high
        lp.screenBrightness = 1.0f;
        createCameraSource();
        startCameraSource();
    }


    private boolean verificationCameraBack() {
        return Camera.getNumberOfCameras() > 0;
    }
}
