package com.reconosersdk.reconosersdk.optimizedCamera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.reconosersdk.reconosersdk.R;
import com.reconosersdk.reconosersdk.libs.EventBus;
import com.reconosersdk.reconosersdk.libs.GreenRobotEventBus;
import com.reconosersdk.reconosersdk.recognition.PhotoTakenEvent;
import com.reconosersdk.reconosersdk.utils.IntentExtras;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DocumentCamActivity extends AppCompatActivity {

    //region ----- Instance Variables -----

    private CameraSource cameraSource = null;
    private CameraSourcePreview preview;
    private GraphicOverlay graphicOverlay;

    private static String TAG = DocumentCamActivity.class.getSimpleName().toString().trim();
    private EventBus eventBus;
    private String fileUrl = null;
    private FaceAndTextProcessor textRecognitionProcessor;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_cam);
        this.eventBus = GreenRobotEventBus.getInstance();
        //FirebaseApp.initializeApp(this);

        preview =  findViewById(R.id.camera_source_preview);
        if (preview == null) {
            Log.d(TAG, "Preview is null");
        }
        graphicOverlay =  findViewById(R.id.graphics_overlay);
        if (graphicOverlay == null) {
            Log.d(TAG, "graphicOverlay is null");
        }
        File photoFile = null;
        try {
            photoFile = createPrivateImageFile();
            fileUrl = photoFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        textRecognitionProcessor = new FaceAndTextProcessor();

        createCameraSource();
        startCameraSource();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        startCameraSource();
    }

    /** Stops the camera. */
    @Override
    protected void onPause() {
        super.onPause();
        preview.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
        }
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
        textRecognitionProcessor.documentScreen(1);
        cameraSource.setMachineLearningFrameProcessor(textRecognitionProcessor);
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
    public void onEventMainThread(PhotoTakenEvent event){
        switch (event.getType()) {
            case PhotoTakenEvent.PHOTO_TAKE:
                takePicture();
                break;
        }
    }

    void takePicture(){
        cameraSource.takePicture(() -> {

        }, picture -> {
            try{

                File file = new File(fileUrl);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(picture);
                fos.flush();
                fos.close();
                Intent res = new Intent();
                res.putExtra(IntentExtras.PATH_FILE_PHOTO, fileUrl);
                setResult(RESULT_OK, res);

                finish();
            }catch(Exception e){
                setResult(Activity.RESULT_CANCELED);

                finish();
            }
        });
    }
    private File createPrivateImageFile() throws IOException {
        return createImageFile(false);
    }
    private File createImageFile(boolean createPublicFile) throws IOException {
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPG_SDK";

        File storageDir;
        if (createPublicFile) {
            storageDir = getExternalCacheDir(); //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        } else {
            storageDir = getCacheDir();
        }
//        storageDir = Application.getAppContext().getCacheDir();
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }
}

