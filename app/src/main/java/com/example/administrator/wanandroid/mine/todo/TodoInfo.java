package com.example.administrator.wanandroid.mine.todo;

import java.util.List;

public class TodoInfo {

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

    public class Datas {
        private long completeDate;

        private String completeDateStr;

        private String content;

        private long date;

        private String dateStr;

        private int id;

        private int priority;

        private int status;

        private String title;

        private int type;

        private int userId;

        public void setCompleteDate(long completeDate){
            this.completeDate = completeDate;
        }
        public long getCompleteDate(){
            return this.completeDate;
        }
        public void setCompleteDateStr(String completeDateStr){
            this.completeDateStr = completeDateStr;
        }
        public String getCompleteDateStr(){
            return this.completeDateStr;
        }
        public void setContent(String content){
            this.content = content;
        }
        public String getContent(){
            return this.content;
        }
        public void setDate(long date){
            this.date = date;
        }
        public long getDate(){
            return this.date;
        }
        public void setDateStr(String dateStr){
            this.dateStr = dateStr;
        }
        public String getDateStr(){
            return this.dateStr;
        }
        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setPriority(int priority){
            this.priority = priority;
        }
        public int getPriority(){
            return this.priority;
        }
        public void setStatus(int status){
            this.status = status;
        }
        public int getStatus(){
            return this.status;
        }
        public void setTitle(String title){
            this.title = title;
        }
        public String getTitle(){
            return this.title;
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


}
