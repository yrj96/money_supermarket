package me.sunlight.dadloans.actiivities;

import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import me.sunlight.dadloans.R;
import me.sunlight.dadloans.actiivities.account.LoginActivity;
import me.sunlight.dadloans.frags.HomeFragment;
import me.sunlight.sdk.common.app.Activity;
import me.sunlight.sdk.common.app.Fragment;
import me.sunlight.sdk.common.util.DoubleClickExitDetector;
import me.sunlight.sdk.common.widget.bottombar.tab.BottomTab;
import me.sunlight.sdk.common.widget.bottombar.tab.TabModel;
import me.sunlight.sdk.common.widget.titlebar.CommonTitleBar;

public class MainActivity extends Activity {

    @BindView(R.id.main_fl_container)
    FrameLayout mContainer;
    @BindView(R.id.bottomTab)
    BottomTab mBottomTab;

    private Fragment mCurrentFragment; //解决fragment重复创建
    private DoubleClickExitDetector doubleClickExitDetector;



    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initBefore() {
        super.initBefore();
        getTitleBar().setLeftVisible(false);
        getTitleBar().addAction(new CommonTitleBar.ImageAction(R.mipmap.tab_mine_normal) {
            @Override
            public void performAction(View view) {
                LoginActivity.runActivity(mContext,LoginActivity.class);
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fl_container, mCurrentFragment = new HomeFragment())
                .commit();

        doubleClickExitDetector = new DoubleClickExitDetector(mContext);
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        mBottomTab.initTab(R.color.black_alpha_48, R.color.colorPrimary);
        mBottomTab
                .addTab(new TabModel.Builder()
                        .addTabText("首页")
                        .addTabNormalIcon(R.mipmap.tab_graborder_normal)
                        .addTabSelectedIcon(R.mipmap.tab_graborder_selected)
                        .build())
                .addTab(new TabModel.Builder()
                        .addTabText("额度高")
                        .addTabNormalIcon(R.mipmap.tab_mine_normal)
                        .addTabSelectedIcon(R.mipmap.tab_mine_selected)
                        .build())
                .addTab(new TabModel.Builder()
                        .addTabText("利率低")
                        .addTabNormalIcon(R.mipmap.tab_mine_normal)
                        .addTabSelectedIcon(R.mipmap.tab_mine_selected)
                        .build())
                .addTab(new TabModel.Builder()
                        .addTabText("放贷快")
                        .addTabNormalIcon(R.mipmap.tab_mine_normal)
                        .addTabSelectedIcon(R.mipmap.tab_mine_selected)
                        .build());


        mBottomTab.setOnTabItemClickListener(new BottomTab.onTabItemClickListener() {
            @Override
            public void onTabItemClick(int positon) {
                switch (positon) {
                    case 0:
                        if (mCurrentFragment instanceof HomeFragment) {
                            return;
                        }
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_fl_container, mCurrentFragment = new HomeFragment())
                                .commit();
                        break;
                }
            }
        });

    }
}
