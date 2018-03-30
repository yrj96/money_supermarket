package me.sunlight.sdk.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import me.sunlight.sdk.common.R;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public class PortraitView extends CircleImageView {
    public PortraitView(Context context) {
        super(context);
    }

    public PortraitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PortraitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setup(Context context, String url) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.default_portrait)
                .error(R.drawable.default_portrait)
                .crossFade()
                .dontAnimate()
                .into(this);
    }
}
