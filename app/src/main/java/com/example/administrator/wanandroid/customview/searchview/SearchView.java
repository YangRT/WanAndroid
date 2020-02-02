package com.example.administrator.wanandroid.customview.searchview;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.wanandroid.R;


//带搜索记录的搜索框
public class SearchView extends LinearLayout {

    private Context context;
    private EditText searchEditText;
    private ImageView searchImage;
    private TextView clearHistory;
    private LinearLayout layout;

    private SearchHistoryListView listView;
    private BaseAdapter baseAdapter;

    private RecordSQLHelper helper ;
    private SQLiteDatabase db;

    private ICallBack iCallBack;
    private BCallBack bCallBack;

    private Float textSizeSearch;
    private int textColorSearch;
    private String textHintSearch;

    private float searchBlockHeight;
    private int searchBlockColor;

    private Boolean enterRun = false;
    public SearchView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAtrr(context,attrs);
        init();
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAtrr(context,attrs);
        init();
    }

    private void init(){
        initView();
        helper = new RecordSQLHelper(context);
        queryData("");

        /**
         * "清空搜索历史"按钮
         */
        clearHistory.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                queryData("");
            }
        });

        /**
         * 监听输入键盘更换后的搜索按键
         * 调用时刻：点击键盘上的搜索键时
         */
        searchEditText.setOnKeyListener(new OnKeyListener() {
            //返回值表示按键事件是否被处理，true表示已处理，回车键不再换行，false回车键换行
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if(searchEditText.getText().toString().equals("")){
                        Toast.makeText(context, "搜索不能为空", Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    if (!(iCallBack == null)){
                        iCallBack.searchAction(searchEditText.getText().toString());
                    }
                    Toast.makeText(context, "需要搜索的是" + searchEditText.getText(), Toast.LENGTH_SHORT).show();

                    // 2. 点击搜索键后，对该搜索字段在数据库是否存在进行检查（查询）->> 关注1
                    boolean hasData = hasData(searchEditText.getText().toString().trim());
                    // 3. 若存在，则不保存；若不存在，则将该搜索字段保存（插入）到数据库，并作为历史搜索记录
                    if (!hasData) {
                        insertData(searchEditText.getText().toString().trim());
                        queryData("");
                    }
                    searchEditText.setText("");
                    return true;
                }
                return false;
            }
        });


        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            // 输入文本后调用该方法
            @Override
            public void afterTextChanged(Editable s) {
                // 每次输入后，模糊查询数据库 & 显示
                String tempName = searchEditText.getText().toString();
                queryData(tempName);

            }
        });


        /**
         * 搜索记录列表（ListView）监听
         * 即当用户点击搜索历史里的字段后,会直接将结果当作搜索字段进行搜索
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 获取用户点击列表里的文字,并自动填充到搜索框内
                TextView textView =  view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                searchEditText.setText(name);
                Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
            }
        });


        searchImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchEditText.getText().toString().equals("")){
                    Toast.makeText(context, "搜索不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(iCallBack == null)){
                    iCallBack.searchAction(searchEditText.getText().toString());
                }
                Toast.makeText(context, "需要搜索的是" + searchEditText.getText(), Toast.LENGTH_SHORT).show();

                // 2. 点击搜索键后，对该搜索字段在数据库是否存在进行检查（查询）->> 关注1
                boolean hasData = hasData(searchEditText.getText().toString().trim());
                // 3. 若存在，则不保存；若不存在，则将该搜索字段保存（插入）到数据库，并作为历史搜索记录
                if (!hasData) {
                    insertData(searchEditText.getText().toString().trim());
                    queryData("");
                }
                searchEditText.setText("");
            }
        });

    }

    private void initAtrr(Context context, AttributeSet set){
        TypedArray typedArray = context.obtainStyledAttributes(set, R.styleable.SearchView);

        // 搜索框字体大小（dp）
        textSizeSearch = typedArray.getDimension(R.styleable.SearchView_textSizeSearch, 16);
        // 搜索框字体颜色（使用十六进制代码，如#333、#8e8e8e）
        int defaultColor = R.color.colorTheme; // 默认颜色 = 白色
        textColorSearch = typedArray.getColor(R.styleable.SearchView_textColorSearch, Color.WHITE);
        // 搜索框提示内容（String）
        textHintSearch = typedArray.getString(R.styleable.SearchView_textHintSearch);
        // 搜索框高度
        searchBlockHeight = typedArray.getDimension(R.styleable.SearchView_searchBlockHeight, 60);
        // 搜索框颜色
        searchBlockColor = typedArray.getColor(R.styleable.SearchView_searchBlockColor, Color.parseColor("#FCA019"));
        // 释放资源
        typedArray.recycle();

    }

    private void initView(){
        LayoutInflater.from(context).inflate(R.layout.search_view,this);
        searchEditText = findViewById(R.id.edit_search);
        searchEditText.setTextSize(textSizeSearch);
        searchEditText.setTextColor(textColorSearch);
        searchEditText.setHint(textHintSearch);
        searchEditText.setHintTextColor(Color.WHITE);
        layout = findViewById(R.id.search_block);
        layout.setBackgroundColor(searchBlockColor);
        listView = findViewById(R.id.history_listview);
        clearHistory = findViewById(R.id.tv_clear);
        clearHistory.setVisibility(INVISIBLE);
        searchImage = findViewById(R.id.image_search);
    }

    private void queryData(String tempName) {
        // 1. 模糊搜索
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 2. 创建adapter适配器对象 & 装入模糊搜索的结果
        baseAdapter = new SimpleCursorAdapter(context, android.R.layout.simple_list_item_1, cursor, new String[] { "name" },
                new int[] { android.R.id.text1 }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 3. 设置适配器
        listView.setAdapter(baseAdapter);
        baseAdapter.notifyDataSetChanged();
        // 当输入框为空 & 数据库中有搜索记录时，显示 "删除搜索记录"按钮
        if (tempName.equals("") && cursor.getCount() != 0){
            clearHistory.setVisibility(VISIBLE);
        }
        else {
            clearHistory.setVisibility(INVISIBLE);
        };

    }

    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
        clearHistory.setVisibility(INVISIBLE);
    }


    private boolean hasData(String tempName) {
        // 从数据库中Record表里找到name=tempName的id
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //  判断是否有下一个
        return cursor.moveToNext();
    }

    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    public void setOnClickSearch(ICallBack mCallBack){
        this.iCallBack = mCallBack;

    }

    public void setOnClickBack(BCallBack bCallBack){
        this.bCallBack = bCallBack;
    }
}
