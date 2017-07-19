package com.logn.sbook.beans;

/**
 * Created by long on 2017/7/17.
 */

public class User {
    /**
     * id : 1
     * username : long
     * password : lujs
     * head_image :
     * sex : F
     * phone : 10086
     * address : ??
     * qq : 123456
     * email : 1@qq.com
     */

    private int id;
    private String username;
    private String password;
    private String head_image;
    private String sex;
    private String phone;
    private String address;
    private String qq;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        String r = "id:" + id;
        return super.toString();
    }
}
