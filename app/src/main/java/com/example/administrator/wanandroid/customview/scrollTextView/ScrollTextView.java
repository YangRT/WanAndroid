package com.example.administrator.wanandroid.customview.scrollTextView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

// 实现文字轮播效果，避免失去焦点而暂停轮播
public class ScrollTextView extends android.support.v7.widget.AppCompatTextView {


    public ScrollTextView(Context context) {
        this(context,null);
    }

    public ScrollTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScrollTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
