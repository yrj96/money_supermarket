package me.sunlight.sdk.common.app.mvp;

import android.support.annotation.LayoutRes;

import me.sunlight.sdk.common.app.Activity;
import me.sunlight.sdk.common.app.Application;
import me.sunlight.sdk.common.app.presenter.BaseContract;

/**
 * Created by gechuanguang on 2017/7/1.
 * 邮箱：1944633835@qq.com
 */
public abstract class PresenterActivity<Presenter extends BaseContract.Presenter> extends Activity
        implements BaseContract.View<Presenter> {


    public abstract Presenter initPresenter();

    protected Presenter mPresenter;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initPresenter();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void showError(int str) {
        Application.showToast(str,Application.TOAST_ERROR);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter =presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       if(mPresenter!=null)
           mPresenter.destroy();
    }
}
