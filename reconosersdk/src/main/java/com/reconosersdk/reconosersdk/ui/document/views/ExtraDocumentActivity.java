package com.reconosersdk.reconosersdk.ui.document.views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.reconosersdk.reconosersdk.R;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidateReceiptOut;
import com.reconosersdk.reconosersdk.http.regula.entities.out.ValidarDocumentoGenericoOut;
import com.reconosersdk.reconosersdk.libs.EventBus;
import com.reconosersdk.reconosersdk.libs.GreenRobotEventBus;
import com.reconosersdk.reconosersdk.optimizedCamera.CameraSource;
import com.reconosersdk.reconosersdk.optimizedCamera.CameraSourcePreviewGD;
import com.reconosersdk.reconosersdk.optimizedCamera.FaceAndTextProcessor;
import com.reconosersdk.reconosersdk.optimizedCamera.GraphicOverlay;
import com.reconosersdk.reconosersdk.recognition.PhotoTakenEvent;
import com.reconosersdk.reconosersdk.ui.LibraryReconoSer;
import com.reconosersdk.reconosersdk.ui.base.BaseActivity;
import com.reconosersdk.reconosersdk.ui.document.interfaces.ExtraDocumentContract;
import com.reconosersdk.reconosersdk.utils.IntentExtras;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

import static com.reconosersdk.reconosersdk.utils.IntentExtras.PATH_FILE_PHOTO_R;

public class ExtraDocumentActivity extends BaseActivity implements ExtraDocumentContract.ExtraDocumentMvpView {

    @Inject
    ExtraDocumentContract.ExtraDocumentPresenter presenter;
    private ProgressDialog progressDialog;

    //region ----- Instance Variables -----

    private CameraSource cameraSource = null;
    private CameraSourcePreviewGD preview;
    private GraphicOverlay graphicOverlay;

    private static String TAG = ExtraDocumentActivity.class.getSimpleName().trim();
    private EventBus eventBus;
    private String fileUrlFront = null;
    private String fileUrlBack = null;
    private int typeExtraValue = 0;
    private FaceAndTextProcessor textRecognitionProcessor;
    //endregion

    //private boolean frontImageTake = false;
    private String frontPath = "";
    private TextView tvTittle;
    private ImageButton takePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setScreen();

