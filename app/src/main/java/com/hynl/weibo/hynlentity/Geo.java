package com.hynl.weibo.hynlentity;

import java.util.ArrayList;

/**
 * Created by tt6000 on 2016/5/8.
 */
public class Geo extends BaseEntity{
    public String type;
    public ArrayList<Float> coordinates = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Float> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Float> coordinates) {
        this.coordinates = coordinates;
    }
}
