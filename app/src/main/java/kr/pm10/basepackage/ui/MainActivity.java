package kr.pm10.basepackage.ui;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.functions.Consumer;
import kr.pm10.basepackage.R;
import kr.pm10.basepackage.library.api.Api;
import kr.pm10.basepackage.library.api.NetworkRequest;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callGetBase();
    }


    private void callGetBase() {
        NetworkRequest.request(Api.getInstance().apiService().getBase(),
                new Consumer<Void>() {
                    @Override
                    public void accept(Void aVoid) throws Exception {
                        Log.d("API", "getBase");
                    }
                });
    }
}
