package com.reconosersdk.reconosersdk.ui.document.presenters;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.reconosersdk.reconosersdk.R;
import com.reconosersdk.reconosersdk.libs.EventBus;
import com.reconosersdk.reconosersdk.libs.GreenRobotEventBus;
import com.reconosersdk.reconosersdk.recognition.PhotoTakenEvent;
import com.reconosersdk.reconosersdk.ui.base.BasePresenter;
import com.reconosersdk.reconosersdk.ui.document.interfaces.DocumentAdContract;
import com.reconosersdk.reconosersdk.utils.IntentExtras;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class DocumentAdPresenterImpl<V extends DocumentAdContract.DocumentAdMvpView> extends BasePresenter<V> implements DocumentAdContract.DocumentAdMvpPresenter<V> {

    private static final String ANVERSO = "Anverso";
    private static final String REVERSO = "Reverso";
    private EventBus eventBus;
    private String textScan;
    private String barcode;
    private String typeBarcode;
    private Bitmap bitmap;
    private int typeDocument;

    @Override
    public void initUI(@NotNull Bundle extras) {
        switch (Objects.requireNonNull(extras.getString(IntentExtras.TEXT_SCAN))) {
            case ANVERSO:
                getMvpView().initUI(R.string.camera_document_txt_title_front, R.string.camera_document_txt_msg_front, 0);
                break;
            case REVERSO:
                getMvpView().initUI( R.string.camera_document_txt_title_back, R.string.camera_document_txt_msg_back, 1);
                break;
            default:
                getMvpView().onErrorNextFinish(R.string.error_not_type_document);
                break;
        }
    }

    @Override
    public void onAttach(@Nullable V view) {
        super.onAttach(view);
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onStart() {
        eventBus.register(this);
    }

    @Override
    public void onStop() {
        eventBus.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(@NonNull PhotoTakenEvent event) {
        switch (event.getType()) {
            case PhotoTakenEvent.PHOTO_TAKE:
                onShowButtonTake(event.getTextScan(), event.getTextBarcode(), event.getTypeBarcode(), event.getBitmap(), event.getTypeDocument());
                event.setType(-1);
                break;
            case PhotoTakenEvent.MAX_FACES_TRAKING:
                onMaxFacesTraking();
                break;
        }
    }

    private void onMaxFacesTraking() {
        getMvpView().onShowMaxFaces();
    }

    private void onShowButtonTake(String textScan, String textBarcode, String typeBarcode, Bitmap bitmap, int typeDocument) {
        this.textScan = textScan;
        this.barcode = textBarcode;
        this.typeBarcode = typeBarcode;
        this.bitmap = bitmap;
        this.typeDocument = typeDocument;

        Objects.requireNonNull(getMvpView()).onTakePhoto(textScan, textBarcode, typeBarcode, bitmap, typeDocument);
    }
}
