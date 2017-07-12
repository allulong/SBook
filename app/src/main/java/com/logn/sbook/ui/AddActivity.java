package com.logn.sbook.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.logn.sbook.R;
//import com.logn.sbook.beans.BookNature;
import com.logn.sbook.gsonforbook.BookGson;
import com.logn.sbook.util.HttpUtil;
import com.logn.titlebar.TitleBar;
import com.soundcloud.android.crop.Crop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Vivian on 2017/7/1.
 */

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    //    private ImageButton imageButton_return;
    private EditText editText_isbn;
    private ImageButton imageButton_isbn_search;
    private ImageButton imageButton_isbn_qrcode;
    private ImageButton imageButton_book_look;
    private EditText editText_book_name;
    private EditText editText_book_author;
    private EditText editText_book_publisher;
    private EditText editText_book_oldprice;
    private EditText editText_book_newprice;
    private EditText editText_book_number;
    private EditText editText_remark;
    private Button choose_kind;
    private Button choose_qulity;
    TitleBar add_return;

    private static Dialog mCameraDialog;

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    public static final int BOOK_KIND = 3;
    public static final int BOOK_QULITY = 4;
    private static Uri imageUri;

    boolean Start_QRCode = false;
    private String QRCode_Result;
    boolean IsShowDialog = true;

    private String[] kindsBooks = new String[]{
            "计算机", "小说", "题库", "考研", "其他"
    };
    //    private List<BookNature> kindsOfBooks=new ArrayList<>();
    private String[] qulityBooks = new String[]{
            "全新", "9成新", "8成新", "7成新", "6成新"
    };
//    private List<BookNature> qulityOfBooks=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initWidget();
//        initBookNature();
        add_return.setOnTitleClickListener(listener);

        imageButton_book_look.setOnClickListener(this);
        imageButton_isbn_search.setOnClickListener(this);
        imageButton_isbn_qrcode.setOnClickListener(this);
        choose_kind.setOnClickListener(this);
        choose_qulity.setOnClickListener(this);
    }

//    public void initBookNature(){
//        for (int i=0;i<kindsBooks.length;i++){
//            kindsOfBooks.add(new BookNature(kindsBooks[i]));
//        }
//        for (int j=0;j<qulityBooks.length;j++){
//            qulityOfBooks.add(new BookNature(qulityBooks[j]));
//        }
//    }

    public void initWidget() {
//        imageButton_return = (ImageButton) findViewById(R.id.button_return);
        add_return = (TitleBar) findViewById(R.id.add_titlebar);
        editText_isbn = (EditText) findViewById(R.id.isbn_edit);
        imageButton_isbn_search = (ImageButton) findViewById(R.id.isbn_search);
        imageButton_isbn_qrcode = (ImageButton) findViewById(R.id.isbn_qrcode);
        imageButton_book_look = (ImageButton) findViewById(R.id.add_book_image);
        editText_book_name = (EditText) findViewById(R.id.add_book_name);
        editText_book_author = (EditText) findViewById(R.id.add_book_author);
        editText_book_publisher = (EditText) findViewById(R.id.add_book_publisher);
        editText_book_oldprice = (EditText) findViewById(R.id.add_book_oldprice);
        editText_book_newprice = (EditText) findViewById(R.id.add_book_newprice);
        editText_book_number = (EditText) findViewById(R.id.add_book_number);
        editText_remark = (EditText) findViewById(R.id.add_book_publisher);
        choose_kind = (Button) findViewById(R.id.add_book_kind);
        choose_qulity = (Button) findViewById(R.id.add_book_qulity);
//        imageButton_return.setOnClickListener(this);
    }

    //    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.button_return:
