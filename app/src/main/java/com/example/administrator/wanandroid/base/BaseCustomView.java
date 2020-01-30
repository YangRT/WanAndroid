package com.example.administrator.wanandroid.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public abstract class BaseCustomView<T extends ViewDataBinding,S extends BaseCustomViewModel> extends LinearLayout implements IBaseCustomView<S>, View.OnClickListener {

    private T dataBinding;
    private S viewModel;
    private ICustomViewActionListener mListener;


    public BaseCustomView(Context context) {
        super(context);
        init();
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public View getRootView(){
        return dataBinding.getRoot();
    }


    private void init(){
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(getViewLayoutId() != 0){
            dataBinding = DataBindingUtil.inflate(inflater,getViewLayoutId(),this,false);
            dataBinding.getRoot().setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        mListener.onAction(v,viewModel);
                    }
                }
            });
        }

    }

    public void setActionListener(ICustomViewActionListener listener){
        mListener = listener;
    }

    protected T getDataBinding(){
        return dataBinding;
    }

    protected S getViewModel(){
        return viewModel;
    }

    protected abstract int getViewLayoutId();

    protected abstract void setDataToView(S data);

    protected abstract void onRootClick(View view);
}
