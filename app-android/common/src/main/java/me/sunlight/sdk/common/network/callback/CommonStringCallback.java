package me.sunlight.sdk.common.network.callback;

import android.accounts.NetworkErrorException;
import android.content.Intent;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import java.net.ConnectException;

import me.sunlight.sdk.common.Common;
import me.sunlight.sdk.common.app.Application;

/**
 * Created by gechuanguang on 2017/7/25.
 * 邮箱：1944633835@qq.com
 */
public abstract class CommonStringCallback extends AbsCallback<String> {
    private StringConvert convert;

    public CommonStringCallback() {
        convert = new StringConvert();
    }

    @Override
    public String convertResponse(okhttp3.Response response) throws Throwable {
        String jsonStr = convert.convertResponse(response);
        response.close();

        JSONObject obj = new JSONObject(jsonStr);
        int status = obj.getInt("status");
        if (status == 500) Application.getInstance().sendBroadcast(new Intent(Common.Constants.OFFLINE_ACTION));
        if (status != 1) throw new IllegalStateException(obj.getString("message"));
        return jsonStr;
    }

    @Override
    public void onError(Response<String> response) {
        super.onError(response);
        if (response.getException() instanceof NetworkErrorException) {
            Application.showToast("网络异常，请检查网络！", Application.TOAST_WARNING);
        } else if (response.getException() instanceof ConnectException) {
            Application.showToast("服务器异常", Application.TOAST_ERROR);
        } else {
            Application.showToast(response.getException().getMessage(), Application.TOAST_WARNING);
        }
    }
}
