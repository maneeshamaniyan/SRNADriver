package com.ryx.srnadriver.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryx.srnadriver.Auth.BasicAuthInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //public static final String BASE_URL = "http://gopadel.r-y-x.net/";
//    public static final String BASE_URL = "https://srna.ae/admin/";
    public static final String BASE_URL = "http://srna.r-y-x.net/";

    public static final String version = "api/v1/";
    private static Retrofit retrofit = null;

    public static ApiInterface getApi() {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        // ServiceRequestDetails customization: add request headers
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Authorization", Credentials.basic("admin", "1234")); // <-- this is the important line

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .build();

        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL+version)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }
}
