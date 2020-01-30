package com.example.administrator.wanandroid.mine;

import com.example.administrator.wanandroid.base.BaseArticleInfo;

public class ShareInfo {

    private int errorCode;
    private String errorMsg;
    private Data data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{

        public MineInfo.Data coinInfo;
        public BaseArticleInfo.DataBean shareArticles;

        public BaseArticleInfo.DataBean getShareArticles() {
            return shareArticles;
        }

        public void setShareArticles(BaseArticleInfo.DataBean shareArticles) {
            this.shareArticles = shareArticles;
        }
    }
}
