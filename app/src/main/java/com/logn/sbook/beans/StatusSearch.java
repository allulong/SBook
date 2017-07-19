package com.logn.sbook.beans;

import java.util.List;

/**
 * Created by long on 2017/7/17.
 */

public class StatusSearch {
    private int status;
    private List<BookWithUser> books;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<BookWithUser> getBooks() {
        return books;
    }

    public void setBooks(List<BookWithUser> books) {
        this.books = books;
    }
}
