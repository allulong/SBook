package com.logn.sbook.gsonforbook;

import android.provider.MediaStore;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by oureda on 2017/7/6.
 */

public class BookGson {
    public Rating rating;
    public String subtitle;


    public String[] author;

    public String pubdate;

    @SerializedName("tags")
    public List<Tags> tagsList;

    public String origin_title;
    public String image;
    public String binding;


    public String[] translator;

    public String catalog;
    public String pages;
    public Images images;
    public String alt;

    @SerializedName("id")
    public String bookId;

    public String publisher;
    public String isbn10;
    public String isbn13;
    public String title;
    public String url;
    public String alt_title;
    public String author_intro;
    public String summary;
    public String price;

}
