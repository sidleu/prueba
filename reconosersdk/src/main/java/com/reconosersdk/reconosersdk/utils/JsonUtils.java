package com.reconosersdk.reconosersdk.utils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.CiudadanoIn;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import timber.log.Timber;

public class JsonUtils {

    public static RequestBody requestBody(@NonNull String key, @NonNull String value) {
        JSONObject object = new JSONObject();
        try {
            object.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    public static RequestBody requestBody(@NonNull String key1, @NonNull String value1, @NonNull String key2, @NonNull String value2) {
        JSONObject object = new JSONObject();
        try {
            object.put(key1, value1);
            object.put(key2, value2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    public static RequestBody requestBody(@NonNull String key1, @NonNull String value1, @NonNull String key2, @NonNull String value2, @NonNull String key3, @NonNull String value3) {
        JSONObject object = new JSONObject();
        try {
            object.put(key1, value1);
            object.put(key2, value2);
            object.put(key3, value3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    public static RequestBody requestBody(@NonNull String key1, @NonNull String value1, @NonNull String key2, @NonNull String value2, @NonNull String key3, @NonNull String value3,
                                          @NonNull String key4, @NonNull String value4, @NonNull String key5, @NonNull String value5) {
        JSONObject object = new JSONObject();
        try {
            object.put(key1, value1);
            object.put(key2, value2);
            object.put(key3, value3);
            object.put(key4, value4);
            object.put(key5, value5);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    public static RequestBody requestBody(@NonNull JSONObject data) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), data.toString());
    }

    public static String makeJson(@NonNull String key1, @NonNull String value1, @NonNull String key2, @NonNull String value2) {
        JSONObject object = new JSONObject();
        try {
            object.put(key1, value1);
            object.put(key2, value2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    public static RequestBody requestSaveResident(@NonNull CiudadanoIn ciudadano) {
        Gson gson = new Gson();
        String json = gson.toJson(ciudadano);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
    }

    public static RequestBody requestObject(@NonNull Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
    }

    //Founded in https://stackoverflow.com/questions/16558709/gson-issue-with-string
    public static RequestBody requestObjectDisableHtmlEscaping(@NonNull Object object) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        //Replace "\n" and "\"", sometimes the library doesn't encode correctly
        String json = gson.toJson(object).replace("\n", "").replace("\"", "");
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
    }

    public static String stringObject(@NonNull Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    //Founded in https://stackoverflow.com/questions/16558709/gson-issue-with-string
    public static RequestBody requestBody(String stringJson) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        //Replace "\n" and "\"", sometimes the library doesn't encode correctly
        String json = gson.toJson(stringJson).replace("\n", "").replace("\"", "");
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
    }

    public static String bodyToString(final RequestBody request) {
        if (request != null) {
            try {
                final RequestBody copy = request;
                final Buffer buffer = new Buffer();
                copy.writeTo(buffer);
                return buffer.readUtf8();
            } catch (final IOException e) {
                Timber.w("Failed to stringify request body: %s", e.getMessage());
                return "";
            }
        }
        Timber.w("Failed to requestBody is null");
        return "";
    }
}
