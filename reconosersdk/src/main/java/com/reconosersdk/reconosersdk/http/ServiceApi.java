package com.reconosersdk.reconosersdk.http;

import com.reconosersdk.reconosersdk.http.amazon.entities.Upload;
import com.reconosersdk.reconosersdk.http.amazon.entities.face.Rekognition;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApi {

    @POST("rekognition")
    Single<Upload> uploadFile(@Body RequestBody body);

    @POST("compare-faces")
    Single<Rekognition> rekognition(@Body RequestBody body);

}
