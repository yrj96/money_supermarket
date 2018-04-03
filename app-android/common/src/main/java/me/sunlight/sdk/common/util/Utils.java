package me.sunlight.sdk.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gechuanguang on 2017/8/1.
 * 邮箱：1944633835@qq.com
 */
public class Utils {


    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 是否隐藏软件盘
     *
     * @param isShow true 显示，false 隐藏
     * @param view
     */
    public static void showSoftware(boolean isShow, View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        if (!isShow) imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }


    /**
     * 验证是否是手机号
     *
     * @param number 手机号
     * @return
     */
    public static boolean isMobilePhone(String number) {
        return RxRegUtils.isMobile(number);
    }

    /**
     * 时间戳转化成日期
     *
     * @param seconds 秒数  时间戳
     * @param format  yyyy-MM-dd HH:mm:ss
     * @return 返回一个日期
     */
    @SuppressLint("SimpleDateFormat")
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty())
            format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     * @return 返回一个字符串秒数
     */
    @SuppressLint("SimpleDateFormat")
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当前时间的时间戳
     */
    public static String getCurrentTimeStamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return date2TimeStamp(str, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 计算time2减去time1的差值 差值只设置 几天 几个小时 或 几分钟
     * 根据差值返回多长之间前或多长时间后
     */
    public static String getDistanceTime(long time1, long time2) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff;
        String flag;
        if (time1 < time2) {
            diff = time2 - time1;
            flag = "前";
        } else {
            diff = time1 - time2;
            flag = "后";
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day != 0) return day + "天" + flag;
        if (hour != 0) return hour + "小时" + flag;
        if (min != 0) return min + "分钟" + flag;
        return "刚刚";
    }

    /**
     * SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
     * String timeStr1="2011-06-10 08:17:15";
     * String timeStr2="2000-10-11 10:20:51";
     * long date1=sdf.parse(timeStr1).getTime();
     * long date2=sdf.parse(timeStr2).getTime();
     *
     * @param timeStr1
     * @param timeStr2
     * @return
     */
    public static long getSecondBy2Time(String timeStr1, String timeStr2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long date1 = 0, date2 = 0;
        try {
            date1 = sdf.parse(timeStr1).getTime();
            date2 = sdf.parse(timeStr2).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (date2 - date1) / 1000;
    }

    /**
     * 保存 响铃 震动状态
     *
     * @param isShaked true:震动但不响铃，false 反之
     */
    public static void saveShakeSettings(boolean isShaked) {
        if (isShaked) {
            RxSPUtils.putBoolean(mContext, "isShaked", true);
        }
    }

    /**
     * 拿到响铃 震动状态
     *
     * @return true: 震动但不响铃， false：不震动但响铃
     */
    public static boolean getShakeSettings() {
        boolean isShaked = RxSPUtils.getBoolean(mContext, "isShaked");
        return isShaked;
    }

    /**
     * 将uri转化成file对象
     *
     * @param uri
     */
    public static File getFileByUri(Uri uri) {
        File file = null;
        try {
            file = new File(new URI(uri.toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return file;
    }


    /**
     * 倒计时
     *
     * @param textView 控件
     * @param waitTime 倒计时总时长
     * @param interval 倒计时的间隔时间
     * @param hint     倒计时完毕时显示的文字
     */
    public static void countDown(final TextView textView, long waitTime, long interval, final String hint) {
        textView.setEnabled(false);
        android.os.CountDownTimer timer = new android.os.CountDownTimer(waitTime, interval) {

            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText("剩下 " + (millisUntilFinished / 1000) + " S");
            }

            @Override
            public void onFinish() {
                textView.setEnabled(true);
                textView.setText(hint);
            }
        };
        timer.start();
    }

    /**
     * url 编码
     *
     * @param input 输入的参数字符串
     * @return "UTF-8" 编码之后的字符串
     */
    public static String urlEncode(String input) {
        return RxEncodeUtils.urlEncode(input);
    }

    /**
     * 保留5位有效数字 (最后以为四舍五入)
     */
    public static String transNumbers(String number) {
        DecimalFormat df = new DecimalFormat("###.00000");
        return df.format(Double.parseDouble(number));
    }


    /**
     * 获取屏幕的宽or高
     *
     * @param type 0 宽度，1高度
     * @return
     */
    public static int getScreenWidthOrHeight(int type) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        if (type == 0) return width;
        if (type == 1) return height;
        return height;
    }


    /**
     * get App versionCode
     *
     * @param context
     * @return
     */
    public static String getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * get App versionName
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


    //------注册部分数据begin--------//
    public static void saveName(String name) {
        RxSPUtils.putString(mContext, "register_name", name);
    }

    public static String getName() {
        return RxSPUtils.getString(mContext, "register_name");
    }

    public static void saveEmail(String email) {
        RxSPUtils.putString(mContext, "register_email", email);
    }

    public static String getEmail() {
        return RxSPUtils.getString(mContext, "register_email");
    }

    public static void saveMobile(String mobile) {
        RxSPUtils.putString(mContext, "register_mobile", mobile);
    }

    public static String getMobile() {
        return RxSPUtils.getString(mContext, "register_mobile");
    }

    public static void saveCode(String code) {
        RxSPUtils.putString(mContext, "register_code", code);
    }

    public static String getCode() {
        return RxSPUtils.getString(mContext, "register_code");
    }

    public static void savePassword(String password) {
        RxSPUtils.putString(mContext, "register_password", password);
    }

    public static String getPassword() {
        return RxSPUtils.getString(mContext, "register_password");
    }

    public static void saveConfirmPassword(String confirmPassword) {
        RxSPUtils.putString(mContext, "register_confirm_password", confirmPassword);
    }

    public static String getConfirmPassword() {
        return RxSPUtils.getString(mContext, "register_confirm_password");
    }

    public static void saveProfile(String profile) {
        RxSPUtils.putString(mContext, "register_profile", profile);
    }

    public static String getProfile() {
        return RxSPUtils.getString(mContext, "register_profile");
    }


    // 注册完成，全部置为空
    public static void clearRegisterParams() {
        RxSPUtils.putString(mContext, "register_name", "");
        RxSPUtils.putString(mContext, "register_email", "");
        RxSPUtils.putString(mContext, "register_mobile", "");
        RxSPUtils.putString(mContext, "register_code", "");
        RxSPUtils.putString(mContext, "register_password", "");
        RxSPUtils.putString(mContext, "register_confirm_password", "");
        RxSPUtils.putString(mContext, "register_profile", "");
    }
    //------注册部分数据end--------//

    // 注册或者登陆 保存Sessionid
    public static void saveSessionid(String sessionid) {
        RxSPUtils.putString(mContext,"session_id",sessionid);
    }
    // 取值
    public static String getSessionid(){
        return  RxSPUtils.getString(mContext,"session_id");
    }
}
