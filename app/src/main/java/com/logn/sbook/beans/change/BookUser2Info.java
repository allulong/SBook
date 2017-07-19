package com.logn.sbook.beans.change;

import com.logn.sbook.R;
import com.logn.sbook.beans.Book;
import com.logn.sbook.beans.BookInfo;
import com.logn.sbook.beans.BookWithUser;
import com.logn.sbook.beans.User;

/**
 * Created by long on 2017/7/17.
 */

public class BookUser2Info {
    public static BookInfo getBookInfo(BookWithUser bu) {
        Book book = bu.getBook();
        User user = bu.getUser();
        BookInfo info = new BookInfo();
        info.setId(book.getBook_id());
        info.setUserId(user.getId());
        info.setISBN(Long.parseLong(book.getIsbn13()));
        info.setUserImageId(R.mipmap.icon_head_image);//默认
        info.setSexImageId(user.getSex().equals("M") ? R.mipmap.icon_male : R.mipmap.icon_female);
        info.setUserName(user.getUsername());
        info.setUserAddress(user.getAddress());
        //图书封面还没设置
        info.setBookImageId(R.mipmap.icon_book_lock);
        info.setBookName(book.getTitle());
        info.setAuthor(book.getAuthor());
        info.setNewPrice(book.getPrice_present());
        info.setOldPrice(book.getPrice_prime());
        info.setDate(book.getCreate_time());
        info.setQuality(book.getLevel());
        info.setPublisher(book.getPublisher());
        info.setBookNumber(book.getNum() + "");
        info.setUserContact(book.getMsg());
        info.setPhone(user.getPhone());
        return info;
    }
}
