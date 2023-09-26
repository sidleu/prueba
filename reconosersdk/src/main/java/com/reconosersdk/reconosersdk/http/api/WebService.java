package com.reconosersdk.reconosersdk.http.api;

import android.content.Context;
import android.content.Intent;

import com.reconosersdk.reconosersdk.BuildConfig;
import com.reconosersdk.reconosersdk.checkPermissions.CheckPermissionsActivity;
import com.reconosersdk.reconosersdk.http.ReconoserIdApi;
import com.reconosersdk.reconosersdk.http.ServiceOlimpiaApi;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.HeaderToken;
import com.reconosersdk.reconosersdk.ui.LibraryReconoSer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebService {

    private static final String BASE_URL = BuildConfig.RETROFIT_OLIMPIA_URL;
    private static final String BASE_URL_RECONOSERID = BuildConfig.RETROFIT_RECONOSERID_URL;
    private static WebService instance;
    private ServiceOlimpiaApi serviceOlimpiaApi;
    private ReconoserIdApi reconoserIdApi;
    private HeaderToken headerToken;
    private Context context;

    //To get the telephone parameters
    private TelephoneParamethers telephoneParamethers;

    private WebService() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //getContext
        context = LibraryReconoSer.getContext();

        //Simple singleton
        headerToken = HeaderToken.getInstance();

        //Simple singleton
        telephoneParamethers = TelephoneParamethers.getInstance();

        //To easy the test
        OkHttpClient.Builder httpClientBuilder = setHttpClient(loggingInterceptor);

        OkHttpClient client = httpClientBuilder.build();

        Retrofit retrofit = getRetrofit(BASE_URL, client);
        serviceOlimpiaApi = retrofit.create(ServiceOlimpiaApi.class);

        Retrofit retrofitReconoser = getRetrofit(BASE_URL_RECONOSERID, client);
        reconoserIdApi = retrofitReconoser.create(ReconoserIdApi.class);
    }

    private Retrofit getRetrofit(String url, OkHttpClient client) {
        return  new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private void getTelephoneParameters() {
        Intent intent = new Intent(context, CheckPermissionsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private OkHttpClient.Builder setHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        getTelephoneParameters();
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request newRequest = originalRequest.newBuilder()
                            //will override preexisting headers identified by key
                            .addHeader("Content-Type", "application/json")
                            .header("Accept", "application/json")
                            .header("Authorization", headerToken.getTokenType().concat(" ").concat(headerToken.getAccessToken()))
                            .header("Ip", telephoneParamethers.getCurrentIP())
                            .header("emai", telephoneParamethers.getNumberIMEI())
                            .build();
                    return chain.proceed(newRequest);
                })
                .connectTimeout(90, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor);
    }

    public static synchronized WebService getInstance() {
        if (instance == null) {
            instance = new WebService();
        }
        return instance;
    }

    public ServiceOlimpiaApi getServiceOlimpiaApi() {
        return serviceOlimpiaApi;
    }

    public ReconoserIdApi getReconoserIdApi() { return reconoserIdApi;}
}
