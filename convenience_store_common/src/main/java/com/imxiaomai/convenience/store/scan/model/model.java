package com.imxiaomai.convenience.store.scan.model;

/**
 * Created by Administrator on 2016/3/13.
 */
public class model {

    private int a;
    private String str;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public model(int a) {
        this.a = a;
    }

    public model(int a, String str) {
        this.a = a;
        this.str = str;
    }

}
