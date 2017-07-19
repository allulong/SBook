package com.logn.sbook.beans;

/**
 * Created by long on 2017/7/17.
 */

public class Book {

    /**
     * book_id : 2
     * user_id : 1
     * title : ?????????????
     * author : Thomas Pittman James Peters
     * publisher : ???????
     * price_prime : 55.00?
     * price_present : 2
     * num : 2
     * type : ???
     * level : ??
     * msg : ghghg
     * images : https://img1.doubanio.com/lpic/s4114769.jpg
     * isbn13 : 9787111288107
     */

    private int book_id;
    private int user_id;
    private String title;
    private String author;
    private String publisher;
    private String price_prime;
    private String price_present;
    private int num;
    private String type;
    private String level;
    private String msg;
    private String images;
    private String isbn13;
    private String create_time;

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPrice_prime() {
        return price_prime;
    }

    public void setPrice_prime(String price_prime) {
        this.price_prime = price_prime;
    }

    public String getPrice_present() {
        return price_present;
    }

    public void setPrice_present(String price_present) {
        this.price_present = price_present;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
