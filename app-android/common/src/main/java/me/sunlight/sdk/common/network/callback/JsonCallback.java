package me.sunlight.sdk.common.network.callback;

import android.accounts.NetworkErrorException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;

import me.sunlight.sdk.common.app.Application;
import me.sunlight.sdk.common.network.api.ResponseModel;
import okhttp3.ResponseBody;

/**
 * <pre>
 *     author : 戈传光
 *     e-mail : 1944633835@qq.com
 *     time   : 2018/01/24
 *     desc   :
 *     version:
 * </pre>
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {

    private Class<T> mClass;
    private Type mType;

    public JsonCallback() {
    }


    public JsonCallback(Class<T> aClass) {
        mClass = aClass;
    }

    public JsonCallback(Type type) {
        mType = type;
    }


    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {

        ResponseBody body = response.body();
        if (body == null) return null;

        T data;

        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(body.charStream());

        if (mClass != null) {
            data = gson.fromJson(jsonReader, mClass);
        } else if (mType != null) {
            data = gson.fromJson(jsonReader, mType);
        } else {
            Type genType = getClass().getGenericSuperclass();
            Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            ResponseModel model = gson.fromJson(jsonReader, type);
            // 这里要根据服务器返回的错误码决定
            if (model.status != 0) throw new IllegalAccessException(model.message);
            data = (T) model;
        }
        return data;
    }

    @Override
    public void onError(Response<T> response) {
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
