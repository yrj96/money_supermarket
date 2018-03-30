package me.sunlight.dadloans.actiivities.account;

import me.sunlight.dadloans.R;
import me.sunlight.sdk.common.app.Activity;

public class RegisterActivity extends Activity {


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_register;
    }


    @Override
    protected void initBefore() {
        super.initBefore();
        getTitleBar().setTitle("注册");

    }
}
