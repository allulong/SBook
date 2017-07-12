package com.logn.sbook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.logn.sbook.R;

import com.logn.titlebar.TitleBar;

import java.util.List;

/**
 * Created by oureda on 2017/7/8.
 */

public class ChooseKindOrQulity extends FragmentActivity {
    TitleBar titleBar;
    private ListView listView;
//    private List<BookNature> BookInfoArr;
    private String[] BookInfoArr;
    public static String item=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_choose_kind_qulity);
        Log.d("CHOOSEKINDQULITY","Launch success------");
        titleBar= (TitleBar) findViewById(R.id.choose_kind_qulity);
        listView= (ListView) findViewById(R.id.listview_kind_qulity);

        BookInfoArr= getIntent().getStringArrayExtra("BookInfo");
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(
                ChooseKindOrQulity.this,android.R.layout.simple_list_item_1,BookInfoArr);


        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item= BookInfoArr[position];
                Intent Bookitem=new Intent();
                Bookitem.putExtra("BookItem",item);
                setResult(0,Bookitem);
                finish();
            }
        });
        titleBar.setOnTitleClickListener(listener);


    }
    TitleBar.OnTitleClickListener listener=new TitleBar.OnTitleClickListener() {
        @Override
        public void onLeftClick() {
            finish();
        }

        @Override
        public void onRightClick() {

        }

        @Override
        public void onTitleClick() {

        }
    };
}
