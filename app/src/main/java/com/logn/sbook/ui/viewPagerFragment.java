package com.logn.sbook.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.logn.sbook.R;
import com.logn.sbook.beans.BookInfo;
import com.logn.sbook.beans.BookWithUser;
import com.logn.sbook.beans.StatusSearch;
import com.logn.sbook.beans.User;
import com.logn.sbook.beans.change.BookUser2Info;
import com.logn.sbook.util.BookAdapter;
import com.logn.sbook.util.FileUtil;
import com.logn.sbook.util.ImageRunnable;
import com.logn.sbook.util.SearchRunnable;
import com.logn.sbook.util.SimpleItemDecoration;
import com.logn.sbook.util.SpUtils;
import com.logn.sbook.util.SpValue;
import com.logn.sbook.util.viewPagerAdapter;
import com.shizhefei.fragment.LazyFragment;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by banz on 2017/7/1.
 */

public class viewPagerFragment extends LazyFragment {
    public static final String INTENT_STRING_TABNAME = "intent_String_tabName";
    public static final String KIND_OF_BOOK = "intent_String_kindOfBook";
    private String tabName;
    private int position;
    private SearchView searchView;
    //    private ViewFlipper viewFlipper;
    private GridView gridView;
    //ViewPager
    private View viewpager1, viewpager2;
    private ViewPager viewPager;

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    //创建list，保存获得的数据
    private List<BookInfo> bookList = new ArrayList<>();
    private String[] kindsOfBooks = new String[]{
            "计算机", "小说", "题库", "考研", "其他"
    };
    private Context context;

    private boolean loginStatus = false;
    private int count = 0;

    private SwipeRefreshLayout swipeRefreshLayout;


    private LinearLayout llSale, llSetting;
    private Button btnLogin;

    private TextView tvUsername, tvAddress;
    private ImageView imgHead;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            if (msg.what == 101) {
                Toast.makeText(context, "未获取到数据", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            } else if (msg.what == 100) {
                String jsonData = msg.obj.toString();
                jsonData = URLDecoder.decode(jsonData);
                swipeRefreshLayout.setRefreshing(false);
                List<BookInfo> dataList = findData(jsonData);
                if (dataList == null) {
                    return;
                }
                bookAdapter.setmBookList(dataList);
                bookAdapter.notifyDataSetChanged();
            } else if (msg.what == 1) {
                bookAdapter.notifyDataSetChanged();
            }
        }
    };

    private List<BookInfo> findData(String jsonData) {
        List<String> urls = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<BookInfo> bookInfos = new ArrayList<>();
        Log.e("findData", "" + jsonData);
        Gson gson = new Gson();
        List<BookWithUser> books;
        StatusSearch statusSearch = gson.fromJson(jsonData, StatusSearch.class);
        if (statusSearch.getStatus() != 200) {
            return null;
        }
        books = statusSearch.getBooks();
        for (BookWithUser bookWithUser : books) {
            //在这里获取图片
            urls.add(bookWithUser.getBook().getImages());
            names.add(bookWithUser.getBook().getTitle());

            BookInfo bookInfo = BookUser2Info.getBookInfo(bookWithUser);
            bookInfos.add(bookInfo);
        }
        ImageRunnable imageRunnable = new ImageRunnable(urls, names);
        imageRunnable.setHandler(handler);
        new Thread(imageRunnable).start();
        return bookInfos;
    }


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        tabName = getArguments().getString(INTENT_STRING_TABNAME);
        context = getContext();
        FileUtil.createDir("sbook");

        if (tabName == "首页") {
            setContentView(R.layout.activity_main);

            implSearchView();
            displayWithViewPager();
            kindsOfBooks();
            implSwipeRefresh();

            //实现recyclerview
//            initBook();
            initBookData();
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new SimpleItemDecoration(context, LinearLayoutManager.VERTICAL));
            bookAdapter = new BookAdapter(context, bookList);
            recyclerView.setAdapter(bookAdapter);
        } else if (tabName == "我") {
            setContentView(R.layout.activity_mine);
            initView();
        }

