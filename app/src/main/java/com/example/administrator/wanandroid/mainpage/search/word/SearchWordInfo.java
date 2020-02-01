package com.example.administrator.wanandroid.mainpage.search.word;

import java.util.List;

public class SearchWordInfo {

    private List<Data> data;

    private int errorCode;

    private String errorMsg;

    public void setData(List<Data> data){
        this.data = data;
    }
    public List<Data> getData(){
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
        private int id;

        private String link;

        private String name;

        private int order;

        private int visible;

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setLink(String link){
            this.link = link;
        }
        public String getLink(){
            return this.link;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setOrder(int order){
            this.order = order;
        }
        public int getOrder(){
            return this.order;
        }
        public void setVisible(int visible){
            this.visible = visible;
        }
        public int getVisible(){
            return this.visible;
        }
    }
}
