package com.reconosersdk.reconosersdk.ui.base;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion;
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.ServicesOlimpia;
import com.reconosersdk.reconosersdk.utils.IntentExtras;

import org.jetbrains.annotations.NotNull;

public class BaseActivity extends AppCompatActivity implements MvpView {

    public ProgressDialog mProgressDialog;

    @Override
    public void showProgessDialog(int msgRes) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            //mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);

            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }

        mProgressDialog.setMessage(getString(msgRes));
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onFinishError(@NotNull RespuestaTransaccion message) {
        dismissProgressDialog();
        Intent errorRes = new Intent();
        errorRes.putExtra(IntentExtras.ERROR_SDK, message);
        setResult(IntentExtras.ERROR_INTENT, errorRes);
        finish();
    }

    @Override
    public void onFinishError(@NotNull String code, int message) {
        dismissProgressDialog();
        Intent errorRes = new Intent();
        errorRes.putExtra(IntentExtras.ERROR_MSG, message);
        errorRes.putExtra(IntentExtras.ERROR_SDK, ServicesOlimpia.getInstance().getErrorCode(code));
        setResult(IntentExtras.ERROR_INTENT, errorRes);
        finish();
    }

    @Override
    public void onFinishError(@NotNull String code, @NotNull String message, @NotNull String path) {
        dismissProgressDialog();
        Intent errorRes = new Intent();
        errorRes.putExtra(IntentExtras.ERROR_MSG, message);
        errorRes.putExtra(IntentExtras.PATH_FILE_PHOTO, path);
        errorRes.putExtra(IntentExtras.ERROR_SDK, ServicesOlimpia.getInstance().getErrorCode(code));
        setResult(IntentExtras.ERROR_INTENT, errorRes);
        finish();
    }

    @Override
    public void onFinishError(@NotNull String code) {
        dismissProgressDialog();
        Intent errorRes = new Intent();
        errorRes.putExtra(IntentExtras.ERROR_SDK, ServicesOlimpia.getInstance().getErrorCode(code));
        setResult(IntentExtras.ERROR_INTENT, errorRes);
        finish();
    }

    @Override
    public void onFinishError(@NotNull String code, @NotNull String data) {
        dismissProgressDialog();
        Intent errorRes = new Intent();
        errorRes.putExtra(IntentExtras.ERROR_SDK, data);
        errorRes.putExtra(IntentExtras.ERROR_SDK, ServicesOlimpia.getInstance().getErrorCode(code));
        setResult(IntentExtras.ERROR_INTENT, errorRes);
        finish();
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }
}
