package com.example.administrator.wanandroid.mine.point;

import java.util.List;

public class PointInfo {


    private Data data;

    private int errorCode;

    private String errorMsg;

    public void setData(Data data){
        this.data = data;
    }
    public Data getData(){
        return this.data;
    }
    public void setErrorCode(int errorCode){
        this.errorCode = errorCode;
    }
    public int getErrorCode(){
        return this.errorCode;
    }
    public void setErrorMsg(String errorMsg){
        this.errorMsg = errorMsg;
    }
    public String getErrorMsg(){
        return this.errorMsg;
    }

    public class Data {
        private int curPage;

        private List<Datas> datas;

        private int offset;

        private boolean over;

        private int pageCount;

        private int size;

        private int total;

        public void setCurPage(int curPage){
            this.curPage = curPage;
        }
        public int getCurPage(){
            return this.curPage;
        }
        public void setDatas(List<Datas> datas){
            this.datas = datas;
        }
        public List<Datas> getDatas(){
            return this.datas;
        }
        public void setOffset(int offset){
            this.offset = offset;
        }
        public int getOffset(){
            return this.offset;
        }
        public void setOver(boolean over){
            this.over = over;
        }
        public boolean getOver(){
            return this.over;
        }
        public void setPageCount(int pageCount){
            this.pageCount = pageCount;
        }
        public int getPageCount(){
            return this.pageCount;
        }
        public void setSize(int size){
            this.size = size;
        }
        public int getSize(){
            return this.size;
        }
        public void setTotal(int total){
            this.total = total;
        }
        public int getTotal(){
            return this.total;
        }
    }

    public class Datas {

        private int coinCount;

        private long date;

        private String desc;

        private int id;

        private String reason;

        private int type;

        private int userId;

        private String userName;

        public void setCoinCount(int coinCount){
            this.coinCount = coinCount;
        }
        public int getCoinCount(){
            return this.coinCount;
        }
        public void setDate(long date){
            this.date = date;
        }
        public long getDate(){
            return this.date;
        }
        public void setDesc(String desc){
            this.desc = desc;
        }
        public String getDesc(){
            return this.desc;
        }
        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setReason(String reason){
            this.reason = reason;
        }
        public String getReason(){
            return this.reason;
        }
        public void setType(int type){
            this.type = type;
        }
        public int getType(){
            return this.type;
        }
        public void setUserId(int userId){
            this.userId = userId;
        }
        public int getUserId(){
            return this.userId;
        }
        public void setUserName(String userName){
            this.userName = userName;
        }
        public String getUserName(){
            return this.userName;
        }
    }
}
