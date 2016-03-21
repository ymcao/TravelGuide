package com.material.travel.model;

import java.util.List;

/**
 * Created by caoyamin on 15/10/15.
 */
public class TravelInfo {

    public boolean ret;
    public int errcode;
    public String errmsg;
    public int ver;
    public Data data;

    public static class Data{
        public List<Book> books;
        public int count;
    }
    public static class Book{
        public String bookUrl;
        public String title;
        public String text;
        public String headImage;
        public String userName;
        public String userHeadImg;
        public String startTime;
        public int routeDays;
        public int bookImgNum;
        public int viewCount;
        public int likeCount;
        public int commentCount;
        public boolean elite;
    }
}
