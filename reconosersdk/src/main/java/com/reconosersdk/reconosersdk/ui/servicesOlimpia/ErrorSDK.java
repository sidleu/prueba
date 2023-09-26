package com.reconosersdk.reconosersdk.ui.servicesOlimpia;

import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ErrorEntransaccion;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion;
import com.reconosersdk.reconosersdk.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ErrorSDK implements InterfaceErrorSDK{

    @NotNull
    @Override
    public RespuestaTransaccion onGetError(@NotNull String code) {
        switch (code) {
            case Constants.ERROR_R101:
                return  setErrorCustom(Constants.ERROR_R101, Constants.ERROR_CONV);
            default:
                return  setErrorCustom(Constants.ERROR_R102, Constants.ERROR_SERVICE_API);
        }
    }

    @NotNull
    @Override
    public RespuestaTransaccion onGetError(@NotNull Throwable e) {
        switch (e.getMessage()) {
            case Constants.ERROR_SERVER:
                return  setErrorCustom(Constants.ERROR_R103, Constants.ERROR_SERVER);
            default:
                return  setErrorCustom(Constants.ERROR_R100, Constants.ERROR_HOST);
        }
    }

    @NotNull
    @Override
    public RespuestaTransaccion getErrorCustom(@NotNull String code, @NotNull String description) {
        return setErrorCustom(code, description);
    }

    private RespuestaTransaccion setErrorCustom(String code, String msg) {
        RespuestaTransaccion error = new RespuestaTransaccion();
        error.setEsExitosa(false);
        ErrorEntransaccion errorEntransaccion = new ErrorEntransaccion();
        errorEntransaccion.setCodigo(code);
        errorEntransaccion.setDescripcion(msg);
        List<ErrorEntransaccion> list = new ArrayList<ErrorEntransaccion>();
        list.add(errorEntransaccion);
        error.setErrorEntransaccion(list);
        return error;
    }
}
