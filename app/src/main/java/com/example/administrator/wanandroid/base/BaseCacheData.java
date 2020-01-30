package com.example.administrator.wanandroid.base;

import java.io.Serializable;

public class BaseCacheData<T> implements Serializable{
    public long updateTimeInMills;
    public T data;

}
