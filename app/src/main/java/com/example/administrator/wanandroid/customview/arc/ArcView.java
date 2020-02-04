package com.example.administrator.wanandroid.customview.arc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

// 利用贝塞尔曲线画弧线
public class ArcView extends View {

    private Paint mPaint;
    private int centerX, centerY;
    private PointF start, end, control;


    public ArcView(Context context) {
        this(context,null);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#FCA019"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        //起点
        start = new PointF(0, 0);
        //终点
        end = new PointF(0, 0);
        //控制点
        control = new PointF(0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerX = getMeasuredWidth() / 2;
        centerY = getMeasuredHeight();
        start.x = 0;
        start.y = getMeasuredHeight()/4 * 3;
        end.x = getMeasuredWidth();
        end.y = start.y = getMeasuredHeight()/4 * 3;
        control.x = centerX;
        control.y = centerY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.moveTo(start.x, start.y);
        path.quadTo(control.x, control.y, end.x, end.y);
        //上面这两句就画出了贝塞尔曲线了
        path.lineTo(end.x, 0);
        path.lineTo(0, 0);
        path.close();
        //填充图形
        canvas.drawPath(path, mPaint);
    }
}
