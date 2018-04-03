package me.sunlight.sdk.common.app.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import me.sunlight.sdk.common.app.Fragment;
import me.sunlight.sdk.common.app.presenter.BaseContract;

/**
 * Created by gechuanguang on 2017/7/3.
 * 邮箱：1944633835@qq.com
 */
public abstract class PresenterFragment<Presenter extends BaseContract.Presenter> extends Fragment
        implements BaseContract.View<Presenter> {

    public abstract Presenter initPresenter();

    protected  Presenter mPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }


    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null)
            mPresenter.destroy();

    }
}


