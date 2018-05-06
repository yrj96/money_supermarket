package me.sunlight.dadloans.actiivities.loans;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.sunlight.dadloans.R;
import me.sunlight.sdk.common.app.Activity;

public class ApplyLoansActivity extends Activity {


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_apply_loans;
    }

    @Override
    protected void initBefore() {
        super.initBefore();

        getTitleBar().setTitle("贷款");

    }
}
