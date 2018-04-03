package me.sunlight.dadloans.actiivities.account;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import me.sunlight.dadloans.R;
import me.sunlight.sdk.common.app.Activity;

public class LoginActivity extends Activity {


    @BindView(R.id.login_et_account)
    EditText mLoginEtAccount;
    @BindView(R.id.login_et_password)
    EditText mEtPassword;
    @BindView(R.id.login_tv_forget)
    TextView mTvForget;
    @BindView(R.id.login_avi_loading)
    AVLoadingIndicatorView mLoginAviLoading;
    @BindView(R.id.login_btn_login)
    Button mBtnLogin;
    @BindView(R.id.login_btn_register)
    Button mBtnRegister;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initBefore() {
        super.initBefore();
        getTitleBar().setTitle("登陆");
    }


    @OnClick({R.id.login_btn_login, R.id.login_btn_register,R.id.login_tv_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn_login:
                break;
            case R.id.login_btn_register:
                RegisterActivity.runActivity(mContext,RegisterActivity.class);
                break;
            case R.id.login_tv_forget:
                break;
        }
    }
}
