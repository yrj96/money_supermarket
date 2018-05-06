package me.sunlight.dadloans.frags;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import me.sunlight.dadloans.App;
import me.sunlight.dadloans.R;
import me.sunlight.dadloans.actiivities.MainActivity;
import me.sunlight.dadloans.actiivities.loans.ApplyLoansActivity;
import me.sunlight.sdk.common.app.Fragment;
import me.sunlight.sdk.common.widget.recycler.DividerItemDecoration;
import me.sunlight.sdk.common.widget.recycler.RecyclerAdapter;

/**
 * <pre>
 *     author : 戈传光
 *     e-mail : 1944633835@qq.com
 *     time   : 2018/03/30
 *     desc   :
 *     version:
 * </pre>
 */
public class HomeFragment extends Fragment {
    @BindView(R.id.fragment_home_banner)
    BGABanner mBanner;

    @BindView(R.id.home_recommend_ryList)
    RecyclerView mRecyclerView;

    @BindView(R.id.home_img_doLoans)
    ImageView doLoad;

    private RecyclerAdapter<String> mAdapter;


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);


        mBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(mContext)
                        .load(model)
                        .placeholder(R.mipmap.ic_empty_photo)
                        .error(R.mipmap.ic_empty_photo)
                        .dontAnimate()
                        .into(itemView);
            }
        });
        List<String> banners = new ArrayList<>();
        banners.add("http://www.sxpft.com.cn//assets//images//a99557d05ace8bf46ad863885d140101.png");
        banners.add("http://www.sxpft.com.cn//assets//images//7f5ee99dff02fd1e61daf2c3e8636437.jpg");
        banners.add("http://www.sxpft.com.cn//assets//images//a99557d05ace8bf46ad863885d140101.png");
        mBanner.setData(banners, Arrays.asList("", "", ""));

        /*--精品推荐--*/
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter = new RecyclerAdapter<String>() {
            @Override
            protected int getItemViewType(int position, String s) {
                return R.layout.item_recommend_rlist;
            }

            @Override
            protected ViewHolder<String> onCreateViewHolder(View root, int viewType) {
                return new ItemHolder(root);
            }
        });
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));

    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter.add("", "", "", "", "");
    }

    /**
     * 精品推荐
     */
    class ItemHolder extends RecyclerAdapter.ViewHolder<String> {

        public ItemHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(String s) {

        }
    }

    @OnClick(R.id.home_img_doLoans)
    public void doloans() {
        App.showToast("去贷款");

        ApplyLoansActivity.runActivity(mContext,ApplyLoansActivity.class);


    }

    @Override
    public void onResume() {
        super.onResume();
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }
}
