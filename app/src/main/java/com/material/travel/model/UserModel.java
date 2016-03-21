package com.material.travel.model;

import java.io.Serializable;
import java.util.List;

public class UserModel {

    public User body;

    public static class User implements Serializable {
        private static final long serialVersionUID = -912571012359295081L;
        public String username;
        public String password;//
        public String nickname;//
        public String gender;
        public String mobile;
        public List<Item> items;

        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

    public static class Item {
        public int id;
    }

}

