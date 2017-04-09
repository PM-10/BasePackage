package kr.pm10.basepackage.library.api;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import kr.pm10.basepackage.BaseApplication;
import kr.pm10.basepackage.Constant;
import kr.pm10.basepackage.library.api.cookie.PersistentCookieStore;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.OkHttpClient.Builder;

public class Api {

    private static Api apiAdapter;
    private BaseApiService baseApiService;
    private static CookieManager cookieManager;
    public static Retrofit retrofit;
    public static OkHttpClient okHttpClient;

    private final int TIME_OUT = 30;


    public static Api getInstance() {
        if (apiAdapter == null)
            apiAdapter = new Api();

        return apiAdapter;
    }

    public BaseApiService apiService() {
        return baseApiService;
    }

    public void setApiService() {
        PersistentCookieStore cookieStore = new PersistentCookieStore(BaseApplication.baseContext);
        cookieManager = new CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Builder builder = new Builder();
        builder.cookieJar(new JavaNetCookieJar(cookieManager));
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.interceptors().add(httpLoggingInterceptor);


        okHttpClient = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BAS_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        baseApiService = retrofit.create(BaseApiService.class);
    }

}
