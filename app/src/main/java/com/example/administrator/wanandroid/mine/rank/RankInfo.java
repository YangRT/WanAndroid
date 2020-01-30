package com.example.administrator.wanandroid.mine.rank;

import java.util.List;

public class RankInfo {

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

    public class Data
    {
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

        private int level;

        private int rank;

        private int userId;

        private String username;

        public void setCoinCount(int coinCount){
            this.coinCount = coinCount;
        }
        public int getCoinCount(){
            return this.coinCount;
        }
        public void setLevel(int level){
            this.level = level;
        }
        public int getLevel(){
            return this.level;
        }
        public void setRank(int rank){
            this.rank = rank;
        }
        public int getRank(){
            return this.rank;
        }
        public void setUserId(int userId){
            this.userId = userId;
        }
        public int getUserId(){
            return this.userId;
        }
        public void setUsername(String username){
            this.username = username;
        }
        public String getUsername(){
            return this.username;
        }
    }
}






