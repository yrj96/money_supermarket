package me.sunlight.sdk.common.widget.bottombar.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.sunlight.sdk.common.R;
import me.sunlight.sdk.common.util.AppUtil;

/**
 * Created by gechuanguang on 2017/4/28.
 * 邮箱：1944633835@qq.com
 *
 * @function 底部导航栏
 */
public class BottomTab extends LinearLayout implements View.OnClickListener {

    private Context mContext;

    /**
     * bottomTab 宽高
     */
    private int width;
    private int height;
    private int defaultWidth;
    private int defaultHeight = 56;

    /**
     * bottomBar 默认的一些值
     */
    private String defaultTextColor = "";
    private String defaultTextSize = "";


    // 工具类
    AppUtil Util;

    private LayoutInflater mInflater;

    /**
     * 点击事件监听
     */
    onTabItemClickListener mOnTabItemClickListener;

    private int size = 0;


    /**
     * 收集tabs 数据集
     */
    private List<TabModel> mtabs = new ArrayList<>();

    /**
     * 收集tabView 数据集
     */
    private List<View> mTabViews = new ArrayList<>();

    public BottomTab(Context context) {
        this(context, null);
    }

    public BottomTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        Util = new AppUtil(mContext);
        defaultWidth = Util.getScreenWidth();
        this.setOrientation(LinearLayout.HORIZONTAL);

        mInflater = LayoutInflater.from(mContext);
    }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        width = (widthMode == MeasureSpec.AT_MOST ? defaultWidth : widthSize);
//        height = (heightMode == MeasureSpec.AT_MOST ? Util.dip2px(defaultHeight) : heightSize);
//
//        setMeasuredDimension(width, height);
//
//    }

    private int normalTextcolor;
    private int selectedTextColor;

    /**
     * 基础颜色的设置
     */
    public void initTab(int normalTextcolor, int selectedTextColor) {
        this.normalTextcolor = normalTextcolor;
        this.selectedTextColor = selectedTextColor;
    }

    /**
     * 添加tab
     */
    public BottomTab addTab(TabModel model) {
        if (model.getNormalTextcolor() == 0 && normalTextcolor != 0) {
            model.setNormalTextcolor(normalTextcolor);
        }

        if (model.getSelectedTextColor() == 0 && selectedTextColor != 0) {
            model.setSelectedTextColor(selectedTextColor);
        }

        mtabs.add(model);
        View tableItemView = mInflater.inflate(R.layout.tab_base_item, null);
        ImageView imageView = tableItemView.findViewById(R.id.item_tab_img);
        TextView textView = tableItemView.findViewById(R.id.item_tab_text);

        if(model.getTabNormalIcon()<0){
            textView.setText(model.getTabText());
        }else {
            imageView.setImageResource(model.getTabNormalIcon());
            textView.setText(model.getTabText());
        }

        LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
        params.gravity = Gravity.CENTER_VERTICAL;

        // 给view设置一个标签
        tableItemView.setTag(size);
        tableItemView.setOnClickListener(this);
        mTabViews.add(tableItemView);

        addView(tableItemView, params);
        size++;

        // 默认选中第一个
        updataTab(0);

        return this;
    }


    /**
     * 设置点击事件
     */
    public void setOnTabItemClickListener(onTabItemClickListener tabItemClickListener) {
        mOnTabItemClickListener = tabItemClickListener;
    }

    /**
     * 底部按钮点击事件
     */
    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        if (mOnTabItemClickListener != null) {

            mOnTabItemClickListener.onTabItemClick(position);
            updataTab(position);
        }

    }

    /**
     * 更新对应position tab的状态
     *
     * @param position
     */
    public void updataTab(int position) {
        setAllTabNormal();
        View view;
        ImageView tabImg;
        TextView tabText;

        view = mTabViews.get(position);
        tabImg = view.findViewById(R.id.item_tab_img);
        tabText = view.findViewById(R.id.item_tab_text);

        tabImg.setImageResource(mtabs.get(position).getSelectedIcon());

        tabText.setTextColor(mContext.getResources().getColor(mtabs.get(position).getSelectedTextColor()));
    }

    /**
     * 将所以的tab都设置成灰色
     */
    private void setAllTabNormal() {
        for (int i = 0; i < mTabViews.size(); i++) {
            View view = mTabViews.get(i);

            ImageView tabImg = view.findViewById(R.id.item_tab_img);
            TextView tabText = view.findViewById(R.id.item_tab_text);

            if(mtabs.get(i).getTabNormalIcon()>0){
                tabImg.setImageResource(mtabs.get(i).getTabNormalIcon());
                tabText.setText(mtabs.get(i).getTabText());
            }


            if (mtabs.get(i).getNormalTextcolor() != 0) {
                tabText.setTextColor(mtabs.get(i).getNormalTextcolor());

            }
        }
    }

    public interface onTabItemClickListener {
        void onTabItemClick(int positon);
    }
}













