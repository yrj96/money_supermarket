package me.sunlight.sdk.common.model;

import java.io.Serializable;
import java.util.List;

public class AreaModel implements Serializable {
    private List<ProvinceModel> provinceList;

    public List<ProvinceModel> getprovinceList() {
        return provinceList;
    }

    public void setprovinceList(List<ProvinceModel> provinceList) {
        this.provinceList = provinceList;
    }
}