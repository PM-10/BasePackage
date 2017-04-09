package kr.pm10.basepackage.library.api;

import android.content.Context;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kr.pm10.basepackage.BaseApplication;
import kr.pm10.basepackage.R;

public class NetworkRequest {


    private static Consumer<Throwable> mOnError = new Consumer<Throwable>() {
        Context baseContext = BaseApplication.baseContext;

        @Override
        public void accept(Throwable throwable) throws Exception {
            Toast.makeText(baseContext,
                    baseContext.getResources().getString(R.string.network_error),
                    Toast.LENGTH_SHORT).show();
        }
    };

    public static <T> Disposable request(Observable<T> observable, Consumer<? super T> consumer) {
        return observable
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, mOnError);
    }

    public static <T> Disposable request(Observable<T> observable, Consumer<? super T> consumer, Consumer<Throwable> onError) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, onError);
    }

}