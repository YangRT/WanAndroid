package com.example.administrator.wanandroid.customview.searchview;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.administrator.wanandroid.R;

public class SearchEditText extends AppCompatEditText {

    private Drawable drawableClear;
    private Drawable drawableBack;

    public SearchEditText(Context context) {
        super(context);
        init();
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setBackground(null);
        drawableBack = ResourcesCompat.getDrawable(getResources(), R.drawable.left_arrow,null);
        drawableClear = ResourcesCompat.getDrawable(getResources(),R.drawable.clear,null);
        setCompoundDrawablesWithIntrinsicBounds(drawableBack, null,
                null, null);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearDrawable(text.length()>0&&hasFocus());
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearDrawable(focused && length()>0);
    }



    private void setClearDrawable(Boolean visible){
        setCompoundDrawablesWithIntrinsicBounds(drawableBack, null,
                visible ? drawableClear : null, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                Drawable drawable = drawableClear;
                if(drawable!=null && x<= (getWidth()-getPaddingRight()) && x >=(getWidth()-getPaddingRight()-drawable.getBounds().width())){
                    setText("");
                }
                break;

        }
        return super.onTouchEvent(event);
    }
}
