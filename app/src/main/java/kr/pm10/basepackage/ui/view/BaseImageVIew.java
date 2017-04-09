package kr.pm10.basepackage.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class BaseImageVIew extends ImageView {

    private Context context;
    SimpleTarget<Bitmap> target;

    public BaseImageVIew(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BaseImageVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        target = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                setImageBitmap(resource);
            }
        };
    }

    public void loadCropImage(String url) {
        if (((Activity) context).isFinishing())
            return;
        if (TextUtils.isEmpty(url))
            return;
        Glide.with(context)
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        setImageBitmap(resource);
                    }
                });

    }

    public void loadGif(int resId) {
        if (((Activity) context).isFinishing())
            return;
        if (resId <= 0)
            return;

        Glide.with(getContext())
                .load(resId)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(this);
    }


}