//                Intent intent=new Intent(AddActivity.this,GuideActivity.class);
//                startActivity(intent);
//                finish();
//                break;
//        }
//    }
    TitleBar.OnTitleClickListener listener = new TitleBar.OnTitleClickListener() {
        @Override
        public void onLeftClick() {
            Intent intent = new Intent(AddActivity.this, GuideActivity.class);
            startActivity(intent);
            finish();
        }

        @Override
        public void onRightClick() {

        }

        @Override
        public void onTitleClick() {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_book_image:
                if (IsShowDialog) {
                    showDialog(AddActivity.this);
                }

                break;
            case R.id.isbn_search:
                requestBookJSON();

                break;
            case R.id.isbn_qrcode:
                Start_QRCode = true;
                IntentIntegrator intentIntegrator = new IntentIntegrator(AddActivity.this);
                intentIntegrator.initiateScan();
                break;
            case R.id.add_book_kind:
                Intent bookKind = new Intent(AddActivity.this, ChooseKindOrQulity.class);
                bookKind.putExtra("BookInfo", kindsBooks);
                startActivityForResult(bookKind, BOOK_KIND);
                Log.d("BOOKKIND", "Launch chooseKindOrQulity----------");
                break;
            case R.id.add_book_qulity:
                Intent bookQulity = new Intent(AddActivity.this, ChooseKindOrQulity.class);
                bookQulity.putExtra("BookInfo", qulityBooks);
                startActivityForResult(bookQulity, BOOK_QULITY);
                Log.d("BOOKQULITY", "Launch chooseKindOrQulity----------");
                break;

        }
    }

    //请求书籍JSON数据
    public void requestBookJSON() {
        if ((editText_isbn.getText().toString().length() == 10 ||
                editText_isbn.getText().toString().length() == 13) &&
                checkISBN13(editText_isbn.getText().toString())){

            String isbnUri = "https://api.douban.com/v2/book/isbn/" + editText_isbn.getText().toString();
            HttpUtil.sendOkHttpRequest(isbnUri, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText = response.body().string();
                    String responseFinal = "{\"HeBook\":[" + responseText + "]}";

                    Log.d("responseFinal", responseFinal);
                    final BookGson bookGson = parseJSONWithGSON(responseFinal);
                    Log.d("TAG", String.valueOf(bookGson));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (bookGson != null) {
                                //执行显示操作
//                            Intent intent=new Intent(AddActivity.this,AddActivity.class);
//                            startActivity(intent);
                                Log.d("bookGson", String.valueOf(bookGson));
                                showBookInfoFromJSON(bookGson);


                            } else {
                                Toast.makeText(AddActivity.this, "获取信息失败", Toast
                                        .LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            });
        } else {
            Toast.makeText(AddActivity.this, "请检查您的ISBN是否正确(暂不支持10位ISBN号)", Toast.LENGTH_SHORT).show();
        }
    }

    //判断编号是否为ISBN号
    private static boolean checkISBN13(String str_ISBN) {
        //不满足978开头的13位数字这返回false
        if (!Pattern.matches("978[0-9]{10}", str_ISBN)) {
            return false;
        }
        int sumOdd = 0;//奇数位总和
        int sumEvn = 0;//偶数位总和
        for (int i = 0; i < str_ISBN.length() - 1; i++) {
            if (i % 2 == 0 == false) {
                sumOdd += str_ISBN.charAt(i) - 48;//奇数位
            } else {
                sumEvn += str_ISBN.charAt(i) - 48;//偶数位
            }
        }
        //奇数位总和自乘3
        sumOdd *= 3;
        int num13 = 10 - (sumOdd + sumEvn) % 10;
        num13 %= 10;
        if ((num13 + 48) == str_ISBN.charAt(str_ISBN.length() - 1)) {
            return true;
        } else {
            return false;
        }
    }

    // 获取某位的ISBN字符---isbn10
    private static int GetISBNAt(String isbn, int index, boolean xEnable) {
        char[] c = isbn.substring(index, 1).toCharArray();

        int n = c[0] - '0';

        if (n < 0 || n > 9) {
            if (xEnable && (c[0]== 'X' || c[0] == 'x')) {
                n = 10;
            }
        }

        return n;
    }

    // 检查ISBN号码--isbn10
    // 7687687787
    public static boolean CheckISBN10(String isbn) {
        try {
            int checkNum = 0;

            if (isbn.length() == 10) {
                int start = 10;

                int total = 0;

                for (int i = 0; i < isbn.length() - 1; ++i) {
                    total += GetISBNAt(isbn, i, false) * start--;
                }

                checkNum = total % 11;

                if (checkNum > 0) {
                    checkNum = 11 - checkNum;
                }
            } else {
                int total = 0;

                for (int i = 0; i < isbn.length() - 1; ++i) {
                    total += GetISBNAt(isbn, i, false) * (i % 2 == 0 ? 1 : 3);
                }

                checkNum = total % 10;

                if (checkNum > 0) {
                    checkNum = 10 - checkNum;
                }
            }

            return GetISBNAt(isbn, isbn.length() - 1, true) == checkNum;
        } catch (Exception e)
        {
            return false;
        }

    }

    //将解析的数据显示在界面中
    public void showBookInfoFromJSON(BookGson bookGson) {
        String title = bookGson.title;
        StringBuffer authorBuffer = new StringBuffer();
        for (int i = 0; i < bookGson.author.length; i++) {
            authorBuffer.append(bookGson.author[i] + " ");
        }


        String publisher = bookGson.publisher;
        String price = bookGson.price;


        editText_book_name.setText(title);
        editText_book_author.setText(authorBuffer);
        editText_book_publisher.setText(publisher);
        editText_book_oldprice.setText(price);
        loadBookImage(bookGson);
    }

    //GSON解析获得的json数据
    public BookGson parseJSONWithGSON(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("HeBook");
            String bookContent = jsonArray.getJSONObject(0).toString();
            Log.d("bookContent", bookContent);
            System.out.println("-------------------");
            return new Gson().fromJson(bookContent, BookGson.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
//        Log.d("TAG","z执行paresejsonwithgson");
//        Gson gson=new Gson();
//        BookGson bookGson=gson.fromJson(jsonData,BookGson.class);
//        return bookGson;

    }

    //通过Glide加载图书封面
    public void loadBookImage(BookGson bookGson) {
        final String requestBookImage = bookGson.images.large;
        HttpUtil.sendOkHttpRequest(requestBookImage, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(AddActivity.this).load(requestBookImage).
                                into(imageButton_book_look);
                    }
                });

            }
        });
    }

    //弹出对话框，选择图片获取
    public void showDialog(Context context) {

        mCameraDialog = new Dialog(context, R.style.my_dialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.layout_camera_control, null);
        root.findViewById(R.id.btn_open_camera).setOnClickListener(btnlistener);
        root.findViewById(R.id.btn_choose_img).setOnClickListener(btnlistener);
        root.findViewById(R.id.btn_cancel).setOnClickListener(btnlistener);
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = -20; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
//      lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度
//      lp.alpha = 9f; // 透明度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
        IsShowDialog = false;

    }

    View.OnClickListener btnlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_open_camera: // 打开照相机
                    takePhoto(AddActivity.this);
                    if (mCameraDialog != null) {
                        mCameraDialog.dismiss();
                        IsShowDialog = true;
                    }
                    break;
                // 打开相册
                case R.id.btn_choose_img:
