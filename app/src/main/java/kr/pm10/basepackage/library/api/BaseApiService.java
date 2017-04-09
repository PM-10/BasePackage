package kr.pm10.basepackage.library.api;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface BaseApiService {

    @GET("get_base/")
    Observable<Void> getBase();
}
