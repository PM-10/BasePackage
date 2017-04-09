package kr.pm10.basepackage.library.api;

import android.content.Context;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import kr.perfectree.heydealer_library.R;
import kr.perfectree.heydealer_library.library.util.DLog;
import kr.perfectree.heydealer_library.library.util.Utils;
import kr.perfectree.heydealer_library.ui.base.LibraryActivity;
import kr.perfectree.heydealer_library.ui.base.LibraryApplication;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.OnErrorNotImplementedException;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class NetworkRequest extends OnErrorNotImplementedException {


    private static Action1<Throwable> mOnError = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            Context context = LibraryApplication.baseContext;

            try {
                ((LibraryActivity) LibraryActivity.getCurrentContext()).loadingComplete();
            } catch (Exception e) {
                DLog.e("loading complete fail");
            }

            try {
                if (throwable instanceof TimeoutException || throwable instanceof SocketTimeoutException) {
                    DLog.e(throwable.toString());
                    Toast.makeText(context, context.getResources().getString(R.string.network_error), Toast.LENGTH_LONG).show();
                } else {
                    Utils.setErrorToast(context, throwable);
                }
            } catch (Exception e) {
                if (LibraryApplication.DEBUG) {
                    e.printStackTrace();
                    DLog.e(throwable.toString());
                    Toast.makeText(context, throwable.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    public static <T> Subscription request(final Observable<T> observable, Action1<? super T> onAction) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onAction, mOnError);
    }

    public static <T> Subscription request(Observable<T> observable, Action1<? super T> onAction, Action1<Throwable> onError) {
        return observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onAction, onError);
    }

    public NetworkRequest(String message, Throwable e) {
        super(message, e);
    }

    public NetworkRequest(Throwable e) {
        super(e);
    }

}