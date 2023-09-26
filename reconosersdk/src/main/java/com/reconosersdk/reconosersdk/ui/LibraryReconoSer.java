package com.reconosersdk.reconosersdk.ui;

import android.app.Application;
import android.content.Context;
import android.os.CountDownTimer;

import com.reconosersdk.reconosersdk.BuildConfig;
import com.reconosersdk.reconosersdk.di.component.AppComponent;
import com.reconosersdk.reconosersdk.di.component.DaggerAppComponent;
import com.reconosersdk.reconosersdk.di.module.AppModule;
import com.reconosersdk.reconosersdk.http.OlimpiaInterface;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ObtenerToken;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.HeaderToken;
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.ServicesOlimpia;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

import static com.reconosersdk.reconosersdk.utils.ConstantsOlimpia.ONE_THOUSAND_TIME;

public class LibraryReconoSer extends Application {

    private static AppComponent sComponent;
    public static ServicesOlimpia webService;
    public static Context context;

    //Just once does the "ConsultarCiudadano" service
    protected static boolean firstTime;
    protected static String data;
    protected static String guidConvCiu;

    //Custom token to ClientId
    protected static String clientId;
    protected static String clientSecret;

    //To get the token repeatedly
    protected static HeaderToken headerToken;
    protected static CountDownTimer countDownTimer;

    public static void init(Application app, Context ctx, @NotNull String guidConv, @NotNull String datos, String user, String pass) {
        sComponent = DaggerAppComponent.builder().appModule(new AppModule(app)).build();
        webService = ServicesOlimpia.getInstance();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        //Set data
        guidConvCiu = guidConv;
        data = datos;
        context = ctx;
        clientId = user;
        clientSecret = pass;


        //Simple singleton
        headerToken = HeaderToken.getInstance();

        repeatToken();
        getToken();
    }

    private static void repeatToken() {
        final int[] futureInMillis = {headerToken.getExpiresIn()};
        countDownTimer = new CountDownTimer(futureInMillis[0] * ONE_THOUSAND_TIME, ONE_THOUSAND_TIME) {
            @Override
            public void onFinish() {
                //what you want to do after CountDownTimer is done
                futureInMillis[0] = headerToken.getExpiresIn();
                getToken();
                //Cancel the countDownTimer to restart again
                countDownTimer.cancel();
                repeatToken(); //start again
            }

            @Override
            public void onTick(long millisUntilFinished) {
            }
        }.start();
    }

    public static void getToken() {
        ObtenerToken obtenerToken;
        if (clientId == null || clientId.isEmpty() || clientSecret == null || clientSecret.isEmpty()) {
            clientId = BuildConfig.USER_TOKEN;
            clientSecret = BuildConfig.PASS_TOKEN;
            obtenerToken = new ObtenerToken(BuildConfig.USER_TOKEN, BuildConfig.PASS_TOKEN);
        } else {
            obtenerToken = new ObtenerToken(clientId, clientSecret);
        }

        webService.traerToken(obtenerToken, new OlimpiaInterface.CallbackGetToken() {
            @Override
            public void onSuccess(HeaderToken headerToken) {
                getConsultarConvenio();
            }

            @Override
            public void onError(HeaderToken headerToken) {
                repeatToken();
                //No implementation needed
            }
        });
    }

    private static void getConsultarConvenio() {
        if (!firstTime) {
            firstTime = true;
            webService.initServer(getGuidConv(), getDatos());
            repeatToken();
        }
    }

    public static AppComponent getComponent() {
        return sComponent;
    }

    public static Context getContext() {
        return context;
    }

    public static String getDatos() {
        return data;
    }

    public static String getGuidConv() {
        return guidConvCiu;
    }
}
