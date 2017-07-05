package com.logn.sbook.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.logn.sbook.R;
import com.logn.sbook.beans.BookInfo;
import com.logn.sbook.ui.BookDetailInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by oureda on 2017/6/28.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<BookInfo> mBookList;
    Context context;

    public BookAdapter(Context context, List<BookInfo> bookInfoList) {
        this.context = context;
        mBookList = bookInfoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.bookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                BookInfo bookInfo = mBookList.get(position);
//                Toast.makeText(v.getContext(), bookInfo.getBookName() + "",
//                        Toast.LENGTH_SHORT).show();
                Intent intent=new Intent("com.logn.sbook.ui.BookDetailInfo");
                intent.putExtra("bookInfo", (Serializable) bookInfo);
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BookInfo bookInfo = mBookList.get(position);
        holder.userName.setText(bookInfo.getUserName());
        holder.userSex.setImageResource(bookInfo.getSexImageId());
        holder.userImage.setImageResource(bookInfo.getUserImageId());
        holder.userAddress.setText(bookInfo.getUserAddress());
        holder.bookImage.setImageResource(bookInfo.getBookImageId());
        holder.bookName.setText(bookInfo.getBookName());
        holder.author.setText(bookInfo.getAuthor());
        holder.newPrice.setText(bookInfo.getNewPrice());
        holder.oldPrice.setText(bookInfo.getOldPrice());
        holder.date.setText(bookInfo.getDate());
        holder.qulity.setText(bookInfo.getQuality());

    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View bookView;
        ImageView userImage;
        ImageView userSex;
        TextView userName;
        TextView userAddress;
        ImageView bookImage;
        TextView bookName;
        TextView author;
        TextView newPrice;
        TextView oldPrice;
        TextView date;
        TextView qulity;

        public ViewHolder(View itemView) {
            super(itemView);
            bookView = itemView;
            userImage = (ImageView) itemView.findViewById(R.id.user_ImageId);
            userSex = (ImageView) itemView.findViewById(R.id.sex_user);
            userName = (TextView) itemView.findViewById(R.id.user_Name);
            userAddress = (TextView) itemView.findViewById(R.id.user_Address);
            bookImage = (ImageView) itemView.findViewById(R.id.book_ImageId);
            bookName = (TextView) itemView.findViewById(R.id.book_name);
            author = (TextView) itemView.findViewById(R.id.author);
            newPrice = (TextView) itemView.findViewById(R.id.price_new);
            oldPrice = (TextView) itemView.findViewById(R.id.price_old);
            date = (TextView) itemView.findViewById(R.id.date);
            qulity = (TextView) itemView.findViewById(R.id.qulity_book);
        }

    }
}