//        testJson();
    }

    private void testJson() {
        String json = "{\"id\":110}";
        Gson gson = new Gson();
        Log.e("testJson", "start");
        User user = gson.fromJson(json, User.class);
        Log.e("testJson", "end:"+user.toString());
    }

    private void initBookData() {
        String url = "http://c1y7502888.iok.la:23110/search";
        new Thread(new SearchRunnable(handler, url, "all")).start();
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        if (tabName == "我") {
            updateView();
        }
    }

    private void updateView() {
        if (hasLogin()) {
            if (count == 0) {
                change2Login();
            } else if (!loginStatus) {//如果未登录
                change2Login();
            }
        } else {
            if (count == 0) {
                change2Logout();
            } else if (loginStatus) {
                change2Logout();
            }
        }
        count++;
    }

    private void change2Login() {
        loginStatus = true;
        setEntry(true);
        btnLogin.setText("退出登录");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpUtils.clear(context);
                change2Logout();
            }
        });
    }

    private void change2Logout() {
        loginStatus = false;
        setEntry(false);
        btnLogin.setText("登录");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(context, LoginActivity.class);
                startActivity(login);
            }
        });
    }

    private void initView() {
        llSale = (LinearLayout) findViewById(R.id.ll_sale_mime);
        llSetting = (LinearLayout) findViewById(R.id.ll_setting_mime);
        btnLogin = (Button) findViewById(R.id.mime_login);
        tvUsername = (TextView) findViewById(R.id.mine_username);
        tvAddress = (TextView) findViewById(R.id.mine_useraddress);
        imgHead = (ImageView) findViewById(R.id.mine_userimage);
    }

    private void setEntry(boolean login) {
        if (login) {
            View.OnClickListener btnListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    switch (v.getId()) {
                        case R.id.ll_sale_mime:
                            intent.setClass(context, kindsOfBooksDetail.class);
                            intent.putExtra(KIND_OF_BOOK, "出售");
                            startActivity(intent);
                            break;
                        case R.id.ll_setting_mime:
                            intent.setClass(context, SettingActivity.class);
                            startActivity(intent);
                            break;
                    }
                }
            };

            llSale.setOnClickListener(btnListener);
            llSetting.setOnClickListener(btnListener);

            String username = SpUtils.get(context, SpValue.key_username, "张三");
            String address = SpUtils.get(context, SpValue.key_address, "中国北京");
            tvUsername.setText(username);
            tvAddress.setText(address);
        } else {
            View.OnClickListener btnListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
                }
            };

            llSale.setOnClickListener(btnListener);
            llSetting.setOnClickListener(btnListener);
            tvUsername.setText("姓名");
            tvAddress.setText("地址");
        }
    }


    private boolean hasLogin() {
        String time = SpUtils.get(context, SpValue.key_login_time, "0");
        if (time.equals("0")) {
            return false;
        }
        return true;
    }

    //从后台数据库获取Book数据-首页
    private void initBook() {
        //example for test
        for (int i = 0; i < 5; i++) {
            BookInfo bookInfo = new BookInfo();
            bookInfo.setAuthor("banz" + i);
            bookInfo.setBookImageId(R.mipmap.icon_book_lock);
            bookInfo.setBookName("哈利波特" + i);
            bookInfo.setDate("2017.6.28");
            bookInfo.setNewPrice("8.00");
            bookInfo.setOldPrice("15.00");
            bookInfo.setQuality("9");
            bookInfo.setUserAddress("SSDUT");
            bookInfo.setSexImageId(Math.random() > 0.5 ? R.mipmap.icon_male : R.mipmap.icon_female);
            bookInfo.setUserImageId(R.mipmap.icon_head_image);
            bookInfo.setUserName("banz");
            bookInfo.setISBN(000000);
            bookInfo.setPublisher("SSDUT");
            bookInfo.setUserContact("DalianSSDUT");
            bookInfo.setBookNumber("1");
            bookList.add(bookInfo);
        }
    }

    //搜索框-首页
    public void implSearchView() {
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);

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

    //实现下拉刷新
    public void implSwipeRefresh() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.cyan);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                initBookData();
            }
        });
    }

    //实现ViewPager-首页
    public void displayWithViewPager() {
        viewPagerAdapter viewPagerAdapter = new viewPagerAdapter();
        viewPager = (ViewPager) findViewById(R.id.displayView);

        viewpager1 = inflater.inflate(R.layout.viewpager1, null);
        viewpager2 = inflater.inflate(R.layout.viewpager2, null);
        viewPagerAdapter.viewList = new ArrayList<>();
        viewPagerAdapter.viewList.add(viewpager1);
        viewPagerAdapter.viewList.add(viewpager2);
        viewPager.setAdapter(viewPagerAdapter);
    }

    //GridView-书的分类-首页
    public void kindsOfBooks() {
        gridView = (GridView) findViewById(R.id.gridView);
        int[] imageIds = new int[]{
                R.drawable.computer, R.drawable.novel, R.drawable.paperwork,
                R.drawable.kaoyan, R.drawable.other
        };

        ArrayList<HashMap<String, Object>> IsImageItem = new
                ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < imageIds.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", imageIds[i]);
            map.put("ItemText", kindsOfBooks[i]);
            IsImageItem.add(map);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(context,
                IsImageItem, R.layout.item_gridview,
                new String[]{"ItemImage", "ItemText"},
                new int[]{R.id.itemGridimage, R.id.itemGridText});
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(new viewPagerFragment.ItemClickListener());
    }

    //实现gridView的点击事件-首页
    class ItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent,
                                View view, int position, long id) {
            Intent intent = new Intent(context, kindsOfBooksDetail.class);
            intent.putExtra(KIND_OF_BOOK, kindsOfBooks[position]);
            startActivity(intent);
        }
    }
}
