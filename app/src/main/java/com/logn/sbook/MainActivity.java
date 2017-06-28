package com.logn.sbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.GridView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {
    private SearchView searchView;
    private ViewFlipper viewFlipper;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        implSearchView();
        autoPlay();
    }
    //搜索框
    public void implSearchView(){
        searchView= (SearchView) findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("书名");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
}
    //Display自动播放图片
    public void autoPlay(){
        viewFlipper= (ViewFlipper) findViewById(R.id.displayView);
        viewFlipper.setInAnimation(this,R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,R.anim.slide_out_right);
        viewFlipper.startFlipping();
    }
}