//                    choosePictureFromAlbum(AddActivity.this);
                    Crop.pickImage(AddActivity.this);//图片选择器，回调onActivityResult
                    if (mCameraDialog != null) {
                        mCameraDialog.dismiss();
                        IsShowDialog = true;
                    }

                    break;
                // 取消
                case R.id.btn_cancel:
                    if (mCameraDialog != null) {
                        mCameraDialog.dismiss();
                        IsShowDialog = true;
                    }
                    break;
            }
        }
    };

    //打开照相机拍照获取图片
    public void takePhoto(Context context) {
        File outputImage = new File(context.getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(context, "com.logn.sbook.fileprovider",
                    outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //选择类型以及新旧的回调数据
        String BookItemInfo = data.getStringExtra("BookItem");
//        BookNature bookNature=new BookNature(BookItemInfo);
        //条形码扫描
        if (Start_QRCode) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode,
                    resultCode, data);
            if (scanResult != null) {
                QRCode_Result = scanResult.getContents();
//                Toast.makeText(AddActivity.this,QRCode_Result,Toast.LENGTH_SHORT).show();
                editText_isbn.setText(QRCode_Result);
                requestBookJSON();

            }
            Start_QRCode = false;
        }

        //图片裁剪
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(imageUri));
                        imageButton_book_look.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
//                if (resultCode == RESULT_OK) {
//                    if (Build.VERSION.SDK_INT >= 19) {
//                        //4.4及以上系统使用这个方法处理图片
//                        handleImageOnKitKat(data);
//                    } else {
//                        //4.4及以下系统使用这个方法处理图片
//                        handleImageBeforeKitKat(data);
//
//                    }
//                }

                break;
            case BOOK_KIND:
                choose_kind.setText(BookItemInfo);
                Log.d("ActivittForResultKind", "--------------");

                break;
            case BOOK_QULITY:
                choose_qulity.setText(BookItemInfo);
                Log.d("ActivittForResultQUL", "--------------");
                break;
            default:
                break;
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "output_image.jpg"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            imageButton_book_look.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //从相册中选择照片
    public void choosePictureFromAlbum(Context context) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }


    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(AddActivity.this, "You denied the permission",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(AddActivity.this, uri)) {
            //如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.document".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        selection);
            } else if ("com.android.providers.downloads.documents".
                    equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads")
                        , Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);

    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            }
            cursor.close();
        }
        return path;

    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageButton_book_look.setImageBitmap(bitmap);
        } else {
            Toast.makeText(AddActivity.this, "failed to get image", Toast.LENGTH_SHORT)
                    .show();
        }

    }

}
