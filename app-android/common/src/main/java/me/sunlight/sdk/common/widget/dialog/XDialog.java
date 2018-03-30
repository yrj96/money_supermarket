package me.sunlight.sdk.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import butterknife.ButterKnife;
import me.sunlight.sdk.common.R;

/**
 * Created by gechuanguang on 2017/8/11.
 * 邮箱：1944633835@qq.com
 */
public class XDialog extends Dialog {

    protected LayoutParams mLayoutParams;

    public XDialog(Context context) {
        super(context);
        initView(context);
    }

    public XDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    protected XDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        ButterKnife.bind(this);
    }
    private void initView(Context context) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawableResource(R.drawable.bg_transparent);
        Window window = this.getWindow();
        mLayoutParams = window.getAttributes();
        mLayoutParams.alpha = 1f;
        window.setAttributes(mLayoutParams);
        if (mLayoutParams != null) {
            mLayoutParams.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
            mLayoutParams.gravity = Gravity.CENTER;
        }
    }
    public static void showDialog(Context context) {
        XDialog dialog = new XDialog(context);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
    }

}