package me.sunlight.sdk.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import me.sunlight.sdk.common.R;

/**
 * @author 戈传光
 */
public class LoadingDialog extends Dialog {
    protected LayoutParams mLayoutParams;

    private static LoadingDialog mLoadingDialog;

    public LoadingDialog(Context context) {
        super(context);
        initView(context);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
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
        setContentView(R.layout.dialog_loading_layout);
    }

    public static void showDialog(Context context) {
        LoadingDialog dialog = new LoadingDialog(context);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        mLoadingDialog = dialog;

    }

    public static void hideDialog() {
        if(mLoadingDialog !=null&&mLoadingDialog.isShowing()){
            mLoadingDialog.hide();
        }
    }
}
