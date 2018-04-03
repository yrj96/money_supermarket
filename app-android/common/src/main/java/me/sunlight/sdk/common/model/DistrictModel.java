package me.sunlight.sdk.common.model;

import java.io.Serializable;

public class DistrictModel implements Serializable {
    private String name;
    private String id;
    private String zipcode;

    public DistrictModel() {
        super();
    }

    public DistrictModel(String name, String id, String zipcode) {
        super();
        this.name = name;
        this.id = id;
        this.zipcode = zipcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "DistrictModel{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}