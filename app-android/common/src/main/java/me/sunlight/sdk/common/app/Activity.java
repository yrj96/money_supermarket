package me.sunlight.sdk.common.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import me.sunlight.sdk.common.Common;
import me.sunlight.sdk.common.R;
import me.sunlight.sdk.common.util.Utils;
import me.sunlight.sdk.common.widget.convention.PlaceHolderView;
import me.sunlight.sdk.common.widget.titlebar.CommonTitleBar;

/**
 * @author sunlight
 */
public abstract class Activity extends AppCompatActivity {

    protected Context mContext = this;
    private LayoutInflater mInflater;
    private FrameLayout ViewContainer;
    private CommonTitleBar mTitleBar;
    protected PlaceHolderView mPlaceHolderView;
    private OffLineReceiver mOffLineReceiver;
    private List<Activity> mActivities = new ArrayList<>();

    /**
     * 入口
     */
    public static void runActivity(Context context, Class<? extends Activity> clazz) {
        context.startActivity(new Intent(context, clazz));
    }

    public static void runActivity(Context context, Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i("当前的界面为:" + getClass().getSimpleName());
        RxActivityUtils.addActivity(this);
        mActivities.add(this);
        Utils.init(this);
        // 在界面未初始化之前调用的初始化窗口
        initWidows();

        if (initArgs(getIntent().getExtras())) {
            // 得到界面Id并设置到Activity界面中
            int layId = getContentLayoutId();
            setContentView(R.layout.activity_base);
            /** ViewContainer --》 承载子类Activity的容器 **/
            ViewContainer = (FrameLayout) findViewById(R.id.fl_container);
            mTitleBar = (CommonTitleBar) findViewById(R.id.base_title_bar);
            setDefaultTitlebar();
            mInflater = LayoutInflater.from(mContext);
            mInflater.inflate(layId, ViewContainer);
            ButterKnife.bind(this);
            initBefore();
            initWidget();
            initData();
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 注册一个下线广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Common.Constants.OFFLINE_ACTION);
        mOffLineReceiver = new OffLineReceiver();
        this.registerReceiver(mOffLineReceiver, filter);

    }

    protected boolean checkIsImmersive() {
        boolean isImmersive = false;
        if (hasKitKat() && !hasLollipop()) {
            isImmersive = true;
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else if (hasLollipop()) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            isImmersive = true;
        }
        return isImmersive;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 初始化 默认的titlebar
     */
    private void setDefaultTitlebar() {
        mTitleBar.setHeight(48 * 2);
        mTitleBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        // 左边可设置图片，文字单独显示，也可以设置图片文字同时显示
        mTitleBar.setLeftImageResource(R.drawable.ic_back);
        mTitleBar.setLeftTextColor(getResources().getColor(R.color.white));
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /** 中间文字根据左右控件始终居中显示，自动排版 */
        mTitleBar.setTitle("");
        mTitleBar.setTitleColor(getResources().getColor(R.color.white));

        /** 下划分割线 */
        mTitleBar.setDividerColor(Color.parseColor("#ececec"));

        /** 如果你的项目使用了沉浸式，布局时候加上这行代码，TitleBar会自动填充状态栏 */
        if (checkIsImmersive()) {
            mTitleBar.setImmersive(true);
        } else {
            mTitleBar.setImmersive(false);
        }

    }

    /**
     * 初始化控件调用之前
     */
    protected void initBefore() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    /**
     * 初始化窗口
     */
    protected void initWidows() {
    }

    /**
     * 初始化相关参数
     *
     * @param bundle 参数Bundle
     * @return 如果参数正确返回True，错误返回False
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    /**
     * 得到当前界面的资源文件Id
     *
     * @return 资源文件Id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget() {
        ButterKnife.bind(this);
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    @Override
    public boolean onSupportNavigateUp() {
        // 当点击界面导航返回时，Finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    /**
     * 设置占位布局
     *
     * @param placeHolderView 继承了占位布局规范的View
     */
    public void setPlaceHolderView(PlaceHolderView placeHolderView) {
        this.mPlaceHolderView = placeHolderView;
    }

    /**
     * 提供titleBar
     */
    public CommonTitleBar getTitleBar() {
        return this.mTitleBar;
    }

    /**
     * 隐藏titleBar
     */
    public void hideTitleBar() {
        this.mTitleBar.setVisibility(View.GONE);
    }

    /**
     * 统统给我下线
     */
    public class OffLineReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Logger.e(mActivities.size() + "---" + mActivities);
            Application.getInstance().showAccountView(context);
            Logger.w("OffLineReceiver:" + context.getClass().getSimpleName());
        }
    }


    /***
     * 中心思想就是首先判断我们手指点击的区域坐标是否落在EditText上面，
     * 如果不是的话，我们需要强制关闭输入法，不管是否已经显示和关闭，
     * 这相对于以前我们直接判断输入法是否已经显示的方案简单不少！
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            View v = getCurrentFocus();

            //如果不是落在EditText区域，则需要关闭输入法
            if (HideKeyboard(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
    private boolean HideKeyboard(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {

            int[] location = {0, 0};
            view.getLocationInWindow(location);

            //获取现在拥有焦点的控件view的位置，即EditText
            int left = location[0], top = location[1], bottom = top + view.getHeight(), right = left + view.getWidth();
            //判断我们手指点击的区域是否落在EditText上面，如果不是，则返回true，否则返回false
            boolean isInEt = (event.getX() > left && event.getX() < right && event.getY() > top
                    && event.getY() < bottom);
            return !isInEt;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mOffLineReceiver != null) {
            this.unregisterReceiver(mOffLineReceiver);
            mOffLineReceiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivities.remove(this);
        Logger.wtf("销毁了--》" + this.getClass().getSimpleName());
        ImmersionBar.with(this).destroy();
    }

}
