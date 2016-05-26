package com.hynl.weibo.hynlentity;

/**
 * Created by tt6000 on 2016/5/8.
 */
public class LocaGeo extends BaseEntity {
            public float longitude;
            public float latitude;
            public int city;
            public int province;
            public String city_name;
            public String province_name;
            public String address;

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public int getProvince() {

        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public float getLongitude() {

        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {

        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getCity_name() {

        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getCity() {

        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
