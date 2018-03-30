package me.sunlight.sdk.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.List;

import me.sunlight.sdk.common.R;
import me.sunlight.sdk.common.model.AreaModel;
import me.sunlight.sdk.common.model.CityModel;
import me.sunlight.sdk.common.model.DistrictModel;
import me.sunlight.sdk.common.model.ProvinceModel;
import me.sunlight.sdk.common.util.ACache;
import me.sunlight.sdk.common.widget.dialog.adapter.ArrayWheelAdapter;
import me.sunlight.sdk.common.widget.dialog.listener.OnWheelChangedListener;

public class WheelViewDialog implements View.OnClickListener, OnWheelChangedListener {
    private Context context;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private ImageView mBtnConfirm, mBtnCancle;
    private Display display;
    private Dialog dialog;
    /**
     * ����ʡ
     */
    protected String[] mProvinceDatas;
    protected List<ProvinceModel> mProvinceDataList;
    protected List<CityModel> mCityDataList;
    protected List<DistrictModel> mDistrictDataList;
    /*
    * 当前选中的省市区
    * */
    ProvinceModel mCurrentProvice = new ProvinceModel();
    CityModel mCurrentCity = new CityModel();
    DistrictModel mCurrentDistrict = new DistrictModel();
    /*
    * 默认选中的id
    * */
    private String proviceid, cityid, districtid;
    public SelectaddressListener selectlistener;

    public WheelViewDialog(Context context, SelectaddressListener selectlistener, String proviceid, String cityid, String districtid) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        this.selectlistener = selectlistener;
        this.proviceid = proviceid;
        this.cityid = cityid;
        this.districtid = districtid;
    }

    public WheelViewDialog builder() {
        // ��ȡDialog����
        View view = LayoutInflater.from(context).inflate(
                R.layout.toast_view_wheelview, null);
        setUpViews(view);
        setUpListener();
        setUpData();
        // ����Dialog��С���Ϊ��Ļ���
        view.setMinimumWidth(display.getWidth());
        // ����Dialog���ֺͲ���
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);

        return this;
    }

    public WheelViewDialog setTitle(String title) {
//		txt_title.setVisibility(View.VISIBLE);
//		txt_title.setText(title);
        return this;
    }

    public WheelViewDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public WheelViewDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public boolean isShow() {
        return dialog.isShowing();
    }

    public void show() {
        dialog.show();
    }

    public void dimss() {
        dialog.dismiss();
    }

    private void setUpViews(View view) {
        mViewProvince = (WheelView) view.findViewById(R.id.id_province);
        mViewCity = (WheelView) view.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) view.findViewById(R.id.id_district);
        mBtnConfirm = (ImageView) view.findViewById(R.id.btn_confirm);
        mBtnCancle = (ImageView) view.findViewById(R.id.btn_cancle);
    }

    private void setUpListener() {
        // ���change�¼�
        mViewProvince.addChangingListener(this);
        // ���change�¼�
        mViewCity.addChangingListener(this);
        // ���change�¼�
        mViewDistrict.addChangingListener(this);
        // ���onclick�¼�
        mBtnConfirm.setOnClickListener(this);
        mBtnCancle.setOnClickListener(this);
    }

    private void setUpData() {
        /**
         *  此处要修改，取值
         */
        mProvinceDataList = ((AreaModel) ACache.get(context).getAsObject("area")).getprovinceList();
        mProvinceDatas = new String[mProvinceDataList.size()];
        for (int i = 0; i < mProvinceDataList.size(); i++) {
            mProvinceDatas[i] = mProvinceDataList.get(i).getName();
        }
        if (mProvinceDatas == null) {
            mProvinceDatas = new String[]{""};
        }
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(context, mProvinceDatas));
        // ���ÿɼ���Ŀ����
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
        Log.i("=======", "gg" + proviceid + cityid + districtid);
        for (int i = 0; i < mProvinceDataList.size(); i++) {
            if (mProvinceDataList.get(i).getId().equals(proviceid)) {
                mViewProvince.setCurrentItem(i);
                Log.i("=======", "gg" + i);
                mCityDataList = mProvinceDataList.get(i).getCityList();
                for (int j = 0; j < mCityDataList.size(); j++) {
                    if (mCityDataList.get(j).getId().equals(cityid)) {
                        mViewCity.setCurrentItem(j);
                        mDistrictDataList = mCityDataList.get(j).getDistrictList();
                        if (mDistrictDataList.size() != 0) {
                            for (int k = 0; k < mDistrictDataList.size(); k++) {
                                if (mDistrictDataList.get(k).getId().equals(districtid)) {
                                    mViewDistrict.setCurrentItem(k);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        int dCurrent = mViewDistrict.getCurrentItem();
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            if (mDistrictDataList.size() != 0) {
                mCurrentDistrict = mDistrictDataList.get(dCurrent);
            } else {
                mCurrentDistrict = new DistrictModel("", "", "");
            }
//            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    private void updateAreas() {
        int cCurrent = mViewCity.getCurrentItem();
//        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent].getId();
        mDistrictDataList = mCityDataList.get(cCurrent).getDistrictList();
        mCurrentCity = mCityDataList.get(cCurrent);
        List<DistrictModel> areaList = mCityDataList.get(cCurrent).getDistrictList();
        String[] areas = new String[areaList.size()];
        for (int i = 0; i < areaList.size(); i++) {
            areas[i] = areaList.get(i).getName();
        }
        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(context, areas));
        mViewDistrict.setCurrentItem(0);
        if (mDistrictDataList.size() != 0) {
            mCurrentDistrict = mDistrictDataList.get(0);
        } else {
            mCurrentDistrict = new DistrictModel("", "", "");
        }
//        mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
    }

    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
//        mCurrentProviceName = mProvinceDatas[pCurrent].getName();
        mCityDataList = mProvinceDataList.get(pCurrent).getCityList();
        mCurrentProvice = mProvinceDataList.get(pCurrent);
        List<CityModel> citieList = mProvinceDataList.get(pCurrent).getCityList();
        String[] cities = new String[citieList.size()];
        for (int i = 0; i < citieList.size(); i++) {
            cities[i] = citieList.get(i).getName();
        }
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(context, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    @Override
    public void onClick(View v) {

        // 确认
        if(v.getId() == R.id.btn_confirm){

            if (mCurrentDistrict.getId().equals("")) {
                return;
            }
            if (selectlistener != null) {
                selectlistener.selectaddress(mCurrentProvice, mCurrentCity, mCurrentDistrict);
                dialog.dismiss();
            }

        }

        // 取消
        if(v.getId() == R.id.btn_cancle){
            dialog.dismiss();
        }

//        switch (v.getId()) {
//            case R.id.btn_confirm:
//                if (mCurrentDistrict.getId().equals("")) {
//                    return;
//                }
//                if (selectlistener != null) {
//                    selectlistener.selectaddress(mCurrentProvice, mCurrentCity, mCurrentDistrict);
//                    dialog.dismiss();
//                }
//                break;
//            case R.id.btn_cancle:
//                dialog.dismiss();
//                break;
//            default:
//                break;
//        }
    }

    private void showSelectedResult() {
//        Toast.makeText(context, "你选择的是" + mCurrentProviceName + "," + mCurrentCityName + ","
//                + mCurrentDistrictName + "," + mCurrentZipCode, Toast.LENGTH_SHORT).show();
    }


    public interface SelectaddressListener {
        void selectaddress(ProvinceModel mCurrentProvice, CityModel mCurrentCity, DistrictModel mCurrentDistrict);
    }
}