package com.example.chemicalcoffee;

public class ObjProduct {
    private String nameObject;
    private int imgId;
    private int count;
    private float coast;

    public ObjProduct(String name, int imgId, int count, float coast){
        nameObject = name;
        this.imgId = imgId;
        this.count = count;
        this.coast = coast;
    }

    public String getNameObject() {
        return nameObject;
    }

    public int getImgId() {
        return imgId;
    }

    public int getCount() {
        return count;
    }

    public float getCoast() {
        return coast;
    }
}
