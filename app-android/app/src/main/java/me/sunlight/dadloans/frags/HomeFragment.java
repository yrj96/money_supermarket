package me.sunlight.dadloans.frags;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import me.sunlight.dadloans.R;
import me.sunlight.sdk.common.app.Fragment;

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
    }
}
