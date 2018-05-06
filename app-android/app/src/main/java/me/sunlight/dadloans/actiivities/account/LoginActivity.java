package me.sunlight.dadloans.actiivities.account;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.wang.avi.AVLoadingIndicatorView;

import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import me.sunlight.dadloans.R;
import me.sunlight.sdk.common.app.Activity;
import me.sunlight.sdk.common.widget.titlebar.CommonTitleBar;

public class LoginActivity extends Activity {

    @BindView(R.id.login_title)
    CommonTitleBar mTitleBar;
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

    @BindView(R.id.login_tv_login)
    TextView tv_login;
    @BindView(R.id.login_img_login_triangle)
    ImageView loginTriangle;
    @BindView(R.id.login_tv_register)
    TextView tv_register;
    @BindView(R.id.login_img_register_triangle)
    ImageView registerTriangle;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initBefore() {
        super.initBefore();
        hideTitleBar();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTitleBar.setLeftImageResource(R.mipmap.ic_back);
        ImmersionBar.hideStatusBar(getWindow());
        mTitleBar.setLeftClickListener(view -> {
            finish();
        });
    }

    @OnClick({R.id.login_btn_login, R.id.login_btn_register, R.id.login_tv_forget,
            R.id.login_tv_login, R.id.login_tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn_login:
                break;
            case R.id.login_btn_register:
                RegisterActivity.runActivity(mContext, RegisterActivity.class);
                break;
            case R.id.login_tv_forget:
                break;

            case R.id.login_tv_login:
                tv_login.setTextColor(Color.parseColor("#4895fe"));
                loginTriangle.setVisibility(View.VISIBLE);
                tv_register.setTextColor(Color.parseColor("#000000"));
                registerTriangle.setVisibility(View.GONE);
                break;
            case R.id.login_tv_register:

                tv_login.setTextColor(Color.parseColor("#000000"));
                loginTriangle.setVisibility(View.GONE);
                tv_register.setTextColor(Color.parseColor("#4895fe"));
                registerTriangle.setVisibility(View.VISIBLE);
                break;
        }
    }
}
