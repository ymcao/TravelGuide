package com.material.travel.model;

/**
 * Created by caoyamin on 15/10/15.
 */
public class Goods {
    private int id;
    private String name;
    private double price;
    private int leftTime;
    private int type;

    public Goods(int id,String name,double price,int leftTime,int type){
        this.id=id;
        this.name=name;
        this.price=price;
        this.leftTime=leftTime;
        this.type=type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(int leftTime) {
        this.leftTime = leftTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
