package com.reconosersdk.reconosersdk.http.di;

import com.reconosersdk.reconosersdk.BuildConfig;
import com.reconosersdk.reconosersdk.http.ServiceOlimpiaApi;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class OlimpiaModule {

    private static final String BASE_URL = BuildConfig.RETROFIT_OLIMPIA_URL;
    @Provides
    public OkHttpClient provideHttpClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    @Provides
    public Retrofit provideRetrofit(String baseURL, OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    public ServiceOlimpiaApi provideOlimpiaService() {
        return provideRetrofit(BASE_URL, provideHttpClient()).create(ServiceOlimpiaApi.class);
    }
}