        presenter.onAttach(this);
        presenter.initUI(getIntent().getExtras());
        setInitValues();


    }

    private void setScreen() {
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_general_document);
        Timber.e("DENSITY: %s", getResources().getDisplayMetrics().density);

        this.eventBus = GreenRobotEventBus.getInstance();
        LibraryReconoSer.getComponent().inject(this);
        takePhoto = findViewById(R.id.capture_button);
        preview = findViewById(R.id.camera_source_preview_gd);
        tvTittle = findViewById(R.id.tv_title_gd);
    }

    private void setInitValues() {
        File photoFile = null;
        try {
            photoFile = createPrivateImageFile();
            fileUrlFront = photoFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (preview == null) {
            Log.d(TAG, "Preview is null");
        }
        graphicOverlay = findViewById(R.id.graphics_overlay);
        if (graphicOverlay == null) {
            Log.d(TAG, "graphicOverlay is null");
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        //textRecognitionProcessor = new FaceAndTextProcessor();
        if (allPermissionsGranted()) {
            createCameraSource();
            startCameraSource();
        } else {
            requestPermissionsDexter();
        }
        takePhoto.setOnClickListener(view -> takePicture());
    }

    @SuppressLint("ResourceAsColor")
    private void setMask(ImageView mask) {
        Bitmap bmp = Bitmap.createBitmap(mask.getWidth() / 2, mask.getHeight() / 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setAlpha(0xFF); // the transparency
        paint.setColor(Color.WHITE); // color is red
        paint.setStyle(Paint.Style.STROKE); // stroke or fill or ...
        paint.setStrokeWidth(5); // the stroke width
        Rect r = new Rect(20, 20, mask.getWidth() / 2, mask.getHeight() / 2);
        canvas.drawRect(r, paint);
        paint.setTextSize(20);
        canvas.drawText("X", 0, 0, paint);
        canvas.drawText("Y", getResources().getDisplayMetrics().heightPixels / 2, getResources().getDisplayMetrics().widthPixels / 2, paint);
        mask.setImageBitmap(bmp);
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
        preview.stop();
    }

    @Override
    public void onDestroy() {
        if (cameraSource != null) {
            cameraSource.release();
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            Timber.w("onDestroy progress");
            progressDialog.dismiss();
        }
        super.onDestroy();
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

    private void createCameraSource() {
        if (cameraSource == null) {
            cameraSource = new CameraSource(this, graphicOverlay);
            cameraSource.setFacing(CameraSource.CAMERA_FACING_BACK);
        }
    }

    private void startCameraSource() {
        if (cameraSource != null) {
            try {
                if (preview == null) {
                    Log.d(TAG, "resume: Preview is null");
                }
                if (graphicOverlay == null) {
                    Log.d(TAG, "resume: graphOverlay is null");
                }
                preview.start(cameraSource, graphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                cameraSource.release();
                cameraSource = null;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(PhotoTakenEvent event) {
        switch (event.getType()) {
            case PhotoTakenEvent.PHOTO_TAKE:
                takePicture();
                break;
        }
    }

    void takePicture() {
        cameraSource.takePicture(() -> {

        }, picture -> {
            try {
                File file = new File(fileUrlFront);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(picture);
                fos.flush();
                fos.close();
                frontPath = file.getPath();
                onShowProgress(R.string.camera_process);
                final Handler handler = new Handler();
                handler.postDelayed(() -> presenter.onLoadImage(frontPath, getApplicationContext()), 3000);
            } catch (Exception e) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    private File createPrivateImageFile() throws IOException {
        return createImageFile(false);
    }

    private File createImageFile(boolean createPublicFile) throws IOException {
        String imageFileName;
        switch (typeExtraValue) {
            case 1:
                imageFileName = "JPG_SDK_VALIDATE_LICENSE";
                break;
            case 2:
                imageFileName = "JPG_SDK_VALIDATE_FEDERAL_LICENSE";
                break;
            default:
                imageFileName = "JPG_SDK_VALIDATE_RECEIPT";
                break;

        }
        File storageDir;
        if (createPublicFile) {
            storageDir = getExternalCacheDir();
        } else {
            storageDir = getCacheDir();
        }
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    public void initUI(@NotNull String opSelected, int typeExtraValue) {
        tvTittle.setText(opSelected);
        this.typeExtraValue = typeExtraValue;
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
                            startCameraSource();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
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

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ExtraDocumentActivity.this);
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

    @Override
    public void onFinish() {
        eventBus.unregister(this);
        Intent res = new Intent();
        res.putExtra(PATH_FILE_PHOTO_R, fileUrlFront);
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

    /*@Override
    public void onFinishData(@NotNull String obversePath, @NotNull String validateReceiptOut) {
        eventBus.unregister(this);
        Intent res = new Intent();
        res.putExtra(IntentExtras.PATH_FILE_ANVERSO, obversePath);
        res.putExtra(IntentExtras.DATA_DOCUMENT, validateReceiptOut);
        *//*String lorem = validateReceiptOut.
        if(validateReceiptOu)*//*
        setResult(RESULT_OK, res);
        onHideProgress();
        finish();
    }*/

    @Override
    public void onFinishData(@NotNull String obversePath, @NotNull String base64) {
        eventBus.unregister(this);
        Intent res = new Intent();
        res.putExtra(IntentExtras.PATH_FILE_ANVERSO, obversePath);
        res.putExtra(IntentExtras.IMAGE64, base64);
        setResult(RESULT_OK, res);
        onHideProgress();
        finish();
    }

}

