package kr.pm10.basepackage.library.api;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import kr.perfectree.heydealer_library.api.interceptor.cookie.PersistentCookieStore;
import kr.perfectree.heydealer_library.api.interceptor.customer.CustomerAppInterceptor;
import kr.perfectree.heydealer_library.api.interceptor.customer.CustomerNetworkInterceptor;
import kr.perfectree.heydealer_library.api.interceptor.dealer.DealerAppInterceptor;
import kr.perfectree.heydealer_library.api.interceptor.dealer.DealerNetworkInterceptor;
import kr.perfectree.heydealer_library.api.service.HeydealerApiService;
import kr.perfectree.heydealer_library.library.Constant;
import kr.perfectree.heydealer_library.library.util.Utils;
import kr.perfectree.heydealer_library.ui.base.LibraryApplication;
import kr.pm10.basepackage.BaseApplication;
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

        Builder builder = new Builder();
        if (Utils.isCustomerPackage()) {
            builder.cookieJar(new JavaNetCookieJar(cookieManager));
            builder.addInterceptor(new CustomerAppInterceptor());
            builder.addNetworkInterceptor(new CustomerNetworkInterceptor());
        } else if (Utils.isDealerPackage()) {
            builder.cookieJar(new JavaNetCookieJar(cookieManager));
            builder.addNetworkInterceptor(new DealerNetworkInterceptor());
            builder.addInterceptor(new DealerAppInterceptor());
        }
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.interceptors().add(httpLoggingInterceptor);


        okHttpClient = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.HEYDEALER_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        baseApiService = retrofit.create(HeydealerApiService.class);
    }

}
