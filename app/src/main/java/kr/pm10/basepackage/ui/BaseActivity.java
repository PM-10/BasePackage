package kr.pm10.basepackage.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import kr.pm10.basepackage.library.otto.BusProvider;
import kr.pm10.basepackage.utils.DLog;

public class BaseActivity extends AppCompatActivity {

    protected Intent mIntent;

    private ProgressDialog dialog;
    private boolean isLoading = false;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        mIntent = getIntent();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            String className = getClass().getSimpleName();
            DLog.d(className);
            if (savedInstanceState != null) {
                finish();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog = new ProgressDialog(this);
    }

    public void loadingStart() {
        try {
            dialog.show();
            isLoading = true;
        } catch (Exception e) {
            isLoading = false;
        }
    }

    public void loadingComplete() {
        if (!isLoading)
            return;
        try {
            dialog.dismiss();
            isLoading = false;
        } catch (Exception e) {

        }
    }

    protected void appFinish() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask();
            Process.killProcess(Process.myPid());
        } else {
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
        if (isLoading)
            loadingComplete();
    }

}
