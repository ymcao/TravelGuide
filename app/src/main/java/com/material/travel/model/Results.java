package com.material.travel.model;

/**
 * Created by caoyamin on 15/9/16.
 */
public class Results {

    public int errNum;
    public String retMsg;
    public Data retData;
    public static class Data{
        public String sex;
        public String birthday;
        public String address;
    }
}
